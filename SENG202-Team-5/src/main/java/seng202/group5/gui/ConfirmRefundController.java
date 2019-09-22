package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.AppEnvironment;
import seng202.group5.Order;

public class ConfirmRefundController {

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private Label infoLabel;

    private HistoryController source;
    private Order order;
    private Button button;
    private AppEnvironment appEnvironment;

    public void setSource(HistoryController controller) {
        source = controller;
    }

    public void setText(Order order) {
        infoLabel.setText(String.format("Are you sure you want to refund Order %s?", order.getID()));
        this.order = order;
    }

    @FXML
    public void confirmOrder(javafx.event.ActionEvent event) {
        StringBuilder builder = new StringBuilder();
        Money moneySum = Money.parse("NZD 0.00");
        for (Money coin : source.confirmOrder(order.getID())) {
            System.out.println(coin.toString());
            moneySum = moneySum.plus(coin);
            builder.append(coin.toString());
            builder.append("\n");
        }
        if (moneySum.isLessThan(order.getTotalCost())) {
            builder.append("Not enough coins in the float to fully refund");
        }
        infoLabel.setText(builder.toString());
        yesButton.setVisible(false);
        noButton.setOnAction(this::closeScreen);
        GridPane.setColumnIndex(noButton, 0);
        GridPane.setColumnSpan(noButton, 2);
        GridPane.setHalignment(noButton, HPos.CENTER);
        noButton.setText("Continue");
    }

    @FXML
    public void rejectOrder(javafx.event.ActionEvent event) {
        closeScreen(event);
        button.setDisable(false);
    }

    public void closeScreen(ActionEvent event) {
        ((Stage) noButton.getScene().getWindow()).close();
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

}
