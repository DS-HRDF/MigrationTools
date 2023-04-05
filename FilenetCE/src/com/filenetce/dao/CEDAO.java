package com.filenetce.dao;

import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.LocalizedString;
import com.filenet.api.admin.PropertyDefinition;
import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.collection.PropertyDefinitionList;
import com.filenet.api.constants.Cardinality;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;
import com.filenet.apiimpl.core.PropertyTemplateBooleanImpl;
import com.filenet.apiimpl.core.PropertyTemplateImpl;
import com.filenet.apiimpl.core.PropertyTemplateStringImpl;
import com.filenetce.model.DocClassList;
import com.filenetce.model.SharedPropTemplate;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class CEDAO {
    static {
        System.setProperty("Djava.security.auth.login.config", "jaas.conf.WSI");
    }

    List<SharedPropTemplate> SharedPropTemplateArray = new ArrayList<SharedPropTemplate>();


    private ObjectStore objectStore = null;


    public void createClassFromJson(String classJson) {
        Gson gson = new Gson();
        DocClassList docClassList = gson.fromJson(classJson, DocClassList.class);


        docClassList.getDocClassList().stream().forEach(c -> {
            // GET PARENT CLASS


            try {
                ClassDefinition parentClass = Factory.ClassDefinition.fetchInstance(
                        getObjectStore(), c.getParentClass(), null);
                ClassDefinition subclass=null;

                try {
                    subclass = Factory.ClassDefinition.fetchInstance(
                            getObjectStore(), c.getCode(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (subclass==null){
                    subclass = parentClass.createSubclass();

                    subclass.set_SymbolicName(c.getCode());

                    // Set up locale
                    LocalizedString objLocStr = Factory.LocalizedString.createInstance();

                    objLocStr.set_LocaleName(getObjectStore().get_LocaleName());
                    objLocStr.set_LocalizedText(c.getLabel());
                    subclass.set_DisplayNames(Factory.LocalizedString.createList());
                    subclass.get_DisplayNames().add(objLocStr);
                }


                ClassDefinition finalSubclass = subclass;
                c.getTemplateList().stream().forEach(propTemplate ->
                {

                    try {
                        CreateAndAddProperty(getObjectStore(), finalSubclass, propTemplate.getCode(), propTemplate.getDataType());
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Failed to add property " + e.getMessage());
                    }

                });

                subclass.save(RefreshMode.REFRESH);

                System.out.println("subclass = " + subclass.get_Id().toString());
            } catch (Exception e) {
                System.err.println("Failed to add Document Class " + e.getMessage());

            }


        });


        // Create subclass of the Folder class


        // Create LocalizedStringList collection


    }


    public void initObjectStore() throws Exception {
        try {
            UserContext uc = UserContext.get();
            Properties prop = properties();

            String uri = prop.getProperty("uri");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            String os = prop.getProperty("os");

            // Get the connection
            Connection conn = Factory.Connection.getConnection(uri);

            // Build the subject using the FileNetP8WSI stanza
            uc.pushSubject(UserContext.createSubject(conn, username, password,
                    "FileNetP8WSI"));

            // Get the default domain
            Domain domain = Factory.Domain.getInstance(conn, null);
            // Get an object store
            objectStore = Factory.ObjectStore.fetchInstance(domain, os,
                    null);
        } catch (Exception e) {
            throw e;
        }
    }

    public ObjectStore getObjectStore() {
        if (objectStore == null) {
            try {
                initObjectStore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return objectStore;
    }


    public SharedPropTemplate get_Shared_PropertyDefinition(String name) {
        SharedPropTemplate data = new SharedPropTemplate();
        data.setName("-1");


        for (int i = 0; i < SharedPropTemplateArray.size(); i++) {
            if (SharedPropTemplateArray.get(i).getName().equals(name)) {
                data = SharedPropTemplateArray.get(i);

            }
        }


        return data;
    }

    public void CreateAndAddProperty(ObjectStore objStore, ClassDefinition classDef, String propertyName, String type) {

        PropertyDefinitionList propDefs = classDef.get_PropertyDefinitions();
        if (isExist(propertyName,propDefs))
            return;

        PropertyTemplate property = getProperty(propertyName);

        if (property == null) {

            PropertyTemplate newPropTemplate = null;//Factory.PropertyTemplateString.createInstance(objStore);

            if (type.equals("number")) {
                newPropTemplate = Factory.PropertyTemplateInteger32.createInstance(objStore);
                newPropTemplate.set_Cardinality(Cardinality.SINGLE);
            } else if (type.equals("text")) {
                newPropTemplate = Factory.PropertyTemplateString.createInstance(objStore);
                newPropTemplate.set_Cardinality(Cardinality.SINGLE);
            } else if (type.equals("boolean")) {
                newPropTemplate = Factory.PropertyTemplateBoolean.createInstance(objStore);
                newPropTemplate.set_Cardinality(Cardinality.SINGLE);
            } else if (type.equals("list")) {
                newPropTemplate = Factory.PropertyTemplateString.createInstance(objStore);
                newPropTemplate.set_Cardinality(Cardinality.LIST);
            }

            LocalizedString locStr = Factory.LocalizedString.createInstance();
            locStr.set_LocalizedText(propertyName);
            locStr.set_LocaleName(objStore.get_LocaleName());

            newPropTemplate.set_DisplayNames(Factory.LocalizedString.createList());
            newPropTemplate.get_DisplayNames().add(locStr);

            newPropTemplate.save(RefreshMode.REFRESH);
            System.out.println("Property Template " + propertyName + " has been created");


            SharedPropTemplateArray.add(new SharedPropTemplate(propertyName, newPropTemplate));

            PropertyDefinition newPropDef = newPropTemplate.createClassProperty();


            propDefs.add(newPropDef);


        } else {

//            PropertyDefinition newPropDef = property.createClassProperty();


            propDefs.add(property);

        }
        classDef.save(RefreshMode.REFRESH);

    }


    private Properties properties() {
        Properties prop = new Properties();
        try {

//            File jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//            String propertiesPath = jarPath.getParentFile().getAbsolutePath();
//            System.out.println(" propertiesPath-" + propertiesPath);

            prop.load(new FileInputStream("D:\\config.properties"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return prop;
    }

    private PropertyTemplate getProperty(String SymbolicName) {

        String select = "SymbolicName";
        String from = "PropertyTemplate";
        String where = "SymbolicName = '" + SymbolicName+"'";
        IndependentObjectSet ios = fetchIndependentObjectSet("this", from, where, true);
        Iterator<PropertyTemplate> itr = ios.iterator();
        String docIdsForLog = "";
        String docNamesForLog = "";
        if (itr.hasNext()) {
            return itr.next();
        }
        return null;
    }
//    java.lang.ClassCastException: com.filenet.apiimpl.core.PropertyTemplateBooleanImpl cannot be cast to
//    com.filenet.api.admin.PropertyDefinition
    private boolean isExist(String SymbolicName, PropertyDefinitionList propertyDefinitionList) {
//        for (int i = 0; i < propertyDefinitionList.size(); i++) {
//            PropertyDefinition property = (PropertyDefinition) propertyDefinitionList.get(i);
//            if (property.get_SymbolicName().equals(SymbolicName))
//                return true;
//        }
        return false;
    }

    private IndependentObjectSet fetchIndependentObjectSet(String select, String from, String where,
                                                           boolean includeSubClass) {
        IndependentObjectSet rrs = null;
        SearchSQL q = new SearchSQL();
        // q.setTimeLimit(20);
        SearchScope ss = new SearchScope(getObjectStore());
        if (select != null && !select.trim().equals(""))
            q.setSelectList(select);
        q.setFromClauseInitialValue(from, null, includeSubClass);
        if (where != null && !where.equals("")) {
            q.setWhereClause(where);
        }

        rrs = ss.fetchObjects(q, null, null, Boolean.TRUE);
        return rrs;
    }
}
