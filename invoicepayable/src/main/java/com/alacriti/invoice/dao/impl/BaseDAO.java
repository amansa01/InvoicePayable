package com.alacriti.invoice.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;



public class BaseDAO {
	private static final Logger log = Logger.getLogger(BaseDAO.class);
	public Long auditEventTransactiondId;
	public int auditEventId;
	protected Connection connection;

	public BaseDAO() {

	}

	public BaseDAO(Connection _connection) {
		this.connection = _connection;
	

	}

	public Connection getConnection() {
//		log.debugPrintCurrentMethodName();
		return connection;
	}

	public void setConnection(Connection connection) {
//		log.debugPrintCurrentMethodName();
		this.connection = connection;
	}

	public void close(ResultSet rs) {
//		log.debugPrintCurrentMethodName();
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				log.error("Exception in close " + e.getMessage(), e);
			}
		}
	}

	public void close(Statement stmt) {
//		log.debugPrintCurrentMethodName();
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				log.error("Exception in close " + e.getMessage(), e);
			}
		}
	}

	public void close(PreparedStatement stmt, ResultSet rs) {
//		log.debugPrintCurrentMethodName();
		close(stmt);
		close(rs);

	}

//	protected PreparedStatement getPreparedStatement(Connection connection, String sqlCmd) throws SQLException {
//
////		log.debugPrintCurrentMethodName();
//
//		log.info("getPreparedStatement: " + sqlCmd);
//		log.info("connection: " + connection);
//		try {
//
//			return connection.prepareStatement(sqlCmd);
//		} catch (SQLException e) {
//			log.error("SQLException in getPreparedStatement " + e.getMessage(), e);
//			throw e;
//		}
//	}
//
//	protected PreparedStatement getPreparedStatementReturnPK(Connection connection, String sqlCmd) throws SQLException {
////		log.debugPrintCurrentMethodName();
//
//		log.info("getPreparedStatement: " + sqlCmd);
//		try {
//
//			return connection.prepareStatement(sqlCmd, Statement.RETURN_GENERATED_KEYS);
//		} catch (SQLException e) {
//			log.error("Exception in getPreparedStatementReturnPK " + e.getMessage(), e);
//			throw e;
//		}
//	}
//
//	protected ResultSet executeQuery(PreparedStatement ps) throws SQLException {
////		log.debugPrintCurrentMethodName();
//
//		log.info("preparedStatement: " + ps);
//		try {
//
//			return ps.executeQuery();
//		} catch (SQLException e) {
//			log.error("SQLException in executeQuery " + e.getMessage(), e);
//			throw e;
//		}
//	}
//
//	protected int executeUpdate(PreparedStatement ps) throws SQLException {
////		log.debugPrintCurrentMethodName();
//
//		try {
//
//			return ps.executeUpdate();
//		} catch (SQLException e) {
//			log.error("SQLException in executeUpdate " + e.getMessage(), e);
//			throw e;
//		} finally {
//			close(ps);
//		}
//	}

		
}
