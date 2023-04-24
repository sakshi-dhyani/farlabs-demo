package com.farelabs.service;

import java.util.ArrayList;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.farelabs.dto.Response;
import com.farelabs.entity.Notification;
import com.farelabs.entity.User;


@Service
public class PushNotificationSender {
	
	
	public void sendPushNotification( Notification notification) {

		try {
			String androidFcmKey = "AAAAGgelL8A:APA91bHqOnDf2A4SSuMPvXAP5JVwraPSbvuOZVY4s6RrHYS1JRmlzvGSCj__2Xljmho9ja8ZZTQTkaAJX6yJRRhyzpQqO7_FBMi-Tvfit_64j5jqR22BXJal4-k32bgB6ZXhS2OEkQr1AAAAGgelL8A:APA91bHqOnDf2A4SSuMPvXAP5JVwraPSbvuOZVY4s6RrHYS1JRmlzvGSCj__2Xljmho9ja8ZZTQTkaAJX6yJRRhyzpQqO7_FBMi-Tvfit_64j5jqR22BXJal4-k32bgB6ZXhS2OEkQr1";
			String androidFcmUrl = "https://fcm.googleapis.com/fcm/send";

			RestTemplate restTemplate = new RestTemplate();

			HttpEntity<String> prepareDataForSend = prepareDataForSend( notification);

			ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
			interceptors.add(
					new com.farelabs.Authentication.HeaderRequestInterceptor("Authorization", "key=" + androidFcmKey));
			interceptors.add(new com.farelabs.Authentication.HeaderRequestInterceptor("Content-Type", "application/json"));
			restTemplate.setInterceptors(interceptors);

			String firebaseResponse = restTemplate.postForObject(androidFcmUrl, prepareDataForSend, String.class);
			System.out.println(firebaseResponse);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private HttpEntity<String> prepareDataForSend(Notification notifications) {

		JSONObject body = new JSONObject();
//		body.put("to", deviceToken);
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", notifications.getTitle());
		notification.put("body", notifications.getMessage());

		JSONObject data = new JSONObject();
		data.put("Key-1", "JSA Data 1");
		data.put("Key-2", "JSA Data 2");

		body.put("notification", notification);
		body.put("data", data);

		return new HttpEntity<>(body.toString());
	}

	//User details
	
	
	public Response sendNotification(Notification notification) {

		Response response = new Response();

		
		User receiver = notification.getUserReciever();

		if (receiver == null) {
			response.setCode(401);
			response.setMessage("Send receiver id");
			return response;
		}

		

		

		new Thread() {
			public void run() {
				new PushNotificationSender().sendPushNotification(notification);
			}
		}.start();

		response.setCode(200);
		response.setMessage("Notification sent!");
		return response;
	}

}
