import java.util.Scanner;

/**
 * Class which gets specific types of user inputs.
 *
 * @author Dongqi Li
 * @version ver1.0.0
 */
public class Input
{
    /**
     * Default constructor which creates the object of class Input.
     *
     */
    public Input()
    {

    }

    /**
     * Method to display a prompt message to user and get a character 
     * as user input.
     *
     * @param   position        The index of the fetched character.   
     * @return                  A character representing the user input.
     */
    public static char acceptCharacterInput(int position)
    {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        try
        {
            input = scanner.nextLine().trim();
            char temp = input.charAt(position);
            return temp;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("User input " + input +
                    " is less than " +
                    (position + 1) +
                    " in length.\n" +
                    "acceptCharacterInput " +
                    "failed.");
        }
    }

    /**
     * Method to display a prompt message to user and get a double-point 
     * number as user input.
     *
     * @return                  A double-point number representing the 
     *                          user input.
     */
    public static double acceptDoubleInput()
    {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        try
        {
            input = scanner.nextLine().trim();
            double temp = Double.parseDouble(input);
            return temp;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("User input " + input +
                    " cannot be parsed as " +
                    "a double.\n" +
                    "acceptDoubleInput " +
                    "failed.");
        }
    }

    /**
     * Method to display a prompt message to user and get an integer 
     * as user input.
     *
     * @return                  An integer representing the user input.
     */
    public static int acceptIntegerInput()
    {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        try
        {
            input = scanner.nextLine().trim();
            int temp = Integer.parseInt(input);
            return temp;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("User input " + input +
                    " cannot be parsed as " +
                    "an integer.\n" +
                    "acceptIntegerInput " +
                    "failed.");
        }
    }

    /**
     * Method to display a prompt message to user and get a string 
     * as user input.
     *
     * @return                  A string representing the user input.
     */
    public static String acceptStringInput()
    {
        Scanner scanner = new Scanner(System.in);
        String temp = scanner.nextLine();
        return temp.trim();
    }
}