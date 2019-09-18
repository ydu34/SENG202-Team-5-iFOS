package seng202.group5.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import seng202.group5.Ingredient;

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
    private TableColumn<Ingredient, ComboBox> quantityCol;

    @FXML
    public void initialize() {
//        ingredientCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        Map<String, Ingredient> ingredientsMap = getAppEnvironment().getStock().getIngredients();
//        List<Ingredient> ingredients = new ArrayList<Ingredient>(ingredientsMap.values());
//        ingredientsTable.setItems(FXCollections.observableArrayList(ingredients));
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

}
