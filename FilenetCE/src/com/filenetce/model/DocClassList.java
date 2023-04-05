package com.filenetce.model;

import java.io.Serializable;
import java.util.List;

public class DocClassList implements Serializable {
    List<DocClass> docClassList;

    public DocClassList() {
    }

    public List<DocClass> getDocClassList() {
        return docClassList;
    }

    public void setDocClassList(List<DocClass> docClassList) {
        this.docClassList = docClassList;
    }
}
