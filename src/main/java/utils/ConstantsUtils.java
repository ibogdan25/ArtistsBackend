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

    public final static String PROP_PATH_TO_IMAGES = "imagesPath";

    public final static String PROP_EMAIL_USERNAME = "email.username";
    public final static String PROP_EMAIL_PASSWORD = "email.password";
    public final static String PROP_EMAIL_SMTP_SERVER = "email.smtp.server";
    public final static String PROP_EMAIL_RESET_PASSWORD_HTML = "email.emailResetPasswordHtml";

    public final static String TRUE = "TRUE";
    public final static String FALSE = "FALSE";
    public final static String EMPTY_STRING = "";
}
