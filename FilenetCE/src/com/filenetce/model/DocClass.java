package com.filenetce.model;

import java.io.Serializable;
import java.util.List;

public class DocClass implements Serializable {
    String parentClass;
    String code;
    String label;
    List<PropTemplate> templateList;

    public DocClass() {
    }


    public String getParentClass() {
        return parentClass;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<PropTemplate> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<PropTemplate> templateList) {
        this.templateList = templateList;
    }
}
