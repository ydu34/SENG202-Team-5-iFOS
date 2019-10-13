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
    public static String newIngredientID() {
        return "INGR" + ingredientID++;
    }

    /**
     * Creates a new  MenuItem ID and increments the static variable by 1.
     * @return The new ID as a string.
     */
    public static String newMenuItemID() {
        return "ITEM" + menuItemID++;
    }

    /**
     * Creates a new  Order ID and increments the static variable by 1.
     * @return The new ID as a string.
     */
    public static String newOrderID() {
        return "ORDR" + orderID++;
    }

    /**
     * Creates a new Customer ID and increments the static variable by 1.
     * @return The new ID as a string.
     */
    public static String newCustomerID() { return "CUST" + customerID++; }

    /**
     * Returns the last ingredientID used.
     * @return an integer last ingredientID.
     */
    public static int getIngredientID() {
        return ingredientID;
    }

    /**
     * Sets the last ingredientID to the parameter.
     * @param newIngredientID An integer referring to the digit of the new ingredientID.
     */
    public static void setIngredientID(int newIngredientID) { if (ingredientID < newIngredientID) ingredientID = newIngredientID; }

    /**
     * Returns the last menuItemID used.
     * @return an integer last menuItemID.
     */
    public static int getMenuItemID() {
        return menuItemID;
    }

    /**
     * Sets the last menuItemID to the parameter.
     * @param newMenuItemID An integer referring to the digit of the new menuItemID.
     */
    public static void setMenuItemID(int newMenuItemID) {
        if (menuItemID < newMenuItemID) menuItemID = newMenuItemID;
    }

    /**
     * Returns the last orderID used.
     * @return an integer last orderID.
     */
    public static int getOrderID() {
        return orderID;
    }

    /**
     * Sets the last orderID to the parameter.
     * @param newOrderID An integer referring to the digit of the new orderID.
     */
    public static void setOrderID(int newOrderID) {
        if (orderID < newOrderID) orderID = newOrderID;
    }

    /**
     * Returns the last customerID used.
     * @return an integer last customerID.
     */
    public static int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the last customerID to the parameter.
     * @param newCustomerID An integer referring to the digit of the new customerID.
     */
    public static void setCustomerID(int newCustomerID) {
        if (customerID < newCustomerID) customerID = newCustomerID;
    }
}
