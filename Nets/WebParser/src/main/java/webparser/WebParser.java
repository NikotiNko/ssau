package webparser;

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
import webparser.service.HttpService;
import webparser.service.ParseService;

import java.net.URL;
import java.util.*;


public class WebParser extends Application implements Initializable {

    @FXML
    private TextField input;

    @FXML
    private TreeView<String> output;

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
                String host = input.getText();
                Collection<String> links = getLinks(host, 3);
                int linksCount = links.size();
                TreeItem<String> root = getTreeRootItem(links, host);
                Map<String,String> headers = HttpService.getHeaders();
                String contentType = headers.get("Content-Type");
                int indexOfCharset = -1;
                if (contentType!=null) {
                    indexOfCharset = headers.get("Content-Type").indexOf("charset");
                }
                String charset = null;
                if (indexOfCharset!=-1){
                    charset = headers.get("Content-Type").substring(indexOfCharset + "charset".length() + 1);
                }
                final String finalCharset = charset;
                Platform.runLater(() -> {
                    output.setRoot(root);
                    String server = headers.get("Server");
                    if (server == null) {
                        server = "Not_Found";
                    }
                    info.setText("Сервер: " + server + (finalCharset !=null?"\nКодировка: " + finalCharset :"")+ "\nКоличество ссылок: " + linksCount);
                    initLoading(false);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void initialize(URL url, ResourceBundle rb) {    }

    private void initLoading(boolean state) {
        indicator.setVisible(state);
        button.setDisable(state);
        input.setDisable(state);
        output.setDisable(state);
    }

    private Collection<String> getLinks(String host, int depth) throws Exception {
        Collection<String> resultLinks;
        if (depth > 0) {
            String hostBody = HttpService.sendRequest(host, "GET");
            resultLinks = ParseService.parseHtml(hostBody);
            depth--;

            Collection<String> currentLinks = resultLinks;
            for (int i = 0; i < depth; i++) {
                Collection<String> tempCollection = new HashSet<>();
                for (String tempLink : currentLinks) {
                    String body = HttpService.sendRequest(host, tempLink, "GET");
                    tempCollection.addAll(ParseService.parseHtml(body, tempLink));
                }

                tempCollection.removeAll(resultLinks);
                resultLinks.addAll(tempCollection);
                currentLinks = tempCollection;
            }
            return resultLinks;
        }
        return Collections.emptyList();
    }

    private TreeItem<String> getTreeRootItem(Collection<String> links, String rootName) {
        TreeItem<String> root = new TreeItem<>(rootName);
        Iterator<String> linksIterator = links.iterator();
        while (linksIterator.hasNext()) {
            String link = linksIterator.next();
            String[] elements = link.split("/");
            TreeItem<String> currentRoot = root;
            for (String element : elements) {
                if (!element.isEmpty()) {
                    TreeItem<String> container = contains(currentRoot, element);
                    if (container != null) {
                        currentRoot = container;
                    } else {
                        TreeItem<String> newChild = new TreeItem<>(element);
                        currentRoot.getChildren().add(newChild);
                        currentRoot = newChild;
                    }
                }
            }
            linksIterator.remove();
        }
        return root;
    }

    private TreeItem<String> contains(TreeItem<String> item, String child) {
        for (TreeItem<String> childItem : item.getChildren()) {
            if (childItem.getValue().equals(child)) {
                return childItem;
            }
        }
        return null;
    }
}
