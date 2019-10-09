package seng202.group5.gui.invoice;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.gui.GeneralController;

import java.util.ArrayList;

public class PaymentSuccessController extends GeneralController {

    private ArrayList<Money> change;

    @FXML
    private Label totalChangeLabel;

    @FXML
    private Text paymentText;

    @FXML
    private Label changeLabel;

    @FXML
    private Button okButton;

    @Override
    public void pseudoInitialize() {
        paymentText.setFill(Color.GREEN);

        // Getting the total change
        Money totalChange = Money.parse("NZD 0");
        for (Money money : change) {
            totalChange.plus(money);
        }
        totalChangeLabel.setText("$" + totalChange.toString().replaceAll("[^\\d.]", ""));

        showChange();
    }

    public void showChange() {
        StringBuilder display = new StringBuilder();
        Money totalChange = Money.parse("NZD 0.00");
        for (Money money : change) {
            display.append(money).append("\n");
            totalChange = totalChange.plus(money);
        }
        changeLabel.setText(display.toString());
    }

    public void close() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void setChange(ArrayList<Money> tempChange) { change = tempChange ;}

}
