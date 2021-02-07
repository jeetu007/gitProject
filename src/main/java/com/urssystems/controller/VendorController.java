package com.urssystems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.urssystems.DTO.AppointmentDTO;
import com.urssystems.DTO.AppointmentDetailDTO;
import com.urssystems.DTO.BookingDTO;
import com.urssystems.DTO.DistributionCenterDTO;
import com.urssystems.DTO.OrderSupplierDTO;
import com.urssystems.service.AppointmentService;
import com.urssystems.service.BookingService;
import com.urssystems.service.DistributionCenterService;
import com.urssystems.service.OrderSupplierService;
import com.urssystems.service.UserService;

@RestController
public class VendorController {

	@Autowired
	UserService userService;

	@Autowired
	OrderSupplierService orderSupplierService;

	@Autowired
	AppointmentService appointmentService;

	@Autowired
	DistributionCenterService distributionCenterService;

	@Autowired
	BookingService bookingService;

	@GetMapping(path = "/vendor/distributioncenter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DistributionCenterDTO>> getAllDistributionCenter() {
		List<DistributionCenterDTO> distributionCenterDTOList = distributionCenterService.getDistributionCenterList();
		if (distributionCenterDTOList == null) {
			return new ResponseEntity<List<DistributionCenterDTO>>(distributionCenterDTOList, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<DistributionCenterDTO>>(distributionCenterDTOList, HttpStatus.OK);

	}// getAllDistributionCenter()

	@GetMapping(path = "/vendor/orders/search/{userId}/{distributionCenterId}/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderSupplierDTO>> getOrderList(@PathVariable("userId") int userId,
			@PathVariable("distributionCenterId") int distributionCenterId, @PathVariable("pageNo") int pageNo,
			@PathVariable("pageSize") int pageSize) {
		List<OrderSupplierDTO> orderSupplierDTOList = orderSupplierService.getAllOrders(userId, distributionCenterId,
				pageNo, pageSize);
		if (orderSupplierDTOList == null) {
			return new ResponseEntity<List<OrderSupplierDTO>>(orderSupplierDTOList, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<OrderSupplierDTO>>(orderSupplierDTOList, HttpStatus.OK);
	}// getOrderList(-,-)

	@GetMapping(path = "/vendor/appointment/{userId}/{distributionCenterId}/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentDetailDTO> getAllAppointment(@PathVariable("userId") int userId,
			@PathVariable("distributionCenterId") int distributionCenterId, @PathVariable("pageNo") int pageNo,
			@PathVariable("pageSize") int pageSize) {
		AppointmentDetailDTO appointmentDTOList = appointmentService.getAllAppointment(userId, distributionCenterId,
				"vendor", pageNo, pageSize);
		if (appointmentDTOList == null) {
			return new ResponseEntity<AppointmentDetailDTO>(appointmentDTOList, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AppointmentDetailDTO>(appointmentDTOList, HttpStatus.OK);
	}// getAllAppointment(-)

	@PostMapping(path = "/vendor/appointment/book", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> bookAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		boolean result = appointmentService.bookAppointment(appointmentDTO);
		if (result) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.NOT_IMPLEMENTED);
	}// bookAppointment(-)

	@GetMapping(path = {"/vendor/booking/{bookingSlot}","/zonalmpr/booking/{bookingSlot}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookingDTO>> getBookingSlot(@PathVariable("bookingSlot") String bookingSlot) {
		List<BookingDTO> booking = bookingService.getBooking(bookingSlot);
		if (booking == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<BookingDTO>>(booking, HttpStatus.OK);
	}//getBookingSlot(-)

}// class