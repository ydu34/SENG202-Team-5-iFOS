package seng202.group5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class DatabaseTest {
    Database handler;
    Recipe cheeseBurgerRecipe;
    HashMap<String, Integer> cheeseBurgerIngredients;
    MenuItem cheeseBurger;
    Ingredient cheese;
    Ingredient bun;
    Ingredient beefPattie;
    Ingredient lettuce;
    Ingredient tomatoSauce;

    @BeforeEach
    void init() {
        handler= new Database();
        cheeseBurgerRecipe = new Recipe("Cheese Burger", "PlaceholderRecipe");
        cheeseBurgerIngredients = new HashMap<>();
        cheeseBurgerIngredients.put("1", 10);
        cheeseBurgerIngredients.put("2",5);
        cheeseBurger = new MenuItem("Cheese Burger", cheeseBurgerRecipe, 5.0, 8.0, "1", true);
        cheeseBurgerRecipe.setIngredientIDs(cheeseBurgerIngredients);
        Ingredient cheese = new Ingredient("Cheese", "Count", "Dairy", "1", 0.10);
        Ingredient bun = new Ingredient("Bun","Count", "Bread", "2", 0.50);
        Ingredient beefPattile = new Ingredient("Beef Pattie", "Count", "Meat", "3", 1.0);
        Ingredient lettuce = new Ingredient("Lettuce", "Count", "Vegetable", "4", 0.2);
        Ingredient tomatoSauce = new Ingredient("Tomato Sauce", "ml", "Sauce", "5", 0.1);
    }

    /**
     * Please do not touch my test case.
     */
    @Test
    void test() {
        Ingredient ing1 = new Ingredient("Milk", "L", "Liquid" , "1",4.0f);
        Ingredient ing2 = new Ingredient("Apple", "kg", "Fruit", "2", 1.0f);

        Stock stock = handler.getStock();
        stock = new Stock();
        stock.getIngredients().put("1", ing1);
        stock.getIngredients().put("2", ing2);

        handler.objectToXml(Stock.class, stock, "stock.xml");
        handler.setStock(stock);




        MenuManager menuManager = new MenuManager();
        menuManager.setItemList(new HashMap<String, MenuItem>());
        menuManager.getItemList().put("1", cheeseBurger);


        handler.objectToXml(MenuManager.class, menuManager, "menu.xml");
        HashMap<String, MenuItem> menuItems = menuManager.getItemList();
        handler.handleMenu(menuItems);
        System.out.print(menuItems.get("1").getRecipe().getIngredientsAmount());
    }

    @Test
    void testHistoryObjectToXml() {
        HashMap<MenuItem, Integer> orderItems = new HashMap<>();
        orderItems.put(cheeseBurger, 1);
        Order order = new Order(orderItems, cheeseBurger.getSellingCost(), "1");
        History history = new History();
        history.getTransactionHistory().put("1", order);
        handler.objectToXml(History.class, history, "history.xml");
    }

}