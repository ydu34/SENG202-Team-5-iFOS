package seng202.group5.gui;

import com.jfoenix.controls.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import org.joda.money.Money;
import seng202.group5.Database;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Transaction;
import seng202.group5.logic.Finance;
import seng202.group5.logic.Till;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A controller for managing the administration screen
 *
 * @author Yu Duan, Shivin Gaba, Daniel Harris
 */
public class AdminController extends GeneralController {

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Label saleSummaryText;

    @FXML
    private Button exportDataButton;

    @FXML
    private Button selectStockButton;

    @FXML
    private Button selectMenuButton;

    @FXML
    private Button selectFinanceButton;

    @FXML
    private Button importDataButton;

    @FXML
    private Button selectImagesFolderButton;

    @FXML
    private Text fileNotificationText;

    @FXML
    private Text exportNotificationText;

    @FXML
    private JFXTextField imageLocation;

    @FXML
    private Text stockWarningText;

    @FXML
    private Text menuWarningText;

    @FXML
    private Text historyWarningText;

    @FXML
    private Text financeWarningText;

    @FXML
    private LineChart<String, Integer> financeGraph;

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

    @FXML
    private Text infoText;

    @FXML
    private Text warningText;

    @FXML
    private JFXTabPane adminTabPane;
    @FXML
    private JFXComboBox<Database.OverwriteType> dataMergeTypeMenu;

    private Map<String, File> fileMap;

    @FXML
    private JFXCheckBox autosaveCheckbox;

    @FXML
    private JFXCheckBox autoloadCheckbox;

    @FXML
    private JFXTextField autoLocation;

    @FXML
    private Spinner<Integer> spinner10c;
    @FXML
    private Spinner<Integer> spinner20c;
    @FXML
    private Spinner<Integer> spinner50c;
    @FXML
    private Spinner<Integer> spinner1d;
    @FXML
    private Spinner<Integer> spinner2d;
    @FXML
    private Spinner<Integer> spinner5d;
    @FXML
    private Spinner<Integer> spinner10d;
    @FXML
    private Spinner<Integer> spinner20d;
    @FXML
    private Spinner<Integer> spinner50d;
    @FXML
    private Spinner<Integer> spinner100d;
    @FXML
    private Text promptText;
    @FXML
    private Text oldPasswordWarning;
    @FXML
    private Text newPasswordWarning;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXPasswordField oldPasswordText;
    @FXML
    private JFXPasswordField newPasswordText;
    @FXML
    private JFXPasswordField confirmPasswordText;

    private ArrayList<Spinner<Integer>> spinnerList;

    /**
     * An initializer for this controller
     */
    @Override
    public void pseudoInitialize() {
        super.pseudoInitialize();
        finance = getAppEnvironment().getFinance();
        financeInitialize();
        viewHistory();

        recipeTableInitialize();
        fileMap = new HashMap<>();

        textFieldListeners(oldPasswordText);
        textFieldListeners(newPasswordText);
        textFieldListeners(confirmPasswordText);

        // Creating listeners for each spinner in the TillManager
        spinnerList = new ArrayList<>(Arrays.asList(
                spinner10c, spinner20c, spinner50c, spinner1d, spinner2d, spinner5d, spinner10d,
                spinner20d, spinner50d, spinner100d));
        updateTillSpinners();

        for (Spinner<Integer> spinner : spinnerList) {
            spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
            Money amount = finance.getDenomination().get(spinnerList.size() - spinnerList.indexOf(spinner) - 1);
            spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (oldValue > newValue) {
                    try {
                        finance.getTill().removeDenomination(amount, oldValue - newValue);
                    } catch (InsufficientCashException e) {

                    }
                } else if (newValue > oldValue) {
                    finance.getTill().addDenomination(amount, newValue - oldValue);
                }
            });
        }

        // Setting the text for the overwrite setting of the database
        dataMergeTypeMenu.setItems(FXCollections.observableArrayList(Database.OverwriteType.values()));
        dataMergeTypeMenu.setValue(getAppEnvironment().getDatabase().getOverwriteSetting());

        // Setting initial values for autosaving/loading elements
        autosaveCheckbox.setSelected(getAppEnvironment().getDatabase().isAutosaveEnabled());
        autoloadCheckbox.setSelected(getAppEnvironment().getDatabase().isAutoloadEnabled());
        autoLocation.setText(getAppEnvironment().getDatabase().getSaveFileLocation());

        if (!getAppEnvironment().getImagesFolderPath().equals(""))
            imageLocation.setText(getAppEnvironment().getImagesFolderPath());

        // Disables buttons if an order is in progress
        checkIfOrderInProgress();
    }

    /**
     * This function adds listeners to the text fields under the password setting tab pane and only allow the user to enter a
     * a 4 digit pin. It also prevents the user form typing and special characters or alphabets.
     * @param someTextField
     */

    public void textFieldListeners(JFXPasswordField someTextField){

        someTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,4}?")) {
                someTextField.setText(oldValue);
            }
        });

    }

    /**
     * If an order is in progress disable buttons on admin screen.
     */
    public void checkIfOrderInProgress() {

        try {
            System.out.println(getAppEnvironment().getOrderManager().getOrder().getOrderItems().values());
            if (!getAppEnvironment().getOrderManager().getOrder().getOrderItems().isEmpty()) {
                infoText.setText("Can not Add/Modify/Delete Menu Item when Order is in progress.");
                selectFinanceButton.setDisable(true);
                selectMenuButton.setDisable(true);
                selectStockButton.setDisable(true);
                exportDataButton.setDisable(true);
                addButton.setDisable(true);
                modifyButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        } catch (NoOrderException e) {
            e.printStackTrace();
        }
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
        itemTable.getSortOrder().add(nameCol);
        itemTable.sort();
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
            saleSummaryText.setText("Total cost of orders: " + result.get(0) +
                    "\nAverage daily cost: " + result.get(1) +
                    "\nTotal profits: " + result.get(2) +
                    "\nAverage daily profits: " + result.get(3));
        } else {
            saleSummaryText.setText("End date is before start date");
        }
        updateFinanceGraph();
    }

    /**
     * Initializes the start date and end date pickers for the finance tab
     */
    private void financeInitialize() {
        LocalDate minDate = LocalDate.MAX;
        LocalDate maxDate = LocalDate.MIN;
        for (Transaction transaction : finance.getTransactionHistory().values()) {
            if (transaction.getDateTime().toLocalDate().isBefore(minDate))
                minDate = transaction.getDateTime().toLocalDate();
            if (transaction.getDateTime().toLocalDate().isAfter(maxDate))
                maxDate = transaction.getDateTime().toLocalDate();
        }
        if (!minDate.isAfter(maxDate)) {
            startDate.setValue(minDate);
            endDate.setValue(maxDate);
        } else {
            startDate.setValue(LocalDate.now());
            endDate.setValue(LocalDate.now());
        }
        startDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate tempEndDate = endDate.getValue();
                endDate.valueProperty().addListener((unused, old, newObj) -> this.setDisable(date.isAfter(newObj)));
                if (tempEndDate == null) {
                    setDisable(empty);
                } else {
                    setDisable(empty || date.isAfter(tempEndDate));
                }
            }
        });
        startDate.setConverter(new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String input) {
                LocalDate date = super.fromString(input);
                LocalDate tempEndDate = endDate.getValue();
                if (tempEndDate != null && date.isAfter(tempEndDate)) {
                    date = tempEndDate;
                }
                return date;
            }
        });

        endDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate tempStartDate = startDate.getValue();
                startDate.valueProperty().addListener((unused, old, newObj) -> this.setDisable(date.isBefore(newObj)));
                if (tempStartDate == null) {
                    setDisable(empty);
                } else {
                    setDisable(empty || date.isBefore(tempStartDate));
                }
            }
        });
        endDate.setConverter(new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String input) {
                LocalDate date = super.fromString(input);
                LocalDate tempStartDate = startDate.getValue();
                if (tempStartDate != null && date.isBefore(tempStartDate)) {
                    date = tempStartDate;
                }
                return date;
            }
        });
    }

    /**
     * Updates the graph containing profits for the specified period
     */
    private void updateFinanceGraph() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        startDate.getValue().datesUntil(endDate.getValue().plusDays(1))
                .forEach((date) -> series.getData().add(new XYChart.Data<>(DateTimeFormatter.ofPattern("dd-MM").format(date),
                        finance.totalCalculator(LocalDateTime.of(date, LocalTime.MIN),
                                LocalDateTime.of(date, LocalTime.MAX)).get(2).getAmountMajorInt())));
        financeGraph.setCreateSymbols(false);
        financeGraph.getData().clear();
        financeGraph.getData().add(series);
        financeGraph.setTitle(String.format("Total profits in NZD from %s to %s",
                DateTimeFormatter.ofPattern("dd-MM-yy").format(startDate.getValue()),
                DateTimeFormatter.ofPattern("dd-MM-yy").format(endDate.getValue())));
        financeGraph.setLegendVisible(false);
    }

    /**
     * Gets the file that the user selects, limits the user to only select xml files
     * @return the selected file from the file chooser.
     */
    public File getSelectedFile() {
        FileChooser fileChooser = new FileChooser();
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
     * @return whether or not the correct file is selected
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
     * Checks if the number of files selected by the user is three, if it is
     * it means that all xml files are selected and ready to be imported.
     * Therefore enable the import data button for the user to click.
     */
    public void checkFilesSelected() {
        if (fileMap.size() >= 1) {
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
        try {
            getAppEnvironment().getDatabase().importData(fileMap);
            finance = getAppEnvironment().getFinance();
            fileNotificationText.setText("All xml files successfully uploaded into application!");
            updateTillSpinners();
        } catch (Exception e) {
            fileNotificationText.setText(e.getMessage());
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

    /**
     * Action for modifyButton, opens a new window for the use to modify the menu item.
     */
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
                stage.setScene(new Scene(root, 800, 600));
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
                getAppEnvironment().getDatabase().allObjectsToXml(selectedDirectory.getPath());
                exportNotificationText.setText("All files successfully exported!");
            } catch (Exception e) {
                exportNotificationText.setText("Files failed to export, please try again.");
            }
        }

    }

    /**
     * The method called when selectImagesFolderButton is clicked
     * Allows the user to select the folder containing all the images for the menu items
     */
    @FXML
    public void selectImagesFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            String imageFolderPath = selectedDirectory.getPath();
            getAppEnvironment().setImagesFolderPath(imageFolderPath);
            imageLocation.setText(imageFolderPath);
        }
    }

    /**
     * Sets the location where automatically saved/loaded files are found
     */
    public void updateAutoLocation() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            String autoLocationString = selectedDirectory.getPath();
            getAppEnvironment().getDatabase().setSaveFileLocation(autoLocationString);
            autoLocation.setText(autoLocationString);
        }
    }

    /**
     * Updates the autosave feature of the database
     */
    public void updateAutosave() {
        getAppEnvironment().getDatabase().setAutosaveEnabled(autosaveCheckbox.isSelected());
    }

    /**
     * Updates the autoload feature of the database
     */
    public void updateAutoload() {
        getAppEnvironment().getDatabase().setAutoloadEnabled(autoloadCheckbox.isSelected());
    }

    /**
     * Sets the overwrite setting of the database
     */
    public void setOverwriteType() {
        getAppEnvironment().getDatabase().setOverwriteSetting(dataMergeTypeMenu.getValue());
    }

    /**
     * Resets the till so that it contains no amounts of any denominations
     */
    public void resetTill() {
        finance.setTill(new Till(finance.getDenomination()));
        updateTillSpinners();
    }

    private void updateTillSpinners() {
        for (Spinner<Integer> spinner : spinnerList) {
            Money amount = finance.getDenomination().get(spinnerList.size() - spinnerList.indexOf(spinner) - 1);
            Integer initialValue = finance.getTill().getDenominations().getOrDefault(amount, 0);
            spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                    0, 999999, initialValue));
        }
    }


    public void setFinance(Finance newFinance) {
        finance = newFinance;
    }

    /**
     * This function checks all the fields on password settings screen and checks if they are valid and accordingly allows
     * user to update the password.
     * @param event
     */

    public void updateOldPassword(ActionEvent event) {

        if (getAppEnvironment().getDatabase().validatePassword(oldPasswordText.getText().hashCode())) {
            if (newPasswordText.getText().equals(confirmPasswordText.getText())){
                if(newPasswordText.getText().length() == 4 && confirmPasswordText.getText().length() == 4){
                    oldPasswordWarning.setText("");
                    newPasswordWarning.setFill(Color.GREEN);
                    newPasswordWarning.setText("Password updated!!");
                    getAppEnvironment().getDatabase().setPasswordHash(newPasswordText.getText().hashCode());
                }
                else if (newPasswordText.getText().length() < 4 || confirmPasswordText.getText().length() < 4){
                    newPasswordWarning.setFill(Color.RED);
                    newPasswordWarning.setText("Password has to be 4 digit long");
                    oldPasswordWarning.setText("");
                }
                }
            else{
                newPasswordWarning.setFill(Color.RED);
                newPasswordWarning.setText("The new password and confirm password does not match");
                oldPasswordWarning.setText("");
            }
        }
        else{
            oldPasswordWarning.setFill(Color.RED);
            oldPasswordWarning.setText("Password does not match the old password");

        }
    }
}


