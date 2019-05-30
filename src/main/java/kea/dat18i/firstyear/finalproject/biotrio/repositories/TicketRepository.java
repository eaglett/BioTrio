package kea.dat18i.firstyear.finalproject.biotrio.repositories;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Ticket;
import kea.dat18i.firstyear.finalproject.biotrio.entities.TicketReservationForm;
import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketRepository {

    private static Principal principal = new Principal();

    @Autowired
    private JdbcTemplate jdbc;

    private ShowingRepository showingRepo;

    @Autowired
    public TicketRepository(@Lazy ShowingRepository showingRepo) {
        this.showingRepo = showingRepo;
    }


    public List<Ticket> findTickets(int showing_id){
        List<Ticket> tickets = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM ticket WHERE showing_id=" + showing_id);
        // Iterate over every row in our ticket table by using a while loop
        // and checking if next row exists
        while(rs.next()) {
            Ticket ticket = new Ticket();
            ticket.setTicket_id(rs.getInt("ticket_id"));
            ticket.setCustomer_id(rs.getInt("customer_id"));
            ticket.setShowing_id(rs.getInt("showing_id"));
            ticket.setSeat_row(rs.getInt("seat_row"));
            ticket.setSeat_nb(rs.getInt("seat_nb"));
            tickets.add(ticket);
        }
        return tickets;
    }

    public List<Ticket> findTicketsByPhoneNb(int showing_id, String phone_nb){
        List<Ticket> tickets = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM ticket INNER JOIN customer ON ticket.customer_id = customer.customer_id WHERE (showing_id=" + showing_id
                + " AND phone_nb=" + phone_nb +")");
        // Iterate over every row in our ticket table by using a while loop
        // and checking if next row exists
        while(rs.next()) {
            Ticket ticket = new Ticket();
            ticket.setTicket_id(rs.getInt("ticket_id"));
            ticket.setCustomer_id(rs.getInt("customer_id"));
            ticket.setShowing_id(rs.getInt("showing_id"));
            ticket.setSeat_row(rs.getInt("seat_row"));
            ticket.setSeat_nb(rs.getInt("seat_nb"));
            ticket.setPhone_nb(rs.getString("phone_nb"));
            ticket.setFirst_name(rs.getString("first_name"));
            ticket.setLast_name(rs.getString("last_name"));
            tickets.add(ticket);
        }
        return tickets;
    }

    public void insertTicketInDB (Ticket ticket, int showingId){
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO ticket VALUES (null, ?, ?, ?, ?)", new String[]{"ticket_id"});
            ps.setInt(1, showingId);
            ps.setInt(2, principal.getPrincipal_id()); //TODO: add a customer id
            ps.setInt(3, ticket.getSeat_row());
            ps.setInt(4, ticket.getSeat_nb());

            return ps;
        };

        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
        } catch (NullPointerException e) {
            System.out.println(e + " at INSERT ticket in our repository");
        }
    }

    public boolean validateTicketAvailability(Ticket ticket, int showingId){
        ArrayList<ArrayList<String>> seatsMatrix = showingRepo.findTakenSeats(showingId);
        String availability = seatsMatrix.get(ticket.getSeat_row()).get(ticket.getSeat_nb());

        return availability.equals("Available");
    }


}
