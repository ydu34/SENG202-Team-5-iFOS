package seng202.group5.gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
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
        modifyingItemCountSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        modifyingItemCountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, maxQuantity, 1));
    }


    public void addExtraIngredientScreen(ActionEvent event, String scenePath) {

        SelectModifiableItemsController controller = (SelectModifiableItemsController) changeScreen(event, scenePath);
    }

    /**
     * Closes this window.
     */
    public void closeScreen() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }


    public void setModifyingItem(MenuItem tempItem) {
        modifyingItem = tempItem;
    }

    public void setMaxQuantity(int tempQuantity) {
        maxQuantity = tempQuantity;
    }


}
