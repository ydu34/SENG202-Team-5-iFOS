package seng202.group5.gui.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.gui.AddExtraIngredientController;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.information.TypeEnum;
import seng202.group5.logic.MenuManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller for addRecipe.fxml, used to create new recipes and added to the menu.
 *
 * @author Yu Duan
 */
public class AddRecipeController extends GeneralController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField markupCostField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<Ingredient> ingredientsTable;

    @FXML
    private TableColumn<Ingredient, String> ingredientCol;

    @FXML
    private TableColumn<Ingredient, String> quantityCol;

    @FXML
    private TableColumn<Ingredient, String> ingredientCostCol;

    @FXML
    private Button addIngredientButton;

    @FXML
    private Text markupCostWarningText;

    @FXML
    private Text ingredientCostText;

    @FXML
    private Text totalCostText;

    @FXML
    private Text itemNameWarningText;

    @FXML
    private ComboBox<TypeEnum> menuTypeComboBox;

    @FXML
    private TextArea recipeTextArea;

    @FXML
    private ImageView itemImage;

    @FXML
    private Button selectImageButton;

    @FXML
    private Text imageWarningText;

    private String itemImageName;

    private MenuItem item;

    private MenuManager menuManager;

    private AdminController parentController;


    /**
     * An initializer for this controller
     */
    @FXML
    public void pseudoInitialize() {
        super.pseudoInitialize();
        // Listener for the markup cost text field, stops the use of letters
        markupCostField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                markupCostField.setText(oldValue);
            }
            // This is here in case we want to get rid of the Compute Total Cost button, having computeTotalCost() here
            // means it updates as the markupCostField changes.
            // computeTotalCost();
        });

        // Initialising the screen
        menuManager = getAppEnvironment().getMenuManager();
        item = new MenuItem();
        initializeTypeComboBox();
        initializeTextValues();
        initializeImageView();
        populateIngredientsTable();

    }

    /**
     * Saves the item into the system and close this window.
     */
    public void saveRecipe() {
        try {
            saveTextFieldValues();
            System.out.println("Item image" + item.getImageString());
            menuManager.addItem(item);
            closeScreen();
        } catch (NumberFormatException e) {
            markupCostWarningText.setText("Invalid value");
        } catch (IllegalArgumentException e) {
            markupCostWarningText.setText("make sure there are no spaces");
        } catch (Exception e) {
            itemNameWarningText.setText("Invalid name");
        }
    }

    /**
     * Saves the values in the text fields into the item.
     * @throws NumberFormatException Thrown when the mark up price is negative
     * @throws IllegalArgumentException Thrown when the mark up price is illegal
     * @throws Exception Thrown when the name is null or ""
     */
    public void saveTextFieldValues() throws NumberFormatException, IllegalArgumentException, Exception{
        String name = nameField.getText();
        String markupPriceStr = markupCostField.getText();
        item.setType(menuTypeComboBox.getSelectionModel().getSelectedItem());
        try {
            Double.parseDouble(markupCostField.getText());
            Money markupPrice = Money.parse("NZD " + markupPriceStr);
            if (name == null || name.equals("")) {
                throw new Exception();
            } else {
                item.getRecipe().setName(name);
            }
            if (markupPrice.isNegative()) {
                throw new NumberFormatException();
            } else {
                item.getRecipe().setRecipeText(recipeTextArea.getText());
                item.setItemName(name);
                item.setMarkupCost(markupPrice);
                item.calculateFinalCost();
                item.setImageString(itemImageName);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Compute the total cost of the menu item and display to the user.
     */
    public void computeTotalCost() {
        markupCostWarningText.setText("");
        String markupPriceStr = markupCostField.getText();

        if (markupPriceStr == "") {
            markupPriceStr = "0";
        }

        try {
            Double.parseDouble(markupCostField.getText());
            Money markupPrice = Money.parse("NZD " + markupPriceStr);
            if (markupPrice.isNegative()) {
                markupCostWarningText.setText("Invalid value");
            } else {
                item.setMarkupCost(markupPrice);
                totalCostText.setText(item.calculateFinalCost().toString());
            }
        } catch (NumberFormatException e) {
            markupCostWarningText.setText("Invalid value");
        } catch (IllegalArgumentException e) {
            markupCostWarningText.setText("make sure there are no spaces");
        }
    }

    /**
     * Closes this window.
     */
    public void closeScreen() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    /**
     * Changes screen to the add ingredient screen to select ingredients to add to the recipe by passing in the item.
     * @param actionEvent an event that caused this to happen
     */
    public void launchAddExtraIngredientScreen(javafx.event.ActionEvent actionEvent) {
        try {
            saveTextFieldValues();
            AddExtraIngredientController controller =
                    (AddExtraIngredientController) changeScreen(actionEvent, "/gui/addExtraIngredient.fxml");
            controller.setMenuItem(item);
            controller.setOpenMode("Recipe");
            controller.updateStockRecipeMode();
            controller.initializeTable();
        } catch (NumberFormatException e) {
            markupCostWarningText.setText("Invalid value");
        } catch (Exception e) {
            itemNameWarningText.setText("Invalid name");
        }
    }

    /**
     * Populates the table using the item's ingredients and quantities.
     */
    public void populateIngredientsTable() {
        Recipe currentRecipe = item.getRecipe();
        Map<Ingredient, Integer> recipeIngredientsMap = currentRecipe.getIngredientsAmount();
        List<Ingredient> recipeIngredients = new ArrayList<>(recipeIngredientsMap.keySet());
        ingredientCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(data ->{
            int quantity = recipeIngredientsMap.get(data.getValue());
            return new SimpleStringProperty(Integer.toString(quantity));
        });
        ingredientCostCol.setCellValueFactory(data -> {
            int quantity = recipeIngredientsMap.get(data.getValue());
            Money totalPrice = data.getValue().getPrice().multipliedBy(quantity);
            return new SimpleStringProperty(totalPrice.toString());
        });
        ingredientsTable.setItems(FXCollections.observableArrayList(recipeIngredients));
        ingredientsTable.getSortOrder().add(ingredientCol);
        ingredientsTable.sort();
    }

    /**
     * Initializes the text fields with values of the item.
     */
    public void initializeTextValues() {
        nameField.setText(item.getItemName());
        recipeTextArea.setText(item.getRecipe().getRecipeText());
        ingredientCostText.setText(item.calculateMakingCost().toString());
        markupCostField.setText(item.getMarkupCost().toString().substring(4));
        totalCostText.setText(item.calculateFinalCost().toString());
    }

    /**
     * Initializes the combo box containing the menu item types.
     */
    public void initializeTypeComboBox() {
        ObservableList<TypeEnum> typeEnumOptions =
                FXCollections.observableArrayList(
                        TypeEnum.values()
                );
        menuTypeComboBox.setItems(typeEnumOptions);
        menuTypeComboBox.getSelectionModel().select(item.getItemType());
    }

    public void initializeImageView() {
        try {
            Image image = new Image(new FileInputStream(getAppEnvironment().getImagesFolderPath() + "/" + item.getImageString()));
            itemImage.setImage(image);
        } catch (Exception e) {
        }
    }

    /**
     * @param tempItem Item that has been modified in AddExtraIngredientController.
     */
    public void setMenuItem(MenuItem tempItem) {
        item = tempItem;
        initializeTextValues();
        initializeTypeComboBox();
        initializeImageView();
        populateIngredientsTable();
    }

    /** On action for button selectImageButton, opens a file chooser for the user
     * to select an image for the current menu item they are making.
     */
    @FXML
    public void addImageToItem() {
        imageWarningText.setText("");
        FileChooser fileChooser = new FileChooser();
        List<String> imageExtensions = new ArrayList<>();
        imageExtensions.add("*.png");
        imageExtensions.add("*.jpg");
        imageExtensions.add("*.jpeg");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", imageExtensions));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            if (selectedFile.length() > 250 * 1024) {
                imageWarningText.setText("maximum size 250kB");
            } else {
                try {
                    Image image = new Image(new FileInputStream(selectedFile.getPath()));
                    itemImage.setImage(image);
                    itemImageName = selectedFile.getName();
                    System.out.println(itemImageName);
                } catch (Exception e) {
                }
            }
        }




    }
}
