/**
 * 
 */
package com.concordia.dist.asg1.Server;

import java.util.logging.Logger;

import com.concordia.dist.asg1.Models.ServerConfig;
import com.concordia.dist.asg1.StaticContent.StaticContent;
import com.concordia.dist.asg1.Utilities.CLogger;
import com.concordia.dist.asg1.Utilities.FileStorage;

/**
 * @author SajjadAshrafCan
 *
 */
public class MainServer {
	private final static Logger LOGGER = Logger.getLogger(MainServer.class.getName());
	private static CLogger clogger;

	/**
	 * This Class Start all Servers.
	 */
	public MainServer() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			// initialize logger
			clogger = new CLogger(LOGGER, "Server/server.log");
			
			clogger.log("Server strat Initiated.");

			// Read Configuration File
			clogger.log("Reading Server Configurations.");
			int size = StaticContent.getServersList().serverConfigList.size();

			// String Server through loops
			BaseServerImplementation[] servers = new BaseServerImplementation[size];
			for (int i = 0; i < size; i++) {
				ServerConfig serverConfig = StaticContent.getServersList().serverConfigList.get(i);
				servers[i] = new BaseServerImplementation(serverConfig.rmiPort, serverConfig.udpPort,
						serverConfig.serverName);
				servers[i].mainFunc();
				servers[i].startUDPServer();
			}
			clogger.log("All " + size + " Servers are started Successfully.");
		}
		catch(Exception ex){
			clogger.logException("On Server Start.", ex);
			ex.printStackTrace();
		}
		
	}
}
