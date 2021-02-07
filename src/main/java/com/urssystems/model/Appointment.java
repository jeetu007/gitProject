package com.urssystems.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.urssystems.DTO.OrderSupplierDTO;

@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appointment_id")
	private int appointmentId;

	private int zonalMPRId;

	private int vendorId;

	private int distributionCenterId;

	private String orderList;
	
	@Transient
	private List<OrderSupplierDTO> orderDetailDTOList;
	
	private Date deliveryDate;
	
	private Date unloadingTime;
	
	private int vehicleId;
	
	private String appointmentApprovalStatus;
	
	@Column(unique = true)
	private String appointmentNumber;
	
	private Date appointmentDate;
	
	private String slotSelected;
		
	private String shipmentStatus;

	@Transient
	private int totalNoOfAppointmentRecords;
	
	private String remarkMessage;

	private Date createdOn;
	
	private Date modifiedOn;
	
	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public int getZonalMPRId() {
		return zonalMPRId;
	}

	public void setZonalMPRId(int zonalMPRId) {
		this.zonalMPRId = zonalMPRId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public int getDistributionCenterId() {
		return distributionCenterId;
	}

	public void setDistributionCenterId(int distributionCenterId) {
		this.distributionCenterId = distributionCenterId;
	}

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getUnloadingTime() {
		return unloadingTime;
	}

	public void setUnloadingTime(Date unloadingTime) {
		this.unloadingTime = unloadingTime;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getAppointmentApprovalStatus() {
		return appointmentApprovalStatus;
	}

	public void setAppointmentApprovalStatus(String appointmentApprovalStatus) {
		this.appointmentApprovalStatus = appointmentApprovalStatus;
	}

	public String getAppointmentNumber() {
		return appointmentNumber;
	}

	public void setAppointmentNumber(String appointmentNumber) {
		this.appointmentNumber = appointmentNumber;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public String getRemarkMessage() {
		return remarkMessage;
	}

	public void setRemarkMessage(String remarkMessage) {
		this.remarkMessage = remarkMessage;
	}

	public String getSlotSelected() {
		return slotSelected;
	}

	public void setSlotSelected(String slotSelected) {
		this.slotSelected = slotSelected;
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
