package seng202.group5.gui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.MenuItem;
import seng202.group5.logic.Order;

import java.util.ArrayList;

public class SelectModifiableItemsController extends GeneralController {

    @FXML
    Spinner modifyingItemCountSpinner;

    @FXML
    JFXButton confirmButton;

    @FXML
    JFXButton backButton;

    Order currentOrder;

    MenuItem modifyingItem;

    @Override
    public void pseudoInitialize() {
        super.pseudoInitialize();
        try {
            currentOrder = getAppEnvironment().getOrderManager().getOrder();

        } catch (NoOrderException e) {
            System.out.println(e);
        }



    }


}
