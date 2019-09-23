package seng202.group5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * A class which creates IDs for all that requires an ID and makes sure that
 * they're never the same.
 * @author Michael Morgoun
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IDGenerator {

    /**
     * A static variable ID which shows the last ID used or 0 if none used so far.
     */
    @XmlElement
    private static int id = 0;

    public IDGenerator() {

    }

    /**
     * Creates a new ID but incrementing the static variable by 1.
     * @return The new ID as a string.
     */
    public String newID() {
        String newID = Integer.toString(id++);
        return newID;
    }

    /**
     * Sets the last ID used, to the string lastID.
     * @param lastID A string ID.
     */
    public void setLastID(String lastID) {
        id = Integer.parseInt(lastID);
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        IDGenerator.id = id;
    }

}
