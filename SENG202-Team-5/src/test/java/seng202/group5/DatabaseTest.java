package seng202.group5;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

class DatabaseTest {

    /**
     * Please do not touch my test case.
     */
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

        HashMap<String, Integer> cheeseBurgerIngredients = new HashMap<>();
        cheeseBurgerIngredients.put("1", 10);
        cheeseBurgerIngredients.put("2",5);

        Recipe cheeseBurgerRecipe = new Recipe("Cheese Burger", "PlaceholderRecipe");
        cheeseBurgerRecipe.setIngredientIDs(cheeseBurgerIngredients);
        MenuItem cheeseBurger = new MenuItem("Cheese Burger", cheeseBurgerRecipe, 5.0, 8.0, "1", true);

        MenuManager menuManager = new MenuManager();
        menuManager.setItemList(new HashMap<String, MenuItem>());
        menuManager.getItemList().put("1", cheeseBurger);


        handler.objectToXml(MenuManager.class, menuManager, "menu.xml");
        HashMap<String, MenuItem> menuItems = menuManager.getItemList();
        handler.handleMenu(menuItems);
        System.out.print(menuItems.get("1").getRecipe().getIngredientsAmount());
    }


}