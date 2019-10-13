package seng202.group5.logic;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.information.TypeEnum;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MenuManagerTest {
    private Map<String, MenuItem> itemList = new HashMap<String, MenuItem>();
    private MenuManager menuManager = new MenuManager();
    private String burgerRecipeText = "Make a burger!";
    private Ingredient bun = new Ingredient("Bun", "Main", "Bun123", Money.parse("NZD 10.30"));
    private HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
    private Recipe burger;
    private String name;
    private Money cost;


    @BeforeEach
    public void init() {
        menuManager = new MenuManager();
        ingredients.put(bun, 2);
        name = "Cheese Burger";
        cost = Money.parse("NZD 11.50");
        burger = new Recipe("Burger", burgerRecipeText, ingredients);
    }

    @Test
    public void testCreateMenuManagerWithExistingMenuItems() {
        MenuItem item = new MenuItem("Hamburger", burger, cost,
                "burg124", true, TypeEnum.MAIN);
        HashMap<String, MenuItem> itemListToBeAdded = new HashMap<String, MenuItem>();
        itemListToBeAdded.put("1", item);
        MenuManager newMenuManager = new MenuManager(itemListToBeAdded);
        assertEquals(1, newMenuManager.getItemMap().size());
    }

    @Test
    public void testCreateRecipeReturnsRecipe() {
        assertNotNull(burger);
    }

    @Test
    public void testCreateItemAddtoMenu() {
        menuManager.createItem(name, burger, cost, "burg123", true);
        assertEquals(1, menuManager.getMenuItems().size());
    }

    @Test
    public void testCreateItemWithoutAddingtoMenu() {
        menuManager.createItem(name, burger, cost, "burg123", false);
        assertEquals(1, menuManager.getItemMap().size());
        //assertTrue(menuManager.getMenuItems().size() == 0);
    }

    @Test
    public void testRemoveItemWithItemInMenu() {
        String testID = "burg123";
        menuManager.createItem(name, burger, cost, "burg123", false);
        assertEquals(1, menuManager.getItemMap().size());
        menuManager.removeItem(testID);
        assertEquals(0, menuManager.getItemMap().size());
    }
}