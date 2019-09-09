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

public class AddExtraIngredientController {

    @FXML
    private Button launchSelectionScreenButton;


    public void changeScreen(ActionEvent event, String scenePath, String windowTitle){
        Parent newScene = null;
        try {

            newScene = FXMLLoader.load(getClass().getResource(scenePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldStage.setTitle(windowTitle);
        oldStage.setScene(new Scene(newScene, 821, 628));
    }

    /**
    * Takes Worker and the list of MenuItems and iterates through finding each item
     * and adds it to the addExtraIngredient.fxml file.
     */
    public void initialise() {



    }


    public void launchSelectionScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/selection.fxml", "Select an Item");
    }

}

