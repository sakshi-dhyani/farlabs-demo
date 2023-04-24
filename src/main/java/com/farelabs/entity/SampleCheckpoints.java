package com.farelabs.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SampleCheckpoints {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private byte ssfAllotment;
	private byte equipmentPickup;
	private int userId;
    private int sampleId;
	private byte leftForDelivery;
    private byte samplePickup;
	private String pickupTime;
	private byte uploadForm;
	private byte finalstatus;
	private byte ssf;
	private String date;
	private String endDate;
	
	
	
	
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public byte getFinalstatus() {
		return finalstatus;
	}
	public void setFinalstatus(byte finalstatus) {
		this.finalstatus = finalstatus;
	}
	public byte getSsf() {
		return ssf;
	}
	public void setSsf(byte ssf) {
		this.ssf = ssf;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte getSsfAllotment() {
		return ssfAllotment;
	}
	public void setSsfAllotment(byte ssfAllotment) {
		this.ssfAllotment = ssfAllotment;
	}
	public byte getEquipmentPickup() {
		return equipmentPickup;
	}
	public void setEquipmentPickup(byte equipmentPickup) {
		this.equipmentPickup = equipmentPickup;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSampleId() {
		return sampleId;
	}
	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
	}
	public byte getLeftForDelivery() {
		return leftForDelivery;
	}
	public void setLeftForDelivery(byte leftForDelivery) {
		this.leftForDelivery = leftForDelivery;
	}
	public byte getSamplePickup() {
		return samplePickup;
	}
	public void setSamplePickup(byte samplePickup) {
		this.samplePickup = samplePickup;
	}
	
	public String getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}
	public byte getUploadForm() {
		return uploadForm;
	}
	public void setUploadForm(byte uploadForm) {
		this.uploadForm = uploadForm;
	}
	
	
	
	
	
	
	

}
