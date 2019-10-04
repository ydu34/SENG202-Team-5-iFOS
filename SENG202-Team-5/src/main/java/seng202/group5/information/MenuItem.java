package seng202.group5.information;

import org.joda.money.Money;
import seng202.group5.IDGenerator;
import seng202.group5.adapters.MoneyAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class contains methods to update the stock, removes the stock , calculates the making and selling price for the menu item.
 *
 * @author Shivin Gaba, James Kwok, Yu Duan
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MenuItem {

    /**
     * Name of the dish/item on the menu.
     */
    private String itemName;
    /**
     * The recipe object that has the list of the ingredients involved to make that menu item
     */
    private Recipe recipe;
    /**
     * The final cost of the menu item that after the mark up
     */
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money markupCost;
    /**
     * The unique id related to every item on the menu
     */
    private IDGenerator generator = new IDGenerator();
    /**
     * Unique id for a menu item
     */
    private String id = generator.newID();
    /**
     * Whether or not this item is in the menu
     */
    private boolean inMenu;
    /**
     * Enum type that tells about the dietary info about the menu item
     */
    private TypeEnum itemType;
    /**
     * Whether or not the item was modified
     */
    private boolean edited;

    /**
     * total cost of the menu item
     */
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money totalCost;

    /**
     * A string representing the image for this menu item
     */
    private String imageString;

    /**
     * Default constructor for the menu item class
     */
    public MenuItem() {
        itemName = "";
        recipe = new Recipe();
        markupCost = Money.parse("NZD 0.00");
        inMenu = true;
        itemType = TypeEnum.MAIN;
        edited = false;
        totalCost = Money.parse("NZD 0.00");
        imageString = null;
    }

    /**
     * @param tempItemName is the name of an item on the menu
     * @param tempRecipe   is the recipe for a an item on the menu
     * @param tempMarkupCost   is the cost added to the ingredient cost of the menu item
     * @param uniqueId     is the unique id related to each menu item
     * @param tempInMenu whether or not the item is in the menu
     */
    @Deprecated(since = "ID is made using ID Maker now, this may be useful for tests though")
    public MenuItem(String tempItemName, Recipe tempRecipe, Money tempMarkupCost, String uniqueId, boolean tempInMenu) {
        itemName = tempItemName;
        recipe = tempRecipe;
        markupCost = tempMarkupCost;
        id = uniqueId;
        inMenu = tempInMenu;
        itemType = TypeEnum.MAIN;
        edited = false;
        totalCost = calculateFinalCost();
    }

    /**
     * @param tempItemName   is the name of an item on the menu
     * @param tempRecipe     is the recipe for a an item on the menu
     * @param tempMarkupCost is the cost added to the ingredient cost of the menu item
     * @param tempInMenu     whether or not the item is in the menu
     * @param itemType       the type of the item
     */
    public MenuItem(String tempItemName, Recipe tempRecipe, Money tempMarkupCost, boolean tempInMenu, TypeEnum itemType) {
        itemName = tempItemName;
        recipe = tempRecipe;
        markupCost = tempMarkupCost;
        inMenu = tempInMenu;
        this.itemType = itemType;
        edited = false;
        totalCost = calculateFinalCost();
    }


    /**
     * This method runs a loop over the ingredientAmount hash map and calculates the total cost of making a menu item in NZD
     *
     * @return the making cost of the recipe in the form of the money object in NZD
     */
    public Money calculateMakingCost() {
        Money recipeMakingCost = Money.parse("NZD 0.00");
        if (recipe != null) {
            HashMap<Ingredient, Integer> ingredients = recipe.getIngredientsAmount();
            for (Map.Entry<Ingredient, Integer> eachIngredient : ingredients.entrySet()) {
                Ingredient ingredient = eachIngredient.getKey();
                Integer amount = eachIngredient.getValue();
                recipeMakingCost = recipeMakingCost.plus(ingredient.getCost().multipliedBy(amount));
            }
        }
        return recipeMakingCost;
    }

    /**
     * This function adds a markup to the total cost of the ingredients based
     * on the markup cost
     *
     * @return the selling cost of the menu item in the form of the Money object in NZD
     */
    public Money calculateFinalCost() {
        totalCost = Money.parse("NZD 0.00");
        if (markupCost != null) {
            totalCost = calculateMakingCost().plus(markupCost);
        }
        return totalCost;

    }

    /**
     *
     * @return a boolean based on if the if the item is in the menu or not
     */
    public boolean isInMenu() {
        return inMenu;
    }

    /**
     * The function returns the id for the menu item
     * @return td id for the menu item
     */

    public String getID() {
        return id;
    }

    /**
     * The function returns the name of the item
     * @return the name of the item
     */

    public String getItemName() {
        if (edited) {
            return  "Edited\n" +itemName;
        } else {
            return itemName;
        }
    }

    /**
     *
     * @return The function returns the recipe object
     */

    public Recipe getRecipe() {
        return recipe;
    }

    /**
     *
     * @return The function returns the markup cost set by the user
     */

    public Money getMarkupCost() { return markupCost; }

    /**
     * The function assigns the value to the markup cost
     * @param markupCost is the cost added to the ingredient cost of the menu item
     */

    public void setMarkupCost(Money markupCost) {
        this.markupCost = markupCost;
    }

    /**
     *
     * @return a unique integer based on the contents of this menu item
     */

    public int hashcode() {
        ArrayList<Object> tempList = new ArrayList<>();
        tempList.addAll(recipe.getIngredientsAmount().values());
        tempList.addAll(recipe.getIngredientsAmount().keySet());
        tempList.add(recipe.getName());
        tempList.add(id);
        tempList.add(markupCost);
        return Objects.hash(tempList);
    }

    public boolean equals(MenuItem other) {
        return hashcode() == other.hashcode();
    }

    /**
     * Creates a clone of this menu item, with a different instance of Recipe
     *
     * @return the clone of this menu item
     */
    public MenuItem clone() {
        Recipe tempRecipe = new Recipe(recipe.getName(),
                                       recipe.getRecipeText(),
                                       new HashMap<>(recipe.getIngredientsAmount()),
                                       new HashMap<>(recipe.getIngredientIDs()));
        MenuItem returnItem = new MenuItem(itemName, tempRecipe, markupCost, id, inMenu);
        returnItem.setEdited(edited);
        returnItem.setType(itemType);
        return returnItem;
    }

    /**
     *
     * @return the enum which reflects the dietary information for the menu item
     */

    public TypeEnum getItemType() {
        return itemType;
    }

    /**
     * This function sets the itemType enum for the menu item
     * @param itemType the new type of the item
     */

    public void setType(TypeEnum itemType) {
        this.itemType = itemType;
    }

    /**
     * The function sets the name of the menu item
     * @param tempName the assigned name for the menu item
     */

    public void setItemName(String tempName) {
        itemName = tempName;
    }

    /**
     * Checks if this menu item is edited or not
     *
     * @return the value for the boolean if the item was edited
     */
    public boolean isEdited() {
        return edited;
    }

    /**
     * Sets the boolean to true if the menu item was edited
     * @param tempEdited whether or not the item was edited
     */

    public void setEdited(boolean tempEdited) {
        edited = tempEdited;
    }

    /**
     * The function returns the total cost of the menu item
     * @return the total cost for a menu item
     */
    public Money getTotalCost() {
        calculateFinalCost();
        return totalCost;
    }

    /**
     * The function sets the total cost of the menu item
     * @param totalCost the final cost of the menu item
     */
    public void setTotalCost(Money totalCost) {
        this.totalCost = totalCost;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

}





