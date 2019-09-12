package seng202.group5;

import org.joda.money.Money;
import seng202.group5.adapters.LocalDateTimeAdapter;

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

    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    private LocalDateTime datetime;
    private Money change;
    private Money totalPrice;
    private IDGenerator generator = new IDGenerator();
    private String orderNum = generator.newID();
    private Boolean isRefunded;

    Transaction() {}

    public Transaction(LocalDateTime newDateTime, Money newChange, Money newTotalPrice) {
        isRefunded = false;
        datetime = newDateTime;
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

    public LocalDateTime getDateTime() {
        return datetime;
    }
    public void refund() {
        isRefunded = true;
    }
}
