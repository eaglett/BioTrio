package kea.dat18i.firstyear.finalproject.biotrio.repositories;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Ticket;
import kea.dat18i.firstyear.finalproject.biotrio.entities.TicketReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void insertTickets(TicketReservationForm tickets, int showingId){
        //unfortunatly we cannot use a for loop here as we have 4 different objects and not an array of objects

        if(tickets.getTicket1().getSeat_row() != 0 && tickets.getTicket1().getSeat_nb() != 0){
            insertTicketInDB(tickets.getTicket1(), showingId);
        }
        if (tickets.getTicket2().getSeat_row() != 0 && tickets.getTicket2().getSeat_nb() != 0){
            insertTicketInDB(tickets.getTicket2(), showingId);
        }
        if (tickets.getTicket3().getSeat_row() != 0 && tickets.getTicket3().getSeat_nb() != 0){
            insertTicketInDB(tickets.getTicket3(), showingId);
        }
        if (tickets.getTicket4().getSeat_row() != 0 && tickets.getTicket4().getSeat_nb() != 0){
            insertTicketInDB(tickets.getTicket4(), showingId);
        }
    }

    public void insertTicketInDB (Ticket ticket, int showingId){
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO ticket VALUES (null, ?, ?, ?, ?)", new String[]{"ticket_id"});
            ps.setInt(1, showingId);
            ps.setInt(2, 5); //TODO: add a customer id
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
}
