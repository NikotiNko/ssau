package monitor.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import monitor.service.Analyzer;


import java.net.URL;
import java.util.ResourceBundle;


public class AnalyseView extends Application implements Initializable {

    @FXML
    private TextField input;
    @FXML
    private TextArea output;

    private Analyzer analyzer;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Monitor");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void analyze(ActionEvent event) throws Exception {
        new Thread(() -> {

        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
