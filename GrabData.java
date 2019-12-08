
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class GrabData {

    /**
     * 2.The java class or classes that grab the data from the website, create
     * objects from it, and store them in an ArrayList. (5 points)
     *
     * @param url
     * @return
     */
    public static ArrayList<String> grab(String url) {
        ArrayList<String> ll = new ArrayList<>();

        try {
            URL oracle = new URL(url);

            Scanner scanner = new Scanner(oracle.openStream());

            while (scanner.hasNext()) {
                ll.add(scanner.nextLine());
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(GrabData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GrabData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ll;
    }
}
