package seng202.group5;

import org.joda.money.Money;

import java.util.ArrayList;
import java.util.HashMap;

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



}
