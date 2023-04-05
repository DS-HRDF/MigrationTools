package com.dataserve.migration.spga.objects;


import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

public class ForwardingInfo {

    private String token;
    private String correspondenceId;
    private String displayNumber;
    private int correspondenceTypeId = -1;
    private int correspondenceNo = -1;
    private int hijricYear = -1;
    private String instructions;
    private String wobNum;
    private String lang;
    private String closeCause;
    private int reminderPeriod = -1;
    private int reminderType = -1; // 1,2,3,4 Minute,Hour,Day,Month
    private int deadLinePeriod = -1;
    private int deadLineType = -1;// 1,2,3,4 Minute,Hour,Day,Month
    private int urgencyType = 0; // 0,1,2 Normal,Urgent,Very Urgent
    private boolean important;
    private boolean followUp;
    private boolean requestReadResponse;
    private String queueName;
    private String physicalAttachment;
    private String importantStr;
    private String followUpStr;
    private String requestReadResponseStr;
    private String followUpFromDate;
    private String followUpToDate;
    private int actionId = 0;
    private String[] toUsers;
    private String[] ccUsers;
    private String[] toDepartments;
    private String[] ccDepartments;
    private String[] wobNums;
    private UserInstructions[] usersInstruction;
    private Hashtable<String, String> usersInstructions;
    private boolean withdrawAction;
    private int presentAction;
    private int confirmSend;

    public ForwardingInfo() {
        super();
    }

    public ForwardingInfo(String token, String correspondenceId,
                          int correspondenceTypeId, String instructions, String wobNum,
                          String lang, int reminderPeriod, int reminderType,
                          int deadLinePeriod, int deadLineType, int urgencyType,
                          boolean important, boolean followUp, boolean requestReadResponse,
                          String[] toUsers, String[] ccUsers, String[] toDepartments,
                          String[] ccDepartments, Hashtable<String, String> usersInstructions) {
        super();
        this.token = token;
        this.correspondenceId = correspondenceId;
        this.correspondenceTypeId = correspondenceTypeId;
        this.instructions = instructions;
        this.wobNum = wobNum;
        this.lang = lang;
        this.reminderPeriod = reminderPeriod;
        this.reminderType = reminderType;
        this.deadLinePeriod = deadLinePeriod;
        this.deadLineType = deadLineType;
        this.urgencyType = urgencyType;
        this.important = important;
        this.followUp = followUp;
        this.requestReadResponse = requestReadResponse;
        this.toUsers = toUsers;
        this.ccUsers = ccUsers;
        this.toDepartments = toDepartments;
        this.ccDepartments = ccDepartments;
        this.usersInstructions = usersInstructions;
    }

    public ForwardingInfo(String token, String correspondenceId,
                          int correspondenceTypeId, String instructions, String lang,
                          String[] toUsers, Hashtable<String, String> usersInstructions) {
        super();
        this.token = token;
        this.correspondenceId = correspondenceId;
        this.correspondenceTypeId = correspondenceTypeId;
        this.instructions = instructions;
        this.lang = lang;
        this.toUsers = toUsers;
        this.usersInstructions = usersInstructions;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCorrespondenceId() {
        return correspondenceId;
    }

    public void setCorrespondenceId(String correspondenceId) {
        this.correspondenceId = correspondenceId;
    }

    public int getCorrespondenceTypeId() {
        return correspondenceTypeId;
    }

    public void setCorrespondenceTypeId(int correspondenceTypeId) {
        this.correspondenceTypeId = correspondenceTypeId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getWobNum() {
        return wobNum;
    }

    public void setWobNum(String wobNum) {
        this.wobNum = wobNum;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String[] getToUsers() {
        return toUsers;
    }

    public void setToUsers(String[] toUsers) {
        this.toUsers = toUsers;
    }

    public String[] getCcUsers() {
        return ccUsers;
    }

    public void setCcUsers(String[] ccUsers) {
        this.ccUsers = ccUsers;
    }

    public String[] getToDepartments() {
        return toDepartments;
    }

    public void setToDepartments(String[] toDepartments) {
        this.toDepartments = toDepartments;
    }

    public String[] getCcDepartments() {
        return ccDepartments;
    }

    public void setCcDepartments(String[] ccDepartments) {
        this.ccDepartments = ccDepartments;
    }

    public int getReminderPeriod() {
        return reminderPeriod;
    }

    public void setReminderPeriod(int reminderPeriod) {
        this.reminderPeriod = reminderPeriod;
    }

    public int getReminderType() {
        return reminderType;
    }

    public void setReminderType(int reminderType) {
        this.reminderType = reminderType;
    }

    public int getDeadLinePeriod() {
        return deadLinePeriod;
    }

    public void setDeadLinePeriod(int deadLinePeriod) {
        this.deadLinePeriod = deadLinePeriod;
    }

    public int getDeadLineType() {
        return deadLineType;
    }

    public void setDeadLineType(int deadLineType) {
        this.deadLineType = deadLineType;
    }

    public int getUrgencyType() {
        return urgencyType;
    }

    public void setUrgencyType(int urgencyType) {
        this.urgencyType = urgencyType;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isFollowUp() {
        return followUp;
    }

    public void setFollowUp(boolean followUp) {
        this.followUp = followUp;
    }

    public boolean isRequestReadResponse() {
        return requestReadResponse;
    }

    public void setRequestReadResponse(boolean requestReadResponse) {
        this.requestReadResponse = requestReadResponse;
    }

    public int getCorrespondenceNo() {
        return correspondenceNo;
    }

    public int getHijricYear() {
        return hijricYear;
    }

    public String[] getWobNums() {
        return wobNums;
    }

    public void setWobNums(String[] wobNums) {
        this.wobNums = wobNums;
    }

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public void setImportantStr(String importantStr) {
        this.importantStr = importantStr;

        important = !StringUtils.isEmpty(importantStr)
                && importantStr.trim().equalsIgnoreCase("yes") ? true : false;
    }

    public void setFollowUpStr(String followUpStr) {
        this.followUpStr = followUpStr;

        followUp = !StringUtils.isEmpty(followUpStr)
                && followUpStr.trim().equalsIgnoreCase("yes") ? true : false;
    }

    public void setRequestReadResponseStr(String requestReadResponseStr) {
        this.requestReadResponseStr = requestReadResponseStr;

        requestReadResponse = !StringUtils.isEmpty(requestReadResponseStr)
                && requestReadResponseStr.trim().equalsIgnoreCase("yes") ? true
                : false;
    }

    public Hashtable<String, String> getUsersInstructions() {
        return usersInstructions;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setUsersInstructions(Hashtable<String, String> usersInstructions) {
        this.usersInstructions = usersInstructions;
    }

    public UserInstructions[] getUsersInstruction() {
        return usersInstruction;
    }

    public void setUsersInstruction(UserInstructions[] usersInstruction) {
        this.usersInstruction = usersInstruction;
    }

    public String getPhysicalAttachment() {
        return physicalAttachment;
    }

    public void setPhysicalAttachment(String physicalAttachment) {
        this.physicalAttachment = physicalAttachment;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public boolean isWithdrawAction() {
        return withdrawAction;
    }

    public void setWithdrawAction(boolean withdrawAction) {
        this.withdrawAction = withdrawAction;
    }

    public String getFollowUpFromDate() {
        return followUpFromDate;
    }

    public void setFollowUpFromDate(String followUpFromDate) {
        this.followUpFromDate = followUpFromDate;
    }

    public String getImportantStr() {
        return importantStr;
    }

    public String getFollowUpStr() {
        return followUpStr;
    }

    public String getRequestReadResponseStr() {
        return requestReadResponseStr;
    }

    public void setCorrespondenceNo(int correspondenceNo) {
        this.correspondenceNo = correspondenceNo;
    }

    public void setHijricYear(int hijricYear) {
        this.hijricYear = hijricYear;
    }

    public int getPresentAction() {
        return presentAction;
    }

    public void setPresentAction(int presentAction) {
        this.presentAction = presentAction;
    }

    public int getConfirmSend() {
        return confirmSend;
    }

    public void setConfirmSend(int confirmSend) {
        this.confirmSend = confirmSend;
    }

//    public String getDisplayNumber() {
//        return displayNumber;
//    }
//
//    public void setDisplayNumber(String displayNumber) {
//        this.displayNumber = displayNumber;
//    }


}
