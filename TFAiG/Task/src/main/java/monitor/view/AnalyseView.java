package monitor.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import monitor.service.Analyzer;
import monitor.service.entity.DataItem;
import monitor.service.entity.Result;

import java.net.URL;
import java.util.ResourceBundle;


public class AnalyseView extends Application implements Initializable {

    public TableColumn<DataItem, Integer> id1;
    public TableColumn<DataItem, Integer> id2;
    @FXML
    private TableColumn<DataItem, String> identificators;
    @FXML
    private TableColumn<DataItem, String> constants;
    @FXML
    private TableView<DataItem> constantsTable;
    @FXML
    private TableView<DataItem> identificatorsTable;

    @FXML
    private TextField input;
    @FXML
    private Label output;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Analyzer");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void analyze(ActionEvent event) throws Exception {
        new Thread(() -> {
            Analyzer analyzer = new Analyzer();
            Result result = analyzer.analyse(input.getText());
            if (result.isSuccess()) {
                Platform.runLater(() -> {
                    output.setText("Успешно!");
                    constantsTable.setItems(FXCollections.observableList(result.getConstants()));
                    identificatorsTable.setItems(FXCollections.observableList(result.getIdentificators()));
                });
            }else {
                Platform.runLater(()->{
                    output.setText(result.getMessage() + " Позиция:" + result.getPosition());
                    input.requestFocus();
                    input.selectRange(result.getPosition(), result.getPosition()+1);
                });

            }
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        identificators.setCellValueFactory(new PropertyValueFactory<DataItem, String>("data"));
        constants.setCellValueFactory(new PropertyValueFactory<DataItem, String>("data"));
        id1.setCellValueFactory(new PropertyValueFactory<DataItem, Integer>("id"));
        id2.setCellValueFactory(new PropertyValueFactory<DataItem, Integer>("id"));
    }
}
