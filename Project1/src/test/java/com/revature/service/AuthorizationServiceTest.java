package com.revature.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.exceptions.Unauthorized;
import com.revature.model.UsersModel;

public class AuthorizationServiceTest {
	
private AuthorizationService auth;
	
	@BeforeEach
	public void setUp() {
		this.auth = new AuthorizationService();
	}
	
	@Test
	public void authorizeRegular_positiveTest_employeeUser() throws Unauthorized {
		UsersModel user = new UsersModel(1, "JJJameson09", "password12", "J John", "Jameson", 30, "JJohns43@yahoo.org", "Employee");
		
		this.auth.authorizeRegular(user);
	}
	
	@Test
	public void authorizeRegular_negativeTest_userIsNull() {
		
		Assertions.assertThrows(Unauthorized.class, () -> {
			this.auth.authorizeRegular(null);
		});
	}
	
	@Test
	public void authorizeRegular_negativeTest_employeeUserButIsActuallyFinanceManager() {
		UsersModel user = new UsersModel(1, "JJJameson09", "password12", "J John", "Jameson", 30, "JJohns43@yahoo.org", "Finance Manager");
		
		Assertions.assertThrows(Unauthorized.class, () -> {
			this.auth.authorizeRegular(user);
		});
	}
	
	@Test
	public void authorizeManager_positiveTest_financeManagerUser() throws Unauthorized {
		UsersModel user = new UsersModel(1, "JJJameson09", "password12", "J John", "Jameson", 30, "JJohns43@yahoo.org", "Finance Manager");
		
		this.auth.authorizeManager(user);
	}
	
	@Test
	public void authorizeManager_negativeTest_userIsNull() {
		
		Assertions.assertThrows(Unauthorized.class, () -> {
			this.auth.authorizeManager(null);
		});
	}
	
	@Test
	public void authorizeManager_negativeTest_financeManagerUserButIsActuallyEmployee() {
		UsersModel user = new UsersModel(1, "JJJameson09", "password12", "J John", "Jameson", 30, "JJohns43@yahoo.org", "Employee");
		
		Assertions.assertThrows(Unauthorized.class, () -> {
			this.auth.authorizeManager(user);
		});
	}
	
	@Test
	public void authorizeRegularOrManager_positiveTest_employeeUser() throws Unauthorized {
		UsersModel user = new UsersModel(1, "JJJameson09", "password12", "J John", "Jameson", 30, "JJohns43@yahoo.org", "Employee");
		
		this.auth.authorizeRegularOrManager(user);
	}
	
	@Test
	public void authorizeRegularOrManager_positiveTest_financeManagerUser() throws Unauthorized {
		UsersModel user = new UsersModel(1, "JJJameson09", "password12", "J John", "Jameson", 30, "JJohns43@yahoo.org", "Finance Manager");
		
		this.auth.authorizeRegularOrManager(user);
	}
	
	@Test
	public void authorizeRegularOrManager_negativeTest_userIsNull() {
		
		Assertions.assertThrows(Unauthorized.class, () -> {
			this.auth.authorizeRegularOrManager(null);
		});
	}
	
	@Test
	public void authorizeRegularOrManager_negativeTest_userIsNotEmployeeOrFinanceManager() {
		UsersModel user = new UsersModel(1, "JJJameson09", "password12", "J John", "Jameson", 30, "JJohns43@yahoo.org", "Associate");
		
		Assertions.assertThrows(Unauthorized.class, () -> {
			this.auth.authorizeRegularOrManager(user);
		});
	}

}
