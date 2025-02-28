import java.util.ArrayList;
/**
 * Class which represents shopping cart.
 *
 * @version ver1.0.0
 */
public class ShoppingCart {
    private final ArrayList<Item> itemList;

    /**
     * Default constructor for ShoppingCart class.
     */
    public ShoppingCart() {
        this.itemList = new ArrayList<>();
    }

    /**
     * Non-default constructor for ShoppingCart class.
     *
     * @param itemList                  An ArrayList of Item objects that belongs to the cart.
     */
    public ShoppingCart(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    /**
     * Getter method for itemList.
     *
     * @return                          An ArrayList of Item objects that belongs to the cart.
     */
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    /**
     * Get the shopping cart's total price.
     *
     * @return                          The total price of the items in the cart.
     */
    public double getTotalPrice() {
        double price = (double) 0;
        for (Item item : itemList) {
            price += item.getTotalPrice();
        }
        return price;
    }

    /**
     * Get the shopping cart's total member price.
     *
     * @return                           The total member price of the items in the cart.
     */
    public double getTotalMemberPrice() {
        double price = (double) 0;
        for (Item item : itemList) {
            price += item.getTotalMemberPrice();
        }
        return price;
    }

    /**
     * Get the shopping cart's total member discount.
     *
     * @return                            The total member discount of the items in the cart.
     */
    public double getMemberDiscount() {
        double discount = (double) 0;
        for (Item item : itemList) {
            Product product = item.getProduct();
            discount += (product.getPrice() - product.getMemberPrice()) *
                         item.getQuantity();
        }
        return discount;
    }

    /**
     * Get an item in the shopping cart by its product.
     *
     * @param product                       The product to be used for searching.
     * @return                              The targeted Item object.
     */
    public Item getItemByProduct(Product product) {
        for (Item item : itemList) {
            if (item.getProduct() == product) {
                return item;
            }
        }
        return null;
    }

    /**
     * Add an item to the shopping cart.
     *
     * @param product                       The product to be added.
     * @param quantity                      The quantity of the added product.
     */
    public void addItem(Product product, int quantity) {
        Item item = new Item(product, quantity);
        itemList.add(item);
    }

    /**
     * Set the item's quantity.
     *
     * @param product                      The target product that is included in the item.
     * @param quantity                     The new quantity of the product.
     */
    public void setItemQuantity(Product product, int quantity) {
        for (Item item : itemList) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                item.setQuantity(quantity);
            }
        }
    }

    /**
     * Get the shopping cart's size.
     *
     * @return                              The size of the shopping cart.
     */
    public int getSize() {
        return itemList.size();
    }


    /**
     * Check how many pieces of the target product does the cart contain.
     *
     * @param product                       The target product.
     * @return                              The quantity of the product currently in the cart.
     */
    public int checkProductQuantity(Product product) {
        for (Item item : itemList) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                return item.getQuantity();
            }
        }
        return 0;
    }

    /**
     * Clear the shopping cart.
     */

    public void clear() {
        itemList.clear();
    }

}
