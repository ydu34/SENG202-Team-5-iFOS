package seng202.group5.gui.invoice;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Customer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Controller for the screen after payment is accepted. This screen shows the change quantities for the worker
 * to give to the customer.
 * @author Michael Morgoun
 */
public class PaymentSuccessController extends GeneralController {

    /**
     * An ArrayList which is of all the change that must be given to the customer.
     */
    private ArrayList<Money> change;

    /**
     * The label which shows the total amount of change to give back.
     */
    @FXML
    private Label totalChangeLabel;

    /**
     * Text which states "Payment Success!".
     */
    @FXML
    private Text paymentText;

    /**
     * The label where all the change will be shown.
     */
    @FXML
    private Text changeLabel;

    /**
     * A button for closing the screen and continuing.
     */
    @FXML
    private Button okButton;

    /**
     * Initialises the screen with all relevant information.
     */
    @Override
    public void pseudoInitialize() {
        paymentText.setFill(Color.GREEN);

        // Getting the total change
        Money totalChange = Money.parse("NZD 0");
        for (Money money : change) {
            totalChange = totalChange.plus(money);
        }
        totalChangeLabel.setText("$" + totalChange.toString().replaceAll("[^\\d.]", ""));

        showChange();
    }

    /**
     * A method which creates a string that shows all the change denominations and their quantities.
     */
    public void showChange() {
        // Initialising local variables
        StringBuilder display = new StringBuilder();
        HashMap<String, Integer> changeSet = new HashMap<>();

        // Adding the money from the array to a HashMap containing the quantity of each denomination
        for (Money money : change) {
            // The Key for the HashMap
            String key = "$" + money.toString().replaceAll("[^\\d.]", "");

            if (changeSet.containsKey(key)) {
                changeSet.replace(key, changeSet.get(key) + 1);
            } else {
                changeSet.put(key, 1);
            }
        }

        // For each object, append them to the display.
        for (Object key : changeSet.keySet().toArray()) {
            display.append(key);
            display.append(": ");
            display.append(changeSet.get(key));
            display.append("\n");
        }
        changeLabel.setText(display.toString());
    }

    /**
     * Closes the screen.
     */
    public void close() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets the change to give back to the customer.
     * @param tempChange The change to be given.
     */
    public void setChange(ArrayList<Money> tempChange) { change = tempChange ;}

}
