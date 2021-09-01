package controller;
import decor.Decor;
import decor.DecorDBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPanelController extends ViewController implements Initializable {

    //calendar sadaļai:
    public DatePicker calendar;
    public Button selectButton;
    public TextField dateFormat;
    public Button backButton;

    //decor list sadaļai:
    public TableView<Decor> decorTable;
    public TableColumn<Object, Object> decorIdColumn;
    public TableColumn<Object, Object> decorNameColumn;
    public TableColumn<Object, Object> decorQwtColumn;
    public TableColumn<Object, Object> decorPriceColumn;
    public TableColumn<Object, Object> decorVatColumn;
    public TableColumn<Object, Object> decorStatusColumn;
    public TextField searchTextField;

    //add decor sadaļai:
    public TextField decorName;
    public TextField decorQuantity;
    public TextField decorPrice;
    public TextField decorStatus;
    public Button addButton;

    //delete decor sadaļai:
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
    public Text deleteIdText;

    //update decor Price sadaļai:
    public TableView<Decor> updatePriceTableList;
    public Tab updateDecorPriceTab;
    public TextField updatePriceField;
    public Button updatePriceButton;
    public TableColumn<Object, Object> decorId1;
    public TableColumn<Object, Object> decorName1;
    public TableColumn<Object, Object> decorQwt1;
    public TableColumn<Object, Object> decorPrice1;
    public TableColumn<Object, Object> decorPriceVat1;
    public TableColumn<Object, Object> decorStatus1;
    public TextField newPriceField;

    //update decor quantity sadaļai:
    public TableView<Decor> updateDecorQtwTable;
    public Tab updateDecorQwtTab;
    public TextField updateQuantityField;
    public Button updateQuantityButton;
    public TableColumn<Object, Object> decorId2;
    public TableColumn<Object, Object> decorName2;
    public TableColumn<Object, Object> decorQwt2;
    public TableColumn<Object, Object> decorPrice2;
    public TableColumn<Object, Object> decorPriceVat2;
    public TableColumn<Object, Object> decorStatus2;
    public TextField newQuantityField;



    DecorDBService decorDBService = new DecorDBService();
    List<Decor> decorList = new ArrayList<>(decorDBService.showAllDecorAdmin());
    ObservableList<Decor> decors = FXCollections.observableArrayList(decorList);
    FilteredList<Decor> filteredDecors = new FilteredList<>(decors, p -> true);


    public AdminPanelController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillInDecorTable();
        fillInUpdateDecorPriceTable();
        fillInUpdateDecorQuantityTable();
        fillInDeleteDecorTable();
        filterTable();
    }

    //delete sadaļa
    public void fillInDeleteDecorTable(){
        decorIdColumn1.setCellValueFactory(new PropertyValueFactory<>("decorId"));
        decorNameColumn1.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        decorQwtColumn1.setCellValueFactory(new PropertyValueFactory<>("decorQwt"));
        decorPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("decorPrice"));
        decorVatColumn1.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        decorStatusColumn1.setCellValueFactory(new PropertyValueFactory<>("decorStatus"));
        decorListTable.setItems(decors);
    }
    //update price sadaļa
    public void fillInUpdateDecorPriceTable(){
        decorId1.setCellValueFactory(new PropertyValueFactory<>("decorId"));
        decorName1.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        decorQwt1.setCellValueFactory(new PropertyValueFactory<>("decorQwt"));
        decorPrice1.setCellValueFactory(new PropertyValueFactory<>("decorPrice"));
        decorPriceVat1.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        decorStatus1.setCellValueFactory(new PropertyValueFactory<>("decorStatus"));
        updatePriceTableList.setItems(decors);
    }
    //update quantity sadaļa
    public void fillInUpdateDecorQuantityTable(){
        decorId2.setCellValueFactory(new PropertyValueFactory<>("decorId"));
        decorName2.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        decorQwt2.setCellValueFactory(new PropertyValueFactory<>("decorQwt"));
        decorPrice2.setCellValueFactory(new PropertyValueFactory<>("decorPrice"));
        decorPriceVat2.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        decorStatus2.setCellValueFactory(new PropertyValueFactory<>("decorStatus"));
        updateDecorQtwTable.setItems(decors);
    }
    //decor list sadaļa
    public void fillInDecorTable() {
        decorIdColumn.setCellValueFactory(new PropertyValueFactory<>("decorId"));
        decorNameColumn.setCellValueFactory(new PropertyValueFactory<>("decorName"));
        decorQwtColumn.setCellValueFactory(new PropertyValueFactory<>("decorQwt"));
        decorPriceColumn.setCellValueFactory(new PropertyValueFactory<>("decorPrice"));
        decorVatColumn.setCellValueFactory(new PropertyValueFactory<>("decorPriceVAT"));
        decorStatusColumn.setCellValueFactory(new PropertyValueFactory<>("decorStatus"));
        decorTable.setItems(decors);
    }
    public void filterTable() {
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


    //decor lapā ok poga - uz action vajadzētu ievadītos datus aizsūtīt uz sql table decor_list
    public void handleOkButton(ActionEvent actionEvent) {
        showAlert("Success", "Decor added Successfully", Alert.AlertType.CONFIRMATION);
    }

    //kalendāra izvēles logs
    public void handleSelectButton(ActionEvent actionEvent) {
        dateFormat.appendText(calendar.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n");
    }

    public void handleBackButton(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "welcome");
        } catch (IOException e) {
            showAlert("Problem loading scene", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void handleDeleteButton(ActionEvent actionEvent) throws SQLException {
        decorDBService.deleteDecor(Integer.parseInt(deleteDecorIdField.getText()));
        showAlert("Success", "Decor deleted successfully", Alert.AlertType.CONFIRMATION);
    }

    public void handleAddButton(ActionEvent actionEvent) throws SQLException {
        decorDBService.insertNewDecor(
                decorName.getText(),
                Integer.parseInt(decorQuantity.getText()),
                Double.parseDouble(decorPrice.getText()),
                decorStatus.getText()
        );
        showAlert("Success", "Decor added successfully", Alert.AlertType.CONFIRMATION);
    }

    public void handleUpdatePriceButton(ActionEvent actionEvent) throws SQLException {
        decorDBService.updateDecorPrice(
                Integer.parseInt(updatePriceField.getText()),
                Double.parseDouble(newPriceField.getText())
        );
        showAlert("Success", "Decor price updated successfully", Alert.AlertType.CONFIRMATION);
    }

    public void handleUpdateQuantityButton(ActionEvent actionEvent) throws SQLException {
        decorDBService.updateDecorQuantity(
                Integer.parseInt(updatePriceField.getText()),
                Integer.parseInt(newQuantityField.getText())
        );
        showAlert("Success", "Decor quantity updated successfully", Alert.AlertType.CONFIRMATION);
    }
}
