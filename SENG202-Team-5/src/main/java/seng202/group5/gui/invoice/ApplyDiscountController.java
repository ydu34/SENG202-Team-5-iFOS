package seng202.group5.gui.invoice;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Customer;

public class ApplyDiscountController extends GeneralController {

    @FXML
    private Label pointsLabel;

    @FXML
    private TextField pointField;

    @FXML
    private Label calculatedLabel;

    private Money moneySaved;

    private Money maxPrice;

    private Customer customer;

    @Override
    public void pseudoInitialize() {
        // Listener for the points textfield
        pointField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,6}")) {
                pointField.setText(oldValue);
            }
            calculateSavings();
        });

        pointsLabel.setText("Current Number of Points: " + customer.getPurchasePoints());
    }

    /**
     * Add the value in the pointField by 1.
     */
    @FXML
    public void add() {
        int points = Integer.parseInt(pointField.getText());
        if (points != customer.getPurchasePoints()) {
            pointField.setText(String.valueOf(points+1));
        }
    }

    /**
     * Minus the value in the pointField by 1.
     */
    @FXML
    public void minus() {
        int points = Integer.parseInt(pointField.getText());
        if (points != 0) {
            pointField.setText(String.valueOf(points-1));
        }
    }

    /**
     * Changes the textfield to add all available points.
     */
    @FXML
    public void all() {
        pointField.setText(String.valueOf(customer.getPurchasePoints()));
    }

    /**
     * Calculates how much money you're saving by using however many points are selected.
     */
    public void calculateSavings() {
        Money tempMoneySaved = customer.getPointValue().multipliedBy(Integer.parseInt(pointField.getText()));
        String savedString = tempMoneySaved.toString().replaceAll("[^\\d.]", "");
        calculatedLabel.setText("You save $" + savedString + " by using " + pointField.getText() + " points");
    }

    /**
     * This method is called when the apply button is hit. Which applies the discount and closes the window.
     * Unless the amount that is being discounted is more than the cost of the order. The it issues a warning.
     */
    @FXML
    public void apply() {
        Money tempMoneySaved = customer.getPointValue().multipliedBy(Integer.parseInt(pointField.getText()));
        if (tempMoneySaved.isGreaterThan(maxPrice)) {
            calculatedLabel.setTextFill(Color.RED);
            calculatedLabel.setText("Discount is more than price of Order!");
        } else {
            try {
                customer.discount(Integer.parseInt(pointField.getText()), getAppEnvironment().getOrderManager().getOrder().getTotalCost());

                // Set the moneySaved
                moneySaved = customer.getPointValue().multipliedBy(Integer.parseInt(pointField.getText()));

                // Close the window
                Stage stage = (Stage) calculatedLabel.getScene().getWindow();
                stage.close();
            } catch (NoOrderException ignored) {}
        }
    }

    /**
     * Sets the customer.
     * @param newCustomer The new customer.
     */
    public void setCustomer(Customer newCustomer) {
        customer = newCustomer;
    }

    /**
     * Sets the max price of the order to stop the discount from going into the negatives.
     * @param tempMaxPrice The max price of the order
     */
    public void setMaxPrice(Money tempMaxPrice) { maxPrice = tempMaxPrice; }

    /**
     * Returns the money saved from the discount.
     * @return The money saved.
     */
    public Money getMoneySaved() { return moneySaved; }

    /**
     * Gets the points used in the discount.
     * @return The amount of points used.
     */
    public int getPoints() { return Integer.parseInt(pointField.getText()); }
}
