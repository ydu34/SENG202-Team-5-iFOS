package seng202.group5;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Database {

    private Worker worker;
    private Finance finance;



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

    public static void main(String args[]) {
        Ingredient ing1 = new Ingredient("Milk", "L", "Liquid" , "1",4.0f);
        Ingredient ing2 = new Ingredient("Apple", "kg", "Fruit", "2", 1.0f);

        Stock stock = new Stock();
        stock.getIngredients().put("1", ing1);
        stock.getIngredients().put("2", ing2);
        System.out.print(stock.getIngredients() +"\n");

        Database handler = new Database();
        handler.objectToXml(Stock.class, stock, "stock.xml");


    }

}
