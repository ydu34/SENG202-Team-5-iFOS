/**
 * @author Shivin Gaba, Michael Morgoun
 */
package seng202.group5.gui;

import javafx.beans.property.SimpleStringProperty;
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
import seng202.group5.information.Ingredient;

import java.io.IOException;
import java.util.HashMap;

public class StockController extends GeneralController {


    @FXML
    private TableView<Ingredient> stockTable;

    @FXML
    private TableColumn<Ingredient, String> rowID;

    @FXML
    private TableColumn<Ingredient, String> rowIngredient;

    @FXML
    private TableColumn<Ingredient, String> rowQuantity;

    @FXML
    private TableColumn<Ingredient, String> rowCategory;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button removeButton;

    @FXML
    private Label warningLabel;

    private HashMap<String, Integer> quantities;

    @Override
    public void pseudoInitialize() {
        warningLabel.setText("");

        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(
                getAppEnvironment().getStock().getIngredients().values());

        quantities = getAppEnvironment().getStock().getIngredientStock();

        rowID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        rowIngredient.setCellValueFactory(new PropertyValueFactory<>("name"));
        rowCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        rowQuantity.setCellValueFactory(data -> {
            int quantity = quantities.get(data.getValue().getID());
            return new SimpleStringProperty(Integer.toString(quantity));
        });

        stockTable.setItems(ingredients);
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
            controller.pseudoInitialize();

            // Automatic refresh of the table
            stage.showAndWait();
            pseudoInitialize();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            warningLabel.setText("Please select an item before modifying.");
        }
    }

}
