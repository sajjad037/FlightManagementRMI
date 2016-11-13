package com.concordia.dist.asg1.StaticContent;

import com.concordia.dist.asg1.Models.ServersList;
import com.concordia.dist.asg1.Utilities.FileStorage;

/**
 * save all type of Static string, data etc.
 * 
 * @author SajjadAshrafCan
 *
 */
public class StaticContent {
	public static final String dateFormat = "yyyy/MM/dd";
	public static final String timeFormat = "HH:mm";
	private static ServersList serversConfigurations;

	// Error Messages
	public static final String MSG_NUMBER = "Enter a number[0-9].";
	public static final String MSG_PHONE = "Enter a valid Phone # (10 digit, 5141231238).";
	public static final String MSG_ALPHA_NUMERIC_NO_SPACES = "Enter a valid Aplha Numeric (No spaces).";
	public static final String MSG_ALPHA_NUMERIC_WITH_SPACES = "Enter a valid Aplha Numeric (With spaces).";
	public static final String MSG_CITY_NAME = "Enter a valid City (Montreal, Washington, NewDelhi are case sensitive).";
	public static final String MSG_SHORTCITY = "Enter a valid Short City (MTL, WST, NDL are case sensitive).";
	// public static final String ERROR_MSG_SOURCE ="Enter a valid SOURCE
	// (Montreal, Washington, NewDelhi is case sensitive).";
	public static final String MSG_FLIGHT_CLASS = "Enter a valid Flight Class (First, Business, Economy are case sensitive).";
	public static final String MSG_FLIGHT_FIELDS = "Enter a valid Field Name \r\n (flightDate, flightTime, destinaition, source, seatsInFirstClass, seatsInBusinessClass, seatsInEconomyClass, editFlight, createFlight, deleteFlight, flightDetail are case sensitive).";
	public static final String MSG_RECORD_TYPE = "Enter a valid Flight Class (First, Business, Economy are case sensitive).";
	public static final String MSG_DATE = "Enter a valid Date (yyyy/MM/dd, ex: 2016/10/16).";
	public static final String MSG_TIME = "Enter a valid Time (HH:mm, ex: 13:13).";
	public static final String ERROR_GENERAL_MSG = "Error: Invalid, Please ";

	/**
	 * Get Server Configuration list.
	 * 
	 * @return
	 */
	public static ServersList getServersList() {
		if (serversConfigurations == null) {
			serversConfigurations = (new FileStorage()).LoadServerConfigurations();
		}
		return serversConfigurations;
	}
}
