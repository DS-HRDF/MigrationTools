package com.dataserve.migration.spga;

import com.dataserve.migration.spga.controller.HomeController;
import com.dataserve.migration.spga.util.Shared;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        HomeController home = new HomeController();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(home, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        primaryStage.setTitle("SPGA Migration");
        primaryStage.setScene(scene);
        primaryStage.show();
        Shared.Main = primaryStage;

    }


    public static void main(String[] args) {
        launch(args);
    }
}
