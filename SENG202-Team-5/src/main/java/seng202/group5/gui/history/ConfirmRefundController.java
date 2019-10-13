package seng202.group5.gui.history;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.AppEnvironment;
import seng202.group5.gui.GeneralController;
import seng202.group5.logic.Order;

import java.util.*;

/**
 * A controller for confirming if the user wants to refund an order
 *
 * @author Daniel Harris
 */
public class ConfirmRefundController extends GeneralController {

    /**
     * A button to confirm the refund
     */
    @FXML
    private Button yesButton;

    /**
     * A button to return to the history screen
     */
    @FXML
    private Button returnButton;

    @FXML
    private Label infoLabel;

    /**
     * The order to refund
     */
    private Order order;
    /**
     * The refund button of the order
     */
    private Button button;

    private boolean isRefunded = false;

    public void onClose() {
        if (!isRefunded) {
            button.setDisable(false);
        }
    }

    /**
     * Sets the order to refund, and sets the text on the info label
     * This assumes that the user has not already chosen to refund the order
     *
     * @param order the order to refund
     */
    public void setOrder(Order order) {
        infoLabel.setText(String.format("Are you sure you want to refund Order %s?", order.getId()));
        this.order = order;
    }

    /**
     * Confirms the refund of the order
     */
    @FXML
    public void confirmRefund() {
        // Showing what coins to return to the customer
        ArrayList<Money> refundCoins = getAppEnvironment().getFinance().refund(order.getId());
        StringBuilder builder = new StringBuilder("Return the following cash:\n");
        Money moneySum = Money.parse("NZD 0.00");
        HashMap<Money, Integer> moneyCounts = new HashMap<>();
        for (Money coin : refundCoins) {
            moneySum = moneySum.plus(coin);
            moneyCounts.put(coin, moneyCounts.getOrDefault(coin, 0) + 1);
        }
        ArrayList<Map.Entry<Money, Integer>> moneyCountEntries = new ArrayList<>(moneyCounts.entrySet());
        moneyCountEntries.sort((a, b) -> (-a.getKey().compareTo(b.getKey())));
        for (Map.Entry<Money, Integer> coinEntry : moneyCountEntries) {
            builder.append(coinEntry.getKey().toString());
            builder.append(" x ");
            builder.append(coinEntry.getValue().toString());
            builder.append("\n");
        }
        builder.delete(builder.length() - 1, builder.length());
        if (moneySum.isLessThan(order.getTotalCost())) {
            infoLabel.setText("Not enough coins in the float to fully refund");
            returnButton.setOnAction(this::rejectOrder);
        } else {
            infoLabel.setText(builder.toString());
            returnButton.setOnAction(this::closeScreen);
            isRefunded = true;
        }

        yesButton.setVisible(false);
        // Changing the return button so it does not tell the history that this order was not refunded
        GridPane.setColumnIndex(returnButton, 0);
        GridPane.setColumnSpan(returnButton, 2);
        GridPane.setHalignment(returnButton, HPos.CENTER);
        returnButton.setText("Continue");
        returnButton.getScene().getWindow().setHeight(returnButton.getScene().getWindow().getHeight() + infoLabel.getHeight());
    }

    /**
     * Cancels the request to refund the order and closes the screen
     *
     * @param event an event that caused this to happen
     */
    @FXML
    public void rejectOrder(javafx.event.ActionEvent event) {
        closeScreen(event);
        button.setDisable(false);
    }

    /**
     * Closes this screen
     * @param event an event that caused this to happen
     */
    private void closeScreen(ActionEvent event) {
        ((Stage) returnButton.getScene().getWindow()).close();
    }

    /**
     * Sets the refund button that can be re-enabled if the refund is cancelled
     * @param button the refund button of the order
     */
    public void setButton(Button button) {
        this.button = button;
    }

}
