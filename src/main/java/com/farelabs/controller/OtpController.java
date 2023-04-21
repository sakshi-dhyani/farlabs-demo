package com.farelabs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.farelabs.dto.Response;
import com.farelabs.entity.User;
import com.farelabs.repository.UserRepository;
import com.farelabs.service.OtpService;
import com.farelabs.service.UserService;


@RestController
public class OtpController {
	
	@Autowired
	private OtpService otpService;

	@Autowired
	private UserRepository userRepository;

	

	@GetMapping("/generateOtpForResetPassword/{email}")
	private ResponseEntity<Response> generateOtpForResetPassword(@PathVariable String email) {
		Response response = new Response();
		if (userRepository.findByEmail(email.trim()) == null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setResult(null);
			response.setMessage("user not found");
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		}
		return otpService.generateOTP(email);
	}

	@PostMapping("/verifyOtp")
	private ResponseEntity<Response> verifyOtp(@RequestBody User user) {
		Response response = new Response();
		if (otpService.verifyOTP(user)) {
			response.setCode(200);
			response.setError(null);
			response.setMessage("valid otp");
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		response.setCode(403);
		response.setError(HttpStatus.FORBIDDEN.name());
		response.setResult(null);
		response.setMessage("invalid otp");
		return new ResponseEntity<Response>(response, HttpStatus.FORBIDDEN);
	}

}
