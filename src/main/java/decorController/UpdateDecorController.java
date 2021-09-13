package decorController;

import controller.ViewController;
import decor.Decor;
import decor.DecorDBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateDecorController extends ViewController implements Initializable {


    public TableView updateDecorTableList;
    public TableColumn decorId1;
    public TableColumn decorName1;
    public TableColumn decorQwt1;
    public TableColumn decorPrice1;
    public TableColumn decorPriceVat1;
    public TableColumn decorStatus1;
    public TextField updateDecorId;
    public TextField newPriceField;
    public TextField newQuantityField;
    public Button updatePriceButton;
    public Button updateQuantityButton;
    public Button backButton;



    DecorDBService decorDBService = new DecorDBService();
    List<Decor> decorList = new ArrayList<>(decorDBService.showAllDecorAdmin());
    ObservableList<Decor> decors = FXCollections.observableArrayList(decorList);

    public UpdateDecorController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInUpdateDecorTable();
    }

    public void fillInUpdateDecorTable(){
        decorId1.setCellValueFactory(new PropertyValueFactory<>("decorId"));
        decorName1.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        decorQwt1.setCellValueFactory(new PropertyValueFactory<>("decorQwt"));
        decorPrice1.setCellValueFactory(new PropertyValueFactory<>("decorPrice"));
        decorPriceVat1.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        decorStatus1.setCellValueFactory(new PropertyValueFactory<>("decorStatus"));
        updateDecorTableList.setItems(decors);
    }

    public void handleUpdatePriceButton(ActionEvent actionEvent) {
        try {
            decorDBService.updateDecorPrice(
                    Integer.parseInt(updateDecorId.getText()),
                    Double.parseDouble(newPriceField.getText())
            );
            showAlert("Success", "Decor price updated successfully!", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "updateDecor");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems updating decor price. Please try again!", Alert.AlertType.ERROR);
        }
    }

    public void handleUpdateQuantityButton(ActionEvent actionEvent) {
        try {
            decorDBService.updateDecorQuantity(
                    Integer.parseInt(updateDecorId.getText()),
                    Integer.parseInt(newQuantityField.getText())
            );
            showAlert("Success", "Decor quantity updated successfully!", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "updateDecor");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems updating decor quantity. Please try again!", Alert.AlertType.ERROR);
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
