package seng202.group5;

import org.joda.money.Money;
import seng202.group5.exceptions.InsufficientCashException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Till class keeps track of held denominations value and how many are held. Has methods to calculate the total amount
 * held and methods to calculate change.
 *
 * @author James Kwok
 */
public class Till {

    private HashMap<Money, Integer> denominations;


    public Till(ArrayList<Money> moneyList) {
        for (int i = 0; i < moneyList.size(); i++) {
            denominations.put(moneyList.get(i), 0);
        }
    }

    public Till(HashMap<Money, Integer> tempDenominations) {
        denominations = tempDenominations;
}
    /**
    * Adds the given value to the denominations stored.
     * @param value holds the value of a denomination using Joda Money.
     * @param count The number of denominations to be added.
     */
    public void addDenomination(Money value, int count) {
        if (denominations.containsKey(value)) {
            count += denominations.get(value);
        }
        denominations.put(value, count);
    }

    /**
     * Removes a number stated by count worth of denominations from the held denominations
     * @param value holds the value of a denomination using Joda Money.
     * @param count The number of denominations to be added.
     */
    public void removeDenomination(Money value, int count) throws InsufficientCashException { //Requires an exception.
        if (denominations.containsKey(value)) {
            try {
                count -= denominations.get(value);
                if (count < 0) {
                    throw new InsufficientCashException("");
                }
            } catch InsufficientCashException(String e)
            }
        }
        denominations.put(value, count);
    }

    /**
     * Adds multiple denominations by calling addDenomination multiple times.
     * @param valueMap Uses the Money type as a key and the number of denominations to be removed.
     */
    public addDenominations(HashMap<Money, Integer> valueMap) {

    }

    /**
     * Returns the total amount of money held as a sum.
     */
    public totalValue() {

    }

    public HashMap<Money, Integer> getDenominations() {
        return denominations;
    }
}
