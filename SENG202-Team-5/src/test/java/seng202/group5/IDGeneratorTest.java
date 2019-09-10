package seng202.group5;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
