package seng202.group5.logic;

import org.joda.money.Money;
import seng202.group5.IDGenerator;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Transaction;

import javax.xml.bind.annotation.*;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Finance class records order history, refunds past orders and calculates change.
 *
 * @author Tasman Berry, Daniel Harris
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Finance {

    /**
     * A list of cash denominations available
     */
    @XmlTransient
    private static ArrayList<Money> denomination = new ArrayList<>(Arrays.asList(Money.parse("NZD 100.00"),
            Money.parse("NZD 50.00"), Money.parse("NZD 20.00"), Money.parse("NZD 10.00"),
            Money.parse("NZD 5.00"), Money.parse("NZD 2.00"), Money.parse("NZD 1.00"),
            Money.parse("NZD 0.50"), Money.parse("NZD 0.20"), Money.parse("NZD 0.10")));
    /**
     * An ID generator stored here so that it is kept persistent across the application
     */
    @XmlElement
    private IDGenerator generator = new IDGenerator();
    private HashMap<String, Transaction> transactionHistory;
    @XmlElement
    private Till till;


    public Finance() {
        transactionHistory = new HashMap<>();
        till = new Till(denomination);
    }

    /**
     * Refunds a previous order and returns the list of notes to return in descending size order.
     *
     * @param ID the id of the order to refund
     * @return a list of Money representing coins in descending size order
     */
    public ArrayList<Money> refund(String ID) {
        Transaction refundedOrder = transactionHistory.get(ID);
        ArrayList<Money> refund = calcChange(refundedOrder.getTotalPrice());
        Money refundSum = Money.parse("NZD 0.00");
        for (Money amt : refund) refundSum = refundSum.plus(amt);
        if (refundSum.equals(refundedOrder.getTotalPrice()) && !refundedOrder.isRefunded()) {
            refundedOrder.refund();
            return refund;
        } else {
            for (Money coin : refund) till.addDenomination(coin, 1);
            return new ArrayList<>();
        }
    }

    /**
     * Saves order to database and returns a list of notes to return as change.
     *
     * @param amountPayed a list of Money representing the coins payed
     * @param datetime    the Date and time the order occurred at
     * @param order       the order that is being paid for
     * @return a list of Money representing coins to give as change in descending size order
     * @throws InsufficientCashException Throws error when total cost is negative or the total cost is higher than the amount payed
     */
    public ArrayList<Money> pay(ArrayList<Money> amountPayed, LocalDateTime datetime, Order order) throws InsufficientCashException {
        if (!enoughMoney(amountPayed, order)) throw new InsufficientCashException("Not enough cash in till!");
        Money payedSum = Money.parse("NZD 0");
        Money changeSum = Money.parse("NZD 0");
        Money totalCost = order.getTotalCost();

        for (Money money : amountPayed) {
            payedSum = payedSum.plus(money);
        }
        if (totalCost.isGreaterThan(payedSum) || totalCost.isNegative()) {
            throw new InsufficientCashException();
        }

        for (Money money : amountPayed) {
            till.addDenomination(money, 1);
        }
        ArrayList<Money> change = calcChange(payedSum.minus(totalCost).rounded(1, RoundingMode.HALF_DOWN));
        for (Money money : change) {
            changeSum = changeSum.plus(money);
        }
        Transaction transaction = new Transaction(datetime, changeSum, order);
        transactionHistory.put(transaction.getTransactionID(), transaction);

        return change;
    }

    /**
     * Checks the till for if there are enough denominations to give change
     *
     * @param payment The ArrayList of money given from the customer.
     * @param order   The order which the customer is paying for.
     * @return A boolean saying whether there is enough money in the till to give back change.
     */
    private boolean enoughMoney(ArrayList<Money> payment, Order order) {
        // Copy the till so that changes can be made without affecting the main program
        Till copyTill = till.clone();

        // Calculating the total amount remaining after payment
        Money totalPayed = Money.parse("NZD 0");
        for (Money money : payment) {
            totalPayed = totalPayed.plus(money);
            copyTill.addDenomination(money, 1);
        }


        Money total = totalPayed.minus(order.getTotalCost()).rounded(1, RoundingMode.HALF_DOWN);
        try {
            for (Money money : denomination) {
                while (total.isGreaterThan(money)) {
                    total = total.minus(money);
                    copyTill.removeDenomination(money, 1);
                }
            }
        } catch (InsufficientCashException e) {
            return false;
        }
        return true;
    }

    /**
     * returns a list containing total profits, average profits, and other information to be displayed on the finance screen over the imputed time period
     * Index 0: Total income before expenses
     * Index 1: Average daily income before expenses
     * Index 2: Total profits
     * Index 3: Average daily profits
     *
     * @param startDate the first date to search from
     * @param endDate   the last date to search to
     * @return a list of Money representing total profits, average profits, and other things
     */
    public ArrayList<Money> totalCalculator(LocalDateTime startDate, LocalDateTime endDate) {
        Money total = Money.parse("NZD 0");
        Money totalProfits = Money.parse("NZD 0.00");
        for (Transaction transaction : transactionHistory.values()) {
            if (transaction.getDateTime().compareTo(startDate) >= 0 &&
                    transaction.getDateTime().compareTo(endDate) <= 0) {
                if (!transaction.isRefunded()) {
                    total = total.plus(transaction.getTotalPrice());
                    totalProfits = totalProfits.plus(transaction.getTotalPrice());
                }
                for (Map.Entry<MenuItem, Integer> entry : transaction.getOrder().getOrderItems().entrySet()) {
                    totalProfits = totalProfits.minus(entry.getKey().calculateMakingCost().multipliedBy(entry.getValue()));
                }
            }
        }
        ArrayList<Money> totals = new ArrayList<>();
        totals.add(total);

        long daysBetween = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate()) + 1;
        totals.add(total.dividedBy(daysBetween, RoundingMode.DOWN));

        totals.add(totalProfits);
        totals.add(totalProfits.dividedBy(daysBetween, RoundingMode.DOWN));

        return totals;
    }

    /**
     * returns a list containing the change need to be returned
     *
     * @param change the first date to search from
     * @return returns a list containing the change need to be returned
     */
    public ArrayList<Money> calcChange(Money change) {
        ArrayList<Money> totalChange = new ArrayList<>();

        // First try a greedy algorithm
        Money tempChange = change.rounded(1, RoundingMode.HALF_UP);
        for (Money value : denomination) {
            while (!tempChange.isLessThan(value) && till.getDenominations().get(value) > 0) {
                try {
                    till.removeDenomination(value, 1);
                    totalChange.add(value);
                    tempChange = tempChange.minus(value);
                } catch (InsufficientCashException e) {

                }
            }
        }

        // Return the result if it matches the amount required
        if (tempChange.isZero()) {
            return totalChange;
        }

        // Otherwise, try a dynamic programming approach
        for (Money coin : totalChange) till.addDenomination(coin, 1);
        totalChange = new ArrayList<>();
        ArrayList<Map.Entry<Money, Money>> valueList = new ArrayList<>(); // Helper value, Actual value (weight)
        ArrayList<Money> tempDenominations = new ArrayList<>(denomination);
        tempDenominations.sort(Money::compareTo);
        for (Money coin : tempDenominations) {
            for (int i = 0; i < till.getDenominations().getOrDefault(coin, 0); i++) {
                valueList.add(Map.entry(coin.minus(Money.parse("NZD 0.01")), coin));
            }
        }

        ArrayList<ArrayList<Money>> cache = new ArrayList<>();
        ArrayList<Money> tempList = new ArrayList<>();
        for (Money i = Money.parse("NZD 0.00");
             i.isLessThan(change) || i.isEqual(change);
             i = i.plus(Money.parse("NZD 0.10"))) {
            tempList.add(Money.parse("NZD 0.00"));
        }
        for (int i = 0; i <= valueList.size(); i++) cache.add(new ArrayList<>(tempList));

        for (int n = 1; n <= valueList.size(); n++) {
            for (int top = 1; top < cache.get(0).size(); top++) {
                Money best = cache.get(n - 1).get(top);
                Money valueWithoutLast = best;
                Map.Entry<Money, Money> coin = valueList.get(n - 1);
                if (!coin.getValue().isGreaterThan(Money.parse("NZD 0.10").multipliedBy(top))) {
                    Money valueWithLast = coin.getKey().plus(cache.get(n - 1).get(top - coin.getValue()
                            .multipliedBy(10).getAmountMajorInt()));
                    if (valueWithLast.isGreaterThan(valueWithoutLast)) best = valueWithLast;
                }
                cache.get(n).set(top, best);
            }
        }

        int x = cache.get(0).size() - 1;
        int y = cache.size() - 1;
        while (x != 0 && y != 0) {
            Money value = valueList.get(y - 1).getKey();
            Money coin = valueList.get(y - 1).getValue();
            int diff = coin.multipliedBy(10).getAmountMajorInt();
            if (x >= diff && !cache.get(y - 1).get(x).isGreaterThan(
                    cache.get(y - 1).get(x - diff).plus(value))) {
                totalChange.add(coin);
                x = x - diff;
            }
            y--;
        }

        return totalChange;
    }

    /**
     * Gets a clone of the transaction history
     *
     * @return a clone of the transaction history
     */
    public HashMap<String, Transaction> getTransactionHistoryClone() {
        return new HashMap<>(transactionHistory);
    }

    public Till getTill() {
        return till;
    }

    public void setTill(Till till) {
        this.till = till;
    }

    public HashMap<String, Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public ArrayList<Money> getDenomination() {
        return denomination;
    }
}
