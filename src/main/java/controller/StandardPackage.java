package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import packageTypes.PackagePrice;
import users.UserDBService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StandardPackage extends ViewController implements Initializable {

    @FXML
    private TextField fullNameCallBackField;
    @FXML
    private TextField callBackPhone;
    @FXML
    private Pagination pagination;

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
