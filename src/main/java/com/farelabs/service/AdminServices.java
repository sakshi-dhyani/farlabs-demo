package com.farelabs.service;

import java.sql.Timestamp;

import java.util.Date;
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
import org.springframework.stereotype.Service;

import com.farelabs.Authentication.JwtTokenHelper;
import com.farelabs.dto.AdminSignInResponse;
import com.farelabs.dto.Response;
import com.farelabs.dto.SignInResponse;
import com.farelabs.entity.Admin;
import com.farelabs.entity.Notification;
import com.farelabs.entity.Role;
import com.farelabs.entity.User;
import com.farelabs.repository.AdminRepository;
import com.farelabs.repository.NotificationRepository;
import com.farelabs.repository.RoleRepository;
import com.farelabs.repository.UserRepository;



@Service
public class AdminServices {
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired NotificationRepository notificationRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AdminRepository adminRepository;
	@Autowired private UserRepository userRepository;

	Response response = new Response();
	SignInResponse signinResponse = new SignInResponse();
	public ResponseEntity<Response> registerAdmin(String firstName, String lastName, String email, String password,
			String mobileNumber, String role) {
		
		

		if(firstName==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("enter first name");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
			
		}
		

		if(email==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("enter email");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
			
		}
		if(password==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("enter password");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
			
		}
		if(mobileNumber==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("enter mobile number");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
			
		}
		if(role==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("enter password");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
			
		}
		
		Admin findAdmin = adminRepository.findByMobileNumber(mobileNumber);
		if(findAdmin!=null) {
			response.setCode(409);
			response.setError(HttpStatus.CONFLICT.name());
			response.setMessage("number allready registered");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
			
		}
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(email.trim());
		if (matcher.matches() != true) {
			
			response.setCode(400);
			response.setResult(null);
			response.setMessage("Invalid Email!");
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		Admin user = adminRepository.findByEmail(email);
		{
			if(user!=null) {
				response.setCode(409);
				response.setError(HttpStatus.CONFLICT.name());
				response.setMessage("email already registered");
				response.setResult(null);
			
				return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
			}
		}
		
		Admin admin =new Admin();
		admin.setActive((byte)1);
		admin.setFirstName(firstName);
		admin.setLastName(lastName);
		admin.setEmail(email);
		admin.setPassword(new BCryptPasswordEncoder().encode(password));
		admin.setMobileNumber(mobileNumber);
		admin.setCreatedBy("admin");
		admin.setCreationTimestamp(new Timestamp(new Date().getTime()));
		adminRepository.save(admin);
		
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
//		notification.setUserReciever(admin.getId());
		notification.setAdminReciever(admin);
		notification.setTitle("admin added successfully");
		notification.setMessage("admin added successfully");
		notification.setCreationTimestamp(new Timestamp(new Date().getTime()));
		notification.setSeen((byte) 1);
		notification.setCreatedby("Application");
		notificationRepository.save(notification);
		
		response.setCode(200);
		response.setError(null);
		response.setMessage("sucess");
		response.setResult(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	public ResponseEntity<SignInResponse> adminSignIn(Admin admin) {
		if (admin.getEmail() == null) {
			signinResponse.setMessage("please enter email");
			signinResponse.setCode(400);
			signinResponse.setError(HttpStatus.BAD_REQUEST.name());
			signinResponse.setToken(null);
			signinResponse.setResult(null);
			return new ResponseEntity<SignInResponse>(signinResponse, HttpStatus.BAD_REQUEST);
		}
		if (admin.getEmail() == "" || admin.getEmail().contains(" ") || !admin.getEmail().contains("@")) {
			signinResponse.setMessage("invalid email!");
			signinResponse.setCode(400);
			signinResponse.setError(HttpStatus.BAD_REQUEST.name());
			signinResponse.setResult(null);
			signinResponse.setToken(null);
			return new ResponseEntity<SignInResponse>(signinResponse, HttpStatus.BAD_REQUEST);
		}
		if (admin.getPassword() == null) {
			signinResponse.setMessage("please enter password!");
			signinResponse.setCode(400);
			signinResponse.setError(HttpStatus.BAD_REQUEST.name());
			signinResponse.setToken(null);
			signinResponse.setResult(null);
			return new ResponseEntity<SignInResponse>(signinResponse, HttpStatus.BAD_REQUEST);
		}
		if (admin.getPassword() == "" || admin.getPassword().contains(" ")) {
			signinResponse.setMessage("invalid password!");
			signinResponse.setCode(400);
			signinResponse.setError(HttpStatus.BAD_REQUEST.name());
			signinResponse.setToken(null);
			signinResponse.setResult(null);
			return new ResponseEntity<SignInResponse>(signinResponse, HttpStatus.BAD_REQUEST);
		}
		Admin superAdmin = adminRepository.findByEmail(admin.getEmail());
		if (superAdmin == null) {
			signinResponse.setMessage("admin not found!");
			signinResponse.setError(HttpStatus.NOT_FOUND.name());
			signinResponse.setResult(null);
			signinResponse.setCode(404);
			signinResponse.setToken(null);
			return new ResponseEntity<SignInResponse>(signinResponse, HttpStatus.NOT_FOUND);
		}
		try {
			this.authenticate(admin.getEmail().trim(), admin.getPassword());
		} catch (Exception e) {
			signinResponse.setMessage("please try with correct password!");
			signinResponse.setCode(401);
			signinResponse.setError(HttpStatus.UNAUTHORIZED.name());
			signinResponse.setResult(null);
			signinResponse.setToken(null);
			return new ResponseEntity<SignInResponse>(signinResponse, HttpStatus.UNAUTHORIZED);
		}
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(admin.getEmail().trim());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		superAdmin.setIsLoggedIn((byte) 1);
		superAdmin.setLastLoggedInTimestamp(new Timestamp(new Date().getTime()));
		AdminSignInResponse adminSignInResponse = new AdminSignInResponse();
		adminSignInResponse.setId(superAdmin.getId());
		adminSignInResponse.setFirstName(superAdmin.getFirstName());
		adminSignInResponse.setLastName(superAdmin.getLastName());
		adminSignInResponse.setEmail(superAdmin.getEmail());
		adminSignInResponse.setMobileNumber(superAdmin.getMobileNumber());
		adminRepository.save(superAdmin);
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
//		notification.setUserReciever(admin.getId());
		notification.setAdminReciever(superAdmin);
		notification.setTitle("admin signin successfully");
		notification.setMessage("admin signin successfully");
		notification.setCreationTimestamp(new Timestamp(new Date().getTime()));
		notification.setSeen((byte) 1);
		notification.setCreatedby("Application");
		notificationRepository.save(notification);
		
		signinResponse.setCode(200);
		signinResponse.setError(null);
		signinResponse.setToken(token);
		signinResponse.setResult(adminSignInResponse);
		return new ResponseEntity<SignInResponse>(signinResponse, HttpStatus.OK);
	}

	private void authenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
				password);

		this.authenticationManager.authenticate(authenticationToken);

	}

	public ResponseEntity<Response> signOut(int adminId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email1 = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
		System.out.println(email1);
		Admin getAdmin = adminRepository.findByEmail(email1);
		if (getAdmin == null) {
			response.setMessage("invalid token");
			response.setError(HttpStatus.UNAUTHORIZED.name());
			response.setCode(401);
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);
		}
		
		Admin admin = adminRepository.findByIdAndActive(adminId, (byte) 1);
		if (admin == null) {
			response.setMessage("admin not found!");
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		}
		admin.setIsLoggedIn((byte) 0);
		admin.setLastLoggedOutTimestamp(new Timestamp(new Date().getTime()));
		adminRepository.save(admin);

		Notification notification = new Notification();
		notification.setActive((byte) 1);
//		notification.setUserReciever(admin.getId());
		notification.setAdminReciever(getAdmin);
		notification.setTitle("signout successfully");
		notification.setMessage("signout successfully");
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
	public ResponseEntity<Response> registerUser(String password, String usernames, String mobileNumber, String email,
			String lastName, String firstName, String roleId) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email1 = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
		System.out.println(email1);
		Admin getAdmin = adminRepository.findByEmail(email1);
		if (getAdmin == null) {
			response.setMessage("invalid token");
			response.setError(HttpStatus.UNAUTHORIZED.name());
			response.setCode(401);
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);
		}
		
		if(roleId==null) {
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setMessage("enter role");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
			
		}
		
		int id = Integer.parseInt(roleId);
		
				
		
		Role findRole = roleRepository.findById(id);
		if(findRole==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no role found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		
		
		User findEmail = userRepository.findByEmail(email);
		if(findEmail!=null) {
			response.setCode(409);
			response.setError(HttpStatus.CONFLICT.name());
			response.setMessage("email allready registered");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
			
		}
		
		
		
		if(firstName ==null || firstName.isEmpty()) {
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setMessage("enter first name");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
		}
		if(lastName ==null || lastName.isEmpty()) {
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setMessage("enter last name");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
		}
		if(email ==null || email.isEmpty()) {    
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setMessage("enter email ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
		}
		if(password ==null || password.isEmpty()) {
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setMessage("enter first name");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
		}
		if (!email.contains("@")) {
			response.setMessage("Invalid email address");
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		if (mobileNumber == null) {
			response.setMessage("please enter mobileNumber");
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		if (mobileNumber == "" || mobileNumber.contains(" ")) {
			response.setMessage("invalid mobileNumber");
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setResult(null);
			
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		User user2 = userRepository.findByMobileNumber(mobileNumber);
		if (user2 != null) {
			response.setMessage("already registered with this mobbileNumber");
			response.setCode(409);
			response.setError(HttpStatus.CONFLICT.name());
			response.setResult(null);

			return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
		}
		User user3 = userRepository.findByEmail(email);
		if (user3 != null) {
			response.setMessage("already registered with this email");
			response.setCode(409);
			response.setError(HttpStatus.CONFLICT.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
		}
		
		
		//email check
		
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(email.trim());
		if (matcher.matches() != true) {
			
			response.setCode(400);
			response.setResult(null);
			response.setMessage("Invalid Email!");
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		
		User user =new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setCreationTime(System.currentTimeMillis());
		user.setMobileNumber(mobileNumber);
		user.setRole(findRole);
		user.setUsernames(usernames);
		user.setActive((byte)1);
		userRepository.save(user); 
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
//		notification.setUserReciever(admin.getId());
		notification.setAdminReciever(getAdmin);
		notification.setTitle("user regstered successfully");
		notification.setMessage("user registered successfully");
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
	public ResponseEntity<Response> addRole(String role) {
		if(role==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("enter role");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		Role findRole = roleRepository.findByName(role);
		if(findRole!=null) {
			response.setMessage("already registered with this name");
			response.setCode(409);
			response.setError(HttpStatus.CONFLICT.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
		}
		Role add = new Role();
		add.setName(role);
		roleRepository.save(add);
		
		
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(null);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	}
	
	


