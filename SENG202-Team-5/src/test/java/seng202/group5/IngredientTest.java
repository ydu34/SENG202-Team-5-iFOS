package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.Test;
import seng202.group5.information.Ingredient;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    @Test
    public void testEquals() {
        Ingredient ingredient1 = new Ingredient("ing1", "meat", Money.parse("NZD 0.10"));
        Ingredient ingredient2 = new Ingredient("ing2", "meat", Money.parse("NZD 0.10"));

        assertTrue(ingredient1.equals(ingredient1));
        assertFalse(ingredient1.equals(ingredient2));
    }

    @Test
    public void testDietaryInfo() {
        Ingredient ingredient = new Ingredient("ing1", "g", "meat", Money.parse("NZD 0.10"));

        ArrayList<DietEnum> list = new ArrayList<>();
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
