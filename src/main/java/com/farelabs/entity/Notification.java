package com.farelabs.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.Data;

@Data
@Entity
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String message;
	private byte active;
	private byte seen;
	@ManyToOne(cascade = CascadeType.DETACH,fetch=FetchType.EAGER)
	private User userReciever;
	
	@ManyToOne(cascade = CascadeType.DETACH,fetch=FetchType.EAGER)
	private Admin adminReciever;
	
	private Timestamp creationTimestamp;
	private Timestamp modifiTimestampTimestamp;
	private String createdby;
	private String modifiedBy;
	private String token;
	
	
	
	public Admin getAdminReciever() {
		return adminReciever;
	}
	public void setAdminReciever(Admin adminReciever) {
		this.adminReciever = adminReciever;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public byte getActive() {
		return active;
	}
	public void setActive(byte active) {
		this.active = active;
	}
	public byte getSeen() {
		return seen;
	}
	public void setSeen(byte seen) {
		this.seen = seen;
	}
	public User getUserReciever() {
		return userReciever;
	}
	public void setUserReciever(User userReciever) {
		this.userReciever = userReciever;
	}
	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(Timestamp creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	public Timestamp getModifiTimestampTimestamp() {
		return modifiTimestampTimestamp;
	}
	public void setModifiTimestampTimestamp(Timestamp modifiTimestampTimestamp) {
		this.modifiTimestampTimestamp = modifiTimestampTimestamp;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public Notification(int id, String title, String message, byte active, byte seen, User userReciever,
			Timestamp creationTimestamp, Timestamp modifiTimestampTimestamp, String createdby, String modifiedBy,
			String token) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
		this.active = active;
		this.seen = seen;
		this.userReciever = userReciever;
		this.creationTimestamp = creationTimestamp;
		this.modifiTimestampTimestamp = modifiTimestampTimestamp;
		this.createdby = createdby;
		this.modifiedBy = modifiedBy;
		this.token = token;
	}
	/**
	 * 
	 */
	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Notification [id=" + id + ", title=" + title + ", message=" + message + ", active=" + active + ", seen="
				+ seen + ", userReciever=" + userReciever + ", creationTimestamp=" + creationTimestamp
				+ ", modifiTimestampTimestamp=" + modifiTimestampTimestamp + ", createdby=" + createdby
				+ ", modifiedBy=" + modifiedBy + ", token=" + token + "]";
	}
	
	

}
