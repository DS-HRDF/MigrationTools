package com.dataserve.migration.spga.dao;

import com.dataserve.migration.spga.objects.MigrationData;
import com.dataserve.migration.spga.util.SystemConstants;
import com.dataserve.migration.spga.util.Utils;
import com.filenet.api.collection.*;
import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import com.filenet.api.property.Properties;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.security.AccessPermission;
import com.filenet.api.security.Realm;
import com.filenet.api.util.Id;
import com.filenet.api.util.UserContext;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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


    public String addDocument(MigrationData c, byte[] fileBytes) throws Exception {


        Integer correspondenceNo = Integer.valueOf(c.getCn());
        Integer typeId = Integer.valueOf(c.getType());
        Integer year = Integer.valueOf(c.getHy());
        Integer migTransaction = Integer.valueOf(c.getMigTransactionId());

        ObjectStore os = getObjectStore();
        Document doc = null;
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(c.getCreateDate());
        doc = createChildDoc(c, c.getUsername(), date1, c.getClassId(), fileBytes);

        boolean success = addCorrespondenceDocument(os, correspondenceNo, year, typeId, doc.get_Id() + "",
                c.getUsername(), date1, c.getDocid(), migTransaction);


        return doc.get_Id().toString();
    }

    private String fixTitle(String title) {
        return title.replace("*", "").replace("?", "");
    }

    public Document getMigOldDocId(int correspondenceNo, int year, int typeId,String migOldDoc) {
        String from = "RegulatoryDocuments"; //SystemConstants.CE_DOCUMENT_CLASS_CORRESPONDENCE;
        String where = SystemConstants.CE_PROPERTY_CORRESPONDENCE_NUMBER + "=" + correspondenceNo
                + " AND " + SystemConstants.CE_PROPERTY_HIJRIC_YEAR + "=" + year
                + " AND " + SystemConstants.CE_PROPERTY_CORRESPONDENCE_TYPE + "=" + typeId
                + " AND MigOldDocId = '" + migOldDoc + "'";
        IndependentObjectSet ios = fetchIndependentObjectSet(getObjectStore(), null, from, where);
        Iterator<Document> itr = ios.iterator();
        if (itr.hasNext())
            return itr.next();
        return null;
    }


    private Document createChildDoc(MigrationData c, String userid, Date creationDate, String classId, byte[] fileBytes) throws Exception {
        try {
            Document childDoc = Factory.Document.createInstance(getObjectStore(), classId);
            Path path = new File(c.getFileName()).toPath();
//            String mimeType = Files.probeContentType(path);
//            if (StringUtils.isEmpty(mimeType)) {
//                mimeType = "application/pdf";
//            }
            childDoc.set_MimeType(c.getMimeType());
            childDoc.getProperties().putValue("DocumentTitle", fixTitle(c.getFileName()));
            childDoc.getProperties().putValue("CorrespondenceNumber", Integer.valueOf(c.getCn()));
            childDoc.getProperties().putValue("CorrespondenceType", Integer.valueOf(c.getType()));
            childDoc.getProperties().putValue("HijricYear", Integer.valueOf(c.getHy()));
            childDoc.getProperties().putValue("IsMigrated", Integer.valueOf(1));
            childDoc.getProperties().putValue("MigOldDocId", c.getDocid());
            childDoc.getProperties().putValue("MigTransactionId", Integer.valueOf(c.getMigTransactionId()));

//            childDoc.getProperties().putValue("DisplayNumber", c.getCn());


            childDoc.set_DateCreated(creationDate);
            childDoc.set_DateLastModified(creationDate);

            ContentElementList cel = createSingleContentElements(new ByteArrayInputStream(fileBytes),
                    fixTitle(c.getFileName()), c.getMimeType());
            if (cel != null) {
                childDoc.set_ContentElements(cel);
            }
            childDoc.checkin(null, CheckinType.MAJOR_VERSION);
            childDoc.set_Creator(userid);
            childDoc.set_LastModifier(userid);
            childDoc.save(RefreshMode.REFRESH);
            return childDoc;
        } catch (Exception e) {
            logger.error(e, e);
            throw e;
        }

    }

    public Document updateContentElement(Document doc, String filename, String mimeType, byte[] fileBytes) throws Exception {
        try {
            logger.info("updateContentElement =================== 1 ");
            //initObjectStore();
            logger.info("updateContentElement =================== 2 ");
            logger.info("updateContentElement doc is doc.isLocked() 2 " + doc.isLocked());
            if (!doc.get_IsReserved()) {
                doc.checkout(ReservationType.EXCLUSIVE, null, null, null);
                doc.save(RefreshMode.REFRESH);
                doc.refresh();
                logger.info("updateContentElement doc is checked out and saved 2");
            }
            Document newDoc = (Document) doc.get_Reservation();

            logger.info("updateContentElement newDoc is here 3 newDoc=" + newDoc);

            ContentElementList cel = createSingleContentElements(new ByteArrayInputStream(fileBytes),
                    fixTitle(filename), mimeType);

            logger.info("updateContentElement cel is built 4");


            newDoc.getProperties().putValue("FixStatus", Integer.valueOf(1));//Fixed
            logger.info("updateContentElement FixStatus is set 5");

            if (cel != null) {
                newDoc.get_ContentElements().clear();
                logger.info("updateContentElement ContentElements is cleared 6");

                newDoc.get_ContentElements().addAll(cel);
                logger.info("updateContentElement new cel was added 7");

            }


            newDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
            logger.info("updateContentElement newDoc was checked in 8");

            newDoc.save(RefreshMode.REFRESH);
            logger.info("updateContentElement doc was saved :)");

            return newDoc;
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
            uri = Utils.getConfigs("uri");
            String username = Utils.getConfigs("username");
            String password = Utils.getConfigs("password");

            // Get the connection
            Connection conn = Factory.Connection.getConnection(uri);

            // Build the subject using the FileNetP8WSI stanza
            uc.pushSubject(UserContext.createSubject(conn, username, password, "FileNetP8WSI"));

            // Get the default domain
            Domain domain = Factory.Domain.getInstance(conn, null);
            // Get an object store
            objectStore = Factory.ObjectStore.fetchInstance(domain, Utils.getConfigs("os"), null);
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

    private static ContentTransfer createContentTransfer(InputStream documentContent, String fileName,
                                                         String mimeType) {
        ContentTransfer ctNew = Factory.ContentTransfer.createInstance();
        ctNew.setCaptureSource(documentContent);
        ctNew.set_RetrievalName(fileName);
        ctNew.set_ContentType(mimeType);
        return ctNew;
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

    public void createComponentRelationship(Document childDoc, Document compoundDoc) {
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

    public Document getCorrespondenceInfoCompundDocument(int correspondenceNo, int year, int typeId) {
        String from = SystemConstants.CE_DOCUMENT_CLASS_CORRESPONDENCE;
        String where = SystemConstants.CE_PROPERTY_CORRESPONDENCE_NUMBER + "=" + correspondenceNo
                + " AND " + SystemConstants.CE_PROPERTY_HIJRIC_YEAR + "=" + year
                + " AND " + SystemConstants.CE_PROPERTY_CORRESPONDENCE_TYPE + "=" + typeId;
        IndependentObjectSet ios = fetchIndependentObjectSet(getObjectStore(), null, from, where);
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

    public Document addOldParentCorrespondenceDocument(int correspondenceNo, int year, int typeId, String docId) throws Exception {

        ObjectStore os = getObjectStore();
        Document correspondenceInfo = getCorrespondenceInfoCompundDocument(os, correspondenceNo, year, typeId);

        if (correspondenceInfo == null) {
            correspondenceInfo = Factory.Document.createInstance(os, SystemConstants.CE_DOCUMENT_CLASS_CORRESPONDENCE);
            correspondenceInfo.set_CompoundDocumentState(CompoundDocumentState.COMPOUND_DOCUMENT);
            correspondenceInfo.getProperties().putValue(SystemConstants.CE_PROPERTY_CORRESPONDENCE_NUMBER, correspondenceNo);
            correspondenceInfo.getProperties().putValue(SystemConstants.CE_PROPERTY_CORRESPONDENCE_TYPE, typeId);
            correspondenceInfo.getProperties().putValue(SystemConstants.CE_PROPERTY_HIJRIC_YEAR, year);
            correspondenceInfo.getProperties().putValue("IsMigrated", Integer.valueOf(5));
            correspondenceInfo.getProperties().putValue("fixstatus", Integer.valueOf(100));
            correspondenceInfo.checkin(null, CheckinType.MAJOR_VERSION);
            correspondenceInfo.save(RefreshMode.REFRESH);
        }
        return correspondenceInfo;
//        return correspondenceInfo.get_Id();
//        return true;
    }

    public boolean addCorrespondenceDocument(ObjectStore os, int correspondenceNo, int year, int typeId,
                                             String docId, String userid, Date creationDate, String migOldDocId,
                                             Integer migTransactionId) throws Exception {
        Document correspondenceInfo = getCorrespondenceInfoCompundDocument(os, correspondenceNo, year, typeId);

        if (correspondenceInfo == null) {
            correspondenceInfo = Factory.Document.createInstance(os, SystemConstants.CE_DOCUMENT_CLASS_CORRESPONDENCE);
            correspondenceInfo.set_CompoundDocumentState(CompoundDocumentState.COMPOUND_DOCUMENT);
            correspondenceInfo.getProperties().putValue(SystemConstants.CE_PROPERTY_CORRESPONDENCE_NUMBER, correspondenceNo);
            correspondenceInfo.getProperties().putValue(SystemConstants.CE_PROPERTY_CORRESPONDENCE_TYPE, typeId);
            correspondenceInfo.getProperties().putValue(SystemConstants.CE_PROPERTY_HIJRIC_YEAR, year);
            correspondenceInfo.getProperties().putValue("IsMigrated", Integer.valueOf(1));
            correspondenceInfo.getProperties().putValue("MigTransactionId", migTransactionId);

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
            correspondenceInfo.getProperties().putValue("MigOldDocId", migOldDocId);
            correspondenceInfo.getProperties().putValue("MigTransactionId", migTransactionId);
            correspondenceInfo.getProperties().putValue("IsMigrated", Integer.valueOf(1));


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

    public boolean addDRDocument(int deliveryReportId, int correspondenceNumber,int deliveryReportHijricYear, int deliveryReportType,
                                 String docId,byte[] content,String fileName,String mimeType) throws Exception {
        String drTitle = "بيان تسليم رقم "+deliveryReportId;
        Document correspondenceInfo = Factory.Document.createInstance(getObjectStore(), "DeliveryReportDocument");
        correspondenceInfo.getProperties().putValue("deliveryReportId", deliveryReportId);
        correspondenceInfo.getProperties().putValue("deliveryReportType", deliveryReportType);
        correspondenceInfo.getProperties().putValue("deliveryReportHijricYear", deliveryReportHijricYear);
        correspondenceInfo.getProperties().putValue("CorrespondenceNumber", correspondenceNumber);
        correspondenceInfo.getProperties().putValue("IsMigrated", Integer.valueOf(1));
        correspondenceInfo.getProperties().putValue("MigOldDocId", docId);
        correspondenceInfo.getProperties().putValue("DocumentTitle",drTitle );


        ContentElementList cel = createSingleContentElements(new ByteArrayInputStream(content),
                fixTitle(fileName), mimeType);
        if (cel != null) {
            correspondenceInfo.set_ContentElements(cel);
        }

        correspondenceInfo.checkin(null, CheckinType.MAJOR_VERSION);
        correspondenceInfo.save(RefreshMode.REFRESH);

        return true;
    }

    private String getTypeStr(int deliveryReportType) {
        switch (deliveryReportType){
            case 1:return "وارد";
            case 2:return "صادر";
            case 3:return "داخلي";
        }
        return "";
    }


    public Document fetchDocById(ObjectStore os, String id) {
        if (isStringValueEmpty(id))
            return null;
        Document doc = Factory.Document.fetchInstance(os, id, null);
        return doc;
    }

    public Document fetchDocById(String id) {
        return fetchDocById(getObjectStore(), id);
    }

    public boolean isContentExists(Document doc) {
        logger.error("isContentExists "+doc);
        if (doc == null)
            return false;
        try {
            InputStream inputStream = doc.accessContentStream(0);
            if (inputStream != null)
                return true;
            return false;
        } catch (Exception e) {
            logger.error("error content not exists isContentExists ");
            logger.error("error content not exists isContentExists " + e.getMessage());
        }
        return false;
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

    public boolean applySecurity(Document document) throws Exception {

        AccessPermissionList permissions = document.get_Permissions();
        System.out.println("document.getId = " + document.get_Id().toString());


        AccessPermission p8usersprd = Factory.AccessPermission.createInstance();
        p8usersprd.set_GranteeName("P8UsersPRD@MCI.GOV");
        p8usersprd.set_AccessMask(new Integer(AccessLevel.MAJOR_VERSION_DOCUMENT_AS_INT));
        p8usersprd.set_AccessType(AccessType.ALLOW);

        AccessPermission domainUsers = Factory.AccessPermission.createInstance();
        domainUsers.set_GranteeName("Domain Users@MCI.GOV");
        domainUsers.set_AccessMask(new Integer(AccessLevel.MAJOR_VERSION_DOCUMENT_AS_INT));
        domainUsers.set_AccessType(AccessType.ALLOW);

        List<AccessPermission> toBeAdded = new ArrayList<>();
        List<AccessPermission> toBeRemoved = new ArrayList<>();
        Iterator it = permissions.iterator();
        while (it.hasNext()) {
            AccessPermission permission = (AccessPermission) it.next();
            if (permission.get_GranteeName().equalsIgnoreCase("P8UsersPRD@MCI.GOV")) {
                toBeRemoved.remove(permission);
                System.out.println("P8UsersPRD@MCI.GOV removed");
            }
            if (permission.get_GranteeName().equalsIgnoreCase("Domain Users@MCI.GOV")) {
                toBeRemoved.remove(permission);
                System.out.println("Domain Users@MCI.GOV removed");
            }
            toBeAdded.add(domainUsers);
            toBeAdded.add(p8usersprd);
            System.out.println("Domain Users@MCI.GOV added");
            System.out.println("P8UsersPRD@MCI.GOV added");


            System.out.println("permission.get_GranteeName() = " + permission.get_GranteeName());

        }
        permissions.removeAll(toBeRemoved);
        permissions.addAll(toBeAdded);
        document.getProperties().putValue("FixStatus", Integer.valueOf(1));//Fixed
        document.save(RefreshMode.REFRESH);
        System.out.println("Saved");
        return true;
    }
}


