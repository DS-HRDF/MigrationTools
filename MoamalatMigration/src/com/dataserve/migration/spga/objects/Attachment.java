package com.dataserve.migration.spga.objects;

/************************
 *
 * Created By Mohammad Awwad 02-June-2020
 *
 ************************/
public class Attachment {
    private byte[] bytes;
    private String mimetype;
    private String sahlDocId;

    public Attachment(byte[] bytes, String mimetype) {
        this.bytes = bytes;
        this.mimetype = mimetype;
    }

    public Attachment(byte[] bytes, String mimetype, String sahlDocId) {
        this.bytes = bytes;
        this.mimetype = mimetype;
        this.sahlDocId = sahlDocId;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getSahlDocId() {
        return sahlDocId;
    }

    public void setSahlDocId(String sahlDocId) {
        this.sahlDocId = sahlDocId;
    }
}
