package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.UsersModel;
import com.revature.dto.AddOrUpdateUsersDto;
import com.revature.util.JDBCUtility;

public class UsersDao {
	
	public UsersModel addUser(AddOrUpdateUsersDto s2) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO Users (users_username, users_password, users_first_name, users_last_name, users_age, users_email, users_role)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, s2.getUserName());
			pstmt.setString(2, s2.getPassword());
			pstmt.setString(3, s2.getFirstName());
			pstmt.setString(4, s2.getLastName());
			pstmt.setInt(5, s2.getAge());
			pstmt.setString(6, s2.getEmail());
			pstmt.setString(7, s2.getRole());
			
			int records = pstmt.executeUpdate();
			
			if (records != 1) {
				throw new SQLException("User cannot be added");
			}
			
			ResultSet results = pstmt.getGeneratedKeys();
			
			results.next();
			int autoGenerate = results.getInt(1);
			
			return new UsersModel(autoGenerate, s2.getUserName(), s2.getPassword(), s2.getFirstName(), s2.getLastName(), s2.getAge(), s2.getEmail(), s2.getRole());
		}
	}
	
	public List<UsersModel> getAllUsers() throws SQLException {
		
		List<UsersModel> usersList = new ArrayList();
		
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM Users";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				int id = result.getInt("users_id");
				String username = result.getString("users_username");
				String password = result.getString("users_password");
				String firstName = result.getString("users_first_name");
				String lastName = result.getString("users_last_name");
				int age = result.getInt("users_age");
				String email = result.getString("users_email");
				String role = result.getString("users_role");
				
				UsersModel u = new UsersModel(id, username, password, firstName, lastName, age, email, role);
				
				usersList.add(u);
			}
		}
		
		return usersList;
	}
	
	public UsersModel getUserById(int id) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM Users WHERE users_id = ?;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet result = pstmt.executeQuery();
			
			if(result.next()) {
				return new UsersModel(result.getInt("users_id"), result.getString("users_username"), result.getString("users_password"), result.getString("users_first_name"), 
							result.getString("users_last_name"), result.getInt("users_age"), result.getString("users_email"), result.getString("users_role"));
			} else {
				return null;
			}
		}
	}
	
	public UsersModel getUserByUsernameAndPassword(String username, String password) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM Users WHERE users_username = ? AND users_password = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet result = pstmt.executeQuery();
			
			if (result.next()) {
				int id = result.getInt("users_id");
				String user = result.getString("users_username");
				String pass = result.getString("users_password");
				String firstName = result.getString("users_first_name");
				String lastName = result.getString("users_last_name");
				int age = result.getInt("users_age");
				String email = result.getString("users_email");
				String role = result.getString("users_role");
				
				return new UsersModel(id, user, pass, firstName, lastName, age, email, role);
			}
			else {
				return null;
			}
		}
	}
	
	public UsersModel updateUserById(int userId, AddOrUpdateUsersDto user) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE Users"
					+ "	SET users_username = ?,"
					+ "		users_password = ?,"
					+ "		users_first_name = ?,"
					+ "		users_last_name = ?,"
					+ "		users_age = ?,"
					+ "		users_email = ?,"
					+ "		users_role = ?"
					+ "WHERE "
					+ "	users_id = ?;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstName());
			pstmt.setString(4, user.getLastName());
			pstmt.setInt(5, user.getAge());
			pstmt.setString(6, user.getEmail());
			pstmt.setString(7, user.getRole());
			pstmt.setInt(8, userId);
			
			int recordsUpdated = pstmt.executeUpdate();
			
			if (recordsUpdated != 1) {
				throw new SQLException("User update has failed");
			}
		}
		
		return new UsersModel(userId, user.getUserName(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getAge(), user.getEmail(), user.getRole());
	}

}
