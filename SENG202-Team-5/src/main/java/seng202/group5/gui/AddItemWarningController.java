package seng202.group5.gui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group5.information.DietEnum;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;

import java.util.HashMap;
import java.util.HashSet;

public class AddItemWarningController {


    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private Text warningText;

    private AddExtraIngredientController parentController;

    private Ingredient currentIngredient;

    private HashSet<DietEnum> brokenEnumSet;

    /**
     * An initializer for this controller
     */
    @FXML
    public void pseudoInitialize() {
        warningText.setText("Warning: The ingredient " + currentIngredient.getName() + " you are trying to add " +
                "breaks the following dietary requirements of the item: " +
                brokenEnumSet);
    }

    /**
     * Closes the current screen.
     */
    public void closeScreen() {
        Stage stage = (Stage) backButton.getScene().getWindow();

        stage.close();
    }

    public void setParentController(AddExtraIngredientController tempParentController) {
        parentController = tempParentController;
    }

    public void setCurrentIngredient(Ingredient tempIngredient) {
        currentIngredient = tempIngredient;
    }

    public void setDietRequirements(HashSet<DietEnum> tempBrokenEnumSet) {
        brokenEnumSet = tempBrokenEnumSet;
    }
}
