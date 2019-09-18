package seng202.group5.gui;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.DietEnum;
import seng202.group5.Ingredient;
import seng202.group5.MenuItem;
import seng202.group5.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
    private Label ingredientText;

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
    private String ingredient;
    @FXML
    private CheckBox vegan;

    @FXML
    private CheckBox vegetarian;

    @FXML
    private CheckBox glutenFree;
    @FXML
    private ArrayList<Button> filteredButtons;


    private ArrayList<MenuItem> allItems;
    private ArrayList<MenuItem> filteredItems;

    @Override
    public void pseudoInitialize() {
        allItems = new ArrayList<>();
        allItems.addAll(getAppEnvironment().getMenuManager().getMenuItems().values());

        item = allItems.get(0);
        ingredient = "";
        showItems(new ActionEvent());
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
     * Still trying to work this code out. Please dont delete it.
     */
    @FXML
    public void showItems(ActionEvent event) {
        ArrayList<MenuItem> itemsToShow = new ArrayList<>();
        itemsToShow = filterItems();
        // work with menuItemsList


    }

    public void printIngredeints(MenuItem someItem){
        ingredient = "";
        for (Map.Entry<Ingredient, Integer> entry : someItem.getRecipe().getIngredientsAmount().entrySet()) {
            Ingredient ingredientObject = entry.getKey();
            Integer value = entry.getValue();
            ingredient += ingredientObject.getName() + "   (" + value + ")\n";
        }

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
        System.out.println(item.getItemName());
        controller.setMenuItem(item);

    }

    /**
     * This method calls the make_object() method and sets the text in the Ingredient panel to the list of ingredient
     * present in the recipe  and also updates the totalCostDisplay to the selling cost of that menuitem.
     */
    @FXML
    public void getIngredients(ActionEvent actionEvent) {
        //make_object();
        MenuItem selectedItem = null;

        if (filteredItems != null) {
            for (MenuItem item : filteredItems) {
                Button btn = (Button) actionEvent.getSource();
                if (item.getItemName() == btn.getText()) {
                    selectedItem = item;
                }
            }
            if (selectedItem != null) {
                printIngredeints(selectedItem);
                ingredientText.setText(ingredient);
                System.out.println(selectedItem.calculateFinalCost().getAmount());
                System.out.println(String.valueOf(selectedItem.calculateFinalCost().getAmount()));
                totalCostDisplay.setText(String.valueOf(selectedItem.calculateFinalCost().getAmount()));
                item = selectedItem;
                System.out.println(item.getItemName());
            }

        }
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