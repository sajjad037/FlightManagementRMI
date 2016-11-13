package com.concordia.dist.asg1.Models;

import java.io.Serializable;

/**
 * Passenger Model
 * @author SajjadAshrafCan
 *
 */
public class Passenger implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bookingId;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private Enums.FlightCities destination;
	private String date;
	private Enums.Class _class;
	private int flightId;


	/**
	 * @param bookingId
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param phone
	 * @param destination
	 * @param date
	 * @param _class
	 */
	public Passenger(int flightId, String firstName, String lastName, String address, String phone,
			Enums.FlightCities destination, String date, Enums.Class _class) {
		super();
		// this.bookingId = bookingId;
		this.flightId = flightId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.destination = destination;
		this.date = date;
		this._class = _class;
	}

	/**
	 * @return the bookingId
	 */
	public int getBookingId() {
		return bookingId;
	}

	/**
	 * @param bookingId
	 *            the bookingId to set
	 */
	public synchronized void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public synchronized void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public synchronized void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public synchronized void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public synchronized void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the destination
	 */
	public Enums.FlightCities getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public synchronized void setDestination(Enums.FlightCities destination) {
		this.destination = destination;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public synchronized void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the class
	 */
	public Enums.Class getclass() {
		return _class;
	}

	/**
	 * @param class1
	 *            the class to set
	 */
	public synchronized void setClass(Enums.Class _class) {
		this._class = _class;
	}

	/**
	 * @return the flightId
	 */
	public int getFlightId() {
		return flightId;
	}

	/**
	 * @param flightId
	 *            the flightId to set
	 */
	public synchronized void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Passenger [bookingId=" + bookingId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + ", phone=" + phone + ", destination=" + destination + ", date=" + date
				+ ", _class=" + _class + "]";
	}

}
