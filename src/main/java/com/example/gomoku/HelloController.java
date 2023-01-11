package com.example.gomoku;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button startBtn;
    @FXML
    private TextField nameTxf;

    @FXML
    protected void startClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GomokuApplication.class.getResource("gomoku-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) startBtn.getScene().getWindow();
        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("Your opponent is \n" + nameTxf.getText() + "\"");
        stage.setScene(scene);
        stage.show();
    }
}