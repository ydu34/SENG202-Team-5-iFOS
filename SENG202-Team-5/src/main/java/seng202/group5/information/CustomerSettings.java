package seng202.group5.information;

/**
 * Holds the settings when initialising a customer. These can be edited through the GUI.
 * @author James Kwok
 */
public class CustomerSettings {

    private int initialPurchasePoints = 1;
    private int ratio = 10;

    public int getInitialPurchasePoints() {
        return initialPurchasePoints;
    }

    public int getRatio() {
        return ratio;
    }

    public void setInitialPurchasePoints(int tempInitialPurchasePoints) {
        initialPurchasePoints = tempInitialPurchasePoints;
    }

    public void setRatio(int tempRatio) {
        ratio = tempRatio;
    }
}
