package seng202.group5;

import org.joda.money.Money;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains methods to update the stock, removes the stock , calculates the making and selling price for the menu item.
 *
 * @author Shivin Gaba, James Kwok
 */
public class MenuItem {

    /**
     * Name of the dish/item on the menu.
     */
    private String itemName;
    /**
     * The recipe object that has the list of the ingredients involved to make that menu item
     */
    private Recipe recipe;
    /**
     * The actual cost it costs the producer to produce a particular recipe
     */
    private double productionCost;
    /**
     * The final cost of the menu item that after the mark up
     */
    private double sellingCost;
    /**
     * The unique id related to every item on the menu
     */
    private String id;
    /**
     * Whether or not this item is in the menu
     */
    private boolean inMenu;
    /**
     * The ingredient that can be added or removed from the menu item.
     */
    private Ingredient someIngredient;
    /**
     * The amount specifies how much of a particular ingredient needs to be removed or added.
     */
    private int amount;

    private boolean vegan;
    /**
     * The number of the non vegetarian ingredients
     **/
    private boolean vegeterian;
    /**
     * The number of the non gluten-free ingredients
     **/
    private boolean glutenfree;
    /**
     * The IngredientList will contain all the ingredients used in a particular recipe
     */

     /**
     * @param someItemName is the name of an item on the menu
     * @param someRecipe   is the recipe for a an item on the menu
     * @param makingCost   is the actual cost of production
     * @param markupCost   is the final selling cost of the menu item
     * @param uniqueId     is the unique id related to each menu item
     */

    MenuItem(String someItemName, Recipe someRecipe, double makingCost,
             double markupCost, String uniqueId, boolean someInMenu) {
        itemName = someItemName;
        recipe = someRecipe;
        productionCost = makingCost;
        sellingCost = markupCost;
        id = uniqueId;
        inMenu = someInMenu;
    }

    MenuItem(String someItemName, Recipe someRecipe, double makingCost, double markupCost, String uniqueId, Ingredient randomIngredient, int someAmount) {

        itemName = someItemName;
        recipe = someRecipe;
        productionCost = makingCost;
        sellingCost = markupCost;
        id = uniqueId;
        inMenu = false;
        someIngredient = randomIngredient;
        amount = someAmount;
        this.vegan = recipe.isVegan();
        this.vegeterian = recipe.isVegetarian();
        this.glutenfree = recipe.isGlutenFree();

    }

    /**
     * This method calls the addIngredient method in the Recipe class which takes the ingredient object and the amount as the input
     * and modifies the ingredientsAmount hash map accordingly.
     */
    public void addStock() {
        recipe.addIngredient(someIngredient, amount);
    }

    /**
     * This method calls the removeIngredient method in the Recipe class which takes the ingredient object and the amount as the input
     * and modifies the ingredientsAmount hash map accordingly.This method is only called when a particular ingredient has to be fully
     * omitted from the recipe.
     */

    public void removeStock() {
        recipe.removeIngredient(someIngredient, amount);
    }

    /**
     * This method calls the editIngredient method in the Recipe class which takes the ingredient object and the amount as the input
     * and modifies the ingredientsAmount hash map accordingly.
     */
    public void editStock() {
        recipe.editRecipe(someIngredient, amount);
    }


    /**
     * This method runs a loop over the ingredientAmount hash map and calculates the total cost of making a menu item in NZD
     *
     * @return the making cost of the recipe in the form of the money object
     */
    public Money calculateMakingCost() {
        double recipeMakingCost = 0;
        HashMap<Ingredient, Integer> ingredients = recipe.getIngredientsAmount();
        for (Map.Entry<Ingredient, Integer> eachIngredient : ingredients.entrySet()) {
            Ingredient ingredient = eachIngredient.getKey();
            Integer amount = eachIngredient.getValue();
            recipeMakingCost += amount * ingredient.getCost();
        }
        return Money.parse(String.format("NZD %.2f", recipeMakingCost));
    }

    /**
     * This function adds a markup of 2.5 times the actual making cost of a menu item.
     *
     * @return the selling cost of the menu item in the form of the Money object in NZD
     */
    public Money calculateFinalCost() {
        Money finalCost = calculateMakingCost();
        return (finalCost.multipliedBy(2.5, RoundingMode.DOWN));

    }

    public boolean isInMenu() {
        return inMenu;
    }

    public String getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Recipe getRecipe() {
        return recipe;
    }


    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isVegeterian() {
        return vegeterian;
    }

    public void setVegeterian(boolean vegeterian) {
        this.vegeterian = vegeterian;
    }

    public boolean isGlutenfree() {
        return glutenfree;
    }

    public void setGlutenfree(boolean glutenfree) {
        this.glutenfree = glutenfree;
    }
}



