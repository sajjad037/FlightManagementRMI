/**
 * 
 */
package com.concordia.dist.asg1.Client;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.Utilities.FileStorage;
import com.concordia.dist.asg1.Utilities.InputValidation;

/**
 * @author SajjadAshrafCan
 *
 */
public class test {
	public enum Blah {
		A, B, C, D
	}

	private final static Logger LOGGER = Logger.getLogger(test.class.getName());

	/**
	 * @param args
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		// TODO Auto-generated method stub
		// Create a hash map
		// HashMap hm = new HashMap();
		// System.out.println("Size: " + hm.size());
		// // Put elements to the map
		// hm.put("Zara", new Double(3434.34));
		// hm.put("Mahnaz", new Double(123.22));
		// hm.put("Ayan", new Double(1378.00));
		// hm.put("Daisy", new Double(99.22));
		// hm.put("Qadir", new Double(-19.08));
		// hm.put("Zara", new Double(10.34));
		// System.out.println("Zara's new balance: " + hm.get("Zara"));
		// System.out.println("Size: " + hm.size());

		// Blah val = Blah.valueOf("X");

		// Date Check
		// String value = "2015/12/20";
		// String format = "yyyy/MM/dd";
		// if(checkStringHasValidDate(value, format))
		// {
		// System.out.println("True");
		// }
		// else
		// {
		// System.out.println("False");
		// }
		//
		//
		// String startDateString = "14:23";
		// DateFormat df = new SimpleDateFormat("HH:mm");
		// Date startDate;
		// try {
		// startDate = df.parse(startDateString);
		// String newDateString = df.format(startDate);
		// System.out.println(newDateString);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
//		CLogger clogger = new CLogger(LOGGER, "test.log");
//		clogger.log("test Check");
//		System.out.println(Blah.A.toString());
//
//		ServersList serverList = new ServersList();
//		serverList.serverConfigList = new ArrayList<ServerConfig>();
//		ServerConfig serverConfig = new ServerConfig();
//		serverConfig.rmiPort = 2020;
//		serverConfig.udpPort = 3030;
//		serverConfig.serverName = Enums.FlightCities.Montreal.toString();
//		serverConfig.shortServerName = Enums.FlightCitiesShrot.MTL.toString();
//		serverList.serverConfigList.add(serverConfig);
//		serverConfig = new ServerConfig();
//		serverConfig.rmiPort = 2021;
//		serverConfig.udpPort = 3031;
//		serverConfig.serverName = Enums.FlightCities.Washington.toString();
//		serverConfig.shortServerName = Enums.FlightCitiesShrot.WST.toString();
//		serverList.serverConfigList.add(serverConfig);
//		serverConfig = new ServerConfig();
//		serverConfig.rmiPort = 2022;
//		serverConfig.udpPort = 3032;
//		serverConfig.serverName = Enums.FlightCities.NewDelhi.toString();
//		serverConfig.shortServerName = Enums.FlightCitiesShrot.NDL.toString();
//		serverList.serverConfigList.add(serverConfig);
//
//		(new FileStorage()).saveConfigFile(serverList);
//
//		ServersList serverList1 = (new FileStorage()).LoadServerConfigurations();
//		for (int i = 0; i < serverList1.serverConfigList.size(); i++) {
//			System.out.println(serverList1.serverConfigList.get(i).toString());
//		}
//		System.out.println("Please enter a valid Phone #.");
//		Scanner scanner = new Scanner(System.in);
//		String phoneNumber=InputValidation.inputPhoneNumber(scanner);
		//Boolean value = isValidDestiantio("Montreal1");
//		String managerID= "MTL1111,";
//		managerID =new FileStorage().RemoveCharacterFromEndorRight(managerID.trim(), ",");
//		System.out.println(managerID);
//		System.out.println(managerID.substring(0,3));
//		System.out.println(managerID.substring(3,managerID.length()));
		
		//createManagerAccount("MTL");
		
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Enter Value 2016/31/12: ");
//		//String phoneNumber=InputValidation.inputDate(scanner);
//		//scanner.nextLine();
//		String chr= ""+scanner.next().charAt(0);
//		System.out.println("out");
//		FlightService flightService = new  FlightService();
//		Response response = flightService.createFlight(5, 10, 20, "2016/10/16", "13:14", "Washington", "Montreal");
//		response = flightService.createFlight(5, 10, 20, "2016/10/16", "13:14", "NewDelhi", "Montreal");
//		System.out.println(response.toString());
//		response = flightService.createFlight(5, 10, 20, "2016/10/16", "13:14", "Washington", "NewDelhi");
//		System.out.println(response.toString());
//		System.out.println(flightService.flightDetails().message);
		Scanner scanner = new Scanner(System.in);
		//String managerId = InputValidation.inputString(scanner);
		String managerId = InputValidation.inputRecordTypeFlightCount(scanner);
		System.out.println("out");
	}
	
	public static void createManagerAccount(String shortCityName){
		
		FileStorage fileStorage = new FileStorage();
		String filePath = "Logs/ManagerIds/"+shortCityName+".txt";
		int id = 1110;
		
		//read Manager IDS
		String managerData = fileStorage.readFromFile(filePath, Charset.defaultCharset());
		if(managerData!= null && !managerData.equals("")){
			managerData =fileStorage.RemoveCharacterFromEndorRight(managerData.trim(), ",");
			
			String[] managerIDs = managerData.split(",");		
			int count = managerIDs.length;
			String lastID = managerIDs[count-1];
			
			id = Integer.parseInt(lastID.substring(3,lastID.length()));
			String newManagerId =  shortCityName + (++id);
			managerData = managerData+","+newManagerId;
		}
		else
		{
			String newManagerId =  shortCityName + (++id);
			managerData = newManagerId;
		}	
		
		//Save Manager IDs
		fileStorage.saveToFile(filePath, managerData);
	}

	public static boolean isValidDestiantio(String filedsName) {
		try {
			return Enums.FlightCities.valueOf(filedsName)!= null && !Enums.FlightCities.valueOf(filedsName).equals("");
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	public static boolean checkStringHasValidDate(String strDate, String format) {
		boolean status = false;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			Date output = dateFormat.parse(strDate);
			String value = dateFormat.format(output);
			status = strDate.equals(value);
		} catch (Exception ignore) {
		}

		return status;
	}

}
