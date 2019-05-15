package kea.dat18i.firstyear.finalproject.biotrio.repositories;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Theatre;
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
public class TheatreRepository {

    @Autowired // Handle this field and create the object that needs to be created
    private JdbcTemplate jdbc;





    // Find all Theatres from database BioTrio and table theater
    public List<Theatre> findAllTheatres() {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM theater");
        List<Theatre> theatreList = new ArrayList<>();
        while (rs.next()) {
            Theatre theatre = new Theatre();
            theatre.setTheatre_id(rs.getInt("theater_id"));
            theatre.setName(rs.getString("theater_name"));
            theatre.setRows(rs.getInt("nb_of_rows"));
            theatre.setSeatsPerRow(rs.getInt("seats_per_row"));
            theatreList.add(theatre);
        }
        return theatreList;
    }


    // Inserting data into table theater by using a PreparedStatement
    public Theatre insertTheatre(Theatre theatre) throws NullPointerException{


        // trying with lambda expression
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO theater VALUES (null, ?, ?, ?)", new String[]{"theater_id"});
            ps.setString(1, theatre.getName());
            ps.setInt(2, theatre.getRows());
            ps.setInt(3, theatre.getSeatsPerRow());

            System.out.println("ps Inserted Successfully!");
            return ps;
        };

        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
            theatre.setTheatre_id(keyholder.getKey().intValue());
        } catch (NullPointerException e) {
            System.out.println(e + " at INSERT theatre in our repository");
        }

        return theatre;

    }

    // Deleting a theatre inside the MySQL database with JDBCtemplate.update(String query)
    public void deleteTheatre(Theatre theatre) {
        String query = "DELETE FROM theater WHERE theater_id = " + theatre.getTheatre_id();
        jdbc.update(query);
    }

    // Editing a theatre inside the MySQL database with JDBCtemplate.update
    public void editTheatre(Theatre theatre, int id) {

        PreparedStatementCreator psc = Connection -> {
                PreparedStatement ps = Connection.prepareStatement(
                        "UPDATE theater SET theater_name = ?, nb_of_rows = ?, seats_per_row = ? WHERE theater_id = " + id);
                ps.setString(1, theatre.getName());

                ps.setInt(2, theatre.getRows());
                ps.setInt(3, theatre.getSeatsPerRow());

                System.out.println("ps Updated Successfully!");
                return ps;
        };


        jdbc.update(psc);

    }
}
