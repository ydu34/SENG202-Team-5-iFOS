package seng202.group5;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class MenuManagerTest {
    private Map<String, MenuItem> itemList = new HashMap<String, MenuItem>();
    private MenuManager menuManager = new MenuManager();
    private String burgerRecipeText = "Make a burger!";
    private Ingredient bun = new Ingredient("Bun", "300", "Main", "Bun123", 10.30);
    private HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
    private OrderManager orderManager;
    private Recipe burger;
    private String name;
    private double cost;



    @BeforeEach
    public void init() {
        menuManager = new MenuManager();
        ingredients.put(bun, 2);
        name = "Cheese Burger";
        cost = 11.50;
        burger = menuManager.createRecipe("Burger", ingredients, burgerRecipeText);

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
        assertTrue(menuManager.getItemList().size() == 1);
        assertTrue(menuManager.getMenuItems().size() == 0);
    }

    @Test
    public void testRemoveItemWithItemInMenu() {
        String testID = "burg123";
        menuManager.createItem(name, burger, cost, "burg123", false);
        assertTrue(menuManager.getItemList().size() == 1);
        menuManager.removeItem(testID);
        assertTrue(menuManager.getItemList().size() == 0);
    }
}