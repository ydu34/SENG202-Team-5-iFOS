package seng202.group5;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Recipe class records all the recipes along with the steps that are stored in the database.
 *
 * @author Shivin Gaba
 */

public class Recipe {

    /**
     * Name of each recipe in the database
     **/
    private String name;
    /**
     * All the steps involve in the recipe
     **/
    private String recipeText;
    /**
     * Hash map for all the ingredients and its quantity
     **/
    private HashMap<Ingredient, Integer> ingredientsAmount;

    /**
     * The number of the non vegan ingredients
     **/
    private boolean veganStatus;
    /**
     * The number of the non vegetarian ingredients
     **/
    private boolean vegeterianStatus;
    /**
     * The number of the non gluten-free ingredients
     **/
    private boolean glutenfreeStatus;
    /**
     * The IngredientList will contain all the ingredients used in a particular recipe
     */

    Recipe(String tempName, String tempRecipeText)
    {
        name = tempName;
        recipeText = tempRecipeText;
        veganStatus = true;
        vegeterianStatus = true;
        glutenfreeStatus = true;
        ingredientsAmount = new HashMap<Ingredient, Integer>();

    }

    Recipe(String tempName, String tempRecipeText, HashMap<Ingredient, Integer> tempIngredientsAmount)
    {
        name = tempName;
        recipeText = tempRecipeText;
        veganStatus = false;
        vegeterianStatus = false;
        glutenfreeStatus = false;
        ingredientsAmount = tempIngredientsAmount;

    }

    /**
     * Returns the name of the recipe
     **/
    public String getName() {
        return name;

    }

    /**
     * @return true when the recipe is vegan
     */

    public boolean getVeganStatus() {
        return veganStatus;
    }

    /**
     * @return true when the recipe is vegetarian
     */
    public boolean isvegeterianStatus() {
        return vegeterianStatus;
    }

    /**
     * @return true when the recipe is gluten free
     */
    public boolean isglutenfreeStatus() {
        return glutenfreeStatus;
    }

    /**
     * Returns all the step in written in a particular recipe
     **/
    public String getReceipeText() {
        return recipeText;
    }

    /**
     * This function adds the specified amount of ingredient that needs to be added in the recipe.
     *
     * @param someIngredient ingredient that needs to be added
     * @param quantity       the amount of someIngredient
     */
    public void addIngredient(Ingredient someIngredient, int quantity) {

        Integer amount = ingredientsAmount.get(someIngredient);
        if (amount == null) {
            ingredientsAmount.put(someIngredient, quantity);
        } else {
            ingredientsAmount.put(someIngredient, amount + quantity);
        }
        if (!someIngredient.getVegan()) {
            veganStatus = false;
            System.out.println("Recipe is not vegan anymore");
        }
        if (!someIngredient.getVegetarian()) {
            vegeterianStatus = false;
            System.out.println("Recipe is not vegetarian anymore");
        }
        if (!someIngredient.getGlutenFree()) {
            glutenfreeStatus = false;
            System.out.println("Recipe has Gluten in it");
        }
    }

    /**
     * This function totally removes the specified ingredient form the recipe and returns the boolean accordingly.
     *
     * @param someIngredient ingredient that needs to be removed
     * @return True if the removal of the ingredient was successful, else returns false
     */
    public boolean removeIngredient(Ingredient someIngredient, int quantity) {
        boolean removed = false;

        if (ingredientsAmount.containsKey(someIngredient)) {
            ingredientsAmount.remove(someIngredient);
            for (int i = 0; i < ingredientsAmount.size(); i++) {
                boolean gf = someIngredient.getGlutenFree();
                boolean vegan = someIngredient.getVegan();
                boolean vegetarian = someIngredient.getVegetarian();
                if (!gf) {
                    glutenfreeStatus = false;
                }
                if (!vegan) {
                    veganStatus = false;
                }
                if (!vegetarian) {
                    vegeterianStatus = false;
                }
            }
            removed = true;
        }
        return removed;
    }

    /**
     * This function edits the quantity of the specified ingredient and returns the boolean accordingly.
     *
     * @param someIngredient ingredient whose quantity needs to be edited
     * @param quantity       amount by which the quantity needs to be edited
     * @return true if the editing the quantity of that ingredient was success else returns false
     */
    public boolean editRecipe(Ingredient someIngredient, int quantity) {
        boolean edited = false;
        Integer amount = ingredientsAmount.get(someIngredient.getId());
        if (amount != null) {
            if (ingredientsAmount.containsKey(someIngredient.getId()) && quantity >= 1) {
                ingredientsAmount.put(someIngredient, quantity);
                edited = true;
            }
        }
        return edited;
    }

    /**
     * This method checks if the nonVeganIngredient ingredient parameter is 0 or 1.
     *
     * @return True if the recipe is vegan else False
     */
    public boolean getVegan() {
        return veganStatus;
    }

    /**
     * This method checks if the nonVegetarianIngredient  ingredient parameter is 0 or 1.
     *
     * @return True if the recipe is vegetarian else False
     */
    public boolean getVegetarian() {
        return vegeterianStatus;
    }

    /**
     * This method checks if the nonGlutenFreeIngredient ingredient parameter is 0 or 1.
     *
     * @return True if the recipe is glutenFree else False
     */
    public boolean getGlutenfree() {
        return glutenfreeStatus;
    }

    public HashMap getIngredientAmount(){
        return ingredientsAmount;
    }

}
