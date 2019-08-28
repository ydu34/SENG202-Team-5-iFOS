package seng202.group5;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Database {

    private Worker worker;
    private Finance finance;
    private MenuManager menuManager;


    /**Marshals the given object o into a xml file.
     * @param c The class of the object o.
     * @param o The object you want to marshal into xml file.
     * @param fileName  The name of the xml file.
     */
    public void objectToXml(Class c, Object o, String fileName) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(c);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

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

}
