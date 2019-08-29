//package seng202.group5;
//
//
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//
///**
// * Unit test for simple App.
// */
//public class RecipeTest
//        extends TestCase {
//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public RecipeTest(String testName) {
//        super(testName);
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite() {
//        return new TestSuite(RecipeTest.class);
//    }
//
//    public void testAddIngredient() {
//
//        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai", (float) 2.0);
//        Ingredient chickenpatty = new Ingredient("chicken", "kg", "meat", "12", 20, true, false, false);
//        testRecipe.addIngredient(chickenpatty, 1);
//        assertTrue(testRecipe.getGlutenfree());
//        assertFalse(testRecipe.getVegetarian());
//        assertFalse(testRecipe.getVegan());
//    }
//
//    public void testRemoveIngredient() {
//
//        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai", (float) 2.0);
//        Ingredient chickenPatty = new Ingredient("chicken", "kg", "meat", "12", 20, true, false, false);
//        testRecipe.removeIngredient(chickenPatty, 1);
//        assertTrue(testRecipe.getVegetarian());
//        assertTrue(testRecipe.getGlutenfree());
//    }
//
//    public void testEditIngredient() {
//
//        Recipe testRecipe = new Recipe("Vege burger", "Steps to make pad thai", (float) 2.0);
//        Ingredient chickenPatty = new Ingredient("chicken", "kg", "meat", "12", 20, true, false, false);
//        testRecipe.addIngredient(chickenPatty, 10);
//        assertTrue(testRecipe.editRecepie(chickenPatty, 5));
//    }
//
//
//
//}