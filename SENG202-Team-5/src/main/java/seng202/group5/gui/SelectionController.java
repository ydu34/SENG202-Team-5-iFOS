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
import seng202.group5.Recipe;

import java.io.IOException;

public class SelectionController {


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

    public void changeScreen(ActionEvent event, String scenePath, String windowTitle){

        Parent sampleScene = null;
        try {
            sampleScene = FXMLLoader.load(getClass().getResource(scenePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldStage.setTitle(windowTitle);
        oldStage.setScene(new Scene(sampleScene, 821, 628));
    }


    /**
     * Changes screen to the add extra ingredient screen.
     *
     * @param actionEvent The action of clicking or pressing the button.
     */
    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/addExtraIngredient.fxml", "Modifying Item");
    }

    /**
     *This method takes the recipe object as the input which was passed during th launch of the selection screen sets the
     * recipe object.
     * @param
     */

    public void setRecipe(Recipe someRecipe){
        recipe = someRecipe;

    }

    public void setItemName(){
        System.out.println(recipe.getName());
        itemName.setText(recipe.getName());
    }

    public void launchOrderScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/order.fxml", "Order");
    }
}
