package kea.dat18i.firstyear.finalproject.biotrio.repositories;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Theatre;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShowingRepository {

    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private TheatreRepository theatreRepo;



    // Finds and stores all data from our showing table from our MySQL database
    // into an ArrayList of Showing objects to pass to our ShowingController
    public List<Showing> findAllShowings() {
        List<Showing> showings = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM showing");

        return iterateOverShowings(rs, showings);

    }

    public List<Showing> findShowingsByTheatreId(Theatre theatre) {
        List<Showing> showings = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM showing WHERE theater_id = " + theatre.getTheatre_id());

        return iterateOverShowings(rs, showings);

    }

    private List<Showing> iterateOverShowings(SqlRowSet rs, List<Showing> showings) {
        // Iterate over every row in our showing table by using a while loop
        // and checking if next row exists
        while(rs.next()) {
            // Create new Showing object every iteration while next
            // row exists and store data from our showing table per row
            // into the object which we add into our showings ArrayList
            Showing showing = new Showing();
            showing.setShowing_id(rs.getInt("showing_Id"));
            showing.setMovie_id(rs.getInt("movie_id"));
            showing.setTheatre_id(rs.getInt("theater_id"));
            showing.setStart_date_time(rs.getTimestamp("start_date_time"));
            showings.add(showing);
        }

        return showings;
    }

    public Showing findShowingById(int id) {

        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM showing WHERE showing_id=" + id);

        Showing showing = new Showing();
        while(rs.next()) {
            showing.setShowing_id(rs.getInt("showing_ID"));
            showing.setMovie_id(rs.getInt("movie_id"));
            showing.setTheatre_id(rs.getInt("theater_id"));
            showing.setStart_date_time(rs.getTimestamp("start_date_time"));

        }

        return showing;

    }


    public ArrayList<ArrayList<String>> findSeats(int showing_id){
        ArrayList<ArrayList<String>> seatsMatrix = new ArrayList<ArrayList<String>>();
        Theatre theatre = theatreRepo.findTheatreByShowingId(showing_id);

        List<Ticket> tickets = findTickets(showing_id);

        //inicialization of all seats to available
        for(int i=0; i<  theatre.getRows(); i++){
            seatsMatrix.add(new ArrayList<String>());
            for(int j=0; j<theatre.getSeatsPerRow(); j++){
                seatsMatrix.get(i).add("Available");
            }
        }

        //modifying reserved seats
        for (int i=0; i<tickets.size(); i++){
            Ticket ticket= tickets.get(i);
            seatsMatrix.get(ticket.getSeat_row()).set(ticket.getSeat_nb(), "Reserved");
        }

        return seatsMatrix;
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

    // have to turn LocalDateTime into a TIMESTAMP as well as in database
    public void insertShowing(Showing showing) throws NullPointerException {

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO movie VALUES (null, ?, ?, ?, ?)", new String[]{"movie_id"});
            ps.setInt(1, showing.getShowing_id());
            ps.setInt(2, showing.getMovie_id());
            ps.setInt(3, showing.getTheatre_id());
            ps.setTimestamp(4, showing.getStart_date_time());

            return ps;
        };

        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
            showing.setMovie_id(keyholder.getKey().intValue());
        } catch (NullPointerException e) {
            System.out.println(e + " at INSERT showing in our repository");
        }

    }



    // Deleting a movie inside the MySQL database with JDBCTemplate.update(String query)
    public void deleteShowing(Showing showing) {
        deleteTicketsByShowing(showing);

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "DELETE FROM showing WHERE showing_id = ?");
            ps.setInt(1, showing.getShowing_id());

            return ps;
        };

        jdbc.update(psc);
    }

    private void deleteTicketsByShowing(Showing showing) {
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "DELETE FROM ticket WHERE showing_id = ?");
            ps.setInt(1, showing.getShowing_id());

            return ps;
        };

        jdbc.update(psc);
    }


    public void updateShowing(Showing showing) {
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "UPDATE ticket SET movie_id = ?, theater_id = ?, start_date_time = ?");
            ps.setInt(1, showing.getMovie_id());
            ps.setInt(2, showing.getTheatre_id());
            ps.setTimestamp(3, showing.getStart_date_time());

            return ps;
        };

        jdbc.update(psc);

    }


}
