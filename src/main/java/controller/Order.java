package controller;
import customerData.AppData;
import db.DBHandler;
import decor.Decor;
import events.Event;
import events.EventDBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;
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

public class Order extends ViewController implements Initializable {

    @FXML
    private TableColumn<Decor, String> decorNameColumn;
    @FXML
    private TableView<Decor> orderTable;
    @FXML
    private TableColumn<Decor, Integer> qwtColumn;
    @FXML
    private TableColumn<Decor, Double> totalPriceColumn;
    @FXML
    private ComboBox<Event> eventNameComboBox;
    @FXML
    private TableColumn<Decor, Double> totalColumn;
    @FXML
    private TextField customerID;
    @FXML
    private TableColumn<Decor, Double> decorPriceColumn;
    @FXML
    private TableColumn<Decor, Integer> idColumn;
    @FXML
    private TableColumn<Decor, Double> transportColumn;
    @FXML
    private TextField customerFullNameField;


    Connection connection = DBHandler.getConnection();
    UserDBService userDBService = new UserDBService();
    EventDBService eventDBService = new EventDBService();
    User user = userDBService.showLoggedInCustomer(AppData.getInstance().getLoggedInUserId());
    List<Event> customerEventList = new ArrayList<>(eventDBService.showCustomerEvents());
    ObservableList<Event> customerEvents = FXCollections.observableArrayList(customerEventList);

    ObservableList<Decor> orders = FXCollections.observableArrayList();

    public Order() throws Exception {
    }

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showLoggedInCustomerDetails();
        fillInComboBox();
        handleComboBox(fillInOrderTable());

    }
    private void fillInComboBox() {
        eventNameComboBox.setItems(customerEvents);
    }
    private void showLoggedInCustomerDetails() {
        customerID.setEditable(false);
        customerFullNameField.setEditable(false);
        customerID.setText(String.valueOf(user.getUserId()));
        customerFullNameField.setText(user.getUserFullName());
    }
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "customer");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public ActionEvent fillInOrderTable(){
        if(eventNameComboBox.getValue() == null){
            showAlert("Error", "Please select your event from the list", Alert.AlertType.ERROR);
        }else{
        try {
            orders.removeAll(orders);
            String sql = "SELECT customer_decor_id, customer_decor_name, customer_decor_qwnt, customer_decor_price_vat, total_decor_price, transportation_costs, total_bill " +
                    "FROM customer_decor where customer_id ='" + user.getUserId() + "' " +
                    "&& event_name = '" + eventNameComboBox.getSelectionModel().getSelectedItem()+ "'";
            PreparedStatement pr = connection.prepareStatement(sql);
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                orders.add(new Decor(
                        result.getString("customer_decor_id"),
                        result.getString("customer_decor_name"),
                        result.getInt("customer_decor_qwnt"),
                        result.getDouble("customer_decor_price_vat"),
                        result.getDouble("total_decor_price"),
                        result.getDouble("transportation_costs"),
                        result.getDouble("total_bill")));}
            pr.close();
            // result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        decorNameColumn.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        qwtColumn.setCellValueFactory(new PropertyValueFactory<>("decorQwt"));
        decorPriceColumn.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalDecorPrice"));
        transportColumn.setCellValueFactory(new PropertyValueFactory<>("transportCosts"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalBill"));
        orderTable.setItems(null);
        orderTable.setItems(orders);
        orderTable.setEditable(false);
        return null;
    }

    public void handleComboBox(ActionEvent actionEvent) throws IOException {
        fillInOrderTable();
    }
}


