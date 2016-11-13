package com.concordia.dist.asg1.Models;

import java.io.Serializable;

import com.concordia.dist.asg1.Models.Enums.FlightCities;
/**
 * Flight Model.
 * @author SajjadAshrafCan
 *
 */
public class Flight implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bookedFirstClassSeats = 0;
	private int bookedBusinessClassSeats = 0;
	private int bookedEconomyClassSeats = 0;

	private int seatsInFirstClass = 10;
	private int seatsInBusinessClass = 10;
	private int seatsInEconomyClass = 10;

	private int flightID;
	private String flightDate;
	private String flightTime;
	private Enums.FlightCities destinaition;
	private Enums.FlightCities source;

	/**
	 * @param bookedFirstClassSeats
	 * @param bookedBusinessClassSeats
	 * @param bookedEconomyClassSeats
	 * @param seatsInFirstClass
	 * @param seatsInBusinessClass
	 * @param seatsInEconomyClass
	 * @param flightDate
	 * @param flightTime
	 * @param destinaition
	 * @param source
	 */
	public Flight(int bookedFirstClassSeats, int bookedBusinessClassSeats, int bookedEconomyClassSeats,
			int seatsInFirstClass, int seatsInBusinessClass, int seatsInEconomyClass, String flightDate,
			String flightTime, FlightCities destinaition, FlightCities source) {
		super();
		this.bookedFirstClassSeats = bookedFirstClassSeats;
		this.bookedBusinessClassSeats = bookedBusinessClassSeats;
		this.bookedEconomyClassSeats = bookedEconomyClassSeats;
		this.seatsInFirstClass = seatsInFirstClass;
		this.seatsInBusinessClass = seatsInBusinessClass;
		this.seatsInEconomyClass = seatsInEconomyClass;
		this.flightDate = flightDate;
		this.flightTime = flightTime;
		this.destinaition = destinaition;
		this.source = source;
	}

	public int getTotalBookSeats() {
		return getBookedFirstClassSeats() + getBookedBusinessClassSeats() + getBookedEconomyClassSeats();
	}

	public int getTotalSeats() {
		return getSeatsInFirstClass() + getSeatsInBusinessClass() + getSeatsInEconomyClass();
	}

	public int getAvailableFirstClassSeats() {
		return getSeatsInFirstClass() - getBookedFirstClassSeats();
	}

	public int getAvailableBusinessClassSeats() {
		return getSeatsInBusinessClass() - getBookedBusinessClassSeats();
	}

	public int getAvailableEconomyClassSeats() {
		return getSeatsInEconomyClass() - getBookedEconomyClassSeats();
	}

	public int getAvailableSeats() {
		return getAvailableFirstClassSeats() + getAvailableBusinessClassSeats() + getAvailableEconomyClassSeats();
	}

	/**
	 * @return the bookedFirstClassSeats
	 */
	public int getBookedFirstClassSeats() {
		return bookedFirstClassSeats;
	}

	/**
	 * @param bookedFirstClassSeats
	 *            the bookedFirstClassSeats to set
	 */
	public synchronized void setBookedFirstClassSeats(int bookedFirstClassSeats) {
		this.bookedFirstClassSeats = bookedFirstClassSeats;
	}

	/**
	 * @return the bookedBusinessClassSeats
	 */
	public int getBookedBusinessClassSeats() {
		return bookedBusinessClassSeats;
	}

	/**
	 * @param bookedBusinessClassSeats
	 *            the bookedBusinessClassSeats to set
	 */
	public synchronized void setBookedBusinessClassSeats(int bookedBusinessClassSeats) {
		this.bookedBusinessClassSeats = bookedBusinessClassSeats;
	}

	/**
	 * @return the bookedEconomyClassSeats
	 */
	public int getBookedEconomyClassSeats() {
		return bookedEconomyClassSeats;
	}

	/**
	 * @param bookedEconomyClassSeats
	 *            the bookedEconomyClassSeats to set
	 */
	public synchronized void setBookedEconomyClassSeats(int bookedEconomyClassSeats) {
		this.bookedEconomyClassSeats = bookedEconomyClassSeats;
	}

	/**
	 * @return the seatsInFirstClass
	 */
	public int getSeatsInFirstClass() {
		return seatsInFirstClass;
	}

	/**
	 * @param seatsInFirstClass
	 *            the seatsInFirstClass to set
	 */
	public synchronized void setSeatsInFirstClass(int seatsInFirstClass) {
		this.seatsInFirstClass = seatsInFirstClass;
	}

	/**
	 * @return the seatsInBusinessClass
	 */
	public int getSeatsInBusinessClass() {
		return seatsInBusinessClass;
	}

	/**
	 * @param seatsInBusinessClass
	 *            the seatsInBusinessClass to set
	 */
	public synchronized void setSeatsInBusinessClass(int seatsInBusinessClass) {
		this.seatsInBusinessClass = seatsInBusinessClass;
	}

	/**
	 * @return the seatsInEconomyClass
	 */
	public int getSeatsInEconomyClass() {
		return seatsInEconomyClass;
	}

	/**
	 * @param seatsInEconomyClass
	 *            the seatsInEconomyClass to set
	 */
	public synchronized void setSeatsInEconomyClass(int seatsInEconomyClass) {
		this.seatsInEconomyClass = seatsInEconomyClass;
	}

	/**
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            the flightDate to set
	 */
	public synchronized void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the flightTime
	 */
	public String getFlightTime() {
		return flightTime;
	}

	/**
	 * @param flightTime
	 *            the flightTime to set
	 */
	public synchronized void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	/**
	 * @return the destinaition
	 */
	public Enums.FlightCities getDestinaition() {
		return destinaition;
	}

	/**
	 * @param destinaition
	 *            the destinaition to set
	 */
	public synchronized void setDestinaition(Enums.FlightCities destinaition) {
		this.destinaition = destinaition;
	}

	/**
	 * @return the source
	 */
	public Enums.FlightCities getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public synchronized void setSource(Enums.FlightCities source) {
		this.source = source;
	}

	/**
	 * @return the flightID
	 */
	public int getFlightID() {
		return flightID;
	}

	/**
	 * @param flightID
	 *            the flightID to set
	 */
	public synchronized void setFlightID(int flightID) {
		this.flightID = flightID;
	}

	public String getFlightDateTime() {
		return String.format("%s %s", getFlightDate(), getFlightTime());
	}

}
