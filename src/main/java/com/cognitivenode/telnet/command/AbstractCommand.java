package com.cognitivenode.telnet.command;

import java.io.Writer;

public abstract class AbstractCommand {

	protected String[] options = null;
	protected String[] arguments = null;
	protected Writer console = null;
	protected String currentDirectory = null;

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public String[] getArguments() {
		return arguments;
	}

	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}

	public Writer getConsole() {
		return console;
	}

	public void setConsole(Writer console) {
		this.console = console;
	}

	public String getCurrentDirectory() {
		return currentDirectory;
	}

	public void setCurrentDirectory(String currentDirectory) {
		this.currentDirectory = currentDirectory;
	}

	public abstract void execute();

}
