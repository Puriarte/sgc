package com.puriarte.gcp.resources;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.configuration.PropertiesConfiguration;

public class ConnectionManager {


	private static PropertiesConfiguration config = null;

	public static Connection establishConnection2() {
		Connection connection = null;
		try{
			config = new PropertiesConfiguration(com.puriarte.gcp.web.Constantes.PATHDBCONFIG);
			Class.forName(config.getString("driver"));
			connection = DriverManager.getConnection(config.getString("url"),config.getString("username"),config.getString("password"));
		} catch(Exception exception) {}
		return connection;
	}
	

	public static Connection establishConnection() {
		Connection connection = null;
		try {
		    InitialContext ic = new InitialContext();
		    Context initialContext = (Context) ic.lookup("java:comp/env");
		    DataSource datasource = (DataSource) initialContext.lookup("jdbc/PostgreSQLDS");
		    connection = datasource.getConnection();
		} catch (Exception ex) {
			connection = establishConnection2();
		}
		return connection;
	}

 
}
