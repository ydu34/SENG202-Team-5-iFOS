package seng202.group5.gui.invoice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
        // Initalise the context menu for right clicking on existing members
        createContextMenu();

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

    private void createContextMenu() {
        ContextMenu deleteMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete Member");
        deleteMenu.getItems().add(deleteMenuItem);

        customerTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    deleteMenu.show(customerTable, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                } else {
                    deleteMenu.hide();
                }
            }
        });

        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                customers.getCustomerList().remove(customerTable.getSelectionModel().getSelectedItem());
                updateVisibleCustomers();
            }
        });
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
