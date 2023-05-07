package com.dataserve.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

public abstract class AbstractDAO {
	protected Connection _conn;
	protected CallableStatement _callStmt;
	protected ResultSet _rstSet;
	protected String _sqlStmt;
	private static int trueOnce = 0;
	protected PreparedStatement _prepStatement;
	public AbstractDAO() throws NamingException, SQLException {
	}

	protected final void initConn() throws NamingException, SQLException {
		if ((this._conn == null) || (this._conn.isClosed())) {
			String dataSource = getDataSourceName();
			this._conn = JDBCHelper.getConnection(dataSource);
		}
	}

	private static String getDataSourceName() {
		String dataSource = "FNMOAMALATDS";
		
//		if (dataSource == null)
//			dataSource = "FNMOAMALATDS";
//		// try {
//		// Context ctx = new InitialContext();
//		// Context env = (Context) ctx.lookup("java:comp/env");
//		// dataSource = (String) env.lookup("IOAPPDataSource");
//		// } catch (Exception e) {
//		// }
		return dataSource;
	}

	protected final void safeClose(CallableStatement statement) {
		try {
			if (this._rstSet != null) {
				this._rstSet.close();
			}

			if (statement != null) {
				statement.close();
			}
		} catch (SQLException unexpected) {
		}
	}
	
	protected final void safeClose(PreparedStatement statement) {
		try {
			if (this._rstSet != null) {
				this._rstSet.close();
			}

			if (statement != null) {
				statement.close();
			}
		} catch (SQLException unexpected) {
		}
	}

	protected final void releaseResources() {
		if (this._conn != null) {
			try {
				this._conn.close();
			} catch (SQLException unexpected) {
			}
		}
	}

	public static Connection getDataConnectionName() {
		Connection _conn = null;
		// String dataSource = "DSIOAPPDS";
		try {

			// Context ctx = new InitialContext();
			// Context env = (Context) ctx.lookup("java:comp/env");
			// dataSource = (String) env.lookup("FNMOAMALATDS");

			_conn = JDBCHelper.getConnection("FNMOAMALATDS");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _conn;
	}

	public static int fixDbNullAsInt(ResultSet rs, String columnName)
			throws SQLException {
		int value;

		try {
			rs.getInt(columnName);
			if (!rs.wasNull()) {
				value = rs.getInt(columnName);
			} else {
				value = -1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return value;
	}

	public static String fixDbNullAsString(ResultSet rs, String columnName)
			throws SQLException {
		String value = null;

		try {
			rs.getString(columnName);
			if (!rs.wasNull()) {
				value = rs.getString(columnName);
			} else {
				value = "";
			}

		} catch (SQLException e) {
			throw e;
		}

		return value;
	}
	
	// public static Connection getDataConnectionName(int appServer) {
	// Connection _conn = null;
	// String dataSource = "DSIOAPPDS";
	// try {
	// Context ctx = new InitialContext();
	// Context env = (Context) ctx.lookup("java:comp/env");
	// dataSource = (String) env.lookup("IOAPPDataSource");
	//
	// _conn = JDBCHelper.getConnection(dataSource, appServer);
	// } catch (Exception e) {
	// e.printStackTrace(); AppLogger.LogFullTrace(e);
	// }
	// return _conn;
	// }
}