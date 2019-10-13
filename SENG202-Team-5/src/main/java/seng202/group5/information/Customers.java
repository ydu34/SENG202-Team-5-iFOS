package seng202.group5.information;

import seng202.group5.IDGenerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Holds the list of customers, allows JAXB to easily import and export the customers data
 *
 * @author James Kwok
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customers {

    /**
     * An ID generator stored here so that it is kept persistent across the application
     */
    @XmlElement
    private IDGenerator generator = new IDGenerator();

    @XmlElement
    private ArrayList<Customer> customerList = new ArrayList<>();

    private CustomerSettings customerSettings = new CustomerSettings();

    public Customers() {
    }

    public void add(Customer customer) {
        customer.setPurchasePoints(customerSettings.getInitialPurchasePoints());
        customerList.add(customer);
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public CustomerSettings getCustomerSettings() {
        return customerSettings;
    }

    public void setCustomerSettings(CustomerSettings customerSettings) {
        this.customerSettings = customerSettings;
    }

}
