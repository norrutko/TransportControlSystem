//SMSProcessor.java
package sample;
import com.ericsson.hosasdk.api.TpAddress;
import com.ericsson.hosasdk.api.TpAddressPlan;
import com.ericsson.hosasdk.api.TpAddressRange;
import com.ericsson.hosasdk.api.TpAddressPresentation;
import com.ericsson.hosasdk.api.TpAddressScreening;
import com.ericsson.hosasdk.api.TpHosaDeliveryTime;
import com.ericsson.hosasdk.api.TpHosaMessage;
import com.ericsson.hosasdk.api.TpHosaSendMessageError;
import com.ericsson.hosasdk.api.TpHosaSendMessageReport;
import com.ericsson.hosasdk.api.TpHosaTerminatingAddressList;
import com.ericsson.hosasdk.api.TpHosaUIMessageDeliveryType;
import com.ericsson.hosasdk.api.hui.IpAppHosaUIManager;
import com.ericsson.hosasdk.api.hui.IpAppHosaUIManagerAdapter;
import com.ericsson.hosasdk.api.hui.IpHosaUIManager;
import com.ericsson.hosasdk.api.ui.IpAppUI;
import com.ericsson.hosasdk.api.ui.P_UI_RESPONSE_REQUIRED;
import com.ericsson.hosasdk.api.ui.TpUIEventCriteria;
import com.ericsson.hosasdk.api.ui.TpUIEventInfo;
import com.ericsson.hosasdk.api.ui.TpUIEventNotificationInfo;
import com.ericsson.hosasdk.api.ui.TpUIIdentifier;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

/**
 * Klasa odpowiedzialna za
 * <ul>
 * <li>Wysyłanie SMSów.</li>
 * <li>Obsługę przekazanie przychodzących SMSów do klasy Informer</li>
 * </ul>
 */
public class SMSProcessor extends IpAppHosaUIManagerAdapter implements IpAppHosaUIManager {
    private IpHosaUIManager itsHosaUIManager;
    private Sample parent;
    private Multimap<String,Subscriber> substribers;
    private TimerTask notifySubscribers;
    private final int subscriptionInterval = 5000;
    /**
     * @param aHosaUIManager
     * menadŜer słuŜacy do komunikacji z Ericsson Network Resource Gateway
     * @param aParent
     * Referencja do klasy-rodzica Informer, która przekazuje klasie SMSProcessor wiadomości do
     * wysłania i przetwarza odbierane wiadomości
     */
    public SMSProcessor(IpHosaUIManager aHosaUIManager, Sample aParent) {
        itsHosaUIManager = aHosaUIManager;
        this.parent = parent;
        this.substribers = HashMultimap.create();
        notifySubscribers = new TimerTask() {
            @Override
            public void run() {
                for (Subscriber receiver : substribers.get("2222")) {
                    double rand = new Random().nextDouble();
                    receiver.setFuel(receiver.getFuel()-rand);
                    sendSMS("2222", receiver.getNumber(), "Wiadomosc do subskrybenta " + receiver.getNumber()
                            + ".Stan paliwa: " + receiver.getFuel() + " litry/litra.");
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(notifySubscribers, 0, subscriptionInterval);
    }

    /**
     * Funkcja dodaje nasłuch przychodzacych wiadomości dla podanego numeru
     * (użytkownik o tym numerze nie musi istnieć, może to być numer usługi)
     * @param sDest
     * Adres (numer) uŜytkownika, dla którego utworzona jest
    notyfikacja
     * @return przydzielone ID notyfikacji
     */
    public int startNotifications(String sDest) {
        //interfejs obsługujący zdarzenia pochodzące od bramki, w tym wypadku implementuje go klasa SMSProcessor
        IpAppHosaUIManager appHosaUIManager = this;
        //obiekt reprezentujący adres nadawcy wiadomości - w tym wypadku brak nadawcy
        TpAddressRange originatingAddress =
                new TpAddressRange(TpAddressPlan.P_ADDRESS_PLAN_UNDEFINED,
                        "*", "", "");

        //obiekt reprezentujący adres nadawcy wiadomości - w tym wypadku dowolny nadawca
        TpAddressRange destinationAddress =
                new TpAddressRange(TpAddressPlan.P_ADDRESS_PLAN_E164,
                        sDest, "", "");
        //kod usługi - 00 oznacza wiadomości SMS (np. dla MMS kod - 01)
        String serviceCode = "00";

        //kryterium uruchomienia obsługi zdarzenia - przyszła wiadomość SMS na numer destinationAddress od dowolnego numeru
        TpUIEventCriteria criteria = new
                TpUIEventCriteria(originatingAddress,
                destinationAddress, serviceCode);

        //utworzenie powiadamiania - przekazanie kryteriów i klasy implementującej interfejs obsługi zdarzeń
        int assignmentId = itsHosaUIManager.createNotification(
                appHosaUIManager, criteria);

        return assignmentId;
    }
    /**
     * Usunięcie notyfikacji
     * @param anAssignmentId
     * ID notyfikacji do usunięcia (zwróceone przez
     * @see //messaging.MMSProcessor#startNotifications(String)
    startNotifications )
     */
    public void stopNotifications(int anAssignmentId) {
        itsHosaUIManager.destroyNotification(anAssignmentId);
    }
    /**
     * Wywoływana przez Ericsson Network Resource Gateway w chwili otrzymania
     wiadomości
     *
     * @see com.ericsson.hosasdk.api.hui.IpAppHosaUIManager
     */
    public IpAppUI reportEventNotification(TpUIIdentifier anUserInteraction,
                                           TpUIEventNotificationInfo anEventInfo, int anAssignmentID) {
        String sender = anEventInfo.OriginatingAddress.AddrString;
        String receiver = anEventInfo.DestinationAddress.AddrString;
        String messageContent = new String(anEventInfo.UIEventData);
        //odesłanie wiadomości ,,Witaj świecie'' do nadawcy
        this.sendSMS(receiver, sender, "Witaj swiecie");
        return null;
    }
    /**
     * Wywoływana przez Ericsson Network Resource Gateway w chwili otrzymania
     wiadomości
     *
     * @see com.ericsson.hosasdk.api.hui.IpAppHosaUIManager
     * @deprecated Od wersji standardu 6.0 funkcja nie będzie wspierana, zamiast niej należy używać * @see
    messaging.MMSProcessor#reportEventNotification(TpUIIdentifier,
    TpUIEventNotificationInfo,int) reportEventNotification . Dla zapewnienia
    zdgoności wstecznej została zaimplementowana.
     */
    public IpAppUI reportNotification(TpUIIdentifier anUserInteraction,
                                      TpUIEventInfo anEventInfo, int anAssignmentID) {
        //pobranie danych zdarzenia - nadawcy, odbiorcy i treści wiadomości
        String sender = anEventInfo.OriginatingAddress.AddrString;
        String receiver = anEventInfo.DestinationAddress.AddrString;
        String messageContent = anEventInfo.DataString;


        /*//odesłanie wiadomości ,,Witaj świecie'' do nadawcy
        this.sendSMS(receiver, sender, "Witaj swiecie");
        return null;*/

        subscribersManagment(sender,receiver,messageContent);
        return null;
    }

    //todo ogarnia obsluge wiadomosci kierowcow do bramek
    public void subscribersManagment(String sender, String receiver, String messageContent) {
        String reply = "";
        if (receiver.equals("1111")) { //todo kierowca wyslal sms na bramke 1111 odpowiedzialna za zarzadzanie flota
            Subscriber subscriber = findSubsciber("1111",sender); //todo szukanie subskrypcji kierowcy na bramce 1111
            //todo wejdzie jesli wiadomosc o tresci start i subskrypcji jeszcze nie ma
            if (messageContent.equals("start") &&  subscriber==null) {
                reply = "Dodano pojazd do floty.";
                substribers.put(receiver, new Subscriber(sender)); //todo dodanie kierowcy do floty (subskrypcja na 1111)
            }
            //todo wejdzie jesli wiadomosc o tresci stop i subskrypcja istnieje
            else if (messageContent.equals("stop") && subscriber!=null) {
                reply = "Usunieto pojazd z floty.";
                for (String g : substribers.keySet()) {
                    substribers.remove(g,subscriber); //todo wywalenie kierowcy z floty (z subskrypcji na 1111)
                }
            }
        }
        else if (receiver.equals("2222")) { //todo kierowca wyslal sms na bramke 2222 odpowiedzialna za okresowy raporty o paliwie
            Subscriber fleetMember = findSubsciber("1111",sender); //todo szukanie subskrypcji kierowcy na bramce 1111
            Subscriber fuelSubscriber = findSubsciber("2222",sender); //todo szukanie subskrypcji kierowcy na bramce 2222
            //todo wejdzie jesli wiadomosc o tresci start, kierowca jest we flocie ale nie subskrybuje infa o paliwie
            if (messageContent.equals("start") && fleetMember!=null && fuelSubscriber==null) {
                reply = "Raporty na temat stanu paliwa beda otrzymywane raz na " + subscriptionInterval/1000 + " sekund.";
                substribers.put(receiver, fleetMember); //todo dodanie kierowcy do subskrypcji infa o paliwie (na 2222)
            }
            //todo wejdzie jesli wiadomosci o tresci stop, kierowca subskrybuje info o paliwie. warunek o czlonkowstwie
            //todo zbedny bo napewno jest we flocie
            else if (messageContent.equals("stop") && fuelSubscriber!=null) {
                reply = "Raporty na temat stanu paliwa zostaly anulowane.";
                substribers.remove(receiver,fuelSubscriber); //todo wywalanie kierowcy z subskrypcji o stanie paliwa
            }
        }
        if (!reply.equals("")) { //todo jesli przygotowalismy jakas wiadomosc zrotna
            this.sendSMS(receiver,sender,reply); //todo wyslanie wiadomosci zwrotnej
        }
    }

    //todo szuka subskrypcji kierowcy o numerze "subscriber" na bramce "gate"
    private Subscriber findSubsciber(String gate, String subscriber) {
        for (Subscriber r : substribers.get(gate)) {
            if (r.getNumber().equals(subscriber)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Wysłanie SMSa
     *
     * @param aSender
     * nadawca
     * @param aReceiver
     * odbiorca
     * @param aMessageContent
     * zawartość
     */
    public void sendSMS(String aSender, String aReceiver,
                        String aMessageContent) {
        IpAppHosaUIManager appHosaUIManager = this;
        //typ wiadomości: SMS
        TpHosaUIMessageDeliveryType deliveryType = TpHosaUIMessageDeliveryType.P_HUI_SMS;
        // Bez opóźnienia
        TpHosaDeliveryTime deliveryTime = new TpHosaDeliveryTime();
        deliveryTime.Dummy((short) 0);
        //adres nadawcy
        TpAddress originatingAddress = createTpAddress(aSender);
        //adres odbiorcy
        TpAddress destinationAddress = createTpAddress(aReceiver);
        //lista odbiorców, w tym wypadku zawierająca tylko jeden adres
        TpHosaTerminatingAddressList recipients = new TpHosaTerminatingAddressList();
        recipients.ToAddressList = new TpAddress[] { destinationAddress };
        //treść wiadomości
        TpHosaMessage message = new TpHosaMessage();
        message.Text(aMessageContent);
        //zażądanie wysłania wiadomości
        itsHosaUIManager.hosaSendMessageReq(appHosaUIManager, // callback
                originatingAddress, //nadawca
                recipients, //odbiorcy
                null, // bez tematu
                message, //treść wiadomości
                deliveryType, //rodzaj wiadomości (SMS)
                null,
                P_UI_RESPONSE_REQUIRED.value, false,
                deliveryTime,
                "");
    }
    /**
     * Wywoływana przez Ericsson Network Resource Gateway, gdy wystąpił bład
     przy wysyłaniu wiadomości
     *
     * @see com.ericsson.hosasdk.api.hui.IpAppHosaUIManager
     */
    public void hosaSendMessageErr(int anAssignmentID,
                                   TpHosaSendMessageError[] anErrorList) {
        System.out.println("\nError sending the SMS to "
                + anErrorList[0].UserAddress.AddrString + "(ErrorCode "
                + anErrorList[0].Error.value() + ")");
    }
    /**
     * Wywoływana przez Ericsson Network Resource Gateway, gdy wysyłanie
     wiadomości zakończyło się poprawnie
     *
     * @see com.ericsson.hosasdk.api.hui.IpAppHosaUIManager
     */
    public void hosaSendMessageRes(int anAssignmentID,
                                   TpHosaSendMessageReport[] aResponseList) {
        System.out.println("\nSMS Message sent to "
                + aResponseList[0].UserAddress.AddrString);
    }
    public static TpAddress createTpAddress(String aNumber) {
        return new TpAddress(TpAddressPlan.P_ADDRESS_PLAN_E164, aNumber, //addres
                "", // name
                TpAddressPresentation.P_ADDRESS_PRESENTATION_UNDEFINED,
                TpAddressScreening.P_ADDRESS_SCREENING_UNDEFINED, "");// podadres
    }
}