package seng202.group5.logic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds information on misc settings within the system.
 *
 * @author Michael Morgoun
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Settings {

    /**
     * The max ingredient amount for extra ingredients.
     */
    private int maxIngredientAmount = 50;


    /**
     * Gets the max ingredient amount for adding extra ingredients.
     * @return The max ingredient amount.
     */
    public int getMaxIngredientAmount() {
        return maxIngredientAmount;
    }

    /**
     * Sets the max ingredient amount for adding extra ingredients.
     * @param tempMaxIngredientAmount The new max ingredient.
     */
    public void setMaxIngredientAmount(int tempMaxIngredientAmount) {
        maxIngredientAmount = tempMaxIngredientAmount;
    }


}
