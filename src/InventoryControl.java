import java.time.LocalDate;
import java.util.*;
/**
 * Class which keeps track of inventory data and provides management operations,
 * also is responsible for data persistence.
 *
 * @version ver1.0.0
 */
public class InventoryControl {

    private final ArrayList<Product> productList;
    private final HashMap<String, List<String>> categoryList;

    /**
     * Constructor of InventoryControl class.
     */
    public InventoryControl() {
        productList = new ArrayList<>();
        categoryList = new HashMap<>();
        getProductsFromFile();
        getCategoriesFromFile();
    }

    /**
     * Method to read from the specified category file and populate data.
     */
    public void getCategoriesFromFile() {
        // read category data file
        String fileContent = new FileIO(Constant.CATEGORY_FILE_PATH).readFile();

        if (fileContent.isEmpty()){ // no such file, or empty file, create default categories
            Boundary.alertEmptyOrNotExistFile(Constant.CATEGORY_FILE_PATH);
            categoryList.putAll(Constant.DEFAULT_CATEGORIES);
            return;
        }

        String[] lines = fileContent.split(Constant.STRING_NEXT_LINE);

        int invalidLines = 0;
        for (String line : lines) {
            String[] fields = line.split(Constant.STRING_FIELD_DELIMITER);
            try {
                if (fields.length > 1) { // a category have at least one subcategory
                    String category = fields[0];
                    List<String> subcategories = new ArrayList<>(Arrays.asList(fields).subList(1, fields.length));
                    categoryList.put(category, subcategories);
                }
                else {
                    throw new Exception(Constant.ERR_MSG_INVALID_LINE);
                }
            }
            catch (Exception e) {
                invalidLines++;
                Boundary.alertInvalidFileLine(line, Constant.CATEGORY_FILE_PATH);
            }
        }
        if (invalidLines == lines.length) { // no valid content, use default settings
            Boundary.alertTotalInvalidFileContent(Constant.CATEGORY_FILE_PATH);
            categoryList.putAll(Constant.DEFAULT_CATEGORIES);
        }
        if (invalidLines > 0) { // partially invalid content
            Boundary.alertPartialInvalidFileContent(Constant.CATEGORY_FILE_PATH, invalidLines);
        }
    }

    /**
     * Method to read from the specified inventory file and populate data.
     */
    private void getProductsFromFile() {

        // read product data file
        String fileContent = new FileIO(Constant.INVENTORY_FILE_PATH).readFile();

        if (fileContent.isEmpty()){ // no content or no such file, it's acceptable
            return;
        }

        String[] lines = fileContent.split(Constant.STRING_NEXT_LINE);

        int invalidLines = 0;
        for (String line : lines) {
            String[] fields = line.split(Constant.STRING_FIELD_DELIMITER);
            try {
                if (fields.length == 9) {
                    productList.add(new Product(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3],
                            Double.parseDouble(fields[4]), Double.parseDouble(fields[5]),
                            Integer.parseInt(fields[6]), fields[7], fields[8]));
                }
                else if (fields.length == 13) {
                    productList.add(new FoodProduct(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3],
                            Double.parseDouble(fields[4]), Double.parseDouble(fields[5]),
                            Integer.parseInt(fields[6]), fields[7], fields[8],
                            Util.getDateFromISOString(fields[9]), fields[10], fields[11], fields[12]));
                }
                else {
                    invalidLines++;
                    throw new Exception(Constant.ERR_MSG_INVALID_LINE);
                }
            }
            catch (Exception e) {
                Boundary.alertInvalidFileLine(line, Constant.INVENTORY_FILE_PATH);
            }
        }

        if (invalidLines > 0) {
            Boundary.alertPartialInvalidFileContent(Constant.INVENTORY_FILE_PATH, invalidLines);
        }
    }

    /**
     * Update inventory file's content with latest productList data.
     */
    public void updateInventoryFile() {
        StringBuilder fileContent = new StringBuilder();
        for (Product product : productList) {
            fileContent.append(product.toString()).append("\n");
        }
        new FileIO(Constant.INVENTORY_FILE_PATH).writeFile(fileContent.toString());
    }

    /**
     * Check whether a category is valid.
     *
     * @param category                      The target category.
     * @return                              A boolean representing whether the category is valid.
     */
    private boolean isCategoryValid(String category) {
        for (String key : categoryList.keySet()) {
            if (key.equalsIgnoreCase(category)){
                return true;
            }
        }
        return false;
    }

    /**
     * Get the valid category name.
     *
     * @param category                      The target category.
     * @return                              The valid category name.
     */
    private String getValidCategory(String category) {
        for (String key : categoryList.keySet()) {
            if (key.equalsIgnoreCase(category)){
                return key;
            }
        }
        return null;
    }

    /**
     * Check whether a subcategory is valid under a given category.
     *
     * @param category                      The target category.
     * @param subcategory                   The target subcategory.
     * @return                              A boolean representing whether the subcategory is valid.
     */
    private boolean isSubcategoryValid(String category, String subcategory) {
        for (var entry : categoryList.entrySet()) {
            if ((entry.getKey()).equalsIgnoreCase(category)){
                for (String value : entry.getValue()){
                    if ((value).equalsIgnoreCase(subcategory)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Get the valid subcategory name.
     *
     * @param category                      The target category.
     * @param subcategory                   The target subcategory.
     * @return                              The valid subcategory name.
     */
    private String getValidSubcategory(String category, String subcategory) {
        for (var entry : categoryList.entrySet()) {
            if ((entry.getKey()).equalsIgnoreCase(category)){
                for (String value : entry.getValue()){
                    if ((value).equalsIgnoreCase(subcategory)){
                        return value;
                    }
                }
            }
        }
        return null;
    }


    /**
     * Get a product by its ID.
     *
     * @param productID                     The target product's ID.
     * @return                              The target Product object.
     */
    public Product getProductByID(int productID) {
        for (Product product : productList) {
            if (product.getProductId() == productID) {
                return product;
            }
        }
        return null;
    }
    
    /**
     * Control the process of viewing the inventory.
     *
     * @return                                  A code representing the operation's result.
     */
    public int viewInventory() {
        if (productList.isEmpty()) {
             Boundary.alertEmptyInventory();
                return Constant.CODE_BACK;
        }

        int currentPage = 1;
        int totalPages = (int) Math.ceil((double) (productList.size()) / Constant.PRODUCT_PAGE_SIZE);

        productList.sort(Comparator.comparing(Product::getProductId));
        Boundary.printInventory(productList, currentPage);
        Boundary.printInventoryMenu(currentPage, totalPages);

        while (true) {

            String input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }

            switch (input) {
                case ">" -> {
                    if (currentPage < totalPages) {
                        Boundary.printInventory(productList, ++currentPage);
                        Boundary.printInventoryMenu(currentPage, totalPages);
                    } else {
                        Boundary.alertLastPage();
                    }
                }
                case "<" -> {
                    if (currentPage > 1) {
                        Boundary.printInventory(productList, --currentPage);
                        Boundary.printInventoryMenu(currentPage, totalPages);
                    } else {
                        Boundary.alertFirstPage();
                    }
                }
                case "0" -> {
                    return Constant.CODE_BACK;
                }
                default -> {
                    Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                }
            }

        }

    }
    
    /**
     * Control the process of creating a new product.
     *
     * @return                                  A code representing the operation's result.
     */
    public int createProduct() {

        String input;
        String category = null;
        String subcategory = null;
        String name = null;
        String brand;
        String description;
        double price = 0.0;
        double memberPrice = 0.0;
        int quantity = 0;
        LocalDate expiryDate = null;
        String ingredient = null;
        String storage = null;
        String allergen = null;

        //category
        Boundary.printAvailableCategories(categoryList);
        boolean valid = false;
        while (!valid) {
            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_CATEGORY);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            if (input.isEmpty()){
                Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_CATEGORY);
            }
            else if (!isCategoryValid(input)) {
                Boundary.alertCategoryNotExist(input);
            } else {
                valid = true;
                category = getValidCategory(input);
            }
        }

        //subcategory
        Boundary.printAvailableSubcategories(categoryList, category);
        valid = false;
        while (!valid) {
            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_SUBCATEGORY);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            if (input.isEmpty()){
                Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_SUBCATEGORY);
            }
            else if (!isSubcategoryValid(category, input)) {
                Boundary.alertSubcategoryNotExist(input);
            } else {
                valid = true;
                subcategory = getValidSubcategory(category, input);
            }
        }

        // name
        valid = false;
        while (!valid) {
            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_NAME);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            if (input.isEmpty()) {
                Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_NAME);
            } else {
                valid = true;
                name = input;
            }
        }

        //brand
        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_BRAND);
        input = Input.acceptStringInput();

        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
            return Constant.CODE_LOGOUT;
        }
        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
            return Constant.CODE_BACK;
        }
        brand = Util.formatEmptyAbleValue(input);

        //description
        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_DESC);
        input = Input.acceptStringInput();

        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
            return Constant.CODE_LOGOUT;
        }
        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
            return Constant.CODE_BACK;
        }
        description = Util.formatEmptyAbleValue(input);

        //price
        valid = false;
        while (!valid) {
            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_PRICE);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            if (!Util.isDouble(input)) {
                Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_PRICE);
            } else {
                price = Double.parseDouble(input);
                if (price <= 0 || price >= Constant.PRODUCT_PRICE_LIMIT) {
                    Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_PRICE);
                } else {
                    valid = true;
                }
            }
        }

        // member price
        valid = false;
        while (!valid) {
            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_MEMBER_PRICE);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            if (!Util.isDouble(input)) {
                Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_MEMBER_PRICE);
            } else {
                memberPrice = Double.parseDouble(input);
                if (memberPrice <= 0 || memberPrice >= Constant.PRODUCT_PRICE_LIMIT) {
                    Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_MEMBER_PRICE);
                } else if (memberPrice > price) {
                    Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_MEMBER_PRICE);
                } else {
                    valid = true;
                }
            }
        }

        // quantity
        valid = false;
        while (!valid) {
            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_QUANTITY);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            if (!Util.isInteger(input)) {
                Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_QUANTITY);
            } else {
                quantity = Integer.parseInt(input);
                if (quantity < 0) {
                    Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_QUANTITY);
                } else {
                    valid = true;
                }
            }
        }


        boolean isFoodProduct = category.equalsIgnoreCase(Constant.CATEGORY_FOOD) ||
                category.equalsIgnoreCase(Constant.CATEGORY_BEVERAGE);


        if (isFoodProduct) {
            // expiry date
            valid = false;
            while (!valid) {
                Boundary.promptExpiryDateInput();
                input = Input.acceptStringInput();

                if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                    return Constant.CODE_LOGOUT;
                }
                if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                    return Constant.CODE_BACK;
                }
                if (!Util.isValidDateString(input)) {
                    Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_EXPIRY);
                } else {
                    valid = true;
                    expiryDate = Util.getDateFromString(input);
                }
            }

            // ingredient
            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_INGREDIENT);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            ingredient = Util.formatEmptyAbleValue(input);

            // storage
            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_STORAGE);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            storage = Util.formatEmptyAbleValue(input);

            // allergen
            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_ALLERGEN);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            allergen = Util.formatEmptyAbleValue(input);
        }

        Product newProduct = (isFoodProduct) ?
                new FoodProduct(name, brand, description, price, memberPrice,
                        quantity, category, subcategory, expiryDate, ingredient, storage, allergen) :
                new Product(name, brand, description, price, memberPrice,
                        quantity, category, subcategory);


        Boundary.printOverViewOfProduct(newProduct, Constant.OP_TYPE_CREATE_PRODUCT);

        //confirmation
        valid = false;
        while (!valid) {
            Boundary.promptConfirm(Constant.OP_TYPE_CREATE_PRODUCT);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                return Constant.OPERATION_CANCEL;
            }
            if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                valid = true;
            } else {
                Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
            }
        }

        productList.add(newProduct);
        productList.sort(Comparator.comparing(Product::getProductId));
        updateInventoryFile();
        return Constant.OPERATION_SUCCESS;
    }
    
    /**
     * Control the process of editing product information.
     *
     * @return                                  A code representing the operation's result.
     */
    public int editProduct() {

        String input;
        Product product = null;

        //acquire product id
        boolean valid = false;
        while (!valid) {

            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_ID);
            input = Input.acceptStringInput();

            // check whether user inputs keyword
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }

            if (Util.isInteger(input)) {
                int productID = Integer.parseInt(input);
                product = getProductByID(productID);
                if (product == null) {  // id doesn't exist
                    Boundary.alertProductNotExist(productID);
                } else { // product is fine
                    valid = true;
                }
            } else {
                // input is neither keyword
                Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_ID);
            }
        }

        while (true) {
            Boundary.printEditProductMenu(product);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }

            switch (input) {
                case "0" -> {
                    return Constant.CODE_BACK;
                }
                case "1" -> {//category
                    String newCategory = null;
                    String newSubcategory = null;

                    Boundary.printAvailableCategories(categoryList);
                    boolean back = false;
                    valid = false;
                    while (!valid) {
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_CATEGORY);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            back = true;
                            break;
                        }
                        if (input.isEmpty()){
                            Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_CATEGORY);
                        }
                        else if (!isCategoryValid(input)) {
                            Boundary.alertCategoryNotExist(input);
                        } else {
                            valid = true;
                            newCategory = getValidCategory(input);
                        }
                    }

                    if (back) break;

                    //subcategory
                    Boundary.printAvailableSubcategories(categoryList, newCategory);
                    back = false;
                    valid = false;
                    while (!valid) {
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_SUBCATEGORY);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            back = true;
                            break;
                        }
                        if (input.isEmpty()){
                            Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_SUBCATEGORY);
                        }
                        else if (!isSubcategoryValid(newCategory, input)) {
                            Boundary.alertSubcategoryNotExist(input);
                        } else {
                            valid = true;
                            newSubcategory = getValidSubcategory(newCategory, input);
                        }
                    }

                    if (back) break;

                    boolean isFoodProduct = newCategory.equalsIgnoreCase(Constant.CATEGORY_FOOD) ||
                            newCategory.equalsIgnoreCase(Constant.CATEGORY_BEVERAGE);

                    LocalDate expiryDate = null;
                    String ingredient = null;
                    String storage = null;
                    String allergen = null;

                    // change a non-food product into food product
                    if (isFoodProduct && !product.isFoodProduct()){
                        back = false;
                        Boundary.alertProductTypeChange(Constant.OP_TYPE_CHANGE_NONFOOD_TO_FOOD);

                        // expiry date
                        valid = false;
                        while (!valid) {
                            Boundary.promptExpiryDateInput();
                            input = Input.acceptStringInput();

                            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                                return Constant.CODE_LOGOUT;
                            }
                            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                                Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                                back = true;
                                break;
                            }
                            if (!Util.isValidDateString(input)) {
                                Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_EXPIRY);
                            } else {
                                valid = true;
                                expiryDate = Util.getDateFromString(input);
                            }
                        }

                        if (back) break;

                        // ingredient
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_INGREDIENT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        ingredient = Util.formatEmptyAbleValue(input);

                        // storage
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_STORAGE);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        storage = Util.formatEmptyAbleValue(input);

                        // allergen
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_ALLERGEN);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        allergen = Util.formatEmptyAbleValue(input);
                    }
                    else if (!isFoodProduct && product.isFoodProduct()){
                        Boundary.alertProductTypeChange(Constant.OP_TYPE_CHANGE_FOOD_TO_NONFOOD);
                    }

                    //confirmation
                    valid = false;
                    while (!valid) {
                        Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                            valid = true;
                            if (isFoodProduct && !product.isFoodProduct()) { // change a non-food to food
                                FoodProduct newProduct = new FoodProduct(product, newCategory, newSubcategory,
                                        expiryDate, ingredient, storage, allergen);
                                productList.remove(product);
                                productList.add(newProduct);

                                product = newProduct;
                            }
                            else if (!isFoodProduct && product.isFoodProduct()) { // change a food into non-food
                                Product newProduct = new Product((FoodProduct)product, newCategory, newSubcategory);
                                productList.remove(product);
                                productList.add(newProduct);

                                product = newProduct;
                            }
                            else {
                                product.setCategory(newCategory);
                                product.setSubcategory(newSubcategory);
                            }
                            updateInventoryFile();
                        } else {
                            Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                        }
                    }
                    Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                }
                case "2" -> {//subcategory
                    String newSubcategory = null;
                    Boundary.printAvailableSubcategories(categoryList, product.getCategory());
                    boolean back = false;
                    valid = false;
                    while (!valid) {
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_SUBCATEGORY);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            back = true;
                            break;
                        }
                        if (input.isEmpty()){
                            Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_SUBCATEGORY);
                        }
                        else if (!isSubcategoryValid(product.getCategory(), input)) {
                            Boundary.alertSubcategoryNotExist(input);
                        } else {
                            valid = true;
                            newSubcategory = getValidSubcategory(product.getCategory(), input);
                        }
                    }

                    if (back) break;

                    //confirmation
                    valid = false;
                    while (!valid) {
                        Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                            valid = true;
                            product.setSubcategory(newSubcategory);
                            updateInventoryFile();
                            Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                        } else {
                            Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                        }
                    }
                }
                case "3" -> { //name
                    String newName = null;
                    boolean back = false;
                    valid = false;
                    while (!valid) {
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_NAME);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            back = true;
                            break;
                        }
                        if (input.isEmpty()) {
                            Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_NAME);
                        } else {
                            valid = true;
                            newName = input;
                        }
                    }

                    if (back) break;

                    //confirmation
                    valid = false;
                    while (!valid) {
                        Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                            valid = true;
                            product.setName(newName);
                            updateInventoryFile();
                            Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                        } else {
                            Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                        }
                    }
                }
                case "4" -> { //brand
                    String newBand;
                    Boundary.promptFieldInput(Constant.FIELD_PRODUCT_BRAND);
                    input = Input.acceptStringInput();

                    if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                        return Constant.CODE_LOGOUT;
                    }
                    if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                        Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                        break;
                    }
                    newBand = Util.formatEmptyAbleValue(input);

                    //confirmation
                    valid = false;
                    while (!valid) {
                        Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                            valid = true;
                            product.setBrand(newBand);
                            updateInventoryFile();
                            Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                        } else {
                            Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                        }
                    }
                }
                case "5" -> {//description
                    String newDesc;
                    Boundary.promptFieldInput(Constant.FIELD_PRODUCT_DESC);
                    input = Input.acceptStringInput();

                    if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                        return Constant.CODE_LOGOUT;
                    }
                    if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                        Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                        break;
                    }
                    newDesc = Util.formatEmptyAbleValue(input);

                    //confirmation
                    valid = false;
                    while (!valid) {
                        Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                            valid = true;
                            product.setDescription(newDesc);
                            updateInventoryFile();
                            Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                        } else {
                            Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                        }
                    }
                }
                case "6" -> {//price
                    double newPrice = 0.0;
                    double newMemberPrice = 0.0;
                    boolean back = false;
                    valid = false;
                    while (!valid) {
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_PRICE);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            back = true;
                            break;
                        }
                        if (!Util.isDouble(input)) {
                            Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_PRICE);
                        } else {
                            newPrice = Double.parseDouble(input);
                            if (newPrice <= 0 || newPrice >= Constant.PRODUCT_PRICE_LIMIT) {
                                Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_PRICE);
                            } else {
                                valid = true;
                            }
                        }
                    }

                    if (back) break;

                    back = false;
                    valid = false;
                    while (!valid) {
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_MEMBER_PRICE);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            back = true;
                            break;
                        }
                        if (!Util.isDouble(input)) {
                            Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_MEMBER_PRICE);
                        } else {
                            newMemberPrice = Double.parseDouble(input);
                            if (newMemberPrice <= 0 || newMemberPrice >= Constant.PRODUCT_PRICE_LIMIT) {
                                Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_MEMBER_PRICE);
                            } else if (newMemberPrice > newPrice) {
                                Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_MEMBER_PRICE);
                            } else {
                                valid = true;
                            }
                        }
                    }

                    if (back) break;

                    //confirmation
                    valid = false;
                    while (!valid) {
                        Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                            valid = true;
                            product.setPrice(newPrice);
                            product.setMemberPrice(newMemberPrice);
                            updateInventoryFile();
                            Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                        } else {
                            Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                        }
                    }
                }
                case "7" -> {//member price
                    double newMemberPrice = 0.0;
                    boolean back = false;
                    valid = false;
                    while (!valid) {
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_MEMBER_PRICE);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            back = true;
                            break;
                        }
                        if (!Util.isDouble(input)) {
                            Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_MEMBER_PRICE);
                        } else {
                            newMemberPrice = Double.parseDouble(input);
                            if (newMemberPrice <= 0 || newMemberPrice >= Constant.PRODUCT_PRICE_LIMIT) {
                                Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_MEMBER_PRICE);
                            } else if (newMemberPrice > product.getPrice()) {
                                Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_MEMBER_PRICE);
                            } else {
                                valid = true;
                            }
                        }
                    }

                    if (back) break;

                    //confirmation
                    valid = false;
                    while (!valid) {
                        Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                            valid = true;
                            product.setMemberPrice(newMemberPrice);
                            updateInventoryFile();
                            Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                        } else {
                            Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                        }
                    }
                }
                case "8" -> {//quantity
                    int newQuantity = 0;
                    boolean back = false;
                    valid = false;
                    while (!valid) {
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_QUANTITY);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            back = true;
                            break;
                        }
                        if (!Util.isInteger(input)) {
                            Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_QUANTITY);
                        } else {
                            newQuantity = Integer.parseInt(input);
                            if (newQuantity < 0) {
                                Boundary.alertInvalidValue(Constant.FIELD_PRODUCT_QUANTITY);
                            } else {
                                valid = true;
                            }
                        }
                    }

                    if (back) break;

                    //confirmation
                    valid = false;
                    while (!valid) {
                        Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                            valid = true;
                            product.setQuantity(newQuantity);
                            updateInventoryFile();
                            Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                        } else {
                            Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                        }
                    }
                }

                case "9" -> {//expiry date
                    if (product.isFoodProduct()) {
                        LocalDate newExpiryDate = null;
                        valid = false;
                        boolean back = false;
                        while (!valid) {
                            Boundary.promptExpiryDateInput();
                            input = Input.acceptStringInput();

                            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                                return Constant.CODE_LOGOUT;
                            }
                            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                                Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                                back = true;
                                break;
                            }
                            if (!Util.isValidDateString(input)) {
                                Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_EXPIRY);
                            } else {
                                valid = true;
                                newExpiryDate = Util.getDateFromString(input);
                            }
                        }

                        if (back) break;

                        //confirmation
                        valid = false;
                        while (!valid) {
                            Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                            input = Input.acceptStringInput();

                            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                                return Constant.CODE_LOGOUT;
                            }
                            if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                                Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                                break;
                            }
                            if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                                valid = true;
                                ((FoodProduct)product).setExpirationDate(newExpiryDate);
                                updateInventoryFile();
                                Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                            } else {
                                Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                            }
                        }
                    }
                    else {
                        Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                    }

                }
                case "10" -> {//ingredients
                    if (product.isFoodProduct()) {

                        String newIngredients;
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_INGREDIENT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        newIngredients = Util.formatEmptyAbleValue(input);

                        //confirmation
                        valid = false;
                        while (!valid) {
                            Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                            input = Input.acceptStringInput();

                            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                                return Constant.CODE_LOGOUT;
                            }
                            if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                                Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                                break;
                            }
                            if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                                valid = true;
                                ((FoodProduct)product).setIngredients(newIngredients);
                                updateInventoryFile();
                                Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                            } else {
                                Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                            }
                        }
                    }
                    else {
                        Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                    }
                }
                case "11" -> {//storage instructions
                    if (product.isFoodProduct()) {
                        String newStorage;
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_STORAGE);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        newStorage = Util.formatEmptyAbleValue(input);

                        //confirmation
                        valid = false;
                        while (!valid) {
                            Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                            input = Input.acceptStringInput();

                            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                                return Constant.CODE_LOGOUT;
                            }
                            if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                                Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                                break;
                            }
                            if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                                valid = true;
                                ((FoodProduct)product).setStorageInstructions(newStorage);
                                updateInventoryFile();
                                Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                            } else {
                                Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                            }
                        }
                    }
                    else {
                        Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                    }
                }
                case "12" -> {//allergen info
                    if (product.isFoodProduct()) {
                        String allergen;
                        Boundary.promptFieldInput(Constant.FIELD_PRODUCT_ALLERGEN);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                            return Constant.CODE_LOGOUT;
                        }
                        if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                            Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                            break;
                        }
                        allergen = Util.formatEmptyAbleValue(input);

                        //confirmation
                        valid = false;
                        while (!valid) {
                            Boundary.promptConfirm(Constant.OP_TYPE_UPDATE_PRODUCT);
                            input = Input.acceptStringInput();

                            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                                return Constant.CODE_LOGOUT;
                            }
                            if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                                Boundary.printOperationCancel(Constant.OP_TYPE_UPDATE_PRODUCT);
                                break;
                            }
                            if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                                valid = true;
                                ((FoodProduct)product).setAllergenInfo(allergen);
                                updateInventoryFile();
                                Boundary.printOperationSuccess(Constant.OP_TYPE_UPDATE_PRODUCT);
                            } else {
                                Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                            }
                        }
                    }
                    else {
                        Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                    }
                }

                default -> {
                    Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                }
            }

        }

    }
    
    /**
     * Control the process of removing product from the inventory.
     *
     * @return                                  A code representing the operation's result.
     */
    public int removeProduct() {
        String input;
        Product product = null;

        //acquire product id
        boolean valid = false;
        while (!valid) {

            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_ID);
            input = Input.acceptStringInput();

            // check whether user inputs keyword
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }

            if (Util.isInteger(input)) {
                int productID = Integer.parseInt(input);
                product = getProductByID(productID);
                if (product == null) {  // id doesn't exist
                    Boundary.alertProductNotExist(productID);
                } else { // product is fine
                    valid = true;
                }
            } else {
                // input is neither keyword
                Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_ID);
            }
        }

        Boundary.printOverViewOfProduct(product, Constant.OP_TYPE_REMOVE_PRODUCT);

        //confirmation
        valid = false;
        while (!valid) {
            Boundary.promptConfirm(Constant.OP_TYPE_REMOVE_PRODUCT);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK) || input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                return Constant.OPERATION_CANCEL;
            }
            if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                valid = true;
            } else {
                Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
            }
        }

        productList.remove(product);
        updateInventoryFile();
        return Constant.OPERATION_SUCCESS;

    }

    /**
     * update the products quantity and update the product file after checking out.
     *
     * @param itemList                         An Arraylist of Item that has been bought.
     */
    public void updateQuantityAfterCheckout(ArrayList<Item> itemList) {
        for (Item item : itemList) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
        }
        updateInventoryFile();
    }

}
