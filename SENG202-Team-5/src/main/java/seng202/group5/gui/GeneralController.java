package seng202.group5.gui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import seng202.group5.AppEnvironment;

import java.awt.*;
import java.io.IOException;

/**
 * A controller class for other classes to implement
 *
 * @author Daniel Harris
 */
public class GeneralController {

    private static final double FADE_DURATION = 200; // in milliseconds

    /** The property that is shared between all the controllers to access the AppEnvironment */
    private AppEnvironment appEnvironment;

    /**
     * A function to smoothly transition from one scene to another
     *
     * @param oldStage the old stage which
     */
    public static void smoothTransition(Stage oldStage, Pane origin, Pane destination, EventHandler<ActionEvent> event) {
        // Creating a stack pane to transition from one screen to another
        StackPane fadePane = new StackPane();
        fadePane.setMaxHeight(Double.POSITIVE_INFINITY);
        fadePane.setMaxWidth(Double.POSITIVE_INFINITY);
        fadePane.setPrefHeight(destination.getPrefHeight());
        fadePane.setPrefWidth(destination.getPrefWidth());
        destination.setMouseTransparent(true);
        origin.setMouseTransparent(true);
        fadePane.getChildren().add(destination);
        fadePane.getChildren().add(origin);
        FadeTransition transition = new FadeTransition(Duration.millis(FADE_DURATION), origin);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);

        oldStage.getScene().setRoot(fadePane);
        transition.play();
        transition.setOnFinished((actionEvent) -> {
            fadePane.getChildren().remove(destination);
            destination.setMouseTransparent(false);
            event.handle(actionEvent);
        });
    }

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
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            FXMLLoader sampleLoader = new FXMLLoader(getClass().getResource(scenePath));
            sampleScene = sampleLoader.load();
            controller = sampleLoader.getController();
            controller.setAppEnvironment(appEnvironment);
            controller.pseudoInitialize();

            double prevHeight = ((Node) event.getSource()).getScene().getHeight();
            double prevWidth = ((Node) event.getSource()).getScene().getWidth();

            Parent finalSampleScene = sampleScene;
            smoothTransition(oldStage, (Pane) oldStage.getScene().getRoot(), (Pane) sampleScene, (actionEvent) -> {
                oldStage.getScene().setRoot(finalSampleScene);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            appEnvironment.getDatabase().autosave();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    @FXML
    private void launchPasswordScreen(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/password.fxml"));
            Parent root = loader.load();

            passwordController controller = loader.getController();
            controller.setSource(this);
            controller.setEvent(event);


            Stage stage = new Stage();
            stage.setTitle("Enter the password");
            stage.setScene(new Scene(root, 601, 432));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
