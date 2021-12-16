package com.revature.controller;

import com.revature.exceptions.UserNotFound;
import com.revature.exceptions.ReimbursementNotFound;
import com.revature.exceptions.InvalidParameter;

import java.io.InputStream;
import java.util.List;

import org.apache.tika.Tika;

import com.revature.dto.AddOrUpdateUsersDto;
import com.revature.dto.AddOrUpdateReimbursementDto;
import com.revature.dto.MessageDto;
import com.revature.model.UsersModel;
import com.revature.model.ReimbursementModel;
import com.revature.service.UsersService;
import com.revature.service.ReimbursementService;
import com.revature.service.AuthorizationService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

public class UserReimbursementController implements Controller{
	
	private AuthorizationService authorizedService;
	private UsersService usersService;
	private ReimbursementService reimbursementService;
	
	public UserReimbursementController() {
		this.authorizedService = new AuthorizationService();
		this.usersService = new UsersService();
		this.reimbursementService = new ReimbursementService();
	}
	
	private Handler addUser = (ctx) -> {
		UsersModel user = (UsersModel) ctx.req.getSession().getAttribute("currentuser");
		this.authorizedService.authorizeRegularOrManager(user);
		
		
		AddOrUpdateUsersDto dto = ctx.bodyAsClass(AddOrUpdateUsersDto.class);
		
		UsersModel u = this.usersService.addUser(dto);
		
		ctx.json(u);
		ctx.status(201);
		
	};
	
	private Handler getUserById = (ctx) -> {
		UsersModel user = (UsersModel) ctx.req.getSession().getAttribute("currentuser");
		this.authorizedService.authorizeRegularOrManager(user);
		
		String id = ctx.pathParam("users_id");
		
		try {
			UsersModel c = this.usersService.getUserById(id);
			
			ctx.json(c);
		} catch(InvalidParameter e) {
			ctx.status(400);
			ctx.json(e);
		} catch(UserNotFound e) {
			ctx.status(400);
			ctx.json(e);
		}
		
	};
	
	private Handler getAllUsers = (ctx) -> {
		UsersModel user = (UsersModel) ctx.req.getSession().getAttribute("currentuser");
		this.authorizedService.authorizeManager(user);
		
		List<UsersModel> users = this.usersService.getAllUsers();
		
		ctx.json(users);
		
	};
	
	private Handler updateUserById = (ctx) -> {
		UsersModel user = (UsersModel) ctx.req.getSession().getAttribute("currentuser");
		this.authorizedService.authorizeManager(user);
		
		String userId = ctx.pathParam("users_id");
		
		AddOrUpdateUsersDto dto = ctx.bodyAsClass(AddOrUpdateUsersDto.class);
		UsersModel userEdited = this.usersService.updateUserById(userId, dto);
		
		ctx.json(userEdited);
		
	};
	
	private Handler addReimbursement = (ctx) -> {
		UsersModel user = (UsersModel) ctx.req.getSession().getAttribute("currentuser");
		this.authorizedService.authorizeRegular(user);
		
		String reimbursementAmount = ctx.formParam("amount");
		String reimbursementType = ctx.formParam("type");
		String reimbursementDescription = ctx.formParam("description");
		
		UploadedFile file = ctx.uploadedFile("reciept");
		
		if (file == null) {
			ctx.status(400);
			ctx.json(new MessageDto("Must have an image uploaded"));
			return;
		}
		InputStream content = file.getContent();
		
		Tika tika1 = new Tika();
		String mimeType = tika1.detect(content);
		ReimbursementModel r = this.reimbursementService.addReimbursement(user, mimeType, reimbursementAmount, reimbursementType, reimbursementDescription, content);
		
		ctx.json(r);
		ctx.status(201);
	};
	
	private Handler getAllReimbursements = (ctx) -> {
		UsersModel user = (UsersModel) ctx.req.getSession().getAttribute("currentuser");
		this.authorizedService.authorizeRegularOrManager(user);
		
		List<ReimbursementModel> reimbursements = this.reimbursementService.getAllReimbursements(user);
		
		ctx.json(reimbursements);
	};
	
	private Handler updateReimbursementById = (ctx) -> {
		UsersModel user = (UsersModel) ctx.req.getSession().getAttribute("currentuser");
		this.authorizedService.authorizeManager(user);
		
		String reimbursementId = ctx.pathParam("reimbursement_id");
		
		AddOrUpdateReimbursementDto dto = ctx.bodyAsClass(AddOrUpdateReimbursementDto.class);
		
		this.reimbursementService.updateReimbursementById(reimbursementId, dto.getStatus(), Integer.toString(user.getId()));
		ctx.result("Reimbursement has been updated");
	};
	
	private Handler getImageFromReimbursementById = (ctx) -> {
		UsersModel user = (UsersModel) ctx.req.getSession().getAttribute("currentuser");
		this.authorizedService.authorizeRegularOrManager(user);
		
		String reimbursementId = ctx.pathParam("reimbursement_id");
		
		InputStream image = this.reimbursementService.getImageFromReimbursementById(user, reimbursementId);
		
		Tika tika = new Tika();
		String mimeType = tika.detect(image);
		
		ctx.contentType(mimeType);
		ctx.result(image);
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/users", addUser);
		app.get("/users", getAllUsers);
		app.get("/users/{users_id}", getUserById);
		app.put("/users/{users_id}", updateUserById);
		app.post("/reimbursements", addReimbursement);
		app.get("/reimbursements", getAllReimbursements);
		app.put("/reimbursements/{reimbursement_id}", updateReimbursementById);
		app.get("/reimbursements/{reimbursement_id}/image", getImageFromReimbursementById);
	}

}
