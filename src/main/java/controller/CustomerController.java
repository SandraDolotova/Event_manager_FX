package controller;

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
import users.User;
import users.UserDBService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController extends ViewController implements Initializable {

    EventDBService eventDBService = new EventDBService();
    UserDBService userDBService = new UserDBService();
    DecorDBService decorDBService = new DecorDBService();
    Decor decor;
    Event event;

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
    private ListView<User> guestList;
    @FXML
    private TextField timeField;


    List<Decor> decorList = new ArrayList<>(decorDBService.showAllDecorCustomer());
    ObservableList<Decor> decors = FXCollections.observableArrayList(decorList);
    FilteredList<Decor> filteredDecors = new FilteredList<>(decors, p -> true);

    List<User> fullGuestList = new ArrayList<>(userDBService.showAllGuests());
    ObservableList<User> listOfGuests = FXCollections.observableArrayList(fullGuestList);


    public CustomerController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInCustomerDecorTable();
        filterCustomerTable();
        getSelectedDecor();
        fillInGuestList();
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

    public void handleAddCustomerDecor(ActionEvent actionEvent) {
        TablePosition pos = customerDecorTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Decor item = customerDecorTable.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        String decorName = (String) col.getCellObservableValue(item).getValue();
        if (insertedDecorQwnt.getText().isEmpty()) {
            showAlert("Error", "Please set decor quantity", Alert.AlertType.ERROR);
        } else {
            try {
                Decor decor = new Decor(
                        decorName,
                        Integer.parseInt(insertedDecorQwnt.getText())
                );
                decorDBService.insertCustomerChosenDecor(decor);
                showAlert("Done", "Decor unit has been added to your choice list", Alert.AlertType.CONFIRMATION);
                decorDBService.updateCustomerDecor();
            } catch (Exception e) {
                showAlert("Decor unit was not added", e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
            customerDecorField.clear();
            insertedDecorQwnt.clear();

        }
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

   public void handleInsertButton(ActionEvent actionEvent) {
       /* try {
            validateUserInput();
            Event event = new Event(
                    eventNameField.getText(),
                    calendar.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                            timeField.getValue(),
                            locationField.getText(),
                            guestNumberField.getText()));
            eventDBService.insertNewEvent(event);
            showAlert("Event successfully added", "Now please proceed with decor and guest list  ", Alert.AlertType.CONFIRMATION);

        } catch (Exception e) {
            showAlert("Event registration failed", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }*/
    }

  /*  private void validateUserInput() throws Exception {
        if (eventNameField.getText().isEmpty())
            throw new Exception("Event name cannot be blank. Please fill out this field!");
        if (calendar.getValue().isEmpty) throw new Exception("Event date cannot be blank. Please choose a date!");
        if (calendar.getValue().isBefore(LocalDate.now()))
            throw new Exception("Event name cannot be blank. Please choose a date!");
        if (locationField.getText().isEmpty())
            throw new Exception("Location cannot be blank. Please fill out this field!");
        if (guestNumberField.getText().isEmpty())
            throw new Exception("Guest number cannot be blank. Please fill out this field!");
    }*/

    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void fillInGuestList() {
        guestList.setEditable(true);
        guestList.setItems(listOfGuests);
    }

    public void handleGuestAddButton(ActionEvent actionEvent) throws SQLException {
        if (guestNameField.getText().isEmpty()) {
            showAlert("Error", "Please write in guest full name", Alert.AlertType.ERROR);
        } else {
            try {
                User user = new User(guestNameField.getText());
                userDBService.insertGuests(user);

                showAlert("Successfully", guestNameField.getText() + " has been added to your guest list", Alert.AlertType.CONFIRMATION);
                guestList.getItems().add(user);

            } catch (Exception e) {
                showAlert("Error. Guest was not added", e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
        guestNameField.clear();
        fillInGuestList();
    }

    public void handleDeleteGuestButton(ActionEvent actionEvent) {
        User selectedGuest = guestList.getSelectionModel().getSelectedItem();
        listOfGuests.remove(selectedGuest);
        if (selectedGuest != null) {
            try {
                userDBService.deleteGuest(selectedGuest);
                showAlert("Successfully", selectedGuest + " has been removed from your guest list", Alert.AlertType.CONFIRMATION);
            } catch (Exception e) {
                showAlert("Error. Guest was not deleted", e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }




}