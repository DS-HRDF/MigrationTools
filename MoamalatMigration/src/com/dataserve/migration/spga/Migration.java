package com.dataserve.migration.spga;

import com.dataserve.migration.spga.dao.CEDAO;
import com.dataserve.migration.spga.dao.CEDaoOld;
import com.dataserve.migration.spga.dao.DataDAO;
import com.dataserve.migration.spga.dao.FilesDAO;
import com.dataserve.migration.spga.objects.Attachment;
import com.dataserve.migration.spga.objects.MigrationData;
import com.dataserve.migration.spga.objects.WorkitemObject;
import com.dataserve.migration.spga.objects.WorkitemsData;
import com.dataserve.migration.spga.util.Const;
import com.dataserve.migration.spga.util.PEUtils;
import com.dataserve.migration.spga.util.Shared;
import com.dataserve.migration.spga.util.Utils;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Document;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/************************
 *
 * Created By Mohammad Awwad 02-June-2020
 *
 ************************/
public class Migration {
    final static Logger logger = Logger.getLogger(Migration.class);
    public static boolean STOP = false;

//    public synchronized void mofMigration(List<MigrationData> migrationDataList,int ceConnection) {
//        CEDAO cedao = new CEDAO();
//        cedao.setCeConnection(ceConnection);
//        DataDAO dataDAO = new DataDAO();
//        int index = 0;
//        int lastIndex = 0;
//        int resetSpeedCalc = 10;
//        int resetSpeedIndex = 1;
//        int speedTimeSum = 0;
////        int updateEvery = migrationDataList.size() / 5;
//        int updateEvery = 5;
//
//        long timePerTrans = 0, perHour = 0, perMinutes = 0;
//
//        for (MigrationData data : migrationDataList) {
//            try {
//                long before = System.currentTimeMillis();
//
//                Attachment attachment = cedao.getAttachment(data.getDocid());
//                String fileName = FilesDAO.SaveFile(attachment.getBytes(), data.getDocid(), attachment.getMimetype());
//                try {
//                    dataDAO.updateStatus(data.getDocid(), Const.STATUS_DONE, "DONE", fileName);
//                    data.setStatus(Const.STATUS_DONE);
//                    data.setMessage(Const.STATUS_DONE);
//                    data.setFileName(fileName);
//                    Shared.mofController.refreshData();
//                } catch (Exception e) {
//                    logger.error(e.getMessage(), e);
//                }
//                long after = System.currentTimeMillis();
//                long difference = after - before;
//
//
//                speedTimeSum += difference;
//                resetSpeedIndex++;
//                if (resetSpeedIndex == resetSpeedCalc) {
//                    timePerTrans = speedTimeSum / resetSpeedCalc;
//                    perMinutes = 1000 * 60 / timePerTrans;
//                    perHour = 1000 * 60 * 60 / timePerTrans;
//
//                    resetSpeedIndex = 1;
//                    speedTimeSum = 0;
//                }
//
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//                try {
//                    dataDAO.updateStatus(data.getDocid(), Const.STATUS_ERROR, e.getMessage(), "");
//                    data.setStatus(Const.STATUS_ERROR);
//                    data.setMessage(e.getMessage());
//                    Shared.mofController.refreshData();
//                } catch (Exception ex) {
//                    logger.error(ex.getMessage(), ex);
//                }
//            }
//            if (index == lastIndex + updateEvery) {
//                updateUIMOF(migrationDataList, Thread.currentThread().getName(), timePerTrans, perHour, perMinutes);
//                lastIndex = index;
//            }
//            index++;
//            if (STOP) {
//                updateUIMOF(migrationDataList, Thread.currentThread().getName(), timePerTrans, perHour, perMinutes);
//                cedao.setObjectStore(null);
//                break;
//            }
//        }
//        updateUIMOF(migrationDataList, Thread.currentThread().getName(), timePerTrans, perHour, perMinutes);
//
//        int undone = migrationDataList.stream().filter(e -> e.getStatus().equals("UNDONE")).collect(Collectors.toList()).size();
//        int error = migrationDataList.stream().filter(e -> e.getStatus().equals("ERROR")).collect(Collectors.toList()).size();
//        String errorMsg = "";
//        if (error > 0) {
//            errorMsg += "error documents: " + error;
//        }
//        if (undone > 0) {
//            Utils.showDialog("Migration Stops " + Thread.currentThread().getName(), "Migration stops with " + undone + " documents left " + errorMsg);
//        } else {
//            Utils.showDialog("Migration Completed" + Thread.currentThread().getName(), "Migration Completed " + errorMsg);
//        }
//    }

    public void spgaMigration(List<MigrationData> migrationDataList) {
        CEDAO cedao = new CEDAO();
        CEDaoOld cedaoOld = new CEDaoOld();

        DataDAO dataDAO = new DataDAO();
        int index = 0;
        int lastIndex = 0;
        int resetSpeedCalc = 10;
        int resetSpeedIndex = 1;
        int speedTimeSum = 0;

        long timePerTrans = 0, perHour = 0, perMinutes = 0;
//        int updateEvery = migrationDataList.size() / 5;
        int updateEvery = 2;
        long before;
        long after;
        long difference;
        for (MigrationData data : migrationDataList) {
            try {

                before = System.currentTimeMillis();

                Attachment attachment = cedaoOld.getAttachment(data.getDocid());

                String migratedDocId = cedao.addDocument(data, attachment.getBytes());

                try {
                    dataDAO.updateStatus(data.getDocid(), Const.STATUS_DONE, "DONE", "",migratedDocId);
                    data.setStatus(Const.STATUS_DONE);
                    data.setMessage(Const.STATUS_DONE);
                    Shared.spgaController.refreshData();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }

                after = System.currentTimeMillis();
                difference = after - before;


                speedTimeSum += difference;
                resetSpeedIndex++;
                if (resetSpeedIndex == resetSpeedCalc) {
                    timePerTrans = speedTimeSum / resetSpeedCalc;
                    perMinutes = 1000 * 60 / timePerTrans;
                    perHour = 1000 * 60 * 60 / timePerTrans;

                    resetSpeedIndex = 1;
                    speedTimeSum = 0;
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                try {
                    dataDAO.updateStatus(data.getDocid(), Const.STATUS_ERROR, e.getMessage());
                    data.setStatus(Const.STATUS_ERROR);
                    data.setMessage(e.getMessage());
                    Shared.spgaController.refreshData();
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
            if (index == lastIndex + updateEvery) {
                updateUISPGA(migrationDataList, Thread.currentThread().getName(), timePerTrans, perHour, perMinutes);
                lastIndex = index;
            }
            index++;
            if (STOP) {
                updateUISPGA(migrationDataList, Thread.currentThread().getName(), timePerTrans, perHour, perMinutes);
                cedao.setObjectStore(null);
                break;
            }
        }

        int undone = migrationDataList.stream().filter(e -> e.getStatus().equals("UNDONE")).collect(Collectors.toList()).size();
        int error = migrationDataList.stream().filter(e -> e.getStatus().equals("ERROR")).collect(Collectors.toList()).size();
        String errorMsg = "";
        if (error > 0) {
            errorMsg += "error documents: " + error;
        }
        if (undone > 0) {
            Utils.showDialog("Migration Stops", "Migration stops with " + undone + " documents left " + errorMsg);
        } else {
            Utils.showDialog("Migration Completed", "Migration Completed " + errorMsg);
        }

    }
    public void validateAndFix(List<MigrationData> migrationDataList) {
        CEDAO cedao = new CEDAO();
        CEDaoOld cedaoOld = new CEDaoOld();
        logger.info("start validateAndFix");

        DataDAO dataDAO = new DataDAO();
        int index = 0;
        int lastIndex = 0;
        int resetSpeedCalc = 10;
        int resetSpeedIndex = 1;
        int speedTimeSum = 0;

        long timePerTrans = 0, perHour = 0, perMinutes = 0;
//        int updateEvery = migrationDataList.size() / 5;
        int updateEvery = 2;
        long before;
        long after;
        long difference;
        for (MigrationData data : migrationDataList) {
            try {
                before = System.currentTimeMillis();
                logger.info(" validateAndFix ---- 1 data.getMigDocId() = "+data.getMigDocId());
                Attachment attachment = cedaoOld.getAttachment(data.getDocid());// get it from SAHL+

                Document document = cedao.getMigOldDocId(Integer.parseInt(data.getMigTransactionId()),
                        Integer.parseInt(data.getHy()),Integer.parseInt(data.getType()),data.getDocid());
//                Document document = cedao.fetchDocById(data.getDocid()); //get it from Moamalat
//                Document document111 = cedao.fetchDocById(data.getDocid()); //get it from Moamalat
                logger.info(" validateAndFix ---- 222 document.get_Id() = "+document.get_Id());
                boolean contentExists = cedao.isContentExists(document);// check if exists in Moamalat
                logger.info(" validateAndFix ---- 2 contentExists = "+contentExists);
                String status = "";
                if (!contentExists){ // if not
                    logger.info(" validateAndFix ---- 3 contentExists = "+contentExists);
                    cedao.updateContentElement(document,data.getFileName(),attachment.getMimetype(),attachment.getBytes());
                    status = Const.STATUS_FIXED;
                } else {
                    status = Const.STATUS_VALID;

                    document.getProperties().putValue("FixStatus", Integer.valueOf(200));//Valid

                    document.save(RefreshMode.REFRESH);
                    logger.info(" validateAndFix ---- 4  Already Valid status ="+status);

                }

                try {
                    dataDAO.updateStatus(data.getDocid(), status, status);
                    data.setStatus(status);
                    data.setMessage(status);
                    Shared.spgaController.refreshData();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }

                after = System.currentTimeMillis();
                difference = after - before;


                speedTimeSum += difference;
                resetSpeedIndex++;
                if (resetSpeedIndex == resetSpeedCalc) {
                    timePerTrans = speedTimeSum / resetSpeedCalc;
                    perMinutes = 1000 * 60 / timePerTrans;
                    perHour = 1000 * 60 * 60 / timePerTrans;

                    resetSpeedIndex = 1;
                    speedTimeSum = 0;
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                try {
                    dataDAO.updateStatus(data.getDocid(), Const.STATUS_ERROR, e.getMessage());
                    data.setStatus(Const.STATUS_ERROR);
                    data.setMessage(e.getMessage());
                    Shared.spgaController.refreshData();
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
            if (index == lastIndex + updateEvery) {
                updateUISPGA(migrationDataList, Thread.currentThread().getName(), timePerTrans, perHour, perMinutes);
                lastIndex = index;
            }
            index++;
            if (STOP) {
                updateUISPGA(migrationDataList, Thread.currentThread().getName(), timePerTrans, perHour, perMinutes);
                cedao.setObjectStore(null);
                break;
            }
        }

        int undone = migrationDataList.stream().filter(e -> e.getStatus().equals("UNDONE")).collect(Collectors.toList()).size();
        int error = migrationDataList.stream().filter(e -> e.getStatus().equals("ERROR")).collect(Collectors.toList()).size();
        String errorMsg = "";
        if (error > 0) {
            errorMsg += "error documents: " + error;
        }
        if (undone > 0) {
            Utils.showDialog("Migration Stops", "Migration stops with " + undone + " documents left " + errorMsg);
        } else {
            Utils.showDialog("Migration Completed", "Migration Completed " + errorMsg);
        }

    }

    public void workitemsMigration(List<WorkitemsData> workitemsData) {
        CEDAO cedao = new CEDAO();
        DataDAO dataDAO = new DataDAO();
        int index = 0;
        int lastIndex = 0;
        int resetSpeedCalc = 10;
        int resetSpeedIndex = 1;
        int speedTimeSum = 0;

        long timePerTrans = 0, perHour = 0, perMinutes = 0;
//        int updateEvery = workitemsData.size() / 5;
        int updateEvery = 2;
        PEUtils peUtils = new PEUtils();

        for (WorkitemsData data : workitemsData) {
            try {
                long before = System.currentTimeMillis();


                try {

                    WorkitemObject workitemObject = WorkitemObject.getWorkItemData(data);
                    peUtils.launchCorrespondence(workitemObject);
                    dataDAO.updateWorkitemsStatus(data.getId(), Const.STATUS_DONE, "DONE");
                    data.setStatus(Const.STATUS_DONE);
                    data.setMessage(Const.STATUS_DONE);
                    Shared.workitemsController.refreshData();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                long after = System.currentTimeMillis();
                long difference = after - before;


                speedTimeSum += difference;
                resetSpeedIndex++;
                if (resetSpeedIndex == resetSpeedCalc) {
                    timePerTrans = speedTimeSum / resetSpeedCalc;
                    perMinutes = 1000 * 60 / timePerTrans;
                    perHour = 1000 * 60 * 60 / timePerTrans;

                    resetSpeedIndex = 1;
                    speedTimeSum = 0;
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                try {
                    dataDAO.updateWorkitemsStatus(data.getId(), Const.STATUS_ERROR, e.getMessage());
                    data.setStatus(Const.STATUS_ERROR);
                    data.setMessage(e.getMessage());
                    Shared.workitemsController.refreshData();
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
            if (index == lastIndex + updateEvery) {
                updateUIWorkitems(workitemsData, Thread.currentThread().getName(), timePerTrans, perHour, perMinutes);
                lastIndex = index;
            }
            index++;
            if (STOP) {
                updateUIWorkitems(workitemsData, Thread.currentThread().getName(), timePerTrans, perHour, perMinutes);
                cedao.setObjectStore(null);
                break;
            }
        }

        int undone = workitemsData.stream().filter(e -> e.getStatus().equals("UNDONE")).collect(Collectors.toList()).size();
        int error = workitemsData.stream().filter(e -> e.getStatus().equals("ERROR")).collect(Collectors.toList()).size();
        String errorMsg = "";
        if (error > 0) {
            errorMsg += "error documents: " + error;
        }
        if (undone > 0) {
            Utils.showDialog("Migration Stops", "Migration stops with " + undone + " documents left " + errorMsg);
        } else {
            Utils.showDialog("Migration Completed", "Migration Completed " + errorMsg);
        }

    }


    private String getStatistics(List<MigrationData> migrationData, long timePerTrans, long perHour, long perMinutes) {
        StringBuilder sb = new StringBuilder("Thread" + Thread.currentThread().getName());

        int totalDocs = migrationData.size();
        int doneDocs = migrationData.stream().filter(e -> e.getStatus().equals("DONE")).collect(Collectors.toList()).size();
        int remainDocs = migrationData.stream().filter(e -> e.getStatus().equals("UNDONE")).collect(Collectors.toList()).size();
        int errorDocs = migrationData.stream().filter(e -> e.getStatus().equals("ERROR")).collect(Collectors.toList()).size();

        sb.append("     Total Docs:" + Utils.formatNumber(totalDocs));
        sb.append(" , Done Docs:" + Utils.formatNumber(doneDocs));
        sb.append(" , Remain Docs:" + Utils.formatNumber(remainDocs));
        sb.append(" , Error Docs:" + Utils.formatNumber(errorDocs));

        long totalTimiInMilli = timePerTrans * totalDocs;
        if (Utils.millisecondsToMinutes(totalTimiInMilli) > 60) { // to get the minutes, more than 60 minutes = hours

            sb.append(" , Total Time:" + Utils.millisecondsToHour(totalTimiInMilli) + " Hour(s)");
        } else { //Minutes
            sb.append(" , Total Time:" + Utils.millisecondsToMinutes(totalTimiInMilli) + " Minutes");
        }

        long remainTimiInMilli = timePerTrans * remainDocs;
        if (Utils.millisecondsToMinutes(remainTimiInMilli) > 60) { // to get the minutes, more than 60 minutes = hours

            sb.append(" , Remain Time:" + Utils.millisecondsToHour(remainTimiInMilli) + " Hour(s)");
        } else { //Minutes
            sb.append(" , Remain Time:" + Utils.millisecondsToMinutes(remainTimiInMilli) + " Minutes");
        }

        sb.append(" , Docs Per Minutes:" + Utils.formatNumber((int) perMinutes));
        sb.append(" , Docs Per Hour:" + Utils.formatNumber((int) perHour));
        return sb.toString();
    }

    private double getPercentage(List<MigrationData> migrationData) {
        int done = migrationData.stream().filter(e -> e.getStatus().equals("DONE")).collect(Collectors.toList()).size();
        int total = migrationData.size();

        double percentage = (done * 100.0) / total;
        return percentage / 100;
    }


    private String getStatisticsWorkitems(List<WorkitemsData> workitemObjects, long timePerTrans, long perHour, long perMinutes) {
        StringBuilder sb = new StringBuilder("Thread" + Thread.currentThread().getName());

        int totalDocs = workitemObjects.size();
        int doneDocs = workitemObjects.stream().filter(e -> e.getStatus().equals("DONE")).collect(Collectors.toList()).size();
        int remainDocs = workitemObjects.stream().filter(e -> e.getStatus().equals("UNDONE")).collect(Collectors.toList()).size();
        int errorDocs = workitemObjects.stream().filter(e -> e.getStatus().equals("ERROR")).collect(Collectors.toList()).size();

        sb.append("     Total Docs:" + Utils.formatNumber(totalDocs));
        sb.append(" , Done Docs:" + Utils.formatNumber(doneDocs));
        sb.append(" , Remain Docs:" + Utils.formatNumber(remainDocs));
        sb.append(" , Error Docs:" + Utils.formatNumber(errorDocs));

        long totalTimiInMilli = timePerTrans * totalDocs;
        if (Utils.millisecondsToMinutes(totalTimiInMilli) > 60) { // to get the minutes, more than 60 minutes = hours

            sb.append(" , Total Time:" + Utils.millisecondsToHour(totalTimiInMilli) + " Hour(s)");
        } else { //Minutes
            sb.append(" , Total Time:" + Utils.millisecondsToMinutes(totalTimiInMilli) + " Minutes");
        }

        long remainTimiInMilli = timePerTrans * remainDocs;
        if (Utils.millisecondsToMinutes(remainTimiInMilli) > 60) { // to get the minutes, more than 60 minutes = hours

            sb.append(" , Remain Time:" + Utils.millisecondsToHour(remainTimiInMilli) + " Hour(s)");
        } else { //Minutes
            sb.append(" , Remain Time:" + Utils.millisecondsToMinutes(remainTimiInMilli) + " Minutes");
        }

        sb.append(" , Docs Per Minutes:" + Utils.formatNumber((int) perMinutes));
        sb.append(" , Docs Per Hour:" + Utils.formatNumber((int) perHour));
        return sb.toString();
    }

    private double getPercentageWorkitems(List<WorkitemsData> workitemObjects) {
        int done = workitemObjects.stream().filter(e -> e.getStatus().equals("DONE")).collect(Collectors.toList()).size();
        int total = workitemObjects.size();

        double percentage = (done * 100.0) / total;
        return percentage / 100;
    }

    private void updateUIMOF(List<MigrationData> migrationDataList, String threadName,
                             long timePerTrans, long perHour, long perMinutes) {
        double percentage = getPercentage(migrationDataList);
        String statistics = getStatistics(migrationDataList, timePerTrans, perHour, perMinutes);
        Platform.runLater(
                () -> {

                    Shared.mofController.updateUI(threadName, timePerTrans, perHour, perMinutes, statistics, percentage);
                    Shared.mofController.updateMain(threadName, timePerTrans, perHour, perMinutes);

                }
        );
    }

    private void updateUISPGA(List<MigrationData> migrationDataList, String threadName,
                              long timePerTrans, long perHour, long perMinutes) {
        double percentage = getPercentage(migrationDataList);
        String statistics = getStatistics(migrationDataList, timePerTrans, perHour, perMinutes);
        Platform.runLater(
                () -> {

                    Shared.spgaController.updateUI(threadName, timePerTrans, perHour, perMinutes, statistics, percentage);
                    Shared.spgaController.updateMain(threadName, timePerTrans, perHour, perMinutes);

                }
        );
    }

    private void updateUIWorkitems(List<WorkitemsData> workitemObjects, String threadName,
                                   long timePerTrans, long perHour, long perMinutes) {
        double percentage = getPercentageWorkitems(workitemObjects);
        String statistics = getStatisticsWorkitems(workitemObjects, timePerTrans, perHour, perMinutes);
        Platform.runLater(
                () -> {

                    Shared.workitemsController.updateUI(threadName, timePerTrans, perHour, perMinutes, statistics, percentage);
                    Shared.workitemsController.updateMain(threadName, timePerTrans, perHour, perMinutes);

                }
        );
    }
}
