package seng202.group5.gui.invoice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Customer;
import seng202.group5.information.Customers;

/**
 * The Controller class for the New Customer screen within the Invoice screen
 * @author Michael Morgoun
 */
public class NewCustomerController extends GeneralController {

    /**
     * A warning label that shows when any fields aren't complete and the worker is trying to create a new member.
     */
    @FXML
    private Label warningLabel;

    /**
     * The field where the customer name is inputted.
     */
    @FXML
    private TextField nameField;

    /**
     * The field where the customer phone number is inputted.
     */
    @FXML
    private TextField phoneNumberField;

    /**
     * This button is for confirming the creation of the member.
     */
    @FXML
    private Button confirmButton;

    /**
     * The current customer that is being created.
     */
    private Customer customer;

    /**
     * All customers in the system.
     */
    private Customers customers;

    /**
     * Initialises the screen with all relevant information.
     */
    @Override
    public void pseudoInitialize() {
        // Creating a listener for the nameField
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([\\-' ]*\\w*)*")) {
                nameField.setText(oldValue);
            }
        });

        // Creating a listener for the phoneNumberField
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,15}")) {
                phoneNumberField.setText(oldValue);
            }
        });
    }

    /**
     * This method attempts to confirm the member.
     * It shows a warning message if not all the fields are filled in.
     */
    @FXML
    public void confirmCustomer() {
        // Neither TextFields can be blank
        if (nameField.getText().isBlank() || phoneNumberField.getText().isBlank()) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("Please Fill All Fields!");
        } else {
            // Create the new Customer
            customer = new Customer();
            customer.setName(nameField.getText());
            customer.setPhoneNumber(phoneNumberField.getText());
            customer.setPurchasePoints(customers.getCustomerSettings().getInitialPurchasePoints());
            customers.add(customer);

            // Close the current window
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Gets the new customer created.
     * @return The newly created customer.
     */
    public Customer getCustomer() { return customer; }

    /**
     * Sets the customers.
     * @param newCustomers The customers to be set.
     */
    public void setCustomers(Customers newCustomers) { customers = newCustomers; }

}
