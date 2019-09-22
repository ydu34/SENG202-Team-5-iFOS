package seng202.group5.gui.history;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import seng202.group5.Order;
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

public class AddPastOrderController extends OrderController {

    private Order order;

    private DatePicker datePicker;

    private TextField timePicker;

    private Button confirmButton;

    @FXML
    private GridPane bottomRightGridPane;


    @FXML
    private Button launchOrderButton;

    public static AddPastOrderController changeToPastOrderScreen(ActionEvent event, GeneralController caller) {
        Parent sampleScene = null;
        AddPastOrderController controller = null;
        try {
            FXMLLoader sampleLoader = new FXMLLoader(caller.getClass().getResource("/gui/order.fxml"));
            sampleLoader.setControllerFactory(aClass -> new AddPastOrderController());
            sampleScene = sampleLoader.load();
            controller = sampleLoader.getController();
            sampleLoader.setController(controller);
            controller.setAppEnvironment(caller.getAppEnvironment());
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

    @Override
    public void pseudoInitialize() {
        super.pseudoInitialize();
        Stock maxStock = new Stock();
        for (Ingredient ingr : getAppEnvironment().getStock().getIngredients().values()) {
            maxStock.addNewIngredient(ingr, 9999999);
        }
        order = new Order(maxStock);
        setCurrentOrder(order);

        datePicker = new DatePicker();
        setNodeConstraints(datePicker);
        datePicker.setValue(LocalDate.now());
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
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

        timePicker = new TextField();
        setNodeConstraints(timePicker);
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

        confirmButton = new Button("Confirm");
        confirmButton.setOnAction((ActionEvent event) -> sendPastOrderToHistory(event, formatter));
        confirmButton.setDisable(true);
        setNodeConstraints(confirmButton);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(this::returnToHistory);
        setNodeConstraints(cancelButton);

        bottomRightGridPane.addRow(3);
        bottomRightGridPane.add(datePicker, 0, 3);
        bottomRightGridPane.add(timePicker, 1, 3);
        bottomRightGridPane.addRow(4);
        bottomRightGridPane.add(cancelButton, 0, 4);
        bottomRightGridPane.add(confirmButton, 1, 4);
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
    }

    public void sendPastOrderToHistory(ActionEvent event, DateTimeFormatter formatter) {
        order.setDateTimeProcessed(LocalDateTime.of(datePicker.getValue(),
                                                    LocalTime.from(formatter.parse(timePicker.getText()))));
        HistoryController controller = (HistoryController) changeScreen(event, "/gui/history.fxml");
        controller.addNewOrder(order);
    }

    public void returnToHistory(ActionEvent event) {
        changeScreen(event, "/gui/history.fxml");
    }

    public Order getOrder() {
        return order;
    }

    /**
     * This method launches the selection screen for the selected menu item and passes the recipe and object from the
     * from the current class to the the Selection controller class.
     *
     * @param event
     * @param scenePath
     */
    @Override
    public void addExtraIngredientScreen(ActionEvent event, String scenePath) {
        Parent sampleScene = null;
        AddExtraIngredientController controller = null;
        try {
            FXMLLoader sampleLoader = new FXMLLoader(getClass().getResource(scenePath));
            sampleLoader.setControllerFactory(aClass -> new AddExtraIngredientController() {
                @Override
                public void updateItemIngredients(ActionEvent actionEvent) {
                    AddPastOrderController controller = AddPastOrderController.changeToPastOrderScreen(actionEvent, this);
                    MenuItem selectedItem = getSelectedItem();
                    String itemID = selectedItem.getID();
                    MenuItem oldItem = getAppEnvironment().getMenuManager().getMenuItems().get(itemID);
                    if ((selectedItem.getRecipe().getIngredientsAmount() != oldItem.getRecipe().getIngredientsAmount())
                            && !selectedItem.isEdited()) {
                        selectedItem.setEdited(true);
                    } else {
                        selectedItem.setEdited(false);
                    }
                    controller.setMenuItem(selectedItem);
                }

                @Override
                public void revertScreen(ActionEvent event) {
                    AddPastOrderController controller = AddPastOrderController.changeToPastOrderScreen(event, this);
                    controller.setMenuItem(getOldItem());
                }
            });
            sampleScene = sampleLoader.load();
            controller = sampleLoader.getController();
            sampleLoader.setController(controller);
            controller.setAppEnvironment(getAppEnvironment());
            controller.pseudoInitialize();
            controller.setMenuItem(getSelectedItem());
            controller.setCurrentOrder(order);
            controller.updateStock();
            controller.initializeTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        double prevHeight = ((Node) event.getSource()).getScene().getHeight();
        double prevWidth = ((Node) event.getSource()).getScene().getWidth();
        oldStage.setScene(new Scene(sampleScene, prevWidth, prevHeight));
    }

    @Override
    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        addExtraIngredientScreen(actionEvent, "/gui/addExtraIngredient.fxml");
    }

    @Override
    public void addItemToOrder() {
        super.addItemToOrder();
        confirmButton.setDisable(false);
    }

}
