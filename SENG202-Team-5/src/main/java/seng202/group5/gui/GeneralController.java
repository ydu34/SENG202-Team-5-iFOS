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

/**
 * A controller class for other classes to implement
 *
 * @author Daniel Harris
 */
public class GeneralController {

    /** The property that is shared between all the controllers to access the AppEnvironment */
    private AppEnvironment appEnvironment;

    /**
     * A function which can be overwritten to initialize a controller with the
     * AppEnvironment set, which is not available with the regular initialize method
     */
    public void pseudoInitialize() {
    }

    /**
     * This method is called when the screen needs to change to a different one
     * @param event an event that caused this to happen
     * @param scenePath the location of the fxml file of the new screen
     * @return the new controller for the new screen
     */
    public GeneralController changeScreen(ActionEvent event, String scenePath) {
        Parent sampleScene = null;
        GeneralController controller = null;
        try {
            FXMLLoader sampleLoader = new FXMLLoader(getClass().getResource(scenePath));
            sampleScene = sampleLoader.load();
            controller = sampleLoader.getController();
            controller.setAppEnvironment(appEnvironment);
            controller.pseudoInitialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        double prevHeight = ((Node) event.getSource()).getScene().getHeight();
        double prevWidth = ((Node) event.getSource()).getScene().getWidth();
        oldStage.setScene(new Scene(sampleScene, prevWidth, prevHeight));
        return controller;
    }

    /**
     * This method launches the order screen when clicked on the "Order" button
     * @param actionEvent an event that caused this to happen
     */
    public void launchOrderScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/order.fxml");
    }

    /**
     * This method launches the invoice screen when clicked on the "Invoice" button
     * @param actionEvent an event that caused this to happen
     */
    public void launchInvoiceScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/invoice.fxml");
    }

    /**
     * This method launches the stock screen when clicked on the "History" button
     * @param actionEvent an event that caused this to happen
     */
    public void launchStockScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/stock.fxml");
    }

    /**
     * This method launches the admin screen when clicked on the "Admin" button
     * @param actionEvent an event that caused this to happen
     */
    public void launchAdminScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/admin.fxml");
    }

    /**
     * This method launches the order screen when clicked on the "Order" button
     * @param actionEvent an event that caused this to happen
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
