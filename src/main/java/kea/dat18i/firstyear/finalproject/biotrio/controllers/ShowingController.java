package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.QRcodeGenerator.QRsender;
import kea.dat18i.firstyear.finalproject.biotrio.QRcodeGenerator.QRwriter;
import kea.dat18i.firstyear.finalproject.biotrio.entities.*;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.*;
import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


@Controller
public class ShowingController {

    @Autowired
    ShowingRepository showingRepo;

    @Autowired
    MovieRepository movieRepo;

    @Autowired
    TheatreRepository theatreRepo;

    @Autowired
    TicketRepository ticketRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    private QRwriter qRwriter = new QRwriter();

    @Autowired
    private QRsender qRsender = new QRsender();

    private Principal principal = new Principal();


    /**
     * Formatters for LocalDate and LocalTime
     */
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    /**
     * showingList containing all of the showings
     */
    private List<Showing> showingList = new ArrayList<>();
    /**
     * showing helper display form
     */
    private List<ShowingDisplayForm> showingDisplayForms = new ArrayList<>();

    /**
     * maps the showing view with the help of the movieId
     * @param model passes attributes (showingsList, movie, principal) to the template
     * @param movieId id of the movie to whom that showing belongs to
     * @return showing template,
     */
    // View for showings of one particular showing
    @GetMapping(value = "/movies/showings/{movieId}")
    public String showings(Model model, @PathVariable int movieId) {

        showingDisplayForms = showingRepo.findShowingsByMovieId(movieId);
        showingList = showingRepo.findAllShowings(movieId);

        model.addAttribute("showingsList", showingDisplayForms);
        model.addAttribute("movie", movieRepo.findMovie(movieId));
        model.addAttribute("principal", principal);
        return "/showing/showings";

    }

    /**
     * maps reserve tickets page with the help of the showingId, it sends all of the needed attributes to make a reservation
     * @param model passes the attributes (movie, principal, theater, seatMatrix, showing) to the template
     * @param showingId id of the showing for which the reserve is needed
     * @return depending if user logged in is employee or a customer it returns the appropriate page
     */
    @GetMapping(value = "/movies/showings/reserve/{showingId}")
    public String reserve(Model model, @PathVariable int showingId) {

        model.addAttribute("principal", principal);
        model.addAttribute("movie", movieRepo.findMovieByShowingId(showingId));
        model.addAttribute("theater", theatreRepo.findTheatreByShowingId(showingId));
        model.addAttribute("seatMatrix", showingRepo.findTakenSeats(showingId));
        model.addAttribute("showing", showingRepo.findShowingById(showingId));
        //we decided to create an TicketReservaationForm object that contains 4 ticket objects instead of an array because
        //thymeleaf was creating some problems when we wanted to modify the objects inside the array
        model.addAttribute("tickets", new TicketReservationForm());
        try {
            if(principal.getAccessLevel().equalsIgnoreCase("CUSTOMER")) {
                return "/showing/reserve-ticket";
            }
        } catch(Exception e) {
            return "/showing/employee-reserve-ticket";
        }
        return "/showing/employee-reserve-ticket";
    }


    /**
     *  post maps the reserve a ticket page, it finds the customer for which is the reservation and inserts a ticket to the database
     * @param tickets - TicketReservationForm object - contains 4 ticket objects that can be reserved at the same time
     * @param showingId id of the showing for which
     * @return
     */
    @PostMapping(value = "/movies/showings/reserve/{showingId}")
    public String handleReserve(@ModelAttribute TicketReservationForm tickets, @PathVariable int showingId) {
        Customer customer = new Customer();
        try {
            if (principal.getAccessLevel().equalsIgnoreCase("CUSTOMER")) {
                customer = customerRepo.findCustomer(principal.getPrincipal_id());

            } else {
                customer = customerRepo.insertCustomer(tickets.getCustomer());
            }
        } catch (Exception e) {
            customer = customerRepo.insertCustomer(tickets.getCustomer());
        }


        //we cannot use a loop (which would be more elegant) because of TicketReservationForm format and that is made the way it is
        //because we had problems with modifying arrays through forms

        boolean allOK = true;
        boolean selected = false;
        //first 2 conditions are checking if anything was selected and the 3rd checks if it's already reserved
        if(tickets.getTicket1().getSeat_row() != 0 &&
                tickets.getTicket1().getSeat_nb() != 0 &&
                ticketRepo.validateTicketAvailability(tickets.getTicket1(), showingId)){


            ticketRepo.insertTicketInDB(tickets.getTicket1(), showingId, customer);
            selected = true;

            // Write QR message and send to correct recipient
            qRwriter.writeQR(tickets.getTicket1(), showingId, customer);
            String[] recipients = { customer.getEmail() };
            qRsender.sendEmail(recipients, "QRCODE5_BioTrioTicket");

            // Send SMS to a phone number verified at our Twilio account - god help me vs the spam...
            qRsender.sendSMS();

        } else if(!ticketRepo.validateTicketAvailability(tickets.getTicket1(), showingId)) {
            allOK = false;
        }
        if (tickets.getTicket2().getSeat_row() != 0 &&
                tickets.getTicket2().getSeat_nb() != 0 &&
                ticketRepo.validateTicketAvailability(tickets.getTicket2(), showingId)){


            ticketRepo.insertTicketInDB(tickets.getTicket2(), showingId, customer);
            selected = true;

            // Write QR message and send to correct recipient
            qRwriter.writeQR(tickets.getTicket2(), showingId, customer);
            String[] recipients = { customer.getEmail() };
            qRsender.sendEmail(recipients, "QRCODE5_BioTrioTicket");

        } else if(!ticketRepo.validateTicketAvailability(tickets.getTicket2(), showingId)) {
            allOK = false;
        }
        if (tickets.getTicket3().getSeat_row() != 0 &&
                tickets.getTicket3().getSeat_nb() != 0 &&
                ticketRepo.validateTicketAvailability(tickets.getTicket3(), showingId)){


            ticketRepo.insertTicketInDB(tickets.getTicket3(), showingId, customer);
            selected = true;

            // Write QR message and send to correct recipient
            qRwriter.writeQR(tickets.getTicket3(), showingId, customer);
            String[] recipients = { customer.getEmail() };
            qRsender.sendEmail(recipients, "QRCODE5_BioTrioTicket");

        } else if(!ticketRepo.validateTicketAvailability(tickets.getTicket3(), showingId)) {

            allOK = false;
        }
        if (tickets.getTicket4().getSeat_row() != 0 &&
                tickets.getTicket4().getSeat_nb() != 0 &&
                ticketRepo.validateTicketAvailability(tickets.getTicket4(), showingId)){


            ticketRepo.insertTicketInDB(tickets.getTicket4(), showingId, customer);
            selected = true;

            // Write QR message and send to correct recipient
            qRwriter.writeQR(tickets.getTicket4(), showingId, customer);
            String[] recipients = { customer.getEmail() };
            qRsender.sendEmail(recipients, "QRCODE5_BioTrioTicket");

        } else if(!ticketRepo.validateTicketAvailability(tickets.getTicket4(), showingId)){
            allOK = false ;
        }
        if( allOK && selected) {
            return "redirect:/movies"; //add "you've reserved a ticket" page
        } else if(!allOK){
            return "redirect:/movies/showings/reserve/{showingId}?fail=true";
        } else{
            return "redirect:/movies/showings/reserve/{showingId}?empty=true";
        }
    }

    /**
     * maps add a showing page
     * @param model passes attributes(movies, theatres, new showing and principal) to the template
     * @return returns add a showing page
     */
    @GetMapping(value = "/movies/add_showing")
    public String addShowing(Model model) {
        model.addAttribute("movies", movieRepo.findAllMovies());
        model.addAttribute("theatres", theatreRepo.findAllTheatres());
        model.addAttribute("newShowing", new ShowingFormObject());
        model.addAttribute("principal", principal);

        return "/showing/add-showing";
    }

    /**
     * post maps the add showing page, inserts a showing into a database if the showing is in the next 30 days
     * @param showingFormObject helper form object passed from the template
     * @return returns add a showing page or movies depending if the insert was succesfull
     */
    @PostMapping(value = "/movies/add_showing")
    public String handleAddShowing(@ModelAttribute ShowingFormObject showingFormObject) {

        Showing showing = new Showing();
        try {

            // Setting values of a Showing to the values passed in the form of the view
            showing.setTheatre_id(showingFormObject.getTheatre_id());
            showing.setMovie_id((showingFormObject.getMovie_id()));
            showing.setDate(LocalDate.parse(showingFormObject.getStart_date(), dateFormatter));
            showing.setTime(LocalTime.parse(showingFormObject.getStart_time(), timeFormatter));

            if(DAYS.between(LocalDate.now(), showing.getDate()) > 30) {
                return "redirect:/movies/add_showing?distantDate";
            } else {
                showingRepo.insertShowing(showing);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/movies/add_showing?error";
        }


        return "redirect:/movies";
    }

    /**
     * maps the edit a showing page
     * @param index of the showing that needs to be edited from the showing list
     * @param model passes the attributes to the template
     * @return returns the edit showing page
     */
    @GetMapping(value = "/movies/showings/edit/{index}")
    public String editShowing(@PathVariable int index, Model model) {


        Showing showing = showingList.get(index);
        ShowingFormObject showingFormObject = new ShowingFormObject();
        try {
            showingFormObject.setMovie_id(showing.getMovie_id());
            showingFormObject.setTheatre_id(showing.getTheatre_id());
            showingFormObject.setStart_date(showing.getDate().toString());
            showingFormObject.setStart_time(showing.getTime().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("movies", movieRepo.findAllMovies());
        model.addAttribute("theatres", theatreRepo.findAllTheatres());
        model.addAttribute("editShowing", showingFormObject);
        model.addAttribute("principal", principal);

        return "/showing/edit-showing";
    }

    /**
     * post maps the edit a showing page, it updates the showing in the database
     * @param index of the showing that needs to be edited form the showing list
     * @param showingFormObject helper object passed from the template
     * @return redirects to the movies page
     */
    @PostMapping(value = "/movies/showings/edit/{index}")
    public String handleEditShowing(@PathVariable int index, @ModelAttribute ShowingFormObject showingFormObject) {

        Showing showing = showingList.get(index);
        try {
            // Setting values of a Showing to the values passed in the form of the view
            showing.setTheatre_id(showingFormObject.getTheatre_id());
            showing.setMovie_id((showingFormObject.getMovie_id()));
            showing.setDate(LocalDate.parse(showingFormObject.getStart_date(), dateFormatter));
            showing.setTime(LocalTime.parse(showingFormObject.getStart_time(), timeFormatter));
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/movies?error";
        }

        try {
            showingRepo.updateShowing(showing);
        } catch (Exception e) {
            return "redirect:/movies?error";
        }

        return "redirect:/movies";
    }


    /**
     * maps the delete a showing page and deletes it from the dabase
     * @param index of a showing from the showing listthat needs to be deleted
     * @return redirects to the movies page
     */
    @GetMapping(value = "/movies/showings/delete/{index}")
    public String deleteShowing(@PathVariable int index) {
        try {
            showingRepo.deleteShowing(showingList.get(index));
        } catch (Exception e) {
            return "redirect:/movies?error";
        }

        return "redirect:/movies";
    }


}
