package seng202.group5.gui;

/**
 * author @ Shivin Gaba
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoryController {

    @FXML
    private Button launchInvoiceButton;

    @FXML
    private Button launchOrderButton;

    @FXML
    private Button launchAdminButton;

    @FXML
    private Button launchHistoryButton;

    /**
     * This method is called when any of the button (invoice, history,stock or admin are clicked on the order the screen)
     * @param event
     * @param scenePath
     */
    public void changeScreen(ActionEvent event, String scenePath){
        Parent sampleScene = null;
        try {
            sampleScene = FXMLLoader.load(getClass().getResource(scenePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldStage.setScene(new Scene(sampleScene, 821, 628));
    }

    /**
     * This method launches the invoice screen when clicked on the "Invoice" button
     * @param actionEvent
     */

    public void launchInvoiceScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/invoice.fxml");
    }


    /**
     * This method launches the order screen when clicked on the "Order" button
     * @param actionEvent
     */

    public void launchOrderScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/order.fxml");
    }



    /**
     * This method launches the admin screen when clicked on the "Admin" button
     * @param actionEvent
     */

    public void launchAdminScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/admin.fxml");
    }


    /**
     * This method launches the stock screen when clicked on the "History" button
     * @param actionEvent
     */


    public void launchStockScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/stock.fxml");
    }

}
