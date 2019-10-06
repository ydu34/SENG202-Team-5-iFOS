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
import seng202.group5.information.*;
import seng202.group5.logic.Order;
import seng202.group5.logic.Stock;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A class that sets up the application and starts it
 * @author Daniel Harris, Shivin Gaba, Yu Duan, James Kwok, Tasman Berry ,Michael Morgoun
 */
public class GuiManager extends Application {

    /**
     * Starts the application
     *
     * @param primaryStage the stage to display the application on
     * @throws IOException if the application fails to load
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader sampleLoader = new FXMLLoader(getClass().getResource("/gui/order.fxml"));
        Parent root = sampleLoader.load();
        GeneralController controller = sampleLoader.getController();
        controller.setAppEnvironment(createAppEnvironment());
        controller.pseudoInitialize();
        int height = Screen.getMainScreen().getHeight();
        int width = Screen.getMainScreen().getWidth();
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setTitle("iFOS");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    /**
     * Creates the environment for the application logic to run in
     *
     * @return the new AppEnvironment
     */
    public AppEnvironment createAppEnvironment() {
        AppEnvironment thing = new AppEnvironment();
        addTestData(thing);
        return thing;
    }

    /**
     * Adds data to the specified app environment for testing
     *
     * @param environment the app environment to add data to
     */
    public void addTestData(AppEnvironment environment) {
        Ingredient test = new Ingredient("Flour", "Flour", Money.parse("NZD 7.00"));
        Stock stock = environment.getStock();
        stock.addNewIngredient(test, 100);
        stock.modifyQuantity(test.getID(), 1);
        Recipe testRecipe = new Recipe("Chicken burger", "1) Get some Chicken\n2) Get some cheese\n3) Throw the chicken on the grill and let it fry\n");
        Recipe testRecipe2 = new Recipe("Vege burger", "Steps to Vege burger");
        HashSet<DietEnum> ingredientInfo1 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
        }};
        HashSet<DietEnum> ingredientInfo2 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
        }};
        Ingredient chickenpatty = new Ingredient("Chicken", "Meat", Money.parse("NZD 10"), ingredientInfo1);
        Ingredient cheese = new Ingredient("Cheese", "Dairy", Money.parse("NZD 5"), ingredientInfo2);
        HashSet<DietEnum> ingredientInfo3 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
        }};
        Ingredient vegePatty = new Ingredient("Vegetables", "Vegetable", Money.parse("NZD 10"), ingredientInfo3);
        testRecipe.addIngredient(chickenpatty, 1);
        testRecipe.addIngredient(cheese, 1);
        testRecipe2.addIngredient(vegePatty, 1);

        stock.addNewIngredient(chickenpatty, 100);
        stock.addNewIngredient(cheese, 200);
        stock.addNewIngredient(vegePatty, 150);

        MenuItem chickenBurger = new MenuItem("Chicken Burger", testRecipe, Money.parse("NZD 5"), true, TypeEnum.MAIN);
        chickenBurger.setImageString("chicken_burger.jpg");
        environment.getMenuManager().getItemMap().put(chickenBurger.getID(), chickenBurger);

        MenuItem vegeBurger = new MenuItem("Vege Burger", testRecipe2, Money.parse("NZD 7"), true, TypeEnum.MAIN);
        vegeBurger.setImageString("vege_burger.jpg");
        environment.getMenuManager().getItemMap().put(vegeBurger.getID(), vegeBurger);
        

        environment.getOrderManager().newOrder();

        Order tempOrder = new Order(stock);
        tempOrder.addItem(environment.getMenuManager().getItemMap().get(chickenBurger.getID()), 4);
        try {
            environment.getFinance().pay(new ArrayList<>() {{
                                             add(Money.parse("NZD 100.00"));
                                         }},
                                         LocalDateTime.now(),
                                         tempOrder);
        } catch (InsufficientCashException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
