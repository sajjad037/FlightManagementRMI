package com.concordia.dist.asg1.DAL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.Models.Passenger;
import com.concordia.dist.asg1.Models.Response;

/**
 * Data access Layer Handle Passengers data.
 * 
 * @author SajjadAshrafCan
 *
 */
public class PassengerDal {
	private HashMap<String, ArrayList<Passenger>> passengerData = null;
	private int lastBookingId = 0;

	/**
	 * Constructor for PassengerDal
	 * 
	 * @param passengerData
	 * @param lastBookingId
	 */
	// public PassengerDal(HashMap<String, ArrayList<Passenger>> passengerData,
	// int lastBookingId) {
	public PassengerDal() {
		if (this.passengerData == null) {
			this.passengerData = new HashMap<String, ArrayList<Passenger>>();
		}
	}

	/**
	 * Book Flight
	 * 
	 * @param passengerInfo
	 * @return
	 */
	public Response bookFlight(Passenger passengerInfo) {
		ArrayList<Passenger> passengerList = null;
		Response response = new Response();
		String key = passengerInfo.getLastName().charAt(0) + "";
		passengerInfo.setBookingId(++lastBookingId);

		// Check Size
		if (passengerData != null && passengerData.size() > 0) {
			// Find the Key (already Exist or not)
			passengerList = passengerData.get(key);
			if (passengerList != null) {
				// get Old list and new Info
				passengerList = passengerData.get(key);
				addToHashMap(key, passengerList, passengerInfo);
			} else {
				// create new List then add
				passengerList = new ArrayList<Passenger>();
				addToHashMap(key, passengerList, passengerInfo);
			}
		} else {
			passengerList = new ArrayList<Passenger>();
			addToHashMap(key, passengerList, passengerInfo);
		}

		response.returnID = lastBookingId;
		response.status = true;
		response.message = "New recode successfully added.";

		return response;
	}

	/**
	 * Get Booked Flight Count.
	 * 
	 * @param recordType
	 * @return
	 */
	public int getBookedFlightCount(String recordType) {
		int flightCount = 0;
		if (passengerData.size() > 0) {
			boolean isAll = recordType.toLowerCase().equals("all");
			Enums.Class flightClass = Enums.getClassFromString(recordType);
			// Get a set of the entries
			Map map = Collections.synchronizedMap(passengerData);
			Set set = map.entrySet();
			// Set set = passengerData.entrySet();
			synchronized (map) {
				// Get an iterator
				Iterator i = set.iterator();

				// Display elements
				while (i.hasNext()) {
					Map.Entry me = (Map.Entry) i.next();
					ArrayList<Passenger> passengerList = (ArrayList<Passenger>) me.getValue();
					if (isAll) {
						flightCount = flightCount + passengerList.size();
					} else {
						switch (flightClass) {
						case First:
						case Business:
						case Economy:
							for (int j = 0; j < passengerList.size(); j++) {
								Enums.Class _class = passengerList.get(j).getclass();
								if (_class == flightClass) {
									flightCount = flightCount + 1;
								}
							}

							break;
						default:
							break;
						}
					}
				}
			}

		}
		return flightCount;
	}

	/**
	 * Delete all booking for a flight
	 * 
	 * @param flightId
	 * @return
	 */
	public synchronized Response deleteAllBookingForFlight(int flightId) {
		Response response = new Response();
		int countDelete = 0;
		if (passengerData.size() > 0) {

			Map map = Collections.synchronizedMap(passengerData);
			Set set = map.entrySet();
			synchronized (map) {
				Iterator i = set.iterator();

				ArrayList<String> keysToRemove = new ArrayList<String>();
				;
				ArrayList<Integer> indexToRemove;

				while (i.hasNext()) {
					indexToRemove = new ArrayList<Integer>();

					Map.Entry me = (Map.Entry) i.next();
					String key = me.getKey().toString();
					ArrayList<Passenger> passengerList = (ArrayList<Passenger>) me.getValue();

					for (int index = 0; index < passengerList.size(); index++) {
						if (passengerList.get(index).getFlightId() == flightId) {
							indexToRemove.add(index);
							++countDelete;
						}
					}

					// Look if we need to remove an remove from passenger
					// information.
					int count = indexToRemove.size();
					if (count > 0) {
						for (int j = 0; j < count; j++) {
							Passenger info = passengerList.get(j);
							boolean statu = passengerList.remove(info);
						}

						if (passengerList.size() > 0) {
							passengerData.put(key, passengerList);
						} else {
							keysToRemove.add(key);
						}
						// reset index remove list
						indexToRemove = new ArrayList<Integer>();
					}
					// System.out.print(me.getKey() + ": ");
					// System.out.println(me.getValue());
				}

				// Look for if we need to remove a key
				if (keysToRemove.size() > 0) {
					for (int idx = 0; idx < keysToRemove.size(); idx++) {
						passengerData.remove(keysToRemove.get(idx));
					}
				}

				if (countDelete > 0) {
					response.status = true;
					response.message = countDelete + " records are deleted successfully from Passengers Data.";
				} else {
					response.status = false;
					response.message = "0 records is deleted for that FlightID:" + flightId;
				}
			}
		} else {
			response.status = false;
			response.message = "There is no Passcenger data to Delete.";
		}
		return response;
	}

	 /**
	  * 
	  * @param flightId
	  * @return
	  */
	public synchronized Response deleteBooking(int bookingID) {
		Response response = new Response();
		int countDelete = 0;
		if (passengerData.size() > 0) {

			Map map = Collections.synchronizedMap(passengerData);
			Set set = map.entrySet();
			synchronized (map) {
				Iterator i = set.iterator();

				ArrayList<String> keysToRemove = new ArrayList<String>();
				;
				ArrayList<Integer> indexToRemove;

				while (i.hasNext()) {
					indexToRemove = new ArrayList<Integer>();

					Map.Entry me = (Map.Entry) i.next();
					String key = me.getKey().toString();
					ArrayList<Passenger> passengerList = (ArrayList<Passenger>) me.getValue();

					for (int index = 0; index < passengerList.size(); index++) {
						if (passengerList.get(index).getBookingId() == bookingID) {
							indexToRemove.add(index);
							++countDelete;
							break;
						}
					}

					// Look if we need to remove an remove from passenger
					// information.
					int count = indexToRemove.size();
					if (count > 0) {
						for (int j = 0; j < count; j++) {
							Passenger info = passengerList.get(j);
							boolean statu = passengerList.remove(info);
						}

						if (passengerList.size() > 0) {
							passengerData.put(key, passengerList);
						} else {
							keysToRemove.add(key);
						}
						// reset index remove list
						indexToRemove = new ArrayList<Integer>();
						break;
					}
					// System.out.print(me.getKey() + ": ");
					// System.out.println(me.getValue());
				}

				// Look for if we need to remove a key
				if (keysToRemove.size() > 0) {
					for (int idx = 0; idx < keysToRemove.size(); idx++) {
						passengerData.remove(keysToRemove.get(idx));
					}
				}

				if (countDelete > 0) {
					response.status = true;
					response.message = countDelete + " records are deleted successfully from Passengers Data.";
				} else {
					response.status = false;
					response.message = "0 records is deleted for that bookingID:" + bookingID;
				}
			}
		} else {
			response.status = false;
			response.message = "There is no Passcenger data to Delete.";
		}
		return response;
	}

	
	
	/**
	 * 
	 * @param key
	 * @param passengerList
	 * @param passengerInfo
	 */
	private void addToHashMap(String key, ArrayList<Passenger> passengerList, Passenger passengerInfo) {
		synchronized (passengerData) {
			passengerList.add(passengerInfo);
			passengerData.put(key, passengerList);
		}
	}

	/**
	 * @return the passengerData
	 */
	public HashMap<String, ArrayList<Passenger>> getPassengerData() {
		return passengerData;
	}

	/**
	 * @return the lastBookingId
	 */
	public int getLastBookingId() {
		return lastBookingId;
	}

}
