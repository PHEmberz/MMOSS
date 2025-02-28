/**
 * Class which represents order information.
 *
 * @version ver1.0.0
 */
public class Order {
    private double price;
    private double memberPrice;
    private double creditBefore;
    private double creditAfter;
    private boolean isMemberPrice;

    /**
     * Default constructor of Order class.
     */
    public Order(){
        this.price = 0;
        this.memberPrice = 0;
        this.creditBefore = 0;
        this.creditAfter = 0;
        this.isMemberPrice = false;
    }

    /**
     * Constructor of Order class.
     *
     * @param price                     The order's normal price.
     * @param memberPrice               The order's member price.
     * @param creditBefore              The customer's credits before paying.
     * @param creditAfter               The customer's credits after paying.
     * @param isMemberPrice             Whether the customer pays with member discount.
     */
    public Order(double price, double memberPrice, double creditBefore, double creditAfter, boolean isMemberPrice) {
        this.price = price;
        this.memberPrice = memberPrice;
        this.creditBefore = creditBefore;
        this.creditAfter = creditAfter;
        this.isMemberPrice = isMemberPrice;
    }

    /**
     * Getter method for isMemberPrice.
     *
     * @return                         The order's isMemberPrice.
     */
    public boolean isMemberPrice() {
        return isMemberPrice;
    }

    /**
     *  Setter method for memberPrice.
     *
     * @param memberPrice              New memberPrice.
     */
    public void setMemberPrice(boolean memberPrice) {
        isMemberPrice = memberPrice;
    }

    /**
     * Getter method for price.
     *
     * @return                         The order's price.
     */
    public double getPrice() {
        return price;
    }

    /**
     *  Setter method for price.
     *
     * @param price              New price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter method for memberPrice.
     *
     * @return                         The order's memberPrice.
     */
    public double getMemberPrice() {
        return memberPrice;
    }

    /**
     *  Setter method for memberPrice.
     *
     * @param memberPrice              New memberPrice.
     */
    public void setMemberPrice(double memberPrice) {
        this.memberPrice = memberPrice;
    }

    /**
     * Getter method for creditBefore.
     *
     * @return                         The order's creditBefore.
     */
    public double getCreditBefore() {
        return creditBefore;
    }

    /**
     *  Setter method for creditBefore.
     *
     * @param creditBefore              New creditBefore.
     */
    public void setCreditBefore(double creditBefore) {
        this.creditBefore = creditBefore;
    }

    /**
     * Getter method for creditAfter.
     *
     * @return                         The order's creditAfter.
     */
    public double getCreditAfter() {
        return creditAfter;
    }

    /**
     *  Setter method for creditAfter.
     *
     * @param creditAfter              New creditAfter.
     */
    public void setCreditAfter(double creditAfter) {
        this.creditAfter = creditAfter;
    }
}
