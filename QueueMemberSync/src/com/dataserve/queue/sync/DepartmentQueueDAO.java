package com.dataserve.queue.sync;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;


public class DepartmentQueueDAO extends AbstractDAO {
	public DepartmentQueueDAO() throws NamingException, SQLException {
	}

	public List<DepartmentQueue> getDepartmentQueuesList() throws Exception {
		return getDepartmentQueuesList(-1, null);
	}

	public DepartmentQueue getDepartmentQueue(int departmentId)
			throws Exception {
		List lstDepartmentQueues = getDepartmentQueuesList(departmentId, null);
		if (lstDepartmentQueues.size() == 0) {
			return null;
		}
		return (DepartmentQueue) lstDepartmentQueues.get(0);
	}

	public DepartmentQueue getDepartmentQueue(String queueName)
			throws Exception {
		List lstDepartmentQueues = getDepartmentQueuesList(-1, queueName);
		if (lstDepartmentQueues.size() == 0) {
			return null;
		}
		return (DepartmentQueue) lstDepartmentQueues.get(0);
	}

	public boolean isDepartmentQueue(int departmentId) throws Exception {
		return getDepartmentQueuesList(departmentId, null).size() > 0;
	}

	public boolean isDepartmentQueue(String queueName) throws Exception {
		return getDepartmentQueuesList(-1, queueName).size() > 0;
	}


	public List<DepartmentQueue> getDepartmentQueuesList(int departmentId,
			String queueName) throws Exception {
		List lstDepartmentQueues = new ArrayList();

		this._sqlStmt = "{ call MOAMALAT1.dbo.IO_GetDepartmentQueues(?,?) }";
		
		try {
			super.initConn();
			this._callStmt = this._conn.prepareCall(this._sqlStmt);

			this._callStmt.setInt(1, departmentId);

			if (queueName == null)
				this._callStmt.setNull(2, 12);
			else {
				this._callStmt.setString(2, queueName);
			}

			_rstSet = (ResultSet) _callStmt.executeQuery();
			while (this._rstSet.next()) {
				DepartmentQueue departmentQueue = new DepartmentQueue(
						this._rstSet.getInt(1), this._rstSet.getString(2),
						this._rstSet.getString(3), this._rstSet.getString(4),
						this._rstSet.getString(5), this._rstSet.getString(6),
						this._rstSet.getString(7), this._rstSet.getString(8),
						this._rstSet.getString(9)

				);
				lstDepartmentQueues.add(departmentQueue);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			super.safeClose(this._callStmt);
			super.releaseResources();
		}
		return lstDepartmentQueues;
	}



	public boolean addDepartmentQueues(DepartmentQueue departmentQueue)
			throws Exception {

		_sqlStmt = "{ call IO_ICN_AddDepartmentQueues(?,?,?,?,?,?,?,?,?,?) }";

		try

		{

			super.initConn();
			String queueName = departmentQueue.getQueueName();
			String workQueues = departmentQueue.getWorkQueues();
			String queueShortName = departmentQueue.getQueueShortName();
			_callStmt = _conn.prepareCall(_sqlStmt);
			_callStmt.setInt(1, departmentQueue.getDepartmentId());
			_callStmt.setString(2, departmentQueue.getDepartmentNameAr());
			_callStmt.setString(3, departmentQueue.getDepartmentNameEn());
			_callStmt.setString(4, queueName != null ? queueName.trim() : queueName);
			_callStmt.setString(5, queueShortName != null ? queueShortName.trim() : queueShortName);
			_callStmt.setString(6, departmentQueue.getApplicationSpaceName());
			_callStmt.setString(7, departmentQueue.getRoleName_English());
			_callStmt.setString(8, departmentQueue.getRoleName_Arabic());
			_callStmt.setString(9, workQueues != null ? workQueues.trim() : workQueues );
			_callStmt.registerOutParameter(10, Types.INTEGER);
			_callStmt.executeUpdate();

			int result = _callStmt.getInt(10);



			return (result > 0);

		}

		catch (SQLException e)

		{

			throw e;

		}

		finally

		{

			super.safeClose(_callStmt);

			super.releaseResources();

		}

		// TODO Auto-generated method stub

	}

	public boolean updateDepartmentQueues(DepartmentQueue departmentQueue)
			throws Exception {
		// TODO Auto-generated method stub
		{
			int result = -1;

			_sqlStmt = ("{ call IO_UpdateDepartmentQueues(?,?,?,?,?,?,?,?,?,?) }");

			try

			{

				super.initConn();

				_callStmt = _conn.prepareCall(_sqlStmt);

				_callStmt.registerOutParameter(1, Types.INTEGER);

				_callStmt.setInt(2, departmentQueue.getDepartmentId());

				_callStmt.setString(3, departmentQueue.getDepartmentNameAr());

				_callStmt.setString(4, departmentQueue.getDepartmentNameEn());

				_callStmt.setString(5, departmentQueue.getQueueName());

				_callStmt.setString(6, departmentQueue.getQueueShortName());
				_callStmt.setString(7,
						departmentQueue.getApplicationSpaceName());
				_callStmt.setString(8, departmentQueue.getRoleName_English());
				_callStmt.setString(9, departmentQueue.getRoleName_Arabic());
				_callStmt.setString(10, departmentQueue.getWorkQueues());

				_callStmt.executeUpdate();

				result = _callStmt.getInt(1);

				return (result > 0);

			}

			catch (SQLException e)

			{

				throw e;

			}

			finally

			{

				super.safeClose(_callStmt);

				super.releaseResources();

			}

		}

	}
	
	public boolean addDepartmentQueuesUsers(int departmentId,String UserId)
			throws Exception {

		_sqlStmt = "{ call ADD_USERS_QUEUES(?,?,?) }";

		try

		{

			super.initConn();
		
			_callStmt = _conn.prepareCall(_sqlStmt);
			_callStmt.setInt(1, departmentId);
			_callStmt.setString(2, UserId);
			_callStmt.registerOutParameter(3, Types.INTEGER);
			_callStmt.execute();

			int result = _callStmt.getInt(3);



			return (result > 0);

		}

		catch (SQLException e)

		{

			throw e;

		}

		finally

		{

			super.safeClose(_callStmt);

			super.releaseResources();

		}

		// TODO Auto-generated method stub

	}
	


}
