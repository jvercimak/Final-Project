/**
 * The GrabData class is responsible for "grabbing" data
 * from the webpage, adding data to an ArrayList.
 * 
 * @author Monique Cauty
 * @author Antonio Elhakim
 * @author Dan Laskero
 * @author John Vercimak
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrabData {

	/*******************************************************************************/
	/**
	 * grab() grabs the data from the webpage and adds
	 * to an ArrayList.
	 * 
	 * @param url for the webpage
	 * @return linked list of data
	 */
	public static ArrayList<String> grab(String url) {
		ArrayList<String> ll = new ArrayList<>();

		try {
			URL oracle = new URL(url);

			Scanner scanner = new Scanner(oracle.openStream());
			
			// add data to ArrayList here
			while (scanner.hasNext()) {
				ll.add(scanner.nextLine());
			}
			
		// catch the uglies
		} catch (MalformedURLException ex) {
			Logger.getLogger(GrabData.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GrabData.class.getName()).log(Level.SEVERE, null, ex);
		}
		return ll;
	}
	/*******************************************************************************/
}
