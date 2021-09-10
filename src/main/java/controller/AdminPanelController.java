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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPanelController extends ViewController implements Initializable {

    //Log out button:
    public Button logOutButton;

    //Decor list tab:
    public Tab decorListTab;
    public TableView<Decor> decorTable;
    public TableColumn<Object, Object> decorIdColumn;
    public TableColumn<Object, Object> decorNameColumn;
    public TableColumn<Object, Object> decorQwtColumn;
    public TableColumn<Object, Object> decorPriceColumn;
    public TableColumn<Object, Object> decorVatColumn;
    public TableColumn<Object, Object> decorStatusColumn;
    public TextField searchTextField;
    public Button addDecorButton;
    public Button updateDecorButton;
    public Button deleteDecorButton;

    //Event List tab:
    public Tab eventListTab;
    public TableView<Event> eventListTable;
    public TableColumn<Object, Object> eventIdColumn;
    public TableColumn<Object, Object> eventNameColumn;
    public TableColumn<Object, Object> eventDateColumn;
    public TableColumn<Object, Object> eventTimeColumn;
    public TableColumn<Object, Object> eventLocationColumn;
    public TableColumn<Object, Object> eventGuestQwtColumn;
    public TextField searchEvent;
    public Button addEventButton;
    public Button updateEventButton;
    public Button deleteEventButton;

    //Payment tab:
    public Tab paymentTab;
    public TableView billTable;
    public TableColumn billIdColumn;
    public TableColumn customerNameColumn;
    public TableColumn eventNameColumn3;
    public TableColumn eventDateColumn3;
    public TableColumn decorNameColumn3;
    public TableColumn decorQwtColumn3;
    public TableColumn totalPriceColumn;
    public TableColumn paymentStatusColumn;

    DecorDBService decorDBService = new DecorDBService();
    EventDBService eventDBService = new EventDBService();

    List<Decor> decorList = new ArrayList<>(decorDBService.showAllDecorAdmin());
    ObservableList<Decor> decors = FXCollections.observableArrayList(decorList);
    FilteredList<Decor> filteredDecors = new FilteredList<>(decors, p -> true);

    List<Event> eventList = new ArrayList<>(eventDBService.showAllEvents());
    ObservableList<Event> events = FXCollections.observableArrayList(eventList);
    FilteredList<Event> filteredEvents = new FilteredList<>(events, p -> true);

    public AdminPanelController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInDecorTable();
        fillInEventListTable();
        filterDecorTable();
        filterEventListTable();
    }
    //DECOR LIST TAB
    public void fillInDecorTable() {
        decorIdColumn.setCellValueFactory(new PropertyValueFactory<>("decorId"));
        decorNameColumn.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        decorQwtColumn.setCellValueFactory(new PropertyValueFactory<>("decorQwt"));
        decorPriceColumn.setCellValueFactory(new PropertyValueFactory<>("decorPrice"));
        decorVatColumn.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        decorStatusColumn.setCellValueFactory(new PropertyValueFactory<>("decorStatus"));
        decorTable.setItems(decors);
    }
    //decor search method
    public void filterDecorTable() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDecors.setPredicate(decor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(decor.getDecorName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(decor.getDecorStatus()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(decor.getDecorQwt()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(decor.getDecorPriceVAT()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Decor> sortedDecor = new SortedList<>(filteredDecors);
        sortedDecor.comparatorProperty().bind(decorTable.comparatorProperty());
        decorTable.setItems(sortedDecor);
    }

    public void fillInPaymentTable(){

    }

    //EVENT LIST TAB:
    public void fillInEventListTable(){
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dueTime"));
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventGuestQwtColumn.setCellValueFactory(new PropertyValueFactory<>("guestNumber"));
        eventListTable.setItems(events);
    }
    //event search method
    public void filterEventListTable(){
        searchEvent.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEvents.setPredicate(event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(event.getEventName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(event.getDueDate()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(event.getDueTime()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(event.getLocation()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Event> sortedEvent = new SortedList<>(filteredEvents);
        sortedEvent.comparatorProperty().bind(eventListTable.comparatorProperty());
        eventListTable.setItems(sortedEvent);
    }

    public void handleLogOutButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //DECOR LIST TAB sadaļas pogas - add, update, delete
    public void handleAddDecorButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "addDecor");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void handleUpdateDecorButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "updateDecor");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void handleDeleteDecorButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "deleteDecor");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //EVENT LIST TAB sadaļas pogas - add, update, delete
    public void handleAddEventButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "addEvent");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void handleUpdateEventButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "updateEvent");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void handleDeleteEventButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "deleteEvent");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
