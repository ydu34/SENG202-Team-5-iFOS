package seng202.group5.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmRefundController {

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private Label infoLabel;

    private HistoryController source;
    private String orderID;
    private Button button;

    public void setSource(HistoryController controller) {
        source = controller;
    }

    public void setText(String text) {
        infoLabel.setText(String.format("Are you sure you want to refund Order %s?", text));
        orderID = text;
    }

    @FXML
    public void confirmOrder(javafx.event.ActionEvent event) {
        Stage thing = (Stage) yesButton.getScene().getWindow();
        thing.close();
        source.confirmOrder(orderID);
    }

    @FXML
    public void rejectOrder(javafx.event.ActionEvent event) {
        Stage thing = (Stage) noButton.getScene().getWindow();
        thing.close();
        button.setDisable(false);
    }

    public void setButton(Button button) {
        this.button = button;
    }

}
