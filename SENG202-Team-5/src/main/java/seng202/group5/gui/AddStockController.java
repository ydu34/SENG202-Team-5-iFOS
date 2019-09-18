package seng202.group5.gui;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.Ingredient;
import seng202.group5.Stock;

import java.awt.*;

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


    public void createIngredient(ActiveEvent event) {
        Stock stock = getAppEnvironment().getStock();

        String name = nameField.getText();

        String unit = unitField.getText();

        String category = categoryField.getText();

        Money cost = Money.parse("NZD " + costField.getText());

        int quantity = Integer.parseInt(quantityField.getText());

        Ingredient ingredient = new Ingredient(name, unit, category, cost);

        stock.addNewIngredient(ingredient, quantity);

    }


}
