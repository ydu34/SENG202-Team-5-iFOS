package seng202.group5;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The Ingredient class records all the base data for each ingredient in the database which include its name, price,
 * category, id and its price.
 *
 * @author Shivin Gaba, Daniel Harris
 */
@XmlRootElement
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

    /**
     * Unique id used to identify every ingredient in the database
     **/
    private String id;

    /**
     * The price for a single unit of a ingredient
     **/
    private double price;


    //TODO is this XmlTransient?
    /**
     * A HashSet to store dietary information about the recipe
     */
    private HashSet<DietEnum> dietaryInformation;

    Ingredient() {
    }

    public Ingredient(String tempName, String tempUnit, String tempCategory, String tempId, double tempPrice) {

        name = tempName;
        unit = tempUnit;
        category = tempCategory;
        id = tempId;
        price = tempPrice;
    }

    public Ingredient(String tempName, String tempUnit, String tempCategory, String tempId, double tempPrice, HashSet<DietEnum> dietInfo) {

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
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Returns the number of units of the ingredient on hand
     **/
    @XmlElement
    public String getUnit() {
        return unit;
    }

    /**
     * Returns the category of the ingredient if its a spice, meat or bread.
     **/
    @XmlElement
    public String getCategory() {
        return category;
    }

    /**
     * Returns the Unique id for every ingredient
     **/
    @XmlAttribute
    public String getId() {
        return id;
    }


    /**
     * This method sets the unique id for the new ingredient added to the stock
     *
     * @param someId Id for the new Ingredient added to the stock
     */
    public void setId(String someId) {
        id = someId;
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
    public double getCost() {
        return price;
    }

}
