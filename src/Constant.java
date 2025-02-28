import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class which stores system settings and regularly used constants.
 *
 * @version ver1.0.0
 */
public class Constant {

    /**
     * Default constructor for Constant class.
     */
    private Constant() {

    }

    /**
     * Product number that can be shown in one page.
     */
    public final static int PRODUCT_PAGE_SIZE = 4;
    /**
     * Item number that can be shown in one page.
     */
    public final static int CART_PAGE_SIZE = 5;
    /**
     * Shopping cart item limit.
     */
    public final static int CART_ITEM_LIMIT = 20;
    /**
     * Item quantity limit.
     */
    public final static int ITEM_QUANTITY_LIMIT = 10;
    /**
     * Product price limit.
     */
    public final static double PRODUCT_PRICE_LIMIT = 1000000.0;
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_EMAIL = "email";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PASSWORD = "password";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_ID = "product ID";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_OPTION = "option";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_QUANTITY = "quantity";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_ACCOUNT = "account information";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_CATEGORY = "category";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_SUBCATEGORY = "subcategory";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_NAME = "product name";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_BRAND = "brand";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_DESC = "description";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_PRICE = "price";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_MEMBER_PRICE = "member price";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_QUANTITY = "stock quantity";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_EXPIRY = "expiry date";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_INGREDIENT = "ingredients";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_STORAGE = "storage instructions";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_PRODUCT_ALLERGEN = "allergen info";
    /**
     * Regularly printed field name.
     */
    public final static String FIELD_CONFIRM = "input for confirmation";
    /**
     * Regularly checked string.
     */
    public final static String STRING_BACK = "\\b";
    /**
     * Regularly checked string.
     */
    public final static String STRING_LOGOUT = "\\l";
    /**
     * Regularly checked string.
     */
    public final static String STRING_CONFIRM_YES = "y";
    /**
     * Regularly checked string.
     */
    public final static String STRING_CONFIRM_NO = "n";
    /**
     * Regularly checked string.
     */
    public final static String STRING_NULL_DATE = "NULL";
    /**
     * Regularly checked string.
     */
    public final static String STRING_FIELD_DELIMITER = "/::/";
    /**
     * Regularly checked string.
     */
    public final static String STRING_NEXT_LINE = "\n";
    /**
     * Return code for checking whether user input back shortcut.
     */
    public final static int CODE_BACK = 0;
    /**
     * Return code for checking whether user input logout shortcut.
     */
    public final static int CODE_LOGOUT = 1;
    /**
     * Return code for operation success.
     */
    public final static int OPERATION_SUCCESS = 2;
    /**
     * Return code for operation fail.
     */
    public final static int OPERATION_FAIL = 3;
    /**
     * Return code for operation cancel.
     */
    public final static int OPERATION_CANCEL = 4;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_ADD_TO_CART = 10;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_LOGIN = 11;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_CREATE_PRODUCT = 12;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_UPDATE_PRODUCT = 13;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_REMOVE_PRODUCT = 14;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_CHECK_PRODUCT = 15;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_CHANGE_NONFOOD_TO_FOOD = 16;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_CHANGE_FOOD_TO_NONFOOD = 17;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_LOGOUT = 18;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_CHECKOUT = 19;
    /**
     * Code for indicating operation type.
     */
    public final static int OP_TYPE_EXIT = 20;
    /**
     * Code for indicating user type.
     */
    public final static boolean USER_TYPE_ADMIN = true;
    /**
     * Code for indicating user type.
     */
    public final static boolean USER_TYPE_CUSTOMER = false;
    /**
     * Special food-type category name.
     */
    public final static String CATEGORY_FOOD = "food";
    /**
     * Special food-type category name.
     */
    public final static String CATEGORY_BEVERAGE = "beverages";
    /**
     * Default category list.
     */
    public final static HashMap<String, List<String>> DEFAULT_CATEGORIES = new HashMap<>()
    {{
        put("Electronics", Arrays.asList("Phones", "Earbuds"));
        put("Books", Arrays.asList("Horror", "Romantic"));
        put("Beauty", Arrays.asList("Skincare", "Makeup"));
        put("Personal Care", Arrays.asList("Hygiene", "Grooming"));
        put("Food", Arrays.asList("Fruits", "Bread"));
        put("Beverages", Arrays.asList("Water", "Juices"));
    }};
    /**
     * Default customer.
     */
    public final static Customer DEFAULT_CUSTOMER = new Customer("member@student.monash.edu",
            "Monash1234", "louis", "li", LocalDate.of(1998, 3, 18),
            "111222333", "earth", "male", null);
    /**
     * Default admin.
     */
    public final static Admin DEFAULT_ADMIN = new Admin("admin@merchant.monash.edu", "12345678");
    /**
     * Screen print line character limit.
     */
    public final static int LINE_LENGTH_LIMIT = 73;
    /**
     * User data file name.
     */
    public final static String USER_FILE_PATH = "data/User.txt";
    /**
     * Product data file name.
     */
    public final static String INVENTORY_FILE_PATH = "data/Product.txt";
    /**
     * Category data file name.
     */
    public final static String CATEGORY_FILE_PATH = "data/Category.txt";
    /**
     * Error message for indicating invalid line content.
     */
    public final static String ERR_MSG_INVALID_LINE = "Invalid line content format";
}
