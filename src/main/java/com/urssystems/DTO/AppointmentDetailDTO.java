package com.urssystems.DTO;

import java.util.List;

public class AppointmentDetailDTO {

	private List<AppointmentDTO> appointmentDTOList;
	
	private UserDTO vendor;
	
	private UserDTO zonalMpr;

	public List<AppointmentDTO> getAppointmentDTOList() {
		return appointmentDTOList;
	}

	public void setAppointmentDTOList(List<AppointmentDTO> appointmentDTOList) {
		this.appointmentDTOList = appointmentDTOList;
	}

	public UserDTO getVendor() {
		return vendor;
	}

	public void setVendor(UserDTO vendor) {
		this.vendor = vendor;
	}

	public UserDTO getZonalMpr() {
		return zonalMpr;
	}

	public void setZonalMpr(UserDTO zonalMpr) {
		this.zonalMpr = zonalMpr;
	}
	
}
