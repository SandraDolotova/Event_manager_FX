package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import users.User;
import users.UserDBService;

import java.io.IOException;
import java.sql.SQLException;

public class ConceptPackage extends ViewController {
    UserDBService userDBService = new UserDBService();
    public TextField fullNameCallBackField;
    public TextField callBackPhone;

    public ConceptPackage() throws Exception {
    }


    public void handleCallBackPhone(ActionEvent actionEvent) throws SQLException {
        try {
            User user = new User(
                    fullNameCallBackField.getText(),
                    callBackPhone.getText());
            userDBService.addCallBackConcept(user);
            showAlert("Registration successful", "We will call you back as soon as possible", Alert.AlertType.CONFIRMATION);
        }catch (Exception e){
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
}
