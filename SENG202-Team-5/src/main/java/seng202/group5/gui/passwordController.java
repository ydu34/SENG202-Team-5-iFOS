package seng202.group5.gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class passwordController extends GeneralController {

    @FXML
    JFXButton cancelButton;

    @FXML
    JFXButton numberOne;

    @FXML
    JFXButton numberTwo;

//    @FXML
//    JFXButton numberOne;
//    @FXML
//    JFXButton numberOne;
//    @FXML
//    JFXButton numberOne;
//
//    @FXML
//    JFXButton numberOne;
//
//    @FXML
//    JFXButton numberOne;
//
//    @FXML
//    JFXButton numberOne;
//
//    @FXML
//    JFXButton numberOne;
//
//    @FXML
//    JFXButton numberOne;

    @FXML
    JFXButton confirmButton;

    @FXML
    Text warningText;


    GeneralController source;
    ActionEvent origin;



    String password = "1111";
    String input = "";

    public void closeScreen(ActionEvent event) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public void buildPassword(){
        input+= numberOne.getText();
        warningText.setText(" ");
        System.out.println(input);
    }

    public void checkPassword(ActionEvent event){
        if(input.equals(password)){
            source.launchAdminScreen(origin);
            System.out.println("pass");
            closeScreen(event);
        }

        if(input.length() == 4){
            warningText.setText("Incorrect Password");
        }

        if(input.length() != 4){
            warningText.setText("Password must be of length 4");
            input = "";

        }


    }

    public void setSource(GeneralController controller) {
        source = controller;
    }
    public void setEvent(ActionEvent event) {
        origin = event;
    }

}
