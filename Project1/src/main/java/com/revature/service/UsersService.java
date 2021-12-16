package com.revature.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.security.auth.login.FailedLoginException;

import com.revature.dao.UsersDao;
import com.revature.dto.AddOrUpdateUsersDto;
import com.revature.exceptions.UserNotFound;
import com.revature.exceptions.InvalidParameter;
import com.revature.model.UsersModel;

public class UsersService {
	
	private Logger logger = LoggerFactory.getLogger(UsersService.class);
	
	private UsersDao usersDao;
	
	public UsersService() {
		this.usersDao = new UsersDao();
	}
	
	public UsersService(UsersDao mockUsersDao) {
		this.usersDao = mockUsersDao;
	}
	
	public UsersModel getUserByUsernameAndPassword(String username, String password) throws SQLException, FailedLoginException {
		
		UsersModel user = this.usersDao.getUserByUsernameAndPassword(username, password);
		
		if (user == null) {
			throw new FailedLoginException("Username and/or password is incorrect. Please try again");
		}
		
		return user;
	}
	
	public UsersModel addUser (AddOrUpdateUsersDto dto) throws SQLException, InvalidParameter, UserNotFound {
		if (dto.getUserName().trim().equals("") || dto.getPassword().trim().equals("")) {
			throw new InvalidParameter("A username and password is requied to be filled out.");
		}
		
		if (dto.getFirstName().trim().equals("") || dto.getLastName().trim().equals("")) {
			throw new InvalidParameter("First name and/or last name is blank. Please fill them both out");
		}
		
		if (dto.getAge() < 21) {
			throw new InvalidParameter("Age can't be less than 21. Enter in a valid age.");
		}
		
		if (dto.getEmail().trim().equals("")) {
			throw new InvalidParameter("An email is required to be entered in");
		}
		
		Set<String> validRole = new HashSet<>();
		validRole.add("Employee");
		validRole.add("Finance Manager");
		
		if (!validRole.contains(dto.getRole())) {
			throw new InvalidParameter("Your role can only be accepted if you are an Employee or a Finance Manager.");
		}
		
		dto.setUserName(dto.getUserName().trim());
		dto.setPassword(dto.getPassword().trim());
		dto.setFirstName(dto.getFirstName().trim());
		dto.setLastName(dto.getLastName().trim());
		dto.setEmail(dto.getEmail().trim());
		dto.setRole(dto.getRole().trim());
		
		UsersModel insertUser = this.usersDao.addUser(dto);
		
		return insertUser;
	}
	
	public List<UsersModel> getAllUsers() throws SQLException, InvalidParameter, UserNotFound {
		List<UsersModel> users = this.usersDao.getAllUsers();
		
		return users;
	}
	
	public UsersModel getUserById(String userId) throws SQLException, InvalidParameter, UserNotFound {
		
		logger.info("Invoked getUserById method");
		
		try {
			int id = Integer.parseInt(userId);
			
			UsersModel u = this.usersDao.getUserById(id);
			if (u == null) {
				logger.warn("User id does not exist");
				throw new UserNotFound("Invalid id for this user");
			}
			return u;
		}catch(NumberFormatException e) {
			throw new InvalidParameter("Id provided is not an int");
		}
	}
	
	public UsersModel updateUserById(String id, AddOrUpdateUsersDto dto) throws SQLException, InvalidParameter, UserNotFound {
		
		logger.info("Invoked updateUserById method");
		
		try {
			
			int usersId = Integer.parseInt(id);
			
			UsersModel userIdUpdate = this.usersDao.getUserById(usersId);
			if (userIdUpdate == null) {
				logger.warn("User id does not exist");
				throw new UserNotFound("User id that was just entered in was notfound ");
			}
			
			UsersModel userEditing = this.usersDao.updateUserById(usersId, dto);
			return userEditing;
		} catch (NumberFormatException e) {
			throw new InvalidParameter("Id is invalid");
		}
	}

}
