package seng202.group5.adapters;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * A class created so that Jaxb can deal with Money Objects where the keys represent the money string, and value is the quantity of each money
 */
public class AdaptedMoneyEntry {

    @XmlAttribute
    public String key;

    @XmlValue
    public Integer value;

    public AdaptedMoneyEntry() {

    }
}