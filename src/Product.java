/**
 * Class which represents product.
 *
 * @version ver1.0.0
 */
public class Product {
    private static int numberOfProducts = 0;
    private int productId;
    private String name;
    private String brand;
    private String description;
    private double price;
    private double memberPrice;
    private int quantity;
    private String category;
    private String subcategory;

    /**
     * Default constructor of Product class.
     */
    public Product(){
        numberOfProducts++;
        this.productId = numberOfProducts;
        this.name = "default";
        this.brand = "default";
        this.description = "default";
        this.price = 0;
        this.memberPrice = 0;
        this.quantity = 0;
        this.category = "Electronics";
        this.subcategory = "Books";
    }

    /**
     * Constructor of Product class.
     *
     * @param name                      Product name.
     * @param brand                     Product brand.
     * @param description               Product description.
     * @param price                     Product price.
     * @param memberPrice               Product memberPrice.
     * @param quantity                  Product quantity.
     * @param category                  Product category.
     * @param subcategory               Product subcategory.
     */
    public Product(String name, String brand, String description, double price, double memberPrice, int quantity, String category, String subcategory) {
        numberOfProducts++;
        this.productId = numberOfProducts;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.memberPrice = memberPrice;
        this.quantity = quantity;
        this.category = category;
        this.subcategory = subcategory;
    }

    /**
     * Constructor of Product class.
     *
     * @param productId                 Product ID.
     * @param name                      Product name.
     * @param brand                     Product brand.
     * @param description               Product description.
     * @param price                     Product price.
     * @param memberPrice               Product memberPrice.
     * @param quantity                  Product quantity.
     * @param category                  Product category.
     * @param subcategory               Product subcategory.
     */
    public Product(int productId, String name, String brand, String description, double price, double memberPrice, int quantity, String category, String subcategory) {
        numberOfProducts++;
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.memberPrice = memberPrice;
        this.quantity = quantity;
        this.category = category;
        this.subcategory = subcategory;
    }

    /**
     * Constructor of Product class.
     *
     * @param product                   A Product object, will be changed from a non-food into a food product,
     *                                  without changing its attributes/number of products.
     * @param category                  Product category.
     * @param subcategory               Product subcategory.
     */
    public Product(Product product, String category, String subcategory) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.memberPrice = product.getMemberPrice();
        this.quantity = product.getQuantity();
        this.category = category;
        this.subcategory = subcategory;
    }

    /**
     * Constructor of Product class.
     *
     * @param product                   A FoodProduct object, will be changed into a non-food product,
     *                                  without changing its attributes/number of products.
     * @param category                  Product category.
     * @param subcategory               Product subcategory.
     */
    public Product(FoodProduct product, String category, String subcategory) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.memberPrice = product.getMemberPrice();
        this.quantity = product.getQuantity();
        this.category = category;
        this.subcategory = subcategory;
    }

    /**
     * Getter method for productId.
     *
     * @return                          The product ID.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Setter method for productId.
     *
     * @param productId                 New product ID.
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Getter method for product name.
     *
     * @return                         The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for product name.
     *
     * @param name                     New product name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for product brand.
     *
     * @return                         The product brand.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Setter method for product brand.
     *
     * @param brand                    New product brand.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Getter method for product description.
     *
     * @return                         The product description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for product description.
     *
     * @param description              New product description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for product price.
     *
     * @return                         The product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter method for product price.
     *
     * @param price                   New product price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter method for product member price.
     *
     * @return                        The product member price.
     */
    public double getMemberPrice() {
        return memberPrice;
    }

    /**
     * Setter method for product member price.
     *
     * @param memberPrice             New product member price.
     */
    public void setMemberPrice(double memberPrice) {
        this.memberPrice = memberPrice;
    }

    /**
     * Getter method for product quantity.
     *
     * @return                        The product quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method for product quantity.
     *
     * @param quantity             New product quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method for product category.
     *
     * @return                     The product category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter method for product category.
     *
     * @param category             New product category.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter method for product subcategory.
     *
     * @return                     The product subcategory.
     */
    public String getSubcategory() {
        return subcategory;
    }

    /**
     * Setter method for product subcategory.
     *
     * @param subcategory          New product subcategory.
     */
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    /**
     * Check whether a product is a food product.
     *
     * @return                     A boolean representing result.
     */
    public boolean isFoodProduct() {
        return category.equalsIgnoreCase(Constant.CATEGORY_FOOD) ||
                category.equalsIgnoreCase(Constant.CATEGORY_BEVERAGE);
    }

    /**
     * Override the toString() method.
     *
     * @return                  The product's information in the specified format.
     */
    @Override
    public String toString() {
        return String.join(Constant.STRING_FIELD_DELIMITER, new String[]{String.valueOf(productId), name, brand, description,
                String.valueOf(price), String.valueOf(memberPrice), String.valueOf(quantity), category,
                subcategory});
    }

}
