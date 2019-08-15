package seng202.group5;

import java.util.HashMap;

public class MenuItem {
    /**
     * MenuItem holds each menu item for display in the graphical user interface.
     */
    private String name;
    private Recipe recipe;
    private float cost;
    private String ID;


    public MenuItem(String tempName, Recipe tempRecipe, float tempCost, String tempID) {
        name = tempName;
        recipe = tempRecipe;
        cost = tempCost;
        ID = tempID;
    }

    /**
     * Updates the cost of a MenuItem when given a cost.
     *
     * @param newCost The new cost of the item given as a float.
     */
    public void updateCost(float newCost) {

    }

    /**
     * Adds stock to the ingredients list.
     *
     * @param ingredientList Holds values of stock.
     */
    public void addStock(HashMap<Ingredient, Integer> ingredientList) {

    }

    /**
     * Removes stock of the MenuItem from the ingredients list.
     *
     * @param ingredientList Holds values of stock.
     */
    public void removeStock(HashMap<Ingredient, Integer> ingredientList) {

    }

    public String getName() {
        return name;
    }


    public float getCost() {
        return cost;
    }

    public String getID() {
        return ID;
    }

    public void setCost(float tempCost) {
        cost = tempCost;
    }
}
