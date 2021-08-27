package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class ViewController {

    public void changeScene(ActionEvent event, String sceneName) throws IOException {

        String scenePath = "../view/" + sceneName + ".fxml";
        Node node = (Node) event.getSource(); // to find existing stage
        Stage stage = (Stage) node.getScene().getWindow(); // get place where to store
        //  stage.close();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(scenePath)));
        stage.setScene(new Scene(root, 800, 800));
        stage.show();
    }

    public void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }


}
