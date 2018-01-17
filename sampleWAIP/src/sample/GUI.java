//GUI.java
package sample;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
public class GUI extends JFrame {
    private JTextArea text ;
    private JPanel itsButtons = new JPanel();
    public GUI() {
        itsButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JPanel contents = new JPanel();
        contents.setLayout(new BorderLayout());
        contents.add(this.createTextArea(), BorderLayout.CENTER);
        contents.add(itsButtons, BorderLayout.SOUTH);
        contents.setBorder(new EmptyBorder(8, 8, 0, 8));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contents, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
    public void showCentered() {
        pack();
        Dimension guiSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - guiSize.width) / 2,
                (screenSize.height - guiSize.height) / 2);
        setVisible(true);
    }
    public JTextArea createTextArea() {
        text = new JTextArea(10, 25);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setBorder(new EmptyBorder(4, 8, 4, 8));
        return text;
    }
    public void addText(String stext){
        text.setText(stext);
    }
    public void addButton(Action anAction) {
        itsButtons.add(new JButton(anAction));
    }
}