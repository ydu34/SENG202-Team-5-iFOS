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


    /**
     * Default password that can be used to access the admin screen at the beginning of of the day
     */
    String password = "1111";

    /**
     * The string used to check the validity of the password
     */
    String input="";

    /**
     * This function closes the password pop up screen when clicked on cancel
     * @param event
     */

    public void closeScreen(ActionEvent event) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }


    /**
     * The function sets the text fields to  "*" when the buttons are clicked on.
     */

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

    /**
     * Appends 0 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addZero(){
        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberZero.getText()));
    }

    /**
     * Appends 1 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addOne() {

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberOne.getText()));

    }

    /**
     * Appends 2 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addTwo(){

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberTwo.getText()));

    }

    /**
     * Appends 3 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addThree(){

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberThree.getText()));

    }

    /**
     * Appends 4 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addFour(){

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberFour.getText()));

    }

    /**
     * Appends 5 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addFive(){

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberFive.getText()));

    }

    /**
     * Appends 6 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addSix(){

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberSix.getText()));

    }

    /**
     * Appends 7 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addSeven(){

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberSeven.getText()));

    }

    /**
     * Appends 8 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addEight(){

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberEight.getText()));

    }

    /**
     * Appends 9 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addNine(){

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberNine.getText()));

    }

    /**
     * This function is called when the confirm button is clicked which checks if th user has entered a valid password or not and
     * then displays the warning accordingly.
     * @param event
     */


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
    }

    /**
     * Takes a number and adds it to the password, for checking against the set password, if the current passwords length
     * is greater than 4, then no other number is added to the password.
     * @param num the number to be added to the password.
     */
    public void addNumber(String num) {
        if (input.length() < 4) {
            input += num;
        } else {
            warningText.setText("Password must be of length 4");
        }
    }

    /**
     * This function clears the password that was in progress and lets the user enter the password again
     */

    public void clearPassword(){

        passwordInput1.setText("");
        passwordInput2.setText("");
        passwordInput3.setText("");
        passwordInput4.setText("");
        warningText.setText("");
        input = "";


    }

    public void setSource(GeneralController controller) {
        source = controller;
    }
    public void setEvent(ActionEvent event) {
        origin = event;
    }

}
