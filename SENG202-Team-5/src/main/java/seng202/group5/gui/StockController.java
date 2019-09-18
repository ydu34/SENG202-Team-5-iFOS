/**
 * @author Shivin Gaba, Michael Morgoun
 */
package seng202.group5.gui;

import com.sun.javafx.image.impl.General;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group5.Ingredient;
import seng202.group5.Order;
import seng202.group5.Stock;

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
    private TableColumn<Ingredient, String> rowUnits;

    @FXML
    private TableColumn<Ingredient, String> rowCategory;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button removeButton;

    @Override
    public void pseudoInitialize() {
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(
                getAppEnvironment().getStock().getIngredients().values());

        HashMap<String, Integer> quantities = getAppEnvironment().getStock().getIngredientStock();

        rowID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        rowIngredient.setCellValueFactory(new PropertyValueFactory<>("name"));
        rowUnits.setCellValueFactory(new PropertyValueFactory<>("unit"));
        rowCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        rowQuantity.setCellValueFactory(data -> {
            int quantity = quantities.get(data.getValue().getID());
            return new SimpleStringProperty(Integer.toString(quantity));
        });

        stockTable.setItems(ingredients);
    }

    @FXML
    public void addIngredient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/addStock.fxml"));
            Parent root = loader.load();

            AddStockController controller = loader.<AddStockController>getController();
            controller.setStock(getAppEnvironment().getStock());

            Stage stage = new Stage();
            stage.setTitle("Add An Ingredient");
            stage.setScene(new Scene(root, 600, 200));
            stage.initModality(Modality.APPLICATION_MODAL);

            // Automatic refresh of the table
            stage.showAndWait();
            pseudoInitialize();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
