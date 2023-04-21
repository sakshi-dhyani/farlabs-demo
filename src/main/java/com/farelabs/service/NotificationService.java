package com.farelabs.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.farelabs.dto.Response;
import com.farelabs.entity.Notification;
import com.farelabs.entity.User;
import com.farelabs.repository.NotificationRepository;
import com.farelabs.repository.UserRepository;



@Service
public class NotificationService {
	@Autowired PushNotificationSender notificationSender;
	@Autowired UserRepository userRepository;
	
	@Autowired private NotificationRepository notificationRepository;
	
	public Response sendNotification(Notification notification) {
		  Response response=notificationSender.sendNotification(notification);
		  User user = userRepository.findByIdAndActive(notification.getUserReciever().getId(), (byte) 1);
		//	user2.setDeviceToken(notification.getToken());
			userRepository.save(user);

			if (response.getCode() != 200)

				return response;

			notification.setActive((byte) 1);
		//	notification.setTime(new Timestamp(System.currentTimeMillis()).getTime());
			notification.setCreationTimestamp(new Timestamp(new Date().getTime()));
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			notification.setCreatedby(((org.springframework.security.core.userdetails.User) principal).getUsername());
		//	notification.setImage(
			//		"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbJ6kVvj94gG8xUW1d4owXIU7keKx-cjIklw&usqp=CAU");

			notificationRepository.save(notification);

			response.setCode(200);
			response.setMessage("Notification sent successfully!");
			return response;
	  }


}
