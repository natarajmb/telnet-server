/*
 * Copyright (c) Cognitivenode 2013. All Rights Reserved.
 *
 * No part of this source code or any of its contents may be reproduced, copied,
 * modified or adapted, without the prior written consent of the author,
 * unless otherwise indicated for stand-alone materials.
 */

package com.cognitivenode.telnet.command.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognitivenode.telnet.command.AbstractCommand;

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
