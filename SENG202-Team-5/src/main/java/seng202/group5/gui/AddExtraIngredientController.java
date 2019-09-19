package seng202.group5.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seng202.group5.information.MenuItem;

public class AddExtraIngredientController extends GeneralController {

    @FXML
    private Button launchSelectionScreenButton;

    @FXML
    private MenuItem selectedItem;

    @Override
    public void pseudoInitialize() {

        initializeSelectedIngredients();
        initializeRemainingIngredients();
        }

    public void launchSelectionScreen(javafx.event.ActionEvent actionEvent) { // This does not remember the order
        changeScreen(actionEvent, "/gui/order.fxml");
    }

    /**
     * Updates the given item's ingredients to match what is selected in the GUI and returns to the Order screen.
     */
    public void updateItemIngredients(javafx.event.ActionEvent actionEvent) {
        System.out.println("Implement me!");
    }

    /**
     * Takes a given MenuItem and adds its ingredients required to the tableview.
     */
    public void initializeSelectedIngredients() {
        System.out.println("Implement me!");
    }

    /**
     * Takes the Stock and adds each ingredient that doesn't exist in the table view into it.
     * Furthermore adds ingredients with 0 units left, but prevents the user from adding it to the MenuItem.
     */
    public void initializeRemainingIngredients() {
        System.out.println("Implement Me!");
    }

}

