package seng202.group5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.information.Customer;
import seng202.group5.information.Customers;

import javax.xml.bind.JAXBException;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomersXmlTest {

    private static String testDirectory = System.getProperty("user.dir") + "/src/test/java/seng202/group5";
    private static AppEnvironment appEnvironment = new AppEnvironment(false);
    private Database database = appEnvironment.getDatabase();
    private Customers customers;

    private static Customer customer1;
    private static Customer customer2;
    private static Customer customer3;

    @BeforeAll
    static void createAndMarshallCustomers() {
        AppEnvironment oldAppEnvironment = new AppEnvironment(false);

        customer1 = new Customer();
        customer1.setName("test1");
        customer1.setPhoneNumber("123");
        customer1.setPurchasePoints(1);

        customer2 = new Customer();
        customer2.setName("test2");
        customer2.setPhoneNumber("321");
        customer2.setPurchasePoints(2);

        customer3 = new Customer();
        customer3.setName("test3");
        customer3.setPhoneNumber("246");
        customer3.setPurchasePoints(3);

        oldAppEnvironment.getCustomers().add(customer1);
        oldAppEnvironment.getCustomers().add(customer2);

        appEnvironment.setCustomers(oldAppEnvironment.getCustomers());

        try {
            oldAppEnvironment.getDatabase().objectToXml(Customers.class, oldAppEnvironment.getCustomers(), "customers.xml", testDirectory);
        } catch (JAXBException e) {
            System.out.println("Failed to marshall object");
        }
    }

    @AfterAll
    static void tearDown() {
        File file = new File(testDirectory + "/customers.xml");
        file.delete();
    }

    @BeforeEach
    void testUnmarshallCustomers() {
        try {
            database.customersXmlToObject(testDirectory);
            customers = appEnvironment.getCustomers();
            assertEquals(2, customers.getCustomerList().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCustomerNameInCustomers() {
        String name = "test1";
        String ID = customer1.getID();
        for (Customer customer : customers.getCustomerList()) {
            if (ID.equals(customer.getID())) {
                assertEquals(customer.getName(), name);
            }
        }
    }

    @Test
    void testCustomerPhoneInCustomers() {
        String phoneNumber = "321";
        String ID = customer2.getID();
        for (Customer customer : customers.getCustomerList()) {
            if (ID.equals(customer.getID())) {
                assertEquals(customer.getPhoneNumber(), phoneNumber);
            }
        }
    }

    @Test
    void testCustomerPurchasePointsCustomers() {
        int purchasePoints = 1;
        String ID = customer3.getID();
        for (Customer customer : customers.getCustomerList()) {
            if (ID.equals(customer.getID())) {
                assertEquals(customer.getPurchasePoints(), purchasePoints);
            }
        }
    }

}
