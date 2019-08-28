package handlers;

import seng202.group5.MenuManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;


//storing history and datatype stuff. menuitem and stock vs transactions and orders should stay inside at same time, not going to change overtime

//ideal plan; have a superclass which can open the data, then have functions in each handler which works with the XML format specified.

/**
 *
 */
public class MenuItemHandler {

    /**Converts the hashmap menu contained in MenuManager to an xml file named menu.xml.
     * @param menuManager a instance of the class MenuManager to have it's menu map converted to menu.xml.
     */
    public void objectToXml(MenuManager menuManager) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MenuManager.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.marshal(menuManager, System.out); //print to sys out so we can view and check
            jaxbMarshaller.marshal(menuManager, new File(System.getProperty("user.dir") +
                                                                 "/src/resources/data/menu.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**Converts the xml file menu.xml containing the menuitems into a hashmap contained in the class MenuManager.
     * @return a instance of the class MenuManager with the map filled up with the MenuItems in the menu.xml.
     */
    public MenuManager xmlToObject() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MenuManager.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            MenuManager menuManager = (MenuManager) jaxbUnmarshaller.unmarshal( new File(System.getProperty("user.dir") +
                                                                                                 "/src/resources/data/menu.xml"));
            return menuManager;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
