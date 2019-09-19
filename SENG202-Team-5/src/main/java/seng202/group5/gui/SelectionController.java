/**
 *
 */
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
import seng202.group5.MenuItem;
import seng202.group5.Recipe;

import java.io.IOException;

public class SelectionController extends GeneralController {


    @FXML
    public Text costText;
    @FXML
    public Text recipeText;
    Recipe recipe;
    public OrderController order;
    @FXML
    private Text pressedCountText;

    @FXML
    private Button addIngredientButton;

    @FXML
    private Text itemName;

    @FXML
    private Button closeSelectionScreenButton;


    @FXML
    public MenuItem item;


    /**
     * Changes screen to the add extra ingredient screen.
     *
     * @param actionEvent The action of clicking or pressing the button.
     */
    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        AddExtraIngredientsScreen(actionEvent, "/gui/addExtraIngredient.fxml");
    }

    public void AddExtraIngredientsScreen(ActionEvent event, String scenePath) {
        //TODO this should be refactored somehow so it is using the code in GeneralController instead of being a copy
        Parent extraIngredientsScene = null;
        try {
            FXMLLoader extraIngredientsLoader = new FXMLLoader(getClass().getResource(scenePath));
            extraIngredientsScene = extraIngredientsLoader.load();
            AddExtraIngredientController controller = extraIngredientsLoader.getController();
            controller.setMenuItem(item);
            controller.setAppEnvironment(getAppEnvironment());
            controller.pseudoInitialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        double prevHeight = ((Node) event.getSource()).getScene().getHeight();
        double prevWidth = ((Node) event.getSource()).getScene().getWidth();
        oldStage.setScene(new Scene(extraIngredientsScene, prevWidth, prevHeight));

    }


    /**
     *This method takes the recipe object as the input which was passed during th launch of the selection screen sets the
     * recipe object.
     * @param
     */

    public void setMenuItem(MenuItem newItem){
        System.out.println(newItem);
        item = newItem;
        costText.setText(item.calculateFinalCost().toString());
        recipeText.setText(item.getRecipe().getRecipeText());


    }

    public void setItemName(){
        System.out.println(recipe.getName());
        itemName.setText(recipe.getName());
    }

    public void launchOrderScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/order.fxml");
    }
}
