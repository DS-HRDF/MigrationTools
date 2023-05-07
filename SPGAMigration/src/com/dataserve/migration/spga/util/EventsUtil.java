package com.dataserve.migration.spga.util;

/************************
 *
 * Created By Mohammad Awwad 02-June-2020
 *
 ************************/

import com.dataserve.migration.spga.Migration;
import com.dataserve.migration.spga.controller.IController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventsUtil {


    public void OpenDialog(IController controller, Scene scene, String title) {

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
        stage.setResizable(true);
        stage.initOwner(Shared.Main);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setOnHidden(e -> {
            Migration.STOP = true;
            stage.close();
        });
        stage.show();

        if (controller != null) {
            controller.refreshData();
        }
        Shared.CurrentStage = stage;

    }
}
