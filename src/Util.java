import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Class which provides some util functionalities.
 *
 * @version ver1.0.0
 */public class Util {

    /**
     * Default constructor of Util class.
     */
     public Util(){

     }

    /**
     * Method to get a LocalDate object from a specific String representation.
     *
     * @param dateString            A String representing date in specific format.
     * @return                      A LocalDate object converted from the String.
     */
    public static LocalDate getDateFromString(String dateString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy M d");
        return LocalDate.parse(dateString, dateTimeFormatter);
    }

    /**
     * Method to get a LocalDate object from its ISO String representation.
     *
     * @param dateString            A String representing date in ISO format.
     * @return                      A LocalDate object converted from the String.
     */
    public static LocalDate getDateFromISOString(String dateString) {
        if (dateString.equalsIgnoreCase(Constant.STRING_NULL_DATE)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, dateTimeFormatter);
    }

    /**
     * Method to get the String representation of a LocalDate object.
     *
     * @param date                  A LocalDate object.
     * @return                      The date's String in ISO format.
     */
    public static String getStringFromDate(LocalDate date) {
        if (date == null) {
            return Constant.STRING_NULL_DATE;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(dateTimeFormatter);
    }

    /**
     * Method to format customer's name.
     *
     * @param firstName            The customer's first name.
     * @param lastName             The customer's last name.
     * @return                     Capitalized name.
     */
    public static String formatName(String firstName, String lastName) {
        return  capitalize(firstName) +
                " " +
                capitalize(lastName);
    }

    /**
     * Method to capitalize a string.
     *
     * @param input             A String argument.
     * @return                  Capitalized string.
     */
    public static String capitalize(String input) {
        if (!input.isEmpty())
            return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        else
            return "";
    }

    /**
     * Method to format a String for better presentation for empty able input.
     *
     * @param value              A String that can be empty.
     * @return                   Formatted string, if empty, replace with "Not specified".
     */
    public static String formatEmptyAbleValue(String value) {
        return  value.isEmpty() ? "Not specified" : value;
    }

    /**
     * Check whether a date String is in valid format.
     *
     * @param dateString                    A String.
     * @return                              Whether it is in valid format.
     */
    public static boolean isValidDateString(String dateString){
        try {
            getDateFromString(dateString);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Method to check whether a String is a double.
     *
     * @param input             The input string.
     * @return                  A boolean, true if argument can be
     *                          parsed as double.
     */
    public static boolean isDouble(String input)
    {
        try
        {
            double temp = Double.parseDouble(input);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Method to check whether a String is an integer.
     *
     * @param input             The input string.
     * @return                  A boolean, true if argument can be
     *                          parsed as int.
     */
    public static boolean isInteger(String input)
    {
        try
        {
            int temp = Integer.parseInt(input);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
