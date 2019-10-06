package seng202.group5;

import org.joda.money.Money;
import org.xml.sax.SAXException;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.information.Transaction;
import seng202.group5.logic.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Yu Duan
 */
public class AppEnvironment {

    private OrderManager orderManager;
    private Finance finance;
    private Stock stock;
    private MenuManager menuManager;
    private HashSet<String> acceptedFiles;
    private IDGenerator idGenerator;
    private Database database;


    /**
     * The constructor for AppEnvironment
     */
    public AppEnvironment() {
        finance = new Finance();
        stock = new Stock();
        menuManager = new MenuManager();
        orderManager = new OrderManager(stock);
        idGenerator = new IDGenerator();
        database = new Database(this);
        acceptedFiles = new HashSet<>();
        acceptedFiles.add("stock.xml");
        acceptedFiles.add("menu.xml");
        acceptedFiles.add("finance.xml");
    }

    /**
     * Confirms payment for the current order, sends the order to the history,
     * sends information about the transaction to Finance and retrieves the
     * cash amounts to be given as change
     *
     * @param denominations the cash given to the worker to pay for the item
     * @return the cash to be returned to the customer as change
     * @throws InsufficientCashException if the given cash amount is not enough
     *                                   to pay for the order
     */
    public ArrayList<Money> confirmPayment(ArrayList<Money> denominations) throws InsufficientCashException {
        //        Money totalPayment = Money.parse("NZD 0");
        //        for (Money coin : denominations) totalPayment = totalPayment.plus(coin);
        ArrayList<Money> change = new ArrayList<>();
        try {
            Order order = orderManager.getOrder();
            setStock(order.getStock().clone());
            orderManager.setStock(stock);
            orderManager.newOrder();

            change = finance.pay(denominations, LocalDateTime.now(), order);

        } catch (NoOrderException e) {
            e.printStackTrace();
        }

        return change;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
        orderManager.setStock(stock);
    }

    public Finance getFinance() {
        return finance;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(OrderManager tempManager) {
        orderManager = tempManager;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public IDGenerator getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Database getDatabase() {
        return database;
    }
}
