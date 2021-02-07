package com.urssystems.service;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.urssystems.DTO.AppointmentDTO;
import com.urssystems.DTO.AppointmentDetailDTO;
import com.urssystems.DTO.BookingDTO;
import com.urssystems.DTO.DistributionCenterDTO;
import com.urssystems.DTO.OrderSupplierDTO;
import com.urssystems.DTO.UserDTO;
import com.urssystems.DTO.VehicleDTO;
import com.urssystems.model.Appointment;
import com.urssystems.model.Booking;
import com.urssystems.model.DistributionCenterDOC;
import com.urssystems.model.Mail;
import com.urssystems.repository.AppointmentRepository;
import com.urssystems.repository.BookingRepository;

@Service("appointmentService")
public class AppointmentService {

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	OrderSupplierService orderSupplierService;

	@Autowired
	VehicleService vehicleService;

	@Autowired
	DistributionCenterService distributionCenterService;

	@Autowired
	UserService userService;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	Environment environment;

	@Autowired
	MailClient mailClient;

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	SimpMessagingTemplate messagingTemplate;

	public boolean bookAppointment(AppointmentDTO appointmentDTO) {

		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			appointmentDTO.setAppointmentApprovalStatus("PENDING");
			appointmentDTO.setShipmentStatus("WAITING");
			appointmentDTO.setAppointmentNumber("AP" + timeStamp);
			appointmentDTO.setCreatedOn(new Date(System.currentTimeMillis()));
			appointmentDTO.setModifiedOn(new Date(System.currentTimeMillis()));

			// setting unloading time
			Calendar cal = Calendar.getInstance();
			cal.setTime(appointmentDTO.getDeliveryDate());
			cal.add(Calendar.HOUR, -Integer.parseInt(environment.getRequiredProperty("unloading.time")));
			Date unloadingTime = new Date();
			unloadingTime.setTime(cal.getTimeInMillis());

			appointmentDTO.setUnloadingTime(unloadingTime);
			boolean isValid = checkConditions(appointmentDTO);
			System.out.println("IsValid => " + isValid);
			if (isValid) {
				Appointment appointment = appointmentRepository
						.save(modelMapper.map(appointmentDTO, Appointment.class));
				saveBookingDetails(appointmentDTO);
				if (appointment == null) {
					return false;
				} else {
					saveBookingDetails(appointmentDTO);
					UserDTO vendor = userService.findUserById(appointment.getVendorId());
					UserDTO zonalMPR = userService.findUserById(appointment.getZonalMPRId());
					sendEmailToVendorAndZonalMPR(vendor, zonalMPR, appointment);
					return true;
				}
			} else {
				System.out.println("Invalid Data Provided...");
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}// bookAppointment(-)

	@SuppressWarnings("deprecation")
	private boolean checkConditions(AppointmentDTO appointmentDTO) {
		Appointment existingAppointment = appointmentRepository.findByVendorIdAndVehicleId(appointmentDTO.getVendorId(),
				appointmentDTO.getVehicleId());
		boolean isValid = true;
		// Same vendor with same vehicle is not allowed to book appointment
		// twice for the same day.
		if (existingAppointment != null) {
			String existingTimeStamp = new SimpleDateFormat("yyyy-MM-dd").format(existingAppointment.getModifiedOn());
			String todayTimeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			if (existingTimeStamp.equalsIgnoreCase(todayTimeStamp)) {
				isValid = false;
				String errorMessage = "Same Vendor with same vehicle can't book again on the same date";
				messagingTemplate.convertAndSend("/notify", errorMessage);
			} else {
				isValid = true;
			}
		}
		// Special vendor or Regular
		UserDTO vendor = userService.findUserById(appointmentDTO.getVendorId());
		if (vendor != null) {
			if (vendor.getTag().equalsIgnoreCase("Special")) {
				Date deliveryDate = appointmentDTO.getDeliveryDate();
				Calendar cal = Calendar.getInstance();
				cal.setTime(deliveryDate);
				cal.add(Calendar.DAY_OF_MONTH,
						-Integer.parseInt(environment.getRequiredProperty("advance.booking.value")));
				Date dateAllowedToBook = new Date(cal.getTimeInMillis());

				if (deliveryDate.getTime() >= new Date().getTime()
						&& new Date().getDate() >= dateAllowedToBook.getDate()) {
					System.out.println("Valid for Special Vendor");
				} else {
					isValid = false;
					String errorMessage = "Delivery date needs to be within 2 days from today";
					messagingTemplate.convertAndSend("/notify", errorMessage);
				}
			}
			if (vendor.getTag().equalsIgnoreCase("Regular")) {
				System.out.println("Regular Vendor....");
				Date deliveryDate = appointmentDTO.getDeliveryDate();
				Calendar cal = Calendar.getInstance();
				cal.setTime(deliveryDate);
				cal.add(Calendar.DAY_OF_MONTH,
						-Integer.parseInt(environment.getRequiredProperty("advance.booking.value")));
				Date dateAllowedToBook = new Date(cal.getTimeInMillis());

				if (deliveryDate.getDate() > new Date().getDate()
						&& new Date().getDate() >= dateAllowedToBook.getDate()) {
					System.out.println("Valid for Bookiing Regular Vendor");
				} else {
					isValid = false;
					String errorMessage = "Delivery date needs to be within 2 days from today";
					messagingTemplate.convertAndSend("/notify", errorMessage);
				}
			}
		}
		// order not after date
		String[] orders = appointmentDTO.getOrderList().split(",");
		List<OrderSupplierDTO> orderSupplierList = orderSupplierService.getOrders(orders);
		for (OrderSupplierDTO orderSupplierDTO : orderSupplierList) {
			if (orderSupplierDTO != null && orderSupplierDTO.getNotAfterDate().getDate() <= new Date().getDate()) {
				isValid = false;
				String errorMessage = "Order not after date is expired for " + orderSupplierDTO.getOrderNumber();
				messagingTemplate.convertAndSend("/notify", errorMessage);
			}
		}
		return isValid;
	}

	private BookingDTO saveBookingDetails(AppointmentDTO appointmentDTO) throws ParseException {

		long bookingDateTimeMillis = appointmentDTO.getDeliveryDate().getTime();
		// String baseStartTime =
		// environment.getRequiredProperty("base.start.date");
		// SimpleDateFormat sdf = new
		// SimpleDateFormat(environment.getRequiredProperty("base.start.date.format"));
		// Date baseStartDate = sdf.parse(baseStartTime);
		// long baseStartDateMillis = baseStartDate.getTime();
		// long bookingDate = (bookingDateTimeMillis - baseStartDateMillis);
		DistributionCenterDTO distributionCenter = distributionCenterService
				.getDistributionCenter(appointmentDTO.getDistributionCenterId());
		for (DistributionCenterDOC doc : distributionCenter.getDistributionCenterDOCList()) {
			System.out.println("DOC- Selected => " + doc.getDistributionCenterDOCId());
		}
		StringBuilder bookingSlot = new StringBuilder();
		bookingSlot.append(appointmentDTO.getDistributionCenterId());
		bookingSlot.append('_');
		bookingSlot.append(bookingDateTimeMillis);
		Booking booking = new Booking();
		booking.setBookingSlot(bookingSlot.toString());
		booking.setSlot(appointmentDTO.getSlotSelected());
		Booking savedBooking = bookingRepository.save(booking);
		return modelMapper.map(savedBooking, BookingDTO.class);

	}// saveBookingDetails(-)

	public boolean updateAppointment(List<AppointmentDTO> appointmentDTOList) throws Exception {
		try {

			for (int i = 0; i < appointmentDTOList.size(); i++) {

				AppointmentDTO appointmentDTO = appointmentDTOList.get(i);

				if (appointmentDTOList.get(i).getAppointmentApprovalStatus().equalsIgnoreCase("RESCHEDULED")
						|| appointmentDTOList.get(i).getAppointmentApprovalStatus().equalsIgnoreCase("APPROVED")) {
					vehicleService.updateVehicleStatus(appointmentDTOList.get(i).getVehicleId(), "BOOKED");
					orderSupplierService.updateOrderSupplierStatus(appointmentDTOList.get(i).getOrderList(),
							"IN-PROGRESS", "APPROVED");
					createDocument(appointmentDTOList);

				} else if (appointmentDTOList.get(i).getAppointmentApprovalStatus().equalsIgnoreCase("REJECTED")) {
					orderSupplierService.updateOrderSupplierStatus(appointmentDTOList.get(i).getOrderList(),
							"CANCELLED", "REJECTED");
					createDocument(appointmentDTOList);

				} else if (appointmentDTOList.get(i).getShipmentStatus().equalsIgnoreCase("COMPLETED")) {
					vehicleService.updateVehicleStatus(appointmentDTOList.get(i).getVehicleId(), "FREE");
					orderSupplierService.updateOrderSupplierStatus(appointmentDTOList.get(i).getOrderList(),
							"DELIVERED", "APPROVED");
				}

				appointmentDTO.setModifiedOn(new Date(System.currentTimeMillis()));
				appointmentRepository.save(modelMapper.map(appointmentDTO, Appointment.class));

			}
			// send email
			return true;
		} catch (Exception e) {
			System.out.println("exception->" + e.getMessage());
			return false;
		}
	}// updateAppointment(-)

	// --search appointment for zonalMPR start
	@SuppressWarnings({ "unused" })
	public AppointmentDetailDTO getAppointmentForZonalMPR(String appointmentNumber) {
		try {

			AppointmentDetailDTO appointmentDetailDTO = new AppointmentDetailDTO();

			List<AppointmentDTO> appointmentDTOList = new ArrayList<AppointmentDTO>();

			List<Appointment> appointmentList = new ArrayList<Appointment>();

			Appointment searchedAppointment = appointmentRepository.findByAppointmentNumber(appointmentNumber);

			if (searchedAppointment != null) {

				String orders = searchedAppointment.getOrderList();
				String[] ordersId = orders.split(",");

				List<OrderSupplierDTO> orderSupplierDTOList = orderSupplierService.getOrders(ordersId);

				AppointmentDTO appointmentDTO = modelMapper.map(searchedAppointment, AppointmentDTO.class);
				appointmentDTO.setOrderDetailDTOList(orderSupplierDTOList);
				appointmentDTO.setTotalNoOfAppointmentRecords(1);
				appointmentDTOList.add(appointmentDTO);

				UserDTO vendor = userService.findUserById(appointmentDTOList.get(0).getVendorId());

				UserDTO zonalMpr = userService.findUserById(appointmentDTOList.get(0).getZonalMPRId());

				appointmentDetailDTO.setAppointmentDTOList(appointmentDTOList);

				appointmentDetailDTO.setVendor(vendor);

				appointmentDetailDTO.setZonalMpr(zonalMpr);

				return appointmentDetailDTO;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}// getAllAppointment()
		// --search appointment for zonalMPR end

	@SuppressWarnings("deprecation")
	public AppointmentDetailDTO getAllAppointment(int userId, int distributionCenterId, String role, int pageNo,
			int pageSize) {
		try {

			AppointmentDetailDTO appointmentDetailDTO = new AppointmentDetailDTO();

			List<AppointmentDTO> appointmentDTOList = new ArrayList<AppointmentDTO>();

			List<Appointment> appointmentList = null;

			int appointmentSize = 0;

			if (role.equalsIgnoreCase("vendor")) {
				appointmentSize = getAppointmentSize(userId, distributionCenterId, "vendor");
				Pageable pageable = new PageRequest(pageNo - 1, pageSize, Direction.DESC, "deliveryDate");
				appointmentList = appointmentRepository.findByVendorIdAndDistributionCenterId(userId,
						distributionCenterId, pageable);
			} else if (role.equalsIgnoreCase("zonalmpr")) {
				appointmentSize = getAppointmentSize(userId, distributionCenterId, "zonalmpr");
				Pageable pageable = new PageRequest(pageNo - 1, pageSize, Direction.DESC, "deliveryDate");
				appointmentList = appointmentRepository.findByZonalMPRIdAndDistributionCenterId(userId,
						distributionCenterId, pageable);
			}

			if (appointmentList != null) {

				for (Appointment appointment : appointmentList) {
					String orders = appointment.getOrderList();
					String[] ordersId = orders.split(",");

					List<OrderSupplierDTO> orderSupplierDTOList = orderSupplierService.getOrders(ordersId);

					AppointmentDTO appointmentDTO = modelMapper.map(appointment, AppointmentDTO.class);
					appointmentDTO.setOrderDetailDTOList(orderSupplierDTOList);
					appointmentDTO.setTotalNoOfAppointmentRecords(appointmentSize);
					appointmentDTOList.add(appointmentDTO);
				} // for

				UserDTO vendor = userService.findUserById(appointmentDTOList.get(0).getVendorId());

				UserDTO zonalMpr = userService.findUserById(appointmentDTOList.get(0).getZonalMPRId());

				appointmentDetailDTO.setAppointmentDTOList(appointmentDTOList);

				appointmentDetailDTO.setVendor(vendor);

				appointmentDetailDTO.setZonalMpr(zonalMpr);

				return appointmentDetailDTO;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}// getAllAppointment()

	public int getAppointmentSize(int userId, int distributionCenterId, String role) {
		try {
			int size = 0;
			if (role.equalsIgnoreCase("vendor")) {
				List<Appointment> appointmentList = appointmentRepository.findByVendorIdAndDistributionCenterId(userId,
						distributionCenterId);
				size = appointmentList.size();
			} else if (role.equalsIgnoreCase("zonalmpr")) {
				List<Appointment> appointmentList = appointmentRepository
						.findByZonalMPRIdAndDistributionCenterId(userId, distributionCenterId);
				size = appointmentList.size();
			}

			return size;
		} catch (Exception e) {
			return 0;
		}
	}// appointmentSize(-)

	public void sendEmailToVendorAndZonalMPR(UserDTO vendor, UserDTO zonalMPR, Appointment appointment)
			throws Exception {
		if (vendor != null && vendor.getUserProfiles().get(0).getRole() != null) {
			String userRole = vendor.getUserProfiles().get(0).getRole();
			Mail mail = new Mail();
			mail.setMailFrom(environment.getRequiredProperty("spring.mail.username"));
			mail.setMailTo(vendor.getEmail());
			mail.setMailSubject("Appointment Scheduled");

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("username", vendor.getFirstname() + " " + vendor.getLastname());
			model.put("appointmentNumber", appointment.getAppointmentNumber().toString());
			model.put("appointmentDate", appointment.getDeliveryDate().toString());
			model.put("signature", "www.urssystems.com");
			mail.setModel(model);
			mailClient.sendEmail(mail, userRole);
		}
		if (zonalMPR != null && zonalMPR.getUserProfiles().get(0).getRole() != null) {
			String userRole = zonalMPR.getUserProfiles().get(0).getRole();
			Mail mail = new Mail();
			mail.setMailFrom(environment.getRequiredProperty("spring.mail.username"));
			mail.setMailTo(zonalMPR.getEmail());
			mail.setMailSubject("Appointment Scheduled");

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("zonalMPRName", zonalMPR.getFirstname() + " " + zonalMPR.getLastname());
			model.put("vendorName", vendor.getFirstname() + " " + vendor.getLastname());
			model.put("appointmentNumber", appointment.getAppointmentNumber().toString());
			model.put("appointmentDate", appointment.getDeliveryDate().toString());
			model.put("signature", "www.urssystems.com");
			mail.setModel(model);
			mailClient.sendEmail(mail, userRole);
		}

	}// sendEmailToGuest(-,-,-)

	public void createDocument(List<AppointmentDTO> appointmentDTOList) throws Exception {
		System.out.println("Sending Attatchment-----");
		for (AppointmentDTO appointmentDTO : appointmentDTOList) {

			UserDTO vendor = userService.findUserById(appointmentDTO.getVendorId());
			Mail mail = new Mail();
			mail.setMailFrom(environment.getRequiredProperty("spring.mail.username"));
			mail.setMailTo(vendor.getEmail());
			Map<String, Object> model = new HashMap<String, Object>();

			if (appointmentDTO.getAppointmentApprovalStatus().equalsIgnoreCase("APPROVED")) {
				mail.setMailSubject("Appointment Approved");

			} else if (appointmentDTO.getAppointmentApprovalStatus().equalsIgnoreCase("REJECTED")) {
				mail.setMailSubject("Appointment Rejected");

			} else if (appointmentDTO.getAppointmentApprovalStatus().equalsIgnoreCase("RESCHEDULED")) {
				mail.setMailSubject("Appointment Rescheduled");

			}
			model.put("username", vendor.getFirstname() + " " + vendor.getLastname());
			model.put("appointmentStatus", appointmentDTO.getAppointmentApprovalStatus());
			model.put("signature", "www.urssystems.com");
			mail.setModel(model);
			mailClient.sendEmailWithAttachment(mail, appointmentDTO);
		}

	}// createDocument(-)

	public Document generateDocumentForAttachment(Document document, AppointmentDTO appointmentDTO)
			throws DocumentException {
		Document editedDoc = document;
		editedDoc.open();
		Paragraph paragraph = new Paragraph();
		paragraph.add(new Chunk("Appointment Details"));
		editedDoc.add(paragraph);

		UserDTO vendor = userService.findUserById(appointmentDTO.getVendorId());
		VehicleDTO vehicle = vehicleService.getVehicleDetail(appointmentDTO.getVehicleId());
		int noOfOrders = appointmentDTO.getOrderList().split(",").length;

		PdfPTable table = new PdfPTable(6);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_MIDDLE);
		table.getDefaultCell().setBackgroundColor(Color.LIGHT_GRAY);

		table.addCell("Vendor Name");
		table.addCell("Appointment Number");
		table.addCell("Appointment Date");
		table.addCell("Vehicle Type");
		table.addCell("No. of orders");
		table.addCell("Appointment Status");

		table.addCell(vendor.getFirstname() + " " + vendor.getLastname());
		table.addCell(appointmentDTO.getAppointmentNumber());
		table.addCell(appointmentDTO.getUnloadingTime().toString());
		table.addCell(vehicle.getVehicleType());
		table.addCell(String.valueOf(noOfOrders));
		table.addCell(appointmentDTO.getAppointmentApprovalStatus());

		editedDoc.add(table);
		return editedDoc;

	}

	@SuppressWarnings("deprecation")
	public List<AppointmentDTO> getAppointmentForDC_DOC(int distributionCenterId, int pageNo, int pageSize) {
		try {
			Pageable pageable = new PageRequest(pageNo - 1, pageSize, Direction.DESC, "deliveryDate");
			List<Appointment> appointmentList = appointmentRepository
					.findByDistributionCenterIdAndShipmentStatusNotContainingIgnoreCase(distributionCenterId, "WAITING",
							pageable);

			List<AppointmentDTO> appointmentDTOList = new ArrayList<>();

			for (Appointment appointment : appointmentList) {
				String orders = appointment.getOrderList();
				String[] ordersId = orders.split(",");

				List<OrderSupplierDTO> orderSupplierDTOList = orderSupplierService.getOrders(ordersId);

				int appointmentSize = getAppointmentSizeOfDOC(distributionCenterId);

				AppointmentDTO appointmentDTO = modelMapper.map(appointment, AppointmentDTO.class);
				appointmentDTO.setOrderDetailDTOList(orderSupplierDTOList);
				appointmentDTO.setTotalNoOfAppointmentRecords(appointmentSize);

				appointmentDTOList.add(appointmentDTO);

			} // for

			return appointmentDTOList;

		} catch (Exception e) {
			return null;
		}
	}// getAppointmentForDC_DOC(-)

	public int getAppointmentSizeOfDOC(int distributionCenterId) {
		try {
			int size = 0;
			List<Appointment> appointmentList = appointmentRepository
					.findByDistributionCenterIdAndShipmentStatusNotContainingIgnoreCase(distributionCenterId,
							"WAITING");
			size = appointmentList.size();

			return size;
		} catch (Exception e) {
			return 0;
		}
	}// appointmentSize(-)

	public AppointmentDTO findAppointment(String appointmentNumber) {
		try {
			return modelMapper.map(appointmentRepository
					.findByAppointmentNumberAndShipmentStatusNotContainingIgnoreCase(appointmentNumber, "Completed"),
					AppointmentDTO.class);
		} catch (Exception e) {
			return null;
		}
	}// findAppointment(-)
}// class