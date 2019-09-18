package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.joda.money.Money;

import seng202.group5.AppEnvironment;
import seng202.group5.Ingredient;
import seng202.group5.Stock;


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

    private Stock stock = super.getAppEnvironment().getStock();



    @FXML
    public void createIngredient(ActionEvent actionEvent) {
        String name = nameField.getText();

        String unit = unitField.getText();

        String category = categoryField.getText();

        Money cost = Money.parse("NZD " + costField.getText());

        int quantity = Integer.parseInt(quantityField.getText());

        try {
            Ingredient ingredient = new Ingredient(name, unit, category, cost);
            stock.addNewIngredient(ingredient, quantity);
        } catch (Exception e) {
            warningLabel.setText("Error");
        }

        warningLabel.setText("Created " + name);

    }

}
