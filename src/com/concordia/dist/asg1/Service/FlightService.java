/**
 * 
 */
package com.concordia.dist.asg1.Service;

import java.util.ArrayList;

import com.concordia.dist.asg1.DAL.FlightDAL;
import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.Models.Flight;
import com.concordia.dist.asg1.Models.Response;

/**
 * Service layer for Flights, Perform Necessary Function Before and After saving
 * records
 * 
 * @author SajjadAshrafCan
 *
 */
public class FlightService {

	private FlightDAL flightDAL;
	//int lastFlightID = 0;
	//private ArrayList<Flight> flightsData = null;

	/**
	 * FlightService Constructor
	 */
	public FlightService() {
		//flightDAL = new FlightDAL(flightsData, lastFlightID);
		flightDAL = new FlightDAL();
	}

	/**
	 * Create Flight
	 * 
	 * @param seatsInFirstClass
	 * @param seatsInBusinessClass
	 * @param seatsInEconomyClass
	 * @param flightDate
	 * @param flightTime
	 * @param _destinaition
	 * @param _source
	 * @return
	 */
	public Response createFlight(int seatsInFirstClass, int seatsInBusinessClass, int seatsInEconomyClass,
			String flightDate, String flightTime, String _destinaition, String _source) {
		Response response = new Response();
		int bookedFirstClassSeats = 0, bookedBusinessClassSeats = 0, bookedEconomyClassSeats = 0;
		Enums.FlightCities destinaition = Enums.getFlightCitiesFromString(_destinaition);
		Enums.FlightCities source = Enums.getFlightCitiesFromString(_source);

		Flight flightInfo = new Flight(bookedFirstClassSeats, bookedBusinessClassSeats, bookedEconomyClassSeats,
				seatsInFirstClass, seatsInBusinessClass, seatsInEconomyClass, flightDate, flightTime, destinaition,
				source);

		response = flightDAL.CreateFlight(flightInfo);
		//lastFlightID = response.returnID;

		return response;
	}

	/**
	 * Delete Flight
	 * 
	 * @param passengerService
	 * @param flightID
	 * @return
	 */
	public Response deleteFlight(PassengerService passengerService, int flightID) {
		Response response = flightDAL.deleteFlight(flightID);
		if (response.status) {
			String oldMsg = response.message;

			// remove entries of this flight from Passenger.
			response = passengerService.deleteAllBookingForFlight(flightID);
			response.message = oldMsg + "\r\n" + response.message;
		}
		return response;
	}

	/**
	 * Get Flight Details
	 * 
	 * @return
	 */
	public Response flightDetails() {
		return flightDAL.flightDetails();
	}

	/**
	 * Edit Flight Record
	 * 
	 * @param recordID
	 * @param fieldName
	 * @param newValue
	 * @return
	 */

	public Response editFlightRecord(int recordID, String fieldName, String newValue) {
		return flightDAL.editFlightRecord(recordID, fieldName, newValue);
	}

	/**
	 * is Flight available
	 * 
	 * @param destination
	 * @param date
	 * @param class1
	 * @return
	 */
	public Response isFlightAvailable(Enums.FlightCities destination, String date, Enums.Class class1) {
		return flightDAL.isFlightAvailable(destination, date, class1);
	}

	/**
	 * decrement Flight Seats
	 * 
	 * @param flightID
	 * @param class1
	 * @return
	 */

	public Response decrementFlightSeats(int flightID, Enums.Class class1) {
		return flightDAL.decrementFlightSeats(flightID, class1);
	}

	/**
	 * get Booked Flight Count
	 * 
	 * @return
	 */
	public Response getBookedFlightCount() {
		return flightDAL.getBookedFlightCount();
	}
	
	
	/**
	 * Use in test cases only
	 * @param flightID
	 * @return
	 */
	public Flight getFlight(int flightID){
		return  flightDAL.getFlightsData(flightID);
	}

}
