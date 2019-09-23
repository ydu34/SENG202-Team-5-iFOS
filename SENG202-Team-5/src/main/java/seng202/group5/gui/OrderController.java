package seng202.group5.gui;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seng202.group5.Order;
import seng202.group5.TypeEnum;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;

import java.util.*;

/**
 * The OrderController includes all the methods related to the every button on the order screen.
 *
 * @author Shivin Gaba
 */
public class OrderController extends GeneralController {

    private MenuItem item;

    @FXML
    private Recipe testRecipe2;
    @FXML
    private Label totalCostDisplay;

    @FXML
    private CheckBox vegan;

    @FXML
    private CheckBox vegetarian;

    @FXML
    private CheckBox glutenFree;

    @FXML
    private CheckBox mains;

    @FXML
    private CheckBox sides;

    @FXML
    private CheckBox beverages;

    @FXML
    private Text orderIDText;

    @FXML
    private TableView<Ingredient> ingredientInfoTable;

    @FXML
    private TableColumn<Ingredient, String> ingredientNameCol;

    @FXML
    private TableColumn<Ingredient, String> ingredientQuantityCol;

    @FXML
    private Text itemNameText;

    @FXML
    private Text recipeText;

    @FXML
    private Text costText;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Text menuItemName;

    @FXML
    private Button addItemButton;

    @FXML
    private Button addExtraIngredient;

    @FXML
    private TilePane tilePane;

    @FXML
    private MenuButton sortingBox;

    @FXML
    private Text promptText;


    private Order currentOrder;

    private ArrayList<MenuItem> allItems;

    private ArrayList<MenuItem> filteredItems;
    private SORT_TYPE sortingType = SORT_TYPE.NAME;

    @Override
    public void pseudoInitialize() {
        allItems = new ArrayList<>();
        allItems.addAll(getAppEnvironment().getMenuManager().getMenuItems().values());
        filterItems();
        try {
             currentOrder = getAppEnvironment().getOrderManager().getOrder();
        } catch (NoOrderException e) {
            System.out.println(e);
        }
        orderIDText.setText(currentOrder.getId());
        addItemButton.setDisable(true);
        addExtraIngredient.setDisable(true);
    }

    public void sortItemsPrice(ActionEvent event) {
        sortingType = SORT_TYPE.PRICE;
        filterItems();
    }

    public void sortItemsName(ActionEvent event) {
        sortingType = SORT_TYPE.NAME;
        filterItems();
    }

    /**
     * Adds all the menu items in the menu to the tile pane
     * @param items the items to add to the pane
     */
    public void populateTilePane(Collection<MenuItem> items) {
        if (tilePane != null) {
            ObservableList<Node> buttons = tilePane.getChildren();
            buttons.clear();
            ArrayList<MenuItem> sortedItems = new ArrayList<>(items);

            if (sortingType == SORT_TYPE.NAME) {
                sortedItems.sort(Comparator.comparing(MenuItem::getItemName));
            } else if (sortingType == SORT_TYPE.PRICE) {
                sortedItems.sort(Comparator.comparing(MenuItem::calculateFinalCost));
            }

            for (MenuItem item : sortedItems) {
                Button tempButton = new Button(item.getItemName());
                tempButton.setStyle("-fx-font-size: 20; ");
                tempButton.setPrefWidth(260);
                tempButton.setPrefHeight(100);
                tempButton.setOnAction((ActionEvent event) -> setMenuItem(item));
                buttons.add(tempButton);
            }
        }

    }

    private enum SORT_TYPE {
        NAME,
        PRICE
    }

    public ArrayList<MenuItem> filterItems() {
        ArrayList<MenuItem> filteredMenuItems = new ArrayList<>();
        if (allItems != null) {
            filteredMenuItems = new ArrayList<>(allItems);
        }
        filteredItems = new ArrayList<>(filteredMenuItems);


        if (glutenFree.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (!item.getRecipe().isGlutenFree()) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        filteredItems = new ArrayList<>(filteredMenuItems);
        if (vegan.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (!item.getRecipe().isVegan()) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        filteredItems = new ArrayList<>(filteredMenuItems);
        if (vegetarian.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (!item.getRecipe().isVegetarian()) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        filteredItems = new ArrayList<>(filteredMenuItems);
        if (!mains.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (item.getItemType() == TypeEnum.MAIN) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        filteredItems = new ArrayList<>(filteredMenuItems);
        if (!sides.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (item.getItemType() == TypeEnum.SIDE) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        filteredItems = new ArrayList<>(filteredMenuItems);
        if (!beverages.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (item.getItemType() == TypeEnum.BEVERAGE) {
                    filteredMenuItems.remove(item);
                }
            }
        }

        filteredItems = filteredMenuItems;

        populateTilePane(filteredMenuItems);

        return filteredMenuItems;

    }

    /**
     * Updates the given selected item in the order.
     *
     * @param newItem the new item with updated quantities and categories.
     */
    public void setMenuItem(MenuItem newItem) {

        item = newItem;
        populateIngredientsTable();
        addItemButton.setDisable(false);
        addExtraIngredient.setDisable(false);
        totalCostDisplay.setText(item.calculateFinalCost().multipliedBy(quantitySpinner.getValue()).getAmount().toString());
        menuItemName.setText(item.getItemName());
        recipeText.setText(newItem.getRecipe().getRecipeText());
    }



    /**
     * This method adds the selected menu Item to the stock only if the valid amount of ingredients are available.
     * Otherwise displays the appropriate message if the order can/cannot be added.
     */
    public void addItemToOrder() {

        Integer quantity = quantitySpinner.getValue();
        if (currentOrder.addItem(item, quantity)) {

            promptText.setText(quantity + " x " + item.getItemName() + " added to the current order.");
           promptText.setFill(Color.GREEN);
       }
       else{
           promptText.setText("Some ingredients are low in stock!!\n" + item.getItemName() +  " was not added to the current order");
           promptText.setFill(Color.RED);

       }
    }

    public void populateIngredientsTable() {
        Recipe currentRecipe = item.getRecipe();
        Map<Ingredient, Integer> recipeIngredientsMap = currentRecipe.getIngredientsAmount();
        List<Ingredient> recipeIngredients = new ArrayList<>(recipeIngredientsMap.keySet());
        ingredientNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingredientQuantityCol.setCellValueFactory(data ->{
            int quantity = recipeIngredientsMap.get(data.getValue());
            return new SimpleStringProperty(Integer.toString(quantity));
        });
        ingredientInfoTable.setItems(FXCollections.observableArrayList(recipeIngredients));
        //this code removes the scroll bar buts ends up adding an extra column
       // ingredientNameCol.setPrefWidth(ingredientInfoTable.getPrefWidth()*0.40);
       // ingredientQuantityCol.setPrefWidth(ingredientInfoTable.getPrefWidth()*0.20);

    }


    /**
     * This method launches the screen for adding extra ingredients to the selected menu item and
     * passes the item and order from the current class to the controller
     *
     * @param event an event that caused this to happen
     * @param scenePath the path to the screen file
     */
    public void addExtraIngredientScreen(ActionEvent event, String scenePath) {
        AddExtraIngredientController controller = (AddExtraIngredientController) changeScreen(event, scenePath);
        controller.setMenuItem(item);
        controller.setCurrentOrder(currentOrder);
        controller.setOpenMode("Order");
        controller.updateStock();
        controller.initializeTable();
    }

    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        addExtraIngredientScreen(actionEvent, "/gui/addExtraIngredient.fxml");
    }

    /**
     * A method to set the current order for the alternate order screen in history
     *
     * @param order the order to set the current order to
     */
    protected void setCurrentOrder(Order order) {
        currentOrder = order;
    }

    /**
     * A method to get the selected item for the alternate order screen in history
     *
     * @return the selected item
     */
    protected MenuItem getSelectedItem() {
        return item;
    }




}