package seng202.group5;

import javax.xml.bind.annotation.*;

/**
 * The Ingredient class records all the base data for each ingredient in the database which include its name, price,
 * category, id and its price.
 *
 * @author Shivin Gaba
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
    /**
     * Boolean when True means the product is Gluten Free
     **/
    private boolean isGlutenFree;
    /**
     * Boolean when True means the ingredient is vegetarian
     */
    private boolean isVegetarian;
    /**
     * Boolean when True means the ingredient is vegan
     **/
    private boolean isVegan;

    Ingredient()
    {}

    Ingredient(String tempName, String tempUnit, String tempCategory, String tempId, double tempPrice) {

        name = tempName;
        unit = tempUnit;
        category = tempCategory;
        id = tempId;
        price = tempPrice;
    }

    Ingredient(String tempName, String tempUnit, String tempCategory, String tempId, double tempPrice, boolean glutenFree, boolean vegetarian, boolean vegan) {

        name = tempName;
        unit = tempUnit;
        category = tempCategory;
        id = tempId;
        price = tempPrice;
        isGlutenFree = glutenFree;
        isVegetarian = vegetarian;
        isVegan = vegan;

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
     * This method sets the boolean True for the ingredient if it is vegan
     */

    public void setVegan() {
        isVegan = true;
    }

    /**
     * This method sets the boolean True for the ingredient if it is vegetarian
     */
    public void setVegetarian() {
        isVegetarian = true;
    }

    /**
     * This method sets the boolean True for the ingredient if it is gluten free
     */
    public void setGlutenFree() {
        isGlutenFree = true;
    }

    /**
     * Returns the cost of each ingredient
     **/
    @XmlElement
    public double getCost() {
        return price;
    }

    /**
     * Returns True if the ingredient is Vegan
     **/
    @XmlElement
    public boolean getGlutenFree() {
        return isGlutenFree;
    }

    /**
     * Returns True if the ingredient is Vegan
     **/
    @XmlElement
    public boolean getVegetarian() {
        return isVegetarian;
    }

    /**
     * Returns True if the ingredient is Vegan
     **/
    @XmlElement
    public boolean getVegan() {
        return isVegan;
    }

    /**
     * @param other ingredient that is compared to the current ingredient
     * @return True if the two compared ingredients are the same
     */
    public boolean equals(Ingredient other) {
        return id == other.id;
    }

}
