package com.filenetce.model;

import com.filenet.api.admin.PropertyDefinition;
import com.filenet.api.admin.PropertyDefinitionString;
import com.filenet.api.admin.PropertyTemplate;

import java.io.Serializable;

public class SharedPropTemplate implements Serializable {
    String name;
    PropertyTemplate propertyTemplate;

    public SharedPropTemplate(String name, PropertyTemplate propertyDefinitionString) {
        this.name = name;
        this.propertyTemplate = propertyDefinitionString;
    }

    public SharedPropTemplate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyTemplate getpropertyTemplate() {
        return propertyTemplate;
    }

    public void setpropertyTemplate(PropertyTemplate propertyDefinitionString) {
        this.propertyTemplate = propertyDefinitionString;
    }
}
