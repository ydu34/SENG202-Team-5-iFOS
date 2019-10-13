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
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Customer;
import seng202.group5.information.Customers;

import java.util.Collection;

/**
 * The Controller for the Existing Member screen off of the invoice screen.
 * @author Michael Morgoun
 */
public class ExistingCustomerController extends GeneralController {

    /**
     * The customerTable where you view all customers.
     */
    @FXML
    private TableView<Customer> customerTable;

    /**
     * The column for IDs.
     */
    @FXML
    private TableColumn<Customer, String> rowID;

    /**
     * The column for the names.
     */
    @FXML
    private TableColumn<Customer, String> rowName;

    /**
     * The column for the phone numbers.
     */
    @FXML
    private TableColumn<Customer, String> rowPhoneNumber;

    /**
     * The search bar for the name.
     */
    @FXML
    private TextField nameField;

    /**
     * The search bar for the phone number.
     */
    @FXML
    private TextField phoneNumberField;

    /**
     * The search bar for the ID.
     */
    @FXML
    private TextField idField;

    /**
     * All customers in the system.
     */
    private Customers customers;

    /**
     * Initialises the screen with all necessary information and listeners.
     */
    @Override
    public void pseudoInitialize() {
        // Initialise the context menu for right clicking on existing members
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

    /**
     * Creates the Context Menu when right clicking an item, or selects the item if double clicked.
     */
    private void createContextMenu() {
        ContextMenu deleteMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete Member");
        deleteMenu.getItems().add(deleteMenuItem);

        customerTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
            // Mouse Listeners for a double click, a right click and nothing
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    deleteMenu.show(customerTable, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2 && customerTable.getSelectionModel().getSelectedItem() != null) {
                    selectCustomer();
                } else {
                    deleteMenu.hide();
                }
            }
        });

        // Updates the visible customers if a member was deleted
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                customers.getCustomerList().remove(customerTable.getSelectionModel().getSelectedItem());
                updateVisibleCustomers();
            }
        });
    }

    /**
     * Selects the current customer that is selected.
     * Possible for it to be null.
     */
    @FXML
    public void selectCustomer() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        getAppEnvironment().getOrderManager().getOrder().setCurrentCustomer(customer);
        close();
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

    /**
     * Sets the customers.
     * @param newCustomers The new customers to be set.
     */
    public void setCustomers(Customers newCustomers) { customers = newCustomers; }
}
