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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepository {

    @Autowired
    private JdbcTemplate jdbc;


    // Finds a specific movie in movie table from database biotrio by movie_id = id
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

    // Finds a specific movie in movie table from database biotrio by showing_id = id
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

    // Finds and stores all data from our movie table in our MySQL database
    // into an ArrayList of Movie objects to pass to our MovieController
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
            System.out.println(e + " at INSERT movie in our repository");
        }


        return movie;
    }


    // Deleting a movie inside the MySQL database with JDBCtemplate.update(String query)
    public void deleteMovie(Movie movie) {
        deleteShowingsByMovie(movie);
        String query = "DELETE FROM movie WHERE movie_id = " + movie.getMovie_id();
        jdbc.update(query);
    }

    private void deleteShowingsByMovie(Movie movie) {
        String query = "DELETE FROM showing WHERE movie_id = " + movie.getMovie_id();
        jdbc.update(query);
    }


    public void editMovie(Movie movie, int id) {

//        PreparedStatementCreator psc = Connection -> {
//            PreparedStatement ps = Connection.prepareStatement(
//                    "UPDATE movie SET movie_name = ?, year_of_production = ?, duration = ? WHERE movie_id = "
//                            + id);
//            ps.setString(1, movie.getMovie_name());
//            ps.setInt(2, movie.getYear_of_production());
//            ps.setInt(3, movie.getDuration());
//
//            return ps;
//        };
//
//        jdbc.update(psc);


        // working MySQL UPDATE query
        String query = String.format(("UPDATE movie "
                        + "SET movie_name = '%s', "
                        + "year_of_production = %s, "
                        + "duration = %s "
                        + "WHERE movie_id = %s"),
                            movie.getMovie_name(),
                            movie.getYear_of_production(),
                            movie.getDuration(),
                            id);
        jdbc.update(query);
    }


}
