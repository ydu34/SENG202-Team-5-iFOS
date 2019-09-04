package seng202.group5;

import org.joda.money.Money;
/**
 * The Transaction class records past orders and if they were refunded;
 */
public class Transaction {
    private int date;
    private int time;
    private Money change;
    private Money totalPrice;
    private String orderNum;
    private Boolean isRefunded;

    public Transaction(int newDate, int newTime, Money newChange, Money newTotalPrice) {
        isRefunded = false;
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
    public int getDate() {
        return date;
    }
    public int getTime() {
        return time;
    }
    public void Refund() {
        isRefunded = true;
    }
}
