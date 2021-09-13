package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import packageTypes.PackagePrice;
import users.UserDBService;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class StandardPackage extends ViewController implements Initializable {

    @FXML
    private TextField fullNameCallBackField;
    @FXML
    private TextField callBackPhone;

    UserDBService userDBService = new UserDBService();

    public StandardPackage() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleCallBackPhone(ActionEvent actionEvent) {
        try {
            validateUserInput();
            userDBService.addCallBackConcept(
                    fullNameCallBackField.getText(),
                    (int)Long.parseLong(callBackPhone.getText()),
                    String.valueOf(PackagePrice.STANDARD)
            );
            showAlert("Registration successful", "We will call you back as soon as possible", Alert.AlertType.CONFIRMATION);
        } catch (Exception e) {
            showAlert("Registration failed", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void validateUserInput() throws Exception {
        if (fullNameCallBackField.getText().isEmpty())
            throw new Exception("Please fill in your full name");
        if (callBackPhone.getText().isEmpty())
            throw new Exception("Please write in your phone number");
    }




}
