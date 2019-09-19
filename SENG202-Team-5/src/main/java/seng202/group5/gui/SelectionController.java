/**
 *
 */
package seng202.group5.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import seng202.group5.*;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.Recipe;

import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.Map;

public class SelectionController extends GeneralController {


    @FXML
    public Text costText;
    @FXML
    public Text recipeText;
    Recipe recipe;
    public OrderController order;
    @FXML
    private Text pressedCountText;

    @FXML
    private Button addIngredientButton;

    @FXML
    private Text ingredientText;
    @FXML
    private Text itemNameText;

    @FXML
    private Button closeSelectionScreenButton;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Label ingredientName;
    @FXML
    private Spinner ingredientSpinner;
    @FXML
    public MenuItem item;
    @FXML
    public ComboBox ingredeintQuantity;
    @FXML
    public GridPane ingredientPane;
    @FXML
    private GridPane quantityPane;
    private Order currentOrder;

    @XmlTransient
    private HashMap<Ingredient, Integer> ingredientsAmount;

    @Override
    public void pseudoInitialize() {
        try {
            currentOrder = getAppEnvironment().getOrderManager().getOrder();
        } catch (NoOrderException e) {
        }
    }


    /**
     * Changes screen to the add extra ingredient screen.
     *
     * @param actionEvent The action of clicking or pressing the button.
     */
    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        AddExtraIngredientsScreen(actionEvent, "/gui/addExtraIngredient.fxml");
    }

    public void AddExtraIngredientsScreen(ActionEvent event, String scenePath) {
        //TODO this should be refactored somehow so it is using the code in GeneralController instead of being a copy
        Parent extraIngredientsScene = null;
        try {
            FXMLLoader extraIngredientsLoader = new FXMLLoader(getClass().getResource(scenePath));
            extraIngredientsScene = extraIngredientsLoader.load();
            AddExtraIngredientController controller = extraIngredientsLoader.getController();
            controller.setMenuItem(item);
            controller.setAppEnvironment(getAppEnvironment());
            controller.pseudoInitialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        double prevHeight = ((Node) event.getSource()).getScene().getHeight();
        double prevWidth = ((Node) event.getSource()).getScene().getWidth();
        oldStage.setScene(new Scene(extraIngredientsScene, prevWidth, prevHeight));

    }


    /**
     *This method takes the recipe object as the input which was passed during th launch of the selection screen sets the
     * recipe object.
     * @param
     */

    public void setMenuItem(MenuItem newItem){

        item = newItem;
        costText.setText(item.calculateFinalCost().toString());
        recipeText.setText(item.getRecipe().getRecipeText());
        printIngredients(item);
        itemNameText.setText(item.getItemName());
    }

    public void setItemName(){
        itemNameText.setText(item.getItemName());
    }


    public void printIngredients(MenuItem newItem) {
        int row = 1;
        int col = 1;
        for (Map.Entry<Ingredient, Integer> entry : newItem.getRecipe().getIngredientsAmount().entrySet()) {


            Ingredient ingredientObject = entry.getKey();
            Integer value = entry.getValue();
            Spinner sp = new Spinner(0,5,1);
            Label l = new Label(ingredientObject.getName());
            sp.setMaxWidth(54);
            ingredientPane.setConstraints(sp,1,row);
            ingredientPane.setConstraints(l,0,row);
            ingredientPane.getChildren().add(l);
            ingredientPane.getChildren().add(sp);
            row++;
        }
    }


    public void launchOrderScreen(javafx.event.ActionEvent actionEvent) {
        Integer quantity = quantitySpinner.getValue();
        currentOrder.addItem(item, quantity); // Not working so temporary add manually
        //currentOrder.getOrderItems().put(item, quantity);
        System.out.println(currentOrder.getOrderItems());
        changeScreen(actionEvent, "/gui/order.fxml");

    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        quantity = new Spinner(0,100,0);
////        ingredientSpinner.getValueFactory().setValue(1);
//
//    }
}
