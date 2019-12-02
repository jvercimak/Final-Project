import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class DrawingPanel extends JPanel {

}
public class Web extends JFrame {
    private String textToShow; // this is what the text area will show
    private JTextArea txaWords;  // known throughout the class
    public void setupUI() {
        textToShow = "";
        setTitle("Web Scraper");
        setBounds(100,100,500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        JPanel panNorth = new JPanel();
        panNorth.setLayout(new FlowLayout());
        JLabel label = new JLabel("Enter URL:");
        JTextField txtTextToAdd = new JTextField(20); 
        JButton btnAddText = new JButton("Fetch");
        JButton btnSouthw = new JButton("Save as text");
        c.add(btnSouthw,BorderLayout.SOUTH);
        JButton btnSouthe = new JButton("Save as JSON");
        c.add(btnSouthe,BorderLayout.SOUTH);
        /* the JButton when clicked will take the text in the text field and
         * add it to textToShow. Then it will to the text area to set its 
         * text to it.
         */
        btnAddText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = txtTextToAdd.getText();
                textToShow = textToShow + "\n" + text;
                txaWords.setText(textToShow);
            }
        });
        panNorth.add(label);
        panNorth.add(txtTextToAdd);
        panNorth.add(btnAddText);
        c.add(panNorth,BorderLayout.NORTH);
        txaWords = new JTextArea();
        txaWords.setEditable(false);
        c.add(txaWords,BorderLayout.CENTER);
    }
    public Web() {
        setupUI();
    }
    public static void main(String[] args) {
        Web demo = new Web();
        demo.setVisible(true);
    }
}