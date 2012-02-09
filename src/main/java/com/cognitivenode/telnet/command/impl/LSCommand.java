package com.cognitivenode.telnet.command.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognitivenode.telnet.command.AbstractCommand;

public class LSCommand extends AbstractCommand {

	private final Logger logger = LoggerFactory.getLogger(LSCommand.class);

	@Override
	public void execute() {

		try {
			if (arguments.length > 0) {
				for (String path : arguments) { // iterate over argument
					File file = new File(path);
					if (file.isDirectory()) { // argument is directory
						getConsole().write(file + ":\n");
						listDirectory(file, options);
						getConsole().write("\n");
					} else if (file.isFile()) { // argument is file
						listFile(file, options);
					} else { // argument is neither a file nor a directory
						getConsole().write(
								"\n" + file + ": No such file or directory\n");
					}
				}
			} else { // command without argument, assuming current working directory
				listDirectory(new File(getCurrentDirectory()), options);
			}
			getConsole().write("\n");
		} catch (IOException e) {
			logger.debug("Failed writing to console\n");
			e.printStackTrace();
		}
	}

	void listDirectory(File dir, String[] options) {

		try {
			File[] children = null;
			if (!Arrays.deepToString(options).contains("a")) {
				// add filter not to include hidden files and hidden folders
				children = dir.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return !name.startsWith(".");
					}
				});
			} else {
				children = dir.listFiles();
			}
			if (children != null) {
				for (File file : children) {
					getConsole().write(file.getName() + " ");
				}
			}
		} catch (IOException e) {
			logger.debug("Failed writing to console\n");
			e.printStackTrace();
		}
	}

	void listFile(File file, String[] options) {
		try {
			getConsole().write(file.getName() + " ");
		} catch (IOException e) {
			logger.debug("Failed writing to console\n");
			e.printStackTrace();
		}
	}
}
