package kea.dat18i.firstyear.finalproject.biotrio.repositories;


import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Theatre;
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
public class TheatreRepository {

    private ShowingRepository showingRepo;

    /**
     * @Autowired to connect our Spring application to our database
     */

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * @Lazy initialization to create Bean when called and not on build time
     * @param showingRepo
     */
    @Autowired
    public TheatreRepository(@Lazy ShowingRepository showingRepo) {
        this.showingRepo = showingRepo;
    }


    /**
     * Finding all Theatres from database BioTrio and table theater
     * @return all the theatre entities
     */
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
    /**
     * Finding a theatre by id
     * @param id
     * @return the theatre entity with the given id from the theatre table
     */
    public Theatre findTheatreByShowingId(int id) {
        Showing showing = showingRepo.findShowingById(id);

        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM theater WHERE theater_id=" + showing.getTheatre_id());
        List<Theatre> theatreList = new ArrayList<>();

        Theatre theatre = new Theatre();
        while (rs.next()) {
            theatre.setTheatre_id(rs.getInt("theater_id"));
            theatre.setName(rs.getString("theater_name"));
            theatre.setRows(rs.getInt("nb_of_rows"));
            theatre.setSeatsPerRow(rs.getInt("seats_per_row"));
            theatreList.add(theatre);
        }
        return theatre;
    }

    /**
     * Inserting data into table theater by using a PreparedStatement
     * @param theatre(Theatre)
     * @return a theatre entity
     * @throws NullPointerException
     */
    public Theatre insertTheatre(Theatre theatre) throws NullPointerException {


        // trying with lambda expression
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO theater VALUES (null, ?, ?, ?)", new String[]{"theater_id"});
            ps.setString(1, theatre.getName());
            ps.setInt(2, theatre.getRows());
            ps.setInt(3, theatre.getSeatsPerRow());
            return ps;
        };

        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
            theatre.setTheatre_id(keyholder.getKey().intValue());
        } catch (NullPointerException e) {
        }

        return theatre;

    }

    /**
     * Deleting a theatre inside the MySQL database with JDBCTemplate.update(String query)
     * @param theatre(Theatre)
     */
    public void deleteTheatre(Theatre theatre) {

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "DELETE FROM theater WHERE theater_id = ?");
            ps.setInt(1, theatre.getTheatre_id());

            return ps;
        };

        jdbc.update(psc);
    }


    /**
     * Editing a theatre inside the MySQL database with JDBCtemplate.update
     * @param theatre(Theatre)
     */
    public void editTheatre(Theatre theatre) {

        PreparedStatementCreator psc = Connection -> {
                PreparedStatement ps = Connection.prepareStatement(
                        "UPDATE theater SET theater_name = ?, nb_of_rows = ?, seats_per_row = ? WHERE theater_id = ?");
                ps.setString(1, theatre.getName());
                ps.setInt(2, theatre.getRows());
                ps.setInt(3, theatre.getSeatsPerRow());
                ps.setInt(4, theatre.getTheatre_id());

                return ps;
        };

        jdbc.update(psc);
    }
}
