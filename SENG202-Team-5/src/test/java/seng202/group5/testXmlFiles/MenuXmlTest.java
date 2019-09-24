package seng202.group5.testXmlFiles;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.AppEnvironment;
import seng202.group5.information.DietEnum;
import seng202.group5.information.TypeEnum;
import seng202.group5.information.Ingredient;
import seng202.group5.information.Recipe;
import seng202.group5.logic.MenuManager;

import javax.xml.bind.JAXBException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class MenuXmlTest {
    AppEnvironment appEnvironment = new AppEnvironment();
    MenuManager menuManager;
    String testDirectory = System.getProperty("user.dir") + "/src/test/java/seng202/group5/testXmlFiles";


    @BeforeAll
    public static void createAndMarshalMenuData() {
        String testDirectory = System.getProperty("user.dir") + "/src/test/java/seng202/group5/testXmlFiles";
        AppEnvironment oldAppEnvironment = new AppEnvironment();
        Ingredient flour = new Ingredient("Flour", "Flour", Money.parse("NZD 7.00"));
        HashSet<DietEnum> ingredientInfo1 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
        }};
        HashSet<DietEnum> ingredientInfo2 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
        }};
        Ingredient chickenPatty = new Ingredient("Chicken", "Meat", Money.parse("NZD 10"), ingredientInfo1);
        Ingredient cheese = new Ingredient("Cheese", "Dairy", Money.parse("NZD 5"), ingredientInfo2);
        HashSet<DietEnum> ingredientInfo3 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
        }};
        Ingredient vegePatty = new Ingredient("Vegetables", "Vegetable", Money.parse("NZD 10"), ingredientInfo3);
        flour.setId("1'");

        oldAppEnvironment.getStock().getIngredientStock().put(flour.getID(), 0);
        oldAppEnvironment.getStock().getIngredients().put(flour.getID(), flour);

        chickenPatty.setId("2");
        oldAppEnvironment.getStock().getIngredientStock().put(chickenPatty.getID(), 100);
        oldAppEnvironment.getStock().getIngredients().put(chickenPatty.getID(), chickenPatty);

        cheese.setId("3");
        oldAppEnvironment.getStock().getIngredientStock().put(cheese.getID(), 200);
        oldAppEnvironment.getStock().getIngredients().put(cheese.getID(), cheese);

        vegePatty.setId("4");
        oldAppEnvironment.getStock().getIngredientStock().put(vegePatty.getID(), 150);
        oldAppEnvironment.getStock().getIngredients().put(vegePatty.getID(), vegePatty);

        Recipe testRecipe = new Recipe("Chicken burger", "1) Get some Chicken\n2) Get some cheese\n3) Throw the chicken on the grill and let it fry\n");
        Recipe testRecipe2 = new Recipe("Vege burger", "Steps to make pad thai");
        testRecipe.addIngredient(chickenPatty, 1);
        testRecipe.addIngredient(cheese, 1);
        testRecipe2.addIngredient(vegePatty, 1);

        oldAppEnvironment.getMenuManager().createItem("Chicken Burger", testRecipe, Money.parse("NZD 5"), "1220", true);
        oldAppEnvironment.getMenuManager().createItem("Vege Burger", testRecipe2, Money.parse("NZD 7"), "1222", true);
        try {
            oldAppEnvironment.objectToXml(MenuManager.class, oldAppEnvironment.getMenuManager(), "menu.xml", testDirectory);
        } catch (JAXBException e) {
            System.out.println("Failed to marshal object");
        }
    }

    @BeforeEach
    public void testUnmarshallMenu() {
        try {
            appEnvironment.menuXmlToObject(testDirectory);
            menuManager = appEnvironment.getMenuManager();
            assertEquals(2, menuManager.getItemMap().size());
        } catch (Exception e) {

        }
    }

    @Test
    public void testMenuItemNameIsInMenuManager() {
        String name = menuManager.getItemMap().get("1222").getItemName();
        assertEquals("Vege Burger", name);
    }

    @Test
    public void testMenuItemRecipeNameIsInMenuManager() {
        String recipeName = menuManager.getItemMap().get("1222").getRecipe().getName();
        assertEquals("Vege burger", recipeName);
    }

    @Test
    public void testMenuItemRecipeTestIsInMenuManager() {
        String recipeText = menuManager.getItemMap().get("1222").getRecipe().getRecipeText();
        assertEquals("Steps to make pad thai", recipeText);
    }

    @Test
    public void testMenuItemRecipeDietaryInformationIsInMenuManger() {
        HashSet<DietEnum> dietInfo = menuManager.getItemMap().get("1222").getRecipe().getDietaryInformation();
        assertTrue(dietInfo.contains(DietEnum.VEGETARIAN));
        assertTrue(dietInfo.contains(DietEnum.GLUTEN_FREE));
    }

    @Test
    public void testMenuItemMarkUpCostIsInMenuManager() {
        String markUpCost = menuManager.getItemMap().get("1222").getMarkupCost().toString();
        assertEquals("NZD 7.00", markUpCost);
    }

    @Test
    public void testMenuItemIdIsInMenuManager() {
        String id = menuManager.getItemMap().get("1222").getID();
        assertEquals("1222", id);
    }

    @Test
    public void testMenuItemInMenuBooleanIsInMenuManager() {
        Boolean inMenu = menuManager.getItemMap().get("1222").isInMenu();
        assertTrue(inMenu);
    }

    @Test
    public void testMenuItemItemTypeIsInMenuManager() {
        TypeEnum itemType = menuManager.getItemMap().get("1222").getItemType();
        assertEquals(TypeEnum.MAIN, itemType);
    }

    @Test
    public void testMenuItemEditedBooleanIsInMenuManager() {
        Boolean edited = menuManager.getItemMap().get("1222").isEdited();
        assertFalse(edited);
    }
}
