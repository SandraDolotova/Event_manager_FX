package controller;
import db.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import packageTypes.PackagePrice;
import users.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CallBack extends ViewController implements Initializable {

    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, ComboBox> statusColumn;
    @FXML
    private TableColumn<PackagePrice, String> packagePriceColumn;
    @FXML
    private TableView<User> callBackTable;
    @FXML
    private TableColumn<User, Integer> phoneColumn;
    @FXML
    private ComboBox<String> callStatusComboBox;
    @FXML
    private TextField callBackIdField;

    @FXML
    void handleBack(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "admin");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    Connection connection = DBHandler.getConnection();
    ObservableList<User> calls = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInComboBox();
        fillInCallBackTable();
    }

    public void fillInComboBox() {
        callStatusComboBox.getItems().add("DONE");
        callStatusComboBox.getItems().add("NOT");
        callStatusComboBox.getItems().add("CALL AGAIN");
        callStatusComboBox.getItems().add("SMS");
    }

    public void fillInCallBackTable() {
        try {
            String sql = "SELECT id, user_full_name, phone, package_price, call_status FROM call_back";
            PreparedStatement pr = connection.prepareStatement(sql);
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                calls.add(new User(
                        result.getInt("id"),
                        result.getString("user_full_name"),
                        result.getInt("phone"),
                        result.getString("package_price"),
                        result.getString("call_status")));
            }
            pr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userFullName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        packagePriceColumn.setCellValueFactory(new PropertyValueFactory<>("packagePrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("callStatus"));
        callBackTable.setItems(calls);
        callBackTable.setEditable(false);
    }

    public void handleSelectCallButton(ActionEvent actionEvent) {
        if (callBackIdField.getText().isEmpty()) {
            showAlert("Insert ID", "Select id number from the given list", Alert.AlertType.INFORMATION);
        }else{
            try {
                String sql = "UPDATE call_back SET call_status = '" + callStatusComboBox.getSelectionModel().getSelectedItem() + "'" +
                        "WHERE id = '" + callBackIdField.getText() + "'";
                PreparedStatement pr = connection.prepareStatement(sql);
                pr.executeUpdate();
                pr.close();
                changeScene(actionEvent, "callBack");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


