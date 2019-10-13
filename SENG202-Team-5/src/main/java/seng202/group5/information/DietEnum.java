package seng202.group5.information;

/**
 * An enum for tracking whether or not recipes meet certain dietary requirements
 *
 * @author Daniel Harris
 */
public enum DietEnum {
    GLUTEN_FREE,
    VEGAN,
    VEGETARIAN;

    public String toString() {
        switch (this) {
            case GLUTEN_FREE:
                return "Gluten free";
            case VEGAN:
                return "Vegan";
            case VEGETARIAN:
                return "Vegetarian";
            default:
                return "";
        }
    }
}
//TODO refactor things so we can add more options without needing to write more code
