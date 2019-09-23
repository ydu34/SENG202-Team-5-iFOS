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

    @XmlElement
    private IDGenerator generator = new IDGenerator();

    /**
     * Unique id used to identify every ingredient in the database
     **/
    @XmlAttribute
    private String id = generator.newID();

    /**
     * The price for a single unit of a ingredient
     **/
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money price;


    /**
     * A HashSet to store dietary information about the recipe
     */
    private HashSet<DietEnum> dietaryInformation = new HashSet<>();

    Ingredient() {
    }

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
     * Returns the name of the of the ingredient
     **/
    public String getName() {
        return name;
    }

    /**
     * Returns the category of the ingredient if its a spice, meat or bread.
     **/
    public String getCategory() {
        return category;
    }

    /**
     * Returns the Unique id for every ingredient
     **/
    public String getID() {
        return id;
    }

    /**
     * Sets the dietaryInformation.
     * @param set A HashSet containing DietEnums.
     */
    public void setDietaryInformation(HashSet<DietEnum> set) { dietaryInformation = set; }

    /**
     * This method sets the name to the ingredient added to the stock
     *
     * @param someName name of the new ingredient
     */
    public void setName(String someName) {
        name = someName;
    }

    /**
     * @param someCategory Category for the newly added ingredient like drink,food,spice etc.
     */
    public void setCategory(String someCategory) {
        category = someCategory;
    }

    /**
     * This method sets the price for the ingredient.
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Money getPrice() {
        return price;
    }
}
