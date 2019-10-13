package seng202.group5.gui.stock;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.logic.Stock;

/**
 * The Controller for the screen which warns you before removing an item.
 *
 * @author Michael Morgoun
 */
public class RemoveStockController extends GeneralController {

    /**
     * The label which shows what you are removing.
     */
    @FXML
    private Label removeLabel;

    /**
     * The button for actually removing the item.
     */
    @FXML
    private Button removeButton;

    /**
     * The current stock of the system.
     */
    private Stock stock;

    /**
     * The ingredient at risk of being removed.
     */
    private Ingredient ingredient;

    /**
     * Initialises the labels to ones that show the current ingredient being removed.
     */
    @Override
    public void pseudoInitialize() {
        super.pseudoInitialize();
        removeLabel.setText("Do you want to remove " + ingredient.getName() + "?\n" +
                "This will remove " + ingredient.getName() + " from all recipes!");
    }

    /**
     * Removes the ingredient from the stock.
     */
    @FXML
    public void removeIngredient() {
        stock.removeIngredient(ingredient.getID());
        for (MenuItem item : getAppEnvironment().getMenuManager().getItemMap().values()) {
            if (item.getRecipe().getIngredientsAmount().containsKey(ingredient)) {
                item.getRecipe().removeIngredient(ingredient);
            }
        }

        // Closes the window
        Stage stage = (Stage) removeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Cancels the removal of ingredient, and closes the window.
     */
    @FXML
    public void cancelButton() {
        Stage stage = (Stage) removeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets the ingredient to the one that is being removed.
     * @param ingredient1 The ingredient being removed.
     */
    public void setIngredient(Ingredient ingredient1) {
        ingredient = ingredient1;
    }

    /**
     * Sets the stock.
     * @param newStock The stock of the system.
     */
    public void setStock(Stock newStock) { stock = newStock; }
}
