package com.farelabs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.farelabs.entity.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {
	

}
