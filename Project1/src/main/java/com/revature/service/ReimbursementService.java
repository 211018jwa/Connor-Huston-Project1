package com.revature.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.security.auth.login.FailedLoginException;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddOrUpdateReimbursementDto;
import com.revature.exceptions.ImageNotFoundException;
import com.revature.exceptions.ReimbursementNotFound;
import com.revature.exceptions.Unauthorized;
import com.revature.exceptions.InvalidParameter;
import com.revature.model.ReimbursementModel;
import com.revature.model.UsersModel;

public class ReimbursementService {
	
	private Logger logger = LoggerFactory.getLogger(ReimbursementService.class);
	
	private ReimbursementDao reimbursementDao;
	
	public ReimbursementService() {
		this.reimbursementDao = new ReimbursementDao();
	}
	
	public ReimbursementService(ReimbursementDao mockReimbursementDao) {
		this.reimbursementDao = mockReimbursementDao;
	}
	
	public List<ReimbursementModel> getAllReimbursements(UsersModel currentlyLoggedInUser) throws SQLException {
		List<ReimbursementModel> reimbursements = null;
		
		if(currentlyLoggedInUser.getRole().equals("Employee")) {
			reimbursements = this.reimbursementDao.getAllReimbursementByAuthorId(currentlyLoggedInUser.getId());
		}
		else if(currentlyLoggedInUser.getRole().equals("Finance Manager")) {
			reimbursements = this.reimbursementDao.getAllReimbursements();
		}
		
		return reimbursements;
		
	}
	
	public ReimbursementModel addReimbursement ( UsersModel user, String mimeType, String reimbursementAmount, String reimbursementType, String reimbursementDescription, InputStream content) throws SQLException, InvalidParameter, ReimbursementNotFound, ImageNotFoundException {
		double amount = Double.parseDouble(reimbursementAmount);
		
		if (amount <= 0) {
			throw new InvalidParameter("You can't pay with 0 dollars");
		}
		
		Set<String> validType = new HashSet<>();
		validType.add("Lodging");
		validType.add("Travel");
		validType.add("Food");
		validType.add("Other");
		
		
		if (reimbursementDescription.trim().equals("")) {
			throw new InvalidParameter("A description must be added in for this reimbursement");
		}
		
		Set<String> allowedFileTypes = new HashSet<>();
		allowedFileTypes.add("image/jpeg");
		allowedFileTypes.add("image/png");
		allowedFileTypes.add("image/gif");
		
		if(!allowedFileTypes.contains(mimeType)) {
			throw new InvalidParameter("Only PNG, JPEG, and/or GIF images can be added");
		}
		
		ReimbursementModel insertReimbursement = this.reimbursementDao.addReimbursement(user, amount, reimbursementType, reimbursementDescription, content);
		
		return insertReimbursement;
	}
	
	public void updateReimbursementById(String id1, String id2, String id3) throws SQLException, InvalidParameter, ReimbursementNotFound {
		
		logger.info("Invoked the updateReimbursementById method");
		
		try {
			int reimbursementId = Integer.parseInt(id1);
			String status = id2;
			int resolverId = Integer.parseInt(id3);
			
			this.reimbursementDao.updateReimbursementById(reimbursementId, status, resolverId);
		} catch(NumberFormatException e) {
			logger.warn("Reimbursement, status, or resolver id does not exist!");
			throw new InvalidParameter("One or more invalid id(s) has been detected");
		}
	}
	
	public InputStream getImageFromReimbursementById(UsersModel currentlyLoggedInUser, String reimbursementId) throws SQLException, InvalidParameter, Unauthorized, ImageNotFoundException {
		
		try {
			int id = Integer.parseInt(reimbursementId);
			
			if (currentlyLoggedInUser.getRole().equals("Employee")) {
				
				int employeeId = currentlyLoggedInUser.getId();
				List<ReimbursementModel> employeesReimbursement = this.reimbursementDao.getAllReimbursementByAuthorId(employeeId);
				
				Set<Integer> reimbursementIdsEncountered = new HashSet<>();
				for (ReimbursementModel r : employeesReimbursement) {
					reimbursementIdsEncountered.add(r.getId());
				}
				
				if (!reimbursementIdsEncountered.contains(id)) {
					throw new Unauthorized("You don't have access to these images");
				}
			}
			
			InputStream image = this.reimbursementDao.getImageFromReimbursementById(id);
			
			if (image == null) {
				throw new ImageNotFoundException("Image was not found in this reimbursement");
			}
			
			return image;
		} catch (NumberFormatException e) {
			throw new InvalidParameter("Reimbursement id must be int");
		}
	}

}
