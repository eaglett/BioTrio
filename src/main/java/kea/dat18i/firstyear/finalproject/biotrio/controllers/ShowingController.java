package kea.dat18i.firstyear.finalproject.biotrio.controllers;


import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
import kea.dat18i.firstyear.finalproject.biotrio.entities.ShowingFormObject;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Ticket;
import kea.dat18i.firstyear.finalproject.biotrio.entities.TicketReservationForm;
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
    private JdbcTemplate jdbc;

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

    // View for showings of one particular showing
    @GetMapping(value = "/movies/showings/{movieId}")
    public String showings(Model model, @PathVariable int movieId) {

        showingList = getShowingsById(showingRepo.findAllShowings(), movieId);
        model.addAttribute("showingsList", showingList);
        model.addAttribute("movie", movieRepo.findMovie(movieId));

        return "/showing/showings";

    }

    @GetMapping(value = "/movies/showings/reserve/{showingId}")
    public String reserve(Model model, @PathVariable int showingId) {

        model.addAttribute("movie", movieRepo.findMovieByShowingId(showingId));
        model.addAttribute("theater", theatreRepo.findTheatreByShowingId(showingId));
        model.addAttribute("seatMatrix", showingRepo.findSeats(showingId));
        model.addAttribute("showing", showingRepo.findShowingById(showingId));
        model.addAttribute("tickets", new TicketReservationForm());
        return "/showing/reserve-ticket"; //add "you've reserved a ticket" page
    }


    @PostMapping(value = "/movies/showings/reserve/{showingId}")
    public String handleReserve(@ModelAttribute TicketReservationForm tickets, @PathVariable int showingId) {

        System.out.print(tickets.getTicket1().toString());
        System.out.print(tickets.getTicket2().toString());
        System.out.print(tickets.getTicket3().toString());
        System.out.print(tickets.getTicket4().toString());
        return "redirect:/movies"; //add "you've reserved a ticket" page
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
            System.out.println(showing.toString());
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
        System.out.println(showing.toString() + "  a showing for editing");
        try {
            showingFormObject.setMovie_id(showing.getMovie_id());
            showingFormObject.setTheatre_id(showing.getTheatre_id());
            showingFormObject.setStart_date(showing.getDate().toString());
            showingFormObject.setStart_time(showing.getTime().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Editing showing -> " + showingFormObject.toString());

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
            System.out.println("EDITING ERROR -> " + showing.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/movies?error";
        }
        System.out.println("Edited showing -> " + showing.toString());

        showingRepo.updateShowing(showing);

        return "redirect:/movies";
    }


    @GetMapping(value = "/movies/showings/delete/{index}")
    public String deleteShowing(@PathVariable int index) {
        showingRepo.deleteShowing(showingList.get(index));

        return "redirect:/movies";
    }





    // Might be able to replace with an INNER JOIN
    // Finds all showings with the same movie_id that is passed in the method parameter
    // and stores them into a new ArrayList to display in our showings() view
    private List<Showing> getShowingsById(List<Showing> showingsList, int movieId) {
        List<Showing> showings = new ArrayList<>();

        for(Showing showing : showingsList) {
            if(movieRepo.findMovie(showing.getMovie_id()).getMovie_id() == movieId) {
                showings.add(showing);
            }
        }
        return showings;
    }


}
