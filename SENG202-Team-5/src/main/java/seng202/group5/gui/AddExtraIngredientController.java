package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seng202.group5.Ingredient;
import seng202.group5.MenuItem;
import seng202.group5.Stock;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class AddExtraIngredientController {

    @FXML
    private GridPane gridContainer;
    @FXML
    private Button launchSelectionScreenButton;

    private List<Label> ingredientNameLabels;
    private Stock stock;
    private MenuItem menuItem;
    private List<Label> ingredientQuantityLabels;

    public void changeScreen(ActionEvent event, String scenePath, String windowTitle){
        Parent newScene = null;
        try {

            newScene = FXMLLoader.load(getClass().getResource(scenePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldStage.setTitle(windowTitle);
        oldStage.setScene(new Scene(newScene, 821, 628));
    }

//    public AddExtraIngredientController(Stock tempStock, MenuItem tempItem) {
//        stock = tempStock;
//        menuItem = tempItem;
//    }

    /**
    * Takes Stock and the MenuItem selected and iterates through finding each ingredient
     * and adds it to the addExtraIngredient.fxml file.
     */
//    public void initialize(Stock stock, MenuItem item) {
//        HashMap<String, Ingredient> allIngredients = null;
//        HashSet<String> menuItemNames = new HashSet<>();
//        ingredientNameLabels = new ArrayList<>();
//        ingredientQuantityLabels = new ArrayList<>();
//
//        for (Ingredient ingredient : item.getRecipe().getIngredientsAmount().keySet()) {
//            menuItemNames.add(ingredient.getName());
//            Label ingredientNameLabel = new Label(ingredient.getName());
//            ingredientNameLabels.add(ingredientNameLabel);
//
//            Integer quantity = stock.getIngredientQuantity(ingredient.getId());
//            Label ingredientQuantityLabel = new Label(quantity.toString());
//            ingredientQuantityLabels.add(ingredientQuantityLabel);
//            gridContainer.getChildren().add(ingredientNameLabel);
//            gridContainer.getChildren().add(ingredientQuantityLabel);
//        }
//
//        allIngredients = stock.getIngredients();
//        for (Ingredient ingredient : allIngredients.values()) {
//            String ingredientName = ingredient.getName();
//            if (menuItemNames.contains(ingredientName) == false) {
//                Label ingredientNameLabel = new Label(ingredient.getName());
//                ingredientNameLabels.add(ingredientNameLabel);
//                gridContainer.getChildren().add(ingredientNameLabel);
//            }
//        }
//    }


    public void launchSelectionScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/selection.fxml", "Select an Item");
    }

}


