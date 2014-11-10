package com.puriarte.gcp.resources;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.configuration.PropertiesConfiguration;

public class ConnectionManager {


	private static PropertiesConfiguration config = null;

	public static Connection establishConnection() {
		Connection connection = null;
		try{
			config = new PropertiesConfiguration(com.puriarte.gcp.web.Constantes.PATHDBCONFIG);
			Class.forName(config.getString("driver"));
			connection = DriverManager.getConnection(config.getString("url"),config.getString("username"),config.getString("password"));
		} catch(Exception exception) {}
		return connection;
	}


}
