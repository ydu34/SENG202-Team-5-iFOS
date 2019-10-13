package seng202.group5.gui.invoice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Customer;
import seng202.group5.information.Customers;

import java.util.ArrayList;
import java.util.Collection;

public class ExistingCustomerController extends GeneralController {

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> rowID;

    @FXML
    private TableColumn<Customer, String> rowName;

    @FXML
    private TableColumn<Customer, String> rowPhoneNumber;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField idField;

    private Customers customers;

    @Override
    public void pseudoInitialize() {
        // Listeners for the TextFields to stop incorrect characters
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([\\-' ]*\\w*)*")) {
                nameField.setText(oldValue);
            }
            updateVisibleCustomers();
        });
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,15}")) {
                phoneNumberField.setText(oldValue);
            }
            updateVisibleCustomers();
        });
        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,15}")) {
                idField.setText(oldValue);
            }
            updateVisibleCustomers();
        });

        // Populating the TableView
        ObservableList<Customer> customersList = FXCollections.observableArrayList(
                customers.getCustomerList());

        rowID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        rowName.setCellValueFactory(new PropertyValueFactory<>("name"));
        rowPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        customerTable.setItems(customersList);
    }

    @FXML
    public void selectCustomer() {
        try {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();

            getAppEnvironment().getOrderManager().getOrder().setCurrentCustomer(customer);
            close();
        } catch (NoOrderException ignored) { }
    }

    /**
     * Updates the visible customers via TextFields that are about the Name, Phone number or ID.
     */
    private void updateVisibleCustomers() {
        // Clear the table
        customerTable.getItems().clear();

        // Collect all customers
        Collection<Customer> customersList = customers.getCustomerList();

        // Collect each search element
        String searchName = nameField.getText().toLowerCase();
        String searchPhone = phoneNumberField.getText();
        String searchID = idField.getText().toLowerCase();

        // Match the fields with customers
        for (Customer customer : customersList) {
            if (customer.getName().toLowerCase().matches(".*" + searchName + ".*") &&
            customer.getPhoneNumber().matches(".*" + searchPhone + ".*") &&
            customer.getID().toLowerCase().matches(".*" + searchID + ".*")) {
                customerTable.getItems().add(customer);
            }
        }
    }

    /**
     * A method to close the current screen.
     */
    @FXML
    private void close() {
        Stage stage = (Stage) customerTable.getScene().getWindow();
        stage.close();
    }

    public void setCustomers(Customers newCustomers) { customers = newCustomers; }
}
