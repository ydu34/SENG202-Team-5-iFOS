package seng202.group5.logic;

import org.joda.money.Money;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

/**
 * This class manages the menu, includes creating recipes and items, and removing items from the menu.
 *
 * @author James Kwok
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuManager {

    private HashMap<String, MenuItem> itemMap;


    public MenuManager() {
        itemMap = new HashMap<String, MenuItem>();
    }

    public MenuManager(HashMap<String, MenuItem> tempItemList) {
        itemMap = new HashMap<String, MenuItem>();
        for (String stringKey : tempItemList.keySet()) {
            itemMap.put(stringKey, tempItemList.get(stringKey));
        }
    }

    /**
     * Creates a new recipe
     *
     * @param ingredients contains all the ingredients IDs and their quantities required for the recipe
     * @param recipeName  the recipe's name.
     * @param recipeText  the recipes instructions.
     * @return the newly made Recipe object.
     */
    public Recipe createRecipe(String recipeName, HashMap<Ingredient, Integer> ingredients, String recipeText) {
        return new Recipe(recipeName, recipeText, ingredients);
    }

    /**
     * Creates a menu item and adds it to the item list
     *
     * @param name       the name of the item
     * @param recipe     the recipe for this item
     * @param markupCost the amount to increase the cost of this item by
     * @param inMenu     whether or not this item will be added to the menu
     */
    public void createItem(String name, Recipe recipe, Money markupCost, String id, boolean inMenu) {
        MenuItem newItem = new MenuItem(name, recipe, markupCost, id, inMenu);
        itemMap.put(id, newItem);
    }

    /**
     * @param ID the ID of the item
     * @return true if the item is removed, false if the item does not exist
     */
    public boolean removeItem(String ID) {
        boolean removed = false;
        MenuItem answerItem = itemMap.remove(ID);
        if (answerItem != null) {
            removed = true;
        }
        return removed;
    }

    /**
     * Adds the MenuItem object into the itemMap.
     * @param menuItem The menu item to be added into the menu.
     */
    public void addItem(MenuItem menuItem) {
        itemMap.put(menuItem.getID(), menuItem);
    }

    public HashMap<String, MenuItem> getItemMap() {
        return itemMap;
    }

    public void setItemMap(HashMap<String, MenuItem> tempItemMap) {
        itemMap = tempItemMap;
    }

    /**
     * Calculates a HashMap containing all items which exist in a given menu.
     * @return menuItemMap, a map containing all items which are in the menu.
     */
    public HashMap<String, MenuItem> getMenuItems() {
        HashMap<String, MenuItem> menuItemMap = new HashMap<String, MenuItem>();
        for (String stringKey : itemMap.keySet()) {
            MenuItem item = itemMap.get(stringKey);
            if (item.isInMenu()) {
                menuItemMap.put(stringKey, item);
            }
        }
        return menuItemMap;
    }

}
