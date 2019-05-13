package kea.dat18i.firstyear.finalproject.biotrio.repositories;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
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



    // Finds and stores all data from our showing table from our MySQL database
    // into an ArrayList of Showing objects to pass to our ShowingController
    public List<Showing> findAllShowings() {
        List<Showing> showings = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM showing");
        // Iterate over every row in our showing table by using a while loop
        // and checking if next row exists
        while(rs.next()) {
            // Create new Showing object every iteration while next
            // row exists and store data from our showing table per row
            // into the object which we add into our showings ArrayList
            Showing showing = new Showing();
            showing.setShowing_id(rs.getInt("showing_ID"));
            showing.setMovie_id(rs.getInt("movie_id"));
            showing.setTheatre_id(rs.getInt("theater_id"));
            showing.setStart_date_time(rs.getTimestamp("start_date_time").toLocalDateTime());
            showings.add(showing);

        }

        return showings;

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


}
