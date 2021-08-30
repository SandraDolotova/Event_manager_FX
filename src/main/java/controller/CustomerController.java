package controller;
import decor.Decor;
import decor.DecorDBService;
import events.EventDBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import users.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController extends ViewController implements Initializable {

    DecorDBService decorDBService = new DecorDBService();
    EventDBService eventDBService = new EventDBService();
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

    @FXML
    private TextField timeField;

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
        try {
            validateUserInput();
            Event event = new Event(
                    eventNameField.getText(),
                    calendar.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                    timeField.getValue(),
                    locationField.getText(),
                    guestNumberField.getText());
            eventDBService.insertNewEvent(event);
            showAlert("Event successfully added", "Now please proceed with decor and guest list  ", Alert.AlertType.CONFIRMATION);

        } catch (Exception e) {
            showAlert("Event registration failed", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void validateUserInput() throws Exception {
        if(eventNameField.getText().isEmpty())throw new Exception("Event name cannot be blank. Please fill out this field!");
        if(calendar.getValue().isEmpty())throw new Exception("Event date cannot be blank. Please choose a date!");
        if(calendar.getValue().isBefore(LocalDate.now()))throw new Exception("Event name cannot be blank. Please choose a date!");
        if(locationField.getText().isEmpty())throw new Exception("Location cannot be blank. Please fill out this field!");
        if(guestNumberField.getText().isEmpty())throw new Exception("Guest number cannot be blank. Please fill out this field!");
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
