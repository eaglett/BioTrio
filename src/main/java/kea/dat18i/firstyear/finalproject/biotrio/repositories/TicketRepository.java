package kea.dat18i.firstyear.finalproject.biotrio.repositories;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketRepository {

    @Autowired
    private JdbcTemplate jdbc;


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


}
