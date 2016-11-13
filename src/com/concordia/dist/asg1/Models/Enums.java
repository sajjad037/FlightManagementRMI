package com.concordia.dist.asg1.Models;
/**
 * Contains all Enums.
 * @author SajjadAshrafCan
 *
 */
public class Enums {
	public enum Class {
		First, Business, Economy
	}

	public enum FlightCities {
		Montreal, Washington, NewDelhi
	}

	public enum FlightCitiesShrot {
		MTL, WST, NDL
	}

	public enum FlightFileds {
		flightDate, flightTime, destinaition, source, seatsInFirstClass, seatsInBusinessClass, seatsInEconomyClass, createFlight, deleteFlight, flightDetail
	}

	public static FlightFileds getEnumFlightFiledsFromString(String filedsName) {
		try {
			return FlightFileds.valueOf(filedsName);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static FlightCities getFlightCitiesFromString(String filedsName) {
		try {
			return FlightCities.valueOf(filedsName);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static Class getClassFromString(String filedsName) {
		try {
			return Class.valueOf(filedsName);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static FlightCitiesShrot getFlightCitiesShrotFromString(String filedsName) {
		try {
			return FlightCitiesShrot.valueOf(filedsName);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
