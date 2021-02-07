package com.urssystems.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "order_supplier")
public class OrderSupplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_supplier_id")
	private int orderSupplierId;

	private String orderNumber;

	private int distributionCenterId;

	private int vendorId;

	private Date notAfterDate;
	
	private String orderStatus;
	
	private Date orderApprovalDate;
	
	private long itemNumber;
	
	private int location;
	
	private int quantityOrdered;
	
	private int quantityCancelled;
	
	private String appointmentStatus;
	
	@Transient
	private int totalNoOfOrdersRecords;
	
	private Date createdOn;
	
	private Date modifiedOn;
	
	public int getOrderSupplierId() {
		return orderSupplierId;
	}

	public void setOrderSupplierId(int orderSupplierId) {
		this.orderSupplierId = orderSupplierId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getDistributionCenterId() {
		return distributionCenterId;
	}

	public void setDistributionCenterId(int distributionCenterId) {
		this.distributionCenterId = distributionCenterId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public Date getNotAfterDate() {
		return notAfterDate;
	}

	public void setNotAfterDate(Date notAfterDate) {
		this.notAfterDate = notAfterDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderApprovalDate() {
		return orderApprovalDate;
	}

	public void setOrderApprovalDate(Date orderApprovalDate) {
		this.orderApprovalDate = orderApprovalDate;
	}

	public long getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(long itemNumber) {
		this.itemNumber = itemNumber;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public int getQuantityCancelled() {
		return quantityCancelled;
	}

	public void setQuantityCancelled(int quantityCancelled) {
		this.quantityCancelled = quantityCancelled;
	}

	public String getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
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
