package seng202.group5.information;

import org.joda.money.Money;
import seng202.group5.IDGenerator;
import seng202.group5.adapters.MoneyAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The Ingredient class records all the base data for each ingredient in the database which include its name, price,
 * category, id and its price.
 *
 * @author Shivin Gaba, Daniel Harris, Yu Duan
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Ingredient {

    /**
     * Name of the ingredient used in the recipe
     **/
    private String name;

    /**
     * Category that ingredient belongs to like poultry, meat or bread.
     **/
    private String category;

    /**
     * Unique id used to identify every ingredient in the database
     **/
    @XmlAttribute
    private String id = IDGenerator.newIngredientID();

    /**
     * The price for a single unit of a ingredient
     **/
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money price;


    /**
     * A HashSet to store dietary information about the recipe
     */
    private HashSet<DietEnum> dietaryInformation = new HashSet<>();

    Ingredient(){}

    public Ingredient(String tempName, String tempCategory, Money tempPrice) {
        name = tempName;
        category = tempCategory;
        price = tempPrice;
        dietaryInformation = new HashSet<>();
    }

    public Ingredient(String tempName, String tempCategory, Money tempPrice, HashSet<DietEnum> dietInfo) {
        name = tempName;
        category = tempCategory;
        price = tempPrice;
        dietaryInformation = dietInfo;
    }

    @Deprecated(since = "Used for testing only")
    public Ingredient(String tempName, String tempCategory, String tempId, Money tempPrice) {
        name = tempName;
        category = tempCategory;
        id = tempId;
        price = tempPrice;
        dietaryInformation = new HashSet<>();
    }

    @Deprecated(since = "Used for testing only")
    public Ingredient(String tempName, String tempCategory, String tempId, Money tempPrice, HashSet<DietEnum> dietInfo) {
        name = tempName;
        category = tempCategory;
        id = tempId;
        price = tempPrice;
        dietaryInformation = dietInfo;
    }

    /**
     * Returns the name of the ingredient
     *
     * @return the name of this ingredient
     **/
    public String getName() {
        return name;
    }

    /**
     * Returns the category of the ingredient i.e. if it is a spice, meat or bread.
     *
     * @return the category in which this ingredient is in
     **/
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of this ingredient i.e. drink, food, spice etc.
     *
     * @param someCategory new category for the ingredient
     */
    public void setCategory(String someCategory) {
        category = someCategory;
    }

    /**
     * Returns the unique id for this ingredient
     *
     * @return the id of this ingredient
     **/
    public String getID() {
        return id;
    }

    /**
     * This method sets the name to the ingredient added to the stock
     *
     * @param someName name of the new ingredient
     */
    public void setName(String someName) {
        name = someName;
    }

    /**
     * Sets the dietary information about this ingredient
     * @param set A HashSet containing DietEnums representing the dietary information of this ingredient
     */
    public void setDietaryInformation(HashSet<DietEnum> set) {
        dietaryInformation = set;
    }

    /**
     * Sets the price for the ingredient.
     * @param money A new price for the ingredient using Joda Money.
     */
    public void setPrice(Money money) { price = money; }

    /**
     * Adds dietary information about this ingredient
     *
     * @param newDietInfo A DietEnum describing dietary information about this ingredient
     */
    public void addDietInfo(DietEnum newDietInfo) {
        dietaryInformation.add(newDietInfo);
    }

    /**
     * Adds dietary information about this ingredient
     *
     * @param dietInfoToAdd A list of DietEnums describing dietary information about this ingredient
     */
    public void addDietInfo(ArrayList<DietEnum> dietInfoToAdd) {
        for (DietEnum newDietInfo : dietInfoToAdd) {
            addDietInfo(newDietInfo);
        }
    }

    /**
     * Removes dietary information about this ingredient
     *
     * @param newDietInfo A DietEnum to remove describing dietary information about this ingredient
     */
    public void removeDietInfo(DietEnum newDietInfo) {
        dietaryInformation.remove(newDietInfo);
    }


    /**
     * Removes dietary information about this ingredient
     *
     * @param dietInfoToRemove A list of DietEnums describing dietary information about this ingredient
     */
    public void removeDietInfo(ArrayList<DietEnum> dietInfoToRemove) {
        for (DietEnum newDietInfo : dietInfoToRemove) {
            removeDietInfo(newDietInfo);
        }
    }

    /**
     * Gets the dietary information of this ingredient
     *
     * @return the dietary information about this ingredient
     */
    public HashSet<DietEnum> getDietInfo() {
        return dietaryInformation;
    }

    /**
     * Gets the hashcode of this ingredient
     *
     * @return the hashcode of this ingredient
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Checks if this ingredient is equal to another ingredient
     *
     * @param other ingredient that is compared to the current ingredient
     * @return True if the two compared ingredients are the same
     */
    public boolean equals(Ingredient other) {
        return id.equals(other.id);
    }

    /**
     * Gets the cost of this ingredient
     *
     * @return The cost of this ingredient
     */
    public Money getCost() {
        return price;
    }

    /**
     * Gets the id of this ingredient
     *
     * @return The id of this ingredient
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of this ingredient
     *
     * @param id The new id of this ingredient
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the price of this ingredient
     *
     * @return The price of this ingredient
     */
    public Money getPrice() {
        return price;
    }
}