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

    private ArrayList<Money> change;

    @FXML
    private Label totalChangeLabel;

    @FXML
    private Text paymentText;

    @FXML
    private Text changeLabel;

    @FXML
    private Button okButton;

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

        for (Object key : changeSet.keySet().toArray()) {
            display.append(key);
            display.append(": ");
            display.append(changeSet.get(key));
            display.append("\n");
        }
        System.out.println(display.toString());
        changeLabel.setText(display.toString());
    }

    public void close() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void setChange(ArrayList<Money> tempChange) { change = tempChange ;}

}
