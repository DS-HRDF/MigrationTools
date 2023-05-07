package com.dataserve.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import javax.naming.NamingException;

import com.dataserve.entities.Employee;



public class HandlerDAO extends AbstractDAO {
	public HandlerDAO() throws NamingException, SQLException {
	}
	
	public Employee getEmployeeByUserId(String userId) throws Exception {
		Employee employee = null;
		try {
			_getEmployees(userId, -1, -1);
			if (this._rstSet.next()) {
				
			    ResultSetMetaData rsmd = this._rstSet.getMetaData();
			    int columns = rsmd.getColumnCount();
				 
				employee = new Employee(this._rstSet.getInt(6),
						this._rstSet.getInt(7), this._rstSet.getString(2),
						this._rstSet.getString(1), this._rstSet.getInt(3),
						this._rstSet.getString(5), this._rstSet.getString(4),
						this._rstSet.getLong(8), this._rstSet.getString(9),
						this._rstSet.getInt("ISACTIVE"));
				employee.setManagerId(this._rstSet.getInt("MANAGERID"));
				employee.setDeptEnabledOptionsMask(this._rstSet.getLong(17));
				employee.setEnabledOptionsMaskVIP(this._rstSet.getLong(18));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			super.safeClose(this._callStmt);
			super.releaseResources();
		}
		return employee;
	}
	
	private void _getEmployees(String userId, int departmentId, int isActive)
			throws Exception {
		_sqlStmt = "{ call dbo.IO_GetEmployees(?,?,?) }";
		super.initConn();
		_callStmt = _conn.prepareCall(_sqlStmt);
		if (userId == null) {
			_callStmt.setNull(1, Types.VARCHAR);
		} else {
			_callStmt.setString(1, userId);
		}
		_callStmt.setInt(2, departmentId);
		_callStmt.setInt(3, isActive);
		_rstSet = (ResultSet) _callStmt.executeQuery();
	}

}
