package seng202.group5.gui.history;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import seng202.group5.logic.Order;
import seng202.group5.gui.AddExtraIngredientController;
import seng202.group5.gui.GeneralController;
import seng202.group5.gui.OrderController;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.logic.Stock;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * A controller for detailing a past order specifically for the history
 *
 * @author Daniel Harris
 */
public class AddPastOrderController extends OrderController {

    /**
     * The order that is being specified
     */
    private Order order;

    /**
     * A picker for the date when the order was made
     */
    private JFXDatePicker datePicker;

    /**
     * A picker for the time at which the order was made
     */
    private JFXTextField timePicker;

    /**
     * A button to confirm the new past order
     */
    private JFXButton confirmButton;

    /**
     * The grid pane on the order screen that is used for the nodes added to the order screen
     */
    @FXML
    private GridPane bottomRightGridPane;

    @FXML
    private Button launchOrderButton;

    /**
     * A method for switching to this screen
     *
     * @param event  an event that caused this to happen
     * @param caller the controller that called this function
     * @return the new AddPastOrderController created
     */
    public static AddPastOrderController changeToPastOrderScreen(ActionEvent event, GeneralController caller) {
        Parent sampleScene;
        AddPastOrderController controller = null;
        try {
            FXMLLoader sampleLoader = new FXMLLoader(caller.getClass().getResource("/gui/order.fxml"));
            sampleLoader.setControllerFactory(aClass -> new AddPastOrderController());
            sampleScene = sampleLoader.load();
            controller = sampleLoader.getController();
            controller.setAppEnvironment(caller.getAppEnvironment());
            controller.pseudoInitialize();

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent finalSampleScene = sampleScene;
            smoothTransition(oldStage, (Pane) oldStage.getScene().getRoot(), (Pane) sampleScene, (actionEvent) ->
                    oldStage.getScene().setRoot(finalSampleScene));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    /**
     * Sets the constraints for a node
     *
     * @param node the node to set the constraints for
     */
    private void setNodeConstraints(Region node) {
        GridPane.setHgrow(node, Priority.ALWAYS);
        GridPane.setVgrow(node, Priority.ALWAYS);
        node.setMinHeight(Region.USE_PREF_SIZE);
        node.setPrefHeight(40);
        node.setMaxHeight(Region.USE_PREF_SIZE);
        node.setMaxWidth(300); // Max width of box
    }

    /**
     * An initializer for this controller
     */
    @Override
    public void pseudoInitialize() {
        super.pseudoInitialize();

        // Creating the date picker
        datePicker = new JFXDatePicker();
        setNodeConstraints(datePicker);
        datePicker.setValue(LocalDate.now());
        // This sets the factory that creates each cell in the calendar
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Disable dates that are in the future
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
        // This sets what converts typed dates into actual dates
        datePicker.setConverter(new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String input) {
                LocalDate date = super.fromString(input);
                LocalDate today = LocalDate.now();
                if (date.compareTo(today) > 0) {
                    date = today;
                }
                return date;
            }
        });

        // Creating the time picker
        timePicker = new JFXTextField();
        setNodeConstraints(timePicker);
        // This formats the input into a string that matches something like 2:45 am
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        timePicker.setTextFormatter(new TextFormatter<>(new StringConverter<>() {
            @Override
            public String toString(TemporalAccessor temporalAccessor) {
                return formatter.format(temporalAccessor);
            }

            @Override
            public TemporalAccessor fromString(String s) {
                return formatter.parse(s);
            }
        }, formatter.parse(formatter.format(LocalTime.now()))));

        // Creating the button to confirm the new order
        confirmButton = new JFXButton("Confirm");
        confirmButton.setOnAction((ActionEvent event) -> sendPastOrderToHistory(event, formatter));
        confirmButton.setDisable(true);
        setNodeConstraints(confirmButton);

        // Creating the button to cancel the order
        Button cancelButton = new JFXButton("Cancel");
        cancelButton.setOnAction(this::returnToHistory);
        setNodeConstraints(cancelButton);

        // Adding the nodes into the grid pane
        bottomRightGridPane.addRow(3);
        bottomRightGridPane.add(datePicker, 0, 3);
        bottomRightGridPane.add(timePicker, 1, 3);
        bottomRightGridPane.addRow(4);
        bottomRightGridPane.add(cancelButton, 0, 4);
        bottomRightGridPane.add(confirmButton, 1, 4);

        // Setting the row constraints of the new rows
        RowConstraints row3 = new RowConstraints();
        RowConstraints row4 = new RowConstraints();
        row3.setVgrow(Priority.NEVER);
        row3.setMinHeight(Region.USE_PREF_SIZE);
        row3.setPrefHeight(40);
        row3.setMaxHeight(Region.USE_PREF_SIZE);
        row4.setVgrow(Priority.NEVER);
        row4.setMinHeight(Region.USE_PREF_SIZE);
        row4.setPrefHeight(40);
        row4.setMaxHeight(Region.USE_PREF_SIZE);
        bottomRightGridPane.getRowConstraints().add(row3);
        bottomRightGridPane.getRowConstraints().add(row4);

        launchOrderButton.setDisable(false);
        resetOrder();
    }

    /**
     * Creates a new order
     */
    public void resetOrder() {
        // Creating a new stock that has practically infinite ingredients
        Stock maxStock = new Stock();
        for (Ingredient ingr : getAppEnvironment().getStock().getIngredients().values()) {
            maxStock.addNewIngredient(ingr, 9999999);
        }
        setOrder(new Order(maxStock));
    }

    /**
     * Adds the past order to the history
     *
     * @param event     an event that caused this to happen
     * @param formatter the formatter for the time picker
     */
    public void sendPastOrderToHistory(ActionEvent event, DateTimeFormatter formatter) {
        HistoryController controller = (HistoryController) changeScreen(event, "/gui/history.fxml");
        controller.addNewOrder(order, LocalDateTime.of(datePicker.getValue(),
                                                       LocalTime.from(formatter.parse(timePicker.getText()))));
    }

    /**
     * Returns to the history screen without adding the past order
     * @param event an event that caused this to happen
     */
    public void returnToHistory(ActionEvent event) {
        changeScreen(event, "/gui/history.fxml");
    }

    /**
     * This method launches the screen for adding extra ingredients to the selected menu item and
     * passes the item and order from the current class to the controller
     *
     * @param event     an event that caused this to happen
     * @param scenePath the path to the screen to change to
     */
    @Override
    public void addExtraIngredientScreen(ActionEvent event, String scenePath) {
        Parent sampleScene = null;
        AddExtraIngredientController controller;
        try {
            FXMLLoader sampleLoader = new FXMLLoader(getClass().getResource(scenePath));
            // Need to create a new class here so this screen comes back with the right controller
            sampleScene = sampleLoader.load();
            controller = sampleLoader.getController();
            controller.setOpenMode("PastOrder");
            controller.setAppEnvironment(getAppEnvironment());
            controller.pseudoInitialize();
            controller.setMenuItem(getSelectedItem());
            controller.setCurrentOrder(order);
            controller.updateStockRecipeMode();
            controller.initializeTable();
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double prevHeight = ((Node) event.getSource()).getScene().getHeight();
            double prevWidth = ((Node) event.getSource()).getScene().getWidth();
            oldStage.setScene(new Scene(sampleScene, prevWidth, prevHeight));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the screen to edit the ingredient counts of a menu item
     * @param actionEvent an event that caused this to happen
     */
    @Override
    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        addExtraIngredientScreen(actionEvent, "/gui/addExtraIngredient.fxml");
    }

    /**
     * Adds the selected item to the order
     */
    @Override
    public void addItemToOrder() {
        super.addItemToOrder();
        confirmButton.setDisable(false);
    }

    public void setOrder(Order order) {
        super.setCurrentOrder(order);
        this.order = order;
        confirmButton.setDisable(order.getOrderItems().size() == 0);
    }

}
