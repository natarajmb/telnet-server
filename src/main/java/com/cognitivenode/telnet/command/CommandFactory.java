/*
 * Copyright (c) Cognitivenode 2013. All Rights Reserved.
 *
 * No part of this source code or any of its contents may be reproduced, copied,
 * modified or adapted, without the prior written consent of the author,
 * unless otherwise indicated for stand-alone materials.
 */

package com.cognitivenode.telnet.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(CommandFactory.class);

	public static AbstractCommand getCommandInstance(String command) {

		AbstractCommand abstractCommand = null;
		ECommand matchedCommand = null;

		// Check if command exists in enumeration
		for (ECommand eCommand : ECommand.values()) {
			if (eCommand.getCommand().equals(command)) {
				matchedCommand = eCommand;
				break;
			}
		}

		// Get the implementation class for the command and create an instance
		// and send ClientHandler to be executed
		try {
			if (matchedCommand != null)
				abstractCommand = (AbstractCommand) Class.forName(
						matchedCommand.getImplClassName()).newInstance();
		} catch (ClassNotFoundException e) {
			logger.debug(command + " implementation class not found");
			e.printStackTrace();
		} catch (InstantiationException e) {
			logger.debug(command
					+ " implementation class failed to instantiate");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.debug(command + " implementation class access exception");
			e.printStackTrace();
		}
		return abstractCommand;
	}
}
