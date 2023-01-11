module com.example.gomoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gomoku to javafx.fxml;
    exports com.example.gomoku;
}