package com.cisco.telnet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.telnet.command.AbstractCommand;
import com.cisco.telnet.command.CommandFactory;

public class ClientHandler implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

	private Socket socket = null;
	private BufferedInputStream bis = null;
	private InputStreamReader isr = null;
	private BufferedOutputStream bos = null;
	private OutputStreamWriter osw = null;

	boolean stopper = true;

	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		// place holder to a directory in file-system or PWD
		String currentDirectory = System.getProperty("user.home");

		try {
			bis = new BufferedInputStream(socket.getInputStream());
			isr = new InputStreamReader(bis);
			bos = new BufferedOutputStream(socket.getOutputStream());
			osw = new OutputStreamWriter(bos);

			osw.write("Welcome to Java Telnet Server\n");
			osw.write("\t" + System.getProperty("os.name") + " - "
					+ System.getProperty("os.arch"));
			osw.write("\n\n");
			osw.flush();

			while (stopper) {
				osw.write("$ ");
				osw.flush();

				// read until enter or return (13) key is pressed
				int character;
				StringBuffer sb = new StringBuffer();
				while ((character = isr.read()) != 13)
					sb.append((char) character);

				// split buffer and extract - command, options and arguments
				String[] commandArray = sb.toString().trim().split("\\s+");

				String command = (commandArray.length >= 1) ? commandArray[0]
						: null;
				ArrayList<String> commandOptions = new ArrayList<String>();
				ArrayList<String> commandArguments = new ArrayList<String>();

				for (int i = 1; i < commandArray.length; i++) {
					if (commandArray[i].startsWith("-")) {
						commandOptions.add(commandArray[i]);
					} else {
						commandArguments.add(commandArray[i]);
					}
				}
				logger.info("Command : " + command + " Options : "
						+ commandOptions.toString() + " Arguments : "
						+ commandArguments.toString());

				// process command
				// check if command is to exit
				if (command.equals("exit") || command.equals("quit")
						|| command.equals("bye")) {
					osw.write("See ya!\n");
					osw.flush();
					stopper = false;
				} else {
					// get the command from factory and execute
					AbstractCommand abstractCommand = CommandFactory
							.getCommandInstance(command);
					if (abstractCommand != null) {
						// set command options
						abstractCommand.setOptions(commandOptions
								.toArray(new String[commandOptions.size()]));
						//set command arguments
						abstractCommand.setArguments(commandArguments
								.toArray(new String[commandArguments.size()]));
						// set console to output
						abstractCommand.setConsole(osw);
						// set current working directory
						abstractCommand.setCurrentDirectory(currentDirectory);
						abstractCommand.execute();
						// get the current directory after command execution
						currentDirectory = abstractCommand
								.getCurrentDirectory();
					} else {
						osw.write(command + ": command not found\n");
						osw.flush();
					}
				}
			}
		} catch (IOException e) {
			logger.debug("Failed to read/write from input/output streams\n");
			e.printStackTrace();
		} finally {
			// Close all open streams
			try {
				bis.close();
				isr.close();
				bos.close();
				osw.close();
			} catch (IOException e1) {
				logger.debug("Failed closing input/output streams\n ");
				e1.printStackTrace();
			}

			// Try closing client connection
			try {
				socket.close();
			} catch (IOException e2) {
				logger.debug("Closing of client socket failed\n ");
				e2.printStackTrace();
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		// Close all open streams
		try {
			bis.close();
			isr.close();
			bos.close();
			osw.close();
		} catch (IOException e) {
			logger.debug("Failed closing input/output streams\n ");
			e.printStackTrace();
		}

		// Try closing client connection
		try {
			socket.close();
		} catch (IOException e1) {
			logger.debug("Closing of client socket failed\n ");
			e1.printStackTrace();
		}
	}
}
