package com.urssystems.DTO;

import java.sql.Date;
import java.util.List;

import com.urssystems.model.DistributionCenter;
import com.urssystems.model.UserProfile;
import com.urssystems.model.Vehicle;

public class UserDTO {

	private int id;

	private String username;

	private String password;

	private String firstname;

	private String lastname;

	private String email;

	private String status;

	private String mobile;
	
	private String tag;

	private Date createdOn;

	private Date modifiedOn;

	private List<UserProfile> userProfiles;

	private List<DistributionCenter> distributionCenterList;

	private List<Vehicle> vehicleList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public List<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(List<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	public List<DistributionCenter> getDistributionCenterList() {
		return distributionCenterList;
	}

	public void setDistributionCenterList(List<DistributionCenter> distributionCenterList) {
		this.distributionCenterList = distributionCenterList;
	}

	public List<Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
