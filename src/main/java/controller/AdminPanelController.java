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

    //decor list tab:
    public TableView<Decor> decorTable;
    public TableColumn<Object, Object> decorIdColumn;
    public TableColumn<Object, Object> decorNameColumn;
    public TableColumn<Object, Object> decorQwtColumn;
    public TableColumn<Object, Object> decorPriceColumn;
    public TableColumn<Object, Object> decorVatColumn;
    public TableColumn<Object, Object> decorStatusColumn;
    public TextField searchTextField;

    //add decor tab:
    public TextField decorName;
    public TextField decorQuantity;
    public TextField decorPrice;
    public TextField decorStatus;
    public Button addButton;

    //update decor tab:
    public Tab updateDecorPriceTab;
    public Button updatePriceButton;
    public TableColumn<Object, Object> decorId1;
    public TableColumn<Object, Object> decorName1;
    public TableColumn<Object, Object> decorQwt1;
    public TableColumn<Object, Object> decorPrice1;
    public TableColumn<Object, Object> decorPriceVat1;
    public TableColumn<Object, Object> decorStatus1;
    public TextField newPriceField;
    public Button updateQuantityButton;
    public TextField newQuantityField;
    public TableView<Decor> updateDecorTableList;
    public TextField updateDecorIdField;

    //delete decor tab:
    public Tab deleteDecorTab;
    public TableView<Decor> decorListTable;
    public TableColumn<Object, Object> decorIdColumn1;
    public TableColumn<Object, Object> decorNameColumn1;
    public TableColumn<Object, Object> decorQwtColumn1;
    public TableColumn<Object, Object> decorPriceColumn1;
    public TableColumn<Object, Object> decorVatColumn1;
    public TableColumn<Object, Object> decorStatusColumn1;
    public Button deleteButton;
    public TextField deleteDecorIdField;

    //Event List tab:
    public Tab eventListTab;
    public TableColumn<Object, Object> eventIdColumn;
    public TableColumn<Object, Object> eventNameColumn;
    public TableColumn<Object, Object> eventDateColumn;
    public TableColumn<Object, Object> eventTimeColumn;
    public TableColumn<Object, Object> eventLocationColumn;
    public TableColumn<Object, Object> eventGuestQwtColumn;
    public TextField searchEvent;
    public TableView<Event> eventListTable;

    //add event tab:
    public Tab addEventTab;
    public TextField eventName;
    public DatePicker eventDate;
    public TextField eventTime;
    public TextField eventLocation;
    public TextField eventGuestQwt;
    public Button addEventButton;

    //delete event tab:
    public Tab deleteEventTab;
    public TextField deleteEventIdField;
    public TableColumn<Object, Object> eventIdColumn1;
    public TableColumn<Object, Object> eventNameColumn1;
    public TableColumn<Object, Object> eventDateColumn1;
    public TableColumn<Object, Object> eventTimeColumn1;
    public TableColumn<Object, Object> eventLocationColumn1;
    public TableColumn<Object, Object> eventGuestQwtColumn1;
    public Button deleteEventButton;
    public TableView<Event> deleteEventTable;
    
    //update event tab:
    public Tab updateEventTab;
    public TextField updateEventIdField;
    public TextField updateEventName;
    public TextField updateEventTime;
    public TextField updateEventLocation;
    public TextField updateEventQwt;
    public Button updateNameButton;
    public Button updateDateButton;
    public Button updateTimeButton;
    public Button updateLocationButton;
    public Button updateGuestQwtButton;
    public TableView<Event> eventListTable1;
    public TableColumn<Object, Object> eventIdColumn2;
    public TableColumn<Object, Object> eventNameColumn2;
    public TableColumn<Object, Object> eventDateColumn2;
    public TableColumn<Object, Object> eventTimeColumn2;
    public TableColumn<Object, Object> eventLocationColumn2;
    public TableColumn<Object, Object> eventGuestQwtColumn2;
    public DatePicker updateEventDate;

    //Log out button:
    public Button goBackButton;
    
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
        fillInUpdateDecorTable();
        fillInDeleteDecorTable();
        fillInEventListTable();
        fillInDeleteEventListTable();
        fillInUpdateEventTable();
        filterDecorTable();
        filterEventListTable();
    }

    //DECOR SADAĻAS:
    public void fillInDeleteDecorTable(){
        decorIdColumn1.setCellValueFactory(new PropertyValueFactory<>("decorId"));
        decorNameColumn1.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        decorQwtColumn1.setCellValueFactory(new PropertyValueFactory<>("decorQwt"));
        decorPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("decorPrice"));
        decorVatColumn1.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        decorStatusColumn1.setCellValueFactory(new PropertyValueFactory<>("decorStatus"));
        decorListTable.setItems(decors);
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
    public void fillInDecorTable() {
        decorIdColumn.setCellValueFactory(new PropertyValueFactory<>("decorId"));
        decorNameColumn.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        decorQwtColumn.setCellValueFactory(new PropertyValueFactory<>("decorQwt"));
        decorPriceColumn.setCellValueFactory(new PropertyValueFactory<>("decorPrice"));
        decorVatColumn.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        decorStatusColumn.setCellValueFactory(new PropertyValueFactory<>("decorStatus"));
        decorTable.setItems(decors);
    }
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

    //EVENT SADAĻAS
    public void fillInEventListTable(){
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dueTime"));
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventGuestQwtColumn.setCellValueFactory(new PropertyValueFactory<>("guestNumber"));
        eventListTable.setItems(events);
    }
    public void fillInUpdateEventTable(){
        eventIdColumn2.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        eventNameColumn2.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn2.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        eventTimeColumn2.setCellValueFactory(new PropertyValueFactory<>("dueTime"));
        eventLocationColumn2.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventGuestQwtColumn2.setCellValueFactory(new PropertyValueFactory<>("guestNumber"));
        eventListTable1.setItems(events);
    }
    public void fillInDeleteEventListTable(){
        eventIdColumn1.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        eventNameColumn1.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn1.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        eventTimeColumn1.setCellValueFactory(new PropertyValueFactory<>("dueTime"));
        eventLocationColumn1.setCellValueFactory(new PropertyValueFactory<>("location"));
        eventGuestQwtColumn1.setCellValueFactory(new PropertyValueFactory<>("guestNumber"));
        deleteEventTable.setItems(events);
    }
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

    //DECOR METHODS:
    public void handleDeleteButton(ActionEvent actionEvent) throws SQLException, IOException {
        decorDBService.deleteDecor(Integer.parseInt(deleteDecorIdField.getText()));
        showAlert("Success", "Decor deleted successfully!", Alert.AlertType.CONFIRMATION);
        changeScene(actionEvent, "admin");
    }
    public void handleAddButton(ActionEvent actionEvent) throws SQLException, IOException {
        decorDBService.insertNewDecor(
                decorName.getText(),
                Integer.parseInt(decorQuantity.getText()),
                Double.parseDouble(decorPrice.getText()),
                decorStatus.getText()
        );
        showAlert("Success", "Decor added successfully!", Alert.AlertType.CONFIRMATION);
        changeScene(actionEvent, "admin");
    }
    public void handleUpdatePriceButton(ActionEvent actionEvent) throws SQLException, IOException {
        decorDBService.updateDecorPrice(
                Integer.parseInt(updateDecorIdField.getText()),
                Double.parseDouble(newPriceField.getText())
        );
        showAlert("Success", "Decor price updated successfully!", Alert.AlertType.CONFIRMATION);
        changeScene(actionEvent, "admin");
    }
    public void handleUpdateQuantityButton(ActionEvent actionEvent) throws SQLException, IOException {
        decorDBService.updateDecorQuantity(
                Integer.parseInt(updateDecorIdField.getText()),
                Integer.parseInt(newQuantityField.getText())
        );
        showAlert("Success", "Decor quantity updated successfully!", Alert.AlertType.CONFIRMATION);
        changeScene(actionEvent, "admin");
    }

    //EVENT T+METHODS:
    public void handleAddEventButton(ActionEvent actionEvent) {
        try {
            eventDBService.insertNewEventAdmin(
                    eventName.getText(),
                    Date.valueOf(eventDate.getValue()),
                    eventTime.getText(),
                    eventLocation.getText(),
                    Integer.parseInt(eventGuestQwt.getText())
            );
            showAlert("Success", "Event added successfully!", Alert.AlertType.CONFIRMATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void handleDeleteEventButton(ActionEvent actionEvent) throws SQLException, IOException {
        eventDBService.deleteEvent(
                Integer.parseInt(deleteEventIdField.getText())
        );
        showAlert("Success", "Event deleted successfully!", Alert.AlertType.CONFIRMATION);
        changeScene(actionEvent, "admin");
    }
    public void handleUpdateEventNameButton(ActionEvent actionEvent) throws SQLException, IOException {
        eventDBService.updateEventName(
                Integer.parseInt(updateEventIdField.getText()),
                updateEventName.getText()
        );
        showAlert("Success", "Event Name updated successfully!", Alert.AlertType.CONFIRMATION);
        changeScene(actionEvent, "admin");
    }
    public void handleUpdateEventDateButton(ActionEvent actionEvent) throws SQLException, IOException {
        eventDBService.updateEventDate(
                Integer.parseInt(updateEventIdField.getText()),
                Date.valueOf(updateEventDate.getValue())
        );
        showAlert("Success", "Event Date updated successfully!", Alert.AlertType.CONFIRMATION);
        changeScene(actionEvent, "admin");
    }
    public void handleUpdateEventTimeButton(ActionEvent actionEvent) throws SQLException, IOException {
        eventDBService.updateEventTime(
                Integer.parseInt(updateEventIdField.getText()),
                updateEventTime.getText()
        );
        showAlert("Success", "Event Time updated successfully!", Alert.AlertType.CONFIRMATION);
        changeScene(actionEvent, "admin");
    }
    public void handleUpdateLocationButton(ActionEvent actionEvent) throws SQLException, IOException {
        eventDBService.updateEventLocation(
                Integer.parseInt(updateEventIdField.getText()),
                updateEventLocation.getText()
        );
        showAlert("Success", "Event Location updated successfully!", Alert.AlertType.CONFIRMATION);
        changeScene(actionEvent, "admin");
    }

    public void handleUpdateGuestQwtButton(ActionEvent actionEvent) throws SQLException, IOException {
        eventDBService.updateEventGuestQuantity(
                Integer.parseInt(updateEventIdField.getText()),
                Integer.parseInt(updateEventQwt.getText())
        );
        showAlert("Success", "Event Guest Quantity updated successfully!", Alert.AlertType.CONFIRMATION);
        changeScene(actionEvent, "admin");
    }

    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
