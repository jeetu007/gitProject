package com.urssystems.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.urssystems.model.Appointment;

@Repository("appointmentRepository")
@Transactional
public interface AppointmentRepository extends PagingAndSortingRepository<Appointment, Integer> {

	public List<Appointment> findByDistributionCenterIdAndShipmentStatusNotContainingIgnoreCase(
			int distributionCenterId, String shipmentStatus);

	public List<Appointment> findByDistributionCenterIdAndShipmentStatusNotContainingIgnoreCase(
			int distributionCenterId, String shipmentStatus, Pageable page);

	public List<Appointment> findByVendorIdAndDistributionCenterId(int userId, int distributionCenterId);

	public List<Appointment> findByVendorIdAndDistributionCenterId(int userId, int distributionCenterId, Pageable page);

	public List<Appointment> findByZonalMPRIdAndDistributionCenterId(int userId, int distributionCenterId);

	public List<Appointment> findByZonalMPRIdAndDistributionCenterId(int userId, int distributionCenterId,
			Pageable page);

	public Appointment findByAppointmentNumber(String appointmentNumber);

	public List<Appointment> findByZonalMPRIdAndDistributionCenterIdAndVendorIdAndDeliveryDateAndAppointmentApprovalStatusAndAppointmentNumber(
			int zonalmprId, int distributionCenterId, int vendorId, Date deliveryDate, String appointmentApprovalStatus,
			String appointmentNumber);

	public Appointment findByVendorIdAndVehicleId(int vendorId, int vehicleId);

	public Appointment findByAppointmentNumberAndShipmentStatusNotContainingIgnoreCase(String appointmentNumber,
			String shipmentStatus);

}
