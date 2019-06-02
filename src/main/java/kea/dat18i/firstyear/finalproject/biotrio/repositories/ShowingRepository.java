package kea.dat18i.firstyear.finalproject.biotrio.repositories;

import kea.dat18i.firstyear.finalproject.biotrio.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShowingRepository {

    /**
     * @Autowired to connect our Spring application to our database
     */

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private TheatreRepository theatreRepo;

    /**
     * @Lazy initialization to create Bean when called and not on build time
     */
    private TicketRepository ticketRepo;

    /**
     * @param ticketRepo
     */
    @Autowired
    public ShowingRepository(@Lazy TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }


    // Formatters for LocalDate and LocalTime
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Finds and stores all data from our showing table from our MySQL database
     * into an ArrayList of Showing objects to pass to our ShowingController
     * @param id
     * @return
     */
    public List<Showing> findAllShowings(int id) {
        List<Showing> showings = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT "
                + "showing.showing_id, m.movie_id, t.theater_id, showing.start_date_time FROM showing "
                + "INNER JOIN theater t ON showing.theater_id = t.theater_id "
                + "INNER JOIN movie m ON showing.movie_id = m.movie_id "
                + "WHERE m.movie_id = " + id);

        // Iterate over every row in our showing table by using a while loop
        // and checking if next row exists
        while(rs.next()) {
            showings.add(iterateOverShowings(rs));

        }
        return showings;

    }

    /**
     * Creates a new Showing object every iteration while next
     * row exists and store data fro our showing table per row
     * into the object which we add into our showings ArrayList
     * @param rs
     * @return showing(Showing)
     */
    private Showing iterateOverShowings(SqlRowSet rs) {

        Showing showing = new Showing();

        showing.setShowing_id(rs.getInt("showing_Id"));
        showing.setMovie_id(rs.getInt("movie_id"));
        showing.setTheatre_id(rs.getInt("theater_id"));
        String dateTime = (rs.getTimestamp("start_date_time").toString());
        showing.setDate(LocalDate.parse(dateTime.substring(0,10), dateFormatter));
        showing.setTime(LocalTime.parse(dateTime.substring(11,16), timeFormatter));

        return showing;
    }

    /**
     * Finding a showing by id
     * @param id
     * @return the showing entity with the given id from the showing table
     */
    public Showing findShowingById(int id) {

        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM showing WHERE showing_id = " + id);

        Showing showing = new Showing();
        while(rs.next()) {
            showing = iterateOverShowings(rs);
        }

        return showing;

    }

    /**
     * Finding the taken seats when reserving a ticket
     * @param showing_id
     * @return the seat matrix
     */
    public ArrayList<ArrayList<String>> findTakenSeats(int showing_id){
        ArrayList<ArrayList<String>> seatsMatrix = new ArrayList<ArrayList<String>>();
        Theatre theatre = theatreRepo.findTheatreByShowingId(showing_id);

        List<Ticket> tickets = ticketRepo.findTickets(showing_id);

        // dummy/space holder first row, because index 0 was messing with the seat position
        seatsMatrix.add(new ArrayList<String>());
        for (int i = 0; i<=theatre.getSeatsPerRow(); i++){
            seatsMatrix.get(0).add("dummy");
        }

        //inicialization of all seats to available
            for (int i = 1; i <= theatre.getRows(); i++) {
                seatsMatrix.add(new ArrayList<String>());
                for (int j = 0; j <= theatre.getSeatsPerRow(); j++) {
                    if(j == 0)
                        seatsMatrix.get(i).add("dummy"); //dummy in first column of the first row
                    else
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


    /**
     * @param showing(Showing) passed to retrieve information from a Showing object
     *                         and insert data into our showing table from the database
     * @throws NullPointerException
     */
    public void insertShowing(Showing showing) throws NullPointerException {

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO showing VALUES (null, ?, ?, ?)", new String[]{"showing_id"});
            ps.setInt(1, showing.getMovie_id());
            ps.setInt(2, showing.getTheatre_id());
            String start_date_time = showing.getDate().toString() + " " + showing.getTime().toString() + ":00";
            ps.setTimestamp(3, Timestamp.valueOf(start_date_time));

            return ps;
        };

        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
            showing.setShowing_id(keyholder.getKey().intValue());
        } catch (NullPointerException e) {
        }

    }


    /**
     * Deleting a showing inside the MySQL database with JDBCTemplate.update(String query)
     * @param showing(Showing)
     */
    public void deleteShowing(Showing showing) {

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "DELETE FROM showing WHERE showing_id = ?");
            ps.setInt(1, showing.getShowing_id());

            return ps;
        };

        jdbc.update(psc);
    }

    /**
     * Deletes all showings behind the current date
     */
    public void deletePastShowings() {

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "DELETE FROM showing WHERE start_date_time < CURRENT_TIMESTAMP");
            return ps;
        };

        jdbc.update(psc);
    }

    /**
     * @param showing(Showing) passed to retrieve information from a Showing object
     *                         and update data into our showing table from the database
     */
    public void updateShowing(Showing showing) {
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "UPDATE showing SET movie_id = ?, theater_id = ?, start_date_time = ? WHERE showing_id = ?");
            ps.setInt(1, showing.getMovie_id());
            ps.setInt(2, showing.getTheatre_id());
            String start_date_time = showing.getDate().toString() + " " + showing.getTime().toString() + ":00";
            ps.setTimestamp(3, Timestamp.valueOf(start_date_time));
            ps.setInt(4, showing.getShowing_id());

            return ps;
        };

        jdbc.update(psc);

    }

    /**
     * Finding all showings by movie_name and properly display using our ShowingDisplayForm objects in our view
     * @param id
     * @return the showing entity with the given id from the showing table
     */
    public ArrayList<ShowingDisplayForm> findShowingsByMovieId(int id) {
        ArrayList<ShowingDisplayForm> showings = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT "
                + "showing.showing_id, m.movie_name, t.theater_name, duration, showing.start_date_time FROM showing "
                + "INNER JOIN theater t ON showing.theater_id = t.theater_id "
                + "INNER JOIN movie m ON showing.movie_id = m.movie_id "
                + "WHERE m.movie_id = " + id);

        while(rs.next()) {
            ShowingDisplayForm showing = new ShowingDisplayForm();
            showing.setShowing_id(rs.getInt("showing_Id"));
            showing.setMovie_name(rs.getString("movie_name"));
            showing.setTheatre_name(rs.getString("theater_name"));
            showing.setDuration(rs.getInt("duration"));
            String dateTime = (rs.getTimestamp("start_date_time").toString());
            showing.setDate(LocalDate.parse(dateTime.substring(0,10), dateFormatter));
            showing.setTime(LocalTime.parse(dateTime.substring(11,16), timeFormatter));
            showings.add(showing);
        }
        return showings;
    }


}
