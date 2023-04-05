package com.dataserve.migration.spga.controller;

import com.dataserve.migration.spga.Main;
import com.dataserve.migration.spga.dao.CEDAO;
import com.dataserve.migration.spga.dao.CEDaoOld;
import com.dataserve.migration.spga.dao.DataDAO;
import com.dataserve.migration.spga.objects.Attachment;
import com.dataserve.migration.spga.objects.FixDocumentRelation;
import com.dataserve.migration.spga.objects.InbasketData;
import com.dataserve.migration.spga.util.EventsUtil;
import com.dataserve.migration.spga.util.LDAPManager;
import com.dataserve.migration.spga.util.PEUtils;
import com.dataserve.migration.spga.util.Shared;
import com.filenet.api.collection.DocumentSet;
import com.filenet.api.core.Document;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/************************
 *
 * Created By Mohammad Awwad 01-June-2020
 *
 ************************/
public class HomeController extends StackPane {

    @FXML
    private Button mofBtn;
    @FXML
    private Button spgaBtn;
    @FXML
    private Button workitemsBtn;
    @FXML
    private Button inbasket;
    @FXML
    private Button fixSecurity;
    @FXML
    private Button fixNoParent;
    @FXML
    private Button fixNoParentForOld;
    @FXML
    private Button deliveryReports;

    //    private MofController mofController;
    private SpgaController spgaController;
    private WorkitemsController workitemsController;
    //    private Scene mofScene;
    private Scene SPGAScene;
    private Scene workitemsScene;

    public HomeController() throws IOException {
//        mofController = new MofController();
        spgaController = new SpgaController();
        workitemsController = new WorkitemsController();
//        mofScene = new Scene(mofController);
        SPGAScene = new Scene(spgaController);
        workitemsScene = new Scene(workitemsController);


        FXMLLoader loader = new FXMLLoader(new Main().getClass().getResource("fxml/Home.fxml"));
        loader.setController(this);
        Parent parent = loader.load();
        this.getChildren().add(parent);

//        mofBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Shared.mofController = mofController;
//                new EventsUtil().OpenDialog(mofController, mofScene, "MOF Export Documents");
//
//            }
//        });
        spgaBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Shared.spgaController = spgaController;
                new EventsUtil().OpenDialog(spgaController, SPGAScene, "SPGA Import Documents");
            }
        });
        workitemsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Shared.workitemsController = workitemsController;
                new EventsUtil().OpenDialog(workitemsController, workitemsScene, "workitems ");
            }
        });
//        inbasket.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    System.out.println("----------- 1");
//
//                    DataDAO dataDAO = new DataDAO();
//                    List<InbasketData> inbasketData = dataDAO.getInbasketData();
//                    System.out.println("----------- 2");
//
//                    PEUtils peUtils = new PEUtils();
//                    for (InbasketData data : inbasketData) {
//                        System.out.println("----------- 3");
//
//                        try {
//                            peUtils.createWorkBasket(data.getDepartmentQueueName(), data.getInBasketName(),
//                                    data.getRoleName(), data.getDepartmentId(), data.getRoleUsersIds());
//                            dataDAO.updateInbasketStatus(Integer.parseInt(data.getDepartmentId()), "DONE", "DONE");
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            dataDAO.updateInbasketStatus(Integer.parseInt(data.getDepartmentId()), "UNDONE", e.getMessage());
//                        }
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        fixNoParent.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    System.out.println("----------- 1");
//
//
//                    DataDAO dataDAO = new DataDAO();
//                    List<FixDocumentRelation> inbasketData = dataDAO.getFixDocRelationData();
//                    System.out.println("----------- 2");
//                    CEDAO cedao = new CEDAO();
//                    int noParentCount = 0;
//                    for (FixDocumentRelation data : inbasketData) {
//                        System.out.println("----------- 3");
//
//                        try {
//                            Document child = cedao.fetchDocById(data.getDocid());
//                            DocumentSet parentDocuments = child.get_ParentDocuments();
//                            if (parentDocuments.isEmpty()){
//                                System.out.println("Document "+data.getDocid() +" has no parent");
//                                Document parent = cedao.getCorrespondenceInfoCompundDocument(data.getCn(),data.getHy(), data.getTypeid());
//                                cedao.createComponentRelationship(child,parent);
//                                System.out.println("Document "+data.getDocid() +" Fixed");
//                                noParentCount++;
//
//
//
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//
//                    }
//
//                    System.out.println(noParentCount +" Docs has no parent");
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        fixNoParentForOld.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    System.out.println("----------- 1");
//
//
//                    DataDAO dataDAO = new DataDAO();
//                    List<FixDocumentRelation> inbasketData = dataDAO.getNoParentOld();
//                    System.out.println("----------- 2");
//                    CEDAO cedao = new CEDAO();
//                    int noParentCount = 0;
//                    for (FixDocumentRelation data : inbasketData) {
//                        System.out.println("----------- 3");
//
//                        try {
//                            Document child = cedao.fetchDocById(data.getDocid());
//                            DocumentSet parentDocuments = child.get_ParentDocuments();
//                            if (parentDocuments.isEmpty()){
//                                System.out.println("Document "+data.getDocid() +" has no parent");
//                                Iterator iterator = parentDocuments.iterator();
//                                if (iterator.hasNext()){
//                                    Document next = (Document) iterator.next();
//
//                                }
//
//
//                                //cedao.createComponentRelationship(child,parent);
//                                System.out.println("Document "+data.getDocid() +" Fixed");
//                                noParentCount++;
//
//
//
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//
//                    }
//
//                    System.out.println(noParentCount +" Docs has no parent");
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        fixSecurity.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    System.out.println("----------- 1");
//
//
//                    DataDAO dataDAO = new DataDAO();
//                    List<FixDocumentRelation> inbasketData = dataDAO.getFixDocRelationData();
//                    System.out.println("----------- 2");
//                    CEDAO cedao = new CEDAO();
//                    int noParentCount = 0;
//                    for (FixDocumentRelation data : inbasketData) {
//                        System.out.println("----------- 3");
//
//                        try {
//                            Document document = cedao.fetchDocById(data.getDocid());
//                            cedao.applySecurity(document);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    System.out.println(noParentCount +" Docs has no parent");
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        deliveryReports.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    System.out.println("----------- 1");
//
//
//                    DataDAO dataDAO = new DataDAO();
//                    List<FixDocumentRelation> deliveryReportItem = dataDAO.getDeliveryReports();
//                    System.out.println("----------- 2");
//                    CEDAO cedao = new CEDAO();
//                    int noParentCount = 0;
//                    for (FixDocumentRelation data : deliveryReportItem) {
//                        System.out.println("----------- 3");
//
//                        try {
//                            CEDaoOld cedaoOld = new CEDaoOld();
//                            Attachment attachment = cedaoOld.getAttachment(data.getDocid());
//
//                            cedao.addDRDocument(data.getDeliveryReportNumber(),data.getCn(),data.getHy(),data.getTypeid(),data.getDocid(),
//                                    attachment.getBytes(),data.getFileName(),attachment.getMimetype());
//                            dataDAO.updateStatusDR(data.getDocid(),"DONE","DONE");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            dataDAO.updateStatusDR(data.getDocid(),"ERROR",e.getMessage());
//                        }
//
//                    }
//
//                    System.out.println(noParentCount +" delivery reports added");
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });


    }


}
