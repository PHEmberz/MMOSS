/**
 * Class which represents an shopping cart item, indicating which product and how many.
 *
 * @version ver1.0.0
 */
public class Item {
    private int quantity;
    private Product product;

    /**
     * Constructor of Item class.
     *
     * @param product               The product to be added to the cart.
     * @param quantity              The quantity of the product.
     */
    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Setter method for quantity.
     *
     * @param quantity             The new quantity of the product.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method for quantity.
     *
     * @return                     The product's quantity in the cart.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method for product.
     *
     * @param product              The new product.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Getter method for product.
     *
     * @return                     The product that contains in this cart item.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Calculate subtotal price of the products contained in this cart item.
     *
     * @return                     The subtotal price of the products contained in this cart item.
     */
    public double getTotalPrice(){
        return product.getPrice() * quantity;
    }

    /**
     * Calculate subtotal member price of the products contained in this cart item.
     *
     * @return                     The subtotal member price of the products contained in this cart item.
     */
    public double getTotalMemberPrice(){
        return product.getMemberPrice() * quantity;
    }
}
