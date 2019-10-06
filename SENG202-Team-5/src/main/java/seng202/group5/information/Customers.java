package seng202.group5.information;


import java.util.HashMap;

/**
 * Holds the list of customers, allows JAXB to easily import and export the customers data
 * @author James Kwok
 */
public class Customers {
    private HashMap<Integer, Customer> customerMap;


    public void setCustomerMap(HashMap<Integer, Customer> tempCustomerMap) {
        customerMap = tempCustomerMap;
    }

    public HashMap<Integer, Customer> getCustomerMap() {
        return customerMap;
    }
}
