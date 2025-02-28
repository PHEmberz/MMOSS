import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class which keeps track of users data and provides management operations,
 * also is responsible for data persistence.
 *
 * @version ver1.0.0
 */
public class UserControl {

    private final ArrayList<User> userList;
    private final HashMap<Customer, ArrayList<Order>> orderList;
    private User currentUser;

    /**
     * Constructor of UserControl class.
     */
    public UserControl() {
        userList = new ArrayList<>();
        currentUser = null;
        orderList = new HashMap<>();
        getUsersFromFile();
    }

    /**
     * Getter method for userList.
     *
     * @return                  ArrayList representing current user list.
     */
    public ArrayList<User> getUserList() {
        return userList;
    }

    /**
     * Getter method for currentUser.
     *
     * @return                  User object representing current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }


    /**
     * Check whether current user is admin.
     *
     * @return                  A boolean representing the current user's type.
     */
    public boolean isCurrentUserAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    /**
     * Setter method for currentUser.
     *
     * @param currentUser       A User object.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Method to read from the specified user file and populate data.
     */
    public void getUsersFromFile() {
        // read user data file
        String fileContent = new FileIO(Constant.USER_FILE_PATH).readFile();

        if (fileContent.isEmpty()){ // no content or no such file, create default users
            Boundary.alertEmptyOrNotExistFile(Constant.USER_FILE_PATH);

            Customer defaultCustomer = Constant.DEFAULT_CUSTOMER;
            userList.add(new Customer(defaultCustomer.getEmail(), defaultCustomer.getPassword(),
                    defaultCustomer.getFirstName(), defaultCustomer.getLastName(),
                    defaultCustomer.getBirthday(), defaultCustomer.getMobile(),
                    defaultCustomer.getAddress(), defaultCustomer.getGender(),
                    defaultCustomer.getMemberExpireDate()));

            Admin defaultAdmin = Constant.DEFAULT_ADMIN;
            userList.add(new Admin(defaultAdmin.getEmail(), defaultAdmin.getPassword()));

            return;
        }

        String[] lines = fileContent.split(Constant.STRING_NEXT_LINE);

        int invalidLines = 0;
        for (String line : lines) {
            String[] fields = line.split(Constant.STRING_FIELD_DELIMITER);
            try {
                // the fields length indicates it is admin
                if (fields.length == 3 && fields[2].equals(String.valueOf(Constant.USER_TYPE_ADMIN))) {
                    userList.add(new Admin(fields[0], fields[1]));
                } else if (fields.length == 11 && fields[2].equals(String.valueOf(Constant.USER_TYPE_CUSTOMER))) {
                    // the fields length indicates it is customer
                    userList.add(new Customer(fields[0], fields[1], fields[3], fields[4],
                            Util.getDateFromISOString(fields[5]), fields[6], fields[7],
                            fields[8], Util.getDateFromISOString(fields[9]), Double.parseDouble(fields[10])));
                } else {
                    throw new Exception(Constant.ERR_MSG_INVALID_LINE);
                }
            } catch (Exception e) {
                invalidLines++;
                Boundary.alertInvalidFileLine(line, Constant.USER_FILE_PATH);
            }
        }

        if (invalidLines == lines.length) { // no valid content, use default settings
            Boundary.alertTotalInvalidFileContent(Constant.USER_FILE_PATH);

            Customer defaultCustomer = Constant.DEFAULT_CUSTOMER;
            userList.add(new Customer(defaultCustomer.getEmail(), defaultCustomer.getPassword(),
                    defaultCustomer.getFirstName(), defaultCustomer.getLastName(),
                    defaultCustomer.getBirthday(), defaultCustomer.getMobile(),
                    defaultCustomer.getAddress(), defaultCustomer.getGender(),
                    defaultCustomer.getMemberExpireDate()));

            Admin defaultAdmin = Constant.DEFAULT_ADMIN;
            userList.add(new Admin(defaultAdmin.getEmail(), defaultAdmin.getPassword()));
        }
        else if (invalidLines > 0) { // partially invalid content
            Boundary.alertPartialInvalidFileContent(Constant.USER_FILE_PATH, invalidLines);
        }
    }

    /**
     * Get current user's name, for admin user, return "Admin", for customer return full name.
     *
     * @return                  The current user's name.
     */
    public String getCurrentUserName() {

        if (currentUser.isAdmin()) {
            return "Admin";
        } else {
            return Util.formatName(((Customer) currentUser).getFirstName(),
                    ((Customer) currentUser).getLastName());
        }
    }


    /**
     * Method to handle user login.
     *
     * @return                      A code representing the operation's result.
     */
    public int login() {

        Boundary.promptFieldInput(Constant.FIELD_EMAIL);
        String email = Input.acceptStringInput();

        if (email.equalsIgnoreCase(Constant.STRING_BACK)) {
            return Constant.CODE_BACK;
        }

        Boundary.promptFieldInput(Constant.FIELD_PASSWORD);
        String password = Input.acceptStringInput();

        if (password.equalsIgnoreCase(Constant.STRING_BACK)) {
            return Constant.CODE_BACK;
        }

        for (User user : userList) {
            if (email.equalsIgnoreCase(user.getEmail()) && password.equalsIgnoreCase(user.getPassword())) {
                //set current user
                currentUser = user;
                return Constant.OPERATION_SUCCESS;
            }
        }

        return Constant.OPERATION_FAIL;
    }

    /**
     * Method to handle user logout.
     */
    public void logout() {

        currentUser = null;
        Boundary.printOperationSuccess(Constant.OP_TYPE_LOGOUT);
    }

    /**
     * Add an order for specified customer
     *
     * @param customer             A Customer object.
     * @param order                An order object to be added to the customer's order list.
     */
    public void addOrder(Customer customer, Order order) {

        if (orderList.containsKey(customer)) {
            (orderList.get(customer)).add(order);
        } else {
            ArrayList<Order> orders = new ArrayList<>();
            orders.add(order);
            orderList.put(customer, orders);
        }
    }

    /**
     * Update user file's content with latest userList data.
     */
    public void updateUserFile() {
        StringBuilder fileContent = new StringBuilder();
        for (User user : userList) {
            fileContent.append(user.toString()).append("\n");
        }
        new FileIO(Constant.USER_FILE_PATH).writeFile(fileContent.toString());
    }

    /**
     * Update customer's credit information and add the new order after checkout.
     *
     * @param remainingCredit            The customer's remaining credits.
     * @param newOrder                   The newly created order by this customer.
     */
    public void updateCustomerInfoAfterCheckout(double remainingCredit, Order newOrder) {
        if (currentUser.isCustomer()){
            ((Customer)currentUser).setCredits(remainingCredit);
            addOrder( (Customer)currentUser, newOrder);
            updateUserFile();
        }
    }
}
