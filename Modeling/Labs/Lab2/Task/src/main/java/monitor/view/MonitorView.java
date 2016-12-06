package monitor.view;

import generator.RandomGenerator;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MonitorView extends Application implements Initializable {

    @FXML
    private TextField mx;

    @FXML
    private TextField dx;

    @FXML
    private TextField time;

    @FXML
    private TextArea out;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Monitor");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void process(ActionEvent event) throws Exception {
        RandomGenerator randomGenerator = new RandomGenerator();
        double time = Double.parseDouble(this.time.getText());
        double mx = Double.parseDouble(this.mx.getText());
        double dx = Double.parseDouble(this.dx.getText());

        List<Double> details = new ArrayList<>();
        while (time >= 0) {
            int i = 0;
            double initialTime = time;
            while (i<50 && time>0) {
                time -= randomGenerator.generate(mx, dx);
                i++;
            }
            if (time>0) {
                details.add(initialTime - time);
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Всего деталей:").append(details.size());
        for (int i = 0; i < details.size(); i++) {
            result
                    .append("\n")
                    .append("Деталь№")
                    .append(i+1)
                    .append(":")
                    .append(Math.round(details.get(i)));
        }
        out.setText(result.toString());
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
