package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class SelectionController {

    @FXML
    private Text pressedCountText;

    @FXML
    private Button addIngredientButton;

    private int count = 0;

    public void countButtonPressed() {
        count += 1;
        pressedCountText.setText(String.format("Count: %s", count));
    }
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

    public void launchAddExtraIngredeintScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/addExtraIngredient.fxml");
    }
}
