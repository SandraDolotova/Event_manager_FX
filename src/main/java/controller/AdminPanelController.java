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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import payment.Bill;
import users.User;
import users.UserDBService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPanelController extends ViewController implements Initializable {

    public Button logOutButton;
    public Button callButton;

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
    public TableView<Bill> billTable;
    public TableColumn<Object, Object> billIdColumn;
    public TableColumn<Object, Object> customerIdColumn;
    public TableColumn<Object, Object> eventNameColumn3;
    public TableColumn<Object, Object> totalPriceColumn;
    public TableColumn<Object, Object> paymentStatus;
    public ComboBox<Event> allEventsComboBox;
    public TextField totalSum;

    //Customer List tab:
    public TableView<User> customerListTable;
    public TableColumn<Object, Object> customerId;
    public TableColumn<Object, Object> customerNameColumn;
    public TableColumn<Object, Object> customerEmailColumn;
    public TableColumn<Object, Object> customerPhoneColumn;


    DecorDBService decorDBService = new DecorDBService();
    EventDBService eventDBService = new EventDBService();
    UserDBService userDBService = new UserDBService();


    List<Decor> decorList = new ArrayList<>(decorDBService.showAllDecorAdmin());
    ObservableList<Decor> decors = FXCollections.observableArrayList(decorList);
    FilteredList<Decor> filteredDecors = new FilteredList<>(decors, p -> true);

    List<Event> eventList = new ArrayList<>(eventDBService.showAllEvents());
    ObservableList<Event> events = FXCollections.observableArrayList(eventList);
    FilteredList<Event> filteredEvents = new FilteredList<>(events, p -> true);

    List<User> userList = new ArrayList<>(userDBService.showUsers());
    ObservableList<User> users = FXCollections.observableArrayList(userList);

    ObservableList<Bill> bills = FXCollections.observableArrayList();

    public AdminPanelController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInDecorTable();
        filterDecorTable();
        fillInEventListTable();
        filterEventListTable();
        fillInComboBox();
        fillInPaymentTable();
        fillInCustomerListTable();
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
        decorTable.setEditable(false);
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

    //PAYMENT TAB:
    public ActionEvent fillInPaymentTable(){
        try {
            bills.removeAll(bills);
            Connection connection = DBHandler.getConnection();
                String query = "SELECT id, customer_id, event_name, ROUND (total_bill) as total_bill, payment_status FROM customer_decor where event_name = '" + allEventsComboBox.getSelectionModel().getSelectedItem() + "'";
                //PreparedStatement pr = connection.prepareStatement(Queries.showBillAdmin);
                PreparedStatement pr = connection.prepareStatement(query);
                ResultSet result = pr.executeQuery();
                while (result.next()) {
                    bills.add(new Bill(
                            result.getInt("id"),
                            result.getInt("customer_id"),
                            result.getString("event_name"),
                            result.getDouble("total_bill"),
                            result.getBoolean("payment_status")
                    ));
                }
                pr.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        billIdColumn.setCellValueFactory(new PropertyValueFactory<>("billId"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        eventNameColumn3.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalBill"));
        paymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        billTable.setItems(bills);
        billTable.setEditable(false);
        return null;
    }
    public void showTotalSum() {
        Connection connection = DBHandler.getConnection();
        if (allEventsComboBox.getValue() == null) {
            showAlert("Success", "Please select your event from the list", Alert.AlertType.INFORMATION);
        } else {
            try {
                String sql = "SELECT ROUND(SUM(total_bill)) as total_bill FROM customer_decor WHERE event_name = '" + allEventsComboBox.getSelectionModel().getSelectedItem() + "'";
                PreparedStatement pr = connection.prepareStatement(sql);
                ResultSet result = pr.executeQuery();
                //DecimalFormat decimalFormat = new DecimalFormat("0.00");
                if (result.next()) {
                   // String totalPay = decimalFormat.format(result.getDouble("total_bill"));
                    Double totalPay = result.getDouble("total_bill");
                    totalSum.setText(String.valueOf(totalPay));
                }
            } catch (SQLException e) {
                showAlert("Error", "Total sum not found ", Alert.AlertType.INFORMATION);
                e.printStackTrace();
            }
        }
        totalSum.setEditable(false);
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
        eventListTable.setEditable(false);
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

    //CUSTOMER LIST TAB:
    public void fillInCustomerListTable(){
        customerId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("userFullName"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerListTable.setItems(users);
        customerListTable.setEditable(false);
    }

    public void handleLogOutButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void handleCallButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "callBack");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    //DECOR LIST TAB buttons - add, update, delete
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

    //EVENT LIST TAB buttons - add, update, delete
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

    public void handleAllEventsComboBox(ActionEvent actionEvent) {
        if (allEventsComboBox.getValue() == null) {
            showAlert("Success", "Please select your event from the list", Alert.AlertType.INFORMATION);
        } else {
            fillInPaymentTable();
            showTotalSum();
        }
    }
    private void fillInComboBox() {
        allEventsComboBox.setItems(events);
    }

}
