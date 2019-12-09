/**
 * The Writer class is responsible for writing 
 * the data to the screen, text file, ad JSON file.
 * 
 * @author Monique Cauty
 * @author Antonio Elhakim
 * @author Dan Laskero
 * @author John Vercimak
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class Writer {

	/*******************************************************************************/
	/**
	 * toText() is responsible for writing and saving data 
	 * to a text file.
	 * 
	 * @param the ArrayList of data from webpage
	 * @param filename for saving
	 */
	public static void toText(ArrayList<String> data, File file) {
		try {
			FileWriter fw = new FileWriter(file);

			for (String string : data) {
				fw.write(string + System.lineSeparator());
			}
			fw.close();
		} catch (IOException ex) {
			Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*******************************************************************************/
	/**
	 * toScreen() is responsible for displaying the data to the screen
	 * via the text area.
	 * 
	 * @param ArrayList of data from webpage
	 * @param text area to write information to
	 */
	public static void toScreen(ArrayList<String> data, JTextArea area) {
		area.setText("");
		for (String string : data) {
			area.append(string + System.lineSeparator());
		}
	}

	/*******************************************************************************/
	/**
	 * toJson() is responsible for writing and saving information
	 * to a JSON file.
	 * @param ArrayList of data from webpage
	 * @param filename to save to
	 */
	public static void toJson(ArrayList<String> data, File file) {
		LinkedList<String> ll = new LinkedList<>();
		for (String string : data) {
			String[] sp = string.split("\\s+");
			for (String s : sp) {
				ll.add(s);
			}
		}
		Model m = new Model("Antonio", " hw9_final_project");
		m.parse(ll);
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(m.toString());            
			fw.close();
		} catch (IOException ex) {
			Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*******************************************************************************/
}
