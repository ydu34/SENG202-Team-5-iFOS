package seng202.group5.logic;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import seng202.group5.IDGenerator;
import seng202.group5.adapters.LocalDateTimeAdapter;
import seng202.group5.adapters.MoneyAdapter;
import seng202.group5.logic.Stock;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;

/**
 * The Order class keeps track of the current orders items and total cost. Contains methods to modify items to the order
 * and give a discount to the order.
 *
 * @author Michael Morgoun, Yu Duan
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

    /**
     * A HashMap of items in the order and their quantities.
     **/
    private HashMap<MenuItem, Integer> orderItems;

    /**
     * The current total cost of the order including the discount
     **/
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money totalCost = Money.zero(CurrencyUnit.of("NZD"));

    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime dateTimeProcessed;

    /**
     * The unique ID of the order given by the database
     **/
    private IDGenerator generator = new IDGenerator();
    private String id = generator.newID();


    /**
     * The Stock to update when creating this order
     */
    @XmlTransient
    private Stock temporaryStock;

    Order() {
        orderItems = new HashMap<>();
    }


    /**
     * The builder for an Order object.
     *
     * @param tempOrderItems An order with an initial order list of items.
     * @param tempTotalCost  The total cost of an existing order.
     * @param tempID         The unique ID of the order.
     */
    @Deprecated(since = "Replaced with version that edits stock, THIS VERSION BREAKS OTHER CODE")
    public Order(HashMap<MenuItem, Integer> tempOrderItems, Money tempTotalCost, String tempID) {
        orderItems = tempOrderItems;
        totalCost = tempTotalCost;
        id = tempID;

    }

    /**
     * The builder for an Order object.
     *
     * @param tempOrderItems An order with an initial order list of items.
     * @param tempTotalCost  The total cost of an existing order.
     * @param tempID         The unique ID of the order.
     * @param tempStock      The temporary stock to update when adding/removing items
     */
    public Order(HashMap<MenuItem, Integer> tempOrderItems, Money tempTotalCost, String tempID, Stock tempStock) {
        orderItems = tempOrderItems;
        totalCost = tempTotalCost;
        id = tempID;
        temporaryStock = tempStock.clone();
    }


    /**
     * The builder for an Order object with no initial values.
     */
    public Order(Stock tempStock) {
        orderItems = new HashMap<>();
        totalCost = Money.zero(CurrencyUnit.of("NZD"));
        temporaryStock = tempStock.clone();
    }


    /**
     * Gives a discount to the order based off a percentage given as a parameter.
     *
     * @param percentage The percentage of the discount.
     */
    public void discount(int percentage) {
        //TODO refactor the class so it applies the discount passively, should possibly store the discount as an attribute
        totalCost = totalCost.minus(totalCost.multipliedBy(percentage / 100.0, RoundingMode.UP));
    }

    /**
     * Adds a new item/s to the order and checks the temporaryStock in case there are not enough ingredients
     *
     * @param item     The item to add to the order
     * @param quantity The quantity of the item to add to the order
     * @return Whether there were enough ingredients in the stock to add those item/s.
     */
    public boolean addItem(MenuItem item, int quantity) {
        // Getting the HashMap of ingredients, quantities

        HashMap<Ingredient, Integer> ingredientQuantities = item.getRecipe().getIngredientsAmount();
        HashMap<String, Integer> ingredients = new HashMap<>();
        for (Ingredient ingredient : ingredientQuantities.keySet()) {
            ingredients.put(ingredient.getID(), ingredientQuantities.get(ingredient));
        }
        // Creating an ArrayList so that we can iterate through the ingredients
        Set<String> keySet = ingredients.keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);

        // For each ingredient we change the quantity to accommodate any extra's
        for (String id : listOfKeys) {
            // If we don't have enough in the Stock, we can't add it to order
            if (temporaryStock.getIngredientQuantity(id) < ingredients.get(id) * quantity) {
               // System.out.println("false");

                return false;
            }
        }

        // Finally removing ingredient amounts from stock
        for (String id : listOfKeys) {
            temporaryStock.getIngredientStock().replace(id, temporaryStock.getIngredientQuantity(id) - ingredients.get(id) * quantity);
        }
        if (orderItems.containsKey(item)) {
            orderItems.put(item, orderItems.get(item) + quantity);
        } else {
            orderItems.put(item, quantity);
        }

        // Add price of item to total cost
        totalCost = totalCost.plus(item.calculateFinalCost().multipliedBy(quantity));
        return true;
    }



    /**
     * Removes the item taken as a parameter from the order and updates the temporaryStock and returns a boolean true or false as to whether
     * it was successful or not.
     *
     * @param item The item that is to be removed from the order.
     * @return The boolean success of the removal.
     */
    public boolean removeItem(MenuItem item) {
        if (orderItems.containsKey(item)) {
            int quantity = orderItems.get(item);
            // Getting the ingredient HashMap and creating an Arraylist to iterate through out of the keys in the HashMap
            HashMap<Ingredient, Integer> ingredientQuantities = item.getRecipe().getIngredientsAmount();
            HashMap<String, Integer> ingredients = new HashMap<>();
            for (Ingredient ingredient : ingredientQuantities.keySet()) {
                ingredients.put(ingredient.getID(), ingredientQuantities.get(ingredient));
            }
            Set<String> keySet = ingredients.keySet();
            ArrayList<String> listOfKeys = new ArrayList<>(keySet);

            // Adding the ingredients back into the stock
            for (String id : listOfKeys) {
                temporaryStock.modifyQuantity(id, temporaryStock.getIngredientQuantity(id) + ingredients.get(id) * quantity);
            }

            orderItems.remove(item);

            // Minuses the price of the item from the total cost
            totalCost = totalCost.minus(item.calculateFinalCost().multipliedBy(quantity));

            return true;
        } else {
            return false;
        }
    }

    /**
     * modifies the quantity of an item with the quantity parameter.
     * Returns a boolean true or false as to whether it was successful or not.
     *
     * @param item     The item that will have its quantity change.
     * @param quantity The new quantity for an item.
     * @return The boolean success of the quantity update.
     */
    public boolean modifyItemQuantity(MenuItem item, int quantity) {
        if (orderItems.containsKey(item)) {
            int currentCount = orderItems.get(item);
            removeItem(item);
            if (!addItem(item, quantity)) {
                addItem(item, currentCount);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void clearItemsInOrder(){
        orderItems.clear();
        totalCost = Money.zero(CurrencyUnit.of("NZD"));
    }

    /**
     * Prints the receipt of the order
     *
     * @return A string of the receipt of the order
     */
    public String printReceipt() {
        StringBuilder outputString = new StringBuilder();
        for (Map.Entry<MenuItem, Integer> entry : orderItems.entrySet()) {
            MenuItem a = entry.getKey();
            Integer b = entry.getValue();
            outputString.append(format("%d %s(s) - %s\n",
                                       b,
                                       a.getItemName(),
                                       a.calculateFinalCost().multipliedBy(b)));
        }
        outputString.append("Total cost - ");
        outputString.append(getTotalCost());
        return outputString.toString();
    }
    //TODO formalize receipt structure

    /**
     * Sets the stock to a clone of the specified stock
     *
     * @param stock the new stock to be cloned
     */
    public void resetStock(Stock stock) {
        temporaryStock = stock.clone();
    }

    /**
     * Gets the ID of this order
     *
     * @return the ID of this order
     */
    public String getId() {
        return id;
    }

    /**
     * A getter for the current order list.
     *
     * @return A HashMap<MenuItem, Integer> orderItems
     */
    public HashMap<MenuItem, Integer> getOrderItems() {
        return orderItems;
    }

    /**
     * Returns the total cost.
     *
     * @return the totalCost of the order.
     */
    public Money getTotalCost() {
        return totalCost;
    }

    /**
     * Gets the stock that is being updated
     *
     * @return the stock that is being updated
     */
    public Stock getStock() {
        return temporaryStock;
    }

    public LocalDateTime getDateTimeProcessed() {
        return dateTimeProcessed;
    }

    public void setDateTimeProcessed(LocalDateTime dateProcessed) {
        this.dateTimeProcessed = dateProcessed;
    }

    public void setId(String id) {
        this.id = id;
    }
}
