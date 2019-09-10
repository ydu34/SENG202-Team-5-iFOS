package seng202.group5;

import org.junit.jupiter.api.Test;

import java.security.DigestException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    @Test
    public void testEquals() {
        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        Ingredient ingredient3 = ingredient1;

        assertTrue(ingredient1.equals(ingredient3));
        assertFalse(ingredient1.equals(ingredient2));
    }

    @Test
    public void testDietaryInfo() {
        Ingredient ingredient = new Ingredient();

        ArrayList<DietEnum> list = new ArrayList<DietEnum>();
        list.add(DietEnum.VEGETARIAN);
        list.add(DietEnum.GLUTEN_FREE);


        ingredient.addDietInfo(DietEnum.VEGAN);
        assertTrue(ingredient.getDietInfo().contains(DietEnum.VEGAN));

        ingredient.addDietInfo(list);
        assertEquals(ingredient.getDietInfo().size(), 3);

        ingredient.removeDietInfo(DietEnum.VEGAN);
        assertEquals(ingredient.getDietInfo().size(), 2);

        ingredient.removeDietInfo(list);
        assertEquals(ingredient.getDietInfo().size(), 0);
    }
}
