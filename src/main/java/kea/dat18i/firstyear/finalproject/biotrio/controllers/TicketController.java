package kea.dat18i.firstyear.finalproject.biotrio.controllers;

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

import java.util.ArrayList;
import java.util.List;

@Controller
public class TicketController {

    @Autowired
    MovieRepository movieRepo;

    @Autowired
    ShowingRepository showingRepo;

    @Autowired
    TicketRepository ticketRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    TheatreRepository theatreRepo;

    private Ticket ticket;
    private List<Ticket> ticketList = new ArrayList<>();

    private Principal principal = new Principal();


    /**
     * maps the find ticket page, finds all of the movies that can be selected as the movie wanted to find a ticket for
     * @param model passes the attributes to the template
     * @return find ticket page
     */
    @GetMapping(value = "/findTicket")
    public String findTicket(Model model){
        model.addAttribute("allMovies", movieRepo.findAllMovies());
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("principal", principal);
        return "/ticket/find-ticket";
    }

    /**
     * post maps the find ticket page
     * @param ticket passed back from the template and is going to be used in the select ticket page
     * @return redirects to the select ticket page
     */
    @PostMapping(value = "/findTicket")
    public String handleFindTicket(@ModelAttribute Ticket ticket){

        this.ticket = ticket;
        System.out.println(ticket.toString());
        return "redirect:/select-ticket";

    }

    /**
     * maps the select ticket page, finds all of the showings and tickets that are reserved for a specific phone number
     * @param model passes the attributes to the template
     * @return select ticket page
     */
    @GetMapping(value = "/select-ticket")
    public String selectTicket(Model model){

        List<Showing> showingList = showingRepo.findAllShowings(this.ticket.getMovie_id());
        ticketList.clear();

        for (int i = 0; i < showingList.size(); i++) {
            ticketList.addAll(ticketRepo.findTicketsByPhoneNb(showingList.get(i).getShowing_id(), this.ticket.getPhone_nb()));
        }
//        we are transferring vital information into ticket object so we could show ticket in the easiest way for the employee
        for (int i = 0; i < ticketList.size(); i++) {

            Theatre theatre = theatreRepo.findTheatreByShowingId(ticketList.get(i).getShowing_id());
            ticketList.get(i).setTheatre_name(theatre.getName());

            Movie movie = movieRepo.findMovieByShowingId(ticketList.get(i).getShowing_id());
            ticketList.get(i).setMovie_name(movie.getMovie_name());

            Showing showing = showingRepo.findShowingById(ticketList.get(i).getShowing_id());
            ticketList.get(i).setDate(showing.getDate());
            ticketList.get(i).setTime(showing.getTime());
        }


        model.addAttribute( "ticketList", ticketList);
        model.addAttribute("ticketListSize", ticketList.size());
        model.addAttribute("principal", principal);

        return "/ticket/select-ticket";

    }

    /**
     * maps the select ticket for a specific ticket, updates the ticket status (used/not used)
     * @param index of the ticket that needs to be updated
     * @param model passes the attributes to the template
     * @return redirects to the select ticket page
     */
    @GetMapping(value = "select-ticket/{index}")
    public String handleSelectTicket(@PathVariable int index, Model model) {
        ticketRepo.updateTicketStatus(ticketList.get(index));

        model.addAttribute("principal", principal);

        return "redirect:/select-ticket";
    }

}
