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

public class DeleteDecorController extends ViewController implements Initializable {

    public TableView<Decor> decorListTable;
    public TableColumn<Object, Object> decorIdColumn1;
    public TableColumn<Object, Object> decorNameColumn1;
    public TableColumn<Object, Object> decorQwtColumn1;
    public TableColumn<Object, Object> decorPriceColumn1;
    public TableColumn<Object, Object> decorVatColumn1;
    public TableColumn<Object, Object> decorStatusColumn1;
    public TextField deleteDecorIdField;
    public Button deleteButton;
    public Button backButton;

    DecorDBService decorDBService = new DecorDBService();
    List<Decor> decorList = new ArrayList<>(decorDBService.showAllDecorAdmin());
    ObservableList<Decor> decors = FXCollections.observableArrayList(decorList);

    public DeleteDecorController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInDeleteDecorTable();
    }

    public void fillInDeleteDecorTable(){
        decorIdColumn1.setCellValueFactory(new PropertyValueFactory<>("decorId"));
        decorNameColumn1.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        decorQwtColumn1.setCellValueFactory(new PropertyValueFactory<>("decorQwt"));
        decorPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("decorPrice"));
        decorVatColumn1.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        decorStatusColumn1.setCellValueFactory(new PropertyValueFactory<>("decorStatus"));
        decorListTable.setItems(decors);
    }

    public void handleDeleteButton(ActionEvent actionEvent) {
        try {
            decorDBService.deleteDecor(Integer.parseInt(deleteDecorIdField.getText()));
            showAlert("Success", "Decor deleted successfully!", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "deleteDecor");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems deleting decor. Please try again!", Alert.AlertType.ERROR);

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
