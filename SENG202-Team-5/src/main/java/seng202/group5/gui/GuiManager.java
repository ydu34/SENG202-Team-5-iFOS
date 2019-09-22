package seng202.group5.gui;

import com.sun.glass.ui.Screen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.*;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.logic.Stock;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Integer height = Screen.getMainScreen().getHeight();
        Integer width = Screen.getMainScreen().getWidth();
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
    }

    public AppEnvironment createAppEnvironment() {
        AppEnvironment thing = new AppEnvironment();
        addTestData(thing);
        return thing;
    }

    public void addTestData(AppEnvironment thing) {
        Ingredient test = new Ingredient("test", "mg", "flour", Money.parse("NZD 7.00"));
        Stock stock = thing.getStock();
        stock.addNewIngredient(test);
        stock.modifyQuantity(test.getID(), 1);
        Recipe testRecipe = new Recipe("Chicken burger", "1) Get some Chicken\n2) Get some cheese\n3) Throw the chicken on the grill and let it fry\n");
        Recipe testRecipe2 = new Recipe("Vege burger", "Steps to make pad thai");
        HashSet<DietEnum> ingredientInfo1 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
        }};
        HashSet<DietEnum> ingredientInfo2 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
        }};
        Ingredient chickenpatty = new Ingredient("chicken", "kg", "meat", Money.parse("NZD 10"), ingredientInfo1);
        Ingredient cheese = new Ingredient("cheese", "kg", "dairy", Money.parse("NZD 5"), ingredientInfo2);
        HashSet<DietEnum> ingredientInfo3 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
        }};
        Ingredient vegePatty = new Ingredient("vegetables", "kg", "vege", Money.parse("NZD 10"), ingredientInfo3);
        testRecipe.addIngredient(chickenpatty, 1);
        testRecipe.addIngredient(cheese, 1);
        testRecipe2.addIngredient(vegePatty, 1);

        stock.addNewIngredient(chickenpatty, 100);
        stock.addNewIngredient(cheese, 200);
        stock.addNewIngredient(vegePatty, 150);

        thing.getMenuManager().createItem("Chicken Burger", testRecipe, Money.parse("NZD 5"), "1220", true);
        thing.getMenuManager().createItem("Vege Burger", testRecipe2, Money.parse("NZD 7"), "1222", true);
        thing.getOrderManager().newOrder();

        Order tempOrder = new Order(stock);
        tempOrder.addItem(thing.getMenuManager().getItemMap().get("1220"), 4);
        tempOrder.setDateTimeProcessed(LocalDateTime.now());
        try {
            thing.getFinance().pay(tempOrder.getTotalCost(),
                                   new ArrayList<>() {{
                                       add(Money.parse("NZD 1000.00"));
                                   }},
                                   tempOrder.getDateTimeProcessed(),
                                   tempOrder.getID());
        } catch (InsufficientCashException e) {
            e.printStackTrace();
        }
        thing.getHistory().getTransactionHistory().put(tempOrder.getID(), tempOrder);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
