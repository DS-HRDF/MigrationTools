package com.dataserve.migration.spga.objects;

/************************
 *
 * Created By Mohammad Awwad 27-June-2020
 *
 ************************/
public class WorkitemObject {

    private String id;
    private String F_BoundUser;
//    private double F_CreateTime;
    private String F_Subject;
    private int CorrespondenceDate;
    private String FromUser;
    private int HijricYear;
    private int ImportanceLevel;
    private int isRead;
    private int ItemType;
    private String Originator;
    private String ReceivedOnHijriDate;
    private int SequenceNumber;
    private int UrgencyLevel;
    private int departmentID;
    private String queueName;
    private String username;
    private int forwardType;
    private String status;
    private String message;
    private int participantType;
    private int moNumber;
    private int correspondenceNumber;
    private int forwardingId;
    private String externalUnit;

    public static WorkitemObject getWorkItemData(WorkitemsData w) {


        return new WorkitemObject(w.getId(), w.getF_BoundUser(), Double.valueOf(0), w.getF_Subject(),
                Integer.valueOf(w.getCorrespondenceDate()), w.getFromUser(), Integer.valueOf(w.getHijricYear()),
                Integer.valueOf(w.getImportanceLevel()), Integer.valueOf(w.getIsRead()), Integer.valueOf(w.getItemType()),
                w.getOriginator(), w.getReceivedOnHijriDate(), Integer.valueOf(w.getSequenceNumber()),
                Integer.valueOf(w.getUrgencyLevel()), Integer.valueOf(w.getDepartmentID()), w.getQueueName(),
                w.getUsername(), Integer.valueOf(w.getForwardType()), w.getStatus(), w.getMessage(), Integer.valueOf(w.getParticipantType()),
                Integer.valueOf(w.getMoNumber()),Integer.valueOf(w.getCorrespondenceNumber()),Integer.valueOf(w.getForwardingId()),w.getExternalUnit());
    }

    public WorkitemObject(String id, String f_BoundUser, double f_CreateTime, String f_Subject, int correspondenceDate,
                          String fromUser, int hijricYear, int importanceLevel, int isRead, int itemType, String originator,
                          String receivedOnHijriDate, int sequenceNumber, int urgencyLevel, int departmentID, String queueName,
                          String username, int forwardType, String status, String message, int participantType,
                          int moNumber,int correspondenceNumber,int forwardingId,String externalUnit) {
        this.id = id;
        F_BoundUser = f_BoundUser;
//        F_CreateTime = f_CreateTime;
        F_Subject = f_Subject;
        CorrespondenceDate = correspondenceDate;
        FromUser = fromUser;
        HijricYear = hijricYear;
        ImportanceLevel = importanceLevel;
        this.isRead = isRead;
        ItemType = itemType;
        Originator = originator;
        ReceivedOnHijriDate = receivedOnHijriDate;
        SequenceNumber = sequenceNumber;
        UrgencyLevel = urgencyLevel;
        this.departmentID = departmentID;
        this.queueName = queueName;
        this.username = username;
        this.forwardType = forwardType;
        this.status = status;
        this.message = message;
        this.participantType = participantType;
        this.moNumber = moNumber;
        this.correspondenceNumber = correspondenceNumber;
        this.forwardingId = forwardingId;
        this.externalUnit = externalUnit;
    }

    public String getExternalUnit() {
        return externalUnit;
    }

    public void setExternalUnit(String externalUnit) {
        this.externalUnit = externalUnit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF_BoundUser() {
        return F_BoundUser;
    }

    public void setF_BoundUser(String f_BoundUser) {
        F_BoundUser = f_BoundUser;
    }

//    public double getF_CreateTime() {
//        return F_CreateTime;
//    }
//
//    public void setF_CreateTime(double f_CreateTime) {
//        F_CreateTime = f_CreateTime;
//    }

    public String getF_Subject() {
        return F_Subject;
    }

    public void setF_Subject(String f_Subject) {
        F_Subject = f_Subject;
    }

    public int getCorrespondenceDate() {
        return CorrespondenceDate;
    }

    public void setCorrespondenceDate(int correspondenceDate) {
        CorrespondenceDate = correspondenceDate;
    }

    public String getFromUser() {
        return FromUser;
    }

    public void setFromUser(String fromUser) {
        FromUser = fromUser;
    }

    public int getHijricYear() {
        return HijricYear;
    }

    public void setHijricYear(int hijricYear) {
        HijricYear = hijricYear;
    }

    public int getImportanceLevel() {
        return ImportanceLevel;
    }

    public void setImportanceLevel(int importanceLevel) {
        ImportanceLevel = importanceLevel;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getItemType() {
        return ItemType;
    }

    public void setItemType(int itemType) {
        ItemType = itemType;
    }

    public String getOriginator() {
        return Originator;
    }

    public void setOriginator(String originator) {
        Originator = originator;
    }

    public String getReceivedOnHijriDate() {
        return ReceivedOnHijriDate;
    }

    public void setReceivedOnHijriDate(String receivedOnHijriDate) {
        ReceivedOnHijriDate = receivedOnHijriDate;
    }

    public int getSequenceNumber() {
        return SequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        SequenceNumber = sequenceNumber;
    }

    public int getUrgencyLevel() {
        return UrgencyLevel;
    }

    public void setUrgencyLevel(int urgencyLevel) {
        UrgencyLevel = urgencyLevel;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getForwardType() {
        return forwardType;
    }

    public void setForwardType(int forwardType) {
        this.forwardType = forwardType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getParticipantType() {
        return participantType;
    }

    public void setParticipantType(int participantType) {
        this.participantType = participantType;
    }

    public int getMoNumber() {
        return moNumber;
    }

    public void setMoNumber(int moNumber) {
        this.moNumber = moNumber;
    }

    public int getCorrespondenceNumber() {
        return correspondenceNumber;
    }

    public void setCorrespondenceNumber(int correspondenceNumber) {
        this.correspondenceNumber = correspondenceNumber;
    }
    public int getForwardingId() {
        return forwardingId;
    }

    public void setForwardingId(int forwardingId) {
        this.forwardingId = forwardingId;
    }
}
