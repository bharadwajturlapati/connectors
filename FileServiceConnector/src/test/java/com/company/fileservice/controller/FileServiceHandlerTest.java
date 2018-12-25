package com.company.fileservice.controller;

import org.junit.Test;

public class FileServiceHandlerTest {
	
	
	@Test
	public void testFilesWalker(){
		String path = "E:\\apache-tomcat-8.5.20\\webapps";
		
		FileServiceHandler handler = new FileServiceHandler();
		handler.getFirstLevelContents(path, false);
	}

	
	@Test
	public void testFilesWalkerV2Api(){
		String path = "E:/TestSpace";
		
		FileServiceHandler handler = new FileServiceHandler();
		handler.getFirstLevelContents(path,false);
	}
}
