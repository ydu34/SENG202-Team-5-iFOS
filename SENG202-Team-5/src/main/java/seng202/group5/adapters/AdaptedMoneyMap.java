package seng202.group5.adapters;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A class created so that Jaxb can deal with Money Objects
 */
public class AdaptedMoneyMap {
    @XmlElement()
    public List<AdaptedMoneyEntry> entries = new ArrayList<>();

}