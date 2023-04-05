package com.dataserve.migration.spga.controller;

import com.dataserve.migration.spga.Main;
import com.dataserve.migration.spga.Migration;
import com.dataserve.migration.spga.dao.DataDAO;
import com.dataserve.migration.spga.objects.MigrationData;
import com.dataserve.migration.spga.util.ICallback;
import com.dataserve.migration.spga.util.Shared;
import com.dataserve.migration.spga.util.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/************************
 *
 * Created By Mohammad Awwad 01-June-2020
 *
 ************************/
public class SpgaController extends StackPane implements IController {
    final static Logger logger = Logger.getLogger(SpgaController.class);

    private static final boolean STARTED = false;
    @FXML
    private TableView<MigrationData> DataTbl;
    @FXML
    Label mainStatistics;
    @FXML
    private Button refreshBtn;
    @FXML
    private Button startBtn;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressBarLbl;
    @FXML
    VBox thradsVBox;
    List<MigrationData> migrationData;
//    static List<Thread> threads = new ArrayList<>();
    static List<Thread> threads = Collections.synchronizedList(new ArrayList());


    public SpgaController() throws IOException {


        FXMLLoader loader = new FXMLLoader(new Main().getClass().getResource("fxml/Spga.fxml"));
        loader.setController(this);
        Parent parent = loader.load();
        this.getChildren().add(parent);

        TableColumn CNClmn = new TableColumn("CN");
        TableColumn HYClmn = new TableColumn("HY");
        TableColumn typeIDClmn = new TableColumn("TypeID");
        TableColumn docidClmn = new TableColumn("DOCID");
        TableColumn fileNameClmn = new TableColumn("fileNameClmn");


        TableColumn docTitleClmn = new TableColumn("docTitle");
        TableColumn classIdClmn = new TableColumn("classId");
        TableColumn mimeTypeClmn = new TableColumn("mimeType");

        TableColumn createDateClmn = new TableColumn("createDate");
        TableColumn usernameClmn = new TableColumn("username");
        TableColumn statusClmn = new TableColumn("status");
        TableColumn messageClmn = new TableColumn("message");
        TableColumn transIdClmn = new TableColumn("transId");
        docTitleClmn.setPrefWidth(100);
        mimeTypeClmn.setPrefWidth(100);


        final Image doneImg = new Image("/images/done.png");
        final Image errorImg = new Image("/images/error.png");
        final Image pendingImg = new Image("/images/pending.png");
        statusClmn.setCellValueFactory(c -> {
            TableColumn.CellDataFeatures tclmn = (TableColumn.CellDataFeatures) c;
            MigrationData data = (MigrationData) tclmn.getValue();
            if (data.getStatus().equals("DONE")) {
                return new SimpleObjectProperty<>(new ImageView(doneImg));
            } else if (data.getStatus().equals("UNDONE")) {
                return new SimpleObjectProperty<>(new ImageView(pendingImg));
            } else {
                return new SimpleObjectProperty<>(new ImageView(errorImg));
            }
        });

        CNClmn.setCellValueFactory(
                new PropertyValueFactory<>("cn")
        );
        HYClmn.setCellValueFactory(
                new PropertyValueFactory<>("hy")
        );
        typeIDClmn.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );
        docidClmn.setCellValueFactory(
                new PropertyValueFactory<>("docid")
        );
        fileNameClmn.setCellValueFactory(
                new PropertyValueFactory<>("fileName")
        );

        docTitleClmn.setCellValueFactory(
                new PropertyValueFactory<>("docTitle")
        );
        classIdClmn.setCellValueFactory(
                new PropertyValueFactory<>("classId")
        );
        mimeTypeClmn.setCellValueFactory(
                new PropertyValueFactory<>("mimeType")
        );

        createDateClmn.setCellValueFactory(
                new PropertyValueFactory<>("createDate")
        );
        usernameClmn.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );
        messageClmn.setCellValueFactory(
                new PropertyValueFactory<>("message")
        );
        transIdClmn.setCellValueFactory(
                new PropertyValueFactory<>("migTransactionId")
        );
        DataTbl.getColumns().addAll(CNClmn, HYClmn, typeIDClmn, docidClmn, fileNameClmn, docTitleClmn, classIdClmn, mimeTypeClmn,
                createDateClmn, usernameClmn, statusClmn, messageClmn,transIdClmn);

        try {
            loadDataTable();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (!STARTED) {
                    try {
                        loadDataTable();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }

            }
        });
        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (Utils.isThreadsAlive(threads)) {
                        Migration.STOP = true;
                        startBtn.setText("Start");

                    } else {
                        List<MigrationData> undone = migrationData.stream().filter(e -> e.getStatus().equals("UNDONE")).collect(Collectors.toList());
                        if (undone.size() <= 0) {
                            Utils.showDialog("No Documents", "No Documents to be migrated");
                            return;
                        }
                        Migration.STOP = false;

                        //                        number of threads n
                        int n = Integer.valueOf(Utils.getConfigs("threads.number"));
                        List<List<MigrationData>> lists = Utils.getArrayOfDataLists(undone, n);
                        for (int i = 0; i < n; i++) {
                            //split migrationData array to n number of arrays
                            int finalI = i;
                            Runnable runnable = () -> {

//                                new Migration().spgaMigration(lists.get(finalI));
                                new Migration().validateAndFix(lists.get(finalI));
                                Utils.runInUIThread(new ICallback() {
                                    @Override
                                    public void run() {
                                        if (Utils.isThreadsAlive(threads))
                                            startBtn.setText("Start");
                                    }
                                });
                            };
                            Thread thread = new Thread(runnable);
                            thread.setName(i + "");
                            thread.start();
                            threads.add(thread);
                            int finalI1 = i;
                            buildThreadUI(finalI, "thread " + finalI, 0);

                        }

                        startBtn.setText("Stop");

                    }


                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }

            }
        });
        progressBar.setProgress(0);
        progressBarLbl.setText("0%");
        mainStatistics.setFont(new Font(16));
    }

    private void loadDataTable() throws Exception {
        migrationData = new DataDAO().getMigrationData();
        ObservableList<MigrationData> dataObservableList = FXCollections.observableArrayList(this.migrationData);
        DataTbl.setItems(dataObservableList);
//        String statistics = getStatistics(0, 0, 0);
//        updateThreadUI((int) Thread.currentThread().getId(), statistics, 0);

    }


    public void buildThreadUI(int threadNumber, String text, double progress) {
        Label thradLabel = (Label) Shared.spgaController.lookup("#labelThread" + threadNumber);
        ProgressBar threadProgress = (ProgressBar) Shared.spgaController.lookup("#progressThread" + threadNumber);
        if (thradLabel != null && threadProgress != null) {
            return;
        }
        ProgressBar progressBar = new ProgressBar();
        Label label = new Label();
        label.setText(text);
        label.setId("labelThread" + threadNumber);
        label.setFont(new Font(16));
        progressBar.setProgress(progress);
        progressBar.setId("progressThread" + threadNumber);
        progressBar.setPrefWidth(1000);
        thradsVBox.getChildren().add(label);
        thradsVBox.getChildren().add(progressBar);
    }

    public synchronized void updateThreadUI(int threadNumber, String text, double progress) {
        Label thradLabel = (Label) Shared.spgaController.lookup("#labelThread" + threadNumber);
        ProgressBar threadProgress = (ProgressBar) Shared.spgaController.lookup("#progressThread" + threadNumber);
        thradLabel.setText(text);
        threadProgress.setProgress(progress);
    }


    @Override
    public void refreshData() {
        DataTbl.refresh();
    }

    public void updateUI(String threadName, long timePerTrans, long perHour, long perMinutes, String statistics, double percentage) {
        int threadNumber = Integer.valueOf(threadName);


        updateThreadUI(threadNumber, statistics, percentage);
    }

    Map<String, Long> timePerTransMap = new HashMap<>();
    Map<String, Long> perHourMap = new HashMap<>();
    Map<String, Long> perMinutesMap = new HashMap<>();

    public void updateMain(String threadName, long timePerTrans, long perHour, long perMinutes) {
        timePerTransMap.put(threadName, timePerTrans);
        perHourMap.put(threadName, perHour);
        perMinutesMap.put(threadName, perMinutes);

        long TimePerTransAVG = Utils.calcAverage(timePerTransMap);
        long perHourSUM = Utils.calcSUM(perHourMap);
        long perMinutesSUM = Utils.calcSUM(perMinutesMap);

        int totalDocs = migrationData.size();
        int doneDocs = migrationData.stream().filter(e -> e.getStatus().equals("DONE")).collect(Collectors.toList()).size();
        int remainDocs = migrationData.stream().filter(e -> e.getStatus().equals("UNDONE")).collect(Collectors.toList()).size();
        int errorDocs = migrationData.stream().filter(e -> e.getStatus().equals("ERROR")).collect(Collectors.toList()).size();

        StringBuilder sb = new StringBuilder("");

        sb.append("   Total Docs:" + Utils.formatNumber(totalDocs));
        sb.append(" , Done Docs:" + Utils.formatNumber(doneDocs));
        sb.append(" , Remain Docs:" + Utils.formatNumber(remainDocs));
        sb.append(" , Error Docs:" + Utils.formatNumber(errorDocs));
        sb.append(" , Time Per Doc:" + Utils.formatNumber((int) TimePerTransAVG));
        sb.append(" , Docs Per Hour:" + Utils.formatNumber((int) perHourSUM));
        sb.append(" , Docs Per Minutes:" + Utils.formatNumber((int) perMinutesSUM));

        mainStatistics.setText(sb.toString());

    }


}
