package seng202.group5.information;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds the settings when initialising a customer. These can be edited through the GUI.
 * @author James Kwok
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSettings {

    private int initialPurchasePoints = 1;
    private int ratio = 10;
    private int pointValue = 50;

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

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

}
