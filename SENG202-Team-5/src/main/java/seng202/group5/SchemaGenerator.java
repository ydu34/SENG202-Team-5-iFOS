package seng202.group5;

import seng202.group5.logic.*;

import javax.xml.bind.JAXBContext;
import java.io.FileWriter;

/**
 * A class made to be ran manually only by the developer to generate the XML schema files for the
 * all the XML files, the following is generated by this program:
 *      finance.xsd
 *      history.xsd
 *      menu.xsd
 *      stock.xsd
 *
 * @Author Yu Duan
 */
public class SchemaGenerator {

    private String filePath = System.getProperty("user.dir") + "/src/main/resources/schema";;

    /**
     * @param c Takes in the class that has been annotated with Jaxb XML annotations.
     * @return A string representing the schema.
     */
    public String generateSchema(Class c) {
        String schema = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(c);

            ModifiedSchemaOutputResolver sor = new ModifiedSchemaOutputResolver();
            jaxbContext.generateSchema(sor);
            schema = sor.getSchema();
        } catch (Exception e) {

        }
        return schema;
    }

    /**
     * @param c Takes in the class that has been annotated with Jaxb XML annotations.
     * @param schemaFileName Takes in a string to be used for the generated .xsd file.
     */
    public void writeSchemaToFile(Class c, String schemaFileName) {
        try {
            FileWriter stockSchema = new FileWriter(filePath + "/" + schemaFileName);
            stockSchema.write(generateSchema(c));
            stockSchema.close();
        } catch (Exception e) {
        }

    }

    /**
     * Generates all the following schemas:
     *      finance.xsd
     *      history.xsd
     *      menu.xsd
     *      stock.xsd
     */
    public void generateAllSchemas() {

        writeSchemaToFile(Stock.class, "stock.xsd");
        writeSchemaToFile(History.class, "history.xsd");
        writeSchemaToFile(MenuManager.class, "menu.xsd");
        writeSchemaToFile(Finance.class, "finance.xsd");
    }

    public static void main(String[] v) {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        schemaGenerator.generateAllSchemas();
    }
}
