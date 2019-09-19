package seng202.group5.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seng202.group5.Ingredient;
import seng202.group5.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class AddExtraIngredientController extends GeneralController {

    @FXML
    private Button launchSelectionScreenButton;

    @FXML
    private Button confirmItemButton;

    @FXML
    private MenuItem selectedItem;

    @FXML
    private TableView<Ingredient> ingredientsTable;

    @FXML
    private TableColumn<Ingredient, String> rowID = new TableColumn<>("ID");

    @FXML
    private TableColumn<Ingredient, String> rowIngredientName = new TableColumn<>("ingredientName");

    @FXML
    private TableColumn<Ingredient, String> rowQuantity =  new TableColumn<>("quantity");

    @FXML
    private TableColumn<Ingredient, String> rowCategory = new TableColumn<>("category");

    private Set<Ingredient> selectedIngredientSet;

    private ObservableList<Ingredient> itemIngredients;

    @Override
    public void pseudoInitialize() {
        HashMap<String, Integer> quantities = getAppEnvironment().getStock().getIngredientStock();
        rowID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        rowIngredientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        rowQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        rowCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        rowQuantity.setCellValueFactory(data -> {
            int quantity = quantities.get(data.getValue().getID());
            return new SimpleStringProperty(Integer.toString(quantity));
        });

        initializeSelectedIngredients();
        initializeRemainingIngredients();
        ingredientsTable.setItems(itemIngredients);
        }



    /**
     * Updates the given item's ingredients to match what is selected in the GUI and returns to the Order screen.
     */
    public void updateItemIngredients(javafx.event.ActionEvent actionEvent) {
        //TODO Implement for when the extra ingredients are confirmed.
        System.out.println("Implement me!");
    }

    /**
     * Takes the selected MenuItem and adds its ingredients required to the tableview.
     */
    public void initializeSelectedIngredients() {
        selectedIngredientSet = selectedItem.getRecipe().getIngredientsAmount().keySet();
        itemIngredients = FXCollections.observableArrayList(
                selectedIngredientSet);
    }

    /**
     * Takes the Stock and adds each ingredient that doesn't exist in the table view into it.
     * Furthermore adds ingredients with 0 units left, but prevents the user from adding it to the MenuItem.
     */
    public void initializeRemainingIngredients() {
        Collection<Ingredient> allIngredientList = getAppEnvironment().getStock().getIngredients().values();
        ArrayList<Ingredient> ingredientsNotInTable = new ArrayList<>();
        for (Ingredient ingredient : allIngredientList) {
            System.out.println(ingredient);
            if (!selectedIngredientSet.contains(ingredient)) {
                itemIngredients.add(ingredient);
            }
        }
    }

    public void setMenuItem(MenuItem newItem){
        selectedItem = newItem;
    }

    public void launchSelectionScreen(javafx.event.ActionEvent actionEvent) { // This does not remember the order
        changeScreen(actionEvent, "/gui/selection.fxml");
    }

}

