package seng202.group5.gui.stock;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Ingredient;
import seng202.group5.logic.Stock;


public class RemoveStockController extends GeneralController {

    @FXML
    private Label removeLabel;

    @FXML
    private Button removeButton;

    @FXML
    private Button cancelButton;

    private Stock stock;

    private Ingredient ingredient;

    @Override
    public void pseudoInitialize() {
        removeLabel.setText("Do you want to remove " + ingredient.getName() + "?");
    }

    @FXML
    public void removeIngredient(ActionEvent event) {
        stock.removeIngredient(ingredient.getID());

        Stage stage = (Stage) removeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelButton(ActionEvent event) {
        Stage stage = (Stage) removeButton.getScene().getWindow();
        stage.close();
    }

    public void setIngredient(Ingredient ingredient1) {
        ingredient = ingredient1;
    }

    public void setStock(Stock newStock) { stock = newStock; }

}
