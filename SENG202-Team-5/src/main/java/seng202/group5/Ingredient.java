package seng202.group5;

import org.joda.money.Money;

import javax.xml.bind.annotation.*;
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
     * The measurement used to quantify the ingredient i.e. kg, L, buns etc.
     **/
    private String unit;

    /**
     * Category that ingredient belongs to like poultry, meat or bread.
     **/
    private String category;

    @XmlTransient
    private IDGenerator generator = new IDGenerator();

    /**
     * Unique id used to identify every ingredient in the database
     **/
    @XmlAttribute
    private String id = generator.newID();

    /**
     * The price for a single unit of a ingredient
     **/
    private Money price;


    /**
     * A HashSet to store dietary information about the recipe
     */
    @XmlTransient
    private HashSet<DietEnum> dietaryInformation = new HashSet<>();

    Ingredient() {
    }

    public Ingredient(String tempName, String tempUnit, String tempCategory, Money tempPrice) {
        name = tempName;
        unit = tempUnit;
        category = tempCategory;
        price = tempPrice;
        dietaryInformation = new HashSet<>();
    }

    public Ingredient(String tempName, String tempUnit, String tempCategory, String tempId, Money tempPrice) {

        name = tempName;
        unit = tempUnit;
        category = tempCategory;
        id = tempId;
        price = tempPrice;
        dietaryInformation = new HashSet<>();
    }

    public Ingredient(String tempName, String tempUnit, String tempCategory, String tempId, Money tempPrice, HashSet<DietEnum> dietInfo) {

        name = tempName;
        unit = tempUnit;
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
     * Returns the number of units of the ingredient on hand
     **/
    public String getUnit() {
        return unit;
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

}
