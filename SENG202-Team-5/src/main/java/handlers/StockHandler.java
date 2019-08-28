package handlers;

import seng202.group5.MenuManager;
import seng202.group5.Stock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class StockHandler {

    public void menuToXml(Stock stock) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Stock.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.marshal(stock, System.out); //print to sys out so we can view and check
            jaxbMarshaller.marshal(stock, new File(System.getProperty("user.dir") +
                                                                 "/src/resources/data/stock.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**Converts the xml file menu.xml containing the menuitems into a hashmap contained in the class MenuManager.
     * @return a instance of the class MenuManager with the map filled up with the MenuItems in the menu.xml.
     */
    public MenuManager xmlToMenu() {
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
