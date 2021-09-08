package controller;

import db.DBHandler;
import decor.Decor;
import decor.DecorDBService;
import events.Event;
import events.EventDBService;
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
import lombok.SneakyThrows;
import customerData.AppData;
import users.User;
import users.UserDBService;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController extends ViewController implements Initializable {

    @FXML
    private DatePicker calendar;
    @FXML
    private TextField locationField;
    @FXML
    private TableColumn<Decor, String> decorNameColumn;
    @FXML
    private TableView<Decor> customerDecorTable;
    @FXML
    private TableView<Event> eventTable;
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
    private TableColumn<Event, String> eventNameColumn;
    @FXML
    private TableColumn<Event, Date> eventDateColumn;
    @FXML
    private TableColumn<Event, Integer> eventIdColumn;
    @FXML
    private TableColumn<Event, String> eventTimeColumn;
    @FXML
    private TableColumn<Event, String> eventLocationColumn;
    @FXML
    private TableColumn<Event, Integer> eventGuestsColumn;
    @FXML
    private Button insertButton;
    @FXML
    private Button customerDeleteDecorButton;
    @FXML
    private TextField searchDecorTextField;
    @FXML
    private TextField eventNameField;
    @FXML
    private Button addCustomerDecor;
    @FXML
    private Button backButton;
    @FXML
    private TextField guestNameField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField decorRentPriceField;
    @FXML
    private TextField transportPriceField;
    @FXML
    private TextField totalPriceField;
    @FXML
    private TextField customerID;
    @FXML
    private TextField customerFullNameField;
    @FXML
    private ComboBox<Event> eventNameComboBox;
    @FXML
    private ComboBox<String> timeBox;

    EventDBService eventDBService = new EventDBService();
    UserDBService userDBService = new UserDBService();
    DecorDBService decorDBService = new DecorDBService();
    Decor decor;
    Event event;
    User user = userDBService.showLoggedInCustomer(AppData.getInstance().getLoggedInUserId());

    List<Decor> decorList = new ArrayList<>(decorDBService.showAllDecorCustomer());
    ObservableList<Decor> decors = FXCollections.observableArrayList(decorList);
    FilteredList<Decor> filteredDecors = new FilteredList<>(decors, p -> true);

    List<Event> orderList = new ArrayList(eventDBService.showOrderDetails());
    ObservableList<Event> orders = FXCollections.observableArrayList(orderList);

    List<Event> customerEventList = new ArrayList<>(eventDBService.showCustomerEvents());
    ObservableList<Event> customerEvents = FXCollections.observableArrayList(customerEventList);

    public CustomerController() throws Exception {
    }

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInCustomerDecorTable();
        filterCustomerTable();
        getSelectedDecor();
        fillInCustomerOrderDetailsTable();
        showLoggedInCustomerDetails();
        fillInComboBox();
    }
    void fillInComboBox() throws SQLException {
        eventNameComboBox.setItems(customerEvents);
    }
    public void fillInCustomerOrderDetailsTable() {
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dueTime"));
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventGuestsColumn.setCellValueFactory(new PropertyValueFactory<>("guestNumber"));
        eventTable.setItems(orders);
        eventTable.setEditable(false);
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
    public void handleAddCustomerDecor(ActionEvent actionEvent) throws SQLException {
        TablePosition pos = customerDecorTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Decor item = customerDecorTable.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        String decorName = (String) col.getCellObservableValue(item).getValue();

        String customerIdToDB = customerID.getText();
        String eventName = String.valueOf(eventNameComboBox.getSelectionModel().getSelectedItem());

        if (insertedDecorQwnt.getText().isEmpty()) {
            showAlert("Error", "Please set decor quantity", Alert.AlertType.ERROR);
        }
        if (eventNameComboBox.getValue() == null) {
            showAlert("Error", "Please select your event from the list", Alert.AlertType.ERROR);
        } else {
            try {
                Decor decor = new Decor(
                        decorName, //decor name
                        Integer.parseInt(insertedDecorQwnt.getText()), // decor qwnt
                        customerIdToDB, //customer id
                        eventName // event name from combo box
                );
                decorDBService.insertCustomerChosenDecor(decor);
                decorDBService.updateCustomerDecor();
                showAlert("Done", "Decor unit has been added to your choice list", Alert.AlertType.CONFIRMATION);
            } catch (Exception e) {
                showAlert("Decor unit was not added", e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
        searchDecorTextField.clear();
        insertedDecorQwnt.clear();
    }
    public void handleCustomerDeleteDecor(ActionEvent actionEvent) {
        TablePosition pos = customerDecorTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Decor item = customerDecorTable.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        String decorName = (String) col.getCellObservableValue(item).getValue();
        if (decorName != null) {
            try {
                decorDBService.deleteCustomerDecor(decorName);
                showAlert("Done", "Chosen decor was removed from your list", Alert.AlertType.CONFIRMATION);
            } catch (Exception e) {
                showAlert("Decor eas not removed", e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }
    public void handleInsertButton(ActionEvent actionEvent) throws Exception {
        String customerIdToDB = customerID.getText();
        try {
            validateUserInput();
            Event event = new Event(
                    customerIdToDB,
                    eventNameField.getText(),
                    Date.valueOf(calendar.getValue()),
                    timeField.getText(),
                    locationField.getText(),
                    Integer.parseInt(guestNumberField.getText()));
            eventDBService.insertNewEvent(event);
            showAlert("Event successfully added", "Now please proceed with decor and guest list  ", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "customer");
        } catch (Exception e) {
            showAlert("Event registration failed", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }

    }
    private void validateUserInput() throws Exception {
        if (eventNameField.getText().isEmpty())
            throw new Exception("Event name cannot be blank. Please fill out this field!");
        if (calendar.getValue() == null) throw new Exception("Event date cannot be blank. Please choose a date!");
        if (calendar.getValue().isBefore(LocalDate.now()))
            throw new Exception("Incorrect date input. Please choose a date!");
        if (locationField.getText().isEmpty())
            throw new Exception("Location cannot be blank. Please fill out this field!");
        if (guestNumberField.getText().isEmpty())
            throw new Exception("Guest number cannot be blank. Please fill out this field!");
    }
    public void showLoggedInCustomerDetails() throws Exception {
        customerID.setEditable(false);
        customerFullNameField.setEditable(false);
        customerID.setText(String.valueOf(user.getUserId()));
        customerFullNameField.setText(user.getUserFullName());
    }
    public void handleLogOutButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void handleGuestListButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "guestList");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void handleOrderDetailsButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "order");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void handleRemoveEventCustomer(ActionEvent actionEvent) {
        TablePosition pos = eventTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Event item = eventTable.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        String eventName = (String) col.getCellObservableValue(item).getValue();
        if (eventName != null) {
            try {
                eventDBService.deleteCustomerEvent(eventName);
                showAlert("Done","Chosen event was removed from your list", Alert.AlertType.CONFIRMATION);

            } catch (Exception e) {
                showAlert("Event was not removed", e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }

    }
}