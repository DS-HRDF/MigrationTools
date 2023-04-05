package com.filenetce;

import filenet.vw.api.*;

import java.util.*;

public class MainPE {
    public static void main(String[] args) {
        VWSession adminVWSession = new MainPE().getAdminVWSession();
//        System.out.println(adminVWSession);
        new MainPE().completeWorkflow(adminVWSession,"7F0DE7B0912FB7478310247FA0C3D583","RolesPublicInbox",
                null,null,44400081,1444,1,"fntadmin",""
        ,"","","");

    }

    public boolean completeWorkflow(VWSession vwSession, String wobNum, String queueName, ArrayList<HashMap<String, Object>> properties, String propertiesString, int correspondenceNumber, int hijricYear, int correspondenceType, String userId, String pageName, String userAgent, String referer, String ip) {
        boolean success = true;
        int forwardingId = -1;
        int itemType=-1;
        Integer workitemActionId = null;
        VWStepElement vwStepElement=null;
        try {

            forwardingId = 100;

            vwStepElement = getStepElement(vwSession, wobNum, queueName);

            if(vwStepElement != null) {
                vwStepElement.doLock(true);
                workitemActionId =100;
                itemType = 1;

                if(queueName.equalsIgnoreCase("RolesPublicInbox")) {
                    int publicInboxDepID=5110;
                    queueName=queueName+"#"+publicInboxDepID ;
                }




//                if (property.get("name").toString().equals("ToUsers")) {
//                    propertiesToUsers=(String[]) getPropertyValue(property);
//                }
//
//                if (property.get("name").toString().equals("CCUsers")) {
//                    propertiesCCUsers=(String[]) getPropertyValue(property);
//                }
//
//                if (property.get("name").toString().equals("F_Responses")) {
//                    response=(String)getPropertyValue(property);
//                }

//                WorkflowUtil util = new WorkflowUtil();
//                String receivedOnHijriDate = util.formatReceivedOnHijriDate(new HijriCalendar());

//                for (Iterator iterator = properties.iterator(); iterator.hasNext();) {
//                    HashMap<String, Object> property = (HashMap<String, Object>) iterator.next();
//
//                    if( (property.get("dataType").toString().equalsIgnoreCase("xs:String") &&  property.get("name").toString().equalsIgnoreCase("ReminderGroup")) || property.get("name").toString().equalsIgnoreCase("CallingUser") || property.get("name").toString().equalsIgnoreCase("CorrespondenceHDate") || property.get("name").toString().equalsIgnoreCase("workclassName") || property.get("name").toString().equalsIgnoreCase("sheetName") || property.get("name").toString().equalsIgnoreCase("stepName") ) {
//                        continue;
//                    }
//
//                    //System.out.println("set "+property.get("name").toString());
//
//                    Object value=getPropertyValue(property);
//
//                    if(property.get("name").toString().equalsIgnoreCase("F_Responses")) {
//                    }else if(property.get("name").toString().equals("ReceivedOnHijriDate")) {
//                        vwStepElement.setParameterValue(property.get("name").toString(),receivedOnHijriDate, true);
//                    }else if(property.get("name").toString().equals("EmailReceivedOnHijriDate")) {
//                        String currentHijicDateTime = WorkflowUtil.getFormatedDateForRecivedOnHijriDate(receivedOnHijriDate, null);
//                        vwStepElement.setParameterValue(property.get("name").toString(),currentHijicDateTime, true);
//                    }else if(vwStepElement.hasParameterName(property.get("name").toString())) {
//                        vwStepElement.setParameterValue(property.get("name").toString(),value, true);
//                    }
//                }]


                vwStepElement.setParameterValue("WF_Version",47, true);
                vwStepElement.setParameterValue("Originator","fntadmin", true);
                vwStepElement.setParameterValue("DeadLineInMinutes",500000, true);
                vwStepElement.setParameterValue("ToUsers",new String[]{"fntadmin"}, true);
                vwStepElement.setParameterValue("CCUsers",new String[]{}, true);
                vwStepElement.setParameterValue("FromUser","وحدة التحرير والنسخ-مكتب الوزير", true);
                vwStepElement.setParameterValue("F_Subject","subject 0000", true);
                vwStepElement.setParameterValue("ReceivedOnHijriDate","14440710113014", true);
                vwStepElement.setParameterValue("CallingUser", new String[]{userId} , true );
                vwStepElement.setParameterValue("CallingQueue","#4854", true);
                vwStepElement.setParameterValue("ImportanceLevel",0, true);
                vwStepElement.setParameterValue("UrgencyLevel",0, true);
                vwStepElement.setParameterValue("ActionID",81697, true);
                vwStepElement.setParameterValue("ReadResponseRequested",false, true);
                vwStepElement.setParameterValue("isRead",false, true);
                vwStepElement.setParameterValue("GReminderDate","", true);
//                vwStepElement.setParameterValue("ReminderGroup","", true);

//                vwStepElement.setParameterValue("CurrentCallingUser","fntadmin", true);
//                vwStepElement.setParameterValue("F_Responses","RES_FORWARD", true);

                System.out.println(vwStepElement.getStepResponses());
                vwStepElement.setSelectedResponse("RES_FORWARD");

//                vwStepElement.doLock(true);
//                vwStepElement.doSave(true);
                vwStepElement.doDispatch();


            }else {

                success=false;

            }

        }catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public VWStepElement getStepElement(VWSession vwSession, String wobNum, String queueName) {
        try {

            VWQueue vwQueue = vwSession.getQueue(queueName);
            VWQueueQuery qQuery = null;
            int queryFlags = VWQueue.QUERY_READ_LOCKED;
            int queryType = VWFetchType.FETCH_TYPE_STEP_ELEMENT;
            String filter = "F_WobNum =:A ";
            Object[] substitutionVars = { new VWWorkObjectNumber(wobNum) };
            // construct a queue query object and query for all step elements.
            qQuery = vwQueue.createQuery(null, null, null, queryFlags, filter,substitutionVars, queryType);
            // fetch the first step element using the VWQueueQuery object

            if (qQuery.fetchCount() > 0) {
                return (VWStepElement) qQuery.next();
            }

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public VWSession getAdminVWSession(){
        VWSession vwsession = new VWSession();
        String uri = "http://hrsdvm.ds:9080/wsi/FNCEWS40MTOM/";
        String fileNetAdminUserID = "fntadmin";
        String fileNetAdminPassword = "P@ssw0rd";
        // "filenet";
        String connectionPoint = "CP";

        vwsession.setBootstrapCEURI(uri);
        vwsession.logon(fileNetAdminUserID, fileNetAdminPassword, connectionPoint);
        return vwsession;
    }
}
