package seng202.group5;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the menu, includes creating recipes and items, and removing items from the menu.
 * @author James Kwok
 */
@XmlRootElement
public class MenuManager {


    private Map<String, MenuItem> itemList;

    /**
     * Creates a new recipe
     *
     * @param ingredients contains all the ingredients IDs and their quantities required for the recipe
     * @param recipeName the recipe's name.
     * @param recipeText  the recipes instructions.
     * @return the newly made Recipe object.
     */
    public Recipe createRecipe(String recipeName, HashMap<Ingredient, Integer> ingredients, String recipeText) {
        return new Recipe(recipeName, recipeText, ingredients);
    }

    /**
     * Creates a menu item and adds it to the item list
     *
     * @param name   the name of the item
     * @param recipe the recipe for this item
     * @param cost   the cost of this item
     * @param inMenu true if the item is to be added into the menu, false if the item will not be added to the menu
     */
    public void createItem(String name, Recipe recipe, double cost, String id, boolean inMenu) {
        MenuItem newItem = new MenuItem(name, recipe, cost, 1.0, id); // This will need changing
        newItem.setInMenu(inMenu);
        itemList.put(newItem.getId(), newItem);
    }

    /**
     * @param ID the ID of the item
     * @return true if the item is removed, false if the item does not exist
     */
    public boolean removeItem(String ID) {
        MenuItem answerItem = itemList.remove(ID);
        if (answerItem == null) {
            return false;
        } else {
            return true;
        }
    }

    public Map<String, MenuItem> getItemList() {
        return itemList;
    }

    public void setItemList(Map<String, MenuItem> itemList) {
        this.itemList = itemList;
    }


}
