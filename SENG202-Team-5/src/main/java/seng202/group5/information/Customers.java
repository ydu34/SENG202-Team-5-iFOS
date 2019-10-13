package seng202.group5.information;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Holds the list of customers, allows JAXB to easily import and export the customers data
 * @author James Kwok
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customers {

    @XmlElement
    private ArrayList<Customer> customerList;

    public Customers() {
        customerList = new ArrayList<>();
    }

    public void add(Customer customer) {
        customerList.add(customer);
    }

    public void setCustomerList(ArrayList<Customer> tempCustomerSet) {
        customerList = tempCustomerSet;
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }
}
