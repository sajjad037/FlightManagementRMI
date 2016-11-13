package com.concordia.dist.asg1.Client;

import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.Models.Enums.FlightCities;
import com.concordia.dist.asg1.Models.ServerConfig;
import com.concordia.dist.asg1.Server.IServer;
import com.concordia.dist.asg1.StaticContent.StaticContent;
import com.concordia.dist.asg1.Utilities.CLogger;
import com.concordia.dist.asg1.Utilities.FileStorage;
import com.concordia.dist.asg1.Utilities.InputValidation;

/**
 * This Class is a helper or Initiate servers function for client.
 * 
 * @author SajjadAshrafCan
 *
 */
@SuppressWarnings("deprecation")
public class FlightManagerHelper {
	private IServer iServer = null;
	private CLogger clogger;
	private final static Logger LOGGER = Logger.getLogger(FlightManagerClient.class.getName());
	private String managerID = "-1";
	private String userCurrentCity = "";
	private Scanner scanner;

	public FlightManagerHelper(Scanner scanner) {
		// initialize logger
		setCLoggerForClient();
		this.scanner = scanner;
	}

	/**
	 * Manager Login Menu
	 */
	public void managerLogin() {
		boolean run = true;
		while (run) {
			System.out.println("Please select an option (1-2)");
			System.out.println("1. Create Manager Account.");
			System.out.println("2. Login as Manager.");
			System.out.println("3. Back.");

			int selectedOption = InputValidation.inputInteger(scanner);

			switch (selectedOption) {
			case 1:
				System.out.println("Create Enter Short City / Server Name (like MTL, WST, NDL):");
				String shortCityName = InputValidation.inputShortCity(scanner);
				String newManagerId = createManagerAccount(shortCityName);
				this.managerID = newManagerId;
				System.out.println(
						"Please note your New Manager ID : " + newManagerId + ". \r\n Press Enter to move forward.");
				scanner.nextLine();
				flightManager();
				break;
			case 2:
				System.out.println("Enter Manager ID (MTL0001):");
				String managerId = InputValidation.inputString(scanner);
				if (validateManager(managerId)) {
					this.managerID = managerId;
					flightManager();
				} else {
					System.out.println("ManagerID:" + managerId + " is wrong.");
					scanner.next();
				}

				break;

			case 3:
				run = false;
				System.out.println("Closing Moving Back....");
				break;

			default:
				System.out.println("Please Enter valid option option (1-3).");
				break;
			}
		}
	}

	/**
	 * User Login Menu
	 */
	public void userLogin() {

		System.out.println("Please " + StaticContent.MSG_CITY_NAME.replace("a valid City", "Current City"));
		String currentCity = InputValidation.inputFullCity(scanner);
		this.userCurrentCity = currentCity;

		boolean run = true;
		while (run) {
			userMenu();

			int selectedOption = InputValidation.inputInteger(scanner);

			switch (selectedOption) {
			case 1:
				bookFlight();
				break;
			case 2:
				getFlightDetails(this.userCurrentCity);
				break;

			case 3:
				run = false;
				System.out.println("Closing Moving Back....");
				break;

			default:
				System.out.println("Please Enter valid option option (1-3).");
				break;
			}
		}
	}

	/**
	 * This Function will Create Manager account.
	 * 
	 * @param shortCityName
	 * @return
	 */
	private String createManagerAccount(String shortCityName) {

		FileStorage fileStorage = new FileStorage();
		String filePath = "Logs/ManagerIds/" + shortCityName + ".txt";
		int id = 1110;
		String newManagerId = "";

		// read Manager IDS
		String managerData = fileStorage.readFromFile(filePath, Charset.defaultCharset());
		if (managerData != null && !managerData.equals("")) {
			managerData = fileStorage.RemoveCharacterFromEndorRight(managerData.trim(), ",");

			String[] managerIDs = managerData.split(",");
			int count = managerIDs.length;
			String lastID = managerIDs[count - 1];

			id = Integer.parseInt(lastID.substring(3, lastID.length()));
			newManagerId = shortCityName + (++id);
			managerData = managerData + "," + newManagerId;
		} else {
			newManagerId = shortCityName + (++id);
			managerData = newManagerId;
		}

		// Save Manager IDs
		fileStorage.saveToFile(filePath, managerData);
		return newManagerId;
	}

	/**
	 * This Function will validate Manager ID.
	 * 
	 * @param ManagerID
	 * @return
	 */
	private boolean validateManager(String ManagerID) {
		this.managerID = ManagerID;
		FileStorage fileStorage = new FileStorage();
		String shortCityName = managerID.substring(0, 3);
		String filePath = "Logs/ManagerIds/" + shortCityName + ".txt";

		// read Manager IDS
		String managerData = fileStorage.readFromFile(filePath, Charset.defaultCharset());
		if (managerData != null && !managerData.equals("")) {
			managerData = fileStorage.RemoveCharacterFromEndorRight(managerData.trim(), ",");

			return managerData.contains(ManagerID);
		} else {
			return false;
		}

	}

	/**
	 * Flight Manager Home Menu for Manager
	 */
	private void flightManager() {
		try {
			clogger = new CLogger(LOGGER, "Manager/" + managerID + ".log");
			String shortServerName = managerID.substring(0, 3);
			String serverName = getFullServerName(shortServerName);
			iServer = connectToServer(serverName);

			boolean run = true;
			while (run) {
				// Display Menu
				managerMenu();
				int selectedOption = InputValidation.inputInteger(scanner);

				switch (selectedOption) {
				case 1:
					createFlight();
					break;
				case 2:
					editFlightRecord();
					break;
				case 3:
					getBookedFlightCount();
					break;
				case 4:
					deleteFlight();
					break;

				case 5:
					getFlightDetails(serverName);
					break;
				case 6:
					run = false;
					System.out.println("Closing Moving Back....");
					break;

				default:
					System.out.println("Please Enter valid option option (1-3).");
					break;
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			clogger.logException("", e);
			e.printStackTrace();
		}
	}

	/**
	 * Manager Menu
	 */
	private static void managerMenu() {
		System.out.println("\n**** Flight Manager Client - Manager ****\n");
		System.out.println("Please select an option (1-5)");
		System.out.println("1. Create New Flight.");
		System.out.println("2. Update Flight.");
		System.out.println("3. Booked Flight Count.");
		System.out.println("4. Delete Flight.");
		System.out.println("5. View Avaiable Flights.");
		System.out.println("6. Back");
	}

	/**
	 * User Menu
	 */
	private static void userMenu() {
		System.out.println("\n**** Flight Manager Client - User ****\n");
		System.out.println("Please select an option (1-3)");
		System.out.println("1. Booked Flight.");
		System.out.println("2. View Avaiable Flights.");
		System.out.println("3. Back");
	}

	/**
	 * Set Logger File to log for Client mean in log in client.log file.
	 */
	private void setCLoggerForClient() {
		clogger = new CLogger(LOGGER, "Client/client.log");
	}

	/**
	 * Book Flight, Only user Can access this function.
	 */
	private void bookFlight() {
		try {

			String firstName, lastName, address, phone, destination, date, _class = "";

			System.out.println("\n**** Home -> User -> Book Flight ****\n");

			System.out.println("Enter First Name :");
			firstName = InputValidation.inputString(scanner);

			System.out.println("Enter Last Name :");
			lastName = InputValidation.inputString(scanner);

			System.out.println("Enter Address :");
			address = InputValidation.inputStringWithSpaces(scanner);

			System.out.println(StaticContent.MSG_PHONE);
			phone = InputValidation.inputPhoneNumber(scanner);

			System.out.println(StaticContent.MSG_CITY_NAME.replace("valid City", "Destination"));
			destination = InputValidation.inputFullCity(scanner);

			System.out.println(StaticContent.MSG_DATE);
			date = InputValidation.inputDate(scanner);

			System.out.println(StaticContent.MSG_FLIGHT_CLASS);
			_class = InputValidation.inputFlightClass(scanner);

			iServer = connectToServer(userCurrentCity);

			clogger.log(
					"bookFlight(firstName:" + firstName + ", lastName:" + lastName + ", address:" + address + ", phone:"
							+ phone + ", destination:" + destination + ", date:" + date + ", Class:" + _class + ")");

			String response = iServer.bookFlight(firstName, lastName, address, phone, destination, date, _class);

			System.out.println(response);
			clogger.log(response);

		} catch (Exception e) {
			// TODO: handle exception
			clogger.logException("", e);
			e.printStackTrace();
		}
	}

	/**
	 * Get Booked Flight count from server, Only Manager can call this function.
	 */
	private void getBookedFlightCount() {
		String recordType, response = "";
		try {

			System.out.println("\n**** Home -> Manager -> Booked Flight Count ****\n");
			System.out.println("Enter Record Type (First, Business, Economy, All):");
			recordType = InputValidation.inputRecordTypeFlightCount(scanner);

			clogger.log("Getting BookedFlightCount for Class " + recordType + "....");
			recordType = recordType+":"+managerID;
			response = iServer.getBookedFlightCount(recordType);
			System.out.println(response);
			clogger.log(response);
		} catch (Exception e) {
			clogger.logException("", e);
			e.printStackTrace();
		}
	}

	/**
	 * Edit Flight record. Accessible for only Manager.
	 */
	private void editFlightRecord() {
		String recordID, fieldName, newValue, response = "";
		try {
			System.out.println("\n**** Home -> Booked Flight Count ****\n");
			System.out.println("Enter RecordID of flight to update:");
			recordID = "" + InputValidation.inputInteger(scanner);

			System.out.println("Enter fieldName: \r\n (flightDate, flightTime, destinaition, source, seatsInFirstClass, seatsInBusinessClass, seatsInEconomyClass, createFlight, deleteFlight, flightDetail)");
			fieldName = "" + InputValidation.inputFieldName(scanner);

			System.out.println("Enter newValue of that Filed:");
			newValue = "" + InputValidation.inputNewValueOfField(scanner, fieldName);

			clogger.log("editFlightRecord(recordID:" + recordID + ", fieldName:" + fieldName + ", newValue:" + newValue
					+ ").");
			response = iServer.editFlightRecord(recordID + ":" + managerID, fieldName, newValue);

			System.out.println(response);
			clogger.log(response);
		} catch (Exception e) {
			clogger.logException("", e);
			e.printStackTrace();
		}

	}

	/**
	 * Create Flight record. Accessible for only Manager.
	 */
	private void createFlight() {
		int seatsInFirstClass, seatsInBusinessClass, seatsInEconomyClass = 0;
		String flightDate, flightTime, _destinaition, response = "";

		try {
			System.out.println("\n**** Home -> Manager -> Create New Flight ****\n");
			System.out.println("Enter Seats In First Class: (Integer Value)");
			seatsInFirstClass = InputValidation.inputInteger(scanner);

			System.out.println("Enter Seats In Business Class: (Integer Value)");
			seatsInBusinessClass = InputValidation.inputInteger(scanner);

			System.out.println("Enter Seats In Economy Class: (Integer Value)");
			seatsInEconomyClass = InputValidation.inputInteger(scanner);

			System.out.println(StaticContent.MSG_DATE.replace("a valid", "Flight"));
			flightDate = InputValidation.inputDate(scanner);

			System.out.println(StaticContent.MSG_TIME.replace("a valid", "Flight"));
			flightTime = InputValidation.inputTime(scanner);

			System.out.println(StaticContent.MSG_CITY_NAME.replace("a valid", "Destination"));
			_destinaition = InputValidation.inputFullCity(scanner);

			clogger.log("createFlight(seatsInFirstClass:" + seatsInFirstClass + ", seatsInBusinessClass:"
					+ seatsInBusinessClass + ", seatsInEconomyClass:" + seatsInEconomyClass + ", flightDate:"
					+ flightDate + ", flightTime:" + flightTime + ", destinaition:" + _destinaition + ").");

			String newValues = "" + seatsInFirstClass + ":" + seatsInBusinessClass + ":" + seatsInEconomyClass + ":"
					+ flightDate + ":" + ":" + flightTime + ":" + _destinaition;

			response = iServer.editFlightRecord("-1:" + managerID, Enums.FlightFileds.createFlight.toString(),
					newValues);

			System.out.println(response);
			clogger.log(response);

		} catch (Exception e) {
			// TODO: handle exception
			clogger.logException("", e);
			e.printStackTrace();
		}
	}

	/**
	 * Delete Flight. Accessible for only Manager.
	 */
	private void deleteFlight() {
		int flightID;
		String response = "";

		try {
			System.out.println("\n**** Home -> Manager -> Delete Flight ****\n");
			System.out.println("Enter FlightID To Deleted Record:");
			flightID = InputValidation.inputInteger(scanner);

			response = iServer.editFlightRecord("-1:" + managerID, Enums.FlightFileds.deleteFlight.toString(),
					flightID + "");

			clogger.log("deleteFlight(flightID:" + flightID + ")");
			System.out.println(response);
			clogger.log(response);

		} catch (Exception e) {
			// TODO: handle exception
			clogger.logException("", e);
			e.printStackTrace();
		}
	}

	/**
	 * Get available Flight Details, Both User and Manager can access this
	 * function
	 * 
	 * @param serverName
	 */
	private void getFlightDetails(String serverName) {
		String response = "";

		try {
			System.out.println("\n**** Home -> Flight Details ****\n");
			iServer = connectToServer(serverName);
			response = iServer.editFlightRecord("-1:" + managerID, Enums.FlightFileds.flightDetail.toString(), "");
			System.out.println(response);
			clogger.log(response);

		} catch (Exception e) {
			// TODO: handle exception
			clogger.logException("", e);
			e.printStackTrace();
		}
	}

	/**
	 * Get Server Full Name
	 * 
	 * @param shortServerName
	 * @return
	 */
	private String getFullServerName(String shortServerName) {
		String response = "";
		Enums.FlightCitiesShrot shortName = Enums.getFlightCitiesShrotFromString(shortServerName);
		int shortNameInt = shortName.ordinal();
		Enums.FlightCities flightCities = FlightCities.values()[shortNameInt];
		response = flightCities.toString();
		return response;
	}

	/**
	 * Create an instance of the server
	 * 
	 * @param serverName
	 * @return
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	private static IServer connectToServer(String serverName)
			throws MalformedURLException, RemoteException, NotBoundException {
		IServer _iServer = null;
		int size = StaticContent.getServersList().serverConfigList.size();
		for (int i = 0; i < size; i++) {
			ServerConfig serverConfig = StaticContent.getServersList().serverConfigList.get(i);
			if (serverName.equals(serverConfig.serverName)) {
				System.setSecurityManager(new RMISecurityManager());
				_iServer = (IServer) Naming
						.lookup("rmi://localhost:" + serverConfig.rmiPort + "/" + serverConfig.serverName);
				break;
			}
		}
		return _iServer;
	}

}
