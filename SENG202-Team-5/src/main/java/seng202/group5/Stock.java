package seng202.group5;

import java.util.HashMap;


/**
 * The Stock holds all ingredients in the database and methods to modify those ingredients.
 * @author Michael Morgoun
 */
public class Stock {

    /** The HashMap that holds all ingredients by their ID and their quatities. **/
    private HashMap<String, Integer> ingredientStock;

    /**
     * Adds an ingredient to the stock with a given name, unit, category and quantity.
     * @param name A string name of the ingredient.
     * @param unit The unit of that certain ingredient.
     * @param category The category that the ingredient falls under.
     * @param quantity The starting quantity of the item, leave empty if 0.
     */
    public void addIngredient(String name, String unit, String category, int quantity) {

    }

    /**
     * Adds an ingredient to the stock with a given name, unit, category and with a quantity initialised to 0.
     * @param name A string name of the ingredient.
     * @param unit The unit of that certain ingredient.
     * @param category The category that the ingredient falls under.
     */
    public void addIngredient(String name, String unit, String category) {
        int quantity = 0;
    }

    /**
     * Modifies the quantity of an ingredient already in the stock by adding the parameter quantity to the current
     * quantity. Returns a boolean true or false as to whether it was successful or not.
     * @param ingredient The ingredient that the method will modify.
     * @param quantity The new quantity of the ingredient.
     * @return A boolean representing the success of the quantity change.
     */
    public boolean modifyQuantity(Ingredient ingredient, int quantity) {
        return false;
    }

}
