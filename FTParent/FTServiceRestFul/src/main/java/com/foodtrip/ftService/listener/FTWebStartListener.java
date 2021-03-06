package com.foodtrip.ftService.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.foodtrip.ftcontroller.FTController;
import com.foodtrip.ftmodeldb.Neo4JConnector;

public class FTWebStartListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		WebApplicationContext springContext = 
			    WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		FTController.connector = springContext.getBean(Neo4JConnector.class);
	}
}
