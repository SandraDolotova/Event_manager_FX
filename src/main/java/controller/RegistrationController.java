package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import users.User;
import users.UserDBService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController extends ViewController {
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField loginNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    UserDBService userDBService = new UserDBService();

    public RegistrationController() throws Exception {
    }

    public void handleRegistration(ActionEvent actionEvent) {
        try {
            validateUserInput();
            User user = new User(
                    fullNameField.getText(),
                    emailField.getText(),
                    Integer.parseInt(phoneNumberField.getText()),
                    loginNameField.getText(),
                    passwordField.getText());
            userDBService.addNewUser(user);
            showAlert("Registration successful", "Now please processed with login", Alert.AlertType.CONFIRMATION);

        } catch (Exception e) {
            showAlert("Registration failed", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void handleLoginButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void validateUserInput() throws Exception {
        if (loginNameField.getText().equals("admin")) throw new Exception("Login name exists. Try another!");
        if (!passwordField.getText().equals(confirmPasswordField.getText()))
            throw new Exception("Password does not match with confirm password. Try again!");
        if (passwordField.getText().length() < 4) throw new Exception("Password should be min 4 char");
        if (loginNameField.getText().length() < 5) throw new Exception("User login name should be min 5 char");

    }


}


