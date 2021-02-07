package com.urssystems.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "distribution_center_DOC")
public class DistributionCenterDOC {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "distribution_center_DOC_id")
	private int distributionCenterDOCId;

	private String distributionCenterDOCName;

	private int capacity;

	private String capacityStatus;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
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

	public DistributionCenter getDistributionCenter() {
		return distributionCenter;
	}

	public void setDistributionCenter(DistributionCenter distributionCenter) {
		this.distributionCenter = distributionCenter;
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
