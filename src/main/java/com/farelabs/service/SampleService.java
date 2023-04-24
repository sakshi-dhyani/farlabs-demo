package com.farelabs.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.farelabs.dto.Response;
import com.farelabs.dto.RoleResponse;
import com.farelabs.dto.SampleResponse;
import com.farelabs.dto.SampleStatusResponse;
import com.farelabs.entity.Admin;
import com.farelabs.entity.Notification;
import com.farelabs.entity.Role;
import com.farelabs.entity.SampleAllocation;
import com.farelabs.entity.SampleCheckpoints;
import com.farelabs.entity.SampleCollection;
import com.farelabs.entity.SampleDetails;
import com.farelabs.entity.SampleStatus;
import com.farelabs.entity.User;
import com.farelabs.repository.NotificationRepository;
import com.farelabs.repository.RoleRepository;
import com.farelabs.repository.SampleAllocationRepository;
import com.farelabs.repository.SampleCheckpointRepository;
import com.farelabs.repository.SampleCollectionRepository;
import com.farelabs.repository.SampleDetailsRepository;
import com.farelabs.repository.SampleStatusRepository;
import com.farelabs.repository.UserRepository;

@Service
public class SampleService {
	@Autowired private UserRepository userRepository;
	@Autowired SampleDetailsRepository sampleDetailsRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private SampleCheckpointRepository sampleCheckpointRepository;
	@Autowired private SampleAllocationRepository sampleAllocationRepository;
	@Autowired private SampleStatusRepository sampleStatusRepository;
	@Autowired private SampleCollectionRepository sampleCollectionRepository;
	@Autowired private NotificationRepository notificationRepository;

	Response response = new Response();
	public ResponseEntity<Response> addSampleDetails(String name, String companyName,
			String companyMobile, String companyUserName, String pickUpAddress,
			String deliveryAddress, String pickupDate, String deliveryDate, String entryNumber, String serialNumber,
			String quantity) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
		System.out.println(email);
		User user = userRepository.findByEmail(email);
		if (user == null) {
			response.setMessage("invalid token");
			response.setError(HttpStatus.UNAUTHORIZED.name());
			response.setCode(401);
			response.setResult(null);
			return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);
		}
		Role role = roleRepository.findByName(user.getRole().getName());
		if(!role.getName().equalsIgnoreCase("BD")) {
			
			response.setCode(403);
			response.setError(HttpStatus.FORBIDDEN.name());
			response.setMessage("please sign in with bd credentials");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.FORBIDDEN);
			
		}
		else {
		long pdate = Integer.parseInt(pickupDate);
		long ddate = Integer.parseInt(deliveryDate);
		if(name==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter your name ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		
		if(companyName==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter companyName ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		
		if(companyMobile==null || companyMobile.isEmpty()) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter companyNumber ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		if(companyUserName==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter companyUserName ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		if(deliveryAddress==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter deliverUpAddress ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}

		if(pickUpAddress==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter pickUpAddress ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		if(pickupDate==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter pickupDate ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		if(deliveryDate==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter deliveryDate ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
//		if(quantity==null) {
//			response.setCode(404);
//			response.setError(HttpStatus.NOT_FOUND.name());
//			response.setMessage("please enter quantity ");
//			response.setResult(null);
//			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
//		}
		if(entryNumber==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter entryNumber ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		if(serialNumber==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter serialNumber ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		int sNumbers = Integer.parseInt(serialNumber);

		SampleDetails serial = sampleDetailsRepository.findBySerialNumber(sNumbers);
		if(serial!=null) {
			response.setCode(409);
			response.setError(HttpStatus.CONFLICT.name());
			response.setMessage("serial number allready registered ");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
		
		}
			
		
		Date date = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter1.format(date);
		
		int entryNumbers = Integer.parseInt(entryNumber);
		int sampleQuantity = Integer.parseInt(quantity);
		SampleDetails add = new SampleDetails();
		add.setName(name);
		add.setMobileNumber(user.getMobileNumber());
		add.setCompanyName(companyName);
		add.setCompanyMobile(companyMobile);
		add.setCompanyUserName(companyUserName);
		add.setCreationTime(System.currentTimeMillis());
		add.setPickUpAddress(pickUpAddress);
		add.setDeliveryAddress(deliveryAddress);
		add.setQuantity(sampleQuantity);
		add.setPickupDate(pdate);
		add.setDeliveryDate(ddate);
		add.setEntryNumber(entryNumbers);
		add.setSerialNumber(sNumbers);
		add.setDate(strDate);
		add.setCreationTime(System.currentTimeMillis());
		add.setUserId(user.getId());
		sampleDetailsRepository.save(add);
		
		
		SampleStatus status = new SampleStatus();
		status.setBdStatus((byte)1);
		status.setSampleId(add.getId());
		status.setUserId(user.getId());
		sampleStatusRepository.save(status);
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(user);
		notification.setTitle("sample added successfull");
		notification.setMessage("sample added successfull");
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

}
	public ResponseEntity<Response> allSamples() {
		List<SampleDetails> samples = sampleDetailsRepository.findAll();
		if(samples==null||samples.isEmpty()) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no data found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		
		List<SampleResponse>sampleRes = new ArrayList<>();
		for(SampleDetails s : samples) {
			
			Date date = new Date();
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			String strDate = formatter1.format(date);
//			long timestamp = s.getDeliveryDate();
//			Calendar cal = Calendar.getInstance();
//			cal.setTimeInMillis(timestamp);
//			int deadlineDate = cal.get(Calendar.DATE);
			
			
			SampleResponse sampleResponse= new SampleResponse();
			sampleResponse.setSampleId(s.getId());
			sampleResponse.setDeliverAddress(s.getDeliveryAddress());
			sampleResponse.setPickupAddress(s.getPickUpAddress());
			sampleResponse.setSeialNumber(s.getSerialNumber());
			sampleResponse.setCompanyPersonName(s.getCompanyUserName());
			sampleResponse.setCompanyMobile(s.getMobileNumber());
			sampleResponse.setBdName(s.getName());
			sampleResponse.setQuantity(s.getQuantity());
			sampleResponse.setDeadline(s.getDeliveryDate());
			
			sampleRes.add(sampleResponse);
			
		}
		

		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(sampleRes);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	public ResponseEntity<Response> allIncharges(int inchargeID) {

		Role role = roleRepository.findById(inchargeID);
		if(role==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("role not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		
		List<RoleResponse>roleResponses = new ArrayList<>();
		
		List<User> user = userRepository.findByRoleId(inchargeID);
		if(user.isEmpty()) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("user not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		
		for(User u:user) {
			
			RoleResponse res = new RoleResponse();
			res.setId(u.getId());
			res.setName(u.getFirstName()+ " "+ u.getLastName());
			res.setRole(u.getRole().getName());
			roleResponses.add(res);
		}
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(roleResponses);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
		
	}
	public ResponseEntity<Response> allRoles() {
		
		List<Role> roles = roleRepository.findAll();
		if(roles==null || roles.isEmpty()) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("data not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		response.setCode(200);
		response.setError(null);
		response.setMessage("sucess");
		response.setResult(roles);;
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	public ResponseEntity<Response> executives(int executiveID) {

		Role role = roleRepository.findById(executiveID);
		if(role==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("role not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
//		if(!role.getName().equalsIgnoreCase("sample Executive"));
//		{
//			response.setCode(404);
//			response.setError(HttpStatus.NOT_FOUND.name());
//			response.setMessage("role not found");
//			response.setResult(null);
//			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
//			
//		}
		
		
		List<RoleResponse>roleResponses = new ArrayList<>();
		
		List<User> user = userRepository.findByRoleId(executiveID);
		if(user.isEmpty()) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("user not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		
		for(User u:user) {
			
			RoleResponse res = new RoleResponse();
			res.setId(u.getId());
			res.setName(u.getFirstName()+ " "+ u.getLastName());
			res.setRole(u.getRole().getName());
			roleResponses.add(res);
		}
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(roleResponses);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
		
	}
	public ResponseEntity<Response> allBd(int bdId) {
		Role role = roleRepository.findById(bdId);
		if(role==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("role not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
//		if(!role.getName().equalsIgnoreCase("sample Executive"));
//		{
//			response.setCode(404);
//			response.setError(HttpStatus.NOT_FOUND.name());
//			response.setMessage("role not found");
//			response.setResult(null);
//			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
//			
//		}
		
		
		List<RoleResponse>roleResponses = new ArrayList<>();
		
		List<User> user = userRepository.findByRoleId(bdId);
		if(user.isEmpty()) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("user not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		
		for(User u:user) {
			
			RoleResponse res = new RoleResponse();
			res.setId(u.getId());
			res.setName(u.getFirstName()+ " "+ u.getLastName());
			res.setRole(u.getRole().getName());
			roleResponses.add(res);
		}
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(roleResponses);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
		
	}
	public ResponseEntity<Response> ssfAllotment(int sampleId) {
		
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
		Role role = roleRepository.findByName(getUser.getRole().getName());
		if(!role.getName().equalsIgnoreCase("sample executive")) {
			
			response.setCode(403);
			response.setError(HttpStatus.FORBIDDEN.name());
			response.setMessage("please sign in with sample executive credentials");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.FORBIDDEN);
			
		}
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);  

		
		SampleDetails findSample = sampleDetailsRepository.findById(sampleId);
		if(findSample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no sample found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		SampleCheckpoints status = sampleCheckpointRepository.findBySampleIdAndUserId(sampleId,getUser.getId());
		if(status!=null) {
			status.setSsfAllotment((byte)1);
			status.setDate(strDate);
			sampleCheckpointRepository.save(status);
			
		}
		else {
		
		SampleCheckpoints mark = new SampleCheckpoints();
		mark.setSampleId(sampleId);
		mark.setUserId(getUser.getId());
		mark.setSsfAllotment((byte)1);
		mark.setDate(strDate);
		sampleCheckpointRepository.save(mark);
		}
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("ssfAllotment successfull");
		notification.setMessage("ssfAllotment successfull");
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
	public ResponseEntity<Response> equipmentPickup(int sampleId) {
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
		Role role = roleRepository.findByName(getUser.getRole().getName());
		if(!role.getName().equalsIgnoreCase("sample executive")) {
			
			response.setCode(403);
			response.setError(HttpStatus.FORBIDDEN.name());
			response.setMessage("please sign in with sample executive credentials");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.FORBIDDEN);
			
		}
		
		
		SampleDetails findSample = sampleDetailsRepository.findById(sampleId);
		if(findSample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no sample found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		SampleCheckpoints status = sampleCheckpointRepository.findBySampleIdAndUserId(sampleId,getUser.getId());
		if(status!=null) {
			status.setEquipmentPickup((byte)1);
			sampleCheckpointRepository.save(status);
			
		}
		else {
		
			SampleCheckpoints mark = new SampleCheckpoints();
		mark.setSampleId(sampleId);
		mark.setUserId(getUser.getId());
		mark.setEquipmentPickup((byte)1);
		sampleCheckpointRepository.save(mark);
		}
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("equipmentPickup successfull");
		notification.setMessage("equipmentPickup successfull");
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
	public ResponseEntity<Response> leftForDelivery(int sampleId) {
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
		Role role = roleRepository.findByName(getUser.getRole().getName());
		if(!role.getName().equalsIgnoreCase("sample executive")) {
			
			response.setCode(403);
			response.setError(HttpStatus.FORBIDDEN.name());
			response.setMessage("please sign in with sample executive credentials");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.FORBIDDEN);
			
		}
		
		
		SampleDetails findSample = sampleDetailsRepository.findById(sampleId);
		if(findSample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no sample found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		SampleCheckpoints status = sampleCheckpointRepository.findBySampleIdAndUserId(sampleId,getUser.getId());
		if(status!=null) {
			status.setLeftForDelivery((byte)1);
			sampleCheckpointRepository.save(status);
			
		}
		else {
		
			SampleCheckpoints mark = new SampleCheckpoints();
		mark.setSampleId(sampleId);
		mark.setUserId(getUser.getId());
		mark.setLeftForDelivery((byte)1);
		sampleCheckpointRepository.save(mark);
		}
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("leftForDelivery");
		notification.setMessage("leftForDelivery");
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
	public ResponseEntity<Response> samplePickup(int sampleId) {
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
		Role role = roleRepository.findByName(getUser.getRole().getName());
		if(!role.getName().equalsIgnoreCase("sample executive")) {
			
			response.setCode(403);
			response.setError(HttpStatus.FORBIDDEN.name());
			response.setMessage("please sign in with sample executive credentials");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.FORBIDDEN);
			
		}


		Format f = new SimpleDateFormat("HH.mm.ss");
		String t = f.format(new Date());
		System.out.println("Time = "+t);
		
		SampleDetails findSample = sampleDetailsRepository.findById(sampleId);
		if(findSample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no sample found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		SampleCheckpoints status = sampleCheckpointRepository.findBySampleIdAndUserId(sampleId,getUser.getId());
		if(status!=null) {
			status.setSamplePickup((byte)1);
			status.setPickupTime(t);
			sampleCheckpointRepository.save(status);
			
		}
		else {
		
			SampleCheckpoints mark = new SampleCheckpoints();
		mark.setSampleId(sampleId);
		mark.setUserId(getUser.getId());
		mark.setSamplePickup((byte)1);
		mark.setPickupTime(t);

		sampleCheckpointRepository.save(mark);
		}
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("samplePickup succesfull");
		notification.setMessage("samplePickup succesfull");
		notification.setCreationTimestamp(new Timestamp(new Date().getTime()));
		notification.setSeen((byte) 1);
		notification.setCreatedby("Application");
		notificationRepository.save(notification);
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(t);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	
	}
	public ResponseEntity<Response> ssf(int sampleId) {
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
		Role role = roleRepository.findByName(getUser.getRole().getName());
		if(!role.getName().equalsIgnoreCase("sample executive")) {
			
			response.setCode(403);
			response.setError(HttpStatus.FORBIDDEN.name());
			response.setMessage("please sign in with sample executive credentials");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.FORBIDDEN);
			
		}
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);  
		
		SampleDetails findSample = sampleDetailsRepository.findById(sampleId);
		if(findSample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no sample found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		SampleCheckpoints status = sampleCheckpointRepository.findBySampleIdAndUserId(sampleId,getUser.getId());
		if(status!=null) {
			status.setSsf((byte)1);
			status.setFinalstatus((byte)1);
			status.setEndDate(strDate);
			sampleCheckpointRepository.save(status);
			
		}
		else {
		
			SampleCheckpoints mark = new SampleCheckpoints();
		mark.setSampleId(sampleId);
		mark.setUserId(getUser.getId());
		mark.setSsf((byte)1);
		mark.setFinalstatus((byte)1);
		mark.setEndDate(strDate);
		sampleCheckpointRepository.save(mark);
		}
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("ssf upload succesfull");
		notification.setMessage("ssf upload succesfull");
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
	public ResponseEntity<Response> editSample(String companyName, String date,
			String companyUserName, String name, String pickUpAddress, String deliveryAddress, 
 String sampleId) {
		if(sampleId==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("sample not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		
		}
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
		Role role = roleRepository.findByName(getUser.getRole().getName());
		if(!role.getName().equalsIgnoreCase("bd")) {
			
			response.setCode(403);
			response.setError(HttpStatus.FORBIDDEN.name());
			response.setMessage("please sign in with bd credentials");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.FORBIDDEN);
			
		}
		
		int id = Integer.parseInt(sampleId);
		SampleDetails sample = sampleDetailsRepository.findById(id);
		if(sample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("sample not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
//		int q = Integer.parseInt(quantity);
//		long pDate = Long.parseLong(pickupDate);
//		long dDate = Long.parseLong(deliveryDate);
//		int eNumber = Integer.parseInt(entryNumber);
		sample.setCompanyName(companyName);
		sample.setCompanyUserName(companyUserName);
		sample.setDate(date);
		sample.setDeliveryAddress(deliveryAddress);
		sample.setPickUpAddress(pickUpAddress);
		sample.setModificationTime(System.currentTimeMillis());
		sample.setUserId(getUser.getId());
		sampleDetailsRepository.save(sample);
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("sample edit successfull");
		notification.setMessage("sample edit successfull");
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
	public ResponseEntity<Response> allocateSamples(String sampleId, String executiveId) {

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
		Role role = roleRepository.findByName(getUser.getRole().getName());
		if(!role.getName().equalsIgnoreCase("sample incharge")) {
			
			response.setCode(403);
			response.setError(HttpStatus.FORBIDDEN.name());
			response.setMessage("please sign in with sample incharge credentials");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.FORBIDDEN);
			
		}
		if(sampleId==null) {
			response.setCode(404);
			response.setMessage("enter sample id");
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		if(executiveId==null) {
			response.setCode(404);
			response.setMessage("enter sample executive id");
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		int sample = Integer.parseInt(sampleId);
		SampleDetails getSample = sampleDetailsRepository.findById(sample);
		if(getSample==null) {
			response.setCode(404);
			response.setMessage("no sample found");
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
	
		}
		int getExecutive = Integer.parseInt(executiveId);
		User user = userRepository.findByIdAndActive(getExecutive, (byte)1);
		if(user==null) {
			response.setCode(404);
			response.setMessage("user not found");
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
	
		}
			
		if(!user.getRole().getName().equalsIgnoreCase("sample executive")) {
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setMessage("user is not executive");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
		}
		SampleAllocation find = sampleAllocationRepository.findBySampleIdAndExecutiveId(sample,getExecutive);
		if(find!=null) {
			response.setCode(409);
			response.setError(HttpStatus.CONFLICT.name());
			response.setMessage("allready assigned");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.CONFLICT);
		}
		SampleAllocation add = new SampleAllocation();
		add.setCreationTime(System.currentTimeMillis());
		add.setExecutiveId(getExecutive);
		add.setInchargeId(getUser.getId());
		add.setSampleId(sample);
		sampleAllocationRepository.save(add);
		
//		SampleStatus status = new SampleStatus();
		SampleStatus findStatus = sampleStatusRepository.findBySampleId(sample);
		findStatus.setInchargeStatus((byte)1);
		findStatus.setSampleId(sample);
		findStatus.setUserId(getUser.getId());
		sampleStatusRepository.save(findStatus);
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(user);
		notification.setTitle("sample is allocated");
		notification.setMessage("sample is allocated");
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
	public ResponseEntity<Response> samplesAssigned(int executiveId) {

		User user = userRepository.findByIdAndActive(executiveId, (byte)1);
		if(user==null) {
			response.setCode(404);
			response.setMessage("user not found");
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
	
			
		}
		List<SampleAllocation> getSample = sampleAllocationRepository.findByExecutiveId(executiveId);
		if(getSample.isEmpty()) {
			response.setCode(404);
			response.setMessage("no data found");
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
	
			
		}
		
		List<SampleResponse> allocationResponse = new ArrayList<>();
		for(SampleAllocation a :getSample) {
			
			SampleDetails findSample = sampleDetailsRepository.findById(a.getSampleId());
			
			
			SampleResponse res = new SampleResponse();
			res.setSampleId(findSample.getId());
			res.setSeialNumber(findSample.getSerialNumber());
			res.setDeliverAddress(findSample.getDeliveryAddress());
			res.setPickupAddress(findSample.getPickUpAddress());
			res.setQuantity(findSample.getQuantity());
			res.setBdName(findSample.getName());
			res.setCompanyMobile(findSample.getCompanyMobile());
			res.setCompanyPersonName(findSample.getCompanyUserName());
			res.setDeadline(findSample.getDeliveryDate());
			allocationResponse.add(res);
		}

		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(allocationResponse);
		return new ResponseEntity<Response>(response,HttpStatus.OK);

	}
	public ResponseEntity<Response> getSampleStatus(int sampleId) {

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
		Role role = roleRepository.findByName(getUser.getRole().getName());
		if(!role.getName().equalsIgnoreCase("bd")) {
			
			response.setCode(403);
			response.setError(HttpStatus.FORBIDDEN.name());
			response.setMessage("please sign in with bd credentials");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.FORBIDDEN);
			
		}
		SampleDetails findSample = sampleDetailsRepository.findById(sampleId);
		if(findSample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("sample not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.OK);
		}
		SampleStatus getStatus = sampleStatusRepository.findBySampleId(sampleId);
		if(getStatus==null) {
			response.setCode(400);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("sample not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		
		SampleStatusResponse statusResponse = new SampleStatusResponse();
		statusResponse.setSampleId(sampleId);
		statusResponse.setBdStatus(getStatus.getBdStatus());
		statusResponse.setExecutiveStatus(getStatus.getExecutiveStatus());
		statusResponse.setInchargeStatus(getStatus.getInchargeStatus());
		statusResponse.setFinalStatus(getStatus.getFinalStatus());
		
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(statusResponse);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	public ResponseEntity<Response> storeSampleDetails(String sampleId, int quantity,
			String recieverName, String courierDetails, MultipartFile ssfPicture, MultipartFile samplePicture) {
		// TODO Auto-generated method stub
	
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
		Role role = roleRepository.findByName(getUser.getRole().getName());
		if(!role.getName().equalsIgnoreCase("sample executive")) {
			
			response.setCode(403);
			response.setError(HttpStatus.FORBIDDEN.name());
			response.setMessage("please sign in with sample executive credentials");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.FORBIDDEN);
			
		}
		if(sampleId==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("enter sample id");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		int sample = Integer.parseInt(sampleId);
		SampleDetails findSample = sampleDetailsRepository.findById(sample);
		if(findSample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no samle found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		SampleCollection addDetails = new SampleCollection();
		addDetails.setExecutiveId(getUser.getId());
		addDetails.setSampleId(sample);
		addDetails.setCourierDetails(courierDetails);
		addDetails.setQuantity(quantity);
		addDetails.setRecieverName(recieverName);
		addDetails.setCreationTimestamp(System.currentTimeMillis());
		String filePath = null;
		try {
			filePath = createFile(samplePicture);
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
		
		String filePath2 = null;
		try {
			filePath2 = uploadSsf(ssfPicture);
			System.out.println(filePath2);

			Response response = new Response();

			if (filePath2 != null) {
				response.setResult(filePath2);
				response.setMessage("Image Uploaded");
			} else
				response.setMessage("Image Not Uploaded");
		} catch (Exception e) {
			System.out.println(e);
		}

//		if (filePath2 != null)
//			getUser.setProfilePicture(filePath2);
//		userRepository.save(getUser);
		addDetails.setSsfPicture(filePath2);
		addDetails.setSamplePicture(filePath);
		sampleCollectionRepository.save(addDetails);
		
		
		SampleStatus findStatus = sampleStatusRepository.findBySampleId(sample);
		findStatus.setExecutiveStatus((byte)1);
		findStatus.setFinalStatus((byte)1);
		findStatus.setSampleId(sample);
		findStatus.setUserId(getUser.getId());
		sampleStatusRepository.save(findStatus);
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("details added successfully");
		notification.setMessage("details added successfully");
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

		File file = new File("C:\\Farelabs\\Sample\\images\\");
		if (!file.exists())
			file.mkdir();

		String fileName = multipartFile.getOriginalFilename().split("\\.")[0] + "."
				+ multipartFile.getOriginalFilename().split("\\.")[1];

		try {
			multipartFile.transferTo(new File(file.getAbsolutePath() + "\\" + fileName));
		} catch (IllegalStateException | IOException e) {

			return e.getMessage();
		}
		return "http://18.218.231.50/downloadSampleImage/" + fileName;
	}
	
	private String uploadSsf(MultipartFile multipartFile) {

		File file = new File("C:\\Farelabs\\Sample\\SSf\\images\\");
		if (!file.exists())
			file.mkdir();

		String fileName = multipartFile.getOriginalFilename().split("\\.")[0] + "."
				+ multipartFile.getOriginalFilename().split("\\.")[1];

		try {
			multipartFile.transferTo(new File(file.getAbsolutePath() + "\\" + fileName));
		} catch (IllegalStateException | IOException e) {

			return e.getMessage();
		}
		return "http://18.218.231.50/downloadSsfImage//" + fileName;
	}
	public ResponseEntity<Response> deleteSample(int sampleId) {

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
		
		SampleDetails sample = sampleDetailsRepository.findById(sampleId);
		if(sample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("sample not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response , HttpStatus.NOT_FOUND);
		}
		
		sampleDetailsRepository.delete(sample);
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("sample delete successfull");
		notification.setMessage("sample delete successfull");
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
	public ResponseEntity<Response> editAllocations(String sampleId , String executiveId) {
		
		int s = Integer.parseInt(sampleId);
		SampleDetails sample = sampleDetailsRepository.findById(s);
		if(sample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("sample not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response , HttpStatus.NOT_FOUND);
		}
		SampleAllocation findSample = sampleAllocationRepository.findBySampleId(s);
		if(findSample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("sample not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response , HttpStatus.NOT_FOUND);
		
		}
		int user = Integer.parseInt(executiveId);
		User getUser = userRepository.findByIdAndActive(user, (byte)1);
		if(getUser==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("user not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response , HttpStatus.NOT_FOUND);
		
		}
		Role role = roleRepository.findByName(getUser.getRole().getName());
		if(!role.getName().equalsIgnoreCase("sample executive")) {
			
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setMessage("user not sample executive");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
			
		}
		
		
		findSample.setExecutiveId(user);
		sampleAllocationRepository.save(findSample);
		
		Notification notification = new Notification();
		notification.setActive((byte) 1);
		notification.setUserReciever(getUser);
		notification.setTitle("sample is allocated");
		notification.setMessage("sample is allocated");
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
	public ResponseEntity<Response> getCheckPoints(int sampleId) {
		
		SampleDetails sample = sampleDetailsRepository.findById(sampleId);
		if(sample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("sample not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response , HttpStatus.NOT_FOUND);
		}
		
		SampleCheckpoints checks = sampleCheckpointRepository.findBySampleId(sampleId);
		if(checks == null) {
			
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no data found");
			response.setResult(null);
			return new ResponseEntity<Response>(response , HttpStatus.NOT_FOUND);
			
		}
		
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(checks);
		return new ResponseEntity<Response>(response,HttpStatus.OK);

	}
	public ResponseEntity<Response> getSample(int sampleId) {
		SampleDetails sample = sampleDetailsRepository.findById(sampleId);
		if(sample==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("sample not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response , HttpStatus.NOT_FOUND);
		}
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(sample);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	public ResponseEntity<Response> myHistory(String executiveId, String date) {
		if(executiveId==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter executive id");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		
		int execId = Integer.parseInt(executiveId);
		User findUser = userRepository.findByIdAndActive(execId, (byte)1);
		if(findUser==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("user not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		Role getRole = roleRepository.findById(findUser.getRole().getId());
		if(!getRole.getName().equalsIgnoreCase("sample executive")) {
			
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setMessage("role not match");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
			
		}
		List<SampleCheckpoints> gethistory =  sampleCheckpointRepository.findByUserIdAndFinalstatus(execId,(byte)1);
		if(gethistory.isEmpty()) {

			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no data found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		HashSet<SampleResponse> result= new HashSet<>();
		
		for(SampleCheckpoints s :gethistory) {
			SampleDetails sample = sampleDetailsRepository.findById(s.getSampleId());
			
			SampleResponse res = new SampleResponse();
			res.setPickupAddress(sample.getPickUpAddress());
			res.setDeliverAddress(sample.getDeliveryAddress());
			res.setBdName(sample.getName());
			res.setCompanyPersonName(sample.getCompanyUserName());
			res.setCompanyMobile(sample.getCompanyMobile());
			res.setDeadline(sample.getDeliveryDate());
			res.setQuantity(sample.getQuantity());
			res.setSampleId(sample.getId());
			res.setSeialNumber(sample.getSerialNumber());
			result.add(res);
			
		}
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(result);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	
	}
	public ResponseEntity<Response> myHistoryDate(String executiveId, String date) {
		if(executiveId==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("please enter executive id");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		if(date==null || date.isEmpty()) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("enter date");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		
		int execId = Integer.parseInt(executiveId);
		User findUser = userRepository.findByIdAndActive(execId, (byte)1);
		if(findUser==null) {
			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("user not found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
		Role getRole = roleRepository.findById(findUser.getRole().getId());
		if(!getRole.getName().equalsIgnoreCase("sample executive")) {
			
			response.setCode(400);
			response.setError(HttpStatus.BAD_REQUEST.name());
			response.setMessage("role not match");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
			
		}
		List<SampleCheckpoints> gethistory =  sampleCheckpointRepository.findByUserIdAndFinalstatus(execId,(byte)1);
		if(gethistory.isEmpty()) {

			response.setCode(404);
			response.setError(HttpStatus.NOT_FOUND.name());
			response.setMessage("no data found");
			response.setResult(null);
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
			
		}
		HashSet<SampleResponse> result= new HashSet<>();
		
		for(SampleCheckpoints s :gethistory) {
			if(s.getEndDate().equals(date)) {
			SampleDetails sample = sampleDetailsRepository.findById(s.getSampleId());
			
			SampleResponse res = new SampleResponse();
			res.setPickupAddress(sample.getPickUpAddress());
			res.setDeliverAddress(sample.getDeliveryAddress());
			res.setBdName(sample.getName());
			res.setCompanyPersonName(sample.getCompanyUserName());
			res.setCompanyMobile(sample.getCompanyMobile());
			res.setDeadline(sample.getDeliveryDate());
			res.setQuantity(sample.getQuantity());
			res.setSampleId(sample.getId());
			res.setSeialNumber(sample.getSerialNumber());
			result.add(res);
			}
			
		}
		response.setCode(200);
		response.setError(null);
		response.setMessage("success");
		response.setResult(result);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	
	}
	
	
}
