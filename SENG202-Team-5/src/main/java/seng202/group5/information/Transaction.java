package seng202.group5.information;

import org.joda.money.Money;
import seng202.group5.IDGenerator;
import seng202.group5.adapters.LocalDateTimeAdapter;
import seng202.group5.adapters.MoneyAdapter;
import seng202.group5.logic.Order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;


/**
 * The Transaction class records past orders and if they were refunded;
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {

    /**
     * When this transaction occurred
     */
    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime datetime;

    /**
     * The change given
     */
    @XmlJavaTypeAdapter(value = MoneyAdapter.class)
    private Money change;
    @XmlJavaTypeAdapter(value = MoneyAdapter.class)
    private Money totalPrice;
    private String transactionID;

    /**
     * Whether or not this transaction has been refunded
     */
    private Boolean refunded;

    private Order order;

    Transaction() {}

    public Transaction(LocalDateTime newDateTime, Money newChange, Order newOrder) {
        refunded = false;
        datetime = newDateTime;
        change = newChange;
        if (newOrder != null) {
            totalPrice = newOrder.getTotalCost();
            transactionID = newOrder.getId();
        } else {
            totalPrice = null;
            transactionID = IDGenerator.newTransactionID();
        }
        order = newOrder;
    }

    public Boolean isRefunded() {
        return refunded;
    }
    public Money getTotalPrice() {
        return totalPrice;
    }
    public Money getChange() {
        return change;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public LocalDateTime getDateTime() {
        return datetime;
    }

    public void refund() {
        refunded = true;
    }

    public String getOrderID() {
        return order.getId();
    }

    public Order getOrder() {
        return order;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
}
