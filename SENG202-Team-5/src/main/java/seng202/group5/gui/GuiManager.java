package seng202.group5.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.AppEnvironment;
import seng202.group5.Ingredient;
import seng202.group5.Stock;

import java.io.IOException;

public class GuiManager extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/gui/order.fxml"));
        FXMLLoader sampleLoader = new FXMLLoader(getClass().getResource("/gui/order.fxml"));
        Parent root = sampleLoader.load();
        //primaryStage.setTitle("Selection Screen");
        GeneralController controller = sampleLoader.getController();
        controller.setAppEnvironment(createAppEnvironment());
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public AppEnvironment createAppEnvironment() {
        AppEnvironment thing = new AppEnvironment();
        Ingredient test = new Ingredient("test", "mg", "flour", "ABC123", Money.parse("NZD 7.00"));
        Stock stock = new Stock();
        stock.addNewIngredient(test);
        thing.setStock(stock);
        return thing;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
