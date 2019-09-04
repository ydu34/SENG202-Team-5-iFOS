package seng202.group5;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Yu Duan
 */
public class Database {

    private Worker worker;
    private Finance finance;
    private Stock stock;


    /**Marshals the given object o into a xml file.
     * @param c The class of the object o.
     * @param o The object you want to marshal into xml file.
     * @param fileName  The name of the xml file.
     */
    public void objectToXml(Class c, Object o, String fileName) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(c);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(c.cast(o), System.out); //print to sys out so we can view and check
            jaxbMarshaller.marshal(c.cast(o), new File(System.getProperty("user.dir") +
                                                                 "/src/resources/data/" + fileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**Converts the xml file to an object o.
     * @return an object o.
     */
    public Object xmlToObject(Class c, Object o, String fileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(c);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            o = c.cast(jaxbUnmarshaller.unmarshal( new File(System.getProperty("user.dir") +
                                                                                                 "/src/resources/data/" + fileName)));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return o;
    }

    /** Given the hash map containing ingredient ids and the quantity, search for the corresponding ingredient for each id in the stock and return a
     * hashmap containing the ingredient and quantity.
     * @param IngredientIDs Contains a string as the ingredient id and the value as the quantity.
     * @return A new hash map containing the string ids replaced with ingredient objects, while the value of the hash map is the quantity.
     */
    public HashMap<Ingredient, Integer> getIngredientsFromID(HashMap<String, Integer> IngredientIDs) {
        HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        for (Map.Entry<String, Integer> entry : IngredientIDs.entrySet()) {
            String ID = entry.getKey();
            Integer quantity = entry.getValue();
            Ingredient ingredient = stock.getIngredients().get(ID);
            ingredients.put(ingredient, quantity);
        }
        return ingredients;
    }

    public void handleMenu() {

    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}
