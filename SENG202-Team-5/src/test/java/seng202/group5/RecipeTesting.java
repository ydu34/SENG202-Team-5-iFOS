package seng202.group5;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RecipeTesting
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RecipeTesting(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(RecipeTesting.class);
    }

    public void testAddingredient() {

        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai", (float) 2.0);
        Ingredient chickenpatty = new Ingredient("chicken", 2, "meat", 12, 20, true, false, false);
        testRecipe.addIngredient(chickenpatty, 1);
        assertTrue(testRecipe.getGlutenfree());
        assertFalse(testRecipe.getVegetarian());
        assertFalse(testRecipe.getVegan());
    }

    public void testRemovingredient() {

        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai", (float) 2.0);
        Ingredient chickenPatty = new Ingredient("chicken", 2, "meat", 15, 20, true, false, false);
        testRecipe.removeIngredient(chickenPatty, 1);
        assertTrue(testRecipe.getVegetarian());
        assertTrue(testRecipe.getGlutenfree());
    }

    public void testEditingredient() {

        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai", (float) 2.0);
        Ingredient chickenPatty = new Ingredient("chicken", 2, "meat", 15, 20, true, false, false);
        testRecipe.addIngredient(chickenPatty, 10);
        assertTrue(testRecipe.editRecepie(chickenPatty, 5));
    }



}