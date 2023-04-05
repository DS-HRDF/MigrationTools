package com.dataserve.queue.sync;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;


public abstract class AbstractDAO {
	protected static Connection _conn;
	protected CallableStatement _callStmt;
	protected ResultSet _rstSet;
	protected String _sqlStmt;
	protected PreparedStatement _prepStatement;
    public static final int TOMCAT = 1;
    public static final int WEB_LOGIC = 2;
    public static final int WEB_SPHERE = 3;


	public AbstractDAO() throws NamingException, SQLException {
	}

	protected final void initConn() throws NamingException, SQLException, ClassNotFoundException {
		
		getConnection();
		
	}
	
	public static  Connection getConnection() throws SQLException, NamingException, ClassNotFoundException
    {
	
		// Establish the connection. 
		/*SQLServerDataSource ds = new SQLServerDataSource();
		ds.setIntegratedSecurity(true);
		ds.setServerName("D1SHLDBSQTWV1");//VPSHLSCLT002 //D1SHLDBSQTWV1
		ds.setPortNumber(55624); //55624
		ds.setDatabaseName("MOAMALAT1");//MOAMALAT1
		ds.getConnection();

		return ds.getConnection();*/
		
		

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

//        String connectionUrl = "jdbc:sqlserver://D1SHLDBSQTWV1:55624;database=MOAMALAT1;user=Moamalat1;password=$$Sp22@qwer;";//http://d1shlwbxxdwv1/
        String connectionUrl = "jdbc:sqlserver://10.50.16.139:52169;database=MOAMALAT1;user=moamalat1;password=moamalat1;";//http://d1shlwbxxdwv1/
        //String dbURL = "jdbc:sqlserver://localhost\\sqlexpress;user=sa;password=secret";
        

        _conn = DriverManager.getConnection(connectionUrl);
        System.out.println("connection");

        return _conn;
    }

	private static String getDataSourceName() {
		String dataSource = null;
		if (dataSource == null)
			dataSource = "FNMOAMALATDS1";
		// try {
		// Context ctx = new InitialContext();
		// Context env = (Context) ctx.lookup("java:comp/env");
		// dataSource = (String) env.lookup("IOAPPDataSource");
		// } catch (Exception e) {
		// }
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
	

	public void buildCallProcedure(String procedureName, int parameterCount) {

		StringBuilder sb = new StringBuilder("");
		sb.append("{ call ");
		sb.append("MOAMALAT1.dbo");
		sb.append(procedureName);
		sb.append("(");
		for (int i = 1; i <= parameterCount; i++) {
			sb.append("?");
			if (i != parameterCount)
				sb.append(", ");
		}
		sb.append(") }");
		this._sqlStmt = sb.toString();
	}
	protected void executeQuery(int oracleOutParam) throws SQLException {
	
		_rstSet = _callStmt.executeQuery();
	}
}
