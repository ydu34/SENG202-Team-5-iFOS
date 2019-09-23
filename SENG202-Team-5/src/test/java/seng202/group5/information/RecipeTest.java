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
    public void testAddIngredient() {


        HashSet<DietEnum> ingredientInfo = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);

        }};
        Ingredient cheese = new Ingredient("cheese", "dairy", "12", Money.parse("NZD 20"), ingredientInfo);
        testRecipe.addIngredient(chickenPatty, 2);
        assertEquals(testRecipe.getIngredientsAmount().get(chickenPatty), 2);
        testRecipe.addIngredient(chickenPatty, 12);
        testRecipe.addIngredient(cheese, 1);
        assertEquals(testRecipe.getIngredientsAmount().get(chickenPatty), 14);
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
        assertNotEquals(testRecipe.getIngredientsAmount().get(chickenPatty), 23);
        assertEquals(testRecipe.getIngredientsAmount().get(chickenPatty), 9);
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
        assertEquals(testRecipe.getIngredientsAmount().get(chickenPatty), 5);
        assertEquals(testRecipe.getIngredientsAmount().get(cheese), 2);
    }

    @Test
    public void testRecipeConstructors() {
        HashMap<Ingredient, Integer> ingredientsAmount = new HashMap<>();
        HashMap<String, Integer> ingredientIDs = new HashMap<>();
        Recipe testRecipe2 = new Recipe("Burger", "Making Burger", ingredientsAmount, ingredientIDs);
        testRecipe2.addIngredient(chickenPatty, 2);
        assertEquals(testRecipe2.getIngredientsAmount().get(chickenPatty), 2);

    }

}