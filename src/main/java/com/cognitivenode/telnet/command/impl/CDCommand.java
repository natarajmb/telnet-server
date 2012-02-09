package com.cognitivenode.telnet.command.impl;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognitivenode.telnet.command.AbstractCommand;

public class CDCommand extends AbstractCommand {

	private final Logger logger = LoggerFactory.getLogger(CDCommand.class);

	@Override
	public void execute() {
		if (arguments.length == 0)
			setCurrentDirectory(System.getProperty("user.home"));

		if (arguments.length >= 1) {
			if (arguments[0].equals("..")) {
				setCurrentDirectory(new File(getCurrentDirectory()).getParent());
			} else if (arguments[0].equals(".")) {
				// nothing to do
			} else {
				File file = null;
				file = new File(arguments[0]);
				
				// directory argument is absolute path
				if(file.exists() && file.isDirectory()) {
					if (file.getAbsolutePath().equals(arguments[0])) 
						setCurrentDirectory(file.getAbsolutePath());
				} else { // directory argument is relative path

					file = new File(getCurrentDirectory()+System.getProperty("file.separator")+arguments[0]);
					if (file.exists() && file.isDirectory()) {
							setCurrentDirectory(file.getAbsolutePath());
					} else {
						try {
							getConsole().write(file + ": Not a directory\n");
						} catch (IOException e) {
							logger.debug("Failed writing to console\n");
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
