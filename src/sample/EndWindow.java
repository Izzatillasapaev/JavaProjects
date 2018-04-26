package sample;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

import java.io.*;
import java.lang.invoke.VarHandle;
import java.util.*;

import static javafx.geometry.HPos.*;

public class EndWindow {
    private final double W = 1250, H = 700;
    GridPane gridPane;
    private Map<String, Integer> scores = new HashMap<>();
    int presentScore;
    TextField nick;

    EndWindow(GridPane gridPane, int presentScore) {
        this.gridPane = gridPane;
        this.presentScore = presentScore;
    }

    public void start() {


        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.WHITE);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);

        Label endLabel = new Label("GAME OVER");
        endLabel.setEffect(borderGlow);
        //  endLabel.relocate(W / 2 - 100, 100);
        endLabel.setFont(new Font("Minion Pro", 50));
        endLabel.setTextFill(Color.rgb(110, 0, 15));
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setId("pane3");
        //  gridPane.relocate(W/2-200,100);
        gridPane.setPadding(new Insets(15, 15, 15, 15));
        gridPane.add(endLabel, 0, 0);
        GridPane.setColumnSpan(endLabel, 2);
        GridPane.setHalignment(endLabel, CENTER);


        Label enterNick = new Label("Enter your nick: ");
        enterNick.setFont(new Font(25));
        enterNick.setTextFill(Color.rgb(200, 100, 0));
        enterNick.setEffect(borderGlow);
        gridPane.add(enterNick, 0, 2);
        nick = new TextField();
        nick.setMinWidth(200);
        nick.setId("nickfield");
        gridPane.add(nick, 1, 2);
        Button okBtn = new Button("OK");
        okBtn.setId("ok");

        gridPane.add(okBtn, 2, 2);
        Label yourScore = new Label("Your score is " + presentScore);
        yourScore.setEffect(borderGlow);
        //  endLabel.relocate(W / 2 - 100, 100);
        yourScore.setFont(new Font("Roboto", 25));
        yourScore.setTextFill(Color.rgb(30, 0, 15));

        gridPane.add(yourScore, 0, 1);
        GridPane.setColumnSpan(yourScore, 2);
        GridPane.setHalignment(yourScore, HPos.CENTER);

        gridPane.setMargin(okBtn, new Insets(5, 5, 15, 5));

        gridPane.setMargin(endLabel, new Insets(5, 5, 15, 5));

        gridPane.setMargin(enterNick, new Insets(5, 5, 15, 5));

        gridPane.setMargin(nick, new Insets(5, 5, 15, 5));


        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nick.getText().length() > 0) {
                    okBtn.setDisable(true);
                    updateTable();

                    scores = sortByValue(scores);
                    //  createTable();
                    createNewTable();
                    updateLogs();
                }
            }
        });

        nick.setOnAction(event -> {
                    if (nick.getText().length() > 0 && !okBtn.isDisable()) {
                        okBtn.setDisable(true);
                        updateTable();

                        scores = sortByValue(scores);
                        createNewTable();
                        System.out.println();
                        updateLogs();
                    }
                }
        );


        readLog();
        //  createTable();
        // showLogs();
    }

    private void updateTable() {
        scores.put(nick.getText(), presentScore);

    }

    private void showLogs() {
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue());
        }
    }

    private void readLog() {

        // lines=new ArrayList<>();


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/Users/izzatilla/Desktop/Final project/logs/scores.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String key;
                Integer value;
                String[] lines = line.split("-");
                key = lines[0];
                value = Integer.valueOf(lines[1]);
                scores.put(key, value);


            }
        } catch (IOException e) {
            System.out.println("You have forgotten to write some information\nPlease complete input");
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void createTable(Pane pane) {

        int index = 1;
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            Label number = new Label("" + index);
            number.setMinWidth(10);
            Label nickname = new Label("" + entry.getKey());
            number.setMinWidth(500);
            Label score = new Label("" + entry.getValue());
            number.setMinWidth(100);

            createNewRow(pane, number, nickname, score, index * 38);


            index++;
        }
    }


    private void createNewTable() {
        Pane pane = new Pane();

        Label id = new Label("№");
        id.setMinWidth(50);
        id.setPadding(new Insets(10));
        id.setStyle("-fx-background-color: rgba(0,3,255,0.56);");
        GridPane.setHalignment(id, RIGHT);
        //gridPane.add(id, 0, 3);
        Label nick = new Label("Nick");
        nick.setMinWidth(200);
        nick.setPadding(new Insets(10));
        nick.setStyle("-fx-background-color: rgba(255,0,21,0.56)");
        //  gridPane.add(nick, 1, 3);

        Label score = new Label("Score");
        score.setMinWidth(100);
        score.setPadding(new Insets(10));
        score.setStyle("-fx-background-color: rgba(255,255,255,0.45)");
        //gridPane.add(score, 2, 3);

        id.relocate(50, 0);
        nick.relocate(100, 0);

        score.relocate(300, 0);

        pane.getChildren().addAll(id, nick, score);

        gridPane.add(pane, 0, 4);
        GridPane.setColumnSpan(pane, 4);

        GridPane.setHalignment(pane, HPos.RIGHT);
        createTable(pane);
    }

    private void createNewRow(Pane pane, Label id, Label nickname, Label score, int lowestPoint) {

        id.setMinWidth(50);
        id.setPadding(new Insets(10));
        id.setStyle("-fx-background-color: rgba(0,3,255,0.56)");
        id.setTextFill(Color.WHITE);
        nickname.setTextFill(Color.WHITE);
        score.setTextFill(Color.WHITE);

        nickname.setMinWidth(200);
        nickname.setPadding(new Insets(10));
        nickname.setStyle("-fx-background-color:  rgba(255,0,21,0.56);");

        score.setMinWidth(100);
        score.setPadding(new Insets(10));
        score.setStyle("-fx-background-color: rgba(255,255,255,0.45)");
        id.relocate(50, lowestPoint);
        nickname.relocate(100, lowestPoint);
        score.relocate(300, lowestPoint);
        pane.getChildren().addAll(id, nickname, score);

    }

    private Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o2,
                               Map.Entry<String, Integer> o1) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        return sortedMap;
    }

    private void updateLogs() {
        File file = new File("/Users/izzatilla/Desktop/Final project/logs/scores.txt");
        file.delete();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
                        "/Users/izzatilla/Desktop/Final project/logs/scores.txt"), true));
                bw.write(entry.getKey() + "-" + entry.getValue());
                bw.newLine();
                bw.close();
            } catch (Exception e) {
                System.err.println("Exception error");
                e.printStackTrace();
            }
        }
    }
}
