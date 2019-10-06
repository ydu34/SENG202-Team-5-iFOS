package seng202.group5.information;

import seng202.group5.IDGenerator;

/**
 * The Customer class holds information about each Customer who is considered a member and have a unique ID and an
 * amount of loyalty points that can purchase food with.
 * @author Michael Morgoun, James Kwok? Yeah sure why not
 */
public class Customer {

    private IDGenerator generator = new IDGenerator();
    private String id = generator.newID();

    private String name;
    private int purchasePoints = 0;

    public void setName(String newName) {
        name = newName;
    }

    public String getName() { return name; }

    public String getID() { return id; }



}
