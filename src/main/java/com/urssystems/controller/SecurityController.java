package com.urssystems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.urssystems.DTO.AppointmentDTO;
import com.urssystems.DTO.DistributionCenterDTO;
import com.urssystems.service.AppointmentService;
import com.urssystems.service.DistributionCenterService;

@RestController
public class SecurityController {

	@Autowired
	DistributionCenterService distributionCenterService;

	@Autowired
	AppointmentService appointmentService;

	@GetMapping(path = "/security/appointment/search/{appointmentNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentDTO> findAppointment(@PathVariable("appointmentNumber") String appointmentNumber) {
		AppointmentDTO appointmentDTO = appointmentService.findAppointment(appointmentNumber);
		if (appointmentDTO == null) {
			return new ResponseEntity<AppointmentDTO>(appointmentDTO, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AppointmentDTO>(appointmentDTO, HttpStatus.OK);

	}// getAllDistributionCenter()

	@GetMapping(path = "/security/distributioncenter/{securityId}/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DistributionCenterDTO> getDistributionCenter(@PathVariable("securityId") int securityId,
			@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
		DistributionCenterDTO distributionCenterDTO = distributionCenterService
				.getDistributionCenterForSecurity(securityId, pageNo, pageSize);
		if (distributionCenterDTO == null) {
			return new ResponseEntity<DistributionCenterDTO>(distributionCenterDTO, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DistributionCenterDTO>(distributionCenterDTO, HttpStatus.OK);

	}// getAllDistributionCenter()

	@PutMapping(path = "/security/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updateAppointment(@RequestBody List<AppointmentDTO> appointmentDTOList) throws Exception {
		boolean result = appointmentService.updateAppointment(appointmentDTOList);
		if (!result) {
			return new ResponseEntity<Boolean>(result, HttpStatus.NOT_IMPLEMENTED);
		}
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);

	}// getAllDistributionCenter()

}// class
