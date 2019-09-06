package seng202.group5;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class MenuManagerTest {
    private Map<String, MenuItem> itemList = new HashMap<String, MenuItem>();
    private MenuManager menuManager;
    private String burgerRecipeText = "Make a burger!";
    private Ingredient bun = new Ingredient("Bun", "300", "Main", "Bun123", 10.30);
    private HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
    private Worker worker;



    @BeforeEach
    public void init() {
        menuManager = new MenuManager();
        HashMap<Ingredient, Integer> ingredients = new HashMap<>();
    }

    @Test
    public void testCreateRecipeReturnsRecipe() {

        ingredients.put(bun, 2);
        Recipe burger = menuManager.createRecipe("Burger", ingredients, burgerRecipeText);
        assertTrue(burger instanceof Recipe);
    }

    @Test
    public void testCreateItemAddtoMenu() {
        ingredients.put(bun, 2);
        Recipe burger = menuManager.createRecipe("Burger", ingredients, burgerRecipeText);
        String name = "cheeseBurger";
        double cost = 11.50f;
        menuManager.createItem(name, burger, cost, "burg123", true);
        assertTrue(menuManager.getItemList().size() == 1);
    }

    @Test
    public void testCreateItemNotAddtoMenu() {
        ingredients.put(bun, 2);
        Recipe burger = menuManager.createRecipe("Burger", ingredients, burgerRecipeText);
        String name = "cheeseBurger";
        double cost = 11.50f;
        boolean inMenu = false;
        menuManager.createItem(name, burger, cost, "burg123", inMenu);
        assertTrue(menuManager.getItemList().size() == 1);
        assertTrue(worker.getMenuItems().size() == 0);
    }

    @Test
    public void testRemoveItemWithItemInMenu() {
        String ID = "B1";
        menuManager.removeItem(ID);
        assertTrue(menuManager.getItemList().size() == 0);
    }
}