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
import seng202.group5.logic.Order;

import java.util.ArrayList;

/**
 * A controller for confirming if the user wants to refund an order
 *
 * @author Daniel Harris
 */
public class ConfirmRefundController {

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

    private HistoryController source;
    /**
     * The order to refund
     */
    private Order order;
    /**
     * The refund button of the order
     */
    private Button button;
    private AppEnvironment appEnvironment;

    /**
     * Sets the controller that created the screen this controller is using
     *
     * @param controller the controller that created this
     */
    public void setSource(HistoryController controller) {
        source = controller;
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
     *
     * @param event an event that caused this to happen
     */
    @FXML
    public void confirmRefund(javafx.event.ActionEvent event) {
        // Showing what coins to return to the customer
        StringBuilder builder = new StringBuilder("Return the following cash:\n");
        Money moneySum = Money.parse("NZD 0.00");
        ArrayList<Money> test = source.confirmOrderRefund(order.getId());
        for (Money coin : test) {
            moneySum = moneySum.plus(coin);
            builder.append(coin.toString());
            builder.append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        if (moneySum.isLessThan(order.getTotalCost())) {
            builder.append("\n(Not enough coins in the float to fully refund)");
        }
        infoLabel.setText(builder.toString());
        //TODO screen is not resizing properly

        yesButton.setVisible(false);
        // Changing the return button so it does not tell the history that this order was not refunded
        returnButton.setOnAction(this::closeScreen);
        GridPane.setColumnIndex(returnButton, 0);
        GridPane.setColumnSpan(returnButton, 2);
        GridPane.setHalignment(returnButton, HPos.CENTER);
        returnButton.setText("Continue");
        returnButton.getScene().getWindow().sizeToScene();
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
    public void closeScreen(ActionEvent event) {
        ((Stage) returnButton.getScene().getWindow()).close();
    }

    /**
     * Sets the refund button that can be re-enabled if the refund is cancelled
     * @param button the refund button of the order
     */
    public void setButton(Button button) {
        this.button = button;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

}
