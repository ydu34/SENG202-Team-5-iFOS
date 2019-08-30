package seng202.group5;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Unit test for simple App.
 */
public class RecipeTest {

    @Test
    public void testAddIngredient() {

        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai");
        Ingredient chickenpatty = new Ingredient("chicken", "kg", "meat", "12", 20, true, false, false);
        testRecipe.addIngredient(chickenpatty, 1);
        assertTrue(testRecipe.getGlutenfree());
        assertFalse(testRecipe.getVegetarian());
        assertFalse(testRecipe.getVegan());
    }

    public void testRemoveIngredient() {

        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai");
        Ingredient chickenPatty = new Ingredient("chicken", "kg", "meat", "12", 20, true, false, false);
        testRecipe.removeIngredient(chickenPatty, 1);
        assertTrue(testRecipe.getVegetarian());
        assertTrue(testRecipe.getGlutenfree());
    }

    public void testEditIngredient() {

        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai");
        Ingredient chickenPatty = new Ingredient("chicken", "kg", "meat", "12", 20, true, false, false);
        testRecipe.addIngredient(chickenPatty, 10);
        assertTrue(testRecipe.editRecipe(chickenPatty, 5));
    }



}