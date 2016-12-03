package monitor.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import monitor.controller.MonitorController;
import monitor.controller.component.Timer;
import monitor.controller.entity.Result;
import monitor.controller.entity.Transact;
import monitor.service.generator.RandomGenerator;
import monitor.service.generator.impl.Gaussian;
import monitor.service.generator.impl.RandomAdapter;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringJoiner;


public class MonitorView extends Application implements Initializable {


    @FXML
    private ProgressIndicator generatorLoader;
    @FXML
    private ProgressBar queueLoader;
    @FXML
    private TextField count;
    @FXML
    private ProgressIndicator deviceLoader;
    @FXML
    private Label result;
    @FXML
    private Slider speedSlider;
    @FXML
    private MonitorController monitorController;
    @FXML
    private ToggleButton startStopBtn;
    @FXML
    private Label time;

    //TABLE
    @FXML
    private TableView<Transact> table;
    @FXML
    private TableColumn<Transact, Integer> idColumn;
    @FXML
    private TableColumn<Transact, Integer> birthDateColumn;
    @FXML
    private TableColumn<Transact, Integer> ageColumn;
    @FXML
    private TableColumn<Transact, String> blockColumn;

    //GENERATE
    @FXML
    private Pane generate;

    @FXML
    private ChoiceBox<String> sv1;
    @FXML
    private TextField mx1;
    @FXML
    private TextField dx1;

    //QUEUE
    @FXML
    private Pane queue;
    @FXML
    private TextField queLength;
    @FXML
    private TextField queTime;

    //ADVANCE
    @FXML
    private ChoiceBox<String> sv2;
    @FXML
    private TextField mx2;
    @FXML
    private TextField dx2;
    @FXML
    private Pane device;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Monitor");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void startstop(ActionEvent event) throws Exception {
        result.setText("");
        Thread process = new Thread(() -> {
            try {
                if (monitorController != null) {
                    monitorController.stop();
                    monitorController = null;
                } else {
                    RandomGenerator gen1 = null;
                    RandomGenerator gen2 = null;
                    switch (sv1.getValue()) {
                        case "Нормальная":
                            gen1 = new Gaussian(Double.parseDouble(mx1.getText()), Double.parseDouble(dx1.getText()));
                            break;
                        case "Равномерная":
                            gen1 = new RandomAdapter(Double.parseDouble(mx1.getText()), Double.parseDouble(dx1.getText()));
                            break;
                    }
                    switch (sv2.getValue()) {
                        case "Нормальная":
                            gen2 = new Gaussian(Double.parseDouble(mx2.getText()), Double.parseDouble(dx2.getText()));
                            break;
                        case "Равномерная":
                            gen2 = new RandomAdapter(Double.parseDouble(mx2.getText()), Double.parseDouble(dx2.getText()));
                    }
                    monitorController = new MonitorController(this, gen1, gen2, Integer.parseInt(queLength.getText()), Integer.parseInt(queTime.getText()), Integer.parseInt(count.getText()), getDelay(), generatorLoader, deviceLoader, queueLoader);
                    monitorController.start();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Throwable t) {
                Platform.runLater(() -> viewResult(Result.build().text("Произошла ошибка!").text("Сообщение:" + t.getMessage()).text("Проверьте корректность введенных данных!")));
            }
        });
        process.setDaemon(true);
        process.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> randoms = FXCollections.observableArrayList();
        randoms.add("Нормальная");
        randoms.add("Равномерная");
        sv1.setItems(randoms);
        sv1.setValue("Равномерная");
        sv2.setItems(randoms);
        sv2.setValue("Равномерная");

        table.setEditable(true);

        idColumn.setCellValueFactory(new PropertyValueFactory<Transact, Integer>("id"));

        birthDateColumn.setCellValueFactory(new PropertyValueFactory<Transact, Integer>("birthDate"));

        ageColumn.setCellValueFactory(new PropertyValueFactory<Transact, Integer>("age"));

        blockColumn.setCellValueFactory(new PropertyValueFactory<Transact, String>("block"));

        this.result.setWrapText(true);
    }

    public void setTime(int time) {
        this.time.setText(String.valueOf(time));
    }

    public void reloadTransacts(List<Transact> transacts) {
        ObservableList<Transact> observTransacts = FXCollections.observableList(transacts);
        table.setItems(observTransacts);
        table.refresh();
        System.out.println("Transacts RELOADED, list:" + transacts);
    }

    public void viewResult(Result result) {
        startStopBtn.setSelected(false);
        this.result.setText(result.toString());
    }

    public void changeSpeed(Event event) {
        if (monitorController!=null) {
            monitorController.setFixedDelay(getDelay());
        }
    }

    private int getDelay() {
        return 1000 - (int) speedSlider.getValue() * 10;
    }
}
