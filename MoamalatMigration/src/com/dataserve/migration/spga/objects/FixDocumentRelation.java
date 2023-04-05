package com.dataserve.migration.spga.objects;

public class FixDocumentRelation {
    private int deliveryReportNumber;
    private int cn;
    private int hy;
    private int typeid;
    private String docid;
    private String fileName;
    private String docTitle;
    private String classId;
    private String mimeType;

    public FixDocumentRelation(int cn, int hy, int typeid, String docid, String fileName, String docTitle, String classId, String mimeType) {
        this.cn = cn;
        this.hy = hy;
        this.typeid = typeid;
        this.docid = docid;
        this.fileName = fileName;
        this.docTitle = docTitle;
        this.classId = classId;
        this.mimeType = mimeType;
    }


    public FixDocumentRelation(int cn, int hy, int typeid, String docid) {
        this.cn = cn;
        this.hy = hy;
        this.typeid = typeid;
        this.docid = docid;
    }

    public FixDocumentRelation(int deliveryReportNumber, int cn, int hy, int typeid, String docid, String fileName, String docTitle, String classId, String mimeType) {
        this.deliveryReportNumber = deliveryReportNumber;
        this.cn = cn;
        this.hy = hy;
        this.typeid = typeid;
        this.docid = docid;
        this.fileName = fileName;
        this.docTitle = docTitle;
        this.classId = classId;
        this.mimeType = mimeType;
    }

    public int getCn() {
        return cn;
    }

    public void setCn(int cn) {
        this.cn = cn;
    }

    public int getHy() {
        return hy;
    }

    public void setHy(int hy) {
        this.hy = hy;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getDeliveryReportNumber() {
        return deliveryReportNumber;
    }

    public void setDeliveryReportNumber(int deliveryReportNumber) {
        this.deliveryReportNumber = deliveryReportNumber;
    }
}
