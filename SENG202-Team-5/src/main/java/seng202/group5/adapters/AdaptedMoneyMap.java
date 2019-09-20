package seng202.group5.adapters;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class AdaptedMoneyMap {
    @XmlElement()
    public List<AdaptedMoneyEntry> entries = new ArrayList<>();

}