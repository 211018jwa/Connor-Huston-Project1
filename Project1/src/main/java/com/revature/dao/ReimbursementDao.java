package com.revature.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.ReimbursementModel;
import com.revature.model.UsersModel;
import com.revature.dto.AddOrUpdateReimbursementDto;
import com.revature.util.JDBCUtility;

public class ReimbursementDao {
	
	public ReimbursementModel addReimbursement(UsersModel user, double reimbursementAmount, String reimbursementType, String reimbursementDescription, InputStream image) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			con.setAutoCommit(false);
			
			String sql = "INSERT INTO Reimbursement	(reimbursement_amount, reimbursement_submitted, reimbursement_status, "
					+ "reimbursement_type, reimbursement_description, reimbursement_reciept, reimbursement_author)"
					+ "VALUES (?, now(), ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, reimbursementAmount);
			pstmt.setString(2, "Pending");
			pstmt.setString(3, reimbursementType);
			pstmt.setString(4, reimbursementDescription);
			pstmt.setBinaryStream(5, image);
			pstmt.setInt(6, user.getId());
			
			int records = pstmt.executeUpdate();
			
			if (records != 1) {
				throw new SQLException("Reimbursement unsuccessfully added");
			}
			
			ResultSet results = pstmt.getGeneratedKeys();
			
			results.next();
			int autoGenerate = results.getInt(1);
			
			con.commit();
			return new ReimbursementModel(autoGenerate, reimbursementAmount, results.getString("reimbursement_submitted"), results.getString("reimbursement_resolved"), 
					"Pending", reimbursementType, reimbursementDescription, user.getId(), 0);
		}
	}
	
	public List<ReimbursementModel> getAllReimbursements() throws SQLException {
		
		List<ReimbursementModel> reimbursementList = new ArrayList();
		
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM Reimbursement";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				int id = result.getInt("reimbursement_id");
				double amount = result.getDouble("reimbursement_amount");
				String submitted = result.getString("reimbursement_submitted");
				String resolved = result.getString("reimbursement_resolved");
				String status = result.getString("reimbursement_status");
				String type = result.getString("reimbursement_type");
				String description = result.getString("reimbursement_description");
				int author = result.getInt("reimbursement_author");
				int resolver = result.getInt("reimbursement_resolver");
				
				ReimbursementModel r = new ReimbursementModel(id, amount, submitted, resolved, status, type, description, author, resolver);
				
				reimbursementList.add(r);
			}
		}
		
		return reimbursementList;
	}
	
	public List<ReimbursementModel> getAllReimbursementByAuthorId(int authorId) throws SQLException {
		
		List<ReimbursementModel> reimbursementList = new ArrayList();
		
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM Reimbursement WHERE reimbursement_author = ?;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, authorId);
			ResultSet result = pstmt.executeQuery();
			
			while (result.next()) {
				int id = result.getInt("reimbursement_id");
				double amount = result.getDouble("reimbursement_amount");
				String submitted = result.getString("reimbursement_submitted");
				String resolved = result.getString("reimbursement_resolved");
				String status = result.getString("reimbursement_status");
				String type = result.getString("reimbursement_type");
				String description = result.getString("reimbursement_description");
				int author = result.getInt("reimbursement_author");
				int resolver = result.getInt("reimbursement_resolver");
				
				ReimbursementModel r = new ReimbursementModel(id, amount, submitted, resolved, status, type, description, author, resolver);
				
				reimbursementList.add(r);
			}
		}
		
		return reimbursementList;
	}
	
	public void updateReimbursementById(int reimbursementId, String status, int resolverId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE REIMBURSEMENT "
					+ "	SET reimbursement_resolved = now(),"
					+ "		reimbursement_status = ?,"
					+ "		reimbursement_resolver = ?"
					+ "WHERE "
					+ "reimbursement_id = ?;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, status);
			pstmt.setInt(2, resolverId);
			pstmt.setInt(3, reimbursementId);
			
			int recordsUpdated = pstmt.executeUpdate();
			
			if (recordsUpdated != 1) {
				throw new SQLException("Update has been unsuccessful");
			}
		}
		
	}
	
	public InputStream getImageFromReimbursementById(int id) throws SQLException {
		try(Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT reimbursement_reciept "
					+ "FROM Reimbursement WHERE reimbursement_id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			ResultSet result = pstmt.executeQuery();
			
			if(result.next()) {
				InputStream image = result.getBinaryStream("reimbursement_reciept");
				
				return image;
			}
			
			return null;
		}
	}

}
