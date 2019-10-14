package seng202.group5.information;

import org.joda.money.Money;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

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

    @Test
    public void testSetGetId() {
        Ingredient ingredient = new Ingredient("ing1", "meat", "TId", Money.parse("NZD 0.10"));
        assertEquals(ingredient.getId(), "TId");
        ingredient.setId("TId2");
        assertEquals(ingredient.getId(), "TId2");
    }

    @Test
    public void testSetGetCategory() {
        Ingredient ingredient = new Ingredient("ing1", "meat", "TId", Money.parse("NZD 0.10"));
        assertEquals(ingredient.getCategory(), "meat");
        ingredient.setCategory("Vege");
        assertEquals(ingredient.getCategory(), "Vege");
    }

    @Test
    public void testSetGetPrice() {
        Ingredient ingredient = new Ingredient("ing1", "meat", "TId", Money.parse("NZD 0.10"));
        assertEquals(ingredient.getPrice(), Money.parse("NZD 0.10"));
        ingredient.setPrice(Money.parse("NZD 1000"));
        assertEquals(ingredient.getPrice(), Money.parse("NZD 1000"));
    }
    @Test
    public void testDietaryInfoString() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("New ingredient");
        ingredient.setPrice(Money.parse("NZD50.00"));
        ingredient.setId("INGR15");
        HashSet<DietEnum> testEnumSet = new HashSet<>();
        testEnumSet.add(DietEnum.GLUTEN_FREE);
        ingredient.setDietaryInformation(testEnumSet);
        assertEquals("Gluten free", ingredient.getDietaryInformationString());
    }

    @Test
    public void testRemoveDietInfo() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("New ingredient");
        ingredient.setPrice(Money.parse("NZD50.00"));
        ingredient.setId("INGR15");
        HashSet<DietEnum> testEnumSet = new HashSet<>();
        testEnumSet.add(DietEnum.VEGETARIAN);
        ingredient.setDietaryInformation(testEnumSet);
        assertTrue(ingredient.getDietInfo().size() == 1);
        ingredient.removeDietInfo(DietEnum.VEGETARIAN);
        assertTrue(ingredient.getDietInfo().size() == 0);
    }

    @Test
    public void testRemoveMultipleDietInfo() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("New ingredient");
        ingredient.setPrice(Money.parse("NZD50.00"));
        ingredient.setId("INGR15");
        HashSet<DietEnum> testEnumSet = new HashSet<>();
        testEnumSet.add(DietEnum.VEGETARIAN);
        testEnumSet.add(DietEnum.VEGAN);
        ingredient.setDietaryInformation(testEnumSet);
        assertTrue(ingredient.getDietInfo().size() == 2);
        ArrayList<DietEnum> toBeRemoved = new ArrayList<>();
        toBeRemoved.add(DietEnum.VEGAN);
        toBeRemoved.add(DietEnum.VEGETARIAN);
        ingredient.removeDietInfo(toBeRemoved);
        assertTrue(ingredient.getDietInfo().size() == 0);
    }


}
