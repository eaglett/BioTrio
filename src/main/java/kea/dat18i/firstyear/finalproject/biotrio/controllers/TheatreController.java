package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Theatre;


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
public class TheatreController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TheatreRepository theatreRepo;

    private List<Theatre> theatreList = new ArrayList<>();


    @GetMapping(value = "/theatres")
    public String theatres(Model model) {
        theatreList = theatreRepo.findAllTheatres();
        model.addAttribute("theatres", theatreList);

        return "theatres-page";
    }

    @GetMapping(value = "/theatres/add_theatre")
    public String addTheatre(Model model) {
        model.addAttribute("newTheatre", new Theatre());

        // testing to see if we actually create a Theatre object and add to the ArrayList
        // lambda expression for printing out elements of theatreList
        theatreList.forEach((t) -> System.out.println("\nAdded theatre:\n" + t.getName() + "\n"
                                                          + t.getRows() + "\n"
                                                          + t.getSeatsPerRow())
        );

        return "add-theatre-page";
    }

    @PostMapping(value = "/theatres/add_theatre")
    public String handleAddTheatre(@ModelAttribute Theatre theatre) {
        theatre = theatreRepo.insertTheatre(theatre);
        theatreList.add(theatre);

        return "redirect:/theatres";
    }



    // View of editing a theatre
    @GetMapping("/theatres/edit/{index}")
    public String editTheatre(@PathVariable int index, Model model) {
        model.addAttribute("index", index);
        model.addAttribute("editTheatre", theatreList.get(index));
        return "edit-theatre-page";
    }


    // Edit theatre from MySQL database and redirect back to /theatres
    @PostMapping(value = "/theatres/edit/{index}")
    public String handleEditTheatre(@PathVariable int index, @ModelAttribute Theatre theatre) {
        theatreRepo.editTheatre(theatre, theatreList.get(index).getId());
        return "redirect:/theatres";
    }

    @GetMapping(value = "/theatres/delete/{index}")
    public String handleDeleteTheatre(@PathVariable int index) {
        theatreRepo.deleteTheatre(theatreList.get(index));

        return "redirect:/theatres";
    }


}

