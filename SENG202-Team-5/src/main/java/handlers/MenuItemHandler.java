package handlers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class MenuItemHandler {






    //*************** DOM STUFF ****************
    public void getDataXML(String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace ( );
        }
        Document document = builder.parse(new File(filename));
        System.out.println(document);
    }

    public void main(String[] args) {
        getDataXML('')
    }
}
