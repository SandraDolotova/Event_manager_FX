package controller;

import packageTypes.PackagePrice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import users.UserDBService;

import java.io.*;

public class PremiumPackage extends ViewController {

    @FXML
    private ListView<String> textList;
    @FXML
    private TextField fullNameCallBackField;
    @FXML
    private TextField callBackPhone;

    UserDBService userDBService = new UserDBService();
    ObservableList<String> rowList = FXCollections.observableArrayList();
    File fileObject;

    public PremiumPackage() throws Exception {
    }

    public void handleCallBackPhone(ActionEvent actionEvent) throws Exception {
        try {
            validateUserInput();
            userDBService.addCallBackConcept(
                    fullNameCallBackField.getText(),
                    Integer.parseInt(callBackPhone.getText()),
                    String.valueOf(PackagePrice.PREMIUM)
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
