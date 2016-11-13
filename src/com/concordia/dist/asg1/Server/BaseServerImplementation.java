/**
 * 
 */
package com.concordia.dist.asg1.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.Models.Response;
import com.concordia.dist.asg1.Models.ServerConfig;
import com.concordia.dist.asg1.Service.FlightService;
import com.concordia.dist.asg1.Service.PassengerService;
import com.concordia.dist.asg1.StaticContent.StaticContent;
import com.concordia.dist.asg1.Utilities.CLogger;

/**
 * @author SajjadAshrafCan
 *
 */
public class BaseServerImplementation implements IServer {

	private int RMIPort;
	private int UDPPort;
	private String ServerName;
	private FlightService flightService;
	private PassengerService passengerService;
	private CLogger clogger;
	private Logger LOGGER = Logger.getLogger(MainServer.class.getName());

	/**
	 * Server Implementations
	 * 
	 * @param RMIPort
	 * @param UDPPort
	 * @param ServerName
	 */
	public BaseServerImplementation(int RMIPort, int UDPPort, String ServerName) {
		this.RMIPort = RMIPort;
		this.UDPPort = UDPPort;
		this.ServerName = ServerName;

		flightService = new FlightService();
		passengerService = new PassengerService();

		// initialize logger
		clogger = new CLogger(LOGGER, "Server/" + this.ServerName + ".log");
		
	}

	@Override
	public String bookFlight(String firstName, String lastName, String address, String phone, String destination,
			String date, String class1) throws RemoteException {
		clogger.log("bookFlight(firstName:" + firstName + ", lastName:" + lastName + ", address:" + address + ", phone:"
				+ phone + ", destination:" + destination + ", date:" + date + ", Class:" + class1 + ")");

		Response response = passengerService.bookFlight(flightService, firstName, lastName, address, phone, destination,
				date, class1);

		clogger.log(response.toString());
		return response.toString();
	}

	@Override
	public String getBookedFlightCount(String recordType) throws RemoteException {
		StringBuilder sb = new StringBuilder();
		String[] strArray = recordType.split(":");
		recordType = strArray[0];
		String managerID = strArray[1];
		clogger.log(managerID + " requesting to Compute BookedFlightCount for Class " + recordType);

		sb.append(getLocalFlightCount(recordType));
		int count = StaticContent.getServersList().serverConfigList.size();
		for (int i = 0; i < count; i++) {
			ServerConfig serverConfig = StaticContent.getServersList().serverConfigList.get(i);
			if (!ServerName.equals(serverConfig.serverName)) {
				sb.append(UPDGetCount(ServerName, "localhost", serverConfig.udpPort, recordType));
			}
		}
		// sb.append(UPDGetCount("", "localhost", 12121, recordType));
		// sb.append(UPDGetCount(server, ip, port, recordType));
		clogger.log("Response:" + sb.toString());
		return sb.toString();
	}

	/**
	 * Get Local Flight Count.
	 * 
	 * @param recordType
	 * @return
	 */
	public String getLocalFlightCount(String recordType) {
		int count = passengerService.getBookedFlightCount(recordType);
		clogger.log("getLocalFlightCount(recordType:" + recordType + ") => " + count + ".");
		return ServerName + " has " + count + ".";
	}

	@Override
	public String editFlightRecord(String recordID, String fieldName, String newValue) throws RemoteException {
		String reply = "";
		String[] strArray = recordID.split(":");
		recordID = strArray[0];
		String managerID = strArray[1];

		clogger.log(
				"editFlightRecord(recordID:" + recordID + ", fieldName:" + fieldName + ", newValue:" + newValue + ").");

		Enums.FlightFileds operation = Enums.getEnumFlightFiledsFromString(fieldName);

		switch (operation) {
		case createFlight:
			String[] arr = newValue.split(":");
			// IstCls , BusCls , EconCls , Date , Time , destination.
			reply = createFlight(managerID, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
					Integer.parseInt(arr[2]), arr[3], arr[4], arr[5]);
			break;
		case deleteFlight:
			int flightID = Integer.parseInt(newValue);
			reply = deleteFlight(managerID, flightID);
			break;
		case flightDetail:
			reply = flightDetails();
			break;
		case flightDate:
		case flightTime:
		case destinaition:
		case source:
		case seatsInFirstClass:
		case seatsInBusinessClass:
		case seatsInEconomyClass:
			Response response = flightService.editFlightRecord(Integer.parseInt(recordID), fieldName, newValue);
			reply = response.toString();
			break;

		default:
			reply = "This Operation is not defined.";
			break;
		}

		clogger.log(reply);
		return reply;
	}

	private String createFlight(String ManagerID, int seatsInFirstClass, int seatsInBusinessClass,
			int seatsInEconomyClass, String flightDate, String flightTime, String _destinaition) {
		clogger.log(ManagerID + " requesting createFlight(seatsInFirstClass:" + seatsInFirstClass
				+ ", seatsInBusinessClass:" + seatsInBusinessClass + ", seatsInEconomyClass:" + seatsInEconomyClass
				+ ", flightDate:" + flightDate + ", flightTime:" + flightTime + ", destinaition:" + _destinaition
				+ ").");
		Response response = flightService.createFlight(seatsInFirstClass, seatsInBusinessClass, seatsInEconomyClass,
				flightDate, flightTime, _destinaition, ServerName);
		clogger.log(response.toString());
		return response.toString();
	}

	private String deleteFlight(String ManagerID, int flightID) {
		clogger.log(ManagerID + " is requesting deleteFlight(flightID:" + flightID + ")");
		Response response = flightService.deleteFlight(passengerService, flightID);
		clogger.log(response.toString());
		return response.toString();
	}

	private String flightDetails() {
		Response response = flightService.flightDetails();
		String reply = "";
		if (response.status) {
			reply = response.message;
		} else {
			reply = response.toString();
		}
		return reply;

	}

	/**
	 * Bind Servers to RMI Registry
	 * 
	 * @throws Exception
	 */
	public void exportServer() throws Exception {
		Remote obj = UnicastRemoteObject.exportObject(this, RMIPort);
		Registry r = LocateRegistry.createRegistry(RMIPort);
		r.bind(ServerName, obj);
	}

	/**
	 * Calling the Server Binding Function
	 */
	public void mainFunc() {
		try {
			exportServer();
			String msg = ServerName + " RMI Server is Up! and running";
			System.out.println(msg);
			
			// save some Dummy Data
			saveDummyData();
			
			//Get Flight Data
			//System.out.println(ServerName+"\r\n "+flightDetails());
			
		} catch (Exception e) {
			e.printStackTrace();
			clogger.logException("on Binding Server", e);
		}
	}

	public void startUDPServer() {
		new Thread(new UDPResponder()).start();
	}

	public String UPDGetCount(String remoteServer, String ip, int port, String recordType) {
		String reply = "";
		String msg = "";
		msg = "Requesting " + remoteServer + ", Server for BookedFlightCount for Class " + recordType + ".";
		System.out.println(msg);
		clogger.log(msg);
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(ip);
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			String request = ServerName + ":" + recordType;
			sendData = request.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			clientSocket.close();
			msg = "Reply FROM " + remoteServer + " SERVER:" + modifiedSentence.trim();
			System.out.println(msg);
			clogger.log(msg);
			reply = modifiedSentence.trim();
		} catch (Exception ex) {
			reply = "Error: encouter on " + ServerName + ", Message: " + ex.getMessage();
			clogger.logException("on starting UDP Server", ex);
			ex.printStackTrace();
		}

		return reply;
	}

	public class UDPResponder implements Runnable {

		private DatagramSocket serverSocket;

		public void run() {
			try {
				serverSocket = new DatagramSocket(UDPPort);
				byte[] receiveData = new byte[1024];
				byte[] sendData = new byte[1024];
				String msg = ServerName + " UDP Server Is UP!";

				System.out.println(msg);
				clogger.log(msg);
				while (true) {
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					serverSocket.receive(receivePacket);
					String request = new String(receivePacket.getData());
					String[] requestArray = request.trim().split(":");
					String remoteServer = requestArray[0];
					String recordType = requestArray[1];
					msg = "Request RECEIVED: " + remoteServer + " is requesting for Flight Count for " + recordType
							+ " Class.";
					System.out.println(msg);
					clogger.log(msg);
					InetAddress IPAddress = receivePacket.getAddress();
					int port = receivePacket.getPort();
					String capitalizedSentence = getLocalFlightCount(recordType); // ServerName
																					// +
																					// "
																					// has
																					// .";//
																					// sentence.toUpperCase();
					sendData = capitalizedSentence.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					serverSocket.send(sendPacket);
				}
			} catch (Exception ex) {
				clogger.logException("on starting UDP Server", ex);
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Save some dummy Data. for requested Server.
	 */
	private void saveDummyData() {
		try {
			clogger.log("saving some dummy data.");

			int count = StaticContent.getServersList().serverConfigList.size();
			for (int i = 0; i < count; i++) {
				ServerConfig serverConfig = StaticContent.getServersList().serverConfigList.get(i);
				if (!serverConfig.serverName.equals(ServerName)) {
					
					// Save Flight
					createFlight("system", 5, 10, 20, "2016/10/16", "13:14", serverConfig.serverName);

					// Book Fight
					bookFlight("Sajjad", "Ashraf", "Saint Marc", "1234567890", serverConfig.serverName, "2016/10/16",
							"Economy");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
