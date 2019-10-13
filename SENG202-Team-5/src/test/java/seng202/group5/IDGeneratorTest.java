package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.Test;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.logic.Order;
import seng202.group5.logic.Stock;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class IDGeneratorTest {

    @Test
    void testUniqueIngredientIDs() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(IDGenerator.newIngredientID());
        }

        HashSet<String> set = new HashSet<>(list);
        assertEquals(set.size(), list.size());
    }

    @Test
    void testUniqueMenuItemIDs() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(IDGenerator.newMenuItemID());
        }

        HashSet<String> set = new HashSet<>(list);
        assertEquals(set.size(), list.size());
    }

    @Test
    void testUniqueOrderIDs() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(IDGenerator.newOrderID());
        }

        HashSet<String> set = new HashSet<>(list);
        assertEquals(set.size(), list.size());
    }

    @Test
    void testUniqueCustomerIDs() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(IDGenerator.newCustomerID());
        }

        HashSet<String> set = new HashSet<>(list);
        assertEquals(set.size(), list.size());
    }


    @Test
    void testClassIDs() {
        Ingredient ingredient = new Ingredient();
        Order order = new Order(new Stock());
        MenuItem item = new MenuItem(null, null, Money.parse("NZD 0.10"), false, null);

        ArrayList<String> ids = new ArrayList<>();
        ids.add(ingredient.getID());
        ids.add(order.getId());
        ids.add(item.getID());

        for (int i = 0; i < 3; i++) {
            for (String idd : ids) {
                if (ids.indexOf(idd) != i) {
                    assertNotEquals(ids.get(i), idd);
                }
            }
        }
    }

    @Test
    void testSetIngredientID() {
        int prevID = 700;

        assertNotEquals(IDGenerator.getIngredientID(), prevID);

        IDGenerator.setIngredientID(prevID);

        assertEquals(IDGenerator.getIngredientID(), prevID);
    }

    @Test
    void testSetMenuItemID() {
        int prevID = 700;

        assertNotEquals(IDGenerator.getMenuItemID(), prevID);

        IDGenerator.setMenuItemID(prevID);

        assertEquals(IDGenerator.getMenuItemID(), prevID);
    }

    @Test
    void testSetOrderID() {
        int prevID = 700;

        assertNotEquals(IDGenerator.getOrderID(), prevID);

        IDGenerator.setOrderID(prevID);

        assertEquals(IDGenerator.getOrderID(), prevID);
    }

    @Test
    void testSetCustomerID() {
        int prevID = 700;

        assertNotEquals(IDGenerator.getCustomerID(), prevID);

        IDGenerator.setCustomerID(prevID);

        assertEquals(IDGenerator.getCustomerID(), prevID);
    }
}
