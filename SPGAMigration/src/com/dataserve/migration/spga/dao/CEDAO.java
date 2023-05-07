package com.dataserve.migration.spga.dao;

import com.dataserve.migration.spga.objects.MigrationData;
import com.dataserve.migration.spga.util.Const;
import com.dataserve.migration.spga.util.SystemConstants;
import com.dataserve.migration.spga.util.Utils;
import com.filenet.api.collection.ContentElementList;
import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.collection.UserSet;
import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import com.filenet.api.property.Properties;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.security.Realm;
import com.filenet.api.util.UserContext;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import static com.dataserve.migration.spga.util.Utils.threadID;

/************************
 *
 * Created By Mohammad Awwad 02-June-2020
 *
 ************************/

public class CEDAO {

    static {
        System.setProperty("Djava.security.auth.login.config", "jaas.conf.WSI");
    }

    final static Logger logger = Logger.getLogger(CEDAO.class);

    private ObjectStore objectStore = null;
    private boolean connected = false;

//    DataDAO dataDAO = null;
    private int ceConnection;


    public CEDAO() {

    }


    public String addDocument(MigrationData c, byte[] fileBytes, String mimeType) throws Exception {


        Integer correspondenceNo = Integer.valueOf(c.getCn());
        Integer typeId = Integer.valueOf(c.getType());
        Integer year = Integer.valueOf(c.getHy());
        Integer migTransaction = Integer.valueOf(c.getDocid());

        ObjectStore os = getObjectStore();
        System.out.println("get_Creator <======> "+os.get_Creator());
        Document doc = null;
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(c.getCreateDate());
        System.out.println("getUsername ======> "+c.getUsername());
        System.out.println("getClassId ======> "+c.getClassId());
        System.out.println("fileBytes ======> "+fileBytes.length);
        System.out.println("date1 ======> "+date1);
        doc = createChildDoc(c, c.getUsername(), date1, c.getClassId(), fileBytes, mimeType);
//        c.setMimeType(mimeType);
        boolean success = addCorrespondenceDocument(os, correspondenceNo, year, typeId, doc.get_Id() + "",
                c.getUsername(), date1,c.getDocid(),migTransaction);

        System.out.println("doc.get_Id().toString() ======> "+doc.get_Id().toString());
        return  doc.get_Id().toString();
    }

    private String fixTitle(String title) {
        return title.replace("*", "").replace("?", "");
    }

    private final String[] DEFAULT_ALLOWED_MIMETYPES = new String[]{"application/x-tika-ooxml", "image/png", "image/jpeg", "image/pjpeg",
            "image/pngimage/tiff", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/msword", "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/vnd.ms-access",
            "application/pdf"};
    
    private Document createChildDoc(MigrationData c, String userid, Date creationDate, String classId, byte[] fileBytes,String mimeType) throws Exception {
        try {
//        	System.out.println(" createChildDoc get_MimeType ======> "+c.getMimeType());
            Document childDoc = Factory.Document.createInstance(getObjectStore(), classId);
//            System.out.println("get_MimeType ======> "+childDoc.get_MimeType());
//            Path path = new File(c.getFileName()).toPath();
//            String mimeType = Files.probeContentType(path);
//            if (StringUtils.isEmpty(mimeType)) {
//                mimeType = "application/pdf";
//            }
//            childDoc.set_MimeType(c.getMimeType());
            childDoc.set_MimeType(mimeType);
//            childDoc.getProperties().putValue("DocumentTitle", fixTitle(c.getFileName()));
//            childDoc.getProperties().putValue("DocumentTitle", c.getFileName());
            childDoc.getProperties().putValue("DocumentTitle", c.getDocTitle());
            childDoc.getProperties().putValue("CorrespondenceNumber", Integer.valueOf(c.getCn()));
            childDoc.getProperties().putValue("CorrespondenceType", Integer.valueOf(c.getType()));
            childDoc.getProperties().putValue("HijricYear", Integer.valueOf(c.getHy()));
            childDoc.getProperties().putValue("IsMigrated", Boolean.TRUE);
//            childDoc.getProperties().putValue("MigOldDocId", c.getDocid());
            childDoc.getProperties().putValue("MigratedDocId", Integer.valueOf(c.getDocid()));

//            childDoc.getProperties().putValue("DisplayNumber", c.getCn());

            childDoc.set_Creator(userid);
            childDoc.set_LastModifier(userid);
            childDoc.set_DateCreated(creationDate);
            childDoc.set_DateLastModified(creationDate);

            ContentElementList cel = createSingleContentElements(new ByteArrayInputStream(fileBytes),
//                    fixTitle(c.getFileName()), c.getMimeType());
//            fixTitle(c.getFileName()), c.getMimeType());
            c.getFileName(), c.getMimeType());
            if (cel != null) {
                childDoc.set_ContentElements(cel);
            }
            childDoc.checkin(null, CheckinType.MAJOR_VERSION);
            childDoc.save(RefreshMode.REFRESH);
            return childDoc;
        } catch (Exception e) {
            logger.error(e, e);
            throw e;
        }

    }

//    private Document createCompoundDocuemnt(MigrationData c) {
//        try {
//
//            Document compoundDoc;
//            compoundDoc = Factory.Document.createInstance(getObjectStore(), "CorrespondenceInfo");
//            compoundDoc.set_CompoundDocumentState(CompoundDocumentState.COMPOUND_DOCUMENT);
//
//            compoundDoc.getProperties().putValue("DocumentTitle", fixTitle(c.getDocTitle()));
//            compoundDoc.getProperties().putValue("CorrespondenceNumber", Integer.valueOf(c.getCn()));
//            compoundDoc.getProperties().putValue("CorrespondenceType", Integer.valueOf(c.getType()));
//            compoundDoc.getProperties().putValue("HijricYear", Integer.valueOf(c.getHy()));
//            compoundDoc.getProperties().putValue("IsMigrated", Integer.valueOf(1));
//            compoundDoc.getProperties().putValue("MigTransactionId", c.getMigTransactionId());
////            compoundDoc.getProperties().putValue("DisplayNumber", c.getCn());
//
//            compoundDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
//            compoundDoc.save(RefreshMode.REFRESH);
//
//            return compoundDoc;
//        } catch (Exception e) {
//            logger.error(e, e);
//        }
//        return null;
//    }

    public void initObjectStore() throws Exception {
        try {
            UserContext uc = UserContext.get();

            logger.info(" -----------initObjectStore--------------- ");
            String uri = "";
            uri = Utils.getConfigs("uri.prod");
            String username = Utils.getConfigs("username.prod");
            String password = Utils.getConfigs("password.prod");

            // Get the connection
            Connection conn = Factory.Connection.getConnection(uri);
System.out.println("conn ======> "+conn);
            // Build the subject using the FileNetP8WSI stanza
            uc.pushSubject(UserContext.createSubject(conn, username, password, "FileNetP8WSI"));

            // Get the default domain
            Domain domain = Factory.Domain.getInstance(conn, null);
            // Get an object store
            objectStore = Factory.ObjectStore.fetchInstance(domain, Utils.getConfigs("os.prod"), null);
            
            System.out.println("get_DisplayName ======> "+objectStore.get_DisplayName());
            setConnected(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private static ContentElementList createSingleContentElements(InputStream documentContent, String fileName,
                                                                  String mimeType) {
        ContentElementList cel = null;
        if (documentContent != null) {
            cel = Factory.ContentElement.createList();
            ContentTransfer ctNew = Factory.ContentTransfer.createInstance();
            ctNew.setCaptureSource(documentContent);
            ctNew.set_RetrievalName(fileName);
            ctNew.set_ContentType(mimeType);
            cel.add(ctNew);

        }

        return cel;
    }

    public ObjectStore getObjectStore() {
        if (objectStore == null) {
            try {
                initObjectStore();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return objectStore;
    }

    public void setObjectStore(ObjectStore objectStore) {
        this.objectStore = objectStore;
    }

    private void createComponentRelationship(Document childDoc, Document compoundDoc) {
        ComponentRelationship cr = Factory.ComponentRelationship.createInstance(getObjectStore(), null);

        cr.set_VersionBindType(VersionBindType.LATEST_MAJOR_VERSION);
        cr.set_ParentComponent(compoundDoc);
        cr.set_ChildComponent(childDoc);
        cr.set_ComponentRelationshipType(ComponentRelationshipType.DYNAMIC_CR);
        cr.set_ComponentSortOrder(1000);
        cr.save(RefreshMode.REFRESH);


    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isUserExist(String username) {

        try {
            Realm realm = Factory.Realm.fetchCurrent(getObjectStore().getConnection(), null);
            UserSet findUsers = realm.findUsers(username, PrincipalSearchType.EXACT, PrincipalSearchAttribute.SHORT_NAME,
                    PrincipalSearchSortType.NONE, 0, null);
            Iterator it = findUsers.iterator();
            return it.hasNext();
        } catch (Exception e) {
            logger.error(e, e);
        }
        return false;
    }

    public static int getThreadID() {
        return threadID;
    }


    public IndependentObjectSet fetchIndependentObjectSet(ObjectStore os, String select, String from, String where) {
        return fetchIndependentObjectSet(os, select, from, where, null, 0, 0, null, null, null, true);
    }

    public IndependentObjectSet fetchIndependentObjectSet(ObjectStore os, String select, String from, String where,
                                                          String orderBy, int rows) {
        return fetchIndependentObjectSet(os, select, from, where, orderBy, rows, 0, null, null, null, false);
    }

    public IndependentObjectSet fetchIndependentObjectSet(ObjectStore os, String select, String from, String where,
                                                          String orderBy, int rows, PropertyFilter filters, boolean includeSubClass) {
        return fetchIndependentObjectSet(os, select, from, where, orderBy, rows, 0, filters, null, null, includeSubClass);
    }

    public IndependentObjectSet fetchIndependentObjectSet(
            ObjectStore os, String select, String from, String where,
            String orderBy, int rows, int pageSize, PropertyFilter filters,
            String freeText, String containsRestriction, boolean includeSubClass) {
        IndependentObjectSet rrs = null;
        SearchSQL q = new SearchSQL();
        // q.setTimeLimit(20);
        SearchScope ss = new SearchScope(os);
        if (select != null && !select.trim().equals(""))
            q.setSelectList(select);
        q.setFromClauseInitialValue(from, null, includeSubClass);
        if (!isStringValueEmpty(freeText))
            q.setFreetextRestriction(from, freeText);
        if (!isStringValueEmpty(containsRestriction))
            q.setContainsRestriction(from, containsRestriction);
        if (where != null && !where.equals("")) {
            q.setWhereClause(where);
        }
        if (rows != 0) {
            q.setMaxRecords(rows);
        }

        Integer pSize = null;
        if (pageSize != 0) {
            pSize = pageSize;
        }
        if (filters == null) {
            filters = new PropertyFilter();
        }
        if (orderBy != null && !orderBy.equals(""))
            q.setOrderByClause(orderBy);
        rrs = ss.fetchObjects(q, pSize, filters, Boolean.TRUE);
        return rrs;
    }

    /**
     * Get correspondence parent document
     *
     * @param os               object store object
     * @param correspondenceNo correspondence number
     * @param year             year of correspondence
     * @param typeId           type of correspondence
     * @return
     */
    public Document getCorrespondenceInfoCompundDocument(ObjectStore os, int correspondenceNo, int year, int typeId) {
        String from = SystemConstants.CE_DOCUMENT_CLASS_CORRESPONDENCE;
        String where = SystemConstants.CE_PROPERTY_CORRESPONDENCE_NUMBER + "=" + correspondenceNo
                + " AND " + SystemConstants.CE_PROPERTY_HIJRIC_YEAR + "=" + year
                + " AND " + SystemConstants.CE_PROPERTY_CORRESPONDENCE_TYPE + "=" + typeId;
        IndependentObjectSet ios = fetchIndependentObjectSet(os, null, from, where);
        Iterator<Document> itr = ios.iterator();
        if (itr.hasNext())
            return itr.next();
        return null;
    }

    public boolean isRegulatoryDocumentExists(ObjectStore os, int correspondenceNo, int year, int typeId, String title) {
        String from = "RegulatoryDocuments";
        String where = SystemConstants.CE_PROPERTY_CORRESPONDENCE_NUMBER + "=" + correspondenceNo
                + " AND " + SystemConstants.CE_PROPERTY_HIJRIC_YEAR + "=" + year
                + " AND " + SystemConstants.CE_PROPERTY_CORRESPONDENCE_TYPE + "=" + typeId
                + " AND " + "DocumentTitle" + "='" + title + "'";
        IndependentObjectSet ios = fetchIndependentObjectSet(os, null, from, where);
        Iterator<Document> itr = ios.iterator();
        if (itr.hasNext())
            return true;
        return false;
    }


    public boolean addCorrespondenceDocument(ObjectStore os, int correspondenceNo, int year, int typeId,
                                             String docId, String userid, Date creationDate,String migOldDocId,
                                             Integer migDocumentId)
            throws Exception {
        Document correspondenceInfo = getCorrespondenceInfoCompundDocument(os, correspondenceNo, year, typeId);

        if (correspondenceInfo == null) {
            correspondenceInfo = Factory.Document.createInstance(os, SystemConstants.CE_DOCUMENT_CLASS_CORRESPONDENCE);
            correspondenceInfo.set_CompoundDocumentState(CompoundDocumentState.COMPOUND_DOCUMENT);
            correspondenceInfo.getProperties().putValue(SystemConstants.CE_PROPERTY_CORRESPONDENCE_NUMBER, correspondenceNo);
            correspondenceInfo.getProperties().putValue(SystemConstants.CE_PROPERTY_CORRESPONDENCE_TYPE, typeId);
            correspondenceInfo.getProperties().putValue(SystemConstants.CE_PROPERTY_HIJRIC_YEAR, year);
            correspondenceInfo.getProperties().putValue("IsMigrated", Boolean.TRUE);
            correspondenceInfo.getProperties().putValue("MigratedDocId", migDocumentId);

//            IsMigrated
//                    MigOldDocId
//            MigTransactionId

//            correspondenceInfo.getProperties().putValue(SystemConstants.CE_PROPERTY_CUSTOM_MODIFIED_BY, userId);
//            correspondenceInfo.getProperties().putValue("DisplayNumber", correspondenceNo + "");
            correspondenceInfo.checkin(null, CheckinType.MAJOR_VERSION);
            correspondenceInfo.save(RefreshMode.REFRESH);

        } else {
            DocumentSet docs = correspondenceInfo.get_ChildDocuments();
            Iterator<Document> childDocs = docs.iterator();
            while (childDocs.hasNext()) {
                Document child = childDocs.next();
                if ((child.get_Id() + "").equalsIgnoreCase(docId)) {
                    logger.error("This document is already Exist remove it docId =" + docId);
                    return true;
                }

            }
        }

        Document doc = fetchDocById(os, docId);


        //HH
        Properties props = doc.getProperties();
        if (props.isPropertyPresent(SystemConstants.CE_PROPERTY_CORRESPONDENCE_NUMBER)
                && props.isPropertyPresent(SystemConstants.CE_PROPERTY_CORRESPONDENCE_TYPE)
                && props.isPropertyPresent(SystemConstants.CE_PROPERTY_HIJRIC_YEAR)) {

            props.putValue(SystemConstants.CE_PROPERTY_CORRESPONDENCE_NUMBER, correspondenceNo);
//            props.putValue("DisplayNumber", correspondenceNo + "");
            props.putValue(SystemConstants.CE_PROPERTY_CORRESPONDENCE_TYPE, typeId);
            props.putValue(SystemConstants.CE_PROPERTY_HIJRIC_YEAR, year);
            correspondenceInfo.getProperties().putValue("MigratedDocId", migDocumentId);
            correspondenceInfo.getProperties().putValue("IsMigrated", Boolean.TRUE);


            doc.save(RefreshMode.REFRESH);
            doc.refresh();
        }


        ComponentRelationship cr = Factory.ComponentRelationship.createInstance(os, null);
        cr.set_VersionBindType(VersionBindType.LATEST_MAJOR_VERSION);

        cr.set_ParentComponent(correspondenceInfo);
        cr.set_ChildComponent(doc);
        cr.set_ComponentSortOrder(1);
        cr.set_ComponentRelationshipType(ComponentRelationshipType.DYNAMIC_CR);
        cr.save(RefreshMode.REFRESH);

//		HijriCalendar hd = new HijriCalendar();
//		int operation_hijri_date =hd.getCurrentHijriDateInt();
//		OperationsLog log = new OperationsLog(SystemConstants.LOG_APP_NAME_MOAMALAT,
//				SystemOperations.OP_ATTACH_DOCUMENT, operation_hijri_date, userId, correspondenceNo, typeId, year, docId, doc.get_Name(),
//				"correspondenceInfo,"+correspondenceInfo.get_Id());
//		new CorrespondencePropertiesDAO().InsertInto_IO_LOG(log);

        return true;
    }

    public Document fetchDocById(ObjectStore os, String id) {
        if (isStringValueEmpty(id))
            return null;
        Document doc = Factory.Document.fetchInstance(os, id, null);
        return doc;
    }

    public InputStream getDocStream(ObjectStore os, String id) {
        Document doc = fetchDocById(os, id);
        InputStream stream = doc.accessContentStream(0);
        return stream;
    }

    public InputStream getDocStreamForScan(ObjectStore os, String id) {
        Document doc = fetchDocById(os, id);
        if (!doc.get_IsCurrentVersion())
            doc = (Document) doc.get_CurrentVersion();
        InputStream stream = doc.accessContentStream(0);
        return stream;
    }

    public static boolean isStringValueEmpty(String value) {
        return value == null || value.trim().equals("")
                || value.trim().equalsIgnoreCase("null");
    }
//
//    public static void main(String[] args) {
//        // A query that will return a large result set
//        SearchSQL sql = new SearchSQL("select [DocumentTitle] from document");
//        SearchScope scope = new SearchScope(getObjectStore());
//        // Set the paging to be 50 items per page and enable continuation
//        IndependentObjectSet s = scope.fetchObjects(sql, 20, null, true);
//        // Get the page iterator
//        PageIterator p = s.pageIterator();
//        // Loop through each page
//        while (p.nextPage()) {
//            // Loop through each item in the page
//            for (Object obj : p.getCurrentPage()) {
//                // Get the document object and write Document Title
//                Document doc = (Document) obj;
//                System.out.println(
//                        doc.getProperties().getStringValue("DocumentTitle"));
//            }
//        }
//    }

    public void setCeConnection(int ceConnection) {
        this.ceConnection = ceConnection;
    }

    public int getCeConnection() {
        return ceConnection;
    }
}


