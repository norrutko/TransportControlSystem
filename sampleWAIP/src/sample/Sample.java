//Sample.java
package sample;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.HashMap;
import java.io.*;
import javax.swing.ImageIcon;
import org.csapi.*;
import com.ericsson.hosasdk.api.HOSAMonitor;
import com.ericsson.hosasdk.api.fw.P_UNKNOWN_SERVICE_TYPE;
import com.ericsson.hosasdk.api.hui.IpHosaUIManager;
import com.ericsson.hosasdk.api.mm.ul.IpUserLocation;
import com.ericsson.hosasdk.utility.framework.FWproxy;
public class Sample {
    private FWproxy itsFramework;
    private IpHosaUIManager itsHosaUIManager;
    private IpUserLocation itsOsaULManager;
    public SMSProcessor smsProcessor;
    public GUI theGUI;
    public Sample(GUI aGUI) {
        theGUI = aGUI;
        aGUI.setTitle("Monitoring floty samochodowej");
    }
    protected void start() {
        Properties p = new Properties();
        try{
            p.load(new FileInputStream("./ini/nrg.ini"));
            itsFramework = new FWproxy(p);
            itsHosaUIManager = (IpHosaUIManager) itsFramework
                    .obtainSCF("SP_HOSA_USER_INTERACTION");
        }
        catch (P_UNKNOWN_SERVICE_TYPE anException){
            System.err.println("Nie odnaleziono usługi"+ anException);
            System.exit(-1);
        }
        catch(IOException e){
            System.err.println("Nie można otworzyć pliku konfiguracyjnego: "+
                    e);
        }
        smsProcessor = new SMSProcessor(itsHosaUIManager, this);
        smsProcessor.startNotifications("2222");
        theGUI.addText("Aplikacja połączona");
    }
    public void stop() {
        if (smsProcessor != null) {
            smsProcessor.dispose();
        }
        System.out.println("Disposing service manager");
        if (itsHosaUIManager != null) {
            itsFramework.releaseSCF(itsHosaUIManager);
        }
        if (itsOsaULManager != null) {
            itsFramework.releaseSCF(itsOsaULManager);
        }
        System.out.println("Disposing framework");
        if (itsFramework != null) {
            itsFramework.endAccess();
            itsFramework.dispose();
        }
        System.exit(0);
    }
}