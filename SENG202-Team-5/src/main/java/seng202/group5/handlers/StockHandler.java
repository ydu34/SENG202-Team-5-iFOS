package seng202.group5.handlers;

import seng202.group5.Stock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class StockHandler {

    public void objectToXml(Stock stock) {

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


    public Stock xmlToObject() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Stock.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Stock stock = (Stock) jaxbUnmarshaller.unmarshal( new File(System.getProperty("user.dir") +
                                                                                                 "/src/resources/data/stock.xml"));
            return stock;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
