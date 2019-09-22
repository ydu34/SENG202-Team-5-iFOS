package seng202.group5.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.DietEnum;
import seng202.group5.information.Ingredient;
import seng202.group5.logic.Stock;

import java.util.HashSet;

/**
 * A controller for a screen that adds ingredients to the stock
 *
 * @author Michael Morgun
 */
public class AddStockController extends GeneralController {

    @FXML
    private Button createButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField unitField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField costField;

    @FXML
    private TextField quantityField;

    @FXML
    private Label warningLabel;

    @FXML
    private CheckBox veganCheck;

    @FXML
    private CheckBox vegetarianCheck;

    @FXML
    private CheckBox glutenFreeCheck;

    private Stock stock;

    private Ingredient ingredient;

    /**
     * Creates a new ingredient and adds it to the stock
     *
     * @param actionEvent an event that caused this to happen
     */
    @FXML
    public void createIngredient(ActionEvent actionEvent) {

        costField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                costField.setText(oldValue);
            }
        });

        try {
            // Getting all the values through the text fields
            String name = nameField.getText();
            String category = categoryField.getText();
            Money cost = Money.parse("NZD " + costField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            // Getting dietary information of ingredient through checkboxes
            HashSet<DietEnum> dietRequirements = new HashSet<>();
            if (veganCheck.isSelected()) {
                dietRequirements.add(DietEnum.VEGAN);
            }
            if (vegetarianCheck.isSelected()) {
                dietRequirements.add(DietEnum.VEGETARIAN);
            }
            if (glutenFreeCheck.isSelected()) {
                dietRequirements.add(DietEnum.GLUTEN_FREE);
            }

            // Attempting to make an ingredient from data collected above
            ingredient = new Ingredient(name, category, cost, dietRequirements);
            addDietaryInformation();
            // Adding ingredient to the stock
            stock.addNewIngredient(ingredient, quantity);

            // Closing window
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            warningLabel.setText("Error creating ingredient.");
            e.printStackTrace();
        }
    }

    /**
     * Adds dietary information to the ingredient object
     */
    public void addDietaryInformation() {
        if (veganCheck.isSelected()) {
            ingredient.addDietInfo(DietEnum.VEGAN);
        }
        if (vegetarianCheck.isSelected()) {
            ingredient.addDietInfo(DietEnum.VEGETARIAN);
        }
        if (glutenFreeCheck.isSelected()) {
            ingredient.addDietInfo(DietEnum.GLUTEN_FREE);
        }
    }

    public void setStock(Stock input) {
        stock = input;
    }

}
