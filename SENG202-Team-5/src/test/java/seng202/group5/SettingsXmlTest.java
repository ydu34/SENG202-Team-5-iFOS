package seng202.group5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group5.logic.Settings;

import javax.xml.bind.JAXBException;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsXmlTest {

    private static String testDirectory = System.getProperty("user.dir") + "/src/test/java/seng202/group5";
    private static AppEnvironment appEnvironment = new AppEnvironment(false);
    private Database database = appEnvironment.getDatabase();
    private Settings settings;

    private static int maxIngredientCount;

    @BeforeAll
    static void createAndMarshallSettings() {
        AppEnvironment oldAppEnvironment = new AppEnvironment(false);

        maxIngredientCount = 15;

        oldAppEnvironment.getSettings().setMaxIngredientAmount(maxIngredientCount);

        appEnvironment.setSettings(oldAppEnvironment.getSettings());

        try {
            oldAppEnvironment.getDatabase().objectToXml(Settings.class, oldAppEnvironment.getSettings(), "settings.xml", testDirectory);
        } catch (JAXBException e) {
            System.out.println("Failed to marshall object");
        }
    }

    @AfterAll
    static void tearDown() {
        File file = new File(testDirectory + "/settings.xml");
        file.delete();
    }

    @Test
    void testUnmarshallSettings() {
        try {
            database.settingsXmlToObject(testDirectory);
            settings = appEnvironment.getSettings();
            assertEquals(maxIngredientCount, settings.getMaxIngredientAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
