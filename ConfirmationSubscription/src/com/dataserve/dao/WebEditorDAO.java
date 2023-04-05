package com.dataserve.dao;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.dataserve.entities.WebEditorLog;

public class WebEditorDAO extends AbstractDAO {

	public static final int LETTER_TYPE_ORIGINAL = 1;

	public WebEditorDAO() throws NamingException, SQLException {
	}

	public boolean addWebEditorLog(String vsid, int actionId, String userId, String documentId, String documentSubject,
			int correspondencenumber, int hijricyear, int correspondenceType, String actionTime, long actionTimeInt,
			String mimeType,String deletedCnfirmationUser,int confirmDeleted) {

		WebEditorLog log = new WebEditorLog();
		log.setVsid(vsid);
		log.setActionId(actionId);
		log.setUserId(userId);
		log.setDocumentSubject(documentSubject);
		log.setDocumentId(documentId);
		log.setCorrespondenceNumber(correspondencenumber);
		log.setHijricyear(hijricyear);
		log.setCorrespondenceType(correspondenceType);
		log.setActionTime(actionTime);
		log.setActionTimeInt(actionTimeInt);
		log.setMimeType(mimeType);
		log.setDeletedCnfirmationUser(deletedCnfirmationUser);
		log.setConfirmDeleted(confirmDeleted);

		return addWebEditorLog(log);

	}

	public boolean addWebEditorLog(WebEditorLog webEditorLog){

		this._sqlStmt = "{ call  dbo.ADD_WEBEDITOR_LOG(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

		try {

			super.initConn();

			this._callStmt = this._conn.prepareCall(this._sqlStmt);

			this._callStmt.setString(1, webEditorLog.getVsid());
			this._callStmt.setInt(2, webEditorLog.getActionId());
			this._callStmt.setString(3, webEditorLog.getUserId());
			this._callStmt.setString(4, webEditorLog.getDocumentId());
			this._callStmt.setString(5, webEditorLog.getDocumentSubject());

			this._callStmt.setInt(6, webEditorLog.getCorrespondenceNumber());
			this._callStmt.setInt(7, webEditorLog.getHijricyear());
			this._callStmt.setInt(8, webEditorLog.getCorrespondenceType());

			this._callStmt.setString(9, webEditorLog.getActionTime());
			this._callStmt.setLong(10, webEditorLog.getActionTimeInt());
			this._callStmt.setString(11, webEditorLog.getMimeType());
			this._callStmt.setString(12, webEditorLog.getDeletedCnfirmationUser());
			if (webEditorLog.getConfirmDeleted() == null)
				this._callStmt.setNull(13, 4);
			else
				this._callStmt.setInt(13, webEditorLog.getConfirmDeleted());

			int affected = _callStmt.executeUpdate();

			return affected > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
			//throw e;
		} finally {
			super.safeClose(this._callStmt);
			super.releaseResources();
		}
	}

}
