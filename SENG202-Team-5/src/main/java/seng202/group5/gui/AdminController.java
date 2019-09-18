package seng202.group5.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.Finance;
import seng202.group5.MenuItem;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yu Duan
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

    @FXML
    private Button addButton;

    private Finance finance;

    private List<File> selectedFiles;

    @FXML
    private TableView<MenuItem> itemTable;

    @FXML
    private TableColumn<MenuItem, String> nameCol;

    @FXML
    private TableColumn dietaryCol;

    @FXML
    private TableColumn<MenuItem, String> sellingPriceCol;



    @Override
    public void pseudoInitialize() {
        finance = getAppEnvironment().getFinance();
        recipeTableInitialize();

    }

    public void recipeTableInitialize() {
        ObservableList<MenuItem> items = FXCollections.observableArrayList(getAppEnvironment().getMenuManager().getMenuItems().values());

        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        sellingPriceCol.setCellValueFactory(new PropertyValueFactory<>("markupCost"));
        itemTable.setItems(items);
    }

    public void selectTime(javafx.event.ActionEvent actionEvent) {
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    }

    @FXML
    public void viewHistory() {
        LocalDateTime eDate = LocalDateTime.of(endDate.getValue(), LocalTime.MIN);
        LocalDateTime sDate = LocalDateTime.of(startDate.getValue(), LocalTime.MAX);
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
        selectedFiles = fileChooser.showOpenMultipleDialog(null);

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
        if (selectedFiles != null) {
            for (int i = 0; i < selectedFiles.size(); i++) {
                String fileName = selectedFiles.get(i).getName();
                switch(fileName) {
                    case "stock.xml": getAppEnvironment().stockXmlToObject(selectedFiles.get(i).getParent());
                        break;
                    case "history.xml": getAppEnvironment().historyXmlToObject(selectedFiles.get(i).getParent());
                        break;
                    case "finance.xml": getAppEnvironment().financeXmlToObject(selectedFiles.get(i).getParent());
                        break;
                    case "menu.xml": getAppEnvironment().menuXmlToObject(selectedFiles.get(i).getParent());
                        break;
                    case "till.xml": getAppEnvironment().tillXmlToObject(selectedFiles.get(i).getParent());
                        break;
                }

            }
            clearList();
        } else {
            fileNotificationText.setText("No files selected");
        }


    }

    public void addRecipe() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/addRecipe.fxml"));
            Parent root = loader.load();

            AddRecipeController controller = loader.<AddRecipeController>getController();
            System.out.println(getAppEnvironment());
            controller.setAppEnvironment(getAppEnvironment());
            controller.pseudoInitialize();

            Stage stage = new Stage();
            stage.setTitle("Add a Recipe");
            stage.setScene(new Scene(root, 600, 600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Empties the list of files selected
     */
    public void clearList() {
        selectedFiles = new ArrayList<>();
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
            getAppEnvironment().allObjectsToXml(selectedDirectory.getPath());
        } else {
            System.out.println("No directory selected");
        }

    }


    public void setFinance(Finance newFinance) {
        finance = newFinance;
    }
}
