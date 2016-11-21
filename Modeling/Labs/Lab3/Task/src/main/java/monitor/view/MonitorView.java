package monitor.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import monitor.controller.entity.Transact;
import monitor.service.generator.RandomGenerator;
import monitor.service.generator.impl.Gaussian;
import monitor.service.generator.impl.RandomAdapter;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MonitorView extends Application implements Initializable {

    private MonitorController monitorController;

    @FXML
    private Pane generate;
    @FXML
    private Pane device;
    @FXML
    private Pane queue;

    @FXML
    private ToggleButton startStopBtn;

    @FXML
    private Label time;

    @FXML
    private TableView<Transact> table;

    //GENERATE
    @FXML
    private ChoiceBox<String> sv1;
    @FXML
    private TextField mx1;
    @FXML
    private TextField dx1;

    //QUEUE
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

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Monitor");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*
            <columns>
          <TableColumn prefWidth="75.0" text="Id" />
          <TableColumn prefWidth="75.0" text="BirthDate" />
           <TableColumn prefWidth="75.0" text="Age" />
           <TableColumn prefWidth="94.0" text="Block" />
        </columns>
     */
    @FXML
    private void startstop(ActionEvent event) throws Exception {
        new Thread(() -> {
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
                        gen2 = new Gaussian(Double.parseDouble(mx1.getText()), Double.parseDouble(dx1.getText()));
                        break;
                    case "Равномерная":
                        gen2 = new RandomAdapter(Double.parseDouble(mx1.getText()), Double.parseDouble(dx1.getText()));
                }
                monitorController = new MonitorController(this, gen1, gen2, Integer.parseInt(queLength.getText()), Integer.parseInt(queTime.getText()), 100);
                try {
                    monitorController.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<Transact,String>("id"));

        TableColumn birthDateCol = new TableColumn("BirthDate");
        idCol.setCellValueFactory(new PropertyValueFactory<Transact,String>("birthDate"));

        TableColumn ageCol = new TableColumn("Age");
        idCol.setCellValueFactory(new PropertyValueFactory<Transact,String>("age"));

        TableColumn blockIdCol = new TableColumn("BlockId");
        idCol.setCellValueFactory(new PropertyValueFactory<Transact,String>("blockId"));
        table.getColumns().addAll(idCol, birthDateCol, ageCol,blockIdCol);
    }

    public void setTime(int time){
        this.time.setText(String.valueOf(time));

    }

    public void reloadTransacts(List<Transact> transacts) {
        table.getItems().clear();
        ObservableList<Transact> observTransacts = FXCollections.observableList(transacts);
        table.setItems(observTransacts);
    }
}
