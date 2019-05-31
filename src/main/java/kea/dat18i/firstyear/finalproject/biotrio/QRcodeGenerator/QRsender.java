package kea.dat18i.firstyear.finalproject.biotrio.QRcodeGenerator;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Ticket;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.CustomerRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.MovieRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.ShowingRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.TheatreRepository;
import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

// Handles the sending of a QRcode .png file through email or SMS
@Component
public class QRsender  {


    // Line separator to separate and properly format our SMS messages
    private String n = System.lineSeparator();

    // Find your Account Sid and Token at twilio.com/user/account
    private static final String ACCOUNT_SID = "AC6002544ad3a247125120e3216feda403";
    private static final String AUTH_TOKEN = "195df714a85710478833e829f001638d";


    // Connecting to Gmail account
    private static String USER_NAME = "Moomooblinks@gmail.com";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "Dunno00pass?"; // GMail password
    private static final String SMTP_HOST_NAME = "smtp.gmail.com"; // Host


    public void sendEmail(String[] to, String attachment) {

        // Set Mail server properties
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        //session.setDebug(true);

        try {
            message.setFrom(new InternetAddress(USER_NAME));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(javax.mail.Message.RecipientType.TO, toAddress[i]);
            }

            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText("Ticket Reservation - Sent from Bio Trio");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "QRdir\\QRCODE5_BioTrioTicket.png";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            message.setSubject(attachment);

            Transport transport = session.getTransport("smtp");
            transport.connect(SMTP_HOST_NAME, USER_NAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
            System.out.println("Problem in emailing 1");
        }
        catch (MessagingException me) {
            me.printStackTrace();
            System.out.println("Problem in emailing 2");
        }
        System.out.println("Email Attachment has been sent");
    }



    public void sendSMS() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber("+4553634060"), new PhoneNumber("+13304002981"),
                "Just testing to see if we can send SMS with java for our QR code!" + n +
                     "Theatre: Red Theatre" + n +
                     "Movie: Avengers: Endgame" + n +
                     "Row: 4" + n +
                     "Seat 7" + n +
                     "Phone number: +yyxxxxxxxx").create();
        System.out.println(message.getSid());
    }
}
