package com.urssystems.DTO;

import java.sql.Date;

import com.urssystems.model.DistributionCenter;

public class DistributionCenterDOCDTO {

	private int distributionCenterDOCId;

	private String distributionCenterDOCName;

	private int capacity;

	private String capacityStatus;

	private DistributionCenter distributionCenter;

	private Date createdOn;

	private Date modifiedOn;

	public int getDistributionCenterDOCId() {
		return distributionCenterDOCId;
	}

	public void setDistributionCenterDOCId(int distributionCenterDOCId) {
		this.distributionCenterDOCId = distributionCenterDOCId;
	}

	public String getDistributionCenterDOCName() {
		return distributionCenterDOCName;
	}

	public void setDistributionCenterDOCName(String distributionCenterDOCName) {
		this.distributionCenterDOCName = distributionCenterDOCName;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getCapacityStatus() {
		return capacityStatus;
	}

	public void setCapacityStatus(String capacityStatus) {
		this.capacityStatus = capacityStatus;
	}

	public DistributionCenter getDistributionCenter() {
		return distributionCenter;
	}

	public void setDistributionCenter(DistributionCenter distributionCenter) {
		this.distributionCenter = distributionCenter;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
}
