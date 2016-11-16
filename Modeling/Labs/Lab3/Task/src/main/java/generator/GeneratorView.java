package generator;

import generator.service.RandomGenerator;
import javafx.application.Application;
import javafx.application.Platform;
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


public class GeneratorView extends Application implements Initializable {

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
        GeneratorView.stage = stage;
        stage.setTitle("Generator");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void process(ActionEvent event) throws Exception {
        new Thread(() -> {
            RandomGenerator generator = new RandomGenerator();
            double mx = Double.parseDouble(this.mx.getText());
            double dx = Double.parseDouble(this.dx.getText());
            double time = Double.parseDouble(this.time.getText());
            int details = 0;
            List<Double> times = new ArrayList<>();
            double currentTime = 0;
            while (currentTime < time) {
                double initTime = currentTime;
                for (int i = 0; i < 50; i++) {
                    currentTime += generator.generate(mx, dx);
                }
                times.add(currentTime - initTime);
                details++;
            }
            if (currentTime != time) {
                details--;
            }

            StringBuilder result = new StringBuilder("Количество деталей: ")
                    .append(details)
                    .append("\nВремя, затраченное на каждую деталь:\n");
            for (int i = 0; i < details; i++) {
                result
                        .append(i + 1)
                        .append(":")
                        .append(times.get(i))
                        .append("\n");
            }
            Platform.runLater(() -> {

                out.setText(result.toString());
            });

        }).start();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
