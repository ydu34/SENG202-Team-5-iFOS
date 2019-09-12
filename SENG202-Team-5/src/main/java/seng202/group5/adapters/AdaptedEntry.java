package seng202.group5.adapters;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class AdaptedEntry {

    @XmlValue
    public String key;

    @XmlValue
    public Integer value;

}