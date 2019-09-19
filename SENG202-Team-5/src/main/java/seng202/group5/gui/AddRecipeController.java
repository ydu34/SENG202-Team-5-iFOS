package seng202.group5.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seng202.group5.AppEnvironment;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddRecipeController extends GeneralController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField makingPriceField;

    @FXML
    private TextField totalPriceField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<Ingredient> ingredientsTable;

    @FXML
    private TableColumn<Ingredient, String> ingredientCol;

    @FXML
    private TableColumn<Ingredient, String> quantityCol;

    @FXML
    private Button addIngredientButton;

    private AppEnvironment appEnvironment;

    private MenuItem item;

    @FXML
    public void pseudoInitialize() {
        newRecipe();
        List<Ingredient> ingredients = new ArrayList<>(item.getRecipe().getIngredientsAmount().keySet());
        HashMap<Ingredient, Integer> quantities = item.getRecipe().getIngredientsAmount();
        ingredientCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(data -> {
            int quantity = quantities.get(data.getValue());
            return new SimpleStringProperty(Integer.toString(quantity));
        });
        ingredientsTable.setItems(FXCollections.observableArrayList(ingredients));
    }

    public void newRecipe() {
        item = new MenuItem();


    }

    public void saveRecipe() {
        String name = nameField.getText();
        String makingPrice = makingPriceField.getText();
        String totalPrice = totalPriceField.getText();

        closeScreen();

    }

    public void closeScreen() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    public void setAppEnvironment(AppEnvironment input) {
        appEnvironment = input;
    }

    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        AddExtraIngredientController controller =
                (AddExtraIngredientController) changeScreen(actionEvent, "/gui/addExtraIngredient.fxml");
        controller.setMenuItem(item);
        controller.initializeTable();
    }

}
