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
import com.urssystems.DTO.AppointmentDetailDTO;
import com.urssystems.DTO.VehicleDTO;
import com.urssystems.service.AppointmentService;
import com.urssystems.service.VehicleService;

@RestController
public class ZonalMprController {

	@Autowired
	AppointmentService appointmentService;

	@Autowired
	VehicleService vehicleService;

	@PutMapping(path = "/zonalmpr/appointment/book", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> bookAppointment(@RequestBody List<AppointmentDTO> appointmentDTOList)
			throws Exception {
		boolean result = appointmentService.updateAppointment(appointmentDTOList);
		if (result) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}// bookAppointment(-)

	@GetMapping(path = "/zonalmpr/appointment/{userId}/{distributionCenterId}/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentDetailDTO> getAllAppointmentDetail(@PathVariable("userId") int userId,
			@PathVariable("distributionCenterId") int distributionCenterId, @PathVariable("pageNo") int pageNo,
			@PathVariable("pageSize") int pageSize) {
		AppointmentDetailDTO appointmentDTOList = appointmentService.getAllAppointment(userId, distributionCenterId,
				"zonalmpr", pageNo, pageSize);
		if (appointmentDTOList == null) {
			return new ResponseEntity<AppointmentDetailDTO>(appointmentDTOList, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AppointmentDetailDTO>(appointmentDTOList, HttpStatus.OK);
	}// getAllAppointment(-)

	@GetMapping(path = "/vehicle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VehicleDTO> getVehicle(@PathVariable("id") int id) {
		VehicleDTO vehicle = vehicleService.getVehicleDetail(id);
		if (vehicle == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<VehicleDTO>(vehicle, HttpStatus.OK);
	}

	@GetMapping(path = "/zonalmpr/appointment/search/{appointmentNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentDetailDTO> getAllAppointmentForZonalMPR(@PathVariable("appointmentNumber") String appointNumber) {
		AppointmentDetailDTO result = appointmentService.getAppointmentForZonalMPR(appointNumber);
		if (result == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AppointmentDetailDTO>(result, HttpStatus.OK);
	}
}// class
