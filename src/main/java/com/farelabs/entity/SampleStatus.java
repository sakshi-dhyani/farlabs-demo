package com.farelabs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SampleStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int sampleId;
	private int userId;
	private byte bdStatus;
    private byte inchargeStatus;
    private byte executiveStatus;
    private byte finalStatus;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSampleId() {
		return sampleId;
	}
	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public byte getBdStatus() {
		return bdStatus;
	}
	public void setBdStatus(byte bdStatus) {
		this.bdStatus = bdStatus;
	}
	public byte getInchargeStatus() {
		return inchargeStatus;
	}
	public void setInchargeStatus(byte inchargeStatus) {
		this.inchargeStatus = inchargeStatus;
	}
	public byte getExecutiveStatus() {
		return executiveStatus;
	}
	public void setExecutiveStatus(byte executiveStatus) {
		this.executiveStatus = executiveStatus;
	}
	public byte getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(byte finalStatus) {
		this.finalStatus = finalStatus;
	}
    
    
    
	

}
