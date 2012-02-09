package com.cisco.telnet.command.impl;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.telnet.command.AbstractCommand;

public class MKDIRCommand extends AbstractCommand {

	private final Logger logger = LoggerFactory.getLogger(MKDIRCommand.class);
	
	@Override
	public void execute() {

		try {

			if (arguments.length == 0) {
				getConsole().write("mkdir: missing operand\n");
			} else {
				for (String dirName : arguments) {
					File file = new File(getCurrentDirectory()
							+ System.getProperty("file.separator") + dirName);
					if (file.exists())
						getConsole().write(
								"mkdir: cannot create directory `" + file
										+ "': File exists\n");
					else
						file.mkdir();
				}
			}

		} catch (IOException e) {
			logger.debug("Failed writing to console\n");
			e.printStackTrace();
		}
	}
}
