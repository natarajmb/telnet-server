package com.cisco.telnet.command.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.telnet.command.AbstractCommand;

public class PWDCommand extends AbstractCommand {

	private final Logger logger = LoggerFactory.getLogger(PWDCommand.class);

	@Override
	public void execute() {
		try {
			getConsole().write(getCurrentDirectory() + "\n");
		} catch (IOException e) {
			logger.debug("Failed writing to console\n");
			e.printStackTrace();
		}
	}

}
