package seng202.group5.testXmlFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.AppEnvironment;
import seng202.group5.DietEnum;
import seng202.group5.TypeEnum;
import seng202.group5.logic.MenuManager;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class MenuXmlTest {
    AppEnvironment appEnvironment = new AppEnvironment();
    MenuManager menuManager;
    String testDirectory = System.getProperty("user.dir") + "\\src\\test\\java\\seng202\\group5\\testXmlFiles";

    @BeforeEach
    public void testUnmarshallMenu() {
        appEnvironment.menuXmlToObject(testDirectory);
        menuManager = appEnvironment.getMenuManager();
        assertEquals(2, menuManager.getItemList().size());
    }

    @Test
    public void testMenuItemNameIsInMenuManager() {
        String name = menuManager.getItemList().get("1222").getItemName();
        assertEquals("Vege Burger", name);
    }

    @Test
    public void testMenuItemRecipeNameIsInMenuManager() {
        String recipeName = menuManager.getItemList().get("1222").getRecipe().getName();
        assertEquals("Vege burger", recipeName);
    }

    @Test
    public void testMenuItemRecipeTestIsInMenuManager() {
        String recipeText = menuManager.getItemList().get("1222").getRecipe().getRecipeText();
        assertEquals("Steps to make pad thai", recipeText);
    }

    @Test
    public void testMenuItemRecipeDietaryInformationIsInMenuManger() {
        HashSet<DietEnum> dietInfo = menuManager.getItemList().get("1222").getRecipe().getDietaryInformation();
        assertTrue(dietInfo.contains(DietEnum.VEGETARIAN));
        assertTrue(dietInfo.contains(DietEnum.GLUTEN_FREE));
    }

    @Test
    public void testMenuItemMarkUpCostIsInMenuManager() {
        String markUpCost = menuManager.getItemList().get("1222").getMarkupCost().toString();
        assertEquals("NZD 7.00", markUpCost);
    }

    @Test
    public void testMenuItemIdIsInMenuManager() {
        String id = menuManager.getItemList().get("1222").getID();
        assertEquals("1222", id);
    }

    @Test
    public void testMenuItemInMenuBooleanIsInMenuManager() {
        Boolean inMenu = menuManager.getItemList().get("1222").isInMenu();
        assertTrue(inMenu);
    }

    @Test
    public void testMenuItemItemTypeIsInMenuManager() {
        TypeEnum itemType = menuManager.getItemList().get("1222").getItemType();
        assertEquals(TypeEnum.MAIN, itemType);
    }

    @Test
    public void testMenuItemEditedBooleanIsInMenuManager() {
        Boolean edited = menuManager.getItemList().get("1222").isEdited();
        assertFalse(edited);
    }
}
