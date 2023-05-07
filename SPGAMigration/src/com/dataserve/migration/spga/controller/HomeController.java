package com.dataserve.migration.spga.controller;

import com.dataserve.migration.spga.Main;
import com.dataserve.migration.spga.dao.DataDAO;
import com.dataserve.migration.spga.objects.InbasketData;
import com.dataserve.migration.spga.util.EventsUtil;
import com.dataserve.migration.spga.util.PEUtils;
import com.dataserve.migration.spga.util.Shared;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
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

    //    private MofController mofController;
    private SpgaController spgaController;
    private WorkitemsController workitemsController;
    //    private Scene mofScene;
    private Scene SPGAScene;
    private Scene workitemsScene;

    public HomeController() throws IOException {
//      mofController = new MofController();
      spgaController = new SpgaController();
      workitemsController = new WorkitemsController();
//      mofScene = new Scene(mofController);
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
                new EventsUtil().OpenDialog(spgaController, SPGAScene, "Moaamalat Import Documents");
            }
        });
        workitemsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Shared.workitemsController = workitemsController;
                new EventsUtil().OpenDialog(workitemsController, workitemsScene, "workitems ");
            }
        });
        inbasket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DataDAO dataDAO = new DataDAO();
                    List<InbasketData> inbasketData = dataDAO.getInbasketData();
                    PEUtils peUtils = new PEUtils();
                    for (InbasketData data : inbasketData) {

                        try {
                            peUtils.createWorkBasket(data.getDepartmentQueueName(), data.getInBasketName(),
                                    data.getRoleName(), data.getDepartmentId(), data.getRoleUsersIds());
                            dataDAO.updateInbasketStatus(Integer.parseInt(data.getDepartmentId()), "DONE", "DONE");

                        } catch (Exception e) {
                            e.printStackTrace();
                            dataDAO.updateInbasketStatus(Integer.parseInt(data.getDepartmentId()), "UNDONE", e.getMessage());
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }


}
