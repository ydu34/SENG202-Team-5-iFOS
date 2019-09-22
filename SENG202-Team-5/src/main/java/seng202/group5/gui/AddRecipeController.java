package seng202.group5.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.AppEnvironment;
import seng202.group5.TypeEnum;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.logic.MenuManager;

import java.util.ArrayList;
import java.util.HashMap;
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
    private TextField makingPriceField;

    @FXML
    private TextField totalPriceField;

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
    private Text totalCostWarningText;

    private MenuItem item;

    private MenuManager menuManager;

    private AdminController parentController;

    @FXML
    public void pseudoInitialize() {
        menuManager = getAppEnvironment().getMenuManager();
        item = new MenuItem();
        List<Ingredient> ingredients = new ArrayList<>(item.getRecipe().getIngredientsAmount().keySet());
        HashMap<Ingredient, Integer> quantities = item.getRecipe().getIngredientsAmount();
        ingredientCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(data -> {
            int quantity = quantities.get(data.getValue());
            return new SimpleStringProperty(Integer.toString(quantity));
        });
        ingredientsTable.setItems(FXCollections.observableArrayList(ingredients));
    }

    /**
     * Saves the recipe into the systems menu and closes the add recipe window.
     */
    public void saveRecipe() {
        String name = nameField.getText();
        String markupPriceStr = makingPriceField.getText();
        String totalPriceStr = totalPriceField.getText();
        try {
            Money.parse("NZD " + markupPriceStr);
        } catch(Exception e) {
            markupCostWarningText.setText("Invalid value");
        }

        try {
            Money.parse("NZD " + totalPriceStr);
        } catch(Exception e) {
            totalCostWarningText.setText("Invalid value");
        }
        Money markupPrice = Money.parse("NZD " + markupPriceStr);
        Money totalPrice = Money.parse("NZD " + totalPriceStr);
        item.getRecipe().setName(name);
        item.getRecipe().setRecipeText("No recipe for this");
        item.setItemName(name);
        item.setMarkupCost(markupPrice);
        item.calculateFinalCost();
        menuManager.addItem(item);
        parentController.recipeTableInitialize();
        closeScreen();
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
     * @param tempItem Item that has been modified in AddExtraIngredientController.
     */
    public void setMenuItem(MenuItem tempItem) {
        item = tempItem;
        populateIngredientsTable();
    }

    /**
     * @param parentController The parent controller, the admin screen that opened this screen.
     */
    public void setParentController(AdminController parentController) {
        this.parentController = parentController;
    }


}
