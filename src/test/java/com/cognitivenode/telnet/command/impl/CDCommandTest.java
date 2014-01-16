/*
 * Copyright (c) Cognitivenode 2013. All Rights Reserved.
 *
 * No part of this source code or any of its contents may be reproduced, copied,
 * modified or adapted, without the prior written consent of the author,
 * unless otherwise indicated for stand-alone materials.
 */

package com.cognitivenode.telnet.command.impl;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CDCommandTest {

	CDCommand cdCommand = new CDCommand();
	File rootDir = new File(System.getProperty("user.dir"));
	File testDir = new File(rootDir.getAbsolutePath()+System.getProperty("file.separator")+"test_dir");
	
	@Before
	public void setUp() {
		testDir.mkdir();
	} 

	@After
	public void tearDown() {
		File[] files = testDir.listFiles();
		for(File file: files)
			file.delete();
		
		testDir.delete();
	}
	
	@Test // Test for $ cd <dir-name-relative>
 	public void changeToRelativeDirectory() {
		File testDir1 = new File(testDir.getAbsolutePath()+System.getProperty("file.separator")+"test1_dir");
		testDir1.mkdir();
		cdCommand.setCurrentDirectory(testDir.getAbsolutePath());
		cdCommand.setArguments(new String[]{testDir1.getName()});
		cdCommand.execute();
		Assert.assertEquals(testDir1.getAbsolutePath(), cdCommand.getCurrentDirectory());
	}
	
	@Test // Test for $ cd <dir-name-absolute>
 	public void changeToAbsoluteDirectory() {
		File testDir1 = new File(testDir.getAbsolutePath()+System.getProperty("file.separator")+"test1_dir");
		testDir1.mkdir();
		cdCommand.setCurrentDirectory(testDir.getAbsolutePath());
		cdCommand.setArguments(new String[]{testDir1.getAbsolutePath()});
		cdCommand.execute();
		Assert.assertEquals(testDir1.getAbsolutePath(), cdCommand.getCurrentDirectory());
	}
	
	@Test // Test for $ cd ..
	public void changeToParent() {
		File testDir1 = new File(testDir.getAbsolutePath()+System.getProperty("file.separator")+"test1_dir");
		testDir1.mkdir();
		cdCommand.setCurrentDirectory(testDir1.getAbsolutePath());
		cdCommand.setArguments(new String[]{".."});
		cdCommand.execute();
		Assert.assertEquals(testDir.getAbsolutePath(), cdCommand.getCurrentDirectory());
	}
	
	@Test // Test for $ cd
	public void changeToHome() {
		File testDir1 = new File(testDir.getAbsolutePath()+System.getProperty("file.separator")+"test1_dir");
		testDir1.mkdir();
		cdCommand.setCurrentDirectory(testDir1.getAbsolutePath());
		cdCommand.setArguments(new String[]{});
		cdCommand.execute();
		Assert.assertEquals(System.getProperty("user.home"), cdCommand.getCurrentDirectory());
	}
}
