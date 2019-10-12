package seng202.group5.gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.MenuItem;
import seng202.group5.logic.Order;

import java.util.ArrayList;

public class SelectModifiableItemsController extends GeneralController {

    @FXML
    Spinner<Integer> modifyingItemCountSpinner;

    @FXML
    JFXButton confirmButton;

    @FXML
    JFXButton backButton;

    Order currentOrder;

    MenuItem modifyingItem;

    int maxQuantity;

    @Override
    public void pseudoInitialize() {
        super.pseudoInitialize();
        try {
            currentOrder = getAppEnvironment().getOrderManager().getOrder();
        } catch (NoOrderException e) {
            System.out.println(e);
        }
        Spinner<Integer> spinner = new Spinner<Integer>();
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, maxQuantity, 1));
        modifyingItemCountSpinner = spinner;
    }

    public void addExtraIngredientScreen(ActionEvent event, String scenePath) {

        SelectModifiableItemsController controller = (SelectModifiableItemsController) changeScreen(event, scenePath);
    }


    /**
     * This method launches the addExtraIngredient Screen.
     * @param actionEvent
     */

    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        addExtraIngredientScreen(actionEvent, "/gui/addExtraIngredient.fxml");
    }

    public void setModifyingItem(MenuItem tempItem) {
        modifyingItem = tempItem;
    }

    public void setMaxQuantity(int tempQuantity) {
        maxQuantity = tempQuantity;
    }


}
