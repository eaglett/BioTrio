package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Movie;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MovieRepository movieRepo;

    private List<Movie> movieList = new ArrayList<>();

    @GetMapping(value = "/movies")
    public String movies(Model model) {
        model.addAttribute("movieList", movieList);
        movieList = movieRepo.findAllMovies();

        return "movies-page";

    }


    // render the html template add-movie-page.html and create a view for adding a movie
    @GetMapping(value = "/movies/add_movie")
    public String addMovie(Model model) {
        model.addAttribute("newMovie", new Movie());


        return "add-movie-page";
    }

    @PostMapping(value = "/movies/add_movie")
    public String handleAddMovie(@ModelAttribute Movie movie) {
        movieRepo.insertMovie(movie);


        return "redirect:/movies";
    }


    // render the html template edit-movie-page.html and create a view for editing a movie
    @GetMapping(value = "/movies/edit_movie/{index}")
    public String editMovie(@PathVariable int index, Model model) {
        model.addAttribute("index", index);
        model.addAttribute("editMovie", movieList.get(index));

        return "edit-movie-page";
    }

    @PostMapping(value = "/movies/edit/{index}")
    public String handleEditMovie(@ModelAttribute Movie movie, @PathVariable int index) {
        movieRepo.editMovie(movie, index);


        return "redirect:/movies";
    }


    @GetMapping(value = "/movies/delete/{index}")
    public String deleteMovie(@PathVariable int index) {
        movieRepo.deleteMovie(movieList.get(index));

        return "redirect:/movies";
    }


}
