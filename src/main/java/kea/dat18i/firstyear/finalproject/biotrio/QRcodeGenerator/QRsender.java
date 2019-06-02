package kea.dat18i.firstyear.finalproject.biotrio.QRcodeGenerator;

import org.springframework.stereotype.Component;

// Twilio SMS package
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

// javax package for emailing
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Handles the sending of a QRcode .png file through email or plain text through SMS.
 * Annotated with the Spring annotation @Component to connect the class to our Spring Web Application
 */
@Component
public class QRsender  {


    /**
     * Line separator to separate and properly format our SMS messages
     */
    private String n = System.lineSeparator();

    /**
     *  Find your Account Sid and Token at twilio.com/user/account
     *  Used to connect our web application to our Twilio Account for sending SMS messages
     */
    private static final String ACCOUNT_SID = "AC6002544ad3a247125120e3216feda403";
    private static final String AUTH_TOKEN = "195df714a85710478833e829f001638d";


    /**
     * Connecting to Gmail account which will send emails containing a QR code ticket to our customers
     */
    private static String USER_NAME = "Moomooblinks@gmail.com";  // GMail username
    private static String PASSWORD = "Dunno00pass?"; // GMail password
    private static final String SMTP_HOST_NAME = "smtp.gmail.com"; // Host for our electronic mail transmission communication protocol


    /**
     * Sends an email containing our QR code ticket to one or multiple recipients' email-addresses
     *
     * @param to(String[]) contains email-addresses of our recipients
     * @param attachment name of the file which we are sending as an email attachment
     */
    public void sendEmail(String[] to, String attachment) {

        // Set Mail server properties
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        // Create Session object to interact with the mail host - smtp.gmail.com
        Session session = Session.getDefaultInstance(props);

        // Create MimeMessage object that contains our email text and attachments
        MimeMessage message = new MimeMessage(session);

        try {
            // Prepare message from our static email address Moomooblinks@gmail.com
            message.setFrom(new InternetAddress(USER_NAME));

            // Prepare to send message to recipients' emails
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(javax.mail.Message.RecipientType.TO, toAddress[i]);
            }

            // Create MimeMessage's body
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message body text
            messageBodyPart.setText("Ticket Reservation - Sent from Bio Trio");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Prepares our attachment and stores it into the email body
            // IMPORTANT - message.setSubject holds our attachment and needs to be called
            // after messageBodyPart.setText, other the email body-text will overwrite our attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "QRdir\\QRCODE5_BioTrioTicket.png";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Must be called after text has been written into the email body
            message.setContent(multipart);
            message.setSubject(attachment);

            // Models an email message for transport and closes the transport once
            // the email has been sent to the recipient(s)
            Transport transport = session.getTransport("smtp");
            transport.connect(SMTP_HOST_NAME, USER_NAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }


    /**
     * Sends an SMS of an example Bio Trio ticket to a phonenumber verified
     * in our Twilio Account - works only with one phone number as we have a Trial Twilio Account
     */
    public void sendSMS() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber("+4553634060"), new PhoneNumber("+13304002981"),
                "Just testing to see if we can send SMS with java for our QR code!" + n +
                     "Theatre: Red Theatre" + n +
                     "Movie: Avengers: Endgame" + n +
                     "Row: 4" + n +
                     "Seat 7" + n +
                     "Phone number: +yyxxxxxxxx").create();
    }
}
