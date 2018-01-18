//Main.java
package sample;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
public class Main {
    private Application application;
    public static void main(String[] args) throws Exception {
        new Main();
    }
    public Main() throws IOException {
        GUI gui = new GUI();
        gui.addButton(new AbstractAction("Start") {
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        application = new Application(gui);
        gui.addButton(new AbstractAction("Stop") {
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        gui.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                terminate();
            }
        });
        gui.showCentered();
    }
    public void start() {
        try {
            application.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        try {
            application.stop();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Terminates the application.
     */
    public void terminate() {
        System.exit(0);
    }
} 