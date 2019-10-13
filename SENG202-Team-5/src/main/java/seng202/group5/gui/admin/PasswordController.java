package seng202.group5.gui.admin;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group5.gui.GeneralController;

/**
 * A controller for managing the password feature in the application
 *
 * @author Shivin Gaba
 */

public class PasswordController extends GeneralController {

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton numberOne;

    @FXML
    private JFXButton numberTwo;

    @FXML
    private JFXButton numberThree;
    @FXML
    private JFXButton numberFour;
    @FXML
    private JFXButton numberFive;

    @FXML
    private JFXButton numberSix;

    @FXML
    private JFXButton numberSeven;

    @FXML
    private JFXButton numberEight;

    @FXML
    private JFXButton numberNine;

    @FXML
    private JFXButton numberZero;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private Text warningText;

    @FXML
    private Text passwordInput1;

    @FXML
    private Text passwordInput2;

    @FXML
    private Text passwordInput3;

    @FXML
    private Text passwordInput4;


    private GeneralController source;
    private ActionEvent origin;


    private IntArgReturnsBool passwordChecker;

    /**
     * The string used to check the validity of the password
     */
    private String input = "";

    /**
     * This function closes the password pop up screen when clicked on cancel
     */
    public void closeScreen() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }



    /**
     * The function sets the text fields to  "*" when the buttons are clicked on.
     */

    private void updatePasswordText() {

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

    public void addZero() {
        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberZero.getText()));
        checkPassword();
    }

    /**
     * Appends 1 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */
    public void addOne() {

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberOne.getText()));
        checkPassword();

    }

    /**
     * Appends 2 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */
    public void addTwo() {

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberTwo.getText()));
        checkPassword();

    }

    /**
     * Appends 3 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */
    public void addThree() {

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberThree.getText()));
        checkPassword();

    }

    /**
     * Appends 4 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */
    public void addFour() {

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberFour.getText()));
        checkPassword();

    }

    /**
     * Appends 5 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */
    public void addFive() {

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberFive.getText()));
        checkPassword();

    }

    /**
     * Appends 6 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addSix() {

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberSix.getText()));
        checkPassword();

    }

    /**
     * Appends 7 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addSeven() {

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberSeven.getText()));
        checkPassword();

    }

    /**
     * Appends 8 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addEight() {

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberEight.getText()));
        checkPassword();

    }

    /**
     * Appends 9 to the password string and checks if the password it still of the valid length updates the warning text accordingly
     */

    public void addNine() {

        warningText.setText(" ");
        updatePasswordText();
        addNumber((numberNine.getText()));
        checkPassword();

    }


    /**
     * This function is called when the confirm button is clicked which checks if th user has entered a valid password or not and
     * then displays the warning accordingly.
     */


    public void checkPassword() {
        if (input.length() == 4) {
            if (passwordChecker.check(input.hashCode())) {
                source.launchAdminScreen(origin);
                closeScreen();
            } else {
                warningText.setText("Incorrect Password");
                clearPassword();
            }
        }
    }

    /**
     * Takes a number and adds it to the password, for checking against the set password, if the current passwords length
     * is greater than 4, then no other number is added to the password.
     * @param num the number to be added to the password.
     */
    private void addNumber(String num) {
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
        input = "";
    }

    public void setSource(GeneralController controller) {
        source = controller;
    }
    public void setEvent(ActionEvent event) {
        origin = event;
    }

    public void setPasswordChecker(IntArgReturnsBool checker) {
        passwordChecker = checker;
    }

    public interface IntArgReturnsBool {

        boolean check(int passwordHash);
    }



}
