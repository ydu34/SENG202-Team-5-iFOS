package seng202.group5;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void test() {
        Ingredient ing1 = new Ingredient("Milk", "L", "Liquid" , "1",4.0f);
        Ingredient ing2 = new Ingredient("Apple", "kg", "Fruit", "2", 1.0f);

        Stock stock = new Stock();
        stock.getIngredients().put("1", ing1);
        stock.getIngredients().put("2", ing2);

        Database handler = new Database();
        handler.objectToXml(Stock.class, stock, "stock.xml");
        handler.setStock(stock);

        HashMap<Ingredient, Integer> cheeseBurgerIngredients = new HashMap<>();
        cheeseBurgerIngredients.put(ing1, 10);
        cheeseBurgerIngredients.put(ing2, 5);

        Recipe cheeseBurgerRecipe = new Recipe("Cheese Burger", "PlaceholderRecipe");
        cheeseBurgerRecipe.setIngredientsAmount(cheeseBurgerIngredients);
        MenuItem cheeseBurger = new MenuItem("Cheese Burger", cheeseBurgerRecipe, 5.0, 8.0, "1", true);

        MenuManager menuManager = new MenuManager();
        menuManager.setItemList(new HashMap<>());
        menuManager.getItemList().put("1", cheeseBurger);

        handler.objectToXml(MenuManager.class, menuManager, "menu.xml");
        for (Map.Entry<String, MenuItem> entry : menuManager.getItemList().entrySet()) {
            String ID = entry.getKey();
            MenuItem menuItem = entry.getValue();

            menuItem.getRecipe().setIngredientsAmount(handler.getIngredientsFromID(menuItem.getRecipe().getIngredientIDs()));
        }
        System.out.print(menuManager.getItemList().get("1").getRecipe().getIngredientsAmount());
    }


}