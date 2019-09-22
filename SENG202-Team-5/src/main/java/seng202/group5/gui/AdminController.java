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
import seng202.group5.information.MenuItem;
import seng202.group5.logic.Finance;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

/**
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
    private Button importStockButton;

    @FXML
    private Button importMenuButton;

    @FXML
    private Button importHistoryButton;

    @FXML
    private Button importFinanceButton;

    @FXML
    private Text fileNotificationText;

    @FXML
    private Button addButton;

    private Finance finance;

    @FXML
    private TableView<MenuItem> itemTable;

    @FXML
    private TableColumn<MenuItem, String> nameCol;

    @FXML
    private TableColumn dietaryCol;

    @FXML
    private TableColumn<MenuItem, String> sellingPriceCol;

    private FileChooser fileChooser;


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

    public File getSelectedFile() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Xml Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        return selectedFile;

    }

    public boolean checkSelectedFile(String xmlFileName, File selectedFile) {
        boolean correct = false;
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            if (fileName.equals(xmlFileName)) {
                correct = true;
                fileNotificationText.setText(xmlFileName + " has been imported into the system");
            } else {
                fileNotificationText.setText("The selected file is must be " + xmlFileName);
            }

        } else {
            fileNotificationText.setText("The selected file is not valid or no file selected");
        }
        return correct;
    }

    public void importStock() {
        File selectedFile = getSelectedFile();
        if (checkSelectedFile("stock.xml", selectedFile) == true) {
            getAppEnvironment().stockXmlToObject(selectedFile.getParent());
            importMenuButton.setDisable(false);
        }
    }

    public void importMenu() {
        File selectedFile = getSelectedFile();
        if (checkSelectedFile("menu.xml", selectedFile) == true) {
            getAppEnvironment().menuXmlToObject(selectedFile.getParent());
            importHistoryButton.setDisable(false);
        }
    }

    public void importHistory() {
        File selectedFile = getSelectedFile();
        if (checkSelectedFile("history.xml", selectedFile) == true) {
            getAppEnvironment().historyXmlToObject(selectedFile.getParent());
            importFinanceButton.setDisable(false);
        }
    }

    public void importFinance() {
        File selectedFile = getSelectedFile();
        if (checkSelectedFile("finance.xml", selectedFile) == true) {
            getAppEnvironment().financeXmlToObject(selectedFile.getParent());
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
            stage.initOwner(addButton.getScene().getWindow());

            stage.showAndWait();
            pseudoInitialize();
        }
        catch (IOException e) {
            e.printStackTrace();
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
