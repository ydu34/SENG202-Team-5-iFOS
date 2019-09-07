package seng202.group5;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

/**
 * This class manages the menu, includes creating recipes and items, and removing items from the menu.
 * @author James Kwok
 */
@XmlRootElement
public class MenuManager {

    private HashMap<String, MenuItem> itemList;


    public MenuManager() {
        itemList = new HashMap<String, MenuItem>();
    }

    public MenuManager(HashMap<String,MenuItem> tempItemList) {
        for (String stringKey : tempItemList.keySet()) {
            itemList.put(stringKey, tempItemList.get(stringKey));
        }
    }

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
        MenuItem newItem = new MenuItem(name, recipe, cost, 1.0, id, inMenu);
        itemList.put(id, newItem);
    }

    /**
     * @param ID the ID of the item
     * @return true if the item is removed, false if the item does not exist
     */
    public boolean removeItem(String ID) {
        boolean removed = false;
        MenuItem answerItem = itemList.remove(ID);
        if (answerItem != null) {
            removed = true;
        }
        return removed;
    }

    public HashMap<String, MenuItem> getItemList() {
        return itemList;
    }

    public void setItemList(HashMap<String, MenuItem> tempItemList) {
        itemList = tempItemList;
    }

    public HashMap<String, MenuItem> getMenuItems() {
        HashMap<String, MenuItem> menuItemList = new HashMap<String, MenuItem>();
        for (String stringKey : itemList.keySet()) {
            MenuItem item = itemList.get(stringKey);
            if (item.isInMenu()) {
                menuItemList.put(stringKey, item);
            }
        }
        return menuItemList;
    }

}
