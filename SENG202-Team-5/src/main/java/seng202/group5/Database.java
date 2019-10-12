package seng202.group5;

import org.xml.sax.SAXException;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.information.Transaction;
import seng202.group5.logic.Finance;
import seng202.group5.logic.MenuManager;
import seng202.group5.logic.Stock;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This class containing all the methods for data importing and exporting xml files,
 * also includes auto saving methods.
 *
 * @author Yu Duan, Daniel Harris
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Database {

    @XmlTransient
    private AppEnvironment appEnvironment;

    private String saveFileLocation;
    private boolean autosaveEnabled;
    private boolean autoloadEnabled;

    /**
     * Imports selected data into the application. This overrides existing data.
     * If any of the files are not present, or are incorrect, the import is aborted
     *
     * @param fileMap A mapping from the name of the file to the file object
     * @throws Exception when the files are incorrect
     */
    public void importData(Map<String, File> fileMap) throws Exception {
        Stock oldStock = appEnvironment.getStock();
        MenuManager oldMenu = appEnvironment.getMenuManager();
        Finance oldFinance = appEnvironment.getFinance();
        try {
            if (fileMap.containsKey("stock.xml")) stockXmlToObject(fileMap.get("stock.xml").getParent());
            if (fileMap.containsKey("menu.xml")) menuXmlToObject(fileMap.get("menu.xml").getParent());
            if (fileMap.containsKey("finance.xml")) financeXmlToObject(fileMap.get("finance.xml").getParent());
        } catch (Exception e) {
            appEnvironment.setStock(oldStock);
            appEnvironment.setMenuManager(oldMenu);
            appEnvironment.setFinance(oldFinance);
            throw new Exception(e);
        }
    }

    private OverwriteType overwriteSetting = OverwriteType.MERGE_PREFER_OLD;

    public static void main(String[] args) {
        Database thing = new Database();
        thing.loadAppData();
    }

    public Database() {
    }

    public Database(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
        loadAppData();
    }

    /**
     * Marshals the given object o into a xml file
     *
     * @param c             The class of the object o
     * @param o             The object you want to marshal into xml file
     * @param fileName      The name of the xml file
     * @param fileDirectory The directory to where the file should be marshalled
     * @throws JAXBException if JAXB fails to convert the file
     */
    public void objectToXml(Class c, Object o, String fileName, String fileDirectory) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(c.cast(o), new File(fileDirectory + "/" + fileName));
    }

    /**
     * Converts the xml file to an object o
     *
     * @param c              the class to create an instance of
     * @param fileName       the name of the file to create the object from
     * @param schemaFileName the name of the schema to use to convert the file
     * @param fileDirectory  the path to the directory the file is in
     * @return an object o
     * @throws JAXBException if JAXB fails to convert the file
     * @throws SAXException  if JAXB fails to convert the file
     */
    private Object xmlToObject(Class c, String fileName, String schemaFileName, String fileDirectory) throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        //Setup schema validator
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new StreamSource(getClass().getClassLoader().getResourceAsStream("schema/" + schemaFileName)));
        jaxbUnmarshaller.setSchema(schema);

        return c.cast(jaxbUnmarshaller.unmarshal(new File(fileDirectory + "/" + fileName)));
    }

    /**
     * Given the hash map containing ingredient ids and the quantity, search for the corresponding ingredient for each id in the stock and return a
     * HashMap containing the ingredient and quantity.
     *
     * @param IngredientIDs Contains a string as the ingredient id and the value as the quantity.
     * @return A new hash map containing the string ids replaced with ingredient objects, while the value of the hash map is the quantity.
     */
    private HashMap<Ingredient, Integer> getIngredientsFromID(HashMap<String, Integer> IngredientIDs) throws NullPointerException {
        HashMap<Ingredient, Integer> ingredients = new HashMap<>();
        for (Map.Entry<String, Integer> entry : IngredientIDs.entrySet()) {
            String ID = entry.getKey();
            Integer quantity = entry.getValue();
            Ingredient ingredient = appEnvironment.getStock().getIngredients().get(ID);
            if (ingredient == null) throw new NullPointerException("Ingredient not present!");
            ingredients.put(ingredient, quantity);
        }
        return ingredients;
    }


    /**
     * Given the hash map containing all the menu items, search through each menu item and get access it's recipe
     * and fill up the ingredientsAmount hash map with ingredient objects using the getIngredientsFromID method.
     *
     * @param menuItems Contains the menu items.
     */
    private void handleMenu(HashMap<String, MenuItem> menuItems) throws NullPointerException {
        for (Map.Entry<String, MenuItem> entry : menuItems.entrySet()) {
            MenuItem menuItem = entry.getValue();
            Recipe recipe = menuItem.getRecipe();
            HashMap<String, Integer> ingredientIDs = menuItem.getRecipe().getIngredientIDs();
            HashMap<Ingredient, Integer> recipeIngredients = getIngredientsFromID(ingredientIDs);
            recipe.setIngredientsAmount(recipeIngredients);
        }
    }


    /**
     * Given the hash map containing all the menu items, search through each menu item and get access it's recipe
     * and fill up the ingredientsAmount hash map with ingredient objects using the getIngredientsFromID method.
     *
     * @param transactionItems Contains the menu items.
     */
    private void handleFinance(HashMap<String, Transaction> transactionItems) throws NullPointerException {
        for (Transaction transaction : transactionItems.values()) {
            for (Map.Entry<MenuItem, Integer> entry : transaction.getOrder().getOrderItems().entrySet()) {
                MenuItem menuItem = entry.getKey();
                Recipe recipe = menuItem.getRecipe();
                HashMap<String, Integer> ingredientIDs = menuItem.getRecipe().getIngredientIDs();
                HashMap<Ingredient, Integer> recipeIngredients = getIngredientsFromID(ingredientIDs);
                recipe.setIngredientsAmount(recipeIngredients);
            }
        }
    }

    /**
     * Gets the metadata about the application from a file in the root location of the app,
     * then loads application data if autoload is enabled
     */
    public void loadAppData() {
        try {
            Database tempDatabase = (Database) xmlToObject(Database.class, "metadata.xml", "metadata.xsd", getDefaultLocation());
            saveFileLocation = tempDatabase.getSaveFileLocation();
            autoloadEnabled = tempDatabase.isAutoloadEnabled();
            autosaveEnabled = tempDatabase.isAutosaveEnabled();
            overwriteSetting = tempDatabase.getOverwriteSetting();
            if (autoloadEnabled) {
                String location = getLocation();
                File stockFile = new File(location + "/stock.xml");
                File menuFile = new File(location + "/menu.xml");
                File financeFile = new File(location + "/finance.xml");
                importData(Map.of("stock.xml", stockFile, "menu.xml", menuFile, "finance.xml", financeFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
            saveFileLocation = "";
            autosaveEnabled = false;
            autoloadEnabled = true;
            overwriteSetting = OverwriteType.MERGE_PREFER_NEW;
            try {
                objectToXml(Database.class, this, "metadata.xml", System.getProperty("user.dir"));
            } catch (JAXBException e1) {
                e1.printStackTrace();
            }
        }
    }


    /**
     * Gets the stock.xml from fileDirectory and unmarshal it to an object.
     *
     * @param fileDirectory The directory of the stock.xml
     * @throws Exception throws exception if stock.xml is invalid
     */
    public void stockXmlToObject(String fileDirectory) throws Exception {
        try {
            Stock tempStock = (Stock) xmlToObject(Stock.class, "stock.xml", "stock.xsd", fileDirectory);
            switch (overwriteSetting) {
                case OVERWRITE_ALL:
                    break;
                case MERGE_PREFER_NEW:
                    for (Map.Entry<String, Ingredient> entry : appEnvironment.getStock().getIngredients().entrySet()) {
                        if (!tempStock.getIngredients().containsKey(entry.getKey())) {
                            tempStock.addNewIngredient(entry.getValue());
                            tempStock.modifyQuantity(entry.getKey(),
                                    appEnvironment.getStock().getIngredientStock().get(entry.getKey()));
                        }
                    }
                    break;
                case MERGE_PREFER_OLD:
                    for (Map.Entry<String, Ingredient> entry : appEnvironment.getStock().getIngredients().entrySet()) {
                        if (!tempStock.getIngredients().containsKey(entry.getKey())) {
                            tempStock.addNewIngredient(entry.getValue());
                        }
                        tempStock.modifyQuantity(entry.getKey(),
                                appEnvironment.getStock().getIngredientStock().get(entry.getKey()));
                    }
                    break;
                default:
                    break;
            }
            appEnvironment.setStock(tempStock);
        } catch (JAXBException | SAXException e) {
            throw new Exception("stock.xml file is invalid");
        }
    }

    /**
     * Gets the finance.xml from fileDirectory and unmarshal it to an object.
     *
     * @param fileDirectory The directory of the finance.xml
     * @throws Exception throws exception if finance.xml is invalid
     */
    public void financeXmlToObject(String fileDirectory) throws Exception {
        try {
            Finance tempFinance = (Finance) xmlToObject(Finance.class, "finance.xml", "finance.xsd", fileDirectory);
            handleFinance(tempFinance.getTransactionHistory());
            switch (overwriteSetting) {
                case OVERWRITE_ALL:
                    appEnvironment.setFinance(tempFinance);
                    return;
                case MERGE_PREFER_NEW:
                    for (Map.Entry<String, Transaction> entry : appEnvironment.getFinance().getTransactionHistoryClone().entrySet()) {
                        if (!tempFinance.getTransactionHistory().containsKey(entry.getKey())) {
                            tempFinance.getTransactionHistory().put(entry.getKey(), entry.getValue());
                        }
                    }
                    break;
                case MERGE_PREFER_OLD:
                    for (Map.Entry<String, Transaction> entry : appEnvironment.getFinance().getTransactionHistoryClone().entrySet()) {
                        if (!tempFinance.getTransactionHistory().containsKey(entry.getKey())) {
                            tempFinance.getTransactionHistory().put(entry.getKey(), entry.getValue());
                        }
                    }
                    break;
                default:
                    break;
            }

            appEnvironment.setFinance(tempFinance);
        } catch (JAXBException | SAXException e) {
            throw new Exception("finance.xml file is invalid");
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * Gets the menu.xml from fileDirectory and unmarshal it to an object.
     *
     * @param fileDirectory The directory of the menu.xml
     * @throws Exception throws exception if menu.xml is invalid
     */
    public void menuXmlToObject(String fileDirectory) throws Exception {
        try {
            MenuManager tempMenu = (MenuManager) xmlToObject(MenuManager.class, "menu.xml", "menu.xsd", fileDirectory);
            handleMenu(tempMenu.getMenuItems());
            switch (overwriteSetting) {
                case OVERWRITE_ALL:
                    break;
                case MERGE_PREFER_NEW:
                    for (Map.Entry<String, MenuItem> entry : appEnvironment.getMenuManager().getItemMap().entrySet()) {
                        if (!tempMenu.getItemMap().containsKey(entry.getKey())) {
                            tempMenu.addItem(entry.getValue());
                        }
                    }
                    break;
                case MERGE_PREFER_OLD:
                    for (Map.Entry<String, MenuItem> entry : appEnvironment.getMenuManager().getItemMap().entrySet()) {
                        tempMenu.getItemMap().put(entry.getKey(), entry.getValue());
                    }
                    break;
                default:
                    break;
            }
            appEnvironment.setMenuManager(tempMenu);
        } catch (JAXBException | SAXException e) {
            throw new Exception("menu.xml file is invalid");
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * Converts all relevant stored data in the system to xml files
     *
     * @param fileDirectory The destination directory for the xml files
     * @throws Exception throws exception if failed to export data to xml
     */
    public void allObjectsToXml(String fileDirectory) throws Exception {
        try {
            objectToXml(Stock.class, appEnvironment.getStock(), "stock.xml", fileDirectory);
            objectToXml(Finance.class, appEnvironment.getFinance(), "finance.xml", fileDirectory);
            objectToXml(MenuManager.class, appEnvironment.getMenuManager(), "menu.xml", fileDirectory);
        } catch (JAXBException e) {
            throw new Exception(e);
        }
    }

    /**
     * Exports all of the data to the save location if autosave is enabled
     */
    public void autosave() throws Exception {
        objectToXml(Database.class, this, "metadata.xml", System.getProperty("user.dir"));
        if (autosaveEnabled) {
            String location = getLocation();
            allObjectsToXml(location);
        }
    }

    @XmlTransient
    public enum OverwriteType {
        OVERWRITE_ALL,
        MERGE_PREFER_NEW,
        MERGE_PREFER_OLD
    }

    private String getLocation() {
        if (saveFileLocation == null || saveFileLocation.equals("")) {
            return getDefaultLocation();
        } else {
            return saveFileLocation;
        }
    }

    private String getDefaultLocation() {
        return System.getProperty("user.dir");
    }

    public boolean isAutosaveEnabled() {
        return autosaveEnabled;
    }

    public void setAutosaveEnabled(boolean autosaveEnabled) {
        this.autosaveEnabled = autosaveEnabled;
    }

    public boolean isAutoloadEnabled() {
        return autoloadEnabled;
    }

    public void setAutoloadEnabled(boolean autoloadEnabled) {
        this.autoloadEnabled = autoloadEnabled;
    }

    public void setSaveFileLocation(String saveFileLocation) {
        this.saveFileLocation = saveFileLocation;
    }

    public String getSaveFileLocation() {
        return saveFileLocation;
    }

    public OverwriteType getOverwriteSetting() {
        return overwriteSetting;
    }

    public void setOverwriteSetting(OverwriteType newSetting) {
        overwriteSetting = newSetting;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

}
