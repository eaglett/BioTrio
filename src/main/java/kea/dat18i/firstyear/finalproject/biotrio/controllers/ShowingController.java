package kea.dat18i.firstyear.finalproject.biotrio.controllers;


import kea.dat18i.firstyear.finalproject.biotrio.entities.Movie;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.MovieRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.ShowingRepository;
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


// STILL FIGURING "Manage Showing" USE CASE OUT
@Controller
public class ShowingController {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    ShowingRepository showingRepo;

    @Autowired
    MovieRepository movieRepo;


    private List<Showing> showingList = new ArrayList<>();

    // View for showings of one particular showing
    @GetMapping(value = "/movies/showings/{movieId}")
    public String showings(Model model, @PathVariable int movieId) {
        showingList = getShowingsById(showingRepo.findAllShowings(), movieId);
        model.addAttribute("showingsList", showingList);

        return "showings";

    }

    @GetMapping(value = "/movies/showings/add_showing")
    public String addShowing(Model model) {
        model.addAttribute("newShowing", new Showing());

        return "add-showing-page";
    }

    @PostMapping(value = "/showings/add_showing")
    public String handleAddShowing(@ModelAttribute Showing showing) {
        showingRepo.insertShowing(showing);


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
