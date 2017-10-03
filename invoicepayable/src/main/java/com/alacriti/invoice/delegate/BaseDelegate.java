package com.alacriti.invoice.delegate;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.alacriti.invoice.datasource.MySqlDataSource;




public class BaseDelegate {

	private static final Logger log = Logger.getLogger(BaseDelegate.class);

	private Connection connection;

	public void setConnection(Connection _connection) {
		this.connection = _connection;
	}

	public Connection getConnection() {
//		log.debugPrintCurrentMethodName();
		return connection;
	}

	protected void endDBTransaction(Connection connection) {
//		log.debugPrintCurrentMethodName();
		try {
			connection.commit();

		} catch (SQLException e) {
			log.error("SQLException in endDBTransaction " + e.getMessage(), e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error("SQLException in endDBTransaction" + e1.getMessage(), e1);
			}
		} catch (Exception e) {
			log.error("Exception in endDBTransaction" + e.getMessage(), e);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				log.error("SQLException in endDBTransaction" + e.getMessage(), e);
			}
		}

	}

	protected void endDBTransaction(Connection connection, boolean isRollback) {
		log.getLoggerRepository();

		if (isRollback) {
			try {
				connection.rollback();
				log.info("Rolled Back on some exception....!!!");
			} catch (SQLException e) {
				log.error("SQLException in endDBTransaction " + e.getMessage(), e);
			}

			finally {
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					log.error("SQLException in endDBTransaction " + e.getMessage(), e);
				}
			}
		} else {
			endDBTransaction(connection);
		}

	}

	protected Connection startDBTransaction() {
		log.getLoggerRepository();
		Connection conn = null;
		try {
			if (conn == null || conn.isClosed())
				conn = MySqlDataSource.getInstance().getConnection();

			conn.setAutoCommit(false);
		} catch (SQLException e) {
			log.error("SQLException in startDBTransaction " + e.getMessage(), e);
		}
		return conn;

	}
}
