package com.farelabs.service;

import java.io.File;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.farelabs.Authentication.JwtTokenHelper;
import com.farelabs.dto.Response;
import com.farelabs.dto.SignInResponse;
import com.farelabs.dto.UserResponse;
import com.farelabs.entity.Admin;
import com.farelabs.entity.Notification;
import com.farelabs.entity.Role;
import com.farelabs.entity.User;
import com.farelabs.repository.NotificationRepository;
import com.farelabs.repository.RoleRepository;
import com.farelabs.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired private NotificationRepository notificationRepository;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	Response response = new Response();
	SignInResponse signinResponse = new SignInResponse();
	UserResponse userResponse = new UserResponse();


	public ResponseEntity<SignInResponse> signIn(String id, String password, String role) {
		
		if(id==null) {
			signinResponse.setCode(404);
			signinResponse.setError(HttpStatus.NOT_FOUND.name());
			signinResponse.setMessage("enter id");
			signinResponse.setResult(null);
			return new  ResponseEntity<SignInResponse>(signinResponse,HttpStatus.NOT_FOUND);
		}
		if(password==null) {
			signinResponse.setCode(404);
			signinResponse.setError(HttpStatus.NOT_FOUND.name());
			signinResponse.setMessage("enter password");
			signinResponse.setResult(null);
			return new  ResponseEntity<SignInResponse>(signinResponse,HttpStatus.NOT_FOUND);
		}
		if(role==null) {
			signinResponse.setCode(404);
			signinResponse.setError(HttpStatus.NOT_FOUND.name());
			signinResponse.setMessage("enter roleId");
			signinResponse.setResult(null);
			return new  ResponseEntity<SignInResponse>(signinResponse,HttpStatus.NOT_FOUND);
		}
//		int role = Integer.parseInt(roleId);
		
		
		if(id!=null) {
		
			  User userEmail = userRepository.findByEmail(id);
                          
			if(userEmail!=null) {
				Role getRole = roleRepository.findByName(role);
				if(getRole!=userEmail.getRole()) {
					signinResponse.setCode(404);
					signinResponse.setError(HttpStatus.NOT_FOUND.name());
					signinResponse.setMessage("no role found");
					signinResponse.setResult(null);
					return new  ResponseEntity<SignInResponse>(signinResponse,HttpStatus.NOT_FOUND);
				}
				
				else {
					
				
				try {
					this.authenticate(id, password);
				} catch (Exception e) {
					System.out.println(e);
					signinResponse.setMessage("Please try with correct password");
					signinResponse.setCode(401);
					signinResponse.setError(HttpStatus.UNAUTHORIZED.name());
					signinResponse.setToken(null);
					signinResponse.setResult(null);
					return new ResponseEntity<SignInResponse>(signinResponse, HttpStatus.UNAUTHORIZED);
				}
		
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(id);
				
				
		
				String token = this.jwtTokenHelper.generateToken(userDetails);
				setUserDetails(userEmail);
				userEmail.setActive((byte)1);
				userEmail.setIsLoggedIn((byte)1);
				userEmail.setLastLoggedInTime(System.currentTimeMillis());

				userRepository.save(userEmail);
				
				signinResponse.setCode(200);
				signinResponse.setMessage("successfully sign in");
				signinResponse.setError(null);
				signinResponse.setToken(token);
				signinResponse.setResult(userResponse); 

				//show user response data
				return new ResponseEntity<SignInResponse>(signinResponse,HttpStatus.OK);
				
				
				
			}}
		}
			
			
		if(id!=null){
				User userMobile = userRepository.findByMobileNumber(id);
				if(userMobile!=null) {
					Role getRole = roleRepository.findByName(role);
					if(getRole!=userMobile.getRole()) {
						signinResponse.setCode(404);
						signinResponse.setError(HttpStatus.NOT_FOUND.name());
						signinResponse.setMessage("no role found");
						signinResponse.setResult(null);
						return new  ResponseEntity<SignInResponse>(signinResponse,HttpStatus.NOT_FOUND);
					}
					
					else {
					
					try {
						this.authenticate(userMobile.getEmail(), password);
					} catch (Exception e) {
						System.out.println(e);
						signinResponse.setMessage("Please try with correct password");
						signinResponse.setCode(401);
						signinResponse.setError(HttpStatus.UNAUTHORIZED.name());
						signinResponse.setToken(null);
						signinResponse.setResult(null);
						return new ResponseEntity<SignInResponse>(signinResponse, HttpStatus.UNAUTHORIZED);
					}
			
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(userMobile.getEmail());
			
					String token = this.jwtTokenHelper.generateToken(userDetails);
					setUserDetails(userMobile);
					userMobile.setActive((byte)1);

					userMobile.setLastLoggedInTime(System.currentTimeMillis());

					userMobile.setIsLoggedIn((byte)1);

					userRepository.save(userMobile);
					
					signinResponse.setCode(200);
					signinResponse.setMessage("successfully sign in");
					signinResponse.setError(null);
					signinResponse.setToken(token);
					signinResponse.setResult(userResponse);  //show user response data

					return new ResponseEntity<SignInResponse>(signinResponse,HttpStatus.OK);
					
					
					
				}
			}}
			
		if(id!=null){
				User getUser = userRepository.findByUsernames(id);
				if(getUser!=null) {
					Role getRole = roleRepository.findByName(role);
					if(getRole!=getUser.getRole()) {
						signinResponse.setCode(404);
						signinResponse.setError(HttpStatus.NOT_FOUND.name());
						signinResponse.setMessage("no role found");
						signinResponse.setResult(null);
						return new  ResponseEntity<SignInResponse>(signinResponse,HttpStatus.NOT_FOUND);
					}
					
					else {
					
					
					try {
						this.authenticate(getUser.getEmail(), password);
					} catch (Exception e) {
						System.out.println(e);
						signinResponse.setMessage("Please try with correct password");
						signinResponse.setCode(401);
						signinResponse.setError(HttpStatus.UNAUTHORIZED.name());
						signinResponse.setToken(null);
						signinResponse.setResult(null);
						return new ResponseEntity<SignInResponse>(signinResponse, HttpStatus.UNAUTHORIZED);
					}
			
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUser.getEmail());
			
					String token = this.jwtTokenHelper.generateToken(userDetails);
					setUserDetails(getUser);
					getUser.setIsLoggedIn((byte)1);
					getUser.setActive((byte)1);

					getUser.setLastLoggedInTime(System.currentTimeMillis());

					userRepository.save(getUser);
					
					Notification notification = new Notification();
					notification.setActive((byte) 1);
					notification.setUserReciever(getUser);
					notification.setTitle("signin successfull");
					notification.setMessage("signin successfull");
					notification.setCreationTimestamp(new Timestamp(new Date().getTime()));
					notification.setSeen((byte) 1);
					notification.setCreatedby("Application");
					notificationRepository.save(notification);


					
					signinResponse.setCode(200);
					signinResponse.setMessage("successfully sign in");
					signinResponse.setError(null);
					signinResponse.setToken(token);
					signinResponse.setResult(userResponse);  //show user response data

					return new ResponseEntity<SignInResponse>(signinResponse,HttpStatus.OK);
					
					
					
				}}
				
			}
	
	
		signinResponse.setCode(404);
		signinResponse.setMessage("user not found");
		signinResponse.setError(HttpStatus.NOT_FOUND.name());
		signinResponse.setToken(null);
		signinResponse.setResult(null);  //show user response data
		return new ResponseEntity<SignInResponse>(signinResponse,HttpStatus.NOT_FOUND);

	

	
	

	}
	
	private void authenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
				password);

		this.authenticationManager.authenticate(authenticationToken);

	}
	
	//userSignIn response
	private User setUserDetails(User user) {
	
		userResponse.setId(user.getId());
		userResponse.setEmail(user.getEmail());
		userResponse.setFirstName(user.getFirstName());
		userResponse.setLastName(user.getLastName());
		userResponse.setImage(null);
		userResponse.setMobile(user.getMobileNumber());
		userResponse.setRole(user.getRole().getName());
		
		return user;
		
	
	}

	
	public ResponseEntity<Response> resetPassword(String email, String password) {
		User getUser = userRepository.findByEmail(email);
		if(getUser==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("user not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		getUser.setPassword(new BCryptPasswordEncoder().encode(password));
		getUser.setModificationTime(System.currentTimeMillis());
		userRepository.save(getUser);
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("password reset successfull");
		notification.setMessage("password reset successfull");
		notification.setCreationTimestamp(new Timestamp(new Date().getTime()));
		notification.setSeen((byte) 1);
		notification.setCreatedby("Application");
		notificationRepository.save(notification);
		
		response.setCode(200);
		response.setError(null);
		response.setMessage("password updated succefully");
		response.setResult(null);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	public ResponseEntity<Response> editProfile(MultipartFile picture, String userId) {
		if(userId==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("enter user id");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		if(picture==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("enter picture");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		
		int user =  Integer.parseInt(userId);
		User getUser = userRepository.findByIdAndActive(user , (byte)1);
		if(getUser==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("user not found");
			response.setResult(null);
		
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		String filePath = null;
		try {
			filePath = createFile(picture);
			System.out.println(filePath);

			Response response = new Response();

			if (filePath != null) {
				response.setResult(filePath);
				response.setMessage("Image Uploaded");
			} else
				response.setMessage("Image Not Uploaded");
		} catch (Exception e) {
			System.out.println(e);
		}

		if (filePath != null)
			getUser.setProfilePicture(filePath);
		userRepository.save(getUser);
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("profile edited successfull");
		notification.setMessage("profile edited successfull");
		notification.setCreationTimestamp(new Timestamp(new Date().getTime()));
		notification.setSeen((byte) 1);
		notification.setCreatedby("Application");
		notificationRepository.save(notification);
		
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(null);
	
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	


	private String createFile(MultipartFile multipartFile) {

		File file = new File("C:\\Farelabs\\User\\User_Images\\");
		if (!file.exists())
			file.mkdir();

		String fileName = multipartFile.getOriginalFilename().split("\\.")[0] + "."
				+ multipartFile.getOriginalFilename().split("\\.")[1];

		try {
			multipartFile.transferTo(new File(file.getAbsolutePath() + "\\" + fileName));
		} catch (IllegalStateException | IOException e) {

			return e.getMessage();
		}
		return "http://18.218.231.50/user/downloadProfileImage/" + fileName;
	}

	public ResponseEntity<Response> signOut(int userId) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email1 = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
		System.out.println(email1);
		User getUser = userRepository.findByEmail(email1);
		if (getUser == null) {
			response.setMessage("invalid token");
			response.setError(HttpStatus.UNAUTHORIZED.name());
			response.setCode(401);
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);
		}
		
		User user = userRepository.findByIdAndActive(userId, (byte) 1);
		if (user == null) {
			response.setMessage("user not found!");
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		}
		user.setIsLoggedIn((byte) 0);
		user.setLastLoggedOutTime(System.currentTimeMillis());
		userRepository.save(user);
		

		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(user);
		notification.setTitle("sign-out successfull");
		notification.setMessage("sign-out successfull");
		notification.setCreationTimestamp(new Timestamp(new Date().getTime()));
		notification.setSeen((byte) 1);
		notification.setCreatedby("Application");
		notificationRepository.save(notification);
		
		
		response.setMessage("logged out successfully");
		response.setCode(200);
		response.setError(null);
		response.setResult(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	public ResponseEntity<Response> myProfile(int userId) {
		
		User getUser = userRepository.findByIdAndActive(userId, (byte)1);
		if(getUser==null) {
			response.setMessage("user not found!");
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		
		}
		setUserDetails(getUser);

	
	response.setCode(200);
	response.setMessage("success");

	response.setError(null);
	response.setResult(userResponse);
	return new ResponseEntity<Response>(response, HttpStatus.OK);
}
}
