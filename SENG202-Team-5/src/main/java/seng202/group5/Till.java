package seng202.group5;

import org.joda.money.Money;

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
    public addDenomination(Money value, int count) { //Requires an exception.

    }

    /**
     * Removes a number stated by count worth of denominations from the held denominations
     * @param value holds the value of a denomination using Joda Money.
     * @param count The number of denominations to be added.
     */
    public removeDenomination(Money value, int count) { //Requires an exception.

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
