package com.puriarte.utils.log4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
 
public class Log4jInit extends HttpServlet {
 
  public void init() {
    String prefix =  getServletContext().getRealPath("/");
    String file = getInitParameter("log4j-init-file");
    String enable = getInitParameter("log4j-enable");
    String watch = getInitParameter("log4j-watch");

    // if the log4j-init-file is not set, then no point in trying
    if(file != null && enable != null && enable.equalsIgnoreCase("true")) {
    		if (watch != null && watch.equalsIgnoreCase("true")) {
    			PropertyConfigurator.configureAndWatch(prefix+file);
    		} else {	
      		PropertyConfigurator.configure(prefix+file);
      	}
    }
  }
 
  public void doGet(HttpServletRequest req, HttpServletResponse res) {
  
  }
}
