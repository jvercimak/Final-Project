/**
 * The GUI class is responsible for setting up
 * the user-friendly interface.
 * 
 * @author Monique Cauty
 * @author Antonio Elhakim
 * @author Dan Laskero
 * @author John Vercimak
 */

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GUI extends JFrame {

    private JTextArea content;
    private JTextField url;
    private ArrayList<String> al;

    public GUI() throws HeadlessException {
        super("Web Scrapper");
        menuBar();
        setLayout(new BorderLayout());
        content = new JTextArea();
        JScrollPane jsp = new JScrollPane(content);
        JPanel jp = new JPanel();

        add(jsp, BorderLayout.CENTER);

        urlBar();
        buttonBar();

        this.setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /******************************************************************/
    /**
     * buttonBar() is responsible for setting up the save
     * buttons/options for the user
     */
    private void buttonBar() {
        JPanel jp = new JPanel();
        JButton toText = new JButton("Save to Text");
        JButton toJson = new JButton("Save to Json");
        jp.add(toText);
        jp.add(toJson);
        add(jp, BorderLayout.SOUTH);

        toText.addActionListener((ActionEvent e) -> {
            if (al.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ther isn't any stored web");
            } else {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    Writer.toText(al, selectedFile);
                }
            }
        });

        toJson.addActionListener((ActionEvent e) -> {
            if (al.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ther isn't any stored web");
            } else {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    Writer.toJson(al, selectedFile);
                }
            }
        });

    }

    /******************************************************************/
    /**
     * urlBar() is responsible for setting up the URL address bar
     */
    private void urlBar() {
        JPanel top = new JPanel();
        top.add(new JLabel("Enter URL"));
        url = new JTextField(30);
        top.add(url);
        JButton jb = new JButton("Finish");
        top.add(jb);
        add(top, BorderLayout.NORTH);
        jb.addActionListener((ActionEvent e) -> {
            al = GrabData.grab(url.getText().trim());
            content.setText("");
            Writer.toScreen(al, content);
        });
    }

    /******************************************************************/
    /**
     * menuBar() sets up menu options such as "File", "Exit, "Help", etc.
     */
    private void menuBar() {
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        String aboutapp = "Homework 9:\n\nMonique Cauty\nAntonio Elhakim\nDan Laskero\nJohn Vercimak";
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        menu.add(menuItem);

        menu = new JMenu("Help");
        menuBar.add(menu);
        menuItem = new JMenuItem("About");
        menuItem.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, aboutapp);
        });
        menu.add(menuItem);

        this.setJMenuBar(menuBar);
    }
}
