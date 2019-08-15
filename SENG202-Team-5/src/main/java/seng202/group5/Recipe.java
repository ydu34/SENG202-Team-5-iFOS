package seng202.group5;

import java.util.HashMap;

/**
 * The Recipe class records all the recipes alog with the steps that are stored in the database.
 * @author Shivin Gaba
 */

public class Recipe {

    /** Name of each recipe in the database **/
    private String name;
    /** All the steps invloved in the recipe **/
    private String recipeText;
    /** Hash map for all the ingredients and its quantity **/
    private HashMap<Ingredient, Integer> IngredientsAmount;

    Recipe(String tempName, String tempRtext){
        tempName = name;
        tempRtext = recipeText;
    }

    /**
    Returns the name of the recipe
     **/
    public String getName() {
        return name;

    }

    /**
    Returns all the step in written in a particular recipe
     **/

    public String getReceipeText() {
        return recipeText;
    }

}
