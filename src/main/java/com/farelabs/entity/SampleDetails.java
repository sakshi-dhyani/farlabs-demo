package com.farelabs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class SampleDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String companyName;
//	private String companyAddress;
	private String companyMobile;
	private String date;
	private String companyUserName;
	private String name;
	private String mobileNumber;
	private String pickUpAddress;
	private String deliveryAddress;
	private String recipt;
	private int quantity;
	private long pickupDate;
	private long deliveryDate;
	private int entryNumber;
	private int serialNumber; 
	private long creationTime;
	private long modificationTime;
	private int userId;
	
	
	
	
	
	public String getCompanyMobile() {
		return companyMobile;
	}
	public void setCompanyMobile(String companyMobile) {
		this.companyMobile = companyMobile;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCompanyUserName() {
		return companyUserName;
	}
	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPickUpAddress() {
		return pickUpAddress;
	}
	public void setPickUpAddress(String pickUpAddress) {
		this.pickUpAddress = pickUpAddress;
	}
	
	public String getRecipt() {
		return recipt;
	}
	public void setRecipt(String recipt) {
		this.recipt = recipt;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(long pickupDate) {
		this.pickupDate = pickupDate;
	}
	public long getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(long deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public int getEntryNumber() {
		return entryNumber;
	}
	public void setEntryNumber(int entryNumber) {
		this.entryNumber = entryNumber;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	public long getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(long modificationTime) {
		this.modificationTime = modificationTime;
	}
	
	
	
	
	
	

}
