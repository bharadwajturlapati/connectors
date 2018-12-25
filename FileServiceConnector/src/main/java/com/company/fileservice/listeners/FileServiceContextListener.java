package com.company.fileservice.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FileServiceContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationConfig config = new ApplicationConfig();
		config.addKVToConfig("FILE_ROOT_FOLDER", "E:/TestSpace");
		sce.getServletContext().setAttribute("appconfig", config);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
