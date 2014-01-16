/*
 * Copyright (c) Cognitivenode 2013. All Rights Reserved.
 *
 * No part of this source code or any of its contents may be reproduced, copied,
 * modified or adapted, without the prior written consent of the author,
 * unless otherwise indicated for stand-alone materials.
 */

package com.cognitivenode.telnet.command;

public enum ECommand {

	// define command and its implementation class
	LS("ls","com.cognitivenode.telnet.command.impl.LSCommand"),
	PWD("pwd","com.cognitivenode.telnet.command.impl.PWDCommand"),
	CD("cd","com.cognitivenode.telnet.command.impl.CDCommand"),
	MKDIR("mkdir","com.cognitivenode.telnet.command.impl.MKDIRCommand");
	
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
