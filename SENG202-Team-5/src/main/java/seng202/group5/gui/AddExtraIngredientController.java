package seng202.group5.gui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Ingredient;

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
    private MenuItem oldItem;
    @FXML
    private MenuItem selectedItem;

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
    private TableColumn<Ingredient, String> columnSpinner = new TableColumn<>("spinner");

    private Set<Ingredient> selectedIngredientSet;

    private ObservableList<Ingredient> itemIngredients;

    @Override
    public void pseudoInitialize() {
        HashMap<String, Integer> quantities = getAppEnvironment().getStock().getIngredientStock();
        columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        columnIngredientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnQuantity.setCellValueFactory(data -> {
            int quantity = quantities.get(data.getValue().getID());
            return new SimpleStringProperty(Integer.toString(quantity));
        });
        }

    /**
     * Calls helper functions which handle the filling of a list which is used to populate the ingredients table view.
     */
    public void initializeTable() {
            initializeSelectedIngredients();
        initializeRemainingIngredients();
        initializeSpinners();
        ingredientsTable.setItems(itemIngredients);
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
                    Ingredient ingredient = getTableView().getItems().get(getIndex());
                    int amount = selectedItem.getRecipe().getIngredientsAmount().getOrDefault(ingredient, 0);
                    Spinner<Integer> spinner = new Spinner<>();
                    //TODO The limit of the spinner should be either 20 or the remaining quantity of items, whatever is smaller.
                    spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, +20, amount));

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
    public void updateItemIngredients(javafx.event.ActionEvent actionEvent) {
        //TODO Currently adds "edited" to ingredients with their original recipe changed that have been added back into the system. Also needs to handle a change in stock.
        OrderController controller = (OrderController) changeScreen(actionEvent, "/gui/order.fxml");
        if ((selectedItem.getRecipe().getIngredientsAmount() != oldItem.getRecipe().getIngredientsAmount())
                && !selectedItem.getEdited()) {
            selectedItem.setEdited(true);
        } else {
            selectedItem.setEdited(false);
        }
        controller.updateItem(selectedItem);
    }

    /**
     * Takes the selected MenuItem and adds its ingredients required to the tableview.
     */
    public void initializeSelectedIngredients() {
        selectedIngredientSet = selectedItem.getRecipe().getIngredientsAmount().keySet();
        itemIngredients = FXCollections.observableArrayList(
                selectedIngredientSet);
    }

    /**
     * Takes the Stock and adds each ingredient that doesn't exist in the table view into it.
     * Furthermore adds ingredients with 0 units left, but prevents the user from adding it to the MenuItem.
     */
    public void initializeRemainingIngredients() {
        Collection<Ingredient> allIngredientList = getAppEnvironment().getStock().getIngredients().values();
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

    /**
     * Returns to the order screen, returning the original item as the selected item.
     */
    public void revertToOrder(javafx.event.ActionEvent actionEvent) {
        OrderController controller = (OrderController) changeScreen(actionEvent, "/gui/order.fxml");
        controller.updateItem(oldItem);
    }

}

