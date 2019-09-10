package seng202.group5;

/**
 * A class which creates IDs for all that requires an ID and makes sure that
 * they're never the same.
 */
public class IDGenerator {

    /**
     * A static variable ID which shows the last ID used or 0 if none used so far.
     */
    private static int id = 0;

    /**
     * Creates a new ID but incrementing the static variable by 1.
     * @return The new ID as a string.
     */
    public String createNew() {
        return Integer.toString(id++);
    }

}
