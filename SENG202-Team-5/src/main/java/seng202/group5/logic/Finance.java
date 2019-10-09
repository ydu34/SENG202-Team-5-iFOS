package seng202.group5.logic;

import org.joda.money.Money;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Transaction;

import javax.xml.bind.annotation.*;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Finance class records order history, refunds past orders and calculates change.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Finance {

    private HashMap<String, Transaction> transactionHistory;

    /**
     * A list of cash denominations available
     */
    @XmlTransient
    private static ArrayList<Money> denomination;
    @XmlElement
    private Till till;
    

    public Finance() {
        transactionHistory = new HashMap<>();
        denomination = new ArrayList<>();
        denomination.add(Money.parse("NZD 100.00"));
        denomination.add(Money.parse("NZD 50.00"));
        denomination.add(Money.parse("NZD 20.00"));
        denomination.add(Money.parse("NZD 10.00"));
        denomination.add(Money.parse("NZD 5.00"));
        denomination.add(Money.parse("NZD 2.00"));
        denomination.add(Money.parse("NZD 1.00"));
        denomination.add(Money.parse("NZD 0.50"));
        denomination.add(Money.parse("NZD 0.20"));
        denomination.add(Money.parse("NZD 0.10"));
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
        }
        return new ArrayList<>();
    }

    /**
     * Saves order to database and returns a list of notes to return as change.
     *
     * @param amountPayed a list of Money representing the coins payed
     * @param datetime        the Date and time the order occurred at
     * @param order     the order that is being paid for
     * @return a list of Money representing coins to give as change in descending size order
     * @throws InsufficientCashException Throws error when total cost is negative or the total cost is higher than the amount payed
     */
    public ArrayList<Money> pay(ArrayList<Money> amountPayed, LocalDateTime datetime, Order order) throws InsufficientCashException {
        Money payedSum = Money.parse("NZD 0");
        Money changeSum = Money.parse("NZD 0");
        Money totalCost = order.getTotalCost();

        for (Money money: amountPayed)
        {
            payedSum = payedSum.plus(money);
        }
        if (totalCost.isGreaterThan(payedSum) || totalCost.isNegative()) {
            throw new InsufficientCashException();
        }

        for (Money money: amountPayed)
        {
            till.addDenomination(money, 1);
        }
        ArrayList<Money> change = calcChange(payedSum.minus(totalCost));
        for (Money money: change)
        {
            changeSum = changeSum.plus(money);
        }
        Transaction transaction = new Transaction(datetime, changeSum, order);
        transactionHistory.put(transaction.getTransactionID(), transaction);

        return change;
    }

    /**
     * returns a list containing total profits, average profits, and other information to be displayed on the finance screen over the imputed time period
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
        change = change.plus(Money.parse("NZD 0.03"));

        for (Money value : denomination)
        {
            while (change.isGreaterThan(value) && till.getDenominations().get(value) > 0) {

                totalChange.add(value);
                change = change.minus(value);
                try {
                    till.removeDenomination(value, 1);
                } catch (InsufficientCashException e) {
                    e.printStackTrace();
                }
            }
        }
        return totalChange;
    }

    /**
     * Gets a clone of the transaction history
     *
     * @return a clone of the transaction history
     */
    public HashMap<String, Transaction> getTransactionHistoryClone() {
        return (HashMap<String, Transaction>) transactionHistory.clone();
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
