package generator;

import generator.service.ExcelService;
import generator.service.RandomGenerator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


public class GeneratorView extends Application implements Initializable {

    private static Stage stage;
    //Login
    @FXML
    private TextField mx;

    @FXML
    private TextField dx;


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
            int N = 1000;
            double[] arr = new double[N];
            RandomGenerator generator = new RandomGenerator();
            for (int i = 0; i < arr.length; i++)
            {
                arr[i] = generator.generate(Double.parseDouble(mx.getText()), Double.parseDouble(dx.getText()));
            }
            Arrays.sort(arr);

            double min = arr[0];
            double max = arr[arr.length - 1];
            double range = max - min;

            int intervalCount = 15;//(int)(2 + 3.22 * Math.Log10(arr.Length));
            double dInterval = range / intervalCount;
            int[] intervals = new int[intervalCount];
            double[] intervalsX = new double[intervalCount];

            for (int i = 0; i < arr.length; i++)
            {
                for (int j = 0; j < intervals.length; j++)
                {
                    if (arr[i] >= min + dInterval * (j) && arr[i] < min + dInterval * (j + 1))
                    {
                        intervals[j] += 1;
                    }
                }
            }

            for (int i = 0; i < intervalCount; i++)
            {
                intervalsX[i] = min + dInterval*(i+1);
            }

            final NumberAxis xAxis = new NumberAxis(min, max, dInterval);
            final NumberAxis yAxis = new NumberAxis();
            final AreaChart<Number,Number> chart =
                    new AreaChart<Number,Number>(xAxis,yAxis);

            XYChart.Series gaussian = new XYChart.Series();
            gaussian.setName("Gaussian");

            for (int i = 0; i < intervalCount; i++) {
                gaussian.getData().add(new XYChart.Data(intervalsX[i], intervals[i]));
            }

            Platform.runLater(()->{
                chart.getData().addAll(gaussian);
                Stage stage = new Stage();
                stage.setTitle("Mx:" + mx.getText() + "; dx:" + dx.getText());
                Scene scene  = new Scene(chart,800,600);
                stage.setScene(scene);
                stage.show();
            });

            double[] intervalBounds = new double[intervalCount+1];

            for (int i = 0; i < intervalBounds.length; i++) {
                intervalBounds[i] = min + dInterval * i;
            }

            try {
                ExcelService.putValues(
                        "pirson.xls",
                        Double.valueOf(mx.getText()),
                        Double.valueOf(dx.getText()),
                        N,
                        intervalBounds,
                        intervals);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
