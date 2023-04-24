package com.farelabs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SampleCollection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int sampleId;
	private int executiveId;
	private int quantity;
	private String recieverName;
	private String samplePicture;
	private String ssfPicture;
	private long creationTimestamp;
	private String courierDetails;
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
	public int getExecutiveId() {
		return executiveId;
	}
	public void setExecutiveId(int executiveId) {
		this.executiveId = executiveId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getRecieverName() {
		return recieverName;
	}
	public void setRecieverName(String recieverName) {
		this.recieverName = recieverName;
	}
	public String getSamplePicture() {
		return samplePicture;
	}
	public void setSamplePicture(String samplePicture) {
		this.samplePicture = samplePicture;
	}
	public String getSsfPicture() {
		return ssfPicture;
	}
	public void setSsfPicture(String ssfPicture) {
		this.ssfPicture = ssfPicture;
	}
	public long getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	public String getCourierDetails() {
		return courierDetails;
	}
	public void setCourierDetails(String courierDetails) {
		this.courierDetails = courierDetails;
	}
	
	

	
	

}
