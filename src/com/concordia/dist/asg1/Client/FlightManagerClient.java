package com.concordia.dist.asg1.Client;

import java.util.Scanner;
import java.util.logging.Logger;

import com.concordia.dist.asg1.Utilities.CLogger;
import com.concordia.dist.asg1.Utilities.InputValidation;

/**
 * Flight Manager Client. Users : Simple User and Managers.
 * 
 * @author SajjadAshrafCan
 *
 */

public class FlightManagerClient {
	private final static Logger LOGGER = Logger.getLogger(FlightManagerClient.class.getName());
	private static CLogger clogger;
	private static FlightManagerHelper flightManagerHelper;

	/**
	 * Return basic menu.
	 */
	private static void showWelcomMenu() {
		System.out.println("\n**** Welcome to Flight Manager Client - Home ****\n");
		System.out.println("Please select an option (1-3)");
		System.out.println("1. Use as User.");
		System.out.println("2. Login as Manager");
		System.out.println("3. Exit");
	}

	public static void main(String[] args) {

		try {
			// initialize logger
			clogger = new CLogger(LOGGER, "Client/client.log");
			clogger.log("Client strat Initiated.");

			Scanner scanner = new Scanner(System.in);
			flightManagerHelper = new FlightManagerHelper(scanner);
			boolean run = true;
			while (run) {
				// Display Menu
				showWelcomMenu();
				int selectedOption = InputValidation.inputInteger(scanner);

				switch (selectedOption) {
				case 1:
					// User View
					flightManagerHelper.userLogin();
					break;
				case 2:
					// Manager Login
					flightManagerHelper.managerLogin();
					break;

				case 3:
					// Exit Application
					run = false;
					System.out.println("Closing Flight Manager, Have Good Day.");
					break;

				default:
					System.out.println("Please Enter valid option option (1-3).");
					break;
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
