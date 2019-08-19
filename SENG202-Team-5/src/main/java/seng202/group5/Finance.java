package seng202.group5;

import java.util.ArrayList;

/**
 * Finance class records order history, refunds past orders and calculates change.
 */
public class Finance {

    /** The database managing the system */
    private Database database;

    public Finance(Database database) {

    }

    /**
     * Refunds a previous order and returns the list of notes to return in descending size order.
     *
     * @param ID the id of the order to refund
     * @return a list of floats representing money in descending size order
     *
     */
    public ArrayList<Double> refund(String ID) {
        return new ArrayList<Double>();
    }

    /**
     * Saves order to database and returns a list of notes to return as change.
     *
     * @param totalCost the total cost of the order
     * @param amountPayed a lost of floats representing money in the denominations payed
     * @param time the time the order occurred at
     * @return a list of floats representing money in descending size order
     */
    public ArrayList<Double> pay(double totalCost, ArrayList<Double> amountPayed, int time) {
        return new ArrayList<Double>();
    }

    /**
     * returns a list containing total profits, average profits, and other information to be displayed on the finance screen over the imputed time period
     *
     * @param startDate the first date to search from
     * @param endDate the last date to search to
     * @return a list of float representing  total profits, average profits, and other things
     */
    public ArrayList<Double> totalCalculator(int startDate, int endDate) {
        return new ArrayList<Double>();
    }
}
