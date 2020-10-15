import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.MalformedURLException;

public class SCChallengeEmail {
    public static void main(String[] args) throws Exception {
        /*String emailID;
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Email ID: ");

        try {
            emailID = bufReader.readLine();

            bufReader.close();
        }
        catch (IOException e) {
            System.out.println("Error with user input" + e.getMessage());
        }*/

        String emailID = getEmailID();
        if (emailID.equals("error"))
        {
            System.exit(0);
        }

        String webAddress = getWebAddress(emailID);

        //URL webPageURL = getURL(webAddress);
        String pageLine = getLineFromPage(webAddress);

        //readFromURL(webPageURL);

        extractName(pageLine);
    }

    // Step 1 - Gets an email ID input from System.in using a BufferedReader object
    private static String getEmailID() {
        String emailID;
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Email ID: ");

        try {
            emailID = bufReader.readLine();

            bufReader.close();
            return emailID;
        }
        catch (IOException e) {
            System.out.println("Error with user input" + e.getMessage());
            return "error";
        }
    }

    // Step 2 - Concatenates to put it in the form of a web address
    private static String getWebAddress(String emailID) {
        String webPageAddress = "https://www.ecs.soton.ac.uk/people/" + emailID;
        return webPageAddress;
    }

    // Step 3 - Creates a URL from the String web address
    private static String getLineFromPage(String webPageAddress) throws Exception {
        try {
            URL webURL = new URL(webPageAddress);
            return readFromURL(webURL);
        }
        catch (MalformedURLException e)
        {
            System.exit(0);
            return "";
        }
    }

    // Step 4/5 - Create a BufferedReader object that can read from the URL & Then save the 
    private static String readFromURL(URL webURL) throws Exception {
        BufferedReader bufReaderURL = new BufferedReader(new InputStreamReader(webURL.openStream()));

        String pageLine = "";
        boolean found = false;
        try {
            while (!found && (pageLine = bufReaderURL.readLine()) != null) {
                if (pageLine.contains("property=\"name\"")) {
                    //System.out.println(pageLine);
                    found = true;
                }
            }
            bufReaderURL.close();
            return pageLine;
        }
        catch (IOException e) {
            bufReaderURL.close();
            System.exit(0);
            return "";
        }
    }

    // Step 6/7 - Find and extract the name from the line then print it
    private static void extractName(String webText) {
        int namePropertyLocation = webText.indexOf("property=\"name\"");
        int offset = 16;
        int nameLocation = namePropertyLocation + offset;
        
        String substringFromName = webText.substring(nameLocation);
        //System.out.println(substringFromName);

        int nameEnd = substringFromName.indexOf('<');
        String name = substringFromName.substring(0, nameEnd);
        System.out.println(name);
    }
}