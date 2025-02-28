import java.util.ArrayList;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
/**
 * Class which provides text file operations including read and write.
 *
 * @author Dongqi Li
 * @version ver1.0.0
 */
public class FileIO
{
    private String fileName;

    /**
     * Default constructor which creates the object of class FileIO.
     *
     */
    public FileIO()
    {
        fileName = "Unknown";
    }

    /**
     * Non-default constructor which creates the object of class FileIO.
     *
     * @param fileName      The file's name to read or write content with
     *                      as a String.  
     */
    public FileIO(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * Accessor method to get the file's name.
     *
     * @return          A String representing the file's name.
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * Method to read all the file content and return it.
     *
     * @return          A String containing the content of the file.
     */
    public String readFile()
    {
        StringBuilder linesContent = new StringBuilder();
        try
        {
            FileReader reader = new FileReader(fileName);
            Scanner scanner = new Scanner(reader);
            try
            {
                while (scanner.hasNextLine())
                    linesContent.append(scanner.nextLine()).append("\n");

            }
            catch (Exception e)
            {
                Boundary.alertFileReadError();
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    Boundary.alertFileReadError();
                }
            }
        }
        catch (IOException e)
        {
            Boundary.alertFileNotFound(fileName);
        }
        catch (Exception e)
        {
            Boundary.alertFileReadError();
        }
        finally
        {
            return linesContent.toString();
        }
    }

    /**
     * Mutator method to set the file's name.
     *
     * @param fileName       A file's name as a String.    
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * Formats the state of the object to optimize the display
     *
     * @return           A string representing the state of the object.
     */
    public String toString()
    {
        return "File Name: " + fileName + "\n";
    }

    /**
     * Method to write the designated content to the file.
     *
     * @param content    The content to be written as a String.
     */
    public void writeFile(String content)
    {
        try
        {
            PrintWriter writer = new PrintWriter(fileName);
            try
            {
                writer.println(content);

            }
            catch (Exception e)
            {
                Boundary.alertFileWriteError();
            }
            finally
            {
                try
                {
                    writer.close();
                }
                catch (Exception e)
                {
                    Boundary.alertFileWriteError();
                }
            }
        }
        catch (Exception e)
        {
            Boundary.alertFileWriteError();
        }
    }
}