/**
 * Class which represents admin user.
 *
 * @version ver1.0.0
 */
public class Admin extends User {
    /**
     * Default constructor of Admin class.
     */
    public Admin() {
        super();
    }

    /**
     * Non-default constructor of Admin class.
     *
     * @param email                     The admin's email.
     * @param password                  The admin's password.
     */
    public Admin(String email, String password) {
        super(email, password, Constant.USER_TYPE_ADMIN);
    }
}
