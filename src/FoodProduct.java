import java.time.LocalDate;

/**
 * Class which represents food product.
 *
 * @version ver1.0.0
 */
public class FoodProduct extends Product{
    // Additional fields for Food category
    private LocalDate expirationDate;
    private String ingredients;
    private String storageInstructions;
    private String allergenInfo;

    /**
     * Constructor of FoodProduct class
     *
     * @param name                      Product name.
     * @param brand                     Product brand.
     * @param description               Product description.
     * @param price                     Product price.
     * @param memberPrice               Product memberPrice.
     * @param quantity                  Product quantity.
     * @param category                  Product category.
     * @param subcategory               Product subcategory.
     * @param expirationDate            Product expiry date.
     * @param ingredients               Product ingredients.
     * @param storageInstructions       Product storage instructions.
     * @param allergenInfo              Product allergen information.
     */
    public FoodProduct(String name, String brand, String description, double price, double memberPrice, int quantity, String category, String subcategory,
                       LocalDate expirationDate, String ingredients, String storageInstructions, String allergenInfo) {
        super(name, brand, description, price, memberPrice, quantity, category, subcategory);
        this.expirationDate = expirationDate;
        this.ingredients = ingredients;
        this.storageInstructions = storageInstructions;
        this.allergenInfo = allergenInfo;
    }

    /**
     * Constructor of FoodProduct class.
     *
     * @param productID                 Product ID
     * @param name                      Product name.
     * @param brand                     Product brand.
     * @param description               Product description.
     * @param price                     Product price.
     * @param memberPrice               Product memberPrice.
     * @param quantity                  Product quantity.
     * @param category                  Product category.
     * @param subcategory               Product subcategory.
     * @param expirationDate            Product expiry date.
     * @param ingredients               Product ingredients.
     * @param storageInstructions       Product storage instructions.
     * @param allergenInfo              Product allergen information.
     */
    public FoodProduct(int productID, String name, String brand, String description, double price, double memberPrice, int quantity, String category, String subcategory,
                       LocalDate expirationDate, String ingredients, String storageInstructions, String allergenInfo) {
        super(productID, name, brand, description, price, memberPrice, quantity, category, subcategory);
        this.expirationDate = expirationDate;
        this.ingredients = ingredients;
        this.storageInstructions = storageInstructions;
        this.allergenInfo = allergenInfo;
    }

    /**
     * Constructor of FoodProduct class.
     *
     * @param product                   A Product object.
     * @param category                  Product category.
     * @param subcategory               Product subcategory.
     * @param expirationDate            Product expiry date.
     * @param ingredients               Product ingredients.
     * @param storageInstructions       Product storage instructions.
     * @param allergenInfo              Product allergen information.
     */
    public FoodProduct(Product product, String category, String subcategory,
                       LocalDate expirationDate, String ingredients, String storageInstructions, String allergenInfo)
    {
        super(product, category, subcategory);
        this.expirationDate = expirationDate;
        this.ingredients = ingredients;
        this.storageInstructions = storageInstructions;
        this.allergenInfo = allergenInfo;
    }

    /**
     * Getter of expirationDate.
     *
     * @return                        Product expiry date.
     */
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * Setter of expirationDate.
     *
     * @param expirationDate          New product expiry date.
     */
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Getter of ingredients.
     *
     * @return                        Product ingredients.
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * Setter of expirationDate.
     *
     * @param ingredients            New product ingredients.
     */
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Getter of storageInstructions.
     *
     * @return                        Product storage instructions.
     */
    public String getStorageInstructions() {
        return storageInstructions;
    }

    /**
     * Setter of expirationDate.
     *
     * @param storageInstructions     New product storage instructions.
     */
    public void setStorageInstructions(String storageInstructions) {
        this.storageInstructions = storageInstructions;
    }

    /**
     * Getter of allergenInfo.
     *
     * @return                        Product allergen information.
     */
    public String getAllergenInfo() {
        return allergenInfo;
    }

    /**
     * Setter of allergenInfo.
     *
     * @param allergenInfo            New product allergen information.
     */
    public void setAllergenInfo(String allergenInfo) {
        this.allergenInfo = allergenInfo;
    }

    /**
     * Override the toString() method.
     *
     * @return                  The product's information in the specified format.
     */
    @Override
    public String toString() {
        return String.join(Constant.STRING_FIELD_DELIMITER, new String[]{super.toString(), Util.getStringFromDate(expirationDate),
                ingredients, storageInstructions, allergenInfo});
    }

}
