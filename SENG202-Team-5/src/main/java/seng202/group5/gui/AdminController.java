package seng202.group5.gui;

import javafx.beans.property.SimpleStringProperty;
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
import seng202.group5.information.MenuItem;
import seng202.group5.logic.Finance;
import seng202.group5.logic.History;
import seng202.group5.logic.MenuManager;
import seng202.group5.logic.Stock;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A controller for managing the administration screen
 * @author Yu Duan
 */
public class AdminController extends GeneralController {

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextArea saleSummaryText;

    @FXML
    private Button exportDataButton;

    @FXML
    private Button selectStockButton;

    @FXML
    private Button selectMenuButton;

    @FXML
    private Button selectHistoryButton;

    @FXML
    private Button selectFinanceButton;

    @FXML
    private Button importDataButton;

    @FXML
    private Text fileNotificationText;

    @FXML
    private Text stockWarningText;

    @FXML
    private Text menuWarningText;

    @FXML
    private Text historyWarningText;

    @FXML
    private Text financeWarningText;

    @FXML
    private Button addButton;

    private Finance finance;

    @FXML
    private TableView<MenuItem> itemTable;

    @FXML
    private TableColumn<MenuItem, String> nameCol;

    @FXML
    private TableColumn<MenuItem, String> dietaryCol;

    @FXML
    private TableColumn<MenuItem, String> sellingPriceCol;

    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    private FileChooser fileChooser;

    private Map<String, File> fileMap;

    /**
     * An initializer for this controller
     */
    @Override
    public void pseudoInitialize() {
        finance = getAppEnvironment().getFinance();
        recipeTableInitialize();
        fileMap = new HashMap<>();
    }

    public void recipeTableInitialize() {
        ObservableList<MenuItem> items = FXCollections.observableArrayList(getAppEnvironment().getMenuManager().getMenuItems().values());

        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        dietaryCol.setCellValueFactory(cellData -> {

            String string = cellData.getValue().getRecipe().getDietaryInformationString();

            return new SimpleStringProperty(string);
        });
        sellingPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        itemTable.getItems().clear();
        itemTable.setItems(items);
    }

    public void selectTime(javafx.event.ActionEvent actionEvent) {
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    }

    /**
     * Views information about transactions in the specified period
     */
    @FXML
    public void viewHistory() {
        LocalDateTime eDate;
        LocalDateTime sDate;
        if (endDate.getValue() != null) {
            eDate = LocalDateTime.of(endDate.getValue(), LocalTime.MAX);
        } else {
            eDate = LocalDateTime.of(LocalDate.MAX, LocalTime.MAX);
        }
        if (startDate.getValue() != null) {
            sDate = LocalDateTime.of(startDate.getValue(), LocalTime.MIN);
        } else {
            sDate = LocalDateTime.of(LocalDate.MIN, LocalTime.MIN);
        }
        if (!eDate.isBefore(sDate)) {
            ArrayList<Money> result = finance.totalCalculator(sDate, eDate);
            saleSummaryText.setText("Total cost of orders: " + result.get(0) + "\nAverage daily cost: " + result.get(1));
        } else {
            saleSummaryText.setText("End date is before start date");
        }

    }

    /**
     * Gets the file that the user selects, limits the user to only select xml files
     * @return the selected file from the file chooser.
     */
    public File getSelectedFile() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Xml Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        fileNotificationText.setText(null);
        return selectedFile;

    }

    /**
     * Compares the xml file name with the selected file name to see if the correct file
     * is selected.
     * @param xmlFileName The name of the xml file with .xml
     * @param selectedFile  The selected file that the user selects
     * @return
     */
    public boolean checkSelectedFile(String xmlFileName, File selectedFile) {
        boolean correct = false;
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            if (fileName.equals(xmlFileName)) {
                correct = true;
            }
        }
        return correct;
    }

    /**
     * Checks if the number of files selected by the user is four, if it is
     * it means that all xml files are selected and ready to be imported.
     * Therefore enable the import data button for the user to click.
     */
    public void checkFilesSelected() {
        if (fileMap.size()==4) {
            importDataButton.setDisable(false);
        } else {
            importDataButton.setDisable(true);
        }
    }

    /**
     * Action for the select stock button to add the selected file to the list of files
     * if it is stock.xml otherwise tell the user it is invalid.
     */
    public void selectStock() {
        File selectedFile = getSelectedFile();
        if (checkSelectedFile("stock.xml", selectedFile)) {
            fileMap.put("stock.xml", selectedFile);
            stockWarningText.setText("stock.xml selected");
            checkFilesSelected();
        } else {
            stockWarningText.setText("Invalid file selected");
        }
    }

    /**
     * Action for the select menu button to add the selected file to the list of files
     * if it is menu.xml otherwise tell the user it is invalid.
     */
    public void selectMenu() {
        File selectedFile = getSelectedFile();
        if (checkSelectedFile("menu.xml", selectedFile)) {
            fileMap.put("menu.xml", selectedFile);
            menuWarningText.setText("menu.xml selected");
            checkFilesSelected();
        } else {
            menuWarningText.setText("Invalid file selected");
        }

    }

    /**
     * Action for the select history button to add the selected file to the list of files
     * if it is history.xml otherwise tell the user it is invalid.
     */
    public void selectHistory() {
        File selectedFile = getSelectedFile();
        if (checkSelectedFile("history.xml", selectedFile)) {
            fileMap.put("history.xml", selectedFile);
            historyWarningText.setText("history.xml selected");
            checkFilesSelected();
        } else {
            historyWarningText.setText("invalid file selected");
        }
    }

    /**
     * Action for the select finance button to add the selected file to the list of files
     * if it is finance.xml otherwise tell the user it is invalid.
     */
    public void selectFinance() {
        File selectedFile = getSelectedFile();
        if (checkSelectedFile("finance.xml", selectedFile)) {
            fileMap.put("finance.xml", selectedFile);
            financeWarningText.setText("finance.xml selected");
            checkFilesSelected();
        } else {
            financeWarningText.setText("invalid file selected");
        }
    }

    /**
     * Gets all the xml files in the hashmap and unmarshal the xml files to objects.
     * If the files are corrupted or invalid, the user is notified.
     */
    public void importData() {
        Stock oldStock = getAppEnvironment().getStock();
        MenuManager oldMenu = getAppEnvironment().getMenuManager();
        History oldHistory = getAppEnvironment().getHistory();
        Finance oldFinance = getAppEnvironment().getFinance();
        try {
            getAppEnvironment().stockXmlToObject(fileMap.get("stock.xml").getParent());
            getAppEnvironment().menuXmlToObject(fileMap.get("menu.xml").getParent());
            getAppEnvironment().historyXmlToObject(fileMap.get("history.xml").getParent());
            getAppEnvironment().financeXmlToObject(fileMap.get("finance.xml").getParent());
            fileNotificationText.setText("All xml files successfully uploaded into application!");
        } catch (Exception e) {
            fileNotificationText.setText(e.getMessage());
            getAppEnvironment().setStock(oldStock);
            getAppEnvironment().setMenuManager(oldMenu);
            getAppEnvironment().setHistory(oldHistory);
            getAppEnvironment().setFinance(oldFinance);
        }
    }

    /**
     * Opens the add recipe screen
     */
    public void addRecipe() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/addRecipe.fxml"));
            Parent root = loader.load();

            AddRecipeController controller = loader.getController();
            controller.setAppEnvironment(getAppEnvironment());
            controller.pseudoInitialize();

            Stage stage = new Stage();
            stage.setTitle("Add a Recipe");
            stage.setScene(new Scene(root, 800, 600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addButton.getScene().getWindow());

            stage.showAndWait();
            pseudoInitialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Action for deleteButton, deletes the selected item from the menu.
     */
    public void deleteSelectedItem() {
        MenuItem selectedItem = itemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            getAppEnvironment().getMenuManager().removeItem(selectedItem.getID());
            recipeTableInitialize();
        }
    }

    public void modifySelectedItem() {
        MenuItem selectedItem = itemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/addRecipe.fxml"));
                Parent root = loader.load();

                AddRecipeController controller = loader.getController();
                controller.setAppEnvironment(getAppEnvironment());
                controller.pseudoInitialize();
                controller.setMenuItem(selectedItem);

                Stage stage = new Stage();
                stage.setTitle("Add a Recipe");
                stage.setScene(new Scene(root, 600, 600));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(addButton.getScene().getWindow());

                stage.showAndWait();
                recipeTableInitialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The method called when exportData button is clicked
     * Converts the objects into xml files and exported to the selected directory.
     */
    public void exportData() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            try {
                getAppEnvironment().allObjectsToXml(selectedDirectory.getPath());
                fileNotificationText.setText("All files successfully exported!");
            } catch (Exception e) {
                fileNotificationText.setText("Files failed to export, please try again.");
            }
        }

    }

    public void setFinance(Finance newFinance) {
        finance = newFinance;
    }
}
