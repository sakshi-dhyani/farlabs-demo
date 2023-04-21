package com.farelabs.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farelabs.entity.Notification;



@Service
public class PushNotificationService {
	
	@Autowired
	private FCMservice fcmService;
	private String token = "BLxr7BCaIK-KVcg758Nk_AduuR4G0GhJVuslVPZalPsbQ9dR_CkBK6Hsor9xeedEdvk_pYZbVq96TaP3W8QmqEg";

	public void sendPushNotificationWithData() {
		Notification pushNotificationRequest = new Notification();
		pushNotificationRequest.setMessage("Send push notifications from Spring Boot server");
		pushNotificationRequest.setTitle("test Push Notification");
		pushNotificationRequest.setToken(token);
		Map<String, String> appData = new HashMap<>();
		appData.put("name", "PushNotification");
		try {
			fcmService.sendMessage(appData, pushNotificationRequest);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
