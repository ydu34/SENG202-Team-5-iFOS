package seng202.group5.information;


import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple AppEnvironment.
 */
public class RecipeTest {

    private Recipe testRecipe;

    private Ingredient chickenPatty;

    private HashSet<DietEnum> ingredientInfo;

    @BeforeEach
    public void setUp() {
        ingredientInfo = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE); }};
        testRecipe = new Recipe("Chicken burger", "Steps to chicken burger");
        chickenPatty = new Ingredient("chicken", "meat", "12", Money.parse("NZD 20"), ingredientInfo);
    }

    @Test
    public void testDietaryInformationString() {
        assertEquals("Gluten free, Vegan, Vegetarian", testRecipe.getDietaryInformationString());
        testRecipe.addIngredient(chickenPatty, 1);
        assertEquals("Gluten free", testRecipe.getDietaryInformationString());
    }

    @Test
    public void testAddIngredient() {


        HashSet<DietEnum> ingredientInfo = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);

        }};
        Ingredient cheese = new Ingredient("cheese", "dairy", "12", Money.parse("NZD 20"), ingredientInfo);
        testRecipe.addIngredient(chickenPatty, 2);
        assertEquals(2, (int) testRecipe.getIngredientsAmount().get(chickenPatty));
        testRecipe.addIngredient(chickenPatty, 12);
        testRecipe.addIngredient(cheese, 1);
        assertEquals(14, (int) testRecipe.getIngredientsAmount().get(chickenPatty));
        assertTrue(testRecipe.getDietaryInformation().contains(DietEnum.GLUTEN_FREE));
        assertFalse(testRecipe.getDietaryInformation().contains(DietEnum.VEGETARIAN));
        assertFalse(testRecipe.getDietaryInformation().contains(DietEnum.VEGAN));

    }

    @Test
    public void testRemoveIngredient() {

        HashSet<DietEnum> ingredientInfo = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
            //add(DietEnum.VEGAN);
        }};
        Ingredient cheese = new Ingredient("cheese", "dairy", "12", Money.parse("NZD 20"), ingredientInfo);

        testRecipe.addIngredient(chickenPatty, 12);
        testRecipe.removeIngredient(chickenPatty, 3);
        assertNotEquals(23, testRecipe.getIngredientsAmount().get(chickenPatty));
        assertEquals(9, (int) testRecipe.getIngredientsAmount().get(chickenPatty));
        testRecipe.removeIngredient(chickenPatty);
        assertTrue(testRecipe.getDietaryInformation().contains(DietEnum.VEGETARIAN));
        testRecipe.addIngredient(chickenPatty, 12);
        testRecipe.addIngredient(cheese, 1);
        assertFalse(testRecipe.getDietaryInformation().contains(DietEnum.VEGETARIAN));
        assertFalse(testRecipe.getDietaryInformation().contains(DietEnum.VEGAN));
        testRecipe.removeIngredient(chickenPatty);
        testRecipe.removeIngredient(cheese);
        assertTrue(testRecipe.getDietaryInformation().contains(DietEnum.VEGAN));
        assertTrue(testRecipe.getDietaryInformation().contains(DietEnum.GLUTEN_FREE));
    }

    @Test
    public void testEditIngredient() {

        Ingredient cheese = new Ingredient("cheese", "dairy", "12", Money.parse("NZD 20"), ingredientInfo);
        testRecipe.addIngredient(chickenPatty, 10);
        testRecipe.editRecipe(chickenPatty, 5);
        testRecipe.editRecipe(cheese, 2);
        assertEquals(5, (int) testRecipe.getIngredientsAmount().get(chickenPatty));
        assertEquals(2, (int) testRecipe.getIngredientsAmount().get(cheese));
    }

    @Test
    public void testRecipeConstructors() {
        HashMap<Ingredient, Integer> ingredientsAmount = new HashMap<>();
        HashMap<String, Integer> ingredientIDs = new HashMap<>();
        Recipe testRecipe2 = new Recipe("Burger", "Making Burger", ingredientsAmount, ingredientIDs);
        testRecipe2.addIngredient(chickenPatty, 2);
        assertEquals(2, (int) testRecipe2.getIngredientsAmount().get(chickenPatty));

    }

}