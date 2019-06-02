package kea.dat18i.firstyear.finalproject.biotrio.repositories;


import kea.dat18i.firstyear.finalproject.biotrio.entities.Movie;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Showing;
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
public class MovieRepository {

    /**
     * @Autowired to connect our Spring application to our database
     */

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * Finding a specific movie in the movie table from the database by the movie_id
     * @param id
     * @return the instance of the movie with the given id
     */
    public Movie findMovie(int id){
        // Create query for sql and parse an object into class
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM movie WHERE movie_id = " + id);

        Movie movie = new Movie();
        while (rs.next()) {
            movie.setMovie_id(rs.getInt("movie_id"));
            movie.setMovie_name(rs.getString("movie_name"));
            movie.setYear_of_production(rs.getInt("year_of_production"));
            movie.setDuration(rs.getInt("duration"));
        }

        return movie;
    }

    /**
     * Finding a specific movie in the movie table from the database by showing_id
     * @param id
     * @return the instance of the movie with the given showing id
     */
    public Movie findMovieByShowingId(int id){
        // Create query for sql and parse an object into class
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM showing WHERE showing_id = " + id);

        Showing showing = new Showing();
        while (rs.next()) {
            showing.setMovie_id(rs.getInt("movie_id"));
        }

        Movie movie = findMovie(showing.getMovie_id());

        return movie;
    }


    /**
     * Finds and stores all data from our movie table in our MySQL database
     * into an ArrayList of Movie objects to pass to our MovieController
     * @return all instances of a movie
     */
    public List<Movie> findAllMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM movie");

        // Iterate over every row in our movie table by using a while loop
        // and checking if next row exists
        while(rs.next()) {
            // Create new Movie object every iteration while next
            // row exists and store data from our movie table per row
            // into the object which we add into our movies ArrayList
            Movie movie = new Movie();
            movie.setMovie_id(rs.getInt("movie_id"));
            movie.setMovie_name(rs.getString("movie_name"));
            movie.setYear_of_production(rs.getInt("year_of_production"));
            movie.setDuration(rs.getInt("duration"));
            movies.add(movie);

        }

        return movies;

    }

    /**
     * @param movie(Movie) passed to retrieve information from a Movie object and insert data into our database
     * @return the entity of the movie inserted
     * @throws NullPointerException
     */
    public Movie insertMovie(Movie movie) throws NullPointerException {

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                        "INSERT INTO movie VALUES (null, ?, ?, ?)", new String[]{"movie_id"});
            ps.setString(1, movie.getMovie_name());
            ps.setInt(2, movie.getYear_of_production());
            ps.setInt(3, movie.getDuration());

            return ps;
        };

        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
            movie.setMovie_id(keyholder.getKey().intValue());
        } catch (NullPointerException e) {
        }


        return movie;
    }

    /**
     * Deleting a movie inside the MySQL database with JDBCtemplate.update(String query)
     * @param movie(Movie)
     */
    public void deleteMovie(Movie movie) {

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "DELETE FROM movie WHERE movie_id = ?");
            ps.setInt(1, movie.getMovie_id());

            return ps;
        };

        jdbc.update(psc);

    }

    /**
     *
     * @param movie(Movie) passed to retrieve information from a Movie object
     *                     and update data into our movie table from the database
     */
    public void editMovie(Movie movie) {

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "UPDATE movie SET movie_name = ?, duration = ?, year_of_production = ? WHERE movie_id = ?");
            ps.setString(1, movie.getMovie_name());
            ps.setInt(2, movie.getYear_of_production());
            ps.setInt(3, movie.getDuration());
            ps.setInt(4, movie.getMovie_id());

            return ps;
        };

        jdbc.update(psc);

    }


}
