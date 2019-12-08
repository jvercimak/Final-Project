
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author
 */
public class Writer {

    /**
     * 4.A Writer class that contains functions that write the data to the
     * screen (2 points) to a text file (2points), and to a JSON file (2 points)
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

    public static void toScreen(ArrayList<String> data, JTextArea area) {
        area.setText("");
        for (String string : data) {
            area.append(string + System.lineSeparator());
        }
    }

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

}
