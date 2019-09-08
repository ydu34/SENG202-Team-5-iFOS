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

public class AddIngredientController {

    @FXML
    private Button launchSelectionScreenButton;


    public void changeScreen(ActionEvent event, String scenePath){
        Parent sampleScene = null;
        try {

            sampleScene = FXMLLoader.load(getClass().getResource(scenePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        oldStage.setScene(new Scene(sampleScene, 821, 628));
    }



    public void launchSelectionScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/selection.fxml");
    }

}

