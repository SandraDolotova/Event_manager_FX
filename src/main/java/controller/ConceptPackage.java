package controller;

import packageTypes.PackagePrice;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import users.UserDBService;

import java.io.IOException;
import java.sql.SQLException;

public class ConceptPackage extends ViewController {
    UserDBService userDBService = new UserDBService();
    public TextField fullNameCallBackField;
    public TextField callBackPhone;

    public ConceptPackage() throws Exception {
    }

    public void handleCallBackPhone(ActionEvent actionEvent) throws Exception {
        try {
            validateUserInput();
            userDBService.addCallBackConcept(
                    fullNameCallBackField.getText(),
                    (int)Long.parseLong(callBackPhone.getText()),
                    String.valueOf(PackagePrice.CONCEPT)
            );
            showAlert("Registration successful", "We will call you back as soon as possible", Alert.AlertType.CONFIRMATION);
        } catch (SQLException e) {
            showAlert("Registration failed", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void validateUserInput() throws Exception {
        if (fullNameCallBackField.getText().isEmpty())
            throw new Exception("Please fill in your full name");
        if (callBackPhone.getText().isEmpty())
            throw new Exception("Please write in your phone number");
    }
}
