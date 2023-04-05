package com.dataserve.migration.spga.objects;

import javafx.beans.property.SimpleStringProperty;

/************************
 *
 * Created By Mohammad Awwad 02-June-2020
 *
 ************************/
public class MigrationData {
    private final SimpleStringProperty cn;
    private final SimpleStringProperty hy;
    private final SimpleStringProperty type;
    private final SimpleStringProperty docid;
    private final SimpleStringProperty fileName;
    private final SimpleStringProperty docTitle;
    private final SimpleStringProperty classId;
    private final SimpleStringProperty mimeType;
    private final SimpleStringProperty createDate;
    private final SimpleStringProperty username;
    private final SimpleStringProperty status;
    private final SimpleStringProperty message;
    private final SimpleStringProperty migTransactionId;
    private final SimpleStringProperty migDocId;


    public MigrationData(String cn, String hy, String type, String docid, String fileName, String docTitle, String classId, String mimeType,
                         String createDate, String username, String status, String message,String migTransactionId,
                         String migDocId) {
        this.cn = new SimpleStringProperty(cn);
        this.hy = new SimpleStringProperty(hy);
        this.type = new SimpleStringProperty(type);
        this.docid = new SimpleStringProperty(docid);
        this.fileName = new SimpleStringProperty(fileName);
        this.docTitle = new SimpleStringProperty(docTitle);
        this.classId = new SimpleStringProperty(classId);
        this.mimeType = new SimpleStringProperty(mimeType);
        this.createDate = new SimpleStringProperty(createDate);
        this.username = new SimpleStringProperty(username);
        this.status = new SimpleStringProperty(status);
        this.message = new SimpleStringProperty(message);
        this.migTransactionId = new SimpleStringProperty(migTransactionId);
        this.migDocId = new SimpleStringProperty(migDocId);
    }

    public String getCn() {
        return cn.get();
    }

    public SimpleStringProperty cnProperty() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn.set(cn);
    }

    public String getHy() {
        return hy.get();
    }

    public SimpleStringProperty hyProperty() {
        return hy;
    }

    public void setHy(String hy) {
        this.hy.set(hy);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getDocid() {
        return docid.get();
    }

    public SimpleStringProperty docidProperty() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid.set(docid);
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

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getCreateDate() {
        return createDate.get();
    }

    public SimpleStringProperty createDateProperty() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate.set(createDate);
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

    public String getClassId() {
        return classId.get();
    }

    public SimpleStringProperty classIdProperty() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId.set(classId);
    }

    public String getMimeType() {
        return mimeType.get();
    }

    public SimpleStringProperty mimeTypeProperty() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType.set(mimeType);
    }

    public String getDocTitle() {
        return docTitle.get();
    }

    public SimpleStringProperty docTitleProperty() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle.set(docTitle);
    }

    public String getMigTransactionId() {
        return migTransactionId.get();
    }

    public SimpleStringProperty migTransactionIdProperty() {
        return migTransactionId;
    }

    public void setMigTransactionId(String migTransactionId) {
        this.migTransactionId.set(migTransactionId);
    }

    public String getMigDocId() {
        return migDocId.get();
    }

    public SimpleStringProperty migDocIdProperty() {
        return migDocId;
    }

    public void setMigDocId(String migDocId) {
        this.migDocId.set(migDocId);
    }
}
