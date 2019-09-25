package seng202.group5;

import org.joda.money.Money;
import org.xml.sax.SAXException;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.logic.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Yu Duan
 */
public class AppEnvironment {

    private OrderManager orderManager;
    private Finance finance;
    private Stock stock;
    private History history;
    private MenuManager menuManager;
    private HashSet<String> acceptedFiles;
    private IDGenerator idGenerator;


    /**
     * The constructor for AppEnvironment
     */
    public AppEnvironment() {
        finance = new Finance();
        stock = new Stock();
        history = new History();
        menuManager = new MenuManager();
        orderManager = new OrderManager(stock, history);
        idGenerator = new IDGenerator();
        acceptedFiles = new HashSet<>();
        acceptedFiles.add("stock.xml");
        acceptedFiles.add("menu.xml");
        acceptedFiles.add("history.xml");
        acceptedFiles.add("finance.xml");
    }

    /**
     * Marshals the given object o into a xml file
     *
     * @param c        The class of the object o
     * @param o        The object you want to marshal into xml file
     * @param fileName The name of the xml file
     * @param fileDirectory The directory to where the file should be marshalled
     * @throws JAXBException if JAXB fails to convert the file
     */
    public void objectToXml(Class c, Object o, String fileName,  String fileDirectory) throws JAXBException{
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(c.cast(o), new File(fileDirectory + "/" + fileName));
    }

    /**
     * Converts the xml file to an object o
     *
     * @param c the class to create an instance of
     * @param o the object being created
     * @param fileName the name of the file to create the object from
     * @param schemaFileName the name of the schema to use to convert the file
     * @param fileDirectory the path to the directory the file is in
     * @return an object o
     * @throws JAXBException if JAXB fails to convert the file
     * @throws SAXException if JAXB fails to convert the file
     */
    public Object xmlToObject(Class c, Object o, String fileName, String schemaFileName, String fileDirectory) throws JAXBException, SAXException{
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        ClassLoader classLoader = getClass().getClassLoader();
        //Setup schema validator
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema( new StreamSource(getClass().getClassLoader().getResourceAsStream("schema/" + schemaFileName)));
        jaxbUnmarshaller.setSchema(schema);

        o = c.cast(jaxbUnmarshaller.unmarshal(new File(fileDirectory + "/" + fileName)));
        return o;
    }

    /**
     * Given the hash map containing ingredient ids and the quantity, search for the corresponding ingredient for each id in the stock and return a
     * HashMap containing the ingredient and quantity.
     *
     * @param IngredientIDs Contains a string as the ingredient id and the value as the quantity.
     * @return A new hash map containing the string ids replaced with ingredient objects, while the value of the hash map is the quantity.
     */
    public HashMap<Ingredient, Integer> getIngredientsFromID(HashMap<String, Integer> IngredientIDs) {
        HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        for (Map.Entry<String, Integer> entry : IngredientIDs.entrySet()) {
            String ID = entry.getKey();
            Integer quantity = entry.getValue();
            Ingredient ingredient = stock.getIngredients().get(ID);
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
    public void handleMenu(HashMap<String, MenuItem> menuItems) {
        for (Map.Entry<String, MenuItem> entry : menuItems.entrySet()) {
            MenuItem menuItem = entry.getValue();
            Recipe recipe = menuItem.getRecipe();
            HashMap<String, Integer> ingredientIDs = menuItem.getRecipe().getIngredientIDs();
            HashMap<Ingredient, Integer> recipeIngredients = getIngredientsFromID(ingredientIDs);
            recipe.setIngredientsAmount(recipeIngredients);
        }
    }

    /**
     * Gets the stock.xml from fileDirectory and unmarshal it to an object.
     * @param fileDirectory The directory of the stock.xml
     * @throws Exception throws exception if stock.xml is invalid
     */
    public void stockXmlToObject(String fileDirectory) throws Exception {
        try {
            stock = (Stock) xmlToObject(Stock.class, stock, "stock.xml", "stock.xsd", fileDirectory);
            orderManager.setStock(stock);
            orderManager.getOrder().resetStock(stock);
        } catch (JAXBException|SAXException e) {
            throw new Exception("stock.xml file is invalid");
        }
    }

    /**
     * Gets the history.xml from fileDirectory and unmarshal it to an object.
     * @param fileDirectory The directory of the history.xml
     * @throws Exception throws exception if history.xml is invalid
     */
    public void historyXmlToObject(String fileDirectory) throws Exception{
        try {
            history = (History) xmlToObject(History.class, history, "history.xml", "history.xsd", fileDirectory);
            orderManager.setCurrentHistory(history);
            orderManager.newOrder();
        } catch (JAXBException|SAXException e) {
            throw new Exception("history.xml file is invalid");
        }
    }

    /**
     * Gets the finance.xml from fileDirectory and unmarshal it to an object.
     * @param fileDirectory The directory of the finance.xml
     * @throws Exception throws exception if finance.xml is invalid
     */
    public void financeXmlToObject(String fileDirectory) throws Exception{
        try {
            finance = (Finance) xmlToObject(Finance.class, finance, "finance.xml", "finance.xsd", fileDirectory);
        } catch (JAXBException|SAXException e) {
            throw new Exception("finance.xml file is invalid");
        }
    }

    /**
     * Gets the menu.xml from fileDirectory and unmarshal it to an object.
     * @param fileDirectory The directory of the menu.xml
     * @throws Exception throws exception if menu.xml is invalid
     */
    public void menuXmlToObject(String fileDirectory) throws Exception{
        try {
            menuManager = (MenuManager) xmlToObject(MenuManager.class, menuManager, "menu.xml", "menu.xsd", fileDirectory);
            handleMenu(menuManager.getMenuItems());
        } catch (JAXBException|SAXException e) {
            throw new Exception("menu.xml file is invalid");
        }
    }

    /**
     * Converts all relevant stored data in the system to xml files
     * @param fileDirectory The destination directory for the xml files
     * @throws Exception throws exception if failed to export data to xml
     */
    public void allObjectsToXml(String fileDirectory) throws Exception{
        try {
            objectToXml(Stock.class, stock, "stock.xml", fileDirectory);
            objectToXml(History.class, history, "history.xml", fileDirectory);
            objectToXml(Finance.class, finance, "finance.xml", fileDirectory);
            objectToXml(MenuManager.class, menuManager, "menu.xml", fileDirectory);
        } catch (JAXBException e) {
            throw new Exception();
        }
    }

    /**
     * Confirms payment for the current order, sends the order to the history,
     * sends information about the transaction to Finance and retrieves the
     * cash amounts to be given as change
     *
     * @param denominations the cash given to the worker to pay for the item
     * @return the cash to be returned to the customer as change
     * @throws InsufficientCashException if the given cash amount is not enough
     *                                   to pay for the order
     */
    public ArrayList<Money> confirmPayment(ArrayList<Money> denominations) throws InsufficientCashException {
        //        Money totalPayment = Money.parse("NZD 0");
        //        for (Money coin : denominations) totalPayment = totalPayment.plus(coin);
        ArrayList<Money> change = new ArrayList<Money>();
        try {
            Order order = orderManager.getOrder();
            order.setDateTimeProcessed(LocalDateTime.now());
            orderManager.getHistory().getTransactionHistory().put(order.getId(), order);
            setStock(order.getStock().clone());
            orderManager.setStock(stock);
            orderManager.newOrder();

            change = finance.pay(order.getTotalCost(),
                                 denominations,
                                 order.getDateTimeProcessed(),
                                 order.getId());

        } catch (NoOrderException e) {
            e.printStackTrace();
        }

        return change;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
        orderManager.setStock(stock);
    }

    public History getHistory() {
        return history;
    }

    public Finance getFinance() {
        return finance;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public HashSet<String> getAcceptedFiles() {
        return acceptedFiles;
    }

    public void setAcceptedFiles(HashSet<String> acceptedFiles) {
        this.acceptedFiles = acceptedFiles;
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(OrderManager tempManager) {
        orderManager = tempManager;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public IDGenerator getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }
}
