package com.farelabs.dto;

import lombok.Data;

@Data
public class SampleStatusResponse {
	
	private int sampleId;
	private byte bdStatus;
    private byte inchargeStatus;
    private byte executiveStatus;
    private byte finalStatus;
	public int getSampleId() {
		return sampleId;
	}
	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
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
