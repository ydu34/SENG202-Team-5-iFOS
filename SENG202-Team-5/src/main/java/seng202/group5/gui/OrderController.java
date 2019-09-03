package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderController {


    @FXML
    private Button launchInvoiceButton;

    @FXML
    private Button launchStockButton;

    @FXML
    private Button launchAdminButton;

    @FXML
    private Button launchHistoryButton;




    public void changeScreen(ActionEvent event, String scenePath){
        Parent invoiceScene = null;
        try {
            invoiceScene = FXMLLoader.load(getClass().getResource(scenePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldStage.setScene(new Scene(invoiceScene, 821, 628));

    }

    public void launchInvoiceScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/invoice.fxml");
    }

    public void launchStockScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/stock.fxml");
    }

    public void launchAdminScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/admin.fxml");
    }

    public void launchHistoryScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/history.fxml");
    }

}