package kea.dat18i.firstyear.finalproject.biotrio.QRcodeGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Ticket;
import kea.dat18i.firstyear.finalproject.biotrio.entities.TicketReservationForm;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.CustomerRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.MovieRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.ShowingRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class QRwriter {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private ShowingRepository showingRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private TheatreRepository theatreRepo;

    @Autowired
    private CustomerRepository customerRepo;



    // Line separator to separate and properly format or QR messages
    private String n = System.lineSeparator();

    // Takes a TicketReservationForm parameter to store text
    // about all reserved Tickets inside a QR encoding
    public void writeQR(TicketReservationForm tickets) {

        // Create directory to store our QR code file and fetch it afterwards for emailing
        createDirForQR("QRdir");

        // Write our formatted String, which holds the information of a Customer's Ticket(s), into our QR code
        String QRmsg = createQRmsg(tickets);

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
    private String createQRmsg(TicketReservationForm tickets) {

        String t_one = formattedStr(tickets.getTicket1());
        String t_two = formattedStr(tickets.getTicket2());
        String t_three = formattedStr(tickets.getTicket3());
        String t_four = formattedStr(tickets.getTicket4());

        return String.format("%s %s %s %s", t_one, t_two, t_three, t_four);
    }

    // Insert a Ticket's data into a formatted String to prepare for writing to QR code
    private String formattedStr(Ticket ticket) {

        Showing showing = showingRepo.findShowingById(ticket.getShowing_id());
        Customer customer = customerRepo.findCustomer(ticket.getCustomer_id());

        // Format a part of our message
        String str = String.format(n + "Theatre: %s" + n +
                     "Movie: %s" + n +
                     "Date: %s" + n +
                     "Playing time: %s" + n +
                     "Customer name: %s %s" + n +
                     "Email: %s" + n +
                     "Phone number: %s" + n + n,
                     theatreRepo.findTheatreByShowingId(ticket.getShowing_id()).getName(),
                     movieRepo.findMovie(showing.getMovie_id()).getMovie_name(),
                     showing.getDate(),
                     showing.getTime(),
                     customer.getFirstName(),
                     customer.getLastName(),
                     customer.getEmail(),
                     customer.getPhoneNumber());

        return str;
    }


}
