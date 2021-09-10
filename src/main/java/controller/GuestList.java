package controller;

import customerData.AppData;
import db.DBHandler;
import db.Queries;
import events.Event;
import events.EventDBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import users.User;
import users.UserDBService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GuestList extends ViewController implements Initializable {
    UserDBService userDBService = new UserDBService();
    EventDBService eventDBService = new EventDBService();
    Connection connection = DBHandler.getConnection();

    @FXML
    private Button addGuestButton;
    @FXML
    private Button deleteGuestButton;
    @FXML
    private TextField guestNameField;
    @FXML
    private Button backButton;
    @FXML
    private ComboBox<Event> eventNameComboBox;
    @FXML
    private ListView<User> guestList;

    List<User> fullGuestList = new ArrayList<>(showAllGuests());
    ObservableList<User> listOfGuests = FXCollections.observableArrayList(fullGuestList);

    List<Event> customerEventList = new ArrayList<>(eventDBService.showCustomerEvents());
    ObservableList<Event> customerEvents = FXCollections.observableArrayList(customerEventList);

    public GuestList() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInGuestList();
        fillInComboBox();
    }

    public void handleGuestAddButton(ActionEvent actionEvent) throws Exception {
        Integer customerIdToDB = AppData.getInstance().getLoggedInUserId();
        String eventName = String.valueOf(eventNameComboBox.getSelectionModel().getSelectedItem());
        if (eventNameComboBox.getValue() == null) {
            showAlert("Error", "Please select your event from the list", Alert.AlertType.ERROR);
        } else {
            if (guestNameField.getText().isEmpty()) {
                showAlert("Error", "Please write in guest full name", Alert.AlertType.ERROR);
            } else {
                try {
                    User user = new User(
                            eventName,
                            customerIdToDB,
                            guestNameField.getText()
                    );
                    userDBService.insertGuests(user);
                    userDBService.updateGuestLIst();
                    showAlert("Successfully", guestNameField.getText() + " has been added to your guest list for event - " + eventName, Alert.AlertType.CONFIRMATION);
                    // guestList.getItems().add(user);
                    changeScene(actionEvent, "guestList");
                } catch (Exception e) {
                    showAlert("Error. Guest was not added", e.getMessage(), Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleDeleteGuestButton(ActionEvent actionEvent) {
        User selectedGuest = guestList.getSelectionModel().getSelectedItem();
        listOfGuests.remove(selectedGuest);
        if (selectedGuest != null) {
            try {
                userDBService.deleteGuest(selectedGuest);
                showAlert("Successfully", selectedGuest + " has been removed from your guest list", Alert.AlertType.CONFIRMATION);
                changeScene(actionEvent, "guestList");
            } catch (Exception e) {
                showAlert("Error. Guest was not deleted", e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "customer");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void fillInComboBox() {
        eventNameComboBox.setItems(customerEvents);
    }

    private void fillInGuestList() {
        guestList.setItems(listOfGuests);
    }
    public ArrayList<User> showAllGuests() throws Exception {
        Integer customerIdToDB = AppData.getInstance().getLoggedInUserId();
        String sql = "SELECT event_name, guest_id, guest_name FROM event_guest_list " +
                "WHERE customer_id = '" + customerIdToDB + "'";
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement pr = connection.prepareStatement(sql);
        ResultSet result = pr.executeQuery();
        while (result.next()) {
            users.add(new User(
                    result.getString("event_name"),
                    result.getInt("guest_id"),
                    result.getString("guest_name")));
            //  result.getBoolean("participation")));
        }
        pr.close();
        return users;
    }


    public void handleComboBox(ActionEvent actionEvent) {

    }
}
