package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Theatre;

import kea.dat18i.firstyear.finalproject.biotrio.repositories.TheatreRepository;
import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
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

    private Principal principal = new Principal();

    /**
     * maps the theatres page, shows all of the teatres
     * @param model passes the attributes to the template
     * @return theatres page
     */
    @GetMapping(value = "/theatres")
    public String theatres(Model model) {
        theatreList = theatreRepo.findAllTheatres();
        model.addAttribute("theatres", theatreList);
        model.addAttribute("principal", principal);

        return "/theatre/theatres-page";
    }

    /**
     * maps the add a theatre page
     * @param model passes the attributes to the template
     * @return returns the add a theatre page
     */
    @GetMapping(value = "/theatres/add_theatre")
    public String addTheatre(Model model) {
        model.addAttribute("newTheatre", new Theatre());
        model.addAttribute("principal", principal);

        return "/theatre/add-theatre-page";
    }

    /**
     * post maps the add a theatre page, adds the theatre to the database
     * @param theatre passed from the template, contains the new theatre info
     * @return redirects to the theatres page
     */
    @PostMapping(value = "/theatres/add_theatre")
    public String handleAddTheatre(@ModelAttribute Theatre theatre) {
        theatre = theatreRepo.insertTheatre(theatre);
        theatreList.add(theatre);

        return "redirect:/theatres";
    }


    /**
     * maps the edit a theatre page
     * @param index of the theatre that needs to be edited, from the theatreList
     * @param model passes the attributes to the template
     * @return edit theatre page
     */
    // View of editing a theatre
    @GetMapping("/theatres/edit/{index}")
    public String editTheatre(@PathVariable int index, Model model) {
        Theatre editTheatre = theatreList.get(index);
        model.addAttribute("index", index);
        model.addAttribute("editTheatre", editTheatre);
        model.addAttribute("principal", principal);
        return "/theatre/edit-theatre-page";
    }


    /**
     * post maps the edit theatre page, updates the theatre in the database
     * @param index of the edited theatre from the theatreList
     * @param theatre object containing the updated values
     * @return redirects to the theatres pages
     */
    // Edit theatre from MySQL database and redirect back to /theatres
    @PostMapping(value = "/theatres/edit/{index}")
    public String handleEditTheatre(@PathVariable int index, @ModelAttribute Theatre theatre) {
        theatre.setTheatre_id(theatreList.get(index).getTheatre_id());
        theatreRepo.editTheatre(theatre);
        return "redirect:/theatres";
    }

    @GetMapping(value = "/theatres/delete/{index}")
    public String handleDeleteTheatre(@PathVariable int index) {
        theatreRepo.deleteTheatre(theatreList.get(index));

        return "redirect:/theatres";
    }


}

