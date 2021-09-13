package eventController;

import controller.ViewController;
import events.Event;
import events.EventDBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddEventController extends ViewController implements Initializable {

    public TextField eventName;
    public DatePicker eventDate;
    public TextField eventTime;
    public TextField eventLocation;
    public TextField eventGuestQwt;
    public Button addEventButton;
    public Button backButton;

    EventDBService eventDBService = new EventDBService();
    List<Event> eventList = new ArrayList<>(eventDBService.showAllEvents());
    ObservableList<Event> events = FXCollections.observableArrayList(eventList);
    FilteredList<Event> filteredEvents = new FilteredList<>(events, p -> true);

    public AddEventController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

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
            changeScene(actionEvent, "addEvent");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems adding event. Please try again!", Alert.AlertType.ERROR);
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
