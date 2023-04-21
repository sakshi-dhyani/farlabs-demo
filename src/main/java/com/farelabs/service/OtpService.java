package com.farelabs.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.farelabs.dto.Response;
import com.farelabs.entity.User;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


@Service
public class OtpService {
	
	@Autowired
	private Environment env;
	@Autowired
	private EmailService emailService;

	private static final Integer EXPIRE_MINS = 20;
	private LoadingCache<String, Integer> otpCache;

	public OtpService() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

	// This method is used to push the opt number against Key. Rewrite the OTP if it
	// exists
	// Using user id as key
	public int generateOTPNumber(String key) {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		otpCache.put(key, otp);
		return otp;
	}

	// This method is used to return the OPT number against Key->Key value is
	// username
	public int getOTP(String key) {
		try {
			return otpCache.get(key);
		} catch (Exception e) {
			return 0;
		}
	}

	// This method is used to clear the OTP catched already
	public void clearOTP(String key) {
		otpCache.invalidate(key);
	}

	public String validateOTP(String email, int otpnum) {

		final String SUCCESS = "SUCCESS";
		final String FAIL = "FAIL";
		// Validate the OTP
		if (otpnum >= 0) {

			int serverOtp = getOTP(email);
			if (serverOtp > 0) {
				if (otpnum == serverOtp) {
					
					clearOTP(email);
					return SUCCESS;
				} else {
					return FAIL;
				}
			} else {
				return FAIL;
			}
		} else {
			return FAIL;
		}
	}

	public boolean verifyOTP(User user) {

		if (validateOTP(user.getEmail().trim(), Integer.parseInt(user.getOtp().trim())).equals("FAIL")) {

//			log.error("Invalid OTP!");
			return false;
		}

//		log.info("Valid OTP!");
		return true;
	}

	public ResponseEntity<Response> generateOTP(String username) {

		/*
		 * try { new InternetAddress(email).validate(); } catch (AddressException e1) {
		 * e1.printStackTrace(); response.setCode(503);
		 * response.setMessage("Invalid email!"); return response; }
		 */
		Response response = new Response();
		
		try {
			Map<String, String> replacements = new HashMap<String, String>();
			replacements.put("user", username);
			replacements.put("otpnum", String.valueOf(generateOTPNumber(username)));

			emailService.sendMail(username, "OTP - farelabs", new EmailTemplate("SendOtp.html").getTemplate(replacements));
		} catch (Exception e) {

//			log.error("OTP Not Generated!", e);
			
			response.setMessage(env.getProperty("OTP.NOT.GENERATED"));
			response.setError(e.getMessage());
			return new ResponseEntity<Response>(response, HttpStatus.EXPECTATION_FAILED);
		}

//		log.info("OTP Generated!");
		response.setCode(200);
		response.setError(null);
		response.setMessage("OTP.GENERATED");
		response.setResult(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
