package seng202.group5;

import java.util.HashMap;


/**
 * The Stock holds all ingredients in the database and methods to modify those ingredients.
 * @author Michael Morgoun
 */
public class Stock {


    private HashMap<String, Integer> ingredientStock;


    /**
     * Ingredient stock getter.
     * @return The HashMap ingredientStock
     */
    public HashMap<String, Integer> getIngredientStock() {
        return ingredientStock;
    }

    public Stock(HashMap<String, Integer> tempIngredientStock) {
        ingredientStock = tempIngredientStock;
    }


    /**
     * Adds an ingredient to the stock with a given name, unit, category and quantity.
     * @param name
     * @param unit
     * @param category
     * @param quantity
     */
    public void addIngredient(String name, String unit, String category, int quantity) {

    }


    /**
     * Adds an ingredient to the stock with a given name, unit, category and with a quantity initialised to 0.
     * @param name
     * @param unit
     * @param category
     */
    public void addIngredient(String name, String unit, String category) {
        int quantity = 0;
    }


    /**
     * Modifies the quantity of an ingredient already in the stock by adding the parameter quantity to the current
     * quantity. Returns a boolean true or false as to whether it was successful or not.
     * @param ingredient
     * @param quantity
     * @return
     */
    public boolean modifyQuantity(Ingredient ingredient, int quantity) {
        return false;
    }

}
