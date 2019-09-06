package seng202.group5;

import org.joda.money.Money;
import org.joda.time.DateTime;


/**
 * The Transaction class records past orders and if they were refunded;
 */
public class Transaction {
    private DateTime date;
    private int time;
    private Money change;
    private Money totalPrice;
    private String orderNum;
    private Boolean isRefunded;

    public Transaction(DateTime newDate, int newTime, Money newChange, Money newTotalPrice) {
        isRefunded = false;
        date = newDate;
        time = newTime;
        change = newChange;
        totalPrice = newTotalPrice;

    }

    public Boolean getRefunded() {
        return isRefunded;
    }
    public Money getTotalPrice() {
        return totalPrice;
    }
    public Money getChange() {
        return change;
    }
    public String getOrderNum() {
        return orderNum;
    }
    public DateTime getDate() {
        return date;
    }
    public int getTime() {
        return time;
    }
    public void refund() {
        isRefunded = true;
    }
}
