package seng202.group5.gui;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.joda.money.Money;
import seng202.group5.logic.Order;
import seng202.group5.information.TypeEnum;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;

import java.util.*;

/**
 * This controller handles all the functionality with the making a new order or
 * editing an existing order.
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

    @FXML
    private AnchorPane tilePaneContainer;

    @FXML
    private TableView<MenuItem> currentOrderTable;

    @FXML
    private TableColumn<MenuItem, String> itemNameCol;

    private Order currentOrder;

    private boolean someOrder = false;

    private ArrayList<MenuItem> allItems;

    private Map<MenuItem, Integer> orderItemsMap;

    @FXML
    private TableColumn<MenuItem, String> itemQuantityCol;

    @FXML
    private Button removeItemButton;

    @FXML
    private TableColumn<MenuItem,String> itemPriceCol;

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

        currentOrderTable();
        orderIDText.setText(currentOrder.getId());
        addItemButton.setDisable(true);
        addExtraIngredient.setDisable(true);

        tilePaneContainer.widthProperty().addListener((width) -> {
            double newWidth = tilePaneContainer.getWidth();
            tilePane.setMinWidth(newWidth);
            tilePane.setPrefWidth(newWidth);
            tilePane.setMaxWidth(newWidth);
        });
    }

    /**
     * This function sorts the menu item by its price
     * @param event an event that caused this to happen
     */

    public void sortItemsPrice(ActionEvent event) {
        sortingType = SORT_TYPE.PRICE;
        filterItems();
    }

    /**
     * Tis function sorts the menu items by its name
     * @param event an event that caused this to happen
     */

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
                JFXButton tempButton = new JFXButton(item.getItemName());
                tempButton.setStyle("-fx-font-size: 20; ");
                tempButton.setPrefWidth(260);
                tempButton.setPrefHeight(100);
                TilePane.setMargin(tempButton, new Insets(5));
                tempButton.setOnAction((ActionEvent event) -> setMenuItem(item));
                buttons.add(tempButton);
            }
        }

    }

    private enum SORT_TYPE {
        NAME,
        PRICE
    }

    /**
     * Filters the menu items in the menu based on the check boxes on the order screen
     */
    public void filterItems() {
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
            //pseudoInitialize();
            currentOrderTable();


       }
       else{
           promptText.setText("Some ingredients are low in stock!!\n" + item.getItemName() +  " was not added to the current order");
           promptText.setFill(Color.RED);

       }
    }

    /**
     * This method shows the amount of ingredients in a selected menu item
     */

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

    /**
     * This function populates the mini invoice screen with the selected menu items along with their quantity which are the part of the current order
     */

    public void currentOrderTable() {
//        int quantity = 0;
        orderItemsMap = currentOrder.getOrderItems();
        List<MenuItem> orderItems = new ArrayList<>(orderItemsMap.keySet());
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemQuantityCol.setCellValueFactory(data -> {
            int quantity = orderItemsMap.get(data.getValue());
            return new SimpleStringProperty(Integer.toString(quantity));

        });

        currentOrderTable.setItems(FXCollections.observableArrayList(orderItems));
    }

    /**
     * This function clears whole order when clicked on the cancel button on the mini invoice screen.
     */
    @FXML
    private void cancelCurrentOrder() {

        try {
            currentOrder = getAppEnvironment().getOrderManager().getOrder();
            currentOrder.resetStock(getAppEnvironment().getStock());
            currentOrder.clearItemsInOrder();
        } catch (NoOrderException ignored) {

        }
        pseudoInitialize();
    }

    /**
     * This function removes the selected item from the current order when clicked on the remove button and updates the invoice display
     * @param actionEvent
     */
    @FXML
    private void removeSelectedItem(javafx.event.ActionEvent actionEvent ) {
        currentOrder.removeItem(currentOrderTable.getSelectionModel().getSelectedItem());
        someOrder =  this.currentOrderTable.getItems().remove(this.currentOrderTable.getSelectionModel().getSelectedItem());
        if(someOrder == true){
            removeItemButton.setDisable(false);
        }

    }

    /**
     * This method launches the addExtraIngredient Screen.
     * @param actionEvent
     */

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