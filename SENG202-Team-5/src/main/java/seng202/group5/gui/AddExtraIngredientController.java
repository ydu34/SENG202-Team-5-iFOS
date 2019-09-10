package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AddExtraIngredientController extends GeneralController {

    @FXML
    private Button launchSelectionScreenButton;

    public void launchSelectionScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/selection.fxml");
    }

}

