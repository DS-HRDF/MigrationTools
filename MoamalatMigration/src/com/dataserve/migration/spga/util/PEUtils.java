package com.dataserve.migration.spga.util;

import com.dataserve.migration.spga.objects.WorkitemObject;
import filenet.vw.api.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/************************
 *
 * Created By Mohammad Awwad 27-June-2020
 *
 ************************/


public class PEUtils {
    final static Logger logger = Logger.getLogger(PEUtils.class);

    String RESPONSE_SAVE = "RES_SAVE";
    String RESPONSE_FORWARD = "RES_FORWARD";
    String RESPONSE_END_REMINDER = "RES_ENDREMINDER";
    String RESPONSE_EXTEND_REMINDER = "RES_EXTENDREMINDER";
    String RESPONSE_REPLY = "RESPONSE_REPLY";
    String RESPONSE_PROJECT_LETTERS = "RESPONSE_PROJECT_LETTERS";
    String PRIVATE_INBOX_QUEUE = "Inbox";


    int type;

    private String WFD;
    public static final String WF_CLASS_INCOMING = "Moamalat_ICN_Incoming";
    public static final String WF_CLASS_OUTGOING = "Moamalat_ICN_Outgoing";
    public static final String WF_CLASS_INTERNAL = "Moamalat_ICN_Internal";

    public static final String WF_OLD_CLASS_INCOMING = "MOAMALAT_INCOMING";
    public static final String WF_OLD_CLASS_OUTGOING = "MOAMALAT_OUTGOING";
    public static final String WF_OLD_CLASS_INTERNAL = "MOAMALAT_INTERNAL";

    public static final String WF_CLASS_DECISION = "Moamalat_ICN_Decisions";
    public static final String WF_CLASS_CIRCULAR = "Moamalat_ICN_Circulars";
    public static final String WFD_READ_RESPONSE = "WF_ReadResponse";

    public String initCorrType(int type) {
        if (type == 1 || type == 1 + 7)
            return WF_CLASS_INCOMING;
        if (type == 2 || type == 2 + 7)
            return WF_CLASS_OUTGOING;
        if (type == 3 || type == 3 + 7)
            return WF_CLASS_INTERNAL;
        if (type == 44 || type == 440)
            return WF_CLASS_DECISION;
        if (type == 55 || type == 550)
            return WF_CLASS_CIRCULAR;
        return "";
    }

    public static final int PARTICIPANT_EMPLOYEE = 1;
    public static final int PARTICIPANT_DEPARTMENT = 2;
    public static final int PARTICIPANT_QUEUE = 3;
    public static String GENERIC_PUBLIC_INBOX = "RolesPublicInbox";
    VWSession vwSession;

    public void launchCorrespondence(WorkitemObject workitemObject) throws Exception {


        vwSession = getVwSession();

        int itemType = workitemObject.getItemType();
        this.WFD = initCorrType(itemType);

        int sequenceNumber = Integer.valueOf(workitemObject.getSequenceNumber());
        int correspondenceNumber = Integer.valueOf(workitemObject.getCorrespondenceNumber());
        int moNumber = Integer.valueOf(workitemObject.getMoNumber());
        int hijricYear = Integer.valueOf(workitemObject.getHijricYear());
        String toUserIds = workitemObject.getUsername();
        String toDeptIds = workitemObject.getDepartmentID() + "";
        int urgencyType = workitemObject.getUrgencyLevel();
        boolean important = workitemObject.getImportanceLevel() > 0;


//        List<String> toUsers = new ArrayList<String>();
//        List<String> ccUsers = new ArrayList<String>();
        int toDepartmentId = workitemObject.getDepartmentID();

        String nextUser = "";
        Integer publicInboxDepID = workitemObject.getDepartmentID();
        String queueName = workitemObject.getQueueName();
        if (queueName != null && queueName.contains(GENERIC_PUBLIC_INBOX) && publicInboxDepID > 0)
            queueName = "#" + publicInboxDepID;

        if (workitemObject.getParticipantType() == 1) { //User
            nextUser = workitemObject.getF_BoundUser();
        }
        if (workitemObject.getParticipantType() == 2) { //Queue
            nextUser = queueName;
        }

        String[] toArray = null;
        String[] ccArray = null;

        if (workitemObject.getForwardType() == 1) {//TO
            toArray = new String[1];
            toArray[0] = nextUser;
        } else {

        }

        if (workitemObject.getForwardType() == 2) {//CC
            ccArray = new String[1];
            ccArray[0] = nextUser;
        } else {

        }


        boolean isLaunch = false;
        VWStepElement stepElementToTeminate = null;
        String fromUser = "";

        VWStepElement stepElement = vwSession.createWorkflow(this.WFD);
        isLaunch = true;


        try {
            if (stepElement.hasParameterName("HijricYear"))
                stepElement.setParameterValue("HijricYear", Integer.valueOf(hijricYear), true);
            if (stepElement.hasParameterName("SequenceNumber"))
                stepElement.setParameterValue("SequenceNumber", Integer.valueOf(correspondenceNumber), true);
            if (stepElement.hasParameterName("MONumber"))
                stepElement.setParameterValue("MONumber", Integer.valueOf(moNumber), true);
//            if (stepElement.hasParameterName("DisplayNumber"))
//                stepElement.setParameterValue("DisplayNumber", workitemObject.getSequenceNumber(), true);
            if (stepElement.hasParameterName("ToUsers") && toArray != null)
                stepElement.setParameterValue("ToUsers", toArray, true);
            if (stepElement.hasParameterName("CCUsers") && ccArray != null)
                stepElement.setParameterValue("CCUsers", ccArray, true);

            if (stepElement.hasParameterName("isRead"))
                stepElement.setParameterValue("isRead", workitemObject.getIsRead() > 0, true);

            if (stepElement.hasParameterName("IsMigrated"))
                stepElement.setParameterValue("IsMigrated", Integer.valueOf(1), true);

            if (stepElement.hasParameterName("MigTransId"))
                stepElement.setParameterValue("MigTransId", Integer.valueOf(workitemObject.getId()), true);

            if (stepElement.hasParameterName("ActionID")){
                stepElement.setParameterValue("ActionID", Integer.valueOf(workitemObject.getForwardingId()), true);
                System.out.println("stepElement ActionID = "+workitemObject.getForwardingId());
            }


            if (isLaunch) {
                if (stepElement.hasParameterName("WF_Version"))
                    stepElement.setParameterValue("WF_Version", "1", true);

                String sequenceNumberDisplayValue = String.valueOf(sequenceNumber);
                String itemTypeClass = "";

                if (stepElement.hasParameterName("EmailSequenceNumber"))
                    stepElement.setParameterValue("EmailSequenceNumber", sequenceNumberDisplayValue, true);
                if (stepElement.hasParameterName("ItemTypeClass"))
                    stepElement.setParameterValue("ItemTypeClass", itemTypeClass, true);

            }
            if (stepElement.hasParameterName("FromUser")) {
                stepElement.setParameterValue("FromUser", workitemObject.getFromUser(), true);
            }

            stepElement.setParameterValue("F_Subject", workitemObject.getF_Subject(), true);


            if (stepElement.hasParameterName("ReceivedOnHijriDate"))
                stepElement.setParameterValue("ReceivedOnHijriDate", workitemObject.getReceivedOnHijriDate(), true);


            if (stepElement.hasParameterName("CorrespondenceDate")) {
                stepElement.setParameterValue("CorrespondenceDate", workitemObject.getCorrespondenceDate(), true);
            }
            if (stepElement.hasParameterName("Originator")) {
                stepElement.setParameterValue("Originator", workitemObject.getOriginator(), true);
            }

            if (stepElement.hasParameterName("CallingUser")) {
                String[] participants = {workitemObject.getOriginator()};
                stepElement.setParameterValue("CallingUser", participants, true);
            }
            if (stepElement.hasParameterName("ImportanceLevel"))
                stepElement.setParameterValue("ImportanceLevel",
                        important ? Integer.valueOf(1) : Integer.valueOf(0),
                        true);
            if (stepElement.hasParameterName("UrgencyLevel"))
                stepElement.setParameterValue("UrgencyLevel", urgencyType, true);

            if (stepElement.hasParameterName("CallingQueue")) {
                stepElement.setParameterValue("CallingQueue", queueName, true);
            }

            stepElement.doDispatch();


//            String queueName = workitemObject.getQueueName();
//            if (queueName != null && queueName.contains(GENERIC_PUBLIC_INBOX) && publicInboxDepID > 0)
//                queueName = "#" + publicInboxDepID;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void createWorkBasket(String queueName,String inBasketName,String roleName
            ,String departmentId,List<String> roleUsersIds) throws Exception {


        String applicationSpaceName = "Moamalat Application Spaces";

        try {
            VWSession pe = getVwSession();
            // code enhancement

            VWSystemConfiguration c = pe.fetchSystemConfiguration();

            VWQueueDefinition queueDefinition = c.getQueueDefinition(queueName);
            VWWorkBasketDefinition workBasketDefinition = queueDefinition.createWorkBasketDefinition(inBasketName);

            VWExposedFieldDefinition[] queueFields = queueDefinition.getFields();

            VWWorkBasketColumnDefinition[] coldefs = getWorkBasketColumns(workBasketDefinition, queueFields);
            int i;
            workBasketDefinition.setWorkBasketColumnDefinitions(coldefs);
            workBasketDefinition.setQueryFilterString("PublicInboxDepID = " + departmentId);
            c.updateQueueDefinition(queueDefinition);
            VWApplicationSpaceDefinition applicationSpaceDefinition = c.getApplicationSpaceDefinition(applicationSpaceName);
            VWRoleDefinition roleDefinition = applicationSpaceDefinition.createRoleDefinition((roleName));
            VWParticipant[] roleParticipants = new VWParticipant[roleUsersIds.size()];
            i = 0;
            for (String u : roleUsersIds) {
                roleParticipants[i] = new VWParticipant(u);
                i++;
            }
            roleDefinition.setRoleParticipants(roleParticipants);
            roleDefinition.setWorkBasketDefinitions(new VWWorkBasketDefinition[] { workBasketDefinition });
            applicationSpaceDefinition.updateRoleDefinition(roleDefinition);

            c.updateApplicationSpaceDefinition(applicationSpaceDefinition);
            createFilterDefinitions(workBasketDefinition, queueDefinition);
//           reOrderInbasketColumns(workBasketDefinition, queueDefinition, c, applicationSpaceName);
            String[] errors = c.commit();
            String creationExceptions = "";
            if (errors != null) {
                for (String err : errors) {
                    creationExceptions += err + "\n";
                }
                throw new Exception(creationExceptions);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private VWWorkBasketColumnDefinition[] getWorkBasketColumns(VWWorkBasketDefinition workBasketDefinition, VWExposedFieldDefinition[] queueFields) {
        String[] allowedFields = new String[]{"UrgencyLevel:Urgency Level", "SequenceNumber:Sequence Number",
                "F_Subject:Correspondence Subject", "FromUser:From User",
                "ReceivedOnHijriDate:Received On HijriDate", "ItemType:Item Type", "ImportanceLevel:ImportanceLevel",
                "HijricYear:HijricYear", "isRead:isRead", "F_Locked:F_Locked", "CorrespondenceDate:Correspondence Date"};

        List<String> asList = Arrays.asList(allowedFields);
        VWWorkBasketColumnDefinition[] coldefs = new VWWorkBasketColumnDefinition[queueFields.length];

        for (int i = 0; i < asList.size(); i++){
            String item = asList.get(i);
            coldefs[i] = getColumnDefinition(item,workBasketDefinition,queueFields);
        }

        return coldefs;
    }


    private VWWorkBasketColumnDefinition getColumnDefinition(String columnName, VWWorkBasketDefinition workBasketDefinition, VWExposedFieldDefinition[] queueFields) {
        int i = 0;
        for (VWExposedFieldDefinition f : queueFields) {
            String item = columnName.split(":")[0];
            String label = columnName.split(":")[1];
            if (f.getName().equals(item)) {
                return workBasketDefinition.createWorkBasketColumnDefinition(label, f);
            }
            i++;
        }
        return null;
    }

    private void reOrderInbasketColumns (VWWorkBasketDefinition workBasketDefinition,
                                         VWQueueDefinition queueDefinition, VWSystemConfiguration sysConfig, String applicationSpaceName)
            throws Exception {
        try {
            List<String> fields = new ArrayList<String>();
            fields.add(0, "UrgencyLevel");
            fields.add(1, "SequenceNumber");
            fields.add(2, "F_Subject");
            fields.add(3, "FromUser");
            fields.add(4, "ReceivedOnHijriDate");
            fields.add(5, "ItemType");

            fields.add(6, "ImportanceLevel");
            fields.add(7, "HijricYear");
            fields.add(8, "isRead");
            fields.add(9, "CorrespondenceDate");
//            fields.add(10, "MONumber");
//            fields.add(11, "PublicInboxDepID");
//            fields.add(12, "Originator");

            VWApplicationSpaceDefinition applicationSpaceDefinition = sysConfig.getApplicationSpaceDefinition(applicationSpaceName);
            if (workBasketDefinition != null) {
                VWWorkBasketColumnDefinition[] oldColdefs = workBasketDefinition.getWorkBasketColumnDefinitions();
                VWWorkBasketColumnDefinition[] newColdefs = new VWWorkBasketColumnDefinition[oldColdefs.length + 1];
                int i = 0;
                for (String field : fields) {
                    VWWorkBasketColumnDefinition selectedCol = null;
                    for (VWWorkBasketColumnDefinition col : oldColdefs) {
                        System.out.println("name = "+ col.getName() );
                        System.out.println("prompt = "+ col.getPrompt() );
                        if (col.getPrompt().equalsIgnoreCase(field)){
                            selectedCol = col;
                            selectedCol.setSortable(true);
                        }
                    }
                    if (selectedCol == null)
                        continue;
                    newColdefs[i] = selectedCol;
                    i++;
                }
                createFilterDefinitions(workBasketDefinition, queueDefinition);
                workBasketDefinition.setWorkBasketColumnDefinitions(newColdefs);
                sysConfig.updateQueueDefinition(queueDefinition);
                sysConfig.updateApplicationSpaceDefinition(applicationSpaceDefinition);
            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace(); logger.error(e.getMessage(), e);
        }
    }
    public void createFilterDefinitions(VWWorkBasketDefinition workBasketDefinition, VWQueueDefinition queueDefinition){
        VWWorkBasketFilterDefinition filterDefinition = workBasketDefinition.getWorkBasketFilterDefinition("F_Subject");

        if(filterDefinition ==null){
            filterDefinition = workBasketDefinition.createWorkBasketFilterDefinition("F_Subject");
            filterDefinition.setSearchField(queueDefinition.getField("F_Subject"), VWWorkBasketFilterDefinition.OPERATOR_LIKE);
            filterDefinition.setPrompt("F_Subject");
        }

        filterDefinition = workBasketDefinition.getWorkBasketFilterDefinition("ItemType");

        if(filterDefinition ==null){
            filterDefinition = workBasketDefinition.createWorkBasketFilterDefinition("ItemType");
            filterDefinition.setSearchField(queueDefinition.getField("ItemType"), VWWorkBasketFilterDefinition.OPERATOR_EQUAL);
            filterDefinition.setPrompt("ItemType");
        }

        filterDefinition = workBasketDefinition.getWorkBasketFilterDefinition("isRead");

        if(filterDefinition ==null){
            filterDefinition = workBasketDefinition.createWorkBasketFilterDefinition("isRead");
            filterDefinition.setSearchField(queueDefinition.getField("isRead"), VWWorkBasketFilterDefinition.OPERATOR_EQUAL);
            filterDefinition.setPrompt("isRead");
        }

        filterDefinition = workBasketDefinition.getWorkBasketFilterDefinition("SequenceNumber");

        if(filterDefinition ==null){
            filterDefinition = workBasketDefinition.createWorkBasketFilterDefinition("SequenceNumber");
            filterDefinition.setSearchField(queueDefinition.getField("SequenceNumber"), VWWorkBasketFilterDefinition.OPERATOR_EQUAL);
            filterDefinition.setPrompt("SequenceNumber");
        }
    }



    private VWSession getVwSession() {
        if (vwSession != null && vwSession.isLoggedOn())
            return vwSession;
        vwSession = new VWSession();

        String userName = Utils.getConfigs("username");
        String password = Utils.getConfigs("password");
        String connectionPoint = Utils.getConfigs("cp");
        String uri = Utils.getConfigs("uri");
        vwSession.setBootstrapCEURI(uri);
        vwSession.logon(userName, password, connectionPoint);
        return vwSession;
    }


//    public void deleteWorkitems(){
//        vwSession = getVwSession();
//
//        VWQueue inboxQueue = vwSession.getQueue("Inbox");
//        VWQueue publicQueue = vwSession.getQueue(SystemConstants.GENERIC_PUBLIC_INBOX);
//
//        int queryFlags = VWQueue.QUERY_NO_OPTIONS;
//        int fetchType = VWFetchType.FETCH_TYPE_WORKOBJECT;
//
//
//        //Check user private queue
//        VWQueueQuery inboxQuery = inboxQueue.createQuery(null, null, null, queryFlags,
//                null, null, fetchType);
//        while (inboxQuery.hasNext()){
//            VWWorkObject  workItem = (VWWorkObject) inboxQuery.next();
//            workItem.doDelete(true, true);
//            logger.info("Deleted inbox");
//        }
//        //Check user private queue
//        VWQueueQuery queueQuery = publicQueue.createQuery(null, null, null, queryFlags,
//                null, null, fetchType);
//        while (queueQuery.hasNext()){
//            VWWorkObject workItem = (VWWorkObject) queueQuery.next();
//            workItem.doDelete(true, true);
//            logger.info("Deleted Queue");
//        }
//
//
//    }
}
