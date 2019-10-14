package seng202.group5.gui.stock;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Ingredient;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * A controller for managing the stock screen.
 *
 * @author Shivin Gaba, Michael Morgoun
 */
public class StockController extends GeneralController {

    /**
     * The TableView for the visualisation of stock.
     */
    @FXML
    private TableView<Ingredient> stockTable;

    /**
     * The column for item cost.
     */
    @FXML
    private TableColumn<Ingredient, String> columnCost;

    /**
     * The column for item ID.
     */
    @FXML
    private TableColumn<Ingredient, String> columnID;

    /**
     * The column for item name.
     */
    @FXML
    private TableColumn<Ingredient, String> columnIngredient;

    /**
     * The column for item quantity.
     */
    @FXML
    private TableColumn<Ingredient, Integer> columnQuantity;

    /**
     * The column for item category.
     */
    @FXML
    private TableColumn<Ingredient, String> columnCategory;

    /**
     * The button for adding new stock.
     */
    @FXML
    private Button addButton;

    /**
     * The button for modifying existing stock.
     */
    @FXML
    private Button modifyButton;

    /**
     * The button for removing stock.
     */
    @FXML
    private Button removeButton;

    /**
     * The text field for searching the stock.
     */
    @FXML
    private TextField ingredientSearchField;

    /**
     * A warning label in case the stock can't be modified.
     */
    @FXML
    private Text warningLabel;

    /**
     * A HashMap for the quantities of ingredients.
     */
    private HashMap<String, Integer> quantities;

    /**
     * An initializer for this controller
     */
    @Override
    public void pseudoInitialize() {
        super.pseudoInitialize();
        // Listener for the ingredientSearchField to only accept numbers
        ingredientSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([\\-': ]*\\w*)*")) {
                ingredientSearchField.setText(oldValue);
            }
            updateVisibleIngredients();
        });

        // Clearing tables and labels
        stockTable.getItems().clear();
        warningLabel.setText("");
        addButton.setDisable(false);
        modifyButton.setDisable(false);
        removeButton.setDisable(false);

        // Initialising the TableView
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

        // If the order is in progress, the stock can't be modified
        if (!getAppEnvironment().getOrderManager().getOrder().getOrderItems().isEmpty()) {
            warningLabel.setFill(Color.RED);
            warningLabel.setText("Can not Add/Modify/Remove Stock when Order is in progress.");
            addButton.setDisable(true);
            modifyButton.setDisable(true);
            removeButton.setDisable(true);
        } else {
            // Mouse listener for the context menu
            initialiseContextMenu();
        }
    }

    /**
     * Updates the visible ingredients, removing those that aren't matched in the search field.
     */
    private void updateVisibleIngredients() {
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

    /**
     * Initialises the context menu for the stock table.
     */
    private void initialiseContextMenu() {
        // Creating the ContextMenu and MenuItems
        ContextMenu cm = new ContextMenu();
        MenuItem modifyItem = new MenuItem("Modify");
        MenuItem removeItem = new MenuItem("Remove");
        cm.getItems().add(modifyItem);
        cm.getItems().add(removeItem);

        // Creating the Handler for MouseEvents
        // Shows cm if right clicked, or goes straight to modify if double clicked.
        stockTable.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                cm.show(stockTable, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2 &&
                    stockTable.getSelectionModel().getSelectedItem() != null) {
                modifyIngredient();
            } else {
                cm.hide();
            }
        });

        // Modifies ingredient if selected.
        modifyItem.setOnAction(actionEvent -> modifyIngredient());

        // Deletes ingredient if selected.
        removeItem.setOnAction(actionEvent -> removeIngredient());
    }

    /**
     * Initialises both the Add and Modify screens.
     *
     * @param setTitle   A title that is set to "Create a new Ingredient" or "Modifing <ingredient>"
     * @param ingredient The ingredient to be modified.
     * @param quantity   The quantity of that ingredient.
     */
    private void initialiseScreen(String setTitle, Ingredient ingredient, String quantity) {
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

        }
    }

    /**
     * Opens the add ingredient to stock screen.
     */
    @FXML
    public void addIngredient() {
        String quantity = "";
        initialiseScreen("New Ingredient", null, quantity);
    }

    /**
     * Opens the modify ingredient screen.
     */
    @FXML
    public void modifyIngredient() {
        Ingredient currentSelected = stockTable.getSelectionModel().getSelectedItem();

        try {
            String quantity = quantities.get(currentSelected.getID()).toString();
            initialiseScreen("Modify " + currentSelected.getName(), currentSelected, quantity);
        } catch (Exception e) {
            warningLabel.setText("Please select an item to modify.");
        }
    }

    /**
     * Opens a prompt asking if the selected item will be deleted or not.
     * If no item is selected, a warning label is shown.
     */
    @FXML
    public void removeIngredient() {
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
