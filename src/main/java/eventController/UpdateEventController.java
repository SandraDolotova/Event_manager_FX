package eventController;

import controller.ViewController;
import events.Event;
import events.EventDBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateEventController extends ViewController implements Initializable {

    public TextField updateEventId;
    public TextField updateEventName;
    public TextField updateEventTime;
    public TextField updateEventLocation;
    public TextField updateEventGuestQwt;
    public DatePicker updateEventDate;
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
    public Button backButton;


    EventDBService eventDBService = new EventDBService();
    List<Event> eventList = new ArrayList<>(eventDBService.showAllEvents());
    ObservableList<Event> events = FXCollections.observableArrayList(eventList);
    FilteredList<Event> filteredEvents = new FilteredList<>(events, p -> true);

    public UpdateEventController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInUpdateEventTable();
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

    public void handleUpdateEventNameButton(ActionEvent actionEvent) {
        try {
            eventDBService.updateEventName(
                    Integer.parseInt(updateEventId.getText()),
                    updateEventName.getText()
            );
            showAlert("Success", "Event Name updated successfully!", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "updateEvent");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems updating event name. Please try again!", Alert.AlertType.ERROR);
        }
    }
    public void handleUpdateEventDateButton(ActionEvent actionEvent) {
        try {
            eventDBService.updateEventDate(
                    Integer.parseInt(updateEventId.getText()),
                    Date.valueOf(updateEventDate.getValue())
            );
            showAlert("Success", "Event Date updated successfully!", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "updateEvent");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems updating event date. Please try again!", Alert.AlertType.ERROR);
        }
    }
    public void handleUpdateEventTimeButton(ActionEvent actionEvent) {
        try {
            eventDBService.updateEventTime(
                    Integer.parseInt(updateEventId.getText()),
                    updateEventTime.getText()
            );
            showAlert("Success", "Event Time updated successfully!", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "updateEvent");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems updating event time. Please try again!", Alert.AlertType.ERROR);
        }
    }
    public void handleUpdateLocationButton(ActionEvent actionEvent) {
        try {
            eventDBService.updateEventLocation(
                    Integer.parseInt(updateEventId.getText()),
                    updateEventLocation.getText()
            );
            showAlert("Success", "Event Location updated successfully!", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "updateEvent");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems updating event location. Please try again!", Alert.AlertType.ERROR);
        }
    }
    public void handleUpdateGuestQwtButton(ActionEvent actionEvent) {
        try {
            eventDBService.updateEventGuestQuantity(
                    Integer.parseInt(updateEventId.getText()),
                    Integer.parseInt(updateEventGuestQwt.getText())
            );
            showAlert("Success", "Event Guest Quantity updated successfully!", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "updateEvent");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems updating event guest quantity. Please try again!", Alert.AlertType.ERROR);
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
