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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteEventController extends ViewController implements Initializable {


    public TableView<Event> deleteEventTable;
    public TableColumn<Object, Object> eventIdColumn1;
    public TableColumn<Object, Object> eventNameColumn1;
    public TableColumn<Object, Object> eventDateColumn1;
    public TableColumn<Object, Object> eventTimeColumn1;
    public TableColumn<Object, Object> eventLocationColumn1;
    public TableColumn<Object, Object> eventGuestQwtColumn1;
    public TextField deleteEventIdField;
    public Button deleteEventButton;
    public Button backButton;

    EventDBService eventDBService = new EventDBService();
    List<Event> eventList = new ArrayList<>(eventDBService.showAllEvents());
    ObservableList<Event> events = FXCollections.observableArrayList(eventList);
    FilteredList<Event> filteredEvents = new FilteredList<>(events, p -> true);

    public DeleteEventController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInDeleteEventListTable();
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

    public void handleDeleteEventButton(ActionEvent actionEvent) {
        try {
            eventDBService.deleteEvent(
                    Integer.parseInt(deleteEventIdField.getText())
            );
            showAlert("Success", "Event deleted successfully!", Alert.AlertType.CONFIRMATION);
            changeScene(actionEvent, "deleteEvent");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Error", "Problems deleting event. Please try again!", Alert.AlertType.ERROR);
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
