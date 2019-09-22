package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.*;
import seng202.group5.logic.MenuManager;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

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
        burger = menuManager.createRecipe("Burger", ingredients, burgerRecipeText);
    }
    @Test
    public void testCreateMenuManagerWithExistingMenuItems() {
        MenuItem item = new MenuItem("Hamburger", burger, cost,
                "burg124", true);
        HashMap<String, MenuItem> itemListToBeAdded = new HashMap<String, MenuItem>();
        itemListToBeAdded.put("1", item);
        MenuManager newMenuManager = new MenuManager(itemListToBeAdded);
        assertTrue(newMenuManager.getItemMap().size() == 1);
    }

    @Test
    public void testCreateRecipeReturnsRecipe() {
        assertTrue(burger instanceof Recipe);
    }

    @Test
    public void testCreateItemAddtoMenu() {
        menuManager.createItem(name, burger, cost, "burg123", true);
        assertTrue(menuManager.getMenuItems().size() == 1);
    }

    @Test
    public void testCreateItemWithoutAddingtoMenu() {
        menuManager.createItem(name, burger, cost, "burg123", false);
        assertTrue(menuManager.getItemMap().size() == 1);
        //assertTrue(menuManager.getMenuItems().size() == 0);
    }

    @Test
    public void testRemoveItemWithItemInMenu() {
        String testID = "burg123";
        menuManager.createItem(name, burger, cost, "burg123", false);
        assertTrue(menuManager.getItemMap().size() == 1);
        menuManager.removeItem(testID);
        assertTrue(menuManager.getItemMap().size() == 0);
    }
}