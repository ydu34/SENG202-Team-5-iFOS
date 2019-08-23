package seng202.group5;

import java.util.HashMap;


/**
 * The Stock holds all ingredients in the database and methods to modify those ingredients.
 *
 * @author Michael Morgoun
 */
public class Stock {


    private HashMap<String, Integer> ingredientStock;


    /**
     * Ingredient stock getter.
     *
     * @return The HashMap ingredientStock
     */
    public HashMap<String, Integer> getIngredientStock() {
        return ingredientStock;
    }


    /**
     * Returns a specific ingredients quantity.
     *
     * @param id The ID of the specific ingredient
     * @return The quantity of the ingredient
     */
    public int getIngredientQuantity(String id) {
        if (ingredientStock.containsKey(id)) {
            return ingredientStock.get(id);
        } else {
            return 0;
        }
    }


    /**
     * The builder for the Stock object.
     *
     * @param tempIngredientStock An initial stock for the object, leave empty if there is none.
     */
    public Stock(HashMap<String, Integer> tempIngredientStock) {
        ingredientStock = tempIngredientStock;
    }


    /**
     * The builder for the Stock object if there is no initial stock.
     */
    public Stock() {
        ingredientStock = new HashMap<String, Integer>();
    }


    /**
     * Adds an ingredient to the stock with a given id, unit, category and quantity.
     *
     * @param id       The unique ID of the ingredient.
     * @param unit     Which unit the ingredient is measured in (e.g kg, ml, L, ... ).
     * @param category The category of the ingredient (e.g. Meat, Vegetable, fruit, ... ).
     * @param quantity The initial quantity of the ingredient, leave empty if 0.
     */
    public void addIngredient(String id, String unit, String category, int quantity) {

    }


    /**
     * Adds an ingredient to the stock with a given ID, unit, category and with a quantity initialised to 0.
     *
     * @param id       The unique ID of the ingredient.
     * @param unit     Which unit the ingredient is measured in (e.g kg, ml, L, ... ).
     * @param category The category of the ingredient (e.g. Meat, Vegetable, fruit, ... ).
     */
    public void addIngredient(String id, String unit, String category) {
        int quantity = 0;
    }


    /**
     * Modifies the quantity of an ingredient already in the stock by changing the parameter quantity to the current
     * quantity. Returns a boolean true or false as to whether it was successful or not.
     *
     * @param id       The unique ID of the ingredient.
     * @param quantity The new quantity of that ingredient.
     * @return A boolean true/false on whether the change was successful.
     */
    public boolean modifyQuantity(String id, int quantity) {
        if (ingredientStock.containsKey(id)) {
            ingredientStock.replace(id, quantity);
            return true;
        } else {
            return false;
        }
    }

}
