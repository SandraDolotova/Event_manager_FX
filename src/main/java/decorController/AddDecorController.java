package decorController;

import controller.ViewController;
import decor.Decor;
import decor.DecorDBService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddDecorController extends ViewController implements Initializable {


    public TextField decorName;
    public TextField decorQuantity;
    public TextField decorPrice;
    public TextField decorStatus;
    public Button addButton;
    public Button backButton;

    DecorDBService decorDBService = new DecorDBService();
    List<Decor> decorList = new ArrayList<>(decorDBService.showAllDecorAdmin());

    public AddDecorController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleAddButton(ActionEvent actionEvent) {
        try {
            decorDBService.insertNewDecor(
                    decorName.getText(),
                    Integer.parseInt(decorQuantity.getText()),
                    Double.parseDouble(decorPrice.getText()),
                    decorStatus.getText()
            );
            showAlert("Success", "Decor added successfully!", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "addDecor");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems adding decor. Please try again!", Alert.AlertType.ERROR);
        }
    }

    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "admin");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
