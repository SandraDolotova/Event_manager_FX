package controller;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Order extends ViewController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }





    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "customer");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
