package handlers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


//storing history and datatype stuff. menuitem and stock vs transactions and orders should stay inside at same time, not going to change overtime

//ideal plan; have a superclass which can open the data, then have functions in each handler which works with the XML format specified.


public class MenuItemHandler {






    //*************** DOM STUFF ****************
    public void getDataXML(String filename, boolean validating) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        factory.setValidating(validating);
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println(e);
            System.exit();
        }
        //builder.setErrorHandler(new ); new myerrorhandler
        Document document = builder.parse(new File(filename));
        System.out.println(document);
    }

    public void main(String[] args) {
        getDataXML('')
    }
}
