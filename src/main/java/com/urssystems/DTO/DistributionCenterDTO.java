package com.urssystems.DTO;

import java.sql.Date;
import java.util.List;

import com.urssystems.model.DistributionCenterDOC;
import com.urssystems.model.User;

public class DistributionCenterDTO {

	private int distributionCenterId;

	private String distributionCenterName;

	private String address;

	private String locationTag;

	private String assignedSecurity;
	
	private String slots;

	private Date createdOn;

	private Date modifiedOn;

	private List<DistributionCenterDOC> distributionCenterDOCList;

	private List<AppointmentDTO> appointmentDTOList;

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

	public String getAssignedSecurity() {
		return assignedSecurity;
	}

	public void setAssignedSecurity(String assignedSecurity) {
		this.assignedSecurity = assignedSecurity;
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

	public List<AppointmentDTO> getAppointmentDTOList() {
		return appointmentDTOList;
	}

	public void setAppointmentDTOList(List<AppointmentDTO> appointmentDTOList) {
		this.appointmentDTOList = appointmentDTOList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
