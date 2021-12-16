package com.revature.controller;

import java.security.InvalidParameterException;

import javax.security.auth.login.FailedLoginException;

import com.revature.dto.MessageDto;
import com.revature.exceptions.Unauthorized;
import com.revature.exceptions.ImageNotFoundException;
import com.revature.exceptions.ReimbursementNotFound;

import io.javalin.Javalin;

public class ExceptionMapper {
	
	public void mapExceptions(Javalin app) {
		app.exception(FailedLoginException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new MessageDto(e.getMessage()));
		});
		
		app.exception(Unauthorized.class, (e, ctx) -> {
			ctx.status(401);
			ctx.json(new MessageDto(e.getMessage()));
		});
		
		app.exception(ImageNotFoundException.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new MessageDto(e.getMessage()));
		});
		
		app.exception(ReimbursementNotFound.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new MessageDto(e.getMessage()));
		});
	}

}
