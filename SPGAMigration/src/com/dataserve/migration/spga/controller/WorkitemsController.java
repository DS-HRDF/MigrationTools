package com.dataserve.migration.spga.controller;

import com.dataserve.migration.spga.Main;
import com.dataserve.migration.spga.Migration;
import com.dataserve.migration.spga.dao.DataDAO;
import com.dataserve.migration.spga.objects.WorkitemsData;
import com.dataserve.migration.spga.util.ICallback;
import com.dataserve.migration.spga.util.Shared;
import com.dataserve.migration.spga.util.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/************************
 *
 * Created By Mohammad Awwad 01-June-2020
 *
 ************************/
public class WorkitemsController extends StackPane implements IController {
    final static Logger logger = Logger.getLogger(WorkitemsController.class);

    private static final boolean STARTED = false;
    @FXML
    private TableView<WorkitemsData> DataTbl;
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
    List<WorkitemsData> workitemsData;
    static List<Thread> threads = new ArrayList<>();


    public WorkitemsController() throws IOException {

        System.out.println("Starting WorkitemsController");
        FXMLLoader loader = new FXMLLoader(new Main().getClass().getResource("fxml/Workitems.fxml"));
        loader.setController(this);
        Parent parent = loader.load();
        this.getChildren().add(parent);


        TableColumn wobnum = new TableColumn("wobnum");
        TableColumn BoundUser = new TableColumn("F_BoundUser");
        TableColumn CreateTime = new TableColumn("F_CreateTime");
        TableColumn Subject = new TableColumn("F_Subject");
        TableColumn CorrespondenceDate = new TableColumn("CorrespondenceDate");
        TableColumn FromUser = new TableColumn("FromUser");
        TableColumn HijricYear = new TableColumn("HijricYear");
        TableColumn ImportanceLevel = new TableColumn("ImportanceLevel");
        TableColumn isRead = new TableColumn("isRead");
        TableColumn ItemType = new TableColumn("ItemType");
        TableColumn Originator = new TableColumn("Originator");
        TableColumn ReceivedOnHijriDate = new TableColumn("ReceivedOnHijriDate");
        TableColumn SequenceNumber = new TableColumn("SequenceNumber");
        TableColumn UrgencyLevel = new TableColumn("UrgencyLevel");
        TableColumn departmentID = new TableColumn("departmentID");
        TableColumn queueName = new TableColumn("queueName");
        TableColumn username = new TableColumn("username");
        TableColumn forwardType = new TableColumn("forwardType");
        TableColumn status = new TableColumn("status");
        TableColumn message = new TableColumn("message");
        TableColumn participantType = new TableColumn("participantType");
        TableColumn externalUnit = new TableColumn("externalUnit");

//        docTitleClmn.setPrefWidth(100);
//        mimeTypeClmn.setPrefWidth(100);


        final Image doneImg = new Image("/images/done.png");
        final Image errorImg = new Image("/images/error.png");
        final Image pendingImg = new Image("/images/pending.png");
        status.setCellValueFactory(c -> {
            TableColumn.CellDataFeatures tclmn = (TableColumn.CellDataFeatures) c;
            WorkitemsData data = (WorkitemsData) tclmn.getValue();
            if (data.getStatus().equals("DONE")) {
                return new SimpleObjectProperty<>(new ImageView(doneImg));
            } else if (data.getStatus().equals("UNDONE")) {
                return new SimpleObjectProperty<>(new ImageView(pendingImg));
            } else {
                return new SimpleObjectProperty<>(new ImageView(errorImg));
            }
        });

        externalUnit.setCellValueFactory(
                new PropertyValueFactory<>("externalUnit")
        );
        wobnum.setCellValueFactory(
                new PropertyValueFactory<>("wobnum")
        );
        BoundUser.setCellValueFactory(
                new PropertyValueFactory<>("F_BoundUser")
        );
        CreateTime.setCellValueFactory(
                new PropertyValueFactory<>("F_CreateTime")
        );
        Subject.setCellValueFactory(
                new PropertyValueFactory<>("F_Subject")
        );
        CorrespondenceDate.setCellValueFactory(
                new PropertyValueFactory<>("CorrespondenceDate")
        );
        FromUser.setCellValueFactory(
                new PropertyValueFactory<>("FromUser")
        );
        HijricYear.setCellValueFactory(
                new PropertyValueFactory<>("HijricYear")
        );
        ImportanceLevel.setCellValueFactory(
                new PropertyValueFactory<>("ImportanceLevel")
        );
        isRead.setCellValueFactory(
                new PropertyValueFactory<>("isRead")
        );
        ItemType.setCellValueFactory(
                new PropertyValueFactory<>("ItemType")
        );
        Originator.setCellValueFactory(
                new PropertyValueFactory<>("Originator")
        );
        ReceivedOnHijriDate.setCellValueFactory(
                new PropertyValueFactory<>("ReceivedOnHijriDate")
        );
        SequenceNumber.setCellValueFactory(
                new PropertyValueFactory<>("SequenceNumber")
        );
        UrgencyLevel.setCellValueFactory(
                new PropertyValueFactory<>("UrgencyLevel")
        );
        departmentID.setCellValueFactory(
                new PropertyValueFactory<>("departmentID")
        );
        queueName.setCellValueFactory(
                new PropertyValueFactory<>("queueName")
        );
        username.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );
        forwardType.setCellValueFactory(
                new PropertyValueFactory<>("forwardType")
        );
        status.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );
        message.setCellValueFactory(
                new PropertyValueFactory<>("message")
        );

        DataTbl.getColumns().addAll(
                wobnum
                , BoundUser
                , CreateTime
                , Subject
                , FromUser
                , CorrespondenceDate
                , participantType
                , message
                , status
                , forwardType
                , username
                , queueName
                , departmentID
                , UrgencyLevel
                , SequenceNumber
                , ReceivedOnHijriDate
                , Originator
                , ItemType
                , isRead
                , ImportanceLevel
                , HijricYear
                , externalUnit
        );

        try {
            loadDataTable();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

        refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (!STARTED) {
                    try {
                        loadDataTable();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        e.printStackTrace();

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
                        List<WorkitemsData> undone = workitemsData.stream().filter(e -> e.getStatus().equals("UNDONE")).collect(Collectors.toList());
                        if (undone.size() <= 0) {
                            Utils.showDialog("No Documents", "No Documents to be migrated");
                            return;
                        }
                        Migration.STOP = false;

                        //                        number of threads n
                        int n = Integer.valueOf(Utils.getConfigs("threads.number"));
                        List<List<WorkitemsData>> lists = Utils.getArrayOfDataListsWorkitems(undone, n);
                        for (int i = 0; i < n; i++) {
                            //split workitemsData array to n number of arrays
                            int finalI = i;
                            Runnable runnable = () -> {

                                new Migration().workitemsMigration(lists.get(finalI));
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
                    e.printStackTrace();

                }

            }
        });
        progressBar.setProgress(0);
        progressBarLbl.setText("0%");
        mainStatistics.setFont(new Font(16));
    }

    private void loadDataTable() throws Exception {
        workitemsData = new DataDAO().getWorkitemObject();
        ObservableList<WorkitemsData> dataObservableList = FXCollections.observableArrayList(this.workitemsData);
        DataTbl.setItems(dataObservableList);
//        String statistics = getStatistics(0, 0, 0);
//        updateThreadUI((int) Thread.currentThread().getId(), statistics, 0);

    }


    public void buildThreadUI(int threadNumber, String text, double progress) {
        Label thradLabel = (Label) Shared.workitemsController.lookup("#labelThread" + threadNumber);
        ProgressBar threadProgress = (ProgressBar) Shared.workitemsController.lookup("#progressThread" + threadNumber);
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
        Label thradLabel = (Label) Shared.workitemsController.lookup("#labelThread" + threadNumber);
        ProgressBar threadProgress = (ProgressBar) Shared.workitemsController.lookup("#progressThread" + threadNumber);
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

        int totalDocs = workitemsData.size();
        int doneDocs = workitemsData.stream().filter(e -> e.getStatus().equals("DONE")).collect(Collectors.toList()).size();
        int remainDocs = workitemsData.stream().filter(e -> e.getStatus().equals("UNDONE")).collect(Collectors.toList()).size();
        int errorDocs = workitemsData.stream().filter(e -> e.getStatus().equals("ERROR")).collect(Collectors.toList()).size();

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
