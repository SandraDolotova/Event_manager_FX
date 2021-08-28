package controller;
import decor.Decor;
import decor.DecorDBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController extends ViewController implements Initializable {

    DecorDBService decorDBService = new DecorDBService();
    Decor decor;

    @FXML
    private DatePicker calendar;

    @FXML
    private TextField locationField;

    @FXML
    private TableColumn<Decor, String> decorNameColumn;

    @FXML
    private TableView<Decor> customerDecorTable;

    @FXML
    private TableColumn<Decor, Integer> decorIdColumn;

    @FXML
    private TextField insertedDecorQwnt;

    @FXML
    private TextField guestNumberField;

    @FXML
    private TextField customerDecorField;

    @FXML
    private TableColumn<Decor, Double> decorVatColumn;

    @FXML
    private Button insertButton;

    @FXML
    private TextField searchDecorTextField;

    @FXML
    private TextField eventNameField;

    @FXML
    private Button addCustomerDecor;

    @FXML
    private Button backButton;

    List<Decor> decorList = new ArrayList<>(decorDBService.showAllDecorCustomer());
    ObservableList<Decor> decors = FXCollections.observableArrayList(decorList);
    FilteredList<Decor> filteredDecors = new FilteredList<>(decors, p -> true);

    public CustomerController() throws SQLException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInCustomerDecorTable();
        filterCustomerTable();
        getSelectedDecor();
    }

    public void fillInCustomerDecorTable() {
        decorIdColumn.setCellValueFactory(new PropertyValueFactory<>("decorId"));
        decorNameColumn.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        decorVatColumn.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        customerDecorTable.setItems(decors);
    }

    public void filterCustomerTable() {
        searchDecorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDecors.setPredicate(decor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(decor.getDecorName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(decor.getDecorStatus()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(decor.getDecorPriceVAT()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Decor> sortedDecor = new SortedList<>(filteredDecors);
        sortedDecor.comparatorProperty().bind(customerDecorTable.comparatorProperty());
        customerDecorTable.setItems(sortedDecor);
    }

    public void getSelectedDecor() {
        customerDecorTable.setRowFactory(tv -> {
            TableRow<Decor> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Decor clickedRow = row.getItem();
                    try {
                        customerDecorField.setText(String.valueOf(clickedRow));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    public void handleInsertButton(ActionEvent actionEvent) {

    }

    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void handleAddCustomerDecor(ActionEvent actionEvent) {
    }
}
