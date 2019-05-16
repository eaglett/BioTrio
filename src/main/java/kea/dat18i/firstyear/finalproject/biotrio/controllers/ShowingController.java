package kea.dat18i.firstyear.finalproject.biotrio.controllers;


import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.MovieRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.ShowingRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        return "/showing/reserve-ticket"; //add "you've reserved a ticket" page
    }

    @GetMapping(value = "/movies/showings/add_showing")
    public String addShowing(Model model) {
        model.addAttribute("movies", movieRepo.findAllMovies());
        model.addAttribute("theatres", theatreRepo.findAllTheatres());
        model.addAttribute("newShowing", new Showing());

        return "/showing/add-showing";
    }

    @PostMapping(value = "/movies/showings/add_showing")
    public String handleAddShowing(@ModelAttribute Showing showing) {
        //showingRepo.insertShowing(showing);
        System.out.println(showing.toString());

        return "redirect:/movies";
    }

    @GetMapping(value = "/movies/showings/edit/{index}")
    public String editShowing(@PathVariable int index, Model model) {
        model.addAttribute("editShowing", showingList.get(index));


        return "redirect:/movies";
    }

    @PostMapping(value = "/movies/showings/edit/{index}")
    public String handleEditShowing(@PathVariable int index, @ModelAttribute Showing showing) {
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
