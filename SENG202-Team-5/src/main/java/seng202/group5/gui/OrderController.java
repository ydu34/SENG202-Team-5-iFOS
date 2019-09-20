package seng202.group5.gui;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import seng202.group5.Order;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The OrderController includes all the methods related to the every button on the order screen.
 *
 * @author Shivin Gaba
 */
public class OrderController extends GeneralController {

    public Recipe testRecipe;



    @FXML
    private Button itemButton;

    @FXML
    private Button itemButton2;
    private MenuItem item;
  //  private MenuItem item2;
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
    private ArrayList<Button> filteredButtons;

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

    private Order currentOrder;

    private ArrayList<MenuItem> allItems;

    private ArrayList<MenuItem> filteredItems;

    @Override
    public void pseudoInitialize() {
        allItems = new ArrayList<>();
        allItems.addAll(getAppEnvironment().getMenuManager().getMenuItems().values());
        showItems(new ActionEvent());
        try {
             currentOrder = getAppEnvironment().getOrderManager().getOrder();
        } catch (NoOrderException e) {
            //TODO: Implement me!
            System.out.println(e);
        }
        orderIDText.setText(currentOrder.getID());
        addItemButton.setDisable(true);
        addExtraIngredient.setDisable(true);

    }

    public void checkDietryInfo(ActionEvent event) {
        // get values of checkboxes here
        // call showItems with those vlaues as parameter
    }

    public ArrayList<MenuItem> filterItems() {
        filteredButtons = new ArrayList<>();
        filteredButtons.add(itemButton);
        filteredButtons.add(itemButton2);
        ArrayList<MenuItem> filteredMenuItems = new ArrayList<>();
        if (allItems != null) {
            filteredMenuItems = new ArrayList<>(allItems);
        }


        if (glutenFree.isSelected()) {
            for (MenuItem item : allItems) {
                if (!item.getRecipe().isGlutenFree()) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        if (vegan.isSelected()) {
            for (MenuItem item : allItems) {
                if (!item.getRecipe().isVegan()) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        if (vegetarian.isSelected()) {
            for (MenuItem item : allItems) {
                if (!item.getRecipe().isVegetarian()) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        int i;
        for (i=0; i<filteredMenuItems.size(); i++){
            System.out.println(i);
            filteredButtons.get(i).setText(filteredMenuItems.get(i).getItemName());
            filteredButtons.get(i).setVisible(true);

        }
        for (i=filteredMenuItems.size(); i<filteredButtons.size(); i++){
            filteredButtons.get(i).setVisible(false);

        }
        filteredItems = filteredMenuItems;

        return filteredMenuItems;

    }

    /**
     * Updates the given selected item in the order.
     *
     * @param newItem the new item with updated quantities and categories.
     */
    public void setMenuItem(MenuItem newItem) {

        item = newItem;
        ingredientsTable();
        addItemButton.setDisable(false);
        addExtraIngredient.setDisable(false);
        totalCostDisplay.setText(item.calculateFinalCost().multipliedBy(quantitySpinner.getValue()).getAmount().toString());
        menuItemName.setText(item.getItemName());

        System.out.println(newItem);
        System.out.println(newItem.calculateFinalCost());
        recipeText.setText(newItem.getRecipe().getRecipeText());
        itemNameText.setText(newItem.getItemName() + "\n");
    }



    /**
     * Still trying to work this code out. Please dont delete it. */
    @FXML
    public void showItems(ActionEvent event) {
        ArrayList<MenuItem> itemsToShow = new ArrayList<>();
        itemsToShow = filterItems();
        // work with menuItemsList


    }

    public void addItemtoOrder() {
        Integer quantity = quantitySpinner.getValue();
        currentOrder.addItem(item, quantity); // Not working so temporary add manually
        //currentOrder.getOrderItems().put(item, quantity);
        System.out.println(currentOrder.getOrderItems());
     //   changeScreen(actionEvent, "/gui/order.fxml");

    }

    public void ingredientsTable() {
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
     * This method launches the selection screen for the selected menu item and passes the recipe and object from the
     * from the current class to the the Selection controller class.
     *
     * @param event
     * @param scenePath
     */
    public void selectionScreen(ActionEvent event, String scenePath) {
        SelectionController controller = (SelectionController) changeScreen(event, scenePath);
        controller.setMenuItem(item);
    }

    /**
     * This method sets the text in the Ingredient panel to the list of ingredient
     * present in the recipe and also updates the totalCostDisplay to the selling cost of that item.
     */
    @FXML
    public void getIngredients(ActionEvent actionEvent) {
        addItemButton.setDisable(false);
        //make_object();

        MenuItem selectedItem = null;

        if (filteredItems != null) {
            for (MenuItem item : filteredItems) {
                Button btn = (Button) actionEvent.getSource();
                if (item.getItemName().equals(btn.getText())) {
                    selectedItem = item;
                }
            }
            if (selectedItem != null) {
                setMenuItem(selectedItem);
            }

        }
    }


    /**
     * This method launches the selection screen for the selected menu item and passes the recipe and object from the
     * from the current class to the the Selection controller class.
     *
     * @param event
     * @param scenePath
     */
    public void addExtraIngredientScreen(ActionEvent event, String scenePath) {
        AddExtraIngredientController controller = (AddExtraIngredientController) changeScreen(event, scenePath);
        controller.setMenuItem(item);
        controller.initializeTable();
    }

    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        addExtraIngredientScreen(actionEvent, "/gui/addExtraIngredient.fxml");
    }

    /**
     * This method launches the selection screen when clicked on the the "Select" button.
     *
     * @param actionEvent
     */
    public void launchSelectionScreen(javafx.event.ActionEvent actionEvent) {
        selectionScreen(actionEvent, "/gui/selection.fxml");
    }



}