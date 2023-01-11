package com.example.gomoku;

import com.example.gomoku.service.data.Gomoku;
import com.example.gomoku.service.data.Status;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.function.Predicate;

public class GomokuController {
    private Pane pane;
    private Gomoku gomoku;

    @FXML
    private SubScene gomokuSs;

    @FXML
    private Text hintTxt;

    @FXML
    private Button surrenderBtn;

    @FXML
    private Button restartBtn;

    @FXML
    public void initialize() {
        initChessGame();

        restartBtn.setOnMouseClicked(mouseEvent -> {
            initChessGame();
        });

        surrenderBtn.setOnMouseClicked(mouseEvent -> {
            setWinColorTitle(gomoku.getCurrentColor());
            surrenderBtn.setDisable(true);
        });

        gomokuSs.setOnMouseExited(mouseEvent -> setSupportLine(-1, -1));

        gomokuSs.setOnMouseClicked(mouseEvent -> {
            int x = (int) Math.round((mouseEvent.getSceneX() - 30) / 20);
            int y = (int) Math.round((mouseEvent.getSceneY() - 35) / 20);
            if (x < 19 && y < 19) {
                Status xdd = addChess(x, y, gomoku.getCurrentColor());
                System.out.println(xdd);
                switch(xdd) {
                    case STILL:
                        setCurrentColorTitle();
                        break;
                    case LOSS:
                        setWinColorTitle(gomoku.getCurrentColor());
                        surrenderBtn.setDisable(true);
                        break;
                    case WIN:
                        setWinColorTitle(gomoku.getLastColor());
                        surrenderBtn.setDisable(true);
                        break;
                    case INVALID:
                        break;
                }
            }
        });

        gomokuSs.setOnMouseMoved(mouseEvent -> {
            int x = (int) Math.round((mouseEvent.getSceneX() - 30) / 20);
            int y = (int) Math.round((mouseEvent.getSceneY() - 35) / 20);
            if (x < 19 && y < 19) {
                setSupportLine(x, y);
            } else {
                setSupportLine(-1, -1);
            }
        });
    }

    private void initChessGame() {
        pane = new Pane();
        gomoku = new Gomoku();
        makeChessBoard();
        setCurrentColorTitle();
        surrenderBtn.setDisable(false);
    }

    private void setWinColorTitle(Color color) {
        if (color.equals(Color.WHITE)) {
            hintTxt.setText("Black WIN!");
        } else {
            hintTxt.setText("White WIN!");
        }
    }

    private void setCurrentColorTitle() {
        if(gomoku.getCurrentColor().equals(Color.WHITE)) {
            hintTxt.setText("White's turn");
        } else {
            hintTxt.setText("Black's turn");
        }
    }

    private void setSupportLine(int x, int y) {
        Node supportLine2 = pane.getChildren().stream().filter(node -> {
            if (node.getId() == null) {
                return false;
            }
            return node.getId().equals("supportLine2");
        }).findFirst().get();
        Node supportLine1 = pane.getChildren().stream().filter(node -> {
            if (node.getId() == null) {
                return false;
            }
            return node.getId().equals("supportLine1");
        }).findFirst().get();

        if (x < 0 || y < 0) {
            supportLine2.setVisible(false);
            supportLine1.setVisible(false);
        } else {
            supportLine2.setLayoutX(getPosition(x));
            supportLine1.setLayoutY(getPosition(y));
            supportLine2.setVisible(true);
            supportLine1.setVisible(true);
        }

    }

    private int getPosition(int index) {
        return 10 + 20 * index;
    }

    private void makeChessBoard() {
        for (int i = 0; i < 19; i ++) {
            Line line1 = new Line(getPosition(i), 10, getPosition(i), 370);
            Line line2 = new Line(10, getPosition(i), 370, getPosition(i));
            pane.getChildren().addAll(line1, line2);
        }

        for (int i = 3; i < 16; i += 6) {
            for (int j = 3; j < 16; j += 6) {
                Circle circle = new Circle(getPosition(i), getPosition(j), 3);
                pane.getChildren().add(circle);
            }
        }

        Line supportLine1 = new Line(0, 0, 400, 0);
        Line supportLine2 = new Line(0, 0, 0, 400);
        supportLine1.setStrokeWidth(2);
        supportLine2.setStrokeWidth(2);
        supportLine1.setId("supportLine1");
        supportLine2.setId("supportLine2");
        supportLine1.setStroke(Color.RED);
        supportLine2.setStroke(Color.RED);
        supportLine1.setVisible(false);
        supportLine2.setVisible(false);
        pane.getChildren().addAll(supportLine1, supportLine2);

        gomokuSs.setRoot(pane);
    }

    private Status addChess(int x, int y, Color color) {
        Status addChessStatus = gomoku.addChess(x, y);

        switch (addChessStatus) {
            case WIN, LOSS, STILL -> {
                Circle chess = new Circle(getPosition(x), getPosition(y), 9);
                chess.setStroke(Color.BLACK);
                chess.setStrokeWidth(1);
                chess.setFill(color);
                pane.getChildren().add(chess);
            }
        }
        return addChessStatus;
    }

}