package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.joda.money.Money;
import org.joda.time.DateTime;
import seng202.group5.Finance;
import seng202.group5.Order;
import seng202.group5.exceptions.InsufficientCashException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Yu Duan
 */
public class AdminController extends GeneralController {

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Text saleSummaryText;

    @FXML
    private Button selectFilesButton;

    @FXML
    private Button exportDataButton;

    @FXML
    private ListView importFilesListView;

    @FXML
    private Button clearListButton;

    @FXML
    private Button importFilesButton;

    @FXML
    private Text fileNotificationText;

    private Finance finance = new Finance();

    @FXML
    public void viewHistory() {
        DateTime eDate = DateTime.parse(endDate.getValue().toString());
        DateTime sDate = DateTime.parse(startDate.getValue().toString());
        if (!eDate.isBefore(sDate)) {
            ArrayList<Money> result = finance.totalCalculator(sDate, eDate);
            saleSummaryText.setText("Testresult\nTotal cost of orders: " + result.get(0) + "\nAverage daily cost: " + result.get(1));
        } else {
            saleSummaryText.setText("End date is before start date");
        }

    }

    /**
     * The method called when importData button is clicked
     * Allows th user to select xml files that they want to import
     */
    public void selectFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Xml Files", "*.xml"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            for (int i = 0; i < selectedFiles.size(); i++) {
                importFilesListView.getItems().add(selectedFiles.get(i).getName());
            }
        } else {
            System.out.println("file is not valid");
            fileNotificationText.setText("The selected file is not valid");
        }
    }


    /**
     * Imports all the files in the importFilesListView by converting
     * all the xml files to objects.
     */
    public void importFiles() {

    }



    /**
     * Empties the list of files selected
     */
    public void clearList() {
        importFilesListView.getItems().clear();
    }

    /**
     * The method called when exportData button is clicked
     * Converts the objects into xml files and exported to the selected directory.
     */
    public void exportData() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            System.out.println(selectedDirectory.getAbsolutePath());
        } else {
            System.out.println("No directory selected");
        }

    }



    public void setFinance(Finance newFinance) {
        finance = newFinance;
    }
}
