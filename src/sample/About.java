package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class About {


    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        pane.setId("pane2");
        Scene scene = new Scene(pane, 1000, 550);
        scene.getStylesheets().add("sample/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
