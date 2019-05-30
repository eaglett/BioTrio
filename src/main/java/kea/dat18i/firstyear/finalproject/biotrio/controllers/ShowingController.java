package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.entities.*;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.MovieRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.ShowingRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.TheatreRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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



@Controller
public class ShowingController {

    @Autowired
    private JdbcTemplate jdbc; //TODO: this is not really necessary here right?

    @Autowired
    ShowingRepository showingRepo;

    @Autowired
    MovieRepository movieRepo;

    @Autowired
    TheatreRepository theatreRepo;

    @Autowired
    TicketRepository ticketRepo;


    // Formatters for LocalDate and LocalTime
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private List<Showing> showingList = new ArrayList<>();
    private List<ShowingDisplayForm> showingDisplayForms = new ArrayList<>();

    // View for showings of one particular showing
    @GetMapping(value = "/movies/showings/{movieId}")
    public String showings(Model model, @PathVariable int movieId) {


        showingDisplayForms = showingRepo.findShowingsByMovieId(movieId);
        showingList = showingRepo.findAllShowings(movieId);

        model.addAttribute("showingsList", showingDisplayForms);
        model.addAttribute("movie", movieRepo.findMovie(movieId));
        return "/showing/showings";

    }

    @GetMapping(value = "/movies/showings/reserve/{showingId}")
    public String reserve(Model model, @PathVariable int showingId) {

        model.addAttribute("movie", movieRepo.findMovieByShowingId(showingId));
        model.addAttribute("theater", theatreRepo.findTheatreByShowingId(showingId));
        model.addAttribute("seatMatrix", showingRepo.findTakenSeats(showingId));
        model.addAttribute("showing", showingRepo.findShowingById(showingId));
        //we decided to create an TicketReservaationForm object that contains 4 ticket objects instead of an array because
        //thymeleaf was creating some problems when we wanted to modify the objects inside the array
        model.addAttribute("tickets", new TicketReservationForm());
        return "/showing/reserve-ticket"; //add "you've reserved a ticket" page
    }


    @PostMapping(value = "/movies/showings/reserve/{showingId}")
    public String handleReserve(@ModelAttribute TicketReservationForm tickets, @PathVariable int showingId) {
        //we cannot use a loop (which would be more elegant) because of TicketReservationForm format and that is made the way it is
        //because we had problems with modifying arrays through forms

        boolean allOK = true;
        //first 2 conditions are checking if anything was selected and the 3rd checks if it's already reserved
        if(tickets.getTicket1().getSeat_row() != 0 &&
                tickets.getTicket1().getSeat_nb() != 0 &&
                ticketRepo.validateTicketAvailability(tickets.getTicket1(), showingId)){

            ticketRepo.insertTicketInDB(tickets.getTicket1(), showingId);
        } else
            allOK = false ;
        if (tickets.getTicket2().getSeat_row() != 0 &&
                tickets.getTicket2().getSeat_nb() != 0 &&
                ticketRepo.validateTicketAvailability(tickets.getTicket2(), showingId)){

            ticketRepo.insertTicketInDB(tickets.getTicket2(), showingId);
        } else
            allOK = false ;
        if (tickets.getTicket3().getSeat_row() != 0 &&
                tickets.getTicket3().getSeat_nb() != 0 &&
                ticketRepo.validateTicketAvailability(tickets.getTicket3(), showingId)){

            ticketRepo.insertTicketInDB(tickets.getTicket3(), showingId);
        } else
            allOK = false ;
        if (tickets.getTicket4().getSeat_row() != 0 &&
                tickets.getTicket4().getSeat_nb() != 0 &&
                ticketRepo.validateTicketAvailability(tickets.getTicket4(), showingId)){

            ticketRepo.insertTicketInDB(tickets.getTicket4(), showingId);
        } else
            allOK = false ;
        if( allOK ) {
            return "redirect:/movies"; //add "you've reserved a ticket" page
        } else {
            return "redirect:/movies/showings/reserve/{showingId}?fail=true";
        }
    }


    @GetMapping(value = "/movies/add_showing")
    public String addShowing(Model model) {
        model.addAttribute("movies", movieRepo.findAllMovies());
        model.addAttribute("theatres", theatreRepo.findAllTheatres());
        model.addAttribute("newShowing", new ShowingFormObject());

        return "/showing/add-showing";
    }

    @PostMapping(value = "/movies/add_showing")
    public String handleAddShowing(@ModelAttribute ShowingFormObject showingFormObject) {

        Showing showing = new Showing();
        try {
            // Setting values of a Showing to the values passed in the form of the view
            showing.setTheatre_id(showingFormObject.getTheatre_id());
            showing.setMovie_id((showingFormObject.getMovie_id()));
            showing.setDate(LocalDate.parse(showingFormObject.getStart_date(), dateFormatter));
            showing.setTime(LocalTime.parse(showingFormObject.getStart_time(), timeFormatter));
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/movies/add_showing?error";
        }
        showingRepo.insertShowing(showing);

        return "redirect:/movies";
    }

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

        return "/showing/edit-showing";
    }

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
