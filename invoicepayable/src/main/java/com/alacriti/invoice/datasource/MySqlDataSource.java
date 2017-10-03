package com.alacriti.invoice.datasource;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class MySqlDataSource {
    private static MySqlDataSource ms_this = null;
    private static DataSource dbSource = null;
    
    private MySqlDataSource() {
        init();
    }

    protected void init() {
        initialize();
    }

    
    
    public static MySqlDataSource getInstance() {
        //log.debugPrintCurrentMethodName();
        synchronized (MySqlDataSource.class) {
            if (ms_this == null) {
                synchronized (MySqlDataSource.class) {
                    ms_this = new MySqlDataSource();
                }
            }
        }

        return ms_this;
    }
    public Connection getConnection() {
        //log.debugPrintCurrentMethodName();
        try {
            
            Connection dbCon = dbSource.getConnection();
            dbCon.setAutoCommit(false);
        
            return dbCon;
        } catch (Exception e) {
            //log.logError("Exception in getConnection " + e.getMessage(), e);
            System.out.println("Exception in getConnection " + e.getMessage());
        }
        return null;
    }
    protected void initialize() {
        //log.debugPrintCurrentMethodName();
        try {
            if (dbSource == null) {
            //    log.logInfo("DataSource  looking up URL " + "java:jboss/datasources/newCheckoutMySqlDS");
                InitialContext aInitialContext = new InitialContext();
                dbSource = (DataSource) aInitialContext.lookup("java:jboss/datasources/TRAINEEE");

                //log.logDebug("DataSource dbSource was null and was successfully setup by looking up URL "
                    //    + "java:jboss/datasources/newCheckoutMySqlDS");
            }
        } catch (NamingException e) {
            //log.logError("NamingException in initialize " + e.getMessage(), e);
        } catch (Exception e) {
            //log.logError("Exception in initialize " + e.getMessage(), e);
        }
    }

}
//public final class MySqlDataSource {
//
//	private static final AppLogger log = LogUtil.getLogger(MySqlDataSource.class);
//	private static MySqlDataSource ms_this = null;
//	private static DataSource dbSource = null;
//
//	private MySqlDataSource() {
//		init();
//	}
//
//	protected void init() {
//		initialize();
//	}
//
//	public static MySqlDataSource getInstance() {
//		log.debugPrintCurrentMethodName();
//		synchronized (MySqlDataSource.class) {
//			if (ms_this == null) {
//				synchronized (MySqlDataSource.class) {
//					ms_this = new MySqlDataSource();
//				}
//			}
//		}
//
//		return ms_this;
//	}
//
//	protected void initialize() {
//		log.debugPrintCurrentMethodName();
//		try {
//			if (dbSource == null) {
//				log.logInfo("DataSource  looking up URL " + "java:jboss/datasources/newCheckoutMySqlDS");
//				InitialContext aInitialContext = new InitialContext();
//				dbSource = (DataSource) aInitialContext.lookup("java:jboss/datasources/newCheckoutMySqlDS");
//
//				log.logDebug("DataSource dbSource was null and was successfully setup by looking up URL "
//						+ "java:jboss/datasources/newCheckoutMySqlDS");
//			}
//		} catch (NamingException e) {
//			log.logError("NamingException in initialize " + e.getMessage(), e);
//		} catch (Exception e) {
//			log.logError("Exception in initialize " + e.getMessage(), e);
//		}
//	}
//
//	public Connection getConnection() {
//		log.debugPrintCurrentMethodName();
//		try {
//			Connection dbCon = dbSource.getConnection();
//			dbCon.setAutoCommit(false);
//			/*
//			 * if (isConneLeakChkEnabled) { Handler handler = new
//			 * Handler(dbCon); Connection mapProxy = (Connection)
//			 * Proxy.newProxyInstance(handler.getClass().getClassLoader(), new
//			 * Class[] { Connection.class }, handler); dbCon = mapProxy;
//			 * 
//			 * synchronized (connLeakVarlock) { totalOpenConnCnt++;
//			 * connOpenedNow++; handler.setId("" + totalOpenConnCnt); String
//			 * localStackTrace = ExceptionUtil .getStackTrace(new
//			 * Exception("No Problem in this exception.")).replaceAll("\\n",
//			 * ""); openConnIdMap.put(handler.getId(), localStackTrace); if
//			 * (localStackTrace.length() > 500) { localStackTrace =
//			 * localStackTrace.substring(0, 499); }
//			 * connLog.info("::Connection pulled:" + totalOpenConnCnt + "::" +
//			 * (-totalClosedConnCnt) + ">" + connOpenedNow + ":\n" +
//			 * localStackTrace);
//			 * 
//			 * } }
//			 */
//			return dbCon;
//		} catch (Exception e) {
//			log.logError("Exception in getConnection " + e.getMessage(), e);
//		}
//		return null;
//	}
//
//	private static final Logger connLog = Logger.getLogger("CONNECTION_LEAK");
//	private static final Map<String, String> openConnIdMap = new HashMap<String, String>();
//	private static final boolean isConneLeakChkEnabled = true;// connLog.isInfoEnabled();
//	private static final Object connLeakVarlock = new Object();
//
//	static class Handler implements InvocationHandler {
//
//		Connection conn;
//		String id;
//
//		public Handler(Connection conn) {
//			this.conn = conn;
//		}
//
//		public String getId() {
//			return id;
//		}
//
//		public void setId(String id) {
//			this.id = id;
//		}
//
//		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//			try {
//				if (method.getName().equalsIgnoreCase("close")) {
//					synchronized (connLeakVarlock) {
//						totalClosedConnCnt--;
//						connOpenedNow--;
//						openConnIdMap.remove(this.getId());
//						String localStackTrace = ExceptionUtil
//								.getStackTrace(new Exception("No Problem in this exception.")).replaceAll("\\n", "");
//						if (localStackTrace.length() > 500) {
//							localStackTrace = localStackTrace.substring(0, 499);
//						}
//						connLog.info("::Connection returned:" + totalOpenConnCnt + "::" + (-totalClosedConnCnt) + ">"
//								+ connOpenedNow + ":\n" + localStackTrace);
//
//					}
//				}
//				return method.invoke(conn, args);
//			} catch (InvocationTargetException e) {
//
//				log.logError("InvocationTargetException in invoke " + e.getMessage(), e);
//
//				throw e.getTargetException();
//			}
//		}
//	}
//
//	private static int totalOpenConnCnt = 0;
//	private static int totalClosedConnCnt = 0;
//	private static int connOpenedNow = 0;
//
//	public static void printConnectionLeakTrace() {
//		log.logDebug("printConnectionLeakTrace(): Start");
//
//		if (openConnIdMap.size() > 0) {
//			log.logError("No Problem in this exception. There are some open connection exist now : ");
//			for (Map.Entry<String, String> entry : openConnIdMap.entrySet()) {
//				log.logError(
//						"No Problem in this exception. ConnId:" + entry.getKey() + " :: Trace :" + entry.getValue());
//			}
//		}
//		log.logDebug("printConnectionLeakTrace(): End");
//
//	}
//}
