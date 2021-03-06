package seng202.group5;

import org.joda.money.Money;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.information.Customers;
import seng202.group5.logic.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Yu Duan, Shivin Gaba
 */
public class AppEnvironment {

    private OrderManager orderManager;
    private Finance finance;
    private Stock stock;
    private MenuManager menuManager;
    private Customers customers;
    private Settings settings;
    private IDGenerator idGenerator;
    private String imagesFolderPath;
    private Database database;

    /**
     * The constructor for AppEnvironment
     */
    public AppEnvironment() {
        finance = new Finance();
        stock = new Stock();
        menuManager = new MenuManager();
        orderManager = new OrderManager(stock);
        customers = new Customers();
        settings = new Settings();
        idGenerator = new IDGenerator();

        imagesFolderPath = "";
        // Do not set any management classes after this
        database = new Database(this);
    }

    @Deprecated(since = "For testing use only")
    public AppEnvironment(boolean autoload) {
        finance = new Finance();
        stock = new Stock();
        menuManager = new MenuManager();
        orderManager = new OrderManager(stock);
        customers = new Customers();
        settings = new Settings();
        idGenerator = new IDGenerator();
        database = new Database();
        database.setAppEnvironment(this);
        if (autoload) {
            database = new Database(this);
        } else {
            database.setAutoloadEnabled(false);
        }
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
        ArrayList<Money> change;
        Order order = orderManager.getOrder();

        if (order.getCurrentCustomer() != null) {
            order.getCurrentCustomer().purchasePoints(order.getTotalCost().minus(order.getDiscount()), customers.getCustomerSettings().getRatio());
        }
        setStock(order.getStock().clone());
        orderManager.setStock(stock);
        orderManager.newOrder();

        setStock(order.getStock().clone());
        orderManager.setStock(stock);
        orderManager.newOrder();

        change = finance.pay(denominations, LocalDateTime.now(), order);

        return change;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
        orderManager.setStock(stock);
        orderManager.newOrder();
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers newCustomers) {
        customers = newCustomers;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(OrderManager tempManager) {
        orderManager = tempManager;
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

    public String getImagesFolderPath() {
        return imagesFolderPath;
    }

    public void setImagesFolderPath(String imagesFolderPath) {
        this.imagesFolderPath = imagesFolderPath;
    }
}
