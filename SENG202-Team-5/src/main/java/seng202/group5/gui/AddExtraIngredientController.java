package seng202.group5.gui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.joda.money.Money;
import seng202.group5.gui.history.AddPastOrderController;
import seng202.group5.logic.Order;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Ingredient;
import seng202.group5.logic.Stock;

import java.util.*;

/**
 * Handles adding and removing ingredients and items from the selected item it is passed.
 * TODO: Add handling for dietary requirements if a selected ingredients breaks the selected dietary rule.
 * TODO: Add method to handle and change the names of a menu item given that it is edited, eg Chicken Burger {+1 Cheese}
 * @author James Kwok
 */
public class AddExtraIngredientController extends GeneralController {

    private static final int MAX_INGREDIENT_AMOUNT = 30;

    @FXML
    private String openMode;

    @FXML
    private MenuItem oldItem;

    @FXML
    private MenuItem selectedItem;

    @FXML
    private Order currentOrder;

    @FXML
    private Stock updatedStock;

    @FXML
    private TableView<Ingredient> ingredientsTable;

    @FXML
    private TableColumn<Ingredient, String> columnID = new TableColumn<>("ID");

    @FXML
    private TableColumn<Ingredient, String> columnIngredientName = new TableColumn<>("ingredientName");

    @FXML
    private TableColumn<Ingredient, String> columnQuantity =  new TableColumn<>("quantity");

    @FXML
    private TableColumn<Ingredient, String> columnCategory = new TableColumn<>("category");
    @FXML
    private TableColumn<Ingredient, String> columnCost = new TableColumn<>("cost");

    @FXML
    private TableColumn<Ingredient, String> columnSpinner = new TableColumn<>("spinner");

    private Set<Ingredient> selectedIngredientSet;

    private ObservableList<Ingredient> itemIngredients;

    /**
     * Calls helper functions which handle the filling of a list which is used to populate the ingredients table view.
     */
    public void initializeTable() {
        initializeColumns();
        initializeSelectedIngredients();
        initializeRemainingIngredients();
        initializeSpinners();
        ingredientsTable.setItems(itemIngredients);
    }

    /**
     * Sets up value factories in each column which take ingredients and populate the table with their data.
     */
    public void initializeColumns() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        columnIngredientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnQuantity.setCellValueFactory(data -> {
            int quantity = updatedStock.getIngredientStock().get(data.getValue().getID());
            return new SimpleStringProperty(Integer.toString(quantity));
        });
        //sets values for each ingredient cost.
        columnCost.setCellValueFactory(data -> {
            Money cost = updatedStock.getIngredients().get(data.getValue().getID()).getCost();
            return new SimpleStringProperty(cost.toString());
        });
    }

    /**
     * Creates the spinners that contain the ingredient objects.
     * Modified from dzim's code at:
     * https://stackoverflow.com/questions/36326058/javafx-how-to-programmatically-change-items-of-combobox-in-tablecell
     */
    public void initializeSpinners() {
        columnSpinner.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getName()));
        columnSpinner.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    int maxAmount = MAX_INGREDIENT_AMOUNT;
                    Ingredient ingredient = getTableView().getItems().get(getIndex());
                    HashMap<Ingredient, Integer> ingredientAmounts = selectedItem.getRecipe().getIngredientsAmount();
                    int index = getIndex();
                    int quantity = Integer.parseInt(columnQuantity.getCellObservableValue(index).getValue());
                    if (quantity < 0) {
                        quantity = 0;
                    }
                    maxAmount = Math.min(maxAmount, quantity);

                    int selectedAmount = ingredientAmounts.getOrDefault(ingredient, 0);
                    Spinner<Integer> spinner = new Spinner<>();
                    spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
                    spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0, maxAmount, selectedAmount));

                    /*
                     * Updates the ingredient amount of the item. If the ingredient does not exist in the item,
                     * it adds the ingredient to the item. If the amount of an ingredient was set to 0, then it
                     * removes the ingredient from the map.
                     */
                    spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                        HashMap<Ingredient,Integer> ingredientAmountMap =
                                selectedItem.getRecipe().getIngredientsAmount();
                        if ((newValue == 0) && (ingredientAmountMap.containsKey(ingredient))) {
                            ingredientAmountMap.remove(ingredient);
                        } else if (newValue != 0) {
                            selectedItem.getRecipe().addIngredient(ingredient,  newValue - oldValue);
                        }
                    });
                    setGraphic(spinner);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        });
    }

    /**
     * Updates the given item's ingredients to match what is selected in the GUI and returns to the Order screen.
     * Also updates the name of the item if it's ingredients are different to the unedited version.
     * @param actionEvent an event that caused this to happen
     */
    public void updateItemIngredients(ActionEvent actionEvent) {
        switch (openMode) {
            case "Order": {
                OrderController controller = (OrderController) changeScreen(actionEvent, "/gui/order.fxml");
                MenuItem originalItem = getOriginalItem();
                if ((selectedItem.getRecipe().getIngredientsAmount().equals(originalItem.getRecipe().getIngredientsAmount()))) {
                    selectedItem.setEdited(false);
                } else {
                    selectedItem.setEdited(true);
                }
                controller.setMenuItem(selectedItem);
                break;
            }
            case "Recipe": {
                AddRecipeController controller = (AddRecipeController) changeScreen(actionEvent, "/gui/addRecipe.fxml");
                controller.setMenuItem(selectedItem);
                break;
            }
            case "PastOrder": {
                AddPastOrderController controller = AddPastOrderController.changeToPastOrderScreen(actionEvent, this);
                MenuItem originalItem = getOriginalItem();
                if ((selectedItem.getRecipe().getIngredientsAmount().equals(originalItem.getRecipe().getIngredientsAmount()))) {
                    selectedItem.setEdited(false);
                } else {
                    selectedItem.setEdited(true);
                }
                controller.setMenuItem(selectedItem);
                controller.setOrder(getCurrentOrder());
                break;
            }
        }
    }

    /**
     * Takes the selected MenuItem and adds its ingredients required to the tableview.
     */
    public void initializeSelectedIngredients() {
        selectedIngredientSet = selectedItem.getRecipe().getIngredientsAmount().keySet();
        itemIngredients = FXCollections.observableArrayList(selectedIngredientSet);
    }

    /**
     *Sets updatedStock to a copy of the current stock. Calculates the updated stock values taking into account
     * the items that currently exist in the order.
     */
    public void updateStock() {
        updatedStock = getAppEnvironment().getStock().clone();
        if (currentOrder != null) {
            updatedStock = currentOrder.getStock().clone();
        }
    }

    /**
     *Sets updatedStock to a copy of the current stock. Set's all quantities to 9999 temporarily.
     */
    public void updateStockRecipeMode() {
        updatedStock = getAppEnvironment().getStock().clone();
        for (String ingredientID : updatedStock.getIngredientStock().keySet()) {
            updatedStock.modifyQuantity(ingredientID, 999999);
        }
    }

    /**
     * Takes the Stock and adds each ingredient that doesn't exist in the table view into it.
     * Furthermore adds ingredients with 0 units left, but prevents the user from adding it to the MenuItem.
     */
    public void initializeRemainingIngredients() {
        Collection<Ingredient> allIngredientList = updatedStock.getIngredients().values();
        for (Ingredient ingredient : allIngredientList) {
            if (!selectedIngredientSet.contains(ingredient)) {
                itemIngredients.add(ingredient);
            }
        }
    }

    public void setMenuItem(MenuItem newItem) {
        oldItem = newItem;
        selectedItem = newItem.clone();
    }
    public void setCurrentOrder(Order tempOrder) {
        currentOrder = tempOrder;
    }

    /**
     * Returns to the previous screen, returning the original item as the selected item.
     * @param actionEvent an event that caused this to happen
     */
    public void revertScreen(javafx.event.ActionEvent actionEvent) {
        switch (openMode) {
            case "Order": {
                OrderController controller = (OrderController) changeScreen(actionEvent, "/gui/order.fxml");
                controller.setMenuItem(oldItem);
                break;
            }
            case "Recipe": {
                AddRecipeController controller = (AddRecipeController) changeScreen(actionEvent, "/gui/addRecipe.fxml");
                controller.setMenuItem(oldItem);
                break;
            }
            case "PastOrder": {
                AddPastOrderController controller = AddPastOrderController.changeToPastOrderScreen(actionEvent, this);
                controller.setOrder(currentOrder);
                controller.setMenuItem(getOriginalItem());
                break;
            }
        }
    }

    public void setOpenMode(String tempOpenMode) {
        openMode = tempOpenMode;
    }

    protected MenuItem getSelectedItem(){
        return selectedItem;
    }

    private MenuItem getOriginalItem() {
        return getAppEnvironment().getMenuManager().getMenuItems().get(selectedItem.getID());
    }

    protected Order getCurrentOrder() {
        return currentOrder;
    }

}

