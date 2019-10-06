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

    @FXML
    JFXButton numberThree;
    @FXML
    JFXButton numberFour;
    @FXML
    JFXButton numberFive;

    @FXML
    JFXButton numberSix;

    @FXML
    JFXButton numberSeven;

    @FXML
    JFXButton numberEight;

    @FXML
    JFXButton numberNine;

    @FXML
    JFXButton numberZero;

    @FXML
    JFXButton confirmButton;

    @FXML
    Text warningText;

    @FXML
    Text passwordInput1;

    @FXML
    Text passwordInput2;

    @FXML
    Text passwordInput3;

    @FXML
    Text passwordInput4;

    @FXML
    JFXButton clearButton;




    GeneralController source;
    ActionEvent origin;



    String password = "1111";
    String input="";

    public void closeScreen(ActionEvent event) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }


    public void addZero(){
        input+= numberZero.getText();
        warningText.setText(" ");
    }

    public void updatePasswordText(){

        if (input.length() == 0) {
            passwordInput1.setText("  *");

        }
        if(input.length() == 1){
            passwordInput2.setText("  *");

        }
        if(input.length() == 2){
            passwordInput3.setText(" *");

        }
        if(input.length() == 3){
            passwordInput4.setText(" *");
        }

        if(input.length() > 3) {
            warningText.setText("Password must be of length 4");
        }
    }

    public void addOne() {

        warningText.setText(" ");
        updatePasswordText();
        input += numberOne.getText();

    }

    public void addTwo(){

        warningText.setText(" ");
        updatePasswordText();
        input+= numberTwo.getText();

    }

    public void addThree(){

        warningText.setText(" ");
        updatePasswordText();
        input+= numberThree.getText();

    }

    public void addFour(){

        warningText.setText(" ");
        updatePasswordText();
        input+= numberFour.getText();

    }

    public void addFive(){

        warningText.setText(" ");
        updatePasswordText();
        input+= numberFive.getText();

    }

    public void addSix(){

        warningText.setText(" ");
        updatePasswordText();
        input+= numberSix.getText();

    }

    public void addSeven(){

        warningText.setText(" ");
        updatePasswordText();
        input+= numberSeven.getText();

    }

    public void addEight(){

        warningText.setText(" ");
        updatePasswordText();
        input+= numberEight.getText();

    }

    public void addNine(){

        warningText.setText(" ");
        updatePasswordText();
        input+= numberNine.getText();

    }


    public void checkPassword(ActionEvent event){
        if(input.equals(password)){
            source.launchAdminScreen(origin);
            closeScreen(event);
        }

        if(input.length() == 4){
            warningText.setText("Incorrect Password");

        }
        if(input.length() != 4){
            warningText.setText("Password must be of length 4");
        }
        passwordInput1.setText("");
        passwordInput2.setText("");
        passwordInput3.setText("");
        passwordInput4.setText("");
        input = "";
    }

    public void setSource(GeneralController controller) {
        source = controller;
    }
    public void setEvent(ActionEvent event) {
        origin = event;
    }

}
