package monitor.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;


public class MonitorView extends Application implements Initializable {

    private static Stage stage;
    //Login
    @FXML
    private TextField mx;

    @FXML
    private TextField dx;

    @FXML
    private TextField time;

    @FXML
    private Label out;

    @Override
    public void start(Stage stage) throws Exception {
        MonitorView.stage = stage;
        stage.setTitle("Monitor");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void process(ActionEvent event) throws Exception {

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
