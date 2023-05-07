package com.dataserve.migration.spga.objects;

import javafx.beans.property.SimpleStringProperty;

/************************
 *
 * Created By Mohammad Awwad 02-June-2020
 *
 ************************/
public class WorkitemsData {

    private final SimpleStringProperty id;
    private final SimpleStringProperty F_BoundUser;
//    private final SimpleStringProperty F_CreateTime;
    private final SimpleStringProperty F_Subject;
    private final SimpleStringProperty CorrespondenceDate;
    private final SimpleStringProperty FromUser;
    private final SimpleStringProperty HijricYear;
    private final SimpleStringProperty ImportanceLevel;
    private final SimpleStringProperty isRead;
    private final SimpleStringProperty ItemType;
    private final SimpleStringProperty Originator;
    private final SimpleStringProperty ReceivedOnHijriDate;
    private final SimpleStringProperty SequenceNumber;
    private final SimpleStringProperty UrgencyLevel;
    private final SimpleStringProperty departmentID;
    private final SimpleStringProperty queueName;
    private final SimpleStringProperty username;
    private final SimpleStringProperty forwardType;
    private final SimpleStringProperty status;
    private final SimpleStringProperty message;
    private final SimpleStringProperty participantType;
    private final SimpleStringProperty moNumber;
    private final SimpleStringProperty correspondenceNumber;
    private final SimpleStringProperty forwardingId;
    private final SimpleStringProperty externalUnit;

    public WorkitemsData(String id, String f_BoundUser, String f_CreateTime, String f_Subject, String correspondenceDate,
                         String fromUser, String hijricYear, String importanceLevel, String isRead, String itemType, 
                         String originator, String receivedOnHijriDate, String sequenceNumber, String urgencyLevel, 
                         String departmentID, String queueName, String username, String forwardType, String status, 
                         String message, String participantType,String moNumber,String correspondenceNumber,
                         String forwardingId,String externalUnit) {
        this.id = new SimpleStringProperty(id);
        F_BoundUser = new SimpleStringProperty(f_BoundUser);
//        F_CreateTime = new SimpleStringProperty(f_CreateTime);
        F_Subject = new SimpleStringProperty(f_Subject);
        CorrespondenceDate = new SimpleStringProperty(correspondenceDate);
        FromUser = new SimpleStringProperty(fromUser);
        HijricYear = new SimpleStringProperty(hijricYear);
        ImportanceLevel = new SimpleStringProperty(importanceLevel);
        this.isRead = new SimpleStringProperty(isRead);
        ItemType = new SimpleStringProperty(itemType);
        Originator = new SimpleStringProperty(originator);
        ReceivedOnHijriDate = new SimpleStringProperty(receivedOnHijriDate);
        SequenceNumber = new SimpleStringProperty(sequenceNumber);
        UrgencyLevel = new SimpleStringProperty(urgencyLevel);
        this.departmentID = new SimpleStringProperty(departmentID);
        this.queueName = new SimpleStringProperty(queueName);
        this.username = new SimpleStringProperty(username);
        this.forwardType = new SimpleStringProperty(forwardType);
        this.status = new SimpleStringProperty(status);
        this.message = new SimpleStringProperty(message);
        this.participantType = new SimpleStringProperty(participantType);
        this.moNumber = new SimpleStringProperty(moNumber);
        this.correspondenceNumber = new SimpleStringProperty(correspondenceNumber);
        this.externalUnit = new SimpleStringProperty(externalUnit);
        this.forwardingId = new SimpleStringProperty(forwardingId);
    }

    public String getExternalUnit() {
        return externalUnit.get();
    }

    public SimpleStringProperty externalUnitProperty() {
        return externalUnit;
    }

    public void setExternalUnit(String externalUnit) {
        this.externalUnit.set(externalUnit);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getF_BoundUser() {
        return F_BoundUser.get();
    }

    public SimpleStringProperty f_BoundUserProperty() {
        return F_BoundUser;
    }

    public void setF_BoundUser(String f_BoundUser) {
        this.F_BoundUser.set(f_BoundUser);
    }

//    public String getF_CreateTime() {
//        return F_CreateTime.get();
//    }
//
//    public SimpleStringProperty f_CreateTimeProperty() {
//        return F_CreateTime;
//    }
//
//    public void setF_CreateTime(String f_CreateTime) {
//        this.F_CreateTime.set(f_CreateTime);
//    }

    public String getF_Subject() {
        return F_Subject.get();
    }

    public SimpleStringProperty f_SubjectProperty() {
        return F_Subject;
    }

    public void setF_Subject(String f_Subject) {
        this.F_Subject.set(f_Subject);
    }

    public String getCorrespondenceDate() {
        return CorrespondenceDate.get();
    }

    public SimpleStringProperty correspondenceDateProperty() {
        return CorrespondenceDate;
    }

    public void setCorrespondenceDate(String correspondenceDate) {
        this.CorrespondenceDate.set(correspondenceDate);
    }

    public String getFromUser() {
        return FromUser.get();
    }

    public SimpleStringProperty fromUserProperty() {
        return FromUser;
    }

    public void setFromUser(String fromUser) {
        this.FromUser.set(fromUser);
    }

    public String getHijricYear() {
        return HijricYear.get();
    }

    public SimpleStringProperty hijricYearProperty() {
        return HijricYear;
    }

    public void setHijricYear(String hijricYear) {
        this.HijricYear.set(hijricYear);
    }

    public String getImportanceLevel() {
        return ImportanceLevel.get();
    }

    public SimpleStringProperty importanceLevelProperty() {
        return ImportanceLevel;
    }

    public void setImportanceLevel(String importanceLevel) {
        this.ImportanceLevel.set(importanceLevel);
    }

    public String getIsRead() {
        return isRead.get();
    }

    public SimpleStringProperty isReadProperty() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead.set(isRead);
    }

    public String getItemType() {
        return ItemType.get();
    }

    public SimpleStringProperty itemTypeProperty() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        this.ItemType.set(itemType);
    }

    public String getOriginator() {
        return Originator.get();
    }

    public SimpleStringProperty originatorProperty() {
        return Originator;
    }

    public void setOriginator(String originator) {
        this.Originator.set(originator);
    }

    public String getReceivedOnHijriDate() {
        return ReceivedOnHijriDate.get();
    }

    public SimpleStringProperty receivedOnHijriDateProperty() {
        return ReceivedOnHijriDate;
    }

    public void setReceivedOnHijriDate(String receivedOnHijriDate) {
        this.ReceivedOnHijriDate.set(receivedOnHijriDate);
    }

    public String getSequenceNumber() {
        return SequenceNumber.get();
    }

    public SimpleStringProperty sequenceNumberProperty() {
        return SequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.SequenceNumber.set(sequenceNumber);
    }

    public String getUrgencyLevel() {
        return UrgencyLevel.get();
    }

    public SimpleStringProperty urgencyLevelProperty() {
        return UrgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.UrgencyLevel.set(urgencyLevel);
    }

    public String getDepartmentID() {
        return departmentID.get();
    }

    public SimpleStringProperty departmentIDProperty() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID.set(departmentID);
    }

    public String getQueueName() {
        return queueName.get();
    }

    public SimpleStringProperty queueNameProperty() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName.set(queueName);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getForwardType() {
        return forwardType.get();
    }

    public SimpleStringProperty forwardTypeProperty() {
        return forwardType;
    }

    public void setForwardType(String forwardType) {
        this.forwardType.set(forwardType);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getMessage() {
        return message.get();
    }

    public SimpleStringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public String getParticipantType() {
        return participantType.get();
    }

    public SimpleStringProperty participantTypeProperty() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
        this.participantType.set(participantType);
    }

    public String getMoNumber() {
        return moNumber.get();
    }

    public SimpleStringProperty moNumberProperty() {
        return moNumber;
    }

    public void setMoNumber(String moNumber) {
        this.moNumber.set(moNumber);
    }

    public String getCorrespondenceNumber() {
        return correspondenceNumber.get();
    }

    public SimpleStringProperty correspondenceNumberProperty() {
        return correspondenceNumber;
    }

    public void setCorrespondenceNumber(String correspondenceNumber) {
        this.correspondenceNumber.set(correspondenceNumber);
    }
    public String getForwardingId() {
        return forwardingId.get();
    }

    public SimpleStringProperty forwardingIdProperty() {
        return forwardingId;
    }

    public void setForwardingId(String forwardingId) {
        this.forwardingId.set(forwardingId);
    }
}
