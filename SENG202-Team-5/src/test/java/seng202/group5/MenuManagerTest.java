package seng202.group5;

import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Ignore
public class MenuManagerTest extends TestCase {
    private Map<String, MenuItem> itemList = new HashMap<String, MenuItem>();
    private MenuManager menuManager;
    private String burgerRecipeText;
    private HashMap<Ingredient, Integer> ingredient;



    @BeforeEach
    public void init() {
        String burgerRecipeText = "Make a burger!";
        HashMap<Ingredient, Integer> ingredient = new HashMap<>();
    }

    @Test
    public void testCreateRecipeReturnsRecipe() {
        Recipe burger = menuManager.createRecipe(ingredient, burgerRecipeText);
        assertTrue(burger instanceof Recipe);
    }

    @Test
    public void testCreateItemAddtoMenu() {
        Recipe burger = menuManager.createRecipe(ingredient, burgerRecipeText);
        String name = "cheeseBurger";
        double cost = 11.50f;
        boolean inMenu = true;
        menuManager.createItem(name, burger, cost, inMenu);
        assertTrue(menuManager.getItemList().size() == 1);
    }

    @Test
    public void testCreateItemNotAddtoMenu() {
        Recipe burger = menuManager.createRecipe(ingredient, burgerRecipeText);
        String name = "cheeseBurger";
        double cost = 11.50f;
        boolean inMenu = false;
        menuManager.createItem(name, burger, cost, inMenu);
        assertTrue(menuManager.getItemList().size() == 0);
    }

    @Test
    public void testRemoveItemWithItemInMenu() {
        String ID = "B1";
        menuManager.removeItem(ID);
        assertTrue(menuManager.getItemList().size() == 0);
    }
}