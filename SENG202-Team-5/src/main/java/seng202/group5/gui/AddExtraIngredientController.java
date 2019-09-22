package seng202.group5.gui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group5.Order;
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

    @FXML
    private Button launchSelectionScreenButton;

    @FXML
    private Button confirmItemButton;

    @FXML
    private Button backButton;

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
    private TableColumn<Ingredient, String> columnIngredientName = new TableColumn<>("ingredientName");

    @FXML
    private TableColumn<Ingredient, String> columnQuantity =  new TableColumn<>("quantity");

    @FXML
    private TableColumn<Ingredient, String> columnCategory = new TableColumn<>("category");

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
        HashMap<String, Integer> quantities = updatedStock.getIngredientStock();
        columnIngredientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnQuantity.setCellValueFactory(data -> {
            int quantity = quantities.get(data.getValue().getID());
            return new SimpleStringProperty(Integer.toString(quantity));
        });
    }

    /**
     * Creates the spinners that contain the ingredient objects.
     * Modified from dzim's code at:
     * https://stackoverflow.com/questions/36326058/javafx-how-to-programmatically-change-items-of-combobox-in-tablecell
     * @author dzim
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
                    int maxAmount = 20;
                    Ingredient ingredient = getTableView().getItems().get(getIndex());
                    HashMap<Ingredient, Integer> ingredientAmounts = selectedItem.getRecipe().getIngredientsAmount();
                    Integer index = getIndex();
                    Integer quantity = Integer.parseInt(columnQuantity.getCellObservableValue(index).getValue());
                    if (quantity < 0) {
                        quantity = 0;
                    }
                    maxAmount = Math.min(maxAmount, quantity);

                    int selectedAmount = ingredientAmounts.getOrDefault(ingredient, 0);
                    Spinner<Integer> spinner = new Spinner<>();
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
                            ingredientAmountMap.put(ingredient, newValue);
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
     */
    public void updateItemIngredients(ActionEvent actionEvent) {
        if (openMode.equals("Order")) {
            OrderController controller = (OrderController) changeScreen(actionEvent, "/gui/order.fxml");
            String itemID = selectedItem.getID();
            MenuItem originalItem = getAppEnvironment().getMenuManager().getMenuItems().get(itemID);
            if ((selectedItem.getRecipe().getIngredientsAmount().equals(originalItem.getRecipe().getIngredientsAmount()))) {
                selectedItem.setEdited(false);
            } else {
                selectedItem.setEdited(true);
            }
            controller.setMenuItem(selectedItem);
        } else if (openMode.equals("Recipe")) {
            AddRecipeController controller =
                    (AddRecipeController) changeScreen(actionEvent, "/gui/addRecipe.fxml");
            controller.setMenuItem(selectedItem);
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
                for (MenuItem item : currentOrder.getOrderItems().keySet()) {
                    HashMap<Ingredient, Integer> ingredientAmounts = item.getRecipe().getIngredientsAmount();
                    for (Ingredient currentIngredient : ingredientAmounts.keySet()) {
                        Integer amount = ingredientAmounts.get(currentIngredient);
                        Integer updatedStockAmount =
                                updatedStock.getIngredientQuantity(currentIngredient.getID()) - amount;
                        updatedStock.modifyQuantity(currentIngredient.getID(), updatedStockAmount);
                    }
                }
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
     */
    public void revertScreen(javafx.event.ActionEvent actionEvent) {
        if (openMode.equals("Order")) {
            OrderController controller = (OrderController) changeScreen(actionEvent, "/gui/order.fxml");
            controller.setMenuItem(oldItem);
        } else if (openMode.equals("Recipe")) {
            AddRecipeController controller = (AddRecipeController) changeScreen(actionEvent, "/gui/addRecipe.fxml");
            controller.setMenuItem(oldItem);
        }
    }

    public void setOpenMode(String tempOpenMode) {
        openMode = tempOpenMode;
    }

    protected MenuItem getSelectedItem() {
        return selectedItem;
    }

    protected MenuItem getOldItem() {
        return oldItem;
    }

}

