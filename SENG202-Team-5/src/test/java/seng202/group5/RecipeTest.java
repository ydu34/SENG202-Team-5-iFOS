package seng202.group5;


import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Unit test for simple AppEnvironment.
 */
public class RecipeTest {

    HashSet<DietEnum> ingredientInfo = new HashSet<>() {{
        add(DietEnum.GLUTEN_FREE);
    }};
    Ingredient chickenPatty = new Ingredient("chicken", "kg", "meat", "12", 20, ingredientInfo);

    @Test
    public void testAddIngredient() {

        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai");
        HashSet<DietEnum> ingredientInfo = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
            add(DietEnum.VEGAN);
        }};
        Ingredient cheese = new Ingredient("cheese", "kg", "dairy", "12", 20, ingredientInfo);
        testRecipe.addIngredient(chickenPatty, 1);
        testRecipe.addIngredient(cheese, 1);
        assertTrue(testRecipe.getDietaryInformation().contains(DietEnum.GLUTEN_FREE));
        assertFalse(testRecipe.getDietaryInformation().contains(DietEnum.VEGETARIAN));
        assertFalse(testRecipe.getDietaryInformation().contains(DietEnum.VEGAN));
    }
    @Test
    public void testRemoveIngredient() {

        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai");

        testRecipe.removeIngredient(chickenPatty, 1);
        assertTrue(testRecipe.getDietaryInformation().contains(DietEnum.VEGETARIAN));
        assertTrue(testRecipe.getDietaryInformation().contains(DietEnum.GLUTEN_FREE));
    }
    @Test
    public void testEditIngredient() {
        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai");
        testRecipe.addIngredient(chickenPatty, 10);
        assertTrue(testRecipe.editRecipe(chickenPatty, 5));
    }



}