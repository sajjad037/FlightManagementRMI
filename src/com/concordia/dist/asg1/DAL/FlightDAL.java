package com.concordia.dist.asg1.DAL;

import java.util.concurrent.CopyOnWriteArrayList;

import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.Models.Flight;
import com.concordia.dist.asg1.Models.Response;

/**
 * Data access Layer Handle Flights data.
 * 
 * @author SajjadAshrafCan
 *
 */
public class FlightDAL {

	CopyOnWriteArrayList<Flight> flightsData = null;
	int lastFlightID = 0;

	/**
	 * Constructor FlightDAL
	 */
	public FlightDAL() {
		if (this.flightsData == null) {
			this.flightsData = new CopyOnWriteArrayList<Flight>();
		}
	}

	/**
	 * Create Flight
	 * 
	 * @param flightInfo
	 * @return
	 */
	public Response CreateFlight(Flight flightInfo) {
		Response response = null;
		flightInfo.setFlightID(++lastFlightID);
		synchronized (flightsData) {
			flightsData.add(flightInfo);
		}
		response = new Response();
		response.status = true;
		response.returnID = lastFlightID;
		response.message = "New Flight added successfully.";
		return response;
	}

	/**
	 * get Flight Index from Flight array
	 * 
	 * @param flightID
	 * @return
	 */
	private int getFlightIndex(int flightID) {
		int index = -1;
		if (flightsData != null && flightsData.size() > 0) {
			for (int i = 0; i < flightsData.size(); i++) {
				if (flightID == flightsData.get(i).getFlightID()) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	/**
	 * Delete Flight.
	 * 
	 * @param flightID
	 * @return
	 */
	public Response deleteFlight(int flightID) {
		Response response = new Response();
		if (flightsData != null && flightsData.size() > 0) {
			int index = getFlightIndex(flightID);
			if (index != -1) {
				synchronized (flightsData) {
					Flight flightInfo = flightsData.get(index);
					flightsData.remove(flightInfo);
				}
				response.status = true;
				// Please do not forget to remove entries of this flight from
				// Passenger.
				response.message = "Flight Deleted Successfuly";
			} else {
				response.status = false;
				response.message = "There is no flight with this flightID : " + flightID + " .";
			}

		} else {
			response.status = false;
			response.message = "There is no flight data.";
		}

		return response;
	}

	/**
	 * Get Booked Flight Count.
	 * 
	 * @return
	 */
	public Response getBookedFlightCount() {
		Response response = new Response();
		int count = 0;
		response.returnID = count;
		if (flightsData != null && flightsData.size() > 0) {
			for (int i = 0; i < flightsData.size(); i++) {
				Flight flightInfo = flightsData.get(i);

				// Case when a flight is Fully Booked all seat booked
				// if(flightInfo.getAvailableSeats() == 0){
				// ++count;
				// }

				// case When a Flight has atleast one seat book
				if (flightInfo.getTotalBookSeats() > 0) {
					++count;
				}
			}
		} else {
			response.status = false;
			response.message = "There is no flight data.";
		}
		if (count > 0) {
			response.status = true;
			response.returnID = count;
			response.message = "Total " + count + " Flights are booked";
		}

		return response;
	}

	/**
	 * Edit record of flight
	 * 
	 * @param recordID
	 * @param fieldName
	 * @param newValue
	 * @return
	 */

	public Response editFlightRecord(int recordID, String fieldName, String newValue) {
		Response response = new Response();
		if (flightsData != null && flightsData.size() > 0) {
			int flightIndex = getFlightIndex(recordID);
			if (flightIndex != -1) {
				Flight flightInfo = flightsData.get(flightIndex);

				Enums.FlightFileds field = Enums.getEnumFlightFiledsFromString(fieldName);
				switch (field) {
				case flightDate:
					flightInfo.setFlightDate(newValue);
					flightsData.set(flightIndex, flightInfo);
					response.status = true;
					break;
				case flightTime:
					flightInfo.setFlightTime(newValue);
					flightsData.set(flightIndex, flightInfo);
					response.status = true;
					break;
				case destinaition:
					Enums.FlightCities newDestination = Enums.getFlightCitiesFromString(newValue);
					if (newDestination != null) {
						flightInfo.setDestinaition(newDestination);
						flightsData.set(flightIndex, flightInfo);
						response.status = true;
					} else {
						response.status = false;
						response.message = "new Destination value is Incorrect";
					}
					break;
				case source:
					response.status = false;
					response.message = "Not Allowed change the source.";
					break;

				case seatsInFirstClass:
					flightInfo.setSeatsInFirstClass(Integer.parseInt(newValue));
					flightsData.set(flightIndex, flightInfo);
					response.status = true;
					break;

				case seatsInBusinessClass:
					flightInfo.setSeatsInBusinessClass(Integer.parseInt(newValue));
					flightsData.set(flightIndex, flightInfo);
					response.status = true;
					break;

				case seatsInEconomyClass:
					flightInfo.setSeatsInEconomyClass(Integer.parseInt(newValue));
					flightsData.set(flightIndex, flightInfo);
					response.status = true;
					break;

				default:
					response.status = false;
					response.message = "Field is not Defined";
					break;
				}

			} else {
				response.status = false;
				response.message = "RecordID:" + recordID + " has no Flight Data.";
			}
		} else {
			response.status = false;
			response.message = "There is no flight data.";
		}

		return response;
	}

	/**
	 * Check Flight is available for given parameters
	 * 
	 * @param destination
	 * @param date
	 * @param class1
	 * @return
	 */
	public Response isFlightAvailable(Enums.FlightCities destination, String date, Enums.Class class1) {
		Response response = new Response();
		response.status = false;
		response.message = "No flight is aviable.";
		boolean needRecheck = false;
		if (flightsData != null && flightsData.size() > 0) {
			for (int i = 0; i < flightsData.size(); i++) {
				needRecheck = false;
				Flight flightInfo = flightsData.get(i);
				if (flightInfo.getAvailableSeats() > 0) {
					switch (class1) {
					case First:
						if (flightInfo.getAvailableFirstClassSeats() > 0) {
							needRecheck = true;
						} else {
							response.status = false;
							response.message = "No Flight aviable in " + class1.toString() + " Class.";
						}
						break;
					case Business:
						if (flightInfo.getAvailableBusinessClassSeats() > 0) {
							needRecheck = true;
						} else {
							response.status = false;
							response.message = "No Flight aviable in " + class1.toString() + " Class.";
						}
						break;
					case Economy:
						if (flightInfo.getAvailableEconomyClassSeats() > 0) {
							needRecheck = true;
						} else {
							response.status = false;
							response.message = "No Flight aviable in " + class1.toString() + " Class.";
						}
						break;

					default:
						response.status = false;
						response.message = "This Flight Class is not defined.";
						break;
					}

					if (needRecheck) {
						if (flightInfo.getDestinaition().equals(destination)) {
							if (flightInfo.getFlightDate().equals(date)) {
								response.status = true;
								response.returnID = flightInfo.getFlightID();
								response.message = "Flight is Aviable.";
								break;
							} else {
								response.status = false;
								response.message = "Flight is not avaible with Class:" + class1.toString()
										+ ", Destination:" + flightInfo.getDestinaition().toString() + ", DateTime :"
										+ date;
							}
						} else {
							response.status = false;
							response.message = "Flight in " + class1.toString() + " Class is not avaible at  "
									+ flightInfo.getDestinaition().toString() + " Destintation.";
						}
					}
				} else {
					response.status = false;
					response.message = "The " + flightInfo.getFlightID() + " is Full for Class:" + class1.toString()
							+ ".";
				}

			}
		} else {
			response.status = false;
			response.message = "There is no flight data.";
		}

		return response;
	}

	/**
	 * decrement Flight Seats
	 * 
	 * @param flightID
	 * @param class1
	 * @return
	 */
	public Response decrementFlightSeats(int flightID, Enums.Class class1) {
		Response response = new Response();
		response.status = false;
		response.message = "No flight is aviable.";
		if (flightsData != null && flightsData.size() > 0) {
			int flightIndex = getFlightIndex(flightID);
			if (flightIndex != -1) {
				Flight flightInfo;
				flightInfo = flightsData.get(flightIndex);

				int count = 0;
				switch (class1) {
				case First:
					if (flightInfo.getAvailableFirstClassSeats() > 0) {
						count = flightInfo.getBookedFirstClassSeats();
						flightInfo.setBookedFirstClassSeats(++count);
						response.status = true;
						response.message = "Flight Seats updated Successfuly";
					} else {
						response.status = false;
						response.message = "Flight in Class:" + class1.toString() + " is already "
								+ flightInfo.getAvailableFirstClassSeats();
					}
					break;
				case Business:
					if (flightInfo.getAvailableBusinessClassSeats() > 0) {
						count = flightInfo.getBookedBusinessClassSeats();
						flightInfo.setBookedBusinessClassSeats(++count);
						response.status = true;
						response.message = "Flight Seats updated Successfuly";
					} else {
						response.status = false;
						response.message = "Flight in Class:" + class1.toString() + " is already "
								+ flightInfo.getAvailableFirstClassSeats();

					}
					break;
				case Economy:
					if (flightInfo.getAvailableEconomyClassSeats() > 0) {
						count = flightInfo.getBookedBusinessClassSeats();
						flightInfo.setBookedBusinessClassSeats(++count);
						response.status = true;
						response.message = "Flight Seats updated Successfuly";
					} else {
						response.status = false;
						response.message = "Flight in Class:" + class1.toString() + " is already "
								+ flightInfo.getAvailableFirstClassSeats();

					}
					break;

				default:
					response.status = false;
					response.message = "This Flight Class is not defined.";
					break;
				}
			} else {
				response.status = false;
				response.message = "There is no flight with this flightID : " + flightID + " .";
			}
		} else {
			response.status = false;
			response.message = "There is no flight data.";
		}

		return response;
	}

	/**
	 * Get All Flight Details.
	 * 
	 * @return
	 */
	public Response flightDetails() {
		Response response = new Response();
		response.status = false;
		response.message = "No flight is aviable.";
		StringBuilder sb = new StringBuilder();
		if (flightsData != null && flightsData.size() > 0) {
			sb.append("FlightID   Destination   Source        Date         Time     Fist   Business   Economy  \r\n");

			int count = flightsData.size();
			for (int i = 0; i < count; i++) {
				Flight flightInfo = flightsData.get(i);
				sb.append(String.format("%d          %s      %s      %s   %s    %s      %s          %s       \r\n",
						flightInfo.getFlightID(), flightInfo.getDestinaition().toString(),
						flightInfo.getSource().toString(), flightInfo.getFlightDate(), flightInfo.getFlightTime(),
						flightInfo.getAvailableFirstClassSeats(), flightInfo.getAvailableBusinessClassSeats(),
						flightInfo.getAvailableEconomyClassSeats()));
			}

			response.status = true;
			response.message = sb.toString();
		} else {
			response.status = false;
			response.message = "There is no flight data.";
		}
		return response;
	}

	 /**
	 * @return the flightsData
	 */
	 public Flight getFlightsData(int flightID) {
		 int index = getFlightIndex(flightID);
		 if(index!= -1)
		 {
			 return flightsData.get(index);
		 }
		 else
		 {
			 return null;
		 }
	 }
	//
	// /**
	// * @return the lastFlightID
	// */
	// public int getLastFlightID() {
	// return lastFlightID;
	// }

}
