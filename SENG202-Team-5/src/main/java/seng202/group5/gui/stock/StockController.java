package seng202.group5.gui.stock;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Ingredient;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * A controller for managing the stock screen
 *
 * @author Shivin Gaba, Michael Morgoun
 */
public class StockController extends GeneralController {


    @FXML
    private TableView<Ingredient> stockTable;

    @FXML
    TableColumn<Ingredient, String> columnCost;
    @FXML
    private TableColumn<Ingredient, String> columnID;
    @FXML
    private TableColumn<Ingredient, String> columnIngredient;
    @FXML
    private TableColumn<Ingredient, Integer> columnQuantity;
    @FXML
    private TableColumn<Ingredient, String> columnCategory;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button removeButton;

    @FXML
    private TextField ingredientSearchField;

    @FXML
    private Label warningLabel;

    private HashMap<String, Integer> quantities;

    /**
     * An initializer for this controller
     */
    @Override
    public void pseudoInitialize() {
        super.pseudoInitialize();
        // Listener for the ingredientSearchField to only accept numbers
        ingredientSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([\\-':]*\\w*)*")) {
                ingredientSearchField.setText(oldValue);
            }
            updateVisibleIngredients();
        });

        stockTable.getItems().clear();
        warningLabel.setText("");
        addButton.setDisable(false);
        modifyButton.setDisable(false);
        removeButton.setDisable(false);


        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(
                getAppEnvironment().getStock().getIngredients().values());

        quantities = getAppEnvironment().getStock().getIngredientStock();

        columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        columnIngredient.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        columnQuantity.setCellValueFactory(data -> {
            int quantity = quantities.get(data.getValue().getID());
            return new SimpleIntegerProperty(quantity).asObject();
        });

        stockTable.setItems(ingredients);
        stockTable.getSortOrder().add(columnID);
        stockTable.sort();

        try {
            ///getAppEnvironment().getOrderManager().getOrder().resetStock(getAppEnvironment().getStock());

            if (!getAppEnvironment().getOrderManager().getOrder().getOrderItems().isEmpty()) {
                warningLabel.setText("Can not Add/Modify/Remove Stock when Order is in progress.");
                addButton.setDisable(true);
                modifyButton.setDisable(true);
                removeButton.setDisable(true);
            }
        } catch (NoOrderException e) {
            e.printStackTrace();
        }
    }

    public void updateVisibleIngredients() {
        stockTable.getItems().clear();

        Collection<Ingredient> ingredientIDs = getAppEnvironment().getStock().getIngredients().values();
        String searchID = ingredientSearchField.getText().toLowerCase();
        for (Ingredient ingredient : ingredientIDs) {
            if (ingredient.getName().toLowerCase().matches(".*" + searchID + ".*") ||
                    ingredient.getID().matches(".*" + searchID + ".*")) {
                stockTable.getItems().add(ingredient);
            }
        }
    }

    public void initialiseScreen(String setTitle, Ingredient ingredient, String quantity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/addStock.fxml"));
            Parent root = loader.load();

            AddStockController controller = loader.getController();
            controller.setStock(getAppEnvironment().getStock());

            Stage stage = new Stage();
            stage.setTitle(setTitle);
            stage.setScene(new Scene(root, 600, 200));
            stage.initModality(Modality.APPLICATION_MODAL);

            controller.setQuantity(quantity);
            controller.setIngredient(ingredient);
            controller.setAppEnvironment(getAppEnvironment());
            controller.pseudoInitialize();

            // Automatic refresh of the table
            stage.showAndWait();
            pseudoInitialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the add ingredient to stock screen
     *
     * @param event an event that caused this to happen
     */
    @FXML
    public void addIngredient(ActionEvent event) {
        String quantity = "";
        initialiseScreen("New Ingredient", null, quantity);
    }

    @FXML
    public void modifyIngredient(ActionEvent event) {
        Ingredient currentSelected = stockTable.getSelectionModel().getSelectedItem();

        try {
            String quantity = quantities.get(currentSelected.getID()).toString();
            initialiseScreen("Modify " + currentSelected.getName(), currentSelected, quantity);
        } catch (Exception e) {
            warningLabel.setText("Please select an item to modify.");
        }
    }

    @FXML
    public void removeIngredient(ActionEvent event) {
        try {
            Ingredient item = stockTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/confirmRemove.fxml"));
            Parent root = loader.load();

            RemoveStockController controller = loader.getController();
            controller.setIngredient(item);
            controller.setStock(getAppEnvironment().getStock());

            Stage stage = new Stage();
            stage.setTitle("Remove Ingredient");
            stage.setScene(new Scene(root, 600, 200));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            controller.setAppEnvironment(getAppEnvironment());
            controller.pseudoInitialize();

            stage.showAndWait();

            pseudoInitialize();
        } catch (Exception e) {
            warningLabel.setText("Please select an item to remove.");
        }
    }

}
