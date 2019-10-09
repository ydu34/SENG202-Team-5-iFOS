package seng202.group5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class which creates IDs for all that requires an ID and makes sure that
 * they're never the same.
 * @author Michael Morgoun
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IDGenerator {

    /**
     * A static variable ID which shows the last Ingredient ID used or 0 if none used so far.
     */
    @XmlElement
    private static int ingredientID = 0;

    /**
     * A static variable ID which shows the last MenuItem ID used or 0 if none used so far.
     */
    @XmlElement
    private static int menuItemID = 0;

    /**
     * A static variable ID which shows the last Order ID used or 0 if none used so far.
     */
    @XmlElement
    private static int orderID = 0;

    /**
     * A static variable ID which shows the last Transaction ID used or 0 if none used so far.
     */
    @XmlElement
    private static int transactionID = 0;

    /**
     * A static variable ID which shows the last Customer ID used or 0 if none used so far.
     */
    @XmlElement
    private static int customerID = 0;

    public IDGenerator() {

    }

    /**
     * Creates a new  Ingredient ID and increments the static variable by 1.
     * @return The new ID as a string.
     */
    public String newIngredientID() {
        String newID = "INGR" + ingredientID++;
        return newID;
    }

    /**
     * Creates a new  MenuItem ID and increments the static variable by 1.
     * @return The new ID as a string.
     */
    public String newMenuItemID() {
        String newID = "ITEM" + menuItemID++;
        return newID;
    }

    /**
     * Creates a new  Order ID and increments the static variable by 1.
     * @return The new ID as a string.
     */
    public String newOrderID() {
        String newID = "ORDR" + orderID++;
        return newID;
    }

    /**
     * Creates a new  Transaction ID and increments the static variable by 1.
     * @return The new ID as a string.
     */
    public String newTransactionID() {
        String newID = "TRAN" + transactionID++;
        return newID;
    }

    /**
     * Creates a new Customer ID and increments the static variable by 1.
     * @return The new ID as a string.
     */
    public String newCustomerID() {
        String newID = "CUST" + customerID++;
        return newID;
    }

    /**
     * Sets the last Ingredient ID used, to the string lastID.
     * @param lastID A string ID.
     */
    public void setLastIngredientID(String lastID) {
        ingredientID = Integer.parseInt(lastID.replaceAll("[^\\d.]", ""));
    }

    /**
     * Sets the last MenuItem ID used, to the string lastID.
     * @param lastID A string ID.
     */
    public void setLastMenuItemID(String lastID) {
        menuItemID = Integer.parseInt(lastID.replaceAll("[^\\d.]", ""));
    }

    /**
     * Sets the last Order ID used, to the string lastID.
     * @param lastID A string ID.
     */
    public void setLastOrderID(String lastID) {
        orderID = Integer.parseInt(lastID.replaceAll("[^\\d.]", ""));
    }

    /**
     * Sets the last Transaction ID used, to the string lastID.
     * @param lastID A string ID.
     */
    public void setLastTransactionID(String lastID) {
        transactionID = Integer.parseInt(lastID.replaceAll("[^\\d.]", ""));
    }

    /**
     * Sets the last Customer ID used, to the string lastID.
     * @param lastID A string ID.
     */
    public void setLastCustomerID(String lastID) {
        customerID = Integer.parseInt(lastID.replaceAll("[^\\d.]", ""));
    }

    public static int getIngredientID() {
        return ingredientID;
    }

    public static void setIngredientID(int newIngredientID) {
        ingredientID = newIngredientID;
    }

    public static int getMenuItemID() { return menuItemID; }

    public static void setMenuItemID(int newMenuItemID) { menuItemID = newMenuItemID; }

    public static int getOrderID() { return orderID; }

    public static void setOrderID(int newOrderID) { orderID = newOrderID; }

    public static int getTransactionID() { return transactionID; }

    public static void setTransactionID(int newTransactionID) { transactionID = newTransactionID; }

    public static int getCustomerID() { return customerID; }

    public static void setCustomerID(int newCustomerID) { customerID = newCustomerID; }
}
