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
    private ArrayList<Customer> customerList = new ArrayList<>();

    public Customers() {    }

    public void add(Customer customer) {
        customerList.add(customer);
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }
}
