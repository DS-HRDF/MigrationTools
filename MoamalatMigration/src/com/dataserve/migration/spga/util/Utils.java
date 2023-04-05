package com.dataserve.migration.spga.util;

import com.dataserve.migration.spga.objects.MigrationData;
import com.dataserve.migration.spga.objects.WorkitemObject;
import com.dataserve.migration.spga.objects.WorkitemsData;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

/************************
 *
 * Created By Mohammad Awwad 02-June-2020
 *
 ************************/
public class Utils {

    final static Logger logger = Logger.getLogger(Utils.class);
    static Properties properties = new Properties();

    public static int threadID;

    private static void initProperties() {
        try {
            properties.load(new FileInputStream("d:\\configs.properties"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    public static void showDialog(String title, String body) {
        Platform.runLater(
                () -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(title);
                    alert.setHeaderText(null);
                    alert.setContentText(body);

                    alert.showAndWait();
                }
        );
    }

    public synchronized static void runInUIThread(ICallback iCallback) {
        Platform.runLater(
                () -> {
                    iCallback.run();
                }
        );
    }

    public static String getConfigs(String key) {
        if (properties.size() == 0) {
            initProperties();
        }
        return properties.getProperty(key);
    }

    public static List<List<MigrationData>> getArrayOfDataLists(List<MigrationData> migrationData, int numberOfThreads) {
        int sizeOfEachList = migrationData.size() / numberOfThreads;
        List<List<MigrationData>> lists = new ArrayList<>(numberOfThreads);

        int startIndex = 0;
        int endIndex = sizeOfEachList;
        int i = 0;
        while (i < numberOfThreads) {
            lists.add(migrationData.subList(startIndex, endIndex));
            i++;
            startIndex = endIndex;
            if (i == numberOfThreads - 1) {
                endIndex = migrationData.size();
            } else {
                endIndex = endIndex + sizeOfEachList;
            }
        }

        return lists;
    }


    public static List<List<WorkitemsData>> getArrayOfDataListsWorkitems(List<WorkitemsData> workitemsData, int numberOfThreads) {
        int sizeOfEachList = workitemsData.size() / numberOfThreads;
        List<List<WorkitemsData>> lists = new ArrayList<>(numberOfThreads);

        int startIndex = 0;
        int endIndex = sizeOfEachList;
        int i = 0;
        while (i < numberOfThreads) {
            lists.add(workitemsData.subList(startIndex, endIndex));
            i++;
            startIndex = endIndex;
            if (i == numberOfThreads - 1) {
                endIndex = workitemsData.size();
            } else {
                endIndex = endIndex + sizeOfEachList;
            }
        }

        return lists;
    }

    public static boolean isThreadsAlive(List<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    public static String formatNumber(int value) {
        double amount = Double.parseDouble(value + "");
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount);
    }

    public static long calcAverage(Map<String, Long> timePerTransMap) {
        return (long) timePerTransMap.values().stream().mapToLong(Long::valueOf).average().getAsDouble();

    }

    public static long calcSUM(Map<String, Long> timePerTransMap) {
        return timePerTransMap.values().stream().mapToLong(Long::valueOf).sum();
    }

    public static long millisecondsToHour(long milli) {
        return milli / (1000 * 60 * 60);
    }

    public static long millisecondsToMinutes(long milli) {
        return milli / (1000 * 60);
    }
}
