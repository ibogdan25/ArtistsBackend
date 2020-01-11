package utils;

/**
 * Properties from application.properties.
 */
public class ConstantsUtils {

    /**
     * Path to the categories file.
     */
    public final static String PROP_LOAD_CATEGORIES_FROM_FILE = "loadCategoriesFromFile";

    /**
     * True will start hibernate.
     * Default value is true.
     */
    public final static String PROP_START_HIBERNATE = "startHibernate";

    /**
     * True will remove all information from database.
     * Default value is false.
     */
    public final static String PROP_RESET_DATABASE = "resetDatabase";

    public final static String TRUE = "TRUE";
    public final static String FALSE = "FALSE";
    public final static String EMPTY_STRING = "";
}
