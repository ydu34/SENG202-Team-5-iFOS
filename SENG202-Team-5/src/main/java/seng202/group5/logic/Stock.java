package seng202.group5.logic;

import seng202.group5.IDGenerator;
import seng202.group5.information.Ingredient;

import javax.xml.bind.annotation.*;
import java.util.HashMap;


/**
 * The Stock holds all ingredients in the database and methods to modify those ingredients.
 *
 * @author Michael Morgoun, Daniel Harris, Yu Duan
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Stock {

    /**
     * An ID generator stored here so that it is kept persistent across the application
     */
    @XmlElement
    private IDGenerator generator = new IDGenerator();

    private HashMap<String, Ingredient> ingredients;
    private HashMap<String, Integer> ingredientStock;

    /**
     * The builder for the Stock object.
     *
     * @param tempIngredients     A mapping from the ID of each ingredient to their instance
     * @param tempIngredientStock An initial stock for the object, leave empty if there is none.
     */
    public Stock(HashMap<String, Ingredient> tempIngredients, HashMap<String, Integer> tempIngredientStock) {
        ingredients = tempIngredients;
        ingredientStock = tempIngredientStock;
    }

    /**
     * The builder for the Stock object if there is no initial stock.
     */
    public Stock() {
        ingredients = new HashMap<>();
        ingredientStock = new HashMap<>();
    }

    /**
     * Adds an ingredient to the stock with a given id, unit, category and quantity.
     *
     * @param ingredient The ingredient to add
     * @param quantity   The initial quantity of the ingredient, leave empty if 0.
     */
    public void addNewIngredient(Ingredient ingredient, int quantity) {
        if (ingredients.containsKey(ingredient.getID())) {
            ingredientStock.put(ingredient.getID(), ingredientStock.get(ingredient.getID() + quantity));
        } else {
            ingredients.put(ingredient.getID(), ingredient);
            ingredientStock.put(ingredient.getID(), quantity);

        }
    }

    /**
     * Removes an ingredient with the same ID.
     * @param id The ID of the ingredient you want to remove.
     */
    public void removeIngredient(String id) {
        ingredients.remove(id);
        ingredientStock.remove(id);
    }

    /**
     * Adds an ingredient to the stock with a given ID, unit, category and with a quantity initialised to 0.
     *
     * @param ingredient The ingredient to add
     */
    public void addNewIngredient(Ingredient ingredient) {
        int quantity = 0;
        addNewIngredient(ingredient, quantity);
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

    /**
     * Creates a clone of this Stock class for a temporary order stock
     *
     * @return A clone of this stock class
     */
    public Stock clone() {
        HashMap<String, Ingredient> tempIngredients = new HashMap<>(ingredients);
        HashMap<String, Integer> tempStock = new HashMap<>(ingredientStock);
        return new Stock(tempIngredients, tempStock);
    }

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
        return ingredientStock.getOrDefault(id, 0);
    }

    /**
     * Returns the ingredients with their respective IDs.
     * @return A HashMap of current ingredients.
     */
    public HashMap<String, Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Gets an ingredient from an ID.
     * @param id the ID of the ingredient.
     * @return The ingredient with the same ID.
     */
    public Ingredient getIngredientFromID(String id) {
        return ingredients.getOrDefault(id, null);
    }

    public void setIngredientsAndStock(HashMap<String, Ingredient> ingredients,
                                       HashMap<String, Integer> ingredientStock) {
        this.ingredients = ingredients;
        this.ingredientStock = ingredientStock;
    }

}
