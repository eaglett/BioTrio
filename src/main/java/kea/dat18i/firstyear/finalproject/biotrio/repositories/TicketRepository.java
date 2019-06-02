package kea.dat18i.firstyear.finalproject.biotrio.repositories;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Ticket;
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

    /**
     * @Autowired to connect our Spring application to our database
     */
    @Autowired
    private JdbcTemplate jdbc;

    private ShowingRepository showingRepo;

    /**
     *
     * @param showingRepo(ShowingRepository)
     * @Lazy initialization to create Bean when called and not on build time
     *
     */
    @Autowired
    public TicketRepository(@Lazy ShowingRepository showingRepo) {
        this.showingRepo = showingRepo;
    }

    /**
     *
     * @param showing_id(int) for selecting all tickets with the same showing_id in our database
     * @return tickets(ArrayList)
     *
     */
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
            ticket.setUsed(rs.getBoolean("used"));
            tickets.add(ticket);
        }
        return tickets;
    }

    /**
     * Selects all tickets with the same showing_id and phone_number of a Customer in our database
     *
     * @param showing_id(int)
     * @param phone_nb(String)
     * @return tickets(ArrayList)
     *
     */
    public List<Ticket> findTicketsByPhoneNb(int showing_id, String phone_nb){
        List<Ticket> tickets = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM ticket " +
                "INNER JOIN customer ON ticket.customer_id = customer.customer_id " +
                "WHERE (showing_id = " + showing_id
                + " AND phone_nb = " + phone_nb +")");
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
            ticket.setUsed(rs.getBoolean("used"));
            tickets.add(ticket);
        }
        return tickets;
    }


    // This works with both our views for reserving tickets

    /**
     * Inserts a new ticket into our database with a passed customer_id from our Customer,
     * ticket information from our Ticket, and a showing_id, with a base "used" status as boolean false
     *
     * @param ticket(Ticket)
     * @param showingId(int)
     * @param customer(Customer)
     */
    public void insertTicketInDB (Ticket ticket, int showingId, Customer customer){
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO ticket VALUES (null, ?, ?, ?, ?, ?)", new String[]{"ticket_id"});
            ps.setInt(1, showingId);
            ps.setInt(2, customer.getId());
            ps.setInt(3, ticket.getSeat_row());
            ps.setInt(4, ticket.getSeat_nb());
            ps.setBoolean(5, false);

            return ps;
        };

        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
        } catch (NullPointerException e) {
        }
    }

    /**
     * Checks availability of a seat
     *
     * @param ticket(Ticket)
     * @param showingId(int)
     * @return true or false(boolean)
     */
    public boolean validateTicketAvailability(Ticket ticket, int showingId){
        ArrayList<ArrayList<String>> seatsMatrix = showingRepo.findTakenSeats(showingId);

        String availability = seatsMatrix.get(ticket.getSeat_row()).get(ticket.getSeat_nb());
        if (!availability.equals("dummy"))
            return availability.equals("Available");
        else
            return true;
    }

    /**
     * Updates a ticket's "used" status in our database by setting it to true
     *
     * @param ticket(Ticket)
     */
    public void updateTicketStatus(Ticket ticket) {
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "UPDATE ticket SET used = ? WHERE ticket_id = ?");
            ps.setBoolean(1, true);
            ps.setInt(2, ticket.getTicket_id());

            return ps;
        };

        jdbc.update(psc);

    }

}
