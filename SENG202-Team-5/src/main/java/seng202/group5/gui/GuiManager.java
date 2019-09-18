package seng202.group5.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class GuiManager extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/gui/order.fxml"));
        FXMLLoader sampleLoader = new FXMLLoader(getClass().getResource("/gui/order.fxml"));
        Parent root = sampleLoader.load();
        //primaryStage.setTitle("Selection Screen");
        GeneralController controller = sampleLoader.getController();
        controller.setAppEnvironment(createAppEnvironment());
        controller.pseudoInitialize();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public AppEnvironment createAppEnvironment() {
        AppEnvironment thing = new AppEnvironment();
        Ingredient test = new Ingredient("test", "mg", "flour", "ABC123", Money.parse("NZD 7.00"));
        Stock stock = thing.getStock();
        stock.addNewIngredient(test);
        thing.setOrderManager(new OrderManager(thing.getStock(), thing.getHistory()));

        Recipe testRecipe = new Recipe("Chicken burger", "1) Get some Chicken\n2) Get some cheese\n3) Throw the chicken on the grill and let it fry\n");
        Recipe testRecipe2 = new Recipe("Vege burger", "Steps to make pad thai");


        HashSet<DietEnum> ingredientInfo1 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
        }};
        HashSet<DietEnum> ingredientInfo2 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
        }};
        Ingredient chickenpatty = new Ingredient("chicken", "kg", "meat", "15", Money.parse("NZD 10"), ingredientInfo1);
        Ingredient cheese = new Ingredient("cheese", "kg", "dairy", "14", Money.parse("NZD 5"), ingredientInfo2);
        HashSet<DietEnum> ingredientInfo3 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
        }};
        Ingredient vegePatty = new Ingredient("vegetables", "kg", "vege", "13", Money.parse("NZD 10"), ingredientInfo3);
        testRecipe.addIngredient(chickenpatty, 1);
        testRecipe.addIngredient(cheese, 1);
        testRecipe2.addIngredient(vegePatty, 1);

        stock.addNewIngredient(chickenpatty, 100);
        stock.addNewIngredient(cheese, 200);
        stock.addNewIngredient(vegePatty, 150);

        thing.getMenuManager().createItem("Chicken Burger", testRecipe, Money.parse("NZD 5"), "1220", true);
        thing.getMenuManager().createItem("Vege Burger", testRecipe2, Money.parse("NZD 7"), "1222", true);

        MenuItem testItem = new MenuItem(
                "Burger Item",
                new Recipe("Burger",
                           "Add items to burger",
                           new HashMap<>() {{
                               put(new Ingredient("Bun", "buns", "Bread", "ARZ4O2", Money.parse("NZD 1.2")), 2);
                               put(new Ingredient("Patty", "patties", "Meat", "5ES240", Money.parse("NZD 3.4")), 1);
                           }}),
                Money.parse("NZD 5.80"),
                "14328",
                true
        );
        Order tempOrder = new Order(new Stock());
        tempOrder.addItem(testItem, 4);
        tempOrder.setDateTimeProcessed(LocalDateTime.now());
        thing.getHistory().getTransactionHistory().put(tempOrder.getID(),
                                                       tempOrder);
        return thing;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
