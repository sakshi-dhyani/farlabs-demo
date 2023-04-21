package com.farelabs.dto;

import lombok.Data;

@Data
public class SampleResponse {
	
	private int sampleId;
	private String pickupAddress;
	private String deliverAddress;
	private String companyMobile;
	private String companyPersonName;
	private int seialNumber;
	private String bdName;
	//show members of bd 
	private long deadline;
	private int quantity;
	
	
	public int getSampleId() {
		return sampleId;
	}
	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
	}
	public String getPickupAddress() {
		return pickupAddress;
	}
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
	public String getDeliverAddress() {
		return deliverAddress;
	}
	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}
	public String getCompanyMobile() {
		return companyMobile;
	}
	public void setCompanyMobile(String companyMobile) {
		this.companyMobile = companyMobile;
	}
	public String getCompanyPersonName() {
		return companyPersonName;
	}
	public void setCompanyPersonName(String companyPersonName) {
		this.companyPersonName = companyPersonName;
	}
	public int getSeialNumber() {
		return seialNumber;
	}
	public void setSeialNumber(int seialNumber) {
		this.seialNumber = seialNumber;
	}
	public String getBdName() {
		return bdName;
	}
	public void setBdName(String bdName) {
		this.bdName = bdName;
	}
	public long getDeadline() {
		return deadline;
	}
	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	

	

}
