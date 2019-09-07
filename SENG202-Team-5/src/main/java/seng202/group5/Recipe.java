package seng202.group5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The Recipe class records all the recipes along with the steps that are stored in the database.
 *
 * @author Shivin Gaba, Yu Duan, Daniel Harris
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
     * Hash map for all the ingredients' string id and its quantity.
     */
    private HashMap<String, Integer> ingredientIDs;

    /**
     * Hash map for all the ingredients and its quantity
     **/
    @XmlTransient
    private HashMap<Ingredient, Integer> ingredientsAmount;

    //TODO is this XmlTransient?
    /**
     * A HashSet to store dietary information about the recipe
     */
    private HashSet<DietEnum> dietaryInformation;

    Recipe() {
    }

    /**
     * The IngredientList will contain all the ingredients used in a particular recipe
     */
    Recipe(String tempName, String tempRecipeText) {
        name = tempName;
        recipeText = tempRecipeText;
        dietaryInformation = new HashSet<>();
        dietaryInformation.addAll(Arrays.asList(DietEnum.values()));
        ingredientsAmount = new HashMap<>();
        ingredientIDs = new HashMap<>();

    }


    Recipe(String tempName, String tempRecipeText, HashMap<Ingredient, Integer> tempIngredientsAmount) {
        name = tempName;
        recipeText = tempRecipeText;
        dietaryInformation = new HashSet<>();
        dietaryInformation.addAll(Arrays.asList(DietEnum.values()));
        ingredientsAmount = tempIngredientsAmount;
        ingredientIDs = new HashMap<>();
    }

    Recipe(String tempName, String tempRecipeText, HashMap<Ingredient, Integer> tempIngredientsAmount, HashMap<String, Integer> tempIngredientIDs) {
        name = tempName;
        recipeText = tempRecipeText;
        ingredientsAmount = tempIngredientsAmount;
        ingredientIDs = tempIngredientIDs;

    }

    /**
     * This function adds the specified amount of ingredient that needs to be added in the recipe.
     *
     * @param someIngredient ingredient that needs to be added
     * @param quantity       the amount of someIngredient
     */
    public void addIngredient(Ingredient someIngredient, int quantity) {

        ingredientsAmount.merge(someIngredient, quantity, Integer::sum);
        dietaryInformation.retainAll(someIngredient.getDietInfo());
    }

    /**
     * This function removes the specified ingredient from the recipe and returns the boolean accordingly.
     *
     * @param someIngredient ingredient that needs to be removed
     * @param quantity the quantity of the ingredient to remove
     * @return True if the removal of the ingredient was successful, else returns false
     */
    public boolean removeIngredient(Ingredient someIngredient, int quantity) {
        boolean removed = false;

        if (ingredientsAmount.containsKey(someIngredient)) {
            if (quantity >= ingredientsAmount.get(someIngredient)) {
                removeIngredient(someIngredient);
            } else {
                ingredientsAmount.replace(someIngredient, ingredientsAmount.get(someIngredient) - quantity);
            }
            removed = true;
        }
        return removed;
    }

    /**
     * Completely removes an ingredient from the recipe
     *
     * @param someIngredient The ingredient to remove
     * @return Whether or not the ingredient was removed
     */
    public boolean removeIngredient(Ingredient someIngredient) {
        if (ingredientsAmount.containsKey(someIngredient)) {
            ingredientsAmount.remove(someIngredient);

            // This section checks if the dietary information can be changed
            for (DietEnum dietType : DietEnum.values()) {
                boolean isOfType = dietaryInformation.contains(dietType);
                if (!isOfType && !someIngredient.getDietInfo().contains(dietType)) {
                    isOfType = true;
                    for (Ingredient ingredient : ingredientsAmount.keySet()) {
                        if (!ingredient.getDietInfo().contains(dietType)) {
                            isOfType = false;
                            break;
                        }
                    }
                    if (isOfType) dietaryInformation.add(dietType);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function edits the quantity of the specified ingredient and returns the boolean accordingly.
     *
     * @param someIngredient ingredient whose quantity needs to be edited
     * @param quantity       amount by which the quantity needs to be edited
     * @return true if the editing the quantity of that ingredient was successful else returns false
     */
    public boolean editRecipe(Ingredient someIngredient, int quantity) {
        boolean edited = false;
        Integer amount = ingredientsAmount.get(someIngredient);
        if (amount != null) {
            if (ingredientsAmount.containsKey(someIngredient) && quantity >= 1) {
                ingredientsAmount.put(someIngredient, quantity);
                edited = true;
            }
        }
        return edited;
    }

    /**
     * Returns the name of the recipe
     * @return Name of the recipe.
     **/

    public String getName() { return name; }


    /**
     * Returns all the step in written in a particular recipe
     **/

    public String getRecipeText() { return recipeText; }


    public HashMap<String, Integer> getIngredientIDs() {
        return ingredientIDs;
    }


    public void setIngredientIDs(HashMap<String, Integer> ingredientIDs) {
        this.ingredientIDs = ingredientIDs;
    }

    public HashMap<Ingredient, Integer> getIngredientsAmount() {
        return ingredientsAmount;
    }

    public void setIngredientsAmount(HashMap<Ingredient, Integer> ingredientsAmount) {
        this.ingredientsAmount = ingredientsAmount;
    }

    public HashSet<DietEnum> getDietaryInformation() {
        return dietaryInformation;
    }

}
