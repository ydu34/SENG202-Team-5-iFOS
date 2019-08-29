package seng202.group5.gui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SampleController {

    @FXML
    private Text pressedCountText;

    private int count = 0;

    public void countButtonPressed() {
        count += 1;
        pressedCountText.setText(String.format("Count: %s", count));
    }

}