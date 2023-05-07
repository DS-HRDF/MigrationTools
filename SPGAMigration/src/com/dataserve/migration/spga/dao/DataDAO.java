/*
 * Mohammad Awwad
 * */
package com.dataserve.migration.spga.dao;

import com.dataserve.migration.spga.objects.InbasketData;
import com.dataserve.migration.spga.objects.MigrationData;
import com.dataserve.migration.spga.objects.WorkitemsData;
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
        List<MigrationData> migrationData = Collections.synchronizedList(new ArrayList());
        System.out.println("getMigrationData() 1  .... ");
        String _sqlStmt = "{ call MIG_GET_MIGRATION_DATA() }";
        Connection _conn = null;
        CallableStatement _callStmt = null;

        try {
            _conn = initConn();
            _callStmt = _conn.prepareCall(_sqlStmt);
            ResultSet resultSet = _callStmt.executeQuery();
            while (resultSet.next()) {

//                String docid = resultSet.getString("docid");

                String cn = resultSet.getString("cn");
                String hy = resultSet.getString("hy");
                String typeid = resultSet.getString("typeid");
                String docid = resultSet.getString("docid");
                String fileName = resultSet.getString("fileName");
                String docTitle = resultSet.getString("docTitle");
                String classId = resultSet.getString("classId");
                String mimeType = resultSet.getString("mimeType");
                Date createDate = resultSet.getDate("createDate");
                String username = resultSet.getString("username");
                String status = resultSet.getString("status");
                String message = resultSet.getString("message");
                String attachmentPath = resultSet.getString("documentPath");
                int attachmentSize = resultSet.getInt("documentSize");
                String migratedDocId = resultSet.getString("migrated_docid");
                String createDateStr = createDate!=null?createDate.toString():"";
//                System.out.println("fileName ====> "+fileName);
//                System.out.println("attachmentPath ====> "+attachmentPath);
//                System.out.println("attachmentPath ====> "+attachmentSize);
//
//                System.out.println(attachmentPath);
                migrationData.add(new MigrationData(cn, hy, typeid, docid, fileName, docTitle, classId, mimeType,
                        createDateStr, username, status, message, attachmentPath,String.valueOf(attachmentSize), migratedDocId));
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(_callStmt);
            releaseResources(_conn);
        }
        return migrationData;
    }


//    public synchronized List<MigrationData> getMigrationData1() throws Exception {
////        List<MigrationData> migrationData = new ArrayList<>();
//        List<MigrationData> migrationData = Collections.synchronizedList(new ArrayList());
////        String _sqlStmt = "{ call MIG_GET_MIGRATION_DATA() }";
//        String _sqlStmt = "{ call MIG_GET_MIGRATION_DATA2() }";
//        Connection _conn = null;
//        CallableStatement _callStmt = null;
//
//        try {
//            _conn = initConn();
//            _callStmt = _conn.prepareCall(_sqlStmt);
//            ResultSet resultSet = _callStmt.executeQuery();
//            while (resultSet.next()) {
//
//                String docid = resultSet.getString("docid");
//                String cn = resultSet.getString("cn");
//                String hy = resultSet.getString("hy");
//                String typeid = resultSet.getString("typeid");
//                String fileName = resultSet.getString("fileName");
//                String docTitle = resultSet.getString("docTitle");
//                String classId = resultSet.getString("classId");
//                String mimeType = resultSet.getString("mimeType");
//                Date createDate = resultSet.getDate("createDate");
//                String username = resultSet.getString("username");
//                String status = resultSet.getString("status");
//                String message = resultSet.getString("message");
//                String migTransactionId = resultSet.getString("TransactionId");
//                String createDateStr = createDate!=null?createDate.toString():"";
//
//                migrationData.add(new MigrationData(cn, hy, typeid, docid, fileName, docTitle, classId, mimeType,
//                        createDateStr, username, status, message, migTransactionId));
//            }
//
//
//        } catch (SQLException e) {
//            logger.error(e.getMessage(), e);
//        } finally {
//            safeClose(_callStmt);
//            releaseResources(_conn);
//        }
//        return migrationData;
//    }

    public synchronized List<WorkitemsData> getWorkitemObject() throws Exception {
        List<WorkitemsData> workitemsData = new ArrayList<>();
        //String _sqlStmt = "{ call MIG_GET_WORKITEMOBJECT(?) }";
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
                String MONumber = String.valueOf(resultSet.getInt("MONumber"));
                String CorrespondenceNumber = String.valueOf(resultSet.getInt("CorrespondenceNumber"));
                String externalUnit = resultSet.getString("ExternalUnit");


                workitemsData.add(new WorkitemsData(id, F_BoundUser, "", F_Subject, CorrespondenceDate, FromUser, HijricYear,
                        ImportanceLevel, isRead, ItemType, Originator, ReceivedOnHijriDate, SequenceNumber, UrgencyLevel, departmentID, queueName,
                        username, forwardType, status, message, participantType, MONumber, CorrespondenceNumber,id,externalUnit));
            }


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(_callStmt);
            releaseResources(_conn);
        }
        return workitemsData;
    }

    public synchronized boolean updateStatus(String docid, String status, String message, String fileName,String migrated_docid) throws Exception {
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

    public synchronized boolean updateInbasketStatus(int departmentId, String status, String message) throws Exception {
        int update = 0;
        PreparedStatement preparedStatement = null;
        Connection _conn = null;
        try {
            _conn = initConn();
            String _sqlStmt = "update MIG_INBASKETDATA set status =?,COMMENTS=? where DEPARTMENTID=?";
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

        System.out.println("AttachmentsDAO");

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");


        Connection _conn = null;
        String connectionUrl = "jdbc:sqlserver://10.70.14.34:1433;"
                + "database=MOAMALAT;"
                + "user=db1;"
                + "password=db1;";

        _conn = DriverManager.getConnection(connectionUrl);
        System.out.println("connection done ....... ");

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
        String _sqlStmt = "{ call MIG_GET_INBASKETDATA() }";
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

    private List<String> getInbasketUsersIds(Integer departmentId) {
        List<String> usersIds = new ArrayList<String>();
        String sqlStmt = "select Username from MIG_PI_USERS_DEPARTMENTS_VIEW where DepartmentId = ? ";
        CallableStatement callStmt = null;
        Connection _conn = null;
        try {
            _conn = initConn();
            callStmt = _conn.prepareCall(sqlStmt);
            callStmt.setInt(1, departmentId);
            ResultSet resultSet = callStmt.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                usersIds.add(username);
//                Employee e = new Employee();
//                e.getDirectManager();
            }
            usersIds.add("HDF.SRV.SD.FNT.X1");

        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        } finally {
            safeClose(callStmt);
            releaseResources(_conn);
        }
        return usersIds;
    }

//    public int getBatchIndex() {
//        return batchIndex;
//    }
//
//    public void nextBatchIndex() {
//        this.batchIndex++;
//    }
//
//    public void resetBatchIndex() {
//        this.batchIndex = 0;
//    }
}
