package seng202.group5;

public class MenuItem {

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


    public void updateCost(float newCost) {

    }

    public void addStock(Map<Ingredient, int> ingredientList) {

    }

    public void removeStock(Map<Ingredient, int> ingredientList) {

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
}
