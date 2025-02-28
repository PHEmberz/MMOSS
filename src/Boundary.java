import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class which prints out messages on standard output.
 *
 * @version ver1.0.0
 */
public class Boundary {

    /**
     * Default constructor for Boundary class.
     */
    public Boundary(){
    }

    /**
     * Print system usage.
     */
    // menus
    public static void printSystemUsage() {
        System.out.println();
        System.out.println("<------------------SYSTEM USAGE--------------------->");
        System.out.println("<  Please type the number before each option for c- >");
        System.out.println("<  onducting that operation.                        >");
        System.out.println("<                                                   >");
        System.out.println("<  Please feel free to type \\b in any input field   >");
        System.out.println("<  to GO BACK to the previous page, or type \\l to   >");
        System.out.println("<  directly LOG OUT.                                >");
        System.out.println("<                                                   >");
        System.out.println("<  However, using shortcut to log out will NOT ask  >");
        System.out.println("<  for confirmation, please be careful to use it.   >");
        System.out.println("<                                                   >");
        System.out.println("<  [Default customer] member@student.monash.edu     >");
        System.out.println("<                     Monash1234                    >");
        System.out.println("<                                                   >");
        System.out.println("<  [  Default admin ] admin@merchant.monash.edu     >");
        System.out.println("<                     12345678                      >");
        System.out.println("<                                                   >");
        System.out.println("<  You can add other accounts in User.txt if you l- >");
        System.out.println("<  ike before executing the process.                >");
        System.out.println("<                                                   >");
        System.out.println("<  Have fun.                                        >");
        System.out.println("<--------------------------------------------------->");
        System.out.println();
        System.out.println();
    }

    /**
     * Print guest's main menu.
     */
    // menus
    public static void printGuestMainMenu() {

        System.out.println();
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));
        System.out.println(" ".repeat(20) + "Welcome to Monash Merchant");
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));

        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("Please enter an option:");
    }

    /**
     * Print customer's main menu.
     *
     * @param username              Customer's full name.
     */
    public static void printCustomerMainMenu(String username) {

        System.out.println();
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));
        System.out.println(" ".repeat(30) + "Welcome, " + username);
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));

        System.out.println("1. View inventory");
        System.out.println("2. add product to shopping cart");
        System.out.println("3. view shopping cart");
        System.out.println("4. logout");
        System.out.println("Please enter an option:");

    }

    /**
     * Print admin's main menu.
     */
    public static void printAdminMainMenu() {

        System.out.println();
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));
        System.out.println(" ".repeat(30) + "Welcome, Admin");
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));

        System.out.println("1. View Inventory");
        System.out.println("2. Add New Product");
        System.out.println("3. Edit Existing Product");
        System.out.println("4. Remove Product");
        System.out.println("5. Logout");
        System.out.println("Please enter an option:");
    }

    /**
     * Print edit product menu.
     * @param product           Product object.
     */
    public static void printEditProductMenu(Product product){
        System.out.println();
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));
        System.out.println(" ".repeat(30) + "Edit product");
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));

        printOverViewOfProduct(product, Constant.OP_TYPE_CHECK_PRODUCT);

        System.out.println("1.  Edit category");
        System.out.println("2.  Edit subcategory");
        System.out.println("3.  Edit name");
        System.out.println("4.  Edit brand");
        System.out.println("5.  Edit description");
        System.out.println("6.  Edit price");
        System.out.println("7.  Edit member price");
        System.out.println("8.  Edit quantity");


        if (product.getCategory().equalsIgnoreCase("Food") ||
                product.getCategory().equalsIgnoreCase("Beverages")) {
            System.out.println("9.  Edit expiry date");
            System.out.println("10. Edit ingredients");
            System.out.println("11. Edit storage instructions");
            System.out.println("12. Edit allergen info");
        }
        System.out.println("0. Back to main page");
        System.out.println("Please enter an option:");

    }

    /**
     * Print shopping cart menu.
     *
     * @param currentPage               Current page number.
     * @param totalPages                Total page number.
     */
    public static void printShoppingCartMenu(int currentPage, int totalPages) {
        System.out.printf(" ".repeat(28) + "Page %03d/%03d%n", currentPage, totalPages);
        System.out.println(" ".repeat(12) + "Input '>' for next page, '<' for previous page");

        System.out.println();
        System.out.println("1. Checkout");
        System.out.println("0. Back to main page");
        System.out.println("Please enter an option:");
    }

    /**
     * Print inventory menu.
     *
     * @param currentPage               Current page number.
     * @param totalPages                Total page number.
     */
    public static void printInventoryMenu(int currentPage, int totalPages) {
        System.out.printf(" ".repeat(28) + "Page %03d/%03d%n", currentPage, totalPages);
        System.out.println(" ".repeat(12) + "Input '>' for next page, '<' for previous page");
        System.out.println();
        System.out.println("0. Back to main page");
        System.out.println("Please enter an option:");
    }

    /**
     * Print checkout menu.
     *
     * @param currentPage               Current page number.
     * @param totalPages                Total page number.
     */
    public static void printCheckOutMenu(int currentPage, int totalPages) {
        System.out.printf(" ".repeat(28) + "Page %03d/%03d%n", currentPage, totalPages);
        System.out.println(" ".repeat(12) + "Input '>' for next page, '<' for previous page");

        System.out.println();
        System.out.println("1. Pay with credits");
        System.out.println("0. Back to shopping cart page");
        System.out.println("Please enter an option:");
    }


    /**
     * Print available categories.
     *
     * @param categoryList                  Available categories.
     */
    public static void printAvailableCategories(HashMap<String, List<String>> categoryList){
        StringBuilder output = new StringBuilder("Available categories: ");
        for (String category : categoryList.keySet()){
            output.append(category).append("/");
        }
        System.out.println(output);
    }

    /**
     * Print available subcategories under target category.
     *
     * @param categoryList                  Available categories.
     * @param requiredCategory              The target category.
     */
    public static void printAvailableSubcategories(HashMap<String, List<String>> categoryList, String requiredCategory){
        StringBuilder output = new StringBuilder("Available subcategories: ");
        categoryList.forEach((category, subcategoryList) -> {
            if (requiredCategory.equalsIgnoreCase(category)) {
                for (String subcategory: subcategoryList){
                    output.append(subcategory).append("/");
                }
            }
        });
        System.out.println(output);
    }

    /**
     * Alert when already at the last page.
     */
    public static void alertLastPage() {
        System.out.println("[!]Already reached last page.");
    }

    /**
     * Alert when already at the first page.
     */
    public static void alertFirstPage() {
        System.out.println("[!]Already reached first page.");
    }

    /**
     * Alert when already at the main menu.
     */
    public static void alertMainMenu() {
        System.out.println("[!]Already reached main menu.");
    }

    /**
     * Print one field of product information with a specified format.
     *
     * @param fieldName                     The field's name.
     * @param value                         The field's value.
     */
    private static void printProductInfoLine(String fieldName, String value){
        int fieldNameLength = fieldName.length();
        int valueLineLength = Constant.LINE_LENGTH_LIMIT - fieldNameLength - 7;
        int valueLength = value.length();
        int lines = (int)Math.ceil((double)valueLength / valueLineLength);
        StringBuilder result = new StringBuilder();
        for (int currentLine = 0; currentLine < lines; currentLine++) {
            if (currentLine == 0){
                result.append(String.format("|  [%s] ", fieldName));
            }
            else {
                result.append("|").append(" ".repeat(fieldNameLength + 5));
            }
            // get the start & end index in the value string
            int lineStartIndex = currentLine * valueLineLength;
            int lineEndIndex = Math.min((currentLine + 1) * valueLineLength, valueLength);

            // number of aligning spaces
            int spaceLength = valueLineLength - (lineEndIndex - lineStartIndex);
            result.append(value, lineStartIndex, lineEndIndex).append(" ".repeat(spaceLength)).append("|\n");

        }
        System.out.print(result);
    }


    /**
     * Present the overview of a product's information.
     *
     * @param product                       The presented product.
     * @param option                        The operation that needs to call this method.
     */
    public static void printOverViewOfProduct(Product product, int option){
        switch (option) {
            case Constant.OP_TYPE_CREATE_PRODUCT -> {
                System.out.println("The new product information is:");
            }
            case Constant.OP_TYPE_CHECK_PRODUCT -> {
                System.out.println("The current product information is:");
            }
            case Constant.OP_TYPE_UPDATE_PRODUCT -> {
                System.out.println("The updated product information is:");
            }
            case Constant.OP_TYPE_REMOVE_PRODUCT -> {
                System.out.println("The to-be-removed product information is:");
            }
        }
        printOneProduct(product);
    }

    /**
     * Present the information of a product.
     *
     * @param product                       The presented product.
     */
    public static void printOneProduct(Product product){

        System.out.println("-".repeat(Constant.LINE_LENGTH_LIMIT));

        printProductInfoLine(String.format("%03d", product.getProductId()), product.getName());
        System.out.println("-".repeat(Constant.LINE_LENGTH_LIMIT));

        printProductInfoLine("PRICE", String.format("$%.2f / VIP $%.2f",
                product.getPrice(), product.getMemberPrice()));
        printProductInfoLine("BRAND", product.getBrand());
        printProductInfoLine("QUANTITY", String.valueOf(product.getQuantity()));
        printProductInfoLine("CATEGORY", product.getCategory() + " > " + product.getSubcategory());
        printProductInfoLine("DESCRIPTION", product.getDescription());


        if (product.getCategory().equalsIgnoreCase(Constant.CATEGORY_FOOD) ||
                product.getCategory().equalsIgnoreCase(Constant.CATEGORY_BEVERAGE)) {
            printProductInfoLine("EXPIRY DATE", Util.getStringFromDate(((FoodProduct)product)
                    .getExpirationDate()));
            printProductInfoLine("INGREDIENT", ((FoodProduct)product).getIngredients());
            printProductInfoLine("STORAGE", ((FoodProduct)product).getStorageInstructions());
            printProductInfoLine("ALLERGEN", ((FoodProduct)product).getAllergenInfo());
        }

        System.out.println("-".repeat(Constant.LINE_LENGTH_LIMIT));
    }

    /**
     * print the inventory.
     *
     * @param productList                       The product list.
     * @param currentPage                       Page number of the list that is currently shown.
     */
    public static void printInventory(ArrayList<Product> productList, int currentPage) {

        System.out.println();
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));
        System.out.println(" ".repeat(30) + "View Inventory");
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));

        int startIdx = (currentPage - 1) * Constant.PRODUCT_PAGE_SIZE;
        int endIdx = Math.min(currentPage * Constant.PRODUCT_PAGE_SIZE, productList.size());

        for (int idx = startIdx; idx < endIdx; idx++) {
            Product product = productList.get(idx);
            printOneProduct(product);
        }
    }

    /**
     * Print operation success message.
     *
     * @param operation                 Operation type.
     */
    public static void printOperationSuccess(int operation){
        switch (operation) {
            case Constant.OP_TYPE_ADD_TO_CART -> {
                System.out.println("[√]Product has been added to cart.");
            }
            case Constant.OP_TYPE_LOGIN -> {
                System.out.println("[√]Login successfully.");
            }
            case Constant.OP_TYPE_LOGOUT -> {
                System.out.println("[√]Logout successfully.");
            }
            case Constant.OP_TYPE_CREATE_PRODUCT -> {
                System.out.println("[√]New product has been successfully created.");
            }
            case Constant.OP_TYPE_UPDATE_PRODUCT -> {
                System.out.println("[√]Product information has been successfully updated.");
            }
            case Constant.OP_TYPE_REMOVE_PRODUCT -> {
                System.out.println("[√]Product has been successfully removed.");
            }
            case Constant.OP_TYPE_CHECKOUT -> {
                System.out.println("[√]Order has been successfully created.");
            }
            default -> {
                System.out.println("[√]Operation succeeds.");
            }
        }
    }

    /**
     * Print operation cancel message.
     *
     * @param operation                 Operation type.
     */
    public static void printOperationCancel(int operation){
        switch (operation) {
            case Constant.OP_TYPE_CREATE_PRODUCT -> {
                System.out.println("[-]Product creation canceled.");
            }
            case Constant.OP_TYPE_UPDATE_PRODUCT -> {
                System.out.println("[-]Product field edition canceled.");
            }
            case Constant.OP_TYPE_REMOVE_PRODUCT -> {
                System.out.println("[-]Product deletion canceled.");
            }
            case Constant.OP_TYPE_CHECKOUT -> {
                System.out.println("[-]Shopping cart checkout canceled.");
            }
        }
    }

    /**
     * Prompt user input for a specific field.
     *
     * @param fieldName                     The name of the field.
     */
    public static void promptFieldInput(String fieldName) {
        System.out.println("Please enter the " + fieldName + ":");
    }

    /**
     * Prompt user input for expiry date
     */
    public static void promptExpiryDateInput(){
        System.out.println("Please enter the expiry date in \"YYYY M D\" format (eg. 2024 5 20):");
    }

    /**
     * Print confirmation request message.
     *
     * @param operation                 Operation type.
     */
    public static void promptConfirm(int operation) {
        switch (operation) {
            case Constant.OP_TYPE_CREATE_PRODUCT -> {
                System.out.println("[?]Are you sure you want to create this product?\n   Please input y or n:");
            }
            case Constant.OP_TYPE_UPDATE_PRODUCT -> {
                System.out.println("[?]Are you sure you want to make this update?\n   Please input y or n:");
            }
            case Constant.OP_TYPE_REMOVE_PRODUCT -> {
                System.out.println("[?]Are you sure you want to remove this product?\n   Please input y or n:");
            }
            case Constant.OP_TYPE_CHECKOUT -> {
                System.out.println("[?]Are you sure you want to checkout?\n   Please input y or n:");
            }
            case Constant.OP_TYPE_EXIT -> {
                System.out.println("[?]Are you sure you want to exit?\n   Please input y or n:");
            }
            case Constant.OP_TYPE_LOGOUT -> {
                System.out.println("[?]Are you sure you want to logout?\n   Please input y or n:");
            }
        }
    }

    /**
     * Print message when user inputs a value violating the field's required format.
     *
     * @param fieldName                     The name of the field.
     */
    public static void alertUnformattedInput(String fieldName) {
        System.out.println("[×]Invalid " + fieldName + ". Please input again.");
    }

    /**
     * Print message when user inputs a value out of the field's required range.
     *
     * @param fieldName                     The name of the field.
     */
    public static void alertInvalidValue(String fieldName) {
        switch (fieldName) {
            case Constant.FIELD_QUANTITY -> {
                System.out.println("[×]Invalid quantity, must be between 0 and 10.");
            }
            case Constant.FIELD_ACCOUNT -> {
                System.out.println("[×]Invalid account information, please check your email or password.");
            }
            case Constant.FIELD_PRODUCT_NAME -> {
                System.out.println("[×]Invalid product name, name cannot be empty.");
            }
            case Constant.FIELD_PRODUCT_CATEGORY -> {
                System.out.println("[×]Invalid category, category cannot be empty.");
            }
            case Constant.FIELD_PRODUCT_SUBCATEGORY -> {
                System.out.println("[×]Invalid subcategory, subcategory cannot be empty.");
            }
            case Constant.FIELD_PRODUCT_PRICE -> {
                System.out.println("[×]Invalid price, must be larger than 0 and smaller than " +
                        String.format("%.0f", Constant.PRODUCT_PRICE_LIMIT) + ".");
            }
            case Constant.FIELD_PRODUCT_MEMBER_PRICE -> {
                System.out.println("[×]Invalid member price, must be between 0 and the normal price.");
            }
            case Constant.FIELD_PRODUCT_QUANTITY -> {
                System.out.println("[×]Invalid stock quantity, cannot be less than 0.");
            }
            default -> {
                System.out.println("[×]Invalid " + fieldName + ".");
            }
        }
    }

    /**
     * Alert user when adding a product to part but the quantity is over than stock.
     *
     * @param currentStock                  The product's current stock.
     */
    public static void alertOutOfStock(int currentStock) {
        System.out.println("[×]Quantity exceeds inventory. Current stock for this product " +
                "is only " + currentStock + ".");
    }

    /**
     * Alert user when adding a product which is out of stock.
     */
    public static void alertOutOfStock() {
        System.out.println("[×]This product is currently sold out.");
    }

    /**
     * Alert user when adding a product to cart but already having limit number of it in cart.
     */
    public static void alertAlreadyReachedLimit() {
        System.out.println("[×]You already have "  + Constant.ITEM_QUANTITY_LIMIT + " of the this product\n" +
                "   in cart. Already reached item quantity limit.");
    }

    /**
     * Alert user when adding a product to cart but already having all of its stock in cart.
     *
     * @param stock                         The product's current stock.
     */
    public static void alertAlreadyReachedStock(int stock) {
        System.out.println("[×]You already have "  + stock + " of the this product\n" +
                "   in cart. Current stock is only " + stock + ".");
    }

    /**
     * Alert user when adding a product to cart but after that will exceed cart item quantity limit.
     *
     * @param inCartQuantity                The product's current stock.
     */
    public static void alertSumOutOfLimit(int inCartQuantity) {
        System.out.println("[×]Quantity exceeds limit. You already have " + inCartQuantity +
                " of this product in cart.\n" + "   Item quantity limit is " + Constant.ITEM_QUANTITY_LIMIT + ".");
    }

    /**
     * Alert user when adding a product to cart but after that will exceed product quantity in stock.
     *
     * @param currentStock                  The product's current stock.
     * @param inCartQuantity                Currently how many of this product have been added to cart.
     */
    public static void alertSumOutOfStock(int currentStock, int inCartQuantity) {
        System.out.println("[×]Quantity exceeds inventory. You already have " + inCartQuantity +
                " of this product in cart.\n" + "   Current stock is only " + currentStock + ".");
    }

    /**
     * Alert user when wanting to access an invalid product ID.
     *
     * @param productID                     The user input productID.
     */
    public static void alertProductNotExist(int productID) {
        System.out.println("[×]Product ID " + productID + " does not exist.");
    }

    /**
     * Alert user when wanting to use an invalid category.
     *
     * @param category                     The user input category.
     */
    public static void alertCategoryNotExist(String category) {
        System.out.println("[×]Category " + category + " does not exist. Please input a valid one.");

    }

    /**
     * Alert user when wanting to use an invalid subcategory.
     *
     * @param subcategory                     The user input subcategory.
     */
    public static void alertSubcategoryNotExist(String subcategory) {
        System.out.println("[×]Subcategory " + subcategory + " does not exist. Please input a valid one.");

    }

    /**
     * Alert user when shopping cart is already full.
     */
    public static void alertFullCart() {
        System.out.println("[-]Shopping cart is full. You can only add 20 items.");
    }

    /**
     * Alert user when shopping cart is empty.
     */
    public static void alertEmptyCart() {
        System.out.println("[-]Shopping cart is empty. ");
    }
    /**
     * Alert user when inventory is empty.
     */
    public static void alertEmptyInventory() {
        System.out.println("[-]Inventory is empty. ");
    }

    /**
     * Alert user when checking out but the credits is not enough.
     */
    public static void alertNotEnoughCredit() {
        System.out.println("[×]Checkout failed, remaining credits is not enough.");
    }

    /**
     * Alert user when they are changing a non-food product's category into a food one or vice versa.
     *
     * @param operation                 Operation type.
     */
    public static void alertProductTypeChange(int operation) {
        switch (operation) {
            case Constant.OP_TYPE_CHANGE_NONFOOD_TO_FOOD -> {
                System.out.println("[!]You are changing a non-food product into food product.\n" +
                        "   Please enter additional attributes.");
            }
            case Constant.OP_TYPE_CHANGE_FOOD_TO_NONFOOD -> {
                System.out.println("[!]Changing a food product into non-food product\n" +
                        "   will remove part of its attributes.");
            }
        }
    }

    /**
     * System alert when parsing an invalid file line.
     *
     * @param line                             The line content.
     * @param fileName                         The parsing filename.
     */
    public static void alertInvalidFileLine(String line, String fileName) {
        System.out.println("[×]" + fileName + " line content is invalid: " + line);
    }

    /**
     * System alert when parsing a totally invalid file.
     *
     * @param fileName                         The data file's name.
     */
    public static void alertTotalInvalidFileContent(String fileName) {
        System.out.println("[!]" + fileName + " doesn't contain any valid content.\n" +
                        "   Default settings will be applied instead.");
    }

    /**
     * System alert when parsing an empty file or doesn't exist.
     *
     * @param fileName                         The data file's name.
     */
    public static void alertEmptyOrNotExistFile(String fileName) {
        System.out.println("[!]" + fileName + " is empty or doesn't exist. \n" +
                "   Default settings will be applied instead.");
    }

    /**
     * Alert user when file read encounters error.
     */
    public static void alertFileReadError(){
        System.out.println("[×]Something went wrong in file reading.");
    }

    /**
     * Alert user when file write encounters error.
     */
    public static void alertFileWriteError(){
        System.out.println("[×]Something went wrong in file writing.");
    }

    /**
     * Alert user when file not found.
     * @param fileName                  File name.
     */
    public static void alertFileNotFound(String fileName){
        System.out.println("[×]Filename " + fileName + " is not found.");
    }

    /**
     * System alert when parsing a partially invalid file.
     *
     * @param fileType                         The data file's type.
     * @param invalidLines                     How many lines are invalid.
     */
    public static void alertPartialInvalidFileContent(String fileType, int invalidLines) {
        System.out.println("[-]" + invalidLines + " lines of " + fileType + " is in invalid format.\n" +
                "   These lines will be ignored.");
    }

    /**
     * System alert when encountering an exception.
     *
     * @param errMsg                            The error message.
     */
    public static void alertException(String errMsg){
        System.out.println("[×]Something went wrong. \n   Error: " + errMsg + "Restart again...");
        System.out.println();
    }


    /**
     * Print out one shopping cart item's information.
     *
     * @param item                          The shopping cart item to be printed.
     */
    public static void printOneItem(Item item){

        Product product = item.getProduct();
        double price = product.getPrice();
        int quantity = item.getQuantity();

        System.out.println("-".repeat(Constant.LINE_LENGTH_LIMIT));

        printProductInfoLine(String.format("%03d", product.getProductId()), product.getName());
        System.out.println("-".repeat(Constant.LINE_LENGTH_LIMIT));
        printProductInfoLine("PRICE", String.format("$%.2f / VIP $%.2f",
                product.getPrice(), product.getMemberPrice()));
        printProductInfoLine("QUANTITY", String.valueOf(quantity));
        printProductInfoLine("SUBTOTAL", String.format("$%.2f", price * quantity));
        System.out.println("-".repeat(Constant.LINE_LENGTH_LIMIT));
    }

    /**
     * Print out the shopping cart.
     *
     * @param shoppingCart                      A ShoppingCart object.
     * @param currentPage                       Page number of the item list that is currently shown.
     * @param isMember                          Whether the shopping cart's owner is a member or not.
     */
    public static void printShoppingCart(ShoppingCart shoppingCart, int currentPage, boolean isMember) {

        System.out.println();
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));
        System.out.println(" ".repeat(30) + "Shopping Cart");
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));

        if (shoppingCart.getSize() == 0) {
            System.out.println("[-]Cart is empty.");
            return;
        }

        System.out.println("=".repeat(Constant.LINE_LENGTH_LIMIT));
        if (isMember){
            String discount = String.format("-$%.2f", shoppingCart.getMemberDiscount());
            System.out.println("|Membership discount" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 21 -
                    discount.length()) + discount + "|");

            String total = String.format("$%.2f", shoppingCart.getTotalMemberPrice());
            System.out.println("|Total Price (Discounted)" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 26 -
                    total.length()) + total + "|");
        }
        else {
            System.out.println("|No Membership Discount" + " ".repeat(Constant.LINE_LENGTH_LIMIT- 24) + "|");
            String total = String.format("$%.2f", shoppingCart.getTotalPrice());
            System.out.println("|Total Price" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 13 -
                    total.length()) + total + "|");
        }
        System.out.println("=".repeat(Constant.LINE_LENGTH_LIMIT));

        int startIdx = (currentPage - 1) * Constant.CART_PAGE_SIZE;
        int endIdx = Math.min(currentPage * Constant.CART_PAGE_SIZE, shoppingCart.getSize());
        ArrayList<Item> itemList = shoppingCart.getItemList();

        for (int idx = startIdx; idx < endIdx; idx++) {
            printOneItem(itemList.get(idx));
        }

    }

    /**
     * Print out the order summary.
     *
     * @param shoppingCart                      A ShoppingCart object.
     * @param currentPage                       Page number of the item list that is currently shown.
     * @param customer                          The customer who this shopping cart belongs to.
     */
    public static void printOrderSummary(ShoppingCart shoppingCart, int currentPage, Customer customer) {
        System.out.println();
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));
        System.out.println(" ".repeat(30) + "Order Summary");
        System.out.println("+".repeat(Constant.LINE_LENGTH_LIMIT));

        if (shoppingCart.getSize() == 0) {
            System.out.println("[-]No item in shopping cart");
            return;
        }

        //print user information
        System.out.println("=".repeat(Constant.LINE_LENGTH_LIMIT));
        String userName = Util.formatName(customer.getFirstName(), customer.getLastName());
        System.out.println("|User Name" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 11 - userName.length()) +
                userName + "|" );

        String email = customer.getEmail();
        System.out.println("|Email" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 7 - email.length()) + email + "|" );

        String credit = String.format("$%.2f", customer.getCredits());
        System.out.println("|Current Credits" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 17 - credit.length()) +
                credit + "|" );

        //print total price
        if (customer.isMember()){
            String discount = String.format("-$%.2f", shoppingCart.getMemberDiscount());
            System.out.println("|Membership discount" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 21 -
                    discount.length()) + discount+ "|" );

            String total = String.format("$%.2f", shoppingCart.getTotalMemberPrice());
            System.out.println("|Total Price (Discounted)" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 26 -
                    total.length()) + total + "|" );
        }
        else {
            System.out.println("|No Membership Discount" + " ".repeat(Constant.LINE_LENGTH_LIMIT- 24) + "|" );
            String total = String.format("$%.2f", shoppingCart.getTotalPrice());
            System.out.println("|Total Price" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 13 -  total.length()) +
                    total + "|" );
        }
        System.out.println("=".repeat(Constant.LINE_LENGTH_LIMIT));

        // print cart content

        int startIdx = (currentPage - 1) * Constant.CART_PAGE_SIZE;
        int endIdx = Math.min(currentPage * Constant.CART_PAGE_SIZE, shoppingCart.getSize());
        ArrayList<Item> itemList = shoppingCart.getItemList();

        for (int idx = startIdx; idx < endIdx; idx++) {
            printOneItem(itemList.get(idx));
        }
    }

    /**
     * Print out an order's brief.
     *
     * @param order                         The Order object to be printed.
     */
    public static void printOrderBrief(Order order) {
        System.out.println("=".repeat(Constant.LINE_LENGTH_LIMIT));

        String creditString = String.format("$%.2f", order.getCreditBefore());
        System.out.println("|Current credits" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 17 -
                creditString.length()) + creditString + "|");

        String priceString = String.format("$%.2f", order.getPrice());
        System.out.println("|Total price" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 13 -
                priceString.length()) + priceString + "|");

        String memPriceString = String.format("$%.2f", order.getMemberPrice());
        System.out.println("|Total member price" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 20 -
                memPriceString.length()) + memPriceString + "|");

        String enjoyDiscount = order.isMemberPrice() ? "yes" : "no";
        System.out.println("|Enjoying member discount" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 26 -
                enjoyDiscount.length()) + enjoyDiscount + "|");

        String remainingString = String.format("$%.2f", order.getCreditAfter());
        System.out.println("|Credits after pay" + " ".repeat(Constant.LINE_LENGTH_LIMIT - 19 -
                remainingString.length()) + remainingString + "|");

        System.out.println("=".repeat(Constant.LINE_LENGTH_LIMIT));
    }
}
