package com.concordia.dist.asg1.Service;

import com.concordia.dist.asg1.DAL.PassengerDal;
import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.Models.Passenger;
import com.concordia.dist.asg1.Models.Response;

/**
 * Service layer for Passengers, Perform Necessary Function Before and After
 * saving
 * 
 * @author SajjadAshrafCan
 *
 */
public class PassengerService {

	private PassengerDal passengerDal;

	/**
	 * Constructor of PassengerService
	 */
	public PassengerService() {
		passengerDal = new PassengerDal();
	}

	/**
	 * book Flight
	 * 
	 * @param flightService
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param phone
	 * @param _destination
	 * @param date
	 * @param class1
	 * @return
	 */
	public Response bookFlight(FlightService flightService, String firstName, String lastName, String address,
			String phone, String _destination, String date, String class1) {
		Response response = new Response();
		int flightId = -1;
		Enums.Class _class = Enums.getClassFromString(class1);
		Enums.FlightCities destination = Enums.getFlightCitiesFromString(_destination);
		// Check Flight is avaiable
		response = flightService.isFlightAvailable(destination, date, _class);
		if (response.status) {
			flightId = response.returnID;
			Passenger passengerInfo = new Passenger(flightId, firstName, lastName, address, phone, destination, date,
					_class);
			response = passengerDal.bookFlight(passengerInfo);

			if (response.status) {
				String oldMsg = response.message;
				// update Flights Seats
				response = flightService.decrementFlightSeats(flightId, _class);
				response.message = oldMsg + "\r\n" + response.message;
			}
		}
		return response;
	}

	/**
	 * get Booked Flight Count
	 * 
	 * @param recordType
	 * @return
	 */
	public int getBookedFlightCount(String recordType) {
		return passengerDal.getBookedFlightCount(recordType);
	}

	/**
	 * delete All Booking For Flight
	 * 
	 * @param flightID
	 * @return
	 */
	public Response deleteAllBookingForFlight(int flightID) {
		return passengerDal.deleteAllBookingForFlight(flightID);
	}

}
