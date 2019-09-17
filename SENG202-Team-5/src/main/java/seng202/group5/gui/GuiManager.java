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

public class GuiManager extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/gui/order.fxml"));
        FXMLLoader sampleLoader = new FXMLLoader(getClass().getResource("/gui/order.fxml"));
        Parent root = sampleLoader.load();
        //primaryStage.setTitle("Selection Screen");
        GeneralController controller = sampleLoader.getController();
        controller.setAppEnvironment(createAppEnvironment());
        //controller.pseudoInitialize();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public AppEnvironment createAppEnvironment() {
        AppEnvironment thing = new AppEnvironment();
        Ingredient test = new Ingredient("test", "mg", "flour", "ABC123", Money.parse("NZD 7.00"));
        Stock stock = new Stock();
        stock.addNewIngredient(test);
        thing.setStock(stock);
        thing.setOrderManager(new OrderManager(thing.getStock(), thing.getHistory()));

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
        thing.getHistory().getTransactionHistory().put(tempOrder.getID(),
                                                       tempOrder);
        tempOrder.setDateTimeProcessed(LocalDateTime.now());
        return thing;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
