import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class DrawingPanel extends JPanel {

}
public class Web extends JFrame {
    private String textToShow; 
    private JTextArea txaWords;  
    public void setupUI() {
        textToShow = "";
        setTitle("Web Scraper");
        setBounds(200,200,700,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        JPanel panNorth = new JPanel();
        panNorth.setLayout(new FlowLayout());
        JLabel label = new JLabel("Enter URL:");
        JTextField txtTextToAdd = new JTextField(20); 
        JButton btnAddText = new JButton("Fetch");
        JButton btnSText = new JButton("Save as text");
        JButton btnSJson = new JButton("Save as JSON");
        JPanel panButtons = new JPanel();
        panButtons.add(btnSText);
        panButtons.add(btnSJson);
        c.add(panButtons,BorderLayout.SOUTH);
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
