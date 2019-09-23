package seng202.group5.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.logic.MenuManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller for addRecipe.fxml, used to create new recipes and added to the menu.
 * @Author Yu Duan
 */
public class AddRecipeController extends GeneralController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField markupCostField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<Ingredient> ingredientsTable;

    @FXML
    private TableColumn<Ingredient, String> ingredientCol;

    @FXML
    private TableColumn<Ingredient, String> quantityCol;

    @FXML
    private Button addIngredientButton;

    @FXML
    private Text markupCostWarningText;

    @FXML
    private Text ingredientCostText;

    @FXML
    private Button computeTotalCostButton;

    @FXML
    private Text totalCostText;

    private MenuItem item;

    private MenuManager menuManager;

    private AdminController parentController;

    /**
     * An initializer for this controller
     */
    @FXML
    public void pseudoInitialize() {
        menuManager = getAppEnvironment().getMenuManager();
        item = new MenuItem();
        initializeTextValues();
        populateIngredientsTable();

    }

    /**
     * Saves the recipe into the systems menu and closes the add recipe window.
     */
    public void saveRecipe() {
        saveTextFieldValues();
        menuManager.addItem(item);
        closeScreen();
    }

    public void saveTextFieldValues() {
        String name = nameField.getText();
        String markupPriceStr = markupCostField.getText();
        try {
            Money markupPrice = Money.parse("NZD " + markupPriceStr);
            item.getRecipe().setName(name);
            item.getRecipe().setRecipeText("No recipe for this");
            item.setItemName(name);
            item.setMarkupCost(markupPrice);
            item.calculateFinalCost();
        } catch(Exception e) {
            markupCostWarningText.setText("Invalid value");
        }

    }

    public void computeTotalCost() {
        String markupPriceStr = markupCostField.getText();
        try {
            Money markupPrice = Money.parse("NZD " + markupPriceStr);
            item.setMarkupCost(markupPrice);
            totalCostText.setText(item.calculateFinalCost().toString());
            System.out.println(item.calculateFinalCost().toString());
        } catch(Exception e) {
            markupCostWarningText.setText("Invalid value");
        }
    }

    /**
     * Closes this window.
     */
    public void closeScreen() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    /**
     * Changes screen to the add ingredient screen to select ingredients to add to the recipe by passing in the item.
     * @param actionEvent
     */
    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        saveTextFieldValues();
        AddExtraIngredientController controller =
                (AddExtraIngredientController) changeScreen(actionEvent, "/gui/addExtraIngredient.fxml");
        controller.setMenuItem(item);
        controller.setOpenMode("Recipe");
        controller.updateStockRecipeMode();
        controller.initializeTable();
    }

    /**
     * Populates the table using the item's ingredients and quantities.
     */
    public void populateIngredientsTable() {
        Recipe currentRecipe = item.getRecipe();
        Map<Ingredient, Integer> recipeIngredientsMap = currentRecipe.getIngredientsAmount();
        List<Ingredient> recipeIngredients = new ArrayList<>(recipeIngredientsMap.keySet());
        ingredientCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(data ->{
            int quantity = recipeIngredientsMap.get(data.getValue());
            return new SimpleStringProperty(Integer.toString(quantity));
        });
        ingredientsTable.setItems(FXCollections.observableArrayList(recipeIngredients));
    }

    /**
     * Initializes the text fields with values of the item.
     */
    public void initializeTextValues() {
        nameField.setText(item.getItemName());
        ingredientCostText.setText(item.calculateMakingCost().toString());
        markupCostField.setText(item.getMarkupCost().toString().substring(4));
        totalCostText.setText(item.calculateFinalCost().toString());
    }

    /**
     * @param tempItem Item that has been modified in AddExtraIngredientController.
     */
    public void setMenuItem(MenuItem tempItem) {
        item = tempItem;
        initializeTextValues();
        populateIngredientsTable();
    }
}
