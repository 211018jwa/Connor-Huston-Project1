package com.revature.service;

import com.revature.exceptions.Unauthorized;
import com.revature.model.UsersModel;

public class AuthorizationService {
	
	public void authorizeRegularOrManager(UsersModel user) throws Unauthorized {
		
		if (user == null || !(user.getRole().equals("Employee") || user.getRole().equals("Finance Manager"))) {
			throw new Unauthorized("Only employees or finance managers have access beyond this point");
		}
	}
	
	public void authorizeManager(UsersModel user) throws Unauthorized {
		
		if (user == null || !user.getRole().equals("Finance Manager")) {
			throw new Unauthorized("Only finance managers have access beyond this point");
		}
	}
	
	public void authorizeRegular(UsersModel user) throws Unauthorized {
		
		if (user == null || !user.getRole().equals("Employee")) {
			throw new Unauthorized("Only employees have access beyond this point");
		}
	}

}
