package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoginDto;
import com.revature.dto.MessageDto;
import com.revature.model.UsersModel;
import com.revature.service.UsersService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthenticationController implements Controller{
	
	private UsersService usersService;
	
	public AuthenticationController() {
		this.usersService = new UsersService();
	}
	
	private Handler login = (ctx) -> {
		LoginDto loginDto = ctx.bodyAsClass(LoginDto.class);
		
		UsersModel user = this.usersService.getUserByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
		
		HttpServletRequest request = ctx.req;
		
		HttpSession session = request.getSession();
		session.setAttribute("currentuser", user);
		
		ctx.json(user);
	};
	
	private Handler logout = (ctx) -> {
		HttpServletRequest request = ctx.req;
		
		request.getSession().invalidate();
	};
	
	private Handler checkLogin = (ctx) -> {
		HttpSession session = ctx.req.getSession();
		
		if(!(session.getAttribute("currentuser") == null)) {
			ctx.json(session.getAttribute("currentuser"));
			ctx.status(200);
		}
		else {
			ctx.json(new MessageDto("User is currently not logged in"));
			ctx.status(401);
		}
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/login", login);
		app.post("/logout", logout);
		app.get("/checkloginstatus", checkLogin);
	}

}
