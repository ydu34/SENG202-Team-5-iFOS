package seng202.group5;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class IDGeneratorTest {

    private IDGenerator generator = new IDGenerator();

    @Test
    public void testUniqueIDs() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add(generator.newID());
        }

        HashSet<String> set = new HashSet<String>(list);
        assertEquals(set.size(), list.size());
    }

    @Test
    public void testClassIDs() {
        Ingredient ingredient = new Ingredient();
        Order order = new Order();
        MenuItem item = new MenuItem();
        Transaction transaction = new Transaction();

        ArrayList<String> ids = new ArrayList<String>();
        ids.add(ingredient.getID());
        ids.add(order.getID());
        ids.add(item.getID());
        ids.add(transaction.getOrderNum());

        for (int i = 0; i < 4; i++) {
            for (String idd : ids) {
                if (ids.indexOf(idd) != i) {
                    assertNotEquals(ids.get(i), idd);
                }
            }
        }
    }

}
