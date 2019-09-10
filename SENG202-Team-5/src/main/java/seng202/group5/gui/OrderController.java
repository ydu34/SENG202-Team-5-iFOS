package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
import java.util.Map;

/**
 * The OrderController includes all the methods related to the every button on the order screen.
 *
 * @author Shivin Gaba
 */
public class OrderController extends GeneralController {

    public Recipe testRecipe;

    @FXML
    private Text ingredientText;

    @FXML
    private Button itemButton;
    private MenuItem item;
    private MenuItem item2;
    @FXML
    private Recipe testRecipe2;
    @FXML
    private Text totalCostDisplay;
    private String ingredient;
    @FXML
    private CheckBox vegan;

    @FXML
    private CheckBox vegetarian;

    @FXML
    private CheckBox glutenFree;



    private ArrayList<MenuItem> menuItemsList =new ArrayList<MenuItem>();

    public void checkDietryInfo(ActionEvent event) {
        // get values of checkboxes here
        // call showItems with those vlaues as parameter
    }

    /**
     * Still trying to work this code out. Please dont delete it.
     */
    public void showItems() {
        // work with menuItemsList
        make_object();
        System.out.println(glutenFree.isSelected());
        for(MenuItem i : menuItemsList){
            if(i.getRecipe().getDietaryInformation().contains(DietEnum.GLUTEN_FREE) && glutenFree.isSelected()){
                System.out.println("print its GF");
            }
        }

            }


    /**
     * this method is juts a test as we are still trying to figure out how to convert the menuitem from the recipe xml to the recipe object.
     * The method below adds makes a menuItem object, adds ingredients to it and the loop over the hash map <Ingredient, Integer> and displays the
     * the list of all the ingredients present in that recipe under on the order screen under the ingredients method and the also return the recipe.
     */
    public ArrayList<MenuItem> make_object() {

        ingredient = "";
        testRecipe = new Recipe("Chicken burger", "Steps to make pad thai");
        testRecipe2 = new Recipe("Vege burger", "Steps to make pad thai");
        item = new MenuItem("Chicken Burger",testRecipe, Money.parse("NZD 5"), "1221", true);
        item2 = new MenuItem("Vege Burger",testRecipe2, Money.parse("NZD 7"), "1222", true);
        HashSet<DietEnum> ingredientInfo1 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
        }};
        HashSet<DietEnum> ingredientInfo2 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
          //  add(DietEnum.VEGETARIAN);
        }};
        Ingredient chickenpatty = new Ingredient("chicken", "kg", "meat", "12", Money.parse("NZD 10"), ingredientInfo1);
        Ingredient cheese = new Ingredient("cheese", "kg", "dairy", "12", Money.parse("NZD 5"), ingredientInfo2);
        HashSet<DietEnum> ingredientInfo3 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
           // add(DietEnum.VEGETARIAN);
        }};
        Ingredient vegePatty = new Ingredient("vegetables", "kg", "vege", "12", Money.parse("NZD 10"), ingredientInfo3);
        testRecipe.addIngredient(chickenpatty, 1);
        testRecipe.addIngredient(cheese, 1);
        testRecipe2.addIngredient(cheese, 1);
        menuItemsList.add(item);
        menuItemsList.add(item2);
        // refactor into another method
        for (Map.Entry<Ingredient, Integer> entry : testRecipe.getIngredientsAmount().entrySet()) {
            Ingredient ingredientObject = entry.getKey();
            Integer value = entry.getValue();
            ingredient += ingredientObject.getName() + "(" + value + ")\n";
        }
        return menuItemsList;

    }

    /**
     * This method launches the selection screen for the selected menu item and passes the recipe and object from the
     * from the current class to the the Selection controller class.
     *
     * @param event
     * @param scenePath
     */
    public void selectionScreen(ActionEvent event, String scenePath) {
        Parent selectionScene = null;
        try {
            FXMLLoader selectionLoader = new FXMLLoader(getClass().getResource(scenePath));
            selectionScene = selectionLoader.load();
            SelectionController controller = selectionLoader.getController();
            controller.setRecipe(item.getRecipe());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldStage.setScene(new Scene(selectionScene, 821, 628));

    }

    /**
     * This method calls the make_object() method and sets the text in the Ingredient panel to the list of ingredient
     * present in the recipe  and also updates the totalCostDisplay to the selling cost of that menuitem.
     */
    @FXML
    public void getIngredients() {
        make_object();
        ingredientText.setText(ingredient);
        if(item.getRecipe().getDietaryInformation().contains(DietEnum.GLUTEN_FREE)){
            System.out.println("print its vegetarian");
        }
    //  System.out.println(item.calculateFinalCost().getAmount());

        System.out.println(item.calculateFinalCost().getAmount());
        System.out.println(String.valueOf(item.calculateFinalCost().getAmount()));
        totalCostDisplay.setText(String.valueOf(item.calculateFinalCost().getAmount()));


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