package email;

import com.sun.mail.smtp.SMTPTransport;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.text.LabelView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.Security;
import java.util.*;


public class EmailClient extends Application implements Initializable {


    private static Stage stage;
    private static SMTPTransport smtpTransport;
    private static String loginStr;
    private static String passStr;
    private static Session session;
    private static Multipart multipart;
    private static Map<String, String> serversHosts;
    //Login
    @FXML
    private ChoiceBox servers;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField login;

    @FXML
    private Label loginInfo;

    //Labels
    @FXML
    private ProgressIndicator loginIndicator;

    @FXML
    private Button loginButton;

    @FXML
    private Label serverLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passLabel;


    //Main
    @FXML
    private TextField subject;

    @FXML
    private TextField recipient;

    @FXML
    private TextArea body;

    @FXML
    private Label info;

    @FXML
    private ProgressIndicator indicator;

    @FXML
    private Label filesInfo;


    //Labels

    @FXML
    private Button send;

    @FXML
    private Button attach;

    @FXML
    private Button back;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label recipientLabel;


    @Override
    public void start(Stage stage) throws Exception {
        EmailClient.stage = stage;
        stage.setTitle("Email Client");
        changeScene("login.fxml");
    }

    @FXML
    private void process(ActionEvent event) throws Exception {
        initLoading2(true);
        new Thread(() -> {
            try {
                //SOME LOGIC

                Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

                sendSSLMessage();
                System.out.println("Sucessfully Sent mail to All Users");

                Platform.runLater(() -> {
                    info.setText("Сообщение успешно отправлено!");
                    initLoading2(false);
                    clear();
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    info.setVisible(false);
                    info.setText("Произошла ошибка:" + e.getMessage());
                });
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void back(ActionEvent event) throws Exception {
        changeScene("login.fxml");
    }

    @FXML
    private void login(ActionEvent event) {
        initLoading1(true);
        new Thread(() -> {
            Properties props = System.getProperties();
            String host = serversHosts.get((String) servers.getValue());
            if (host == null) {
                Platform.runLater(() -> {
                    loginInfo.setText("Выберите почтовый сервер!");
                    initLoading1(false);
                });
                return;
            }
            props.put("mail.smtps.host", host);
            props.put("mail.smtps.auth", "true");
            loginStr = login.getText();
            passStr = pass.getText();
            session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(loginStr, passStr);
                }
            });
            try {
                smtpTransport =
                        (SMTPTransport) session.getTransport("smtps");
                smtpTransport.connect();
                lastResponse();
                Platform.runLater(() -> {
                    try {
                        changeScene("main.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                multipart = new MimeMultipart();
            } catch (MessagingException e) {
                Platform.runLater(() -> {
                    loginInfo.setText("Ошибка аутентификации!");
                });
            }
            Platform.runLater(() -> {
                initLoading1(false);
            });
        }).start();

    }

    @FXML
    private void attach(ActionEvent event) throws MessagingException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File(getClass().getResource("/").getFile()));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(file.getName());
            multipart.addBodyPart(messageBodyPart);
            filesInfo.setText(!filesInfo.getText().isEmpty() ? (filesInfo.getText() + ";") : "Files:" + file.getName());
        }
    }

    private void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (servers != null) {
            serversHosts = new HashMap<>();
            List<String> serversList = new ArrayList<>();

            serversList.add("Mail");
            serversHosts.put("Mail", "smtp.mail.ru");

            ObservableList<String> observableList = FXCollections.observableList(serversList);
            servers.setItems(observableList);
        }
    }

    private void initLoading1(boolean state) {
        loginIndicator.setVisible(state);
        loginButton.setDisable(state);
        serverLabel.setDisable(state);
        loginLabel.setDisable(state);
        passLabel.setDisable(state);
        servers.setDisable(state);
        pass.setDisable(state);
        login.setDisable(state);
        loginInfo.setDisable(state);
    }

    private void initLoading2(boolean state) {
        indicator.setVisible(state);
        subject.setDisable(state);
        recipient.setDisable(state);
        body.setDisable(state);
        info.setDisable(state);
        attach.setDisable(state);
        send.setDisable(state);
        back.setDisable(state);
        subjectLabel.setDisable(state);
        recipientLabel.setDisable(state);
    }

    public void sendSSLMessage() throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(loginStr));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient.getText(), false));
        msg.setSubject(subject.getText());
        msg.setHeader("X-Mailer", "Nets lab4 :)");
        msg.setSentDate(new Date());

        BodyPart part = new MimeBodyPart();
        part.setText(body.getText());
        multipart.addBodyPart(part);
        msg.setContent(multipart);

        smtpTransport.sendMessage(msg, msg.getAllRecipients());
        lastResponse();
//        smtpTransport.close();
    }

    private void lastResponse() {
        System.out.println("Response: " + smtpTransport.getLastServerResponse());
    }

    private void clear() {
        multipart = new MimeMultipart();
        subject.clear();
        recipient.clear();
        body.clear();
        filesInfo.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
