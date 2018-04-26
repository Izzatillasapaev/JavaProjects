package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class WelcomePage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        pane.setId("pane");
        //layout

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.RED);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        //effects
        DropShadow borderGlow1 = new DropShadow();
        borderGlow1.setColor(Color.rgb(100,0,255,0.8));
        borderGlow1.setOffsetX(0f);
        borderGlow1.setOffsetY(0f);
        //welcome menu
        Label name = new Label("The zombie Apocalypse");
        name.setFont(new Font("Broadway", 43));
        name.relocate(25, 100);
        Label newGame = new Label("New Game");
        newGame.setFont(new Font("Luminari", 30));
        newGame.relocate(80, 250);

        newGame.setOnMouseEntered(event -> newGame.setEffect(borderGlow1));
        newGame.setOnMouseExited(event -> newGame.setEffect(null));
        newGame.setOnMouseClicked(event -> {
            try {
                new Main().start(primaryStage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        //actions
        Label rating = new Label("Rating List");
        rating.setFont(new Font("Luminari", 30));
        rating.relocate(80, 300);

        rating.setOnMouseEntered(event -> rating.setEffect(borderGlow1));
        rating.setOnMouseExited(event -> rating.setEffect(null));

        Label about = new Label("About");
        about.setFont(new Font("Luminari", 30));
        about.relocate(80, 350);

        about.setOnMouseEntered(event -> about.setEffect(borderGlow1));
        about.setOnMouseExited(event -> about.setEffect(null));
        about.setOnMouseClicked(event ->new About().start(new Stage()));
        Label quit = new Label("Quit");
        quit.setFont(new Font("Luminari", 30));
        quit.relocate(80, 400);

        quit.setOnMouseEntered(event -> quit.setEffect(borderGlow1));
        quit.setOnMouseExited(event -> quit.setEffect(null));
        quit.setOnMouseClicked(event -> primaryStage.close());

        name.setEffect(borderGlow);


        pane.getChildren().addAll(name, newGame, rating, about, quit);
        Scene scene = new Scene(pane, 1250, 700);
        scene.getStylesheets().add("sample/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
