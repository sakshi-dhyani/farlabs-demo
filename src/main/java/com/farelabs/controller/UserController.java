package com.farelabs.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.farelabs.dto.Response;
import com.farelabs.dto.SignInResponse;
import com.farelabs.entity.User;
import com.farelabs.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired UserService userService;

	
	
	
	@PostMapping("/signin")
	private ResponseEntity<SignInResponse> signin(@RequestParam (value ="id" , required=false )String id,
			@RequestParam (value ="password" , required=false )String password,
			@RequestParam (value ="role" , required=false )String role) {
		
		 
		return userService.signIn(id,password,role);
	}
	
	@PostMapping("/resetPassword")
	  private ResponseEntity<Response> resetPassword(@RequestParam (value ="email" , required=false )String email,
			  @RequestParam (value ="password" , required=false )String password){
		  return userService.resetPassword(email,password);
	  }
	
	//edit user profile
	
	@PutMapping("/editProfile")
	private ResponseEntity<Response>editProfile(@RequestParam (value="picture" , required = false) MultipartFile picture,
			@RequestParam (value ="userId" , required=true )String userId){
		
		
		return userService.editProfile(picture,userId);
	}
	@GetMapping("/downloadProfileImage/{imageName}")
	public ResponseEntity<Resource> downloadClassImage(@PathVariable String imageName) throws IOException {

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		File file = new File("C:\\Farelabs\\User\\User_Images" + imageName);

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new ByteArrayResource(Files.readAllBytes(Paths.get(file.getAbsolutePath()))));
	}
	
	//user sign out
	@GetMapping("/signout/{userId}")
	private ResponseEntity<Response> signOut(@PathVariable int userId) {
		return userService.signOut(userId);
	}
	
	@GetMapping("/myProfile/{userId}")
	public ResponseEntity<Response>myProfile(@PathVariable ("userId") int userId){
		
		return userService.myProfile(userId);
	}
	
	


}
