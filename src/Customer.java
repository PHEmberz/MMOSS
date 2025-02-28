import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Class which represents customer.
 *
 * @version ver1.0.0
 */
public class Customer extends User {

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String mobile;
    private String address;
    private String gender;
    private LocalDate memberExpireDate;
    private double credits;

    /**
     * Default constructor for Customer class.
     */
    public Customer() {
        super();
        this.firstName = "default";
        this.lastName = "default";
        this.birthday = LocalDate.of(1990,2,2);
        this.mobile = "default";
        this.address = "default";
        this.gender = "default";
        this.memberExpireDate = null;
        this.credits = 1000;
    }

    /**
     * Constructor for Customer class.
     *
     * @param email                      User email.
     * @param password                   User password.
     * @param firstName                  User first name.
     * @param lastName                   User last name.
     * @param birthday                   User birthday.
     * @param mobile                     User mobile.
     * @param address                    User address.
     * @param gender                     User gender.
     * @param memberExpireDate           User member expiry date.
     */
    public Customer(String email, String password, String firstName,
                    String lastName, LocalDate birthday, String mobile,
                    String address, String gender, LocalDate memberExpireDate) {
        super(email, password, Constant.USER_TYPE_CUSTOMER);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.mobile = mobile;
        this.address = address;
        this.gender = gender;
        this.memberExpireDate = memberExpireDate;
        this.credits = 1000;
    }

    /**
     * Constructor for Customer class.
     *
     * @param email                      Customer email.
     * @param password                   Customer password.
     * @param firstName                  Customer first name.
     * @param lastName                   Customer last name.
     * @param birthday                   Customer birthday.
     * @param mobile                     Customer mobile.
     * @param address                    Customer address.
     * @param gender                     Customer gender.
     * @param memberExpireDate           Customer member expiry date.
     * @param credits                    Customer credits.
     */
    public Customer(String email, String password, String firstName,
                    String lastName, LocalDate birthday, String mobile,
                    String address, String gender, LocalDate memberExpireDate,
                    double credits) {
        super(email, password, Constant.USER_TYPE_CUSTOMER);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.mobile = mobile;
        this.address = address;
        this.gender = gender;
        this.memberExpireDate = memberExpireDate;
        this.credits = credits;
    }

    /**
     * Getter method for firstName.
     *
     * @return                  The Customer's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter method for firstName.
     *
     * @param firstName         New first name to be set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter method for lastName.
     *
     * @return                  The Customer's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter method for lastName.
     *
     * @param lastName         New last name to be set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter method for birthday.
     *
     * @return                  The Customer's birthday.
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Setter method for birthday.
     *
     * @param birthday         New birthday to be set.
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Getter method for mobile.
     *
     * @return                  The Customer's mobile.
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Setter method for mobile.
     *
     * @param mobile         New mobile to be set.
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Getter method for address.
     *
     * @return                  The Customer's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for address.
     *
     * @param address         New address to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for gender.
     *
     * @return                The Customer's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Setter method for gender.
     *
     * @param gender         New gender to be set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Getter method for member expiry date.
     *
     * @return                The Customer's member expiry date.
     */
    public LocalDate getMemberExpireDate() {
        return memberExpireDate;
    }

    /**
     * Setter method for memberExpireDate.
     *
     * @param memberExpireDate         New member expiry date to be set.
     */
    public void setMemberExpireDate(LocalDate memberExpireDate) {
        this.memberExpireDate = memberExpireDate;
    }

    /**
     * Check whether a customer is a member.
     *
     * @return               A boolean representing the customer's membership.
     */
    public boolean isMember() {
        return memberExpireDate != null && memberExpireDate.isAfter(LocalDate.now());
    }

    /**
     * Getter method for credits.
     *
     * @return                The Customer's credits.
     */
    public double getCredits() {
        return credits;
    }

    /**
     * Setter method for credits.
     *
     * @param credits         New credits to be set.
     */
    public void setCredits(double credits) {
        this.credits = credits;
    }

    /**
     * Override the toString() method.
     *
     * @return                  The user's information in the specified format.
     */
    @Override
    public String toString() {
        return String.join(Constant.STRING_FIELD_DELIMITER, new String[]{super.toString(), firstName, lastName, Util.getStringFromDate(birthday),
                mobile, address, gender, Util.getStringFromDate(memberExpireDate), String.valueOf(credits)});
    }
}
