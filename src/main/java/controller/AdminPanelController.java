package controller;
import decor.Decor;
import decor.DecorDBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPanelController extends ViewController implements Initializable {

    DecorDBService decorDBService = new DecorDBService();
    Decor decor;

    @FXML
    private DatePicker calendar;
    @FXML
    private TextField decorPriceField;
    @FXML
    private ComboBox<String> decorComboBox;
    @FXML
    private TableColumn<Decor, String> decorNameColumn;
    @FXML
    private TableColumn<Decor, String> decorStatusColumn;
    @FXML
    private TextField dateFormat;
    @FXML
    private TableColumn<Decor, Integer> decorIdColumn;
    @FXML
    private Button okButton;
    @FXML
    private TableColumn<Decor, Integer> decorQwtColumn;
    @FXML
    private TableColumn<Decor, Double> decorVatColumn;
    @FXML
    private TextField decorNameField;
    @FXML
    private ComboBox<String> decorStatusComboBox;
    @FXML
    private Button backButton;
    @FXML
    private TextField decorQwtField;
    @FXML
    private TableColumn<Decor, Double> decorPriceColumn;
    @FXML
    private Button selectButton;
    @FXML
    private TableView<Decor> decorTable;
    @FXML
    private TextField searchTextField;
    @FXML
    private TextField decorIdField;


    ObservableList<String> decorStatusList = FXCollections.observableArrayList("AVAILABLE", "Not Available");
    ObservableList<String> decorOptionsList = FXCollections.observableArrayList("Add", "Delete", "Update");

    List<Decor> decorList = new ArrayList<>(decorDBService.showAllDecorAdmin());
    ObservableList<Decor> decors = FXCollections.observableArrayList(decorList);
    FilteredList<Decor> filteredDecors = new FilteredList<>(decors, p -> true);


    public AdminPanelController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpComboBoxes();
        fillInDecorTable();
        filterTable();
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


    public void handleOkButton(ActionEvent actionEvent) {
    }

    public void handleDecorComboBox(ActionEvent actionEvent) {
    }

    public void handleStatusComboBox(ActionEvent actionEvent) {
    }

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

    public void setUpComboBoxes(){
        decorStatusComboBox.setItems(decorStatusList);
        decorStatusComboBox.setValue("AVAILABLE");
        decorComboBox.setItems(decorOptionsList);
        decorComboBox.setValue("Add");
    }
}
