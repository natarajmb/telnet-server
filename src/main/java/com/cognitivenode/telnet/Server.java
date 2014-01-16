/*
 * Copyright (c) Cognitivenode 2013. All Rights Reserved.
 *
 * No part of this source code or any of its contents may be reproduced, copied,
 * modified or adapted, without the prior written consent of the author,
 * unless otherwise indicated for stand-alone materials.
 */

package com.cognitivenode.telnet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

	private static Logger logger = LoggerFactory.getLogger(Server.class);
	private static ServerSocket serverSocket = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// optional argument checking
		if(args.length > 1) {
			System.err.println("Usage: java " + Server.class.getCanonicalName() + " <port-number>");
			System.exit(1);
		}

		int arg0 = -1;
		if(args.length > 0) {
		    try {
		        arg0 = Integer.parseInt(args[0]);
		        if(arg0 < 1024) {
		        	System.err.println("Usage: java " + Server.class.getCanonicalName() + " <port-number>");
		        	System.err.println("Please consider using port above 1024 to start server");
		        	System.exit(1);
		        }
		    } catch (NumberFormatException e) {
		    	System.err.println("Usage: java " + Server.class.getCanonicalName() + " <port-number>");
		        System.err.println("port-number must be an integer");
		        System.exit(1);
		    }
		}
		
		// start server on default 2704 port if no port-number specified in argument
		try {
			logger.info("Starting telnet server on port "+((arg0 == -1) ? 2704 : arg0));
			serverSocket = new ServerSocket(((arg0 == -1) ? 2704 : arg0));
		} catch (Exception e) {
			logger.debug("Opening of server socket failed\n ");
			e.printStackTrace();
			try {
				serverSocket.close();
			} catch (IOException e1) {
				logger.debug("Closing of server socket failed\n ");
				e1.printStackTrace();
			}
		}
		// keep listening, accept connection and spawn a thread
		try{
			while (true) {
				Socket clientConnection = serverSocket.accept();
				new Thread(new ClientHandler(clientConnection)).start();
			}
		} catch (IOException e) {
			logger.debug("Failed listening to connection\n ");
			e.printStackTrace();
			try {
				serverSocket.close();
			} catch (IOException e1) {
				logger.debug("Closing of server socket failed\n ");
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			serverSocket.close();
		} catch (IOException e) {
			logger.debug("Closing of server socket failed\n ");
			e.printStackTrace();
		}
	}
}
