package com.urssystems.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urssystems.model.Booking;

@Repository
@Transactional
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	public List<Booking> findByBookingSlotContainingIgnoreCase(String bookingSlot);

}
