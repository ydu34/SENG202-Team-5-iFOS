package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.DietEnum;
import seng202.group5.Ingredient;
import seng202.group5.MenuItem;
import seng202.group5.Recipe;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

/**
 * The OrderController includes all the methods related to the every button on the order screen.
 *
 * @author Shivin Gaba
 */
public class OrderController {

    public Recipe testRecipe;

    @FXML
    private Button launchInvoiceButton;

    @FXML
    private Button launchStockButton;

    @FXML
    private Button launchAdminButton;

    @FXML
    private Button launchHistoryButton;

    @FXML
    private Text ingredientText;

    @FXML
    private Button itemButton;
    private MenuItem item;
    @FXML
    private Text totalCostDisplay;
    private String ingredient;

    /**
     * this method is juts a test as we are still trying to figure out how to convert the menuitem from the recipe xml to the recipe object.
     * The method below adds makes a recipe object, adds ingredients to it and the loop over the hash map <Ingredient, Integer> and displays the
     * the list of all the ingredients present in that recipe under on the order screen under the ingredients method and the also return the recipe.
     */
    public Recipe make_object() {

        ingredient = "";
        testRecipe = new Recipe("Vege burger", "Steps to make pad thai");
        item = new MenuItem("Vege BUrger",testRecipe, Money.parse("NZD 5"), "1221", true);
        HashSet<DietEnum> ingredientInfo = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
        }};
        Ingredient chickenpatty = new Ingredient("chicken", "kg", "meat", "12", Money.parse("NZD 10"), ingredientInfo);
        Ingredient cheese = new Ingredient("cheese", "kg", "dairy", "12", Money.parse("NZD 5"), ingredientInfo);
        testRecipe.addIngredient(chickenpatty, 1);
        testRecipe.addIngredient(cheese, 1);
        for (Map.Entry<Ingredient, Integer> entry : testRecipe.getIngredientsAmount().entrySet()) {
            Ingredient ingredientObject = entry.getKey();
            Integer value = entry.getValue();
            ingredient += ingredientObject.getName() + "(" + value + ")\n";
        }
        return testRecipe;

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
     * This method is called when any of the button (invoice, history,stock or admin are clicked on the order the screen)
     *
     * @param event
     * @param scenePath
     */
    public void changeScreen(ActionEvent event, String scenePath) {
        Parent sampleScene = null;
        try {
            sampleScene = FXMLLoader.load(getClass().getResource(scenePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldStage.setScene(new Scene(sampleScene, 821, 628));
    }

    /**
     * This method launches the invoice screen when clicked on the "Invoice" button
     *
     * @param actionEvent
     */
    public void launchInvoiceScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/invoice.fxml");
    }


    /**
     * This method launches the stock screen when clicked on the "Stock" button
     *
     * @param actionEvent
     */
    public void launchStockScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/stock.fxml");
    }


    /**
     * This method launches the admin screen when clicked on the "Admin" button
     *
     * @param actionEvent
     */
    public void launchAdminScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/admin.fxml");
    }


    /**
     * This method launches the history screen when clicked on the "History" button.
     *
     * @param actionEvent
     */
    public void launchHistoryScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/history.fxml");
    }

    /**
     * This method calls the make_object() method and sets the text in the Ingredient panel to the list of ingredient
     * present in the recipe  and also updates the totalCostDisplay to the selling cost of that menuitem.
     */
    @FXML
    public void getIngredients() {
        make_object();
        ingredientText.setText(ingredient);
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