package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.joda.money.Money;

import seng202.group5.information.Ingredient;
import seng202.group5.logic.Stock;


public class AddStockController extends GeneralController {

    @FXML
    private Button createButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField unitField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField costField;

    @FXML
    private TextField quantityField;

    @FXML
    private Label warningLabel;

    private Stock stock;


    @FXML
    public void createIngredient(ActionEvent actionEvent) {
        try {
            // Getting all the values through the textfields
            String name = nameField.getText();
            String unit = unitField.getText();
            String category = categoryField.getText();
            Money cost = Money.parse("NZD " + costField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            // Attempting to make an ingredient from data collected above
            Ingredient ingredient = new Ingredient(name, unit, category, cost);
            // Adding ingredient to the stock
            stock.addNewIngredient(ingredient, quantity);

            // Closing window
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            warningLabel.setText("Error creating ingredient");
            e.printStackTrace();
        }
    }

    public void setStock(Stock input) {
        stock = input;
    }

}
