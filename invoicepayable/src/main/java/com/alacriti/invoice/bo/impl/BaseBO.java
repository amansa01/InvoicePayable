package com.alacriti.invoice.bo.impl;

import java.sql.Connection;

import org.apache.log4j.Logger;


public class BaseBO {
	private static final Logger log = Logger.getLogger(BaseBO.class);
	private Connection connection = null;

	public BaseBO() {
	}

	public BaseBO(Connection connection) {
//		log.debugPrintCurrentMethodName();
		this.connection = connection;
	}

	public Connection getConnection() {
//		log.debugPrintCurrentMethodName();
		return connection;
	}

	public void setConnection(Connection connection) {
//		log.debugPrintCurrentMethodName();
		this.connection = connection;
	}
}
