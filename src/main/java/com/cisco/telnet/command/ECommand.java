package com.cisco.telnet.command;

public enum ECommand {

	// define command and its implementation class
	LS("ls","com.cisco.telnet.command.impl.LSCommand"),
	PWD("pwd","com.cisco.telnet.command.impl.PWDCommand"),
	CD("cd","com.cisco.telnet.command.impl.CDCommand"),
	MKDIR("mkdir","com.cisco.telnet.command.impl.MKDIRCommand");
	
	private String implClassName;
	private String command;
	
	private ECommand(String command,String implClassName) {
		this.command = command;
		this.implClassName = implClassName;
	}
	
	public String getImplClassName() {
		return implClassName;
	}

	public String getCommand() {
		return command;
	}
}
