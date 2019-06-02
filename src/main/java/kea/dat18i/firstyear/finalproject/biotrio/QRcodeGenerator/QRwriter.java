package kea.dat18i.firstyear.finalproject.biotrio.QRcodeGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Ticket;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.CustomerRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.MovieRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.ShowingRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Handles creation of a QR code as well as formatting of a Ticket object's information into a proper String
 * to insert into our QR code ticket.
 * Annotated with the Spring annotation @Component to connect the class to our Spring Web Application
 */
@Component
public class QRwriter {

    @Autowired
    private ShowingRepository showingRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private TheatreRepository theatreRepo;

    @Autowired
    private CustomerRepository customerRepo;


    /**
     * Line separator to separate and properly format our QR code messages
     */
    private String n = System.lineSeparator();

    /**
     *
     *  Takes a Ticket and Customer parameter to store text about a reserved
     *  Ticket and it's corresponding Customer inside a QR encoding
     *
     * @param ticket(Ticket)
     * @param showing_id(int)
     * @param customer(Customer)
     */
    public void writeQR(Ticket ticket, int showing_id, Customer customer) {

        // Create directory to store our QR code file and fetch it afterwards for emailing
        createDirForQR("QRdir");

        // Write our formatted String, which holds the information of a Customer's Ticket, into our QR code
        String QRmsg = createQRmsg(ticket, showing_id, customer);

        // Creating a QR code file and inserting text into it
        try {
            String filePath = "QRdir\\QRCODE5_BioTrioTicket.png"; // File to write QR image to
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map < EncodeHintType, ErrorCorrectionLevel > hintMap = new HashMap < EncodeHintType, ErrorCorrectionLevel > ();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(QRmsg.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                    .lastIndexOf('.') + 1), new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates a new directory to store our QR code files into
     * if the directory does not already exit
     *
     * @param dirName(String) name for directory
     */
    private void createDirForQR(String dirName) {
        File dir = new File(dirName);
        if(!dir.exists()) {
            dir.mkdir();
        }
    }


    /**
     * Prepare a formatted QR message to write to our QR code
     * Insert a Ticket's data into a formatted String to prepare for writing to QR code
     *
     * @param ticket(Ticket)
     * @param showing_id(int)
     * @param c(Customer)
     * @return QRmsg(String)
     */
    private String createQRmsg(Ticket ticket, int showing_id, Customer c) {

        // Find the correct Showing's information by showing_id and store it into a Showing object
        Showing showing = showingRepo.findShowingById(showing_id);

        // Find the correct Customer's information by customer_id and store it into a Customer object
        Customer customer = customerRepo.findCustomer(c.getId());

        // Format our QR code ticket text
        String QRmsg = String.format(n + "Theatre: %s" + n +
                     "Movie: %s" + n +
                     "Date: %s" + n +
                     "Playing time: %s" + n +
                     "Row: %s" + n +
                     "Seat: %s" + n +
                     "Customer name: %s %s" + n +
                     "Email: %s" + n +
                     "Phone number: %s" + n + n,
                     theatreRepo.findTheatreByShowingId(showing_id).getName(),
                     movieRepo.findMovie(showing.getMovie_id()).getMovie_name(),
                     showing.getDate(),
                     showing.getTime(),
                     ticket.getSeat_row(),
                     ticket.getSeat_nb(),
                     customer.getFirstName(),
                     customer.getLastName(),
                     customer.getEmail(),
                     customer.getPhoneNumber());

        return QRmsg;
    }


}
