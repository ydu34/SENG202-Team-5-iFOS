package seng202.group5.gui.stock;

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
import seng202.group5.information.DietEnum;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.logic.Stock;

import javax.naming.directory.InvalidAttributeValueException;
import java.awt.*;
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

    @Override
    public void pseudoInitialize() {

        // Set text of all text field to the ones of the ingredient if it exists
        if (ingredient != null) {
            nameField.setText(ingredient.getName());
            categoryField.setText(ingredient.getCategory());
            costField.setText(ingredient.getCost().getAmount().toString());

            for (DietEnum diet : ingredient.getDietInfo()) {
                switch (diet) {
                    case GLUTEN_FREE: glutenFreeCheck.setSelected(true); break;
                    case VEGETARIAN: vegetarianCheck.setSelected(true); break;
                    case VEGAN: veganCheck.setSelected(true); break;
                }
            }

            // Changing button label
            createButton.setText("Modify");
        } else {
            createButton.setText("Create");
        }

        // Listeners for the number only text fields such as quantity and cost
        costField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                costField.setText(oldValue);
            }
        });
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                quantityField.setText(oldValue);
            }
        });
    }

    /**
     * Either creates or modifies an ingredient depending on if it's null or not.
     * @param actionEvent The button action.
     */
    @FXML
    public void buttonAction(ActionEvent actionEvent) {
        try {
            if (ingredient == null) {
                createIngredient();
            } else {
                modifyIngredient();
            }
            // Closing window
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            warningLabel.setText("Error creating ingredient.");
        }
    }

    /**
     * Modifies an existing ingredient.
     */
    public void modifyIngredient()  throws ArithmeticException{
        // Modifying the current ingredient
        String name = nameField.getText();
        String category = categoryField.getText();
        String price = costField.getText();
        String quantity = quantityField.getText();

        // Throwing exceptions if input in incorrect.
        if (name.isEmpty() || category.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
            throw new ArithmeticException("A Field is blank.");
        } else if (Float.parseFloat(price) < 0 || Integer.parseInt(quantity) < 0) {
            throw new ArithmeticException("Integers are out of bounds.");
        } else {

            ingredient.setName(name);
            ingredient.setCategory(category);
            ingredient.setPrice(Money.parse("NZD " + price));
            stock.modifyQuantity(ingredient.getID(), Integer.parseInt(quantity));

            // Clears diet info since it's just added on again later
            HashSet<DietEnum> set = new HashSet<>();
            ingredient.setDietaryInformation(set);

            addDietaryInformation();

            // Updates the dietary information about stored recipes
            for (MenuItem item : getAppEnvironment().getMenuManager().getItemMap().values()) {
                for (DietEnum dietType : DietEnum.values()) item.getRecipe().checkDietaryInfo(dietType);
            }
        }

    }

    /**
     * Creates a new ingredient.
     */
    public void createIngredient() throws ArithmeticException {
            // Getting all the values through the text fields
        String name = nameField.getText();
        String category = categoryField.getText();
        Money cost = Money.parse("NZD " + costField.getText());
        int quantity = Integer.parseInt(quantityField.getText());

        // Throwing exceptions if input in incorrect.
        if (name.isEmpty() || category.isEmpty() || costField.getText().isEmpty() || quantityField.getText().isEmpty()) {
            throw new ArithmeticException("A Field is blank.");
        } else if (Float.parseFloat(costField.getText()) < 0 || quantity < 0) {
            throw new ArithmeticException("Integers are out of bounds.");
        } else {

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
        }
    }

    /**
     * Adds dietary information to the current ingredient.
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

    /**
     * Sets the Stock of this screen to the stock that is currently in the system.
     * @param input The current Stock.
     */
    public void setStock(Stock input) { stock = input; }

    /**
     * Sets the ingredient for modifying.
     * @param ingredient1 The Ingredient to be modified.
     */
    public void setIngredient(Ingredient ingredient1) { ingredient = ingredient1; }

    /**
     * Sets the quantity field separately due to the attributes of an Ingredient not containing its own quantity.
     * @param quantity A string for the quantity.
     */
    public void setQuantity(String quantity) { quantityField.setText(quantity); }
}
