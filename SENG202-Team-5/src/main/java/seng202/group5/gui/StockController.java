/**
 * @author Shivin Gaba, Michael Morgoun
 */
package seng202.group5.gui;

import javafx.beans.property.ReadOnlyStringWrapper;
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
import seng202.group5.Order;
import seng202.group5.Stock;

import java.io.IOException;

public class StockController extends GeneralController {


    @FXML
    private TableView<Ingredient> stockTable;

    @FXML
    private TableColumn rowID;

    @FXML
    private TableColumn rowIngredient;

    @FXML
    private TableColumn rowQuantity;

    @FXML
    private TableColumn rowUnits;

    @FXML
    private TableColumn rowCategory;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button removeButton;

    @Override
    public void pseudoInitialize() {
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(
                getAppEnvironment().getStock().getIngredients().values()
        );
        rowID.setCellValueFactory(new PropertyValueFactory("ID"));
        rowIngredient.setCellValueFactory(new PropertyValueFactory("name"));
        rowUnits.setCellValueFactory(new PropertyValueFactory("unit"));
        rowCategory.setCellValueFactory(new PropertyValueFactory("category"));

        stockTable.setItems(ingredients);
    }

    @FXML
    public void addIngredient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/addStock.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add An Ingredient");
            stage.setScene(new Scene(root, 600, 200));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
