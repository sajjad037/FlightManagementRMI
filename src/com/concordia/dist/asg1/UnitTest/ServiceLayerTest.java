package com.concordia.dist.asg1.UnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.Models.Flight;
import com.concordia.dist.asg1.Models.Response;
import com.concordia.dist.asg1.Service.FlightService;
import com.concordia.dist.asg1.Service.PassengerService;

/**
 * The class <code>PassengerServiceTest</code> contains tests for the class
 * <code>{@link ServiceLayerTest}</code>. This class test Server layer, Data
 * access layer, Models and Some classes from utilities.
 *
 *
 * @author Sajjad Ashraf
 * @version 1.0
 */
public class ServiceLayerTest {
	private PassengerService passengerService = null;
	private FlightService flightService = null;
	private Response response = null;
	private int flightID = -1;
	private String newDate = "2016/10/16";

	/**
	 * Constructor ServiceLayerTest
	 */
	public ServiceLayerTest() {
		passengerService = new PassengerService();
		flightService = new FlightService();
		response = new Response();
	}

	@Before
	public void beforeEachTest() {
		// Create Flight Test
		response.returnID = -1;
		response.message = "-1";
		response.status = false;
		response = flightService.createFlight(5, 10, 20, "2016/10/17", "13:14", "Washington", "Montreal");
		flightID = response.returnID;
	}

	@Test
	/**
	 * Test Create Flight
	 */
	public void createFlightTest() {
		response = flightService.createFlight(10, 10, 20, "2016/10/18", "13:14", "Montreal", "Washington");
		assertTrue(response.status);
	}

	@Test
	/**
	 * EditInfo Flight Info
	 */
	public void EditFlightInfo() {
		// 10, 10, 20, "2016/10/18", "13:14", "Montreal", "Washington"
		String newDate = "2016/10/22";
		response = flightService.editFlightRecord(flightID, Enums.FlightFileds.flightDate.toString(), newDate);
		assertTrue(response.status);

		Flight flightInfo = flightService.getFlight(flightID);
		if (flightInfo != null) {
			String curentDate = flightInfo.getFlightDate();
			if (newDate.equals(curentDate)) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} else {
			assertTrue(false);
		}
	}

	@Test
	/**
	 * book Flight
	 */
	public void bookFlightTest() {
		response = passengerService.bookFlight(flightService, "Sajjad", "Ashraf", "Saint Marc", "1234567890",
				"Washington", "2016/10/17", "Economy");
		assertTrue(response.status);
	}

	@Test
	/**
	 * Test is Flight Available
	 */
	public void isFlightAvailableTest() {
		response = flightService.isFlightAvailable(Enums.getFlightCitiesFromString("Washington"), "2016/10/17",
				Enums.getClassFromString("Economy"));
		assertTrue(response.status);
	}

	@Test
	/**
	 * Get Flight Detail test
	 */
	public void getFlightDetailsTest() {
		response = flightService.flightDetails();
		assertTrue(response.status);
	}

	@Test
	/**
	 * Delete Flight
	 */
	public void deleteFlightTest() {
		response = passengerService.bookFlight(flightService, "Sajjad", "Ashraf", "Saint Marc", "1234567890",
				"Washington", "2016/10/17", "Economy");
		response = flightService.deleteFlight(passengerService, flightID);
		assertTrue(response.status);
	}

	@Test
	/**
	 * Delete Flight
	 */
	public void bookedFlightCount() {
		int count = passengerService.getBookedFlightCount("ALL");
		assertTrue(count == 0);
	}

	@Test
	public void synchroniztionTest() throws InterruptedException {
		// String newDate="2016/10/16";
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				newDate = "2016/10/18";
				response = flightService.editFlightRecord(flightID, Enums.FlightFileds.flightDate.toString(), newDate);
				assertTrue(response.status);
			}
		});

		Thread t2 = new Thread(new Runnable() {

			public void run() {
				newDate = "2016/10/19";
				response = flightService.editFlightRecord(flightID, Enums.FlightFileds.flightDate.toString(), newDate);
				assertTrue(response.status);
			}
		});

		Thread t3 = new Thread(new Runnable() {
			public void run() {
				newDate = "2016/10/20";
				response = flightService.editFlightRecord(flightID, Enums.FlightFileds.flightDate.toString(), newDate);
				assertTrue(response.status);
			}
		});
		t1.start();
		t2.start();
		t3.start();

		t1.join();
		t2.join();
		t3.join();
		Flight flightInfo = flightService.getFlight(flightID);
		if (flightInfo != null) {
			String curentDate = flightInfo.getFlightDate();
			if (newDate.equals(curentDate)) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} else {
			assertTrue(false);
		}

	}

	@After
	public void afterEachTest() {
		response = null;
		flightService = null;
		passengerService = null;
		flightID = -1;
		assertNull(response);
		assertNull(flightService);
		assertNull(passengerService);
		assertEquals(flightID, -1);
	}

	// //@Test
	// /**
	// * Booked Flight
	// * Book Flight
	// * is flight available
	// * Get Flight Detail
	// *
	// * @throws RemoteException
	// */
	// public void mainTest() {
	// //Create Flight Test
	// Response response = flightService.createFlight(5, 10, 20, "2016/10/17",
	// "13:14", "Washington", "Montreal");
	// String reply= "";
	//
	// if (response.status) {
	//
	// int flightID= response.returnID;
	// //Book a seat in Above created flight
	// response = passengerService.bookFlight(flightService, "Sajjad", "Ashraf",
	// "Saint Marc", "1234567890",
	// "Washington", "2016/10/17", "Economy");
	// assertTrue(response.status);
	//
	// //Test is Flight Available
	// response =
	// flightService.isFlightAvailable(Enums.getFlightCitiesFromString("Washington"),
	// "2016/10/17", Enums.getClassFromString("Economy"));
	// assertTrue(response.status);
	//
	// // Get Flight Detail test
	// response = flightService.flightDetails();
	// assertTrue(response.status);
	//
	// //Delete Flight
	// //decrement Flight Seats
	// response = flightService.deleteFlight(passengerService, flightID);
	// assertTrue(response.status);
	//
	// int count = passengerService.getBookedFlightCount("ALL");
	// assertTrue(count == 0);
	//
	// //
	// } else {
	// assertTrue(false);
	// }
	// }
}
