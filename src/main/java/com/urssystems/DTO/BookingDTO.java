package com.urssystems.DTO;

public class BookingDTO {

	private int bookingId;
	private String bookingSlot;
	private String slot;

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getBookingSlot() {
		return bookingSlot;
	}

	public void setBookingSlot(String bookingSlot) {
		this.bookingSlot = bookingSlot;
	}

}
