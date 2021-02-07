package com.urssystems.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urssystems.DTO.BookingDTO;
import com.urssystems.model.Booking;
import com.urssystems.repository.BookingRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<BookingDTO> getBooking(String bookingSlotQuery) {
		List<Booking> allBooking = bookingRepository.findAll();
		List<BookingDTO> matchingDateBookingSlots = new ArrayList<>();
		List<BookingDTO> sortedBookingSlots = new ArrayList<>();
		Set<Integer> slotStartTimeSet = new HashSet<Integer>();
		allBooking.forEach(booking -> {
			String[] dateToken = booking.getBookingSlot().split("_");
			long longDateTime = Long.parseLong(dateToken[1]);
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date(longDateTime));
			String dateToMatch = dateToken[0] + "_" + timeStamp;
			if (bookingSlotQuery.equalsIgnoreCase(dateToMatch)) {
				matchingDateBookingSlots.add(modelMapper.map(booking, BookingDTO.class));
				String[] timings = booking.getSlot().split("-");
				//put the starting hour in Set to make it sorted
				slotStartTimeSet.add(Integer.parseInt(timings[0]));
			}
		});
		for (Integer startTime : slotStartTimeSet) {
			String startingHour = String.valueOf(startTime);
			for (BookingDTO booking : matchingDateBookingSlots) {
				String[] slotHours = booking.getSlot().split("-");
				if (startingHour.equalsIgnoreCase(slotHours[0])) {
					sortedBookingSlots.add(booking);
				}
			}
		}
		return sortedBookingSlots;
	}
}
