package seng202.group5.information;

/**
 * An enum for specifying what type of item a menu item is
 *
 * @author Daniel Harris
 */
public enum TypeEnum {
    MAIN,
    SIDE,
    BEVERAGE;

    @Override
    public String toString() {
        switch (this) {
            case MAIN:
                return "Main";
            case SIDE:
                return "Side";
            case BEVERAGE:
                return "Beverage";
            default:
                return "";
        }
    }
}
