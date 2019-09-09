package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */

class MenuItemTest {

    private MenuItem testBurger;
    private Order testOrder;
    private OrderManager testOrderManager;
    private Stock testStock;
    private MenuItem m;
    private Recipe testRecipe;
    private Ingredient chickenPatty;
    private Ingredient cheese;

    @BeforeEach
    void init() {
        //HashMap<MenuItem, Integer> orderItems = new HashMap<MenuItem, Integer>();
        HashMap<Ingredient, Integer> ingredients = new HashMap<>();
        HashMap<String, Integer> ingredientStock = new HashMap<String, Integer>();
        HashMap<String, Order> transactionHistory = new HashMap<String, Order>();
       // Order testOrder = new Order(orderItems, Money.parse("NZD 0.00"), "1234");
        testRecipe = new Recipe("Cheeseburger", "It's raw.");
        String menuName = "Chicken burger";
        Money cost = Money.parse("NZD 5.00");
        String uniqueId = "23";
        boolean someInMenu = true;
        m = new MenuItem(menuName, testRecipe, cost, "1000", true);
       // Recipe testRecipe_1 = new Recipe("Chicken burger", "Steps to chicken burger");
        HashSet<DietEnum> ingredientInfo = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);

        }};
        chickenPatty = new Ingredient("chicken", "kg", "meat", "12", Money.parse("NZD 5"), ingredientInfo);
        cheese = new Ingredient("cheese", "kg", "dairy", "12", Money.parse("NZD 2"), ingredientInfo);
        testRecipe.addIngredient(chickenPatty, 2);
        testRecipe.addIngredient(cheese, 1);
    }

        // test calculateMakingCost

        @Test
        void testCalculateMakingCostStock() {

            Money makingCost = m.calculateMakingCost();
            Money actualCost = Money.parse("NZD 12.00");
            Money actualCost_1 = Money.parse("NZD 10.00");
            assertTrue(makingCost.equals(actualCost));
            assertEquals(makingCost.getAmount(), actualCost.getAmount());
            testRecipe.removeIngredient(cheese);
            HashMap<Ingredient, Integer> ingredients = testRecipe.getIngredientsAmount();
            for (Map.Entry<Ingredient, Integer> eachIngredient : ingredients.entrySet()) {
                Ingredient ingredient = eachIngredient.getKey();
                Integer amount = eachIngredient.getValue();
            System.out.println(ingredient.getName() + "," + amount);}
            makingCost = m.calculateMakingCost();
            assertEquals(makingCost.getAmount(), actualCost_1.getAmount());
        }

    @Test
    void calculateFinalCost(){


    }





//        MenuItem testBurger = new MenuItem("Burger", testRecipe, 3.00,
//                10.00,  "BRG10", true);
//        ArrayList<MenuItem> menuItems = new ArrayList<>();
//        menuItems.add(testBurger);
       // Stock testStock = new Stock(ingredients, ingredientStock);
       // History testHistory = new History(transactionHistory);
        //OrderManager testOrderManager = new OrderManager(testOrder, testStock, testHistory);
    }
//    @Test
//    void testAddStock() {
//        testBurger.addStock(testOrderManager.getCurrentStock().getIngredientStock()); // getCurrentStock returns Stock yet addStock takes a map as input
//
//    }

//    @Test
//    void testRemoveStock() {
//    }
//}