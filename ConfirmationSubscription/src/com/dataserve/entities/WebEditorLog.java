package com.dataserve.entities;


import java.util.Date;

public class WebEditorLog implements Comparable<WebEditorLog>{
	
	private int serial;
	private String vsid;
	private int actionId;
	private String actionName;
	private String userId;
	private String userFullname;
	private String documentId;
	private String documentSubject;
	private String actionTime;
	private long actionTimeInt;
	
	private int correspondenceNumber;
	private int hijricyear;
	private int correspondenceType;
	
	private Date actionDate;
	
	private String mimeType;
	private String deletedCnfirmationUser;
	private Integer confirmDeleted;
	
	
	public String getVsid() {
		return vsid;
	}
	public void setVsid(String vsid) {
		this.vsid = vsid;
	}
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserFullname() {
		return userFullname;
	}
	public void setUserFullname(String userFullname) {
		this.userFullname = userFullname;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getActionTime() {
		return actionTime;
	}
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	public long getActionTimeInt() {
		return actionTimeInt;
	}
	public void setActionTimeInt(long actionTimeInt) {
		this.actionTimeInt = actionTimeInt;
	}
	public String getDocumentSubject() {
		return documentSubject;
	}
	public void setDocumentSubject(String documentSubject) {
		this.documentSubject = documentSubject;
	}
	public int getCorrespondenceNumber() {
		return correspondenceNumber;
	}
	public void setCorrespondenceNumber(int correspondenceNumber) {
		this.correspondenceNumber = correspondenceNumber;
	}
	public int getHijricyear() {
		return hijricyear;
	}
	public void setHijricyear(int hijricyear) {
		this.hijricyear = hijricyear;
	}
	public int getCorrespondenceType() {
		return correspondenceType;
	}
	public void setCorrespondenceType(int correspondenceType) {
		this.correspondenceType = correspondenceType;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	
	@Override
	public boolean equals(Object arg0) {
		return ((WebEditorLog)arg0).getActionTimeInt() == this.getActionTimeInt();
	}

	@Override
	public int compareTo(WebEditorLog o) {
		return Long.compare(this.actionTimeInt,o.actionTimeInt);
		
	}
	public String getDeletedCnfirmationUser() {
		return deletedCnfirmationUser;
	}
	public void setDeletedCnfirmationUser(String deletedCnfirmationUser) {
		this.deletedCnfirmationUser = deletedCnfirmationUser;
	}
	public Integer getConfirmDeleted() {
		return confirmDeleted;
	}
	public void setConfirmDeleted(Integer confirmDeleted) {
		this.confirmDeleted = confirmDeleted;
	}
	
}
