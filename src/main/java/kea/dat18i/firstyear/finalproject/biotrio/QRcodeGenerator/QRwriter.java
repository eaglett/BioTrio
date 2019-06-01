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
import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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


    // Line separator to separate and properly format our QR messages
    private String n = System.lineSeparator();

    // Takes a Ticket parameter to store text
    // about a reserved Ticket inside a QR encoding
    public void writeQR(Ticket ticket, int showing_id, Customer customer) {

        // Create directory to store our QR code file and fetch it afterwards for emailing
        createDirForQR("QRdir");

        // Write our formatted String, which holds the information of a Customer's Ticket, into our QR code
        String QRmsg = createQRmsg(ticket, showing_id, customer);

        try {
            String filePath = "QRdir\\QRCODE5_BioTrioTicket.png";
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map < EncodeHintType, ErrorCorrectionLevel > hintMap = new HashMap < EncodeHintType, ErrorCorrectionLevel > ();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(QRmsg.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                    .lastIndexOf('.') + 1), new File(filePath));
            System.out.println("QR Code image created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("QR code creation malfunctioned");
        }

    }

    // Creates a new directory to store our QR code files into
    private void createDirForQR(String dirName) {
        File dir = new File(dirName);
        if(!dir.exists()) {
            dir.mkdir();
            System.out.println("Directory " + dirName + " has been created!");
        }
    }


    // Prepare a formatted QR message to write to our QR code
    // Insert a Ticket's data into a formatted String to prepare for writing to QR code
    private String createQRmsg(Ticket ticket, int showing_id, Customer c) {

        Showing showing = showingRepo.findShowingById(showing_id);
        Customer customer = customerRepo.findCustomer(c.getId());

        // Format a part of our message
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
