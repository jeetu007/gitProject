package com.urssystems.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "distribution_center")
public class DistributionCenter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "distribution_center_id")
	private int distributionCenterId;

	private String distributionCenterName;

	private String address;

	private String locationTag;

	private String slots;
	
	private boolean distributionCenterStatus;
	
	private String assignedSecurity;
	
	private Date createdOn;

	private Date modifiedOn;

	@OneToMany(mappedBy = "distributionCenter", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<DistributionCenterDOC> distributionCenterDOCList;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private User user;
	
	public int getDistributionCenterId() {
		return distributionCenterId;
	}

	public void setDistributionCenterId(int distributionCenterId) {
		this.distributionCenterId = distributionCenterId;
	}

	public String getDistributionCenterName() {
		return distributionCenterName;
	}

	public void setDistributionCenterName(String distributionCenterName) {
		this.distributionCenterName = distributionCenterName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocationTag() {
		return locationTag;
	}

	public void setLocationTag(String locationTag) {
		this.locationTag = locationTag;
	}

	public String getSlots() {
		return slots;
	}

	public void setSlots(String slots) {
		this.slots = slots;
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

	public List<DistributionCenterDOC> getDistributionCenterDOCList() {
		return distributionCenterDOCList;
	}

	public void setDistributionCenterDOCList(List<DistributionCenterDOC> distributionCenterDOCList) {
		this.distributionCenterDOCList = distributionCenterDOCList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isDistributionCenterStatus() {
		return distributionCenterStatus;
	}

	public void setDistributionCenterStatus(boolean distributionCenterStatus) {
		this.distributionCenterStatus = distributionCenterStatus;
	}

	public String getAssignedSecurity() {
		return assignedSecurity;
	}

	public void setAssignedSecurity(String assignedSecurity) {
		this.assignedSecurity = assignedSecurity;
	}	
}
