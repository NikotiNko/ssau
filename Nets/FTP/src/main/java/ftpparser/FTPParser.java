package ftpparser;

import com.sun.javafx.collections.ObservableListWrapper;
import ftpparser.service.FTPService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;


public class FTPParser extends Application implements Initializable {

    @FXML
    private TextField address;

    @FXML
    private TextField pass;

    @FXML
    private TextField login;

    @FXML
    private ListView<String> output;

    @FXML
    private Label info;

    @FXML
    private Button button;

    @FXML
    private ProgressIndicator indicator;


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    private void process(ActionEvent event) throws Exception {
        initLoading(true);
        new Thread(() -> {
            try {
                String server = address.getText();
                int port = 21;
                String user = login.getText();
                String pass = this.pass.getText();

                FTPClient ftpClient = FTPService.connect(server, port, user, pass);
                Map<String, Long> filesSizes = FTPService.getFilesSizes(ftpClient);
                output.setItems(convertToList(filesSizes));
                Platform.runLater(() -> {
                    initLoading(false);
                });
            } catch (Throwable e) {
                Platform.runLater(() -> {
                    initLoading(false);
                    info.setVisible(true);
                    info.setText("Произошла ошибка:" + e.getMessage());
                });
                e.printStackTrace();
            }
        }).start();
    }

    public void initialize(URL url, ResourceBundle rb) {
    }

    private void initLoading(boolean state) {
        indicator.setVisible(state);
        button.setDisable(state);
        address.setDisable(state);
        pass.setDisable(state);
        login.setDisable(state);
        output.setDisable(state);
    }

    private ObservableList<String> convertToList(Map<String, Long> map) {
        ObservableList<String> result = new ObservableListWrapper<String>(new ArrayList<>());
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            result.add(entry.getKey() + ": " + processByteValue(entry.getValue()));
        }
        return result;
    }

    private String processByteValue(Long value) {
        if (value < 1024) {
            return value + " bytes";
        } else if (value < (1024 * 1024)) {
            return Math.rint(100*(double) value / 1024)/100 + " KB";
        } else
            return Math.rint(100*(double) value / (1024 * 1024))/100 + " MB";
    }
}
