/**
 * @author Shivin Gaba, Michael Morgoun
 */
package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import seng202.group5.Order;

import java.io.IOException;

public class StockController extends GeneralController {


    @FXML
    private TableView<Order> stockTable;

    @FXML
    private TableColumn rowIngredient;

    @FXML
    private TableColumn rowQuantity;

    @FXML
    private TableColumn rowUnits;

    @FXML
    private TableColumn rowCategory;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button removeButton;

}
