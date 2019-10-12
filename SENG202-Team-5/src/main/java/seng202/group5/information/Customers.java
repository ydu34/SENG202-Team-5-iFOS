package seng202.group5.information;

import java.util.ArrayList;

/**
 * Holds the list of customers, allows JAXB to easily import and export the customers data
 * @author James Kwok
 */
public class Customers {

    private static ArrayList<Customer> customerList;

    public Customers() {
        customerList = new ArrayList<>();
    }

    public static void add(Customer customer) {
        customerList.add(customer);
    }

    public void setCustomerMap(ArrayList<Customer> tempCustomerSet) {
        customerList = tempCustomerSet;
    }

    public ArrayList<Customer> getCustomerSet() {
        return customerList;
    }
}
