/*
 * Mohammad Awwad
 * */
package com.dataserve.migration.spga.dao;

import com.dataserve.migration.spga.objects.FixDocumentRelation;
import com.dataserve.migration.spga.objects.InbasketData;
import com.dataserve.migration.spga.objects.MigrationData;
import com.dataserve.migration.spga.objects.WorkitemsData;
import com.dataserve.migration.spga.util.LDAPManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataDAO {
//    private CallableStatement _callStmt;
//    private ResultSet _rstSet;

    private int batchIndex = 0;
    final static Logger logger = Logger.getLogger(DataDAO.class);


    public DataDAO() {
    }


    public synchronized List<MigrationData> getMigrationData() throws Exception {
//        List<MigrationData> migrationData = new ArrayList<>();
        List<MigrationData> migrationData = Collections.synchronizedList(new ArrayList());
        String _sqlStmt = "{ call MIG_GET_MIGRATION_DATA() }";
//        String _sqlStmt = "{ call MIG_GET_MIGRATION_DATA2() }";
//        String _sqlStmt = "{ call MIG_GET_FIX_MIGRATION_DATA() }";
//        String _sqlStmt = "{ call MIG_GET_FIX_MIGRATION_DATA2() }";
        Connection _conn = null;
        CallableStatement _callStmt = null;

        try {
            _conn = initConn();
            _callStmt = _conn.prepareCall(_sqlStmt);
            ResultSet resultSet = _callStmt.executeQuery();
            while (resultSet.next()) {

                String docid = resultSet.getString("docid");
                String cn = resultSet.getString("cn");
                String hy = resultSet.getString("hy");
                String typeid = resultSet.getString("typeid");
                String fileName = resultSet.getString("fileName");
                String docTitle = resultSet.getString("docTitle");
                String classId = resultSet.getString("classId");
                String mimeType = resultSet.getString("mimeType");
                Date createDate = resultSet.getDate("createDate");
                String username = resultSet.getString("username");
                String status = resultSet.getString("status");
                String message = resultSet.getString("message");
                String migTransactionId = resultSet.getString("TransactionId");
//                String migDocId = "";
//                String migDocId = resultSet.getString("migDocId");
                String migDocId = resultSet.getString("migrated_docid");
                String createDateStr = createDate != null ? createDate.toString() : "";

                migrationData.add(new MigrationData(cn, hy, typeid, docid, fileName, docTitle, classId, mimeType,
                        createDateStr, username, status, message, migTransactionId, migDocId));
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(_callStmt);
            releaseResources(_conn);
        }
        return migrationData;
    }

    public synchronized List<WorkitemsData> getWorkitemObject() throws Exception {
        List<WorkitemsData> workitemsData = new ArrayList<>();
        String _sqlStmt = "{ call MIG_GET_WORKITEMOBJECT() }";
        Connection _conn = null;
        CallableStatement _callStmt = null;

        try {
            _conn = initConn();
            _callStmt = _conn.prepareCall(_sqlStmt);
            ResultSet resultSet = _callStmt.executeQuery();
            while (resultSet.next()) {

                String id = String.valueOf(resultSet.getInt("id"));
                String F_BoundUser = resultSet.getString("f_BoundUser");
//              String F_CreateTime = String.valueOf(resultSet.getDouble("f_CreateTime"));
                String F_Subject = resultSet.getString("f_Subject");
                String CorrespondenceDate = String.valueOf(resultSet.getInt("correspondenceDate"));
                String FromUser = resultSet.getString("fromUser");
                String HijricYear = String.valueOf(resultSet.getInt("hijricYear"));
                String ImportanceLevel = String.valueOf(resultSet.getInt("importanceLevel"));
                String isRead = String.valueOf(resultSet.getInt("isRead"));
                String ItemType = String.valueOf(resultSet.getInt("itemType"));
                String Originator = resultSet.getString("originator");
                String ReceivedOnHijriDate = resultSet.getString("receivedOnHijriDate");
                String SequenceNumber = String.valueOf(resultSet.getInt("sequenceNumber"));
                String UrgencyLevel = String.valueOf(resultSet.getInt("urgencyLevel"));
                String departmentID = String.valueOf(resultSet.getInt("departmentID"));
                String queueName = resultSet.getString("queueName");
                String username = resultSet.getString("username");
                String forwardType = String.valueOf(resultSet.getInt("forwardType"));
                String status = resultSet.getString("status");
                String message = resultSet.getString("message");
                String participantType = String.valueOf(resultSet.getInt("participantType"));
                String MONumber = "-1";
                String CorrespondenceNumber = String.valueOf(resultSet.getInt("sequenceNumber"));
                String forwardingId = String.valueOf(resultSet.getInt("FORWARDINGID"));

                System.out.println("forwardingId = "+forwardingId);
                workitemsData.add(new WorkitemsData(id, F_BoundUser, "", F_Subject, CorrespondenceDate, FromUser, HijricYear,
                        ImportanceLevel, isRead, ItemType, Originator, ReceivedOnHijriDate, SequenceNumber, UrgencyLevel, departmentID, queueName,
                        username, forwardType, status, message, participantType, MONumber, CorrespondenceNumber,forwardingId));
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(_callStmt);
            releaseResources(_conn);
        }
        return workitemsData;
    }

    public synchronized boolean updateStatus(String docid, String status, String message, String fileName, String migrated_docid) throws Exception {
        int update = 0;
        PreparedStatement preparedStatement = null;
        Connection _conn = null;
        try {
            _conn = initConn();
            String _sqlStmt = "update MIG_MIGRATION_DATA set status =?,message=?,fileName=?,migrated_docid =? where docid=?";
            preparedStatement = _conn.prepareStatement(_sqlStmt);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, message);
            preparedStatement.setString(3, fileName);
            preparedStatement.setString(4, migrated_docid);
            preparedStatement.setString(5, docid);

            update = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(preparedStatement);
            releaseResources(_conn);

        }
        return update > 0;
    }

    public synchronized boolean updateStatus(String docid, String status, String message) throws Exception {
        int update = 0;
        PreparedStatement preparedStatement = null;
        Connection _conn = null;
        try {
            _conn = initConn();
            String _sqlStmt = "update MIG_MIGRATION_DATA set status =?,message=? where docid=?";
            preparedStatement = _conn.prepareStatement(_sqlStmt);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, message);
            preparedStatement.setString(3, docid);

            update = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(preparedStatement);
            releaseResources(_conn);

        }
        return update > 0;
    }
    public synchronized boolean updateStatusDR(String docid, String status, String message) throws Exception {
        int update = 0;
        PreparedStatement preparedStatement = null;
        Connection _conn = null;
        try {
            _conn = initConn();
            String _sqlStmt = "update MIG_DELIVERY_REPORT_DATA set status =?,message=? where docid=?";
            preparedStatement = _conn.prepareStatement(_sqlStmt);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, message);
            preparedStatement.setString(3, docid);

            update = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(preparedStatement);
            releaseResources(_conn);

        }
        return update > 0;
    }

    public synchronized boolean updateInbasketStatus(int departmentId, String status, String message) throws Exception {
        int update = 0;
        PreparedStatement preparedStatement = null;
        Connection _conn = null;
        try {
            _conn = initConn();
            String _sqlStmt = "update MIG_INBASETDATA set status =?,COMMENTS=? where DEPARTMENTID=?";
            preparedStatement = _conn.prepareStatement(_sqlStmt);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, message);
            preparedStatement.setInt(3, departmentId);

            update = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(preparedStatement);
            releaseResources(_conn);

        }
        return update > 0;
    }


//    public synchronized boolean updateDocIDMapping(String old_docid, String new_docid) throws Exception {
//        int update = 0;
//        PreparedStatement preparedStatement = null;
//        Connection _conn = null;
//        try {
//            _conn = initConn();
//            String _sqlStmt = "update MIG_MIGRATION_DATA set migrated_docid =? where docid =?";
//            preparedStatement = _conn.prepareStatement(_sqlStmt);
//            preparedStatement.setString(1, new_docid);
//            preparedStatement.setString(2, old_docid);
//
//            update = preparedStatement.executeUpdate();
//
//
//        } catch (SQLException e) {
//            logger.error(e.getMessage(), e);
//        } finally {
//            safeClose(preparedStatement);
//            releaseResources(_conn);
//
//        }
//        return update > 0;
//    }

    public synchronized boolean updateWorkitemsStatus(String id, String status, String message) throws Exception {
        int update = 0;
        PreparedStatement preparedStatement = null;
        Connection _conn = null;

        try {
            _conn = initConn();
            String _sqlStmt = "update MIG_WORKITEMS set status =?,message=? where id=?";
            preparedStatement = _conn.prepareStatement(_sqlStmt);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, message);
            preparedStatement.setInt(3, Integer.valueOf(id));

            update = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(preparedStatement);
            releaseResources(_conn);

        }
        return update > 0;
    }


    private synchronized final Connection initConn() throws SQLException, ClassNotFoundException {

//        try {
//            return C3P0DS.getCon();
//        } catch (SQLException e) {
//            logger.error(e);
//        } catch (PropertyVetoException e) {
//            logger.error(e);
//
//        }
//        return null;
//        if (_conn != null && !_conn.isClosed())
//            return;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");


        Connection _conn = null;
        String connectionUrl = "jdbc:sqlserver://10.50.16.139:52169;"
                + "database=MOAMALATTest;"
                + "user=moamalat1;"
                + "password=moamalat1;";

        _conn = DriverManager.getConnection(connectionUrl);
        System.out.println("connection");

        return _conn;
    }

    private synchronized final void safeClose(CallableStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private synchronized final void safeClose(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private synchronized final void safeClose(ResultSet _rstSet) {
        try {
            if (_rstSet != null) {
                _rstSet.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private synchronized final void releaseResources(Connection _conn) {
        if (_conn != null) {
            try {
                _conn.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public synchronized List<InbasketData> getInbasketData() throws Exception {
        List<InbasketData> workitemsData = new ArrayList<>();
        String _sqlStmt = "{ call MIG_GET_INBASETDATA() }";
        Connection _conn = null;
        CallableStatement _callStmt = null;
        try {
            _conn = initConn();
            _callStmt = _conn.prepareCall(_sqlStmt);
            ResultSet resultSet = _callStmt.executeQuery();
            while (resultSet.next()) {

                String queueName = resultSet.getString("QUEUESHORTNAME");
                String inBasketName = resultSet.getString("DEPARTMENTNAMEAR");
                String roleName = resultSet.getString("DEPARTMENTNAMEAR");
                Integer departmentId = resultSet.getInt("DEPARTMENTID");

                List<String> roleUsersIds = this.getInbasketUsersIds(departmentId);
//                List<String> roleUsersIds = new ArrayList<>();
//                roleUsersIds.add("fntadmin");
                workitemsData.add(new InbasketData(queueName, inBasketName, roleName,
                        String.valueOf(departmentId), roleUsersIds));
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(_callStmt);
            releaseResources(_conn);
        }
        return workitemsData;
    }


    public synchronized List<FixDocumentRelation> getOldParentDocRelationData() throws Exception {
        List<FixDocumentRelation> fixDocumentRelations = new ArrayList<>();
        String _sqlStmt = "{ call FIX_OLD_DOCUMENT_RELATION() }";
        Connection _conn = null;
        CallableStatement _callStmt = null;
        try {
            _conn = initConn();
            _callStmt = _conn.prepareCall(_sqlStmt);
            ResultSet resultSet = _callStmt.executeQuery();
            while (resultSet.next()) {
                int cn =resultSet.getInt("cn");
                int hy =resultSet.getInt("hy");
                int typeid =resultSet.getInt("typeid");
                String docid =resultSet.getString("docId");

                fixDocumentRelations.add(new FixDocumentRelation(cn,hy,typeid,docid));
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(_callStmt);
            releaseResources(_conn);
        }
        return fixDocumentRelations;
    }


    public synchronized List<FixDocumentRelation> getFixDocRelationData() throws Exception {
        List<FixDocumentRelation> fixDocumentRelations = new ArrayList<>();
        String _sqlStmt = "{ call MIG_GET_FIX_DOCUMENT_RELATION() }";
        Connection _conn = null;
        CallableStatement _callStmt = null;
        try {
            _conn = initConn();
            _callStmt = _conn.prepareCall(_sqlStmt);
            ResultSet resultSet = _callStmt.executeQuery();
            while (resultSet.next()) {
                int cn =resultSet.getInt("cn");
                int hy =resultSet.getInt("hy");
                int typeid =resultSet.getInt("typeid");
                String docid =resultSet.getString("docid");
                String fileName =resultSet.getString("fileName");
                String docTitle =resultSet.getString("docTitle");
                String classId =resultSet.getString("cn");
                String mimeType =resultSet.getString("classId");

                fixDocumentRelations.add(new FixDocumentRelation(cn,hy,typeid,docid,fileName,docTitle,classId,mimeType));
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(_callStmt);
            releaseResources(_conn);
        }
        return fixDocumentRelations;
    }

    public synchronized List<FixDocumentRelation> getOldChildNoParent() throws Exception {
        List<FixDocumentRelation> fixDocumentRelations = new ArrayList<>();
        String _sqlStmt = "{ call FIX_OLD_CHILD_DOCUMENT_RELATION() }";
        Connection _conn = null;
        CallableStatement _callStmt = null;
        try {
            _conn = initConn();
            _callStmt = _conn.prepareCall(_sqlStmt);
            ResultSet resultSet = _callStmt.executeQuery();
            while (resultSet.next()) {
                int cn =resultSet.getInt("cn");
                int hy =resultSet.getInt("hy");
                int typeid =resultSet.getInt("typeid");
                String docid =resultSet.getString("docid");

                fixDocumentRelations.add(new FixDocumentRelation(cn,hy,typeid,docid));
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(_callStmt);
            releaseResources(_conn);
        }
        return fixDocumentRelations;
    }

    public synchronized List<FixDocumentRelation> getNoParentOld() throws Exception {
        List<FixDocumentRelation> fixDocumentRelations = new ArrayList<>();
        String _sqlStmt = "{ call MIG_GET_FIX_NO_PARENT_OLD() }";
        Connection _conn = null;
        CallableStatement _callStmt = null;
        try {
            _conn = initConn();
            _callStmt = _conn.prepareCall(_sqlStmt);
            ResultSet resultSet = _callStmt.executeQuery();
            while (resultSet.next()) {
                int cn =resultSet.getInt("cn");
                int hy =resultSet.getInt("hy");
                int typeid =resultSet.getInt("typeid");
                String docid =resultSet.getString("docid");
                String fileName =resultSet.getString("fileName");
                String docTitle =resultSet.getString("docTitle");
                String classId =resultSet.getString("cn");
                String mimeType =resultSet.getString("classId");

                fixDocumentRelations.add(new FixDocumentRelation(cn,hy,typeid,docid,fileName,docTitle,classId,mimeType));
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(_callStmt);
            releaseResources(_conn);
        }
        return fixDocumentRelations;
    }
    public synchronized List<FixDocumentRelation> getDeliveryReports() throws Exception {
        List<FixDocumentRelation> fixDocumentRelations = new ArrayList<>();
        String _sqlStmt = "{ call MIG_GET_DELIVERY_REPORT_DATA() }";
        Connection _conn = null;
        CallableStatement _callStmt = null;
        try {
            _conn = initConn();
            _callStmt = _conn.prepareCall(_sqlStmt);
            ResultSet resultSet = _callStmt.executeQuery();
            while (resultSet.next()) {
                int deliveryReportNumber =resultSet.getInt("deliveryReportNumber");
                int cn =resultSet.getInt("cn");
                int hy =resultSet.getInt("hy");
                int typeid =resultSet.getInt("typeid");
                String docid =resultSet.getString("docid");
                String fileName =resultSet.getString("fileName");
                String mimeType =resultSet.getString("mimeType");

                fixDocumentRelations.add(new FixDocumentRelation(deliveryReportNumber,cn,hy,typeid,docid,fileName,
                        "","",mimeType));
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(_callStmt);
            releaseResources(_conn);
        }
        return fixDocumentRelations;
    }

    private List<String> getInbasketUsersIds(Integer departmentId) {
        System.out.println("getInbasketUsersIds = " + departmentId);
        List<String> usersIds = new ArrayList<String>();
        String sqlStmt = "select UserID from MOAMALATTest.dbo.MIG_USERS_QUEUES where DepartmentId = ? ";
        CallableStatement callStmt = null;
        Connection _conn = null;
        LDAPManager ldapManager = new LDAPManager();
        System.out.println("-----------------1");
        try {
            _conn = initConn();
            System.out.println("-----------------2");
            callStmt = _conn.prepareCall(sqlStmt);
            System.out.println("-----------------3");
            callStmt.setInt(1, departmentId);
            ResultSet resultSet = callStmt.executeQuery();
            System.out.println("-----------------4");
            while (resultSet.next()) {
                System.out.println("-----------------5");
                String username = resultSet.getString("UserID");
                if (ldapManager.isUserExistsInLDAP(username)) {
                    usersIds.add(username);
                    System.out.println("username exists in AD " + username);
                }else {
                    System.out.println("username not exists in AD " + username);

                }

            }
//            usersIds.add("fntadmin");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(callStmt);
            releaseResources(_conn);
        }
        System.out.println("-----------------6");
        return usersIds;
    }

    public int getBatchIndex() {
        return batchIndex;
    }

    public void nextBatchIndex() {
        this.batchIndex++;
    }

    public void resetBatchIndex() {
        this.batchIndex = 0;
    }
}
