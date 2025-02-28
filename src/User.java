/**
 * Class which controls shopping cart, contains shopping cart-related system logics.
 *
 * @version ver1.0.0
 */
public class User {
    private String email;
    private String password;
    private boolean type;

    /**
     * Default constructor for User class.
     */
    public User() {
        email = "default@email.com";
        password = "default";
        type = Constant.USER_TYPE_CUSTOMER;
    }

    /**
     * Non-default constructor for User class.
     *
     * @param email                     User email.
     * @param password                  User password.
     * @param type                      User type.
     */
    public User(String email, String password, boolean type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }

    /**
     * Getter method for email.
     *
     * @return                  The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter method for password.
     *
     * @return                  The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for password.
     *
     * @param password          New password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter method for email.
     *
     * @param email             New email to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for type.
     *
     * @return                  The user's type.
     */
    public boolean getType() {
        return type;
    }

    /**
     * Check whether the user is admin.
     *
     * @return                  A boolean, if true then the user is admin.
     */
    public boolean isAdmin() {
        return type == Constant.USER_TYPE_ADMIN;
    }

    /**
     * Check whether the user is customer.
     *
     * @return                  A boolean, if true then the user is customer.
     */
    public boolean isCustomer() {return type == Constant.USER_TYPE_CUSTOMER; }

    /**
     * Setter method for type.
     *
     * @param type              New type to set.
     */
    public void setType(boolean type) {
        this.type = type;
    }

    /**
     * Override the toString() method.
     *
     * @return                  The user's information in the specified format.
     */
    @Override
    public String toString() {
        return  String.join(Constant.STRING_FIELD_DELIMITER, new String[]{email, password, String.valueOf(type)});
    }
}