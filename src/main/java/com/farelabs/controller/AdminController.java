package com.farelabs.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farelabs.dto.Response;
import com.farelabs.dto.SignInResponse;
import com.farelabs.entity.Admin;
import com.farelabs.service.AdminServices;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired 
	private AdminServices adminService;
	

	//register admin
	
	@PostMapping("/registerAdmin")
	public ResponseEntity<Response> registerAdmin(@RequestParam (value ="firstName" , required=true )String firstName,
			@RequestParam (value ="lastName" , required=false )String lastName,
			@RequestParam (value ="email" , required=true )String email,
			@RequestParam (value ="password" , required=true )String password,
			@RequestParam (value ="mobileNumber" , required=true )String mobileNumber,
			@RequestParam (value ="role" , required=true )String role

			
			){
		
		return adminService.registerAdmin(firstName,lastName,email,password,mobileNumber,role);
	}
	
	@PostMapping("/signin")
	private ResponseEntity<SignInResponse> signIn(@RequestBody Admin admin) {
		return adminService.adminSignIn(admin);
	}

	//register users 
	
	@PostMapping("/registerUser")
	public ResponseEntity<Response> registerUser(
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "mobileNumber", required = false) String mobileNumber,
			@RequestParam(value = "username", required = false) String usernames,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "roleId", required = false) String roleId
			){
		
		
		return adminService.registerUser(password,usernames,mobileNumber,email,lastName,firstName,roleId);
	}
	
	@PostMapping("/addRole")
	public ResponseEntity<Response> addRole(
			@RequestParam(value = "role", required = false) String role){

		return adminService.addRole(role);
}
	@GetMapping("/signout/{adminId}")
	private ResponseEntity<Response> signOut(@PathVariable int adminId) {
		return adminService.signOut(adminId);
	}

}
