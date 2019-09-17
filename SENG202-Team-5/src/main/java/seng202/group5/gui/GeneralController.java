package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import seng202.group5.AppEnvironment;

import java.io.IOException;

public class GeneralController {

    private AppEnvironment appEnvironment;

    @FXML
    private Button launchOrderScreenButton;

    @FXML
    private Button launchInvoiceScreenButton;

    @FXML
    private Button launchStockScreenButton;

    @FXML
    private Button launchAdminScreenButton;

    @FXML
    private Button launchHistoryScreenButton;

    /**
     * A function which can be overwritten to initialize a controller with the
     * AppEnvironment set, which is not available with the regular initialize method
     */
    public void pseudoInitialize() {
        return;
    }

    /**
     * This method is called when the screen needs to change to a different one
     * @param event
     * @param scenePath
     */
    public void changeScreen(ActionEvent event, String scenePath){
        Parent sampleScene = null;
        try {
            FXMLLoader sampleLoader = new FXMLLoader(getClass().getResource(scenePath));
            sampleScene = sampleLoader.load();
            GeneralController controller = sampleLoader.getController();
            controller.setAppEnvironment(appEnvironment);
            controller.pseudoInitialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldStage.setScene(new Scene(sampleScene, 821, 628));
    }

    /**
     * This method launches the order screen when clicked on the "Order" button
     * @param actionEvent
     */
    public void launchOrderScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/order.fxml");
    }

    /**
     * This method launches the invoice screen when clicked on the "Invoice" button
     * @param actionEvent
     */
    public void launchInvoiceScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/invoice.fxml");
    }

    /**
     * This method launches the stock screen when clicked on the "History" button
     * @param actionEvent
     */
    public void launchStockScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/stock.fxml");
    }

    /**
     * This method launches the admin screen when clicked on the "Admin" button
     * @param actionEvent
     */
    public void launchAdminScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/admin.fxml");
    }

    /**
     * This method launches the order screen when clicked on the "Order" button
     * @param actionEvent
     */
    public void launchHistoryScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/history.fxml");
    }

    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

}
