package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import users.UserDBService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WelcomeController extends ViewController {

    UserDBService userDBService = new UserDBService();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private RadioButton premiumRadioButton;
    @FXML
    private RadioButton premSRadioButton;
    @FXML
    private RadioButton standardRadioButton;
    @FXML
    private TextField phoneInput;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Button registerButton;
    @FXML
    private TextField userNameInputTextField;
    @FXML
    private Button loginButton;
    @FXML
    private RadioButton conceptRadioButton;
    @FXML
    private ImageView logo;
    @FXML
    private Label userNameLabel;
    @FXML
    private PasswordField passwordInputField;


    @FXML
    void handleLoginButton(ActionEvent actionEvent) throws Exception {
        try {
            if (userNameInputTextField.getText().equals("admin") && passwordInputField.getText().equals("admin")) {
                try {
                    changeScene(actionEvent, "admin");
                }catch (Exception e){
                    showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            } else if (userDBService.userCheck().containsKey(userNameInputTextField.getText()) && userDBService.userCheck().containsValue(passwordInputField.getText())) {
                System.out.println("CUSTOMER WINDOW");
                // changeScene(actionEvent, "CUSTOMER WINDOW");
            } else {
                System.out.println("Wrong input");
                showAlert("Warning message", "Incorrect input, try again", Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void handleRegisterButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "registration");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    void handleStandardRadioButton(ActionEvent actionEvent) {


        try {
           changeScene(actionEvent, "standardPackage");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    void handlePremiumRadioButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "premiumPackage");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    void handlePremSRadioButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "premiumSPackage");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void handleConceptRadioButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "conceptPackage");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }

    }





}
