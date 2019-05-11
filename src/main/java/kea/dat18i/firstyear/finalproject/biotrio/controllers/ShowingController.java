package kea.dat18i.firstyear.finalproject.biotrio.controllers;


import kea.dat18i.firstyear.finalproject.biotrio.entities.Movie;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
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

    private List<Showing> showingList = new ArrayList<>();

    @GetMapping(value = "/showings")
    public String showings(Model model) {
        // showingList = showingRepo.findShowings();
        model.addAttribute("showingsList", showingList);
        model.addAttribute("movie", new Movie());

        return "showings-page";
    }


    @GetMapping(value = "/showings/add_showing")
    public String addShowing(Model model) {
        model.addAttribute("newShowing", new Showing());


        return "showings-page";
    }

    @PostMapping(value = "/showings/add_showing")
    public String handleAddShowing(@ModelAttribute Showing showing) {
        // showingRepo.insertShowing(showing);


        return "redirect:/showings";
    }

    @GetMapping(value = "/showings/edit/{index}")
    public String editShowing(@PathVariable int index, Model model) {
        model.addAttribute("editShowing", showingList.get(index));


        return "redirect:/showings";
    }

    @PostMapping(value = "/showings/edit{index}")
    public String handleEditShowing(@PathVariable int index, @ModelAttribute Showing showing) {
        // showingRepo.updateShowing(showing);


        return "redirect:/showings";
    }


    @GetMapping(value = "/showings/delete/{index}")
    public String deleteShowing(@PathVariable int index) {
        // showingRepo.deleteShowing(showingsList.get(index).getId());

        return "redirect:/showings";
    }


}
