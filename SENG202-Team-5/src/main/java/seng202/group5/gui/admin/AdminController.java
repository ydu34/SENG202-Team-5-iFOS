package seng202.group5.gui.admin;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.joda.money.Money;
import seng202.group5.Database;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.CustomerSettings;
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
    private Button selectCustomersButton;

    @FXML
    private Button selectSettingsButton;

    @FXML
    private Button importDataButton;

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
    private Text customersWarningText;

    @FXML
    private Text financeWarningText;

    @FXML
    private LineChart<Number, Number> financeGraph;

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
    private Text oldPasswordWarning;
    @FXML
    private Text newPasswordWarning;
    @FXML
    private JFXPasswordField oldPasswordText;
    @FXML
    private JFXPasswordField newPasswordText;
    @FXML
    private JFXPasswordField confirmPasswordText;

    private ArrayList<Spinner<Integer>> spinnerList;

    /**
     * The text field for changing the initial purchase points.
     */
    @FXML
    private TextField initialPointsField;

    /**
     * The text field for changing the ratio.
     */
    @FXML
    private TextField ratioField;

    /**
     * The text field for changing the value of points.
     */
    @FXML
    private TextField pointValueField;

    /**
     * The text field for changing the max ingredients in addExtraIngredient.
     */
    @FXML
    private TextField maxIngredientField;

    /**
     * The label on the settings screen which shows success when values are saved.
     */
    @FXML
    private Label successLabel;

    /**
     * The button for saving the values of the settings screen.
     */
    @FXML
    private Button saveValuesButton;

    /**
     * An initializer for this controller.
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

        // Initialise the settings
        initialiseSettings();

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
                    } catch (InsufficientCashException ignored) {

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
     * a 4 digit pin. It also prevents the user from typing and special characters or alphabets.
     *
     * @param someTextField the text field to add the listener to
     */
    private void textFieldListeners(JFXPasswordField someTextField) {

        someTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,4}?")) {
                someTextField.setText(oldValue);
            }
        });

    }

    /**
     * If an order is in progress, disables buttons on admin screen.
     */
    private void checkIfOrderInProgress() {
        if (!getAppEnvironment().getOrderManager().getOrder().getOrderItems().isEmpty()) {
            infoText.setText("Can not Add/Modify/Delete Menu Item when Order is in progress.");
            selectFinanceButton.setDisable(true);
            selectMenuButton.setDisable(true);
            selectStockButton.setDisable(true);
            selectCustomersButton.setDisable(true);
            exportDataButton.setDisable(true);
            addButton.setDisable(true);
            modifyButton.setDisable(true);
            deleteButton.setDisable(true);
            selectSettingsButton.setDisable(true);

            // For the Settings screen
            saveValuesButton.setDisable(true);
            successLabel.setText("Order in Progress!");
            successLabel.setTextFill(Color.RED);
        }
    }


    private void recipeTableInitialize() {
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
            startDate.setValue(minDate.minusDays(1));
            endDate.setValue(maxDate.plusDays(1));
        } else {
            startDate.setValue(LocalDate.now().minusDays(1));
            endDate.setValue(LocalDate.now().plusDays(1));
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
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        startDate.getValue().datesUntil(endDate.getValue().plusDays(1))
                .forEach((date) -> series.getData().add(new XYChart.Data<>((int) date.toEpochDay(),
                        finance.totalCalculator(LocalDateTime.of(date, LocalTime.MIN),
                                LocalDateTime.of(date, LocalTime.MAX)).get(2).getAmountMajorInt())));
        financeGraph.getXAxis().setAutoRanging(false);
        ((NumberAxis) financeGraph.getXAxis()).setLowerBound(startDate.getValue().toEpochDay());
        ((NumberAxis) financeGraph.getXAxis()).setUpperBound(endDate.getValue().toEpochDay());
        ((NumberAxis) financeGraph.getXAxis()).setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number object) {
                return DateTimeFormatter.ofPattern("dd-MM").format(LocalDate.ofEpochDay(object.intValue()));
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        });
        ((NumberAxis) financeGraph.getYAxis()).setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return String.format("NZD %.2f", object);
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        });
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
     *
     * @return the selected file from the file chooser.
     */
    private File getSelectedFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Xml Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        fileNotificationText.setText(null);
        return selectedFile;

    }

    /**
     * Compares the xml file name with the selected file name to see if the correct file
     * is selected.
     *
     * @param xmlFileName  The name of the xml file with .xml
     * @param selectedFile The selected file that the user selects
     * @return whether or not the correct file is selected
     */
    private boolean checkSelectedFile(String xmlFileName, File selectedFile) {
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
     * Checks if the number of files selected by the user is 5, if it is
     * it means that all xml files are selected and ready to be imported.
     * Therefore enable the import data button for the user to click.
     */
    private void checkFilesSelected() {
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
     * Action for the select settings button to add the selected file to the list of files
     * if it is settings.xml otherwise tell the user it is invalid.
     */
    public void selectSettings() {
        File selectedFile = getSelectedFile();
        if (checkSelectedFile("settings.xml", selectedFile)) {
            fileMap.put("settings.xml", selectedFile);
            selectSettingsButton.setText("Selected Settings!");
            checkFilesSelected();
        } else {
            selectSettingsButton.setText("Invalid settings.xml!");
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

    public void selectCustomers() {
        File selectedFile = getSelectedFile();
        if (checkSelectedFile("customers.xml", selectedFile)) {
            fileMap.put("customers.xml", selectedFile);
            customersWarningText.setText("customers.xml selected");
            checkFilesSelected();
        } else {
            customersWarningText.setText("invalid file selected");
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
            recipeTableInitialize();
            initialiseSettings();
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

    /**
     * Sets the finance.
     *
     * @param newFinance the new finance to set.
     */
    public void setFinance(Finance newFinance) {
        finance = newFinance;
    }

    /**
     * This function checks all the fields on password settings screen and checks if they are valid and accordingly allows
     * user to update the password.
     */
    public void updateOldPassword() {

        if (getAppEnvironment().getDatabase().validatePassword(oldPasswordText.getText().hashCode())) {
            if (newPasswordText.getText().equals(confirmPasswordText.getText())) {
                if (newPasswordText.getText().length() == 4 && confirmPasswordText.getText().length() == 4) {
                    oldPasswordWarning.setText("");
                    newPasswordWarning.setFill(Color.GREEN);
                    newPasswordWarning.setText("Password updated!!");
                    getAppEnvironment().getDatabase().setPasswordHash(newPasswordText.getText().hashCode());
                } else if (newPasswordText.getText().length() < 4 || confirmPasswordText.getText().length() < 4) {
                    newPasswordWarning.setFill(Color.RED);
                    newPasswordWarning.setText("Password has to be 4 digit long");
                    oldPasswordWarning.setText("");
                }
            } else {
                newPasswordWarning.setFill(Color.RED);
                newPasswordWarning.setText("The new password and confirm password does not match");
                oldPasswordWarning.setText("");
            }
        } else {
            oldPasswordWarning.setFill(Color.RED);
            oldPasswordWarning.setText("Password does not match the old password");

        }
    }

    /**
     * The template to initialise all the Settings TextFields.
     *
     * @param field the TextField to add a listener to.
     */
    private void initialiseSettingsTextFields(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}")) {
                field.setText(oldValue);
            }
            if (newValue.isBlank() || newValue.isEmpty()) {
                field.setText("0");
            } else if (newValue.matches("0[\\d]")) {
                field.setText(newValue.replace("0", ""));
            }
            successLabel.setText("");
        });
    }

    /**
     * Initialises the Settings tab.
     */
    private void initialiseSettings() {
        // Initialise all the settings text fields
        initialiseSettingsTextFields(initialPointsField);
        initialiseSettingsTextFields(ratioField);
        initialiseSettingsTextFields(pointValueField);
        initialiseSettingsTextFields(maxIngredientField);

        // Initialise Loyalty System fields
        CustomerSettings settings = getAppEnvironment().getCustomers().getCustomerSettings();
        initialPointsField.setText(String.valueOf(settings.getInitialPurchasePoints()));
        ratioField.setText(String.valueOf(settings.getRatio()));
        pointValueField.setText(String.valueOf(settings.getPointValue()));

        // Initialise Max Ingredient field
        maxIngredientField.setText(String.valueOf(getAppEnvironment().getSettings().getMaxIngredientAmount()));
    }

    /**
     * Saves the changes to the loyalty system.
     */
    @FXML
    public void saveSettings() {
        if (Integer.parseInt(maxIngredientField.getText()) < 5) {
            successLabel.setText("At least 5 Ingredients!");
            successLabel.setTextFill(Color.RED);
        } else if (Integer.parseInt(ratioField.getText()) == 0) {
            successLabel.setText("Ratio must be at least 1!");
            successLabel.setTextFill(Color.RED);
        }
        else {
            // Saving Max % of Ingredients
            getAppEnvironment().getSettings().setMaxIngredientAmount(Integer.parseInt(maxIngredientField.getText()));

            // Saving Loyalty System fields
            int newInitialPurchasePoints = Integer.parseInt(initialPointsField.getText());
            int newRatio = Integer.parseInt(ratioField.getText());
            int newPointValue = Integer.parseInt(pointValueField.getText());

            CustomerSettings settings = getAppEnvironment().getCustomers().getCustomerSettings();
            settings.setInitialPurchasePoints(newInitialPurchasePoints);
            settings.setRatio(newRatio);
            settings.setPointValue(newPointValue);

            successLabel.setText("Values Saved!");
            successLabel.setTextFill(Color.GREEN);
        }
    }

}


