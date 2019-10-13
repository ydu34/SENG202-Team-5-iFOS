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

    @FXML
    private Label warningLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button confirmButton;

    private Customer customer;

    private Customers customers;

    @Override
    public void pseudoInitialize() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([\\-' ]*\\w*)*")) {
                nameField.setText(oldValue);
            }
        });

        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,15}")) {
                phoneNumberField.setText(oldValue);
            }
        });
    }

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
            customers.add(customer);

            // Close the current window
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
    }

    public Customer getCustomer() { return customer; }

    public void setCustomers(Customers newCustomers) { customers = newCustomers; }

}
