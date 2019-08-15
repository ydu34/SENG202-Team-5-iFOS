package seng202.group5;

/**
 * The Ingredients class records all the base data for each ingredient in the database which include its name, price, category,
 * id and its p[rice.
 * @author Shivin Gaba
 */
public class Ingredients {

    /** Name of the ingredient used in thge recipe **/
    private String name;
    /** The quantity of a particular ingredient on hand **/
    private int unit;
    /** Category that ingredient belongs to like poultry, met or bred. **/
    private String category;
    /** Unique id used to identify every ingredient in the database**/
    private int id;
    /** The price for a single unit of a ingredient**/
    private float price;

    Ingredients(String tempName, int tempUnit, String tempCategory, int tempId, float cost) {

        tempName = name;
        tempUnit = unit;
        tempCategory = category;
        tempId = id;
        cost = price;
    }

    /**
    Returns the name of the of the ingredient
     **/
    public String getName() {
        return name;
    }

    /**
    Returns the number of units of the ingredient on hand
     **/
    public int getUnit(){
        return unit;
    }

    /**
    Returns the category of the ingredient if its  a spice, meat or bread.
     **/
    public String getCategory(){
        return category;
    }

    /**
    Returns the Unique id for every ingredient
     **/
    public int getId(){
        return id;
    }

    /**
    Returns the cost of each ingredient
     **/
    public float getCost(){
        return price;
    }






}
