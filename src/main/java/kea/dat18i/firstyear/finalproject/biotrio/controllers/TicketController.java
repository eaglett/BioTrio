package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.entities.*;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TicketController {

    private Ticket ticket;

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


    @GetMapping(value = "/findTicket")
    public String findTicket(Model model){
        model.addAttribute("allMovies", movieRepo.findAllMovies());
        model.addAttribute("ticket", new Ticket());
        return "/find-ticket";
    }

    @PostMapping(value = "/findTicket")
    public String handleFindTicket(@ModelAttribute Ticket ticket){

        this.ticket = ticket; //is there any other better way to pass an attribute to the second page???
        System.out.println(ticket.toString());
        return "redirect:/select-ticket";

    }

    @GetMapping(value = "select-ticket")
    public String selectTicket(Model model){

        List<Showing> showingList= showingRepo.findAllShowings(this.ticket.getMovie_id());

        ArrayList<Ticket> ticketList= new ArrayList<Ticket>();

        for (int i = 0; i < showingList.size(); i++)
           ticketList.addAll(ticketRepo.findTicketsByPhoneNb(showingList.get(i).getShowing_id(), this.ticket.getPhone_nb()));
//            ticketList.addAll(ticketRepo.findTickets(showingList.get(i).getShowing_id()));
//        we are transfering vital information into ticket object so we could show ticket in the easiest way for the employee
        for (int i = 0; i<ticketList.size(); i++){

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
        System.out.print(ticketList);


        return "select-ticket";
    }

}