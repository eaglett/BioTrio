package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Movie;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.MovieRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.ShowingRepository;
import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
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
    private MovieRepository movieRepo;

    @Autowired
    private ShowingRepository showingRepo;

    private List<Movie> movieList = new ArrayList<>();

    private Principal principal = new Principal();


    /**
     * maps movies page, deletes all of the showings that are in the past, finds all movies and passes movieList and principal to the template
     * @param model passes attributes to the template
     * @return movies page
     */
    @GetMapping(value = "/movies")
    public String movies(Model model) {
        showingRepo.deletePastShowings();
        movieList = movieRepo.findAllMovies();
        model.addAttribute("movieList", movieList);
        model.addAttribute("principal", principal);

        return "/movie/movies-page";

    }

    /**
     * maps the add movie page, sends movie and principal objects to the template
     * @param model passes attributes to the template
     * @return add a movie page
     */
    // render the html template add-movie-page.html and create a view for adding a movie
    @GetMapping(value = "/movies/add_movie")
    public String addMovie(Model model) {
        model.addAttribute("newMovie", new Movie());
        model.addAttribute("principal", principal);


        return "/movie/add-movie-page";
    }

    /**
     * post maps the add a movie page and inserts a new movie to database
     * @param movie new movie object passed from the template form
     * @return redirects to the all movies page
     */
    @PostMapping(value = "/movies/add_movie")
    public String handleAddMovie(@ModelAttribute Movie movie) {
        movieRepo.insertMovie(movie);

        return "redirect:/movies";
    }

    /**
     * maps the edit a movie page with the help of the index, sends index, movie and principal object to the template
     * @param index index of the movie in the movieList
     * @param model passes attributes to the template
     * @return returns edit a movie page
     */
    // render the html template edit-movie-page.html and create a view for editing a movie
    @GetMapping(value = "/movies/edit/{index}")
    public String editMovie(@PathVariable int index, Model model) {
        Movie editMovie = movieList.get(index);
        model.addAttribute("index", index);
        model.addAttribute("editMovie", editMovie);
        model.addAttribute("principal", principal);

        return "/movie/edit-movie-page";
    }

    /**
     * post maps the edit a movie page, updates the movie in the database
     * @param movie edited object passed back from the template
     * @param index index of the movie from the movie list
     * @return redirects to the movies page
     */
    @PostMapping(value = "/movies/edit/{index}")
    public String handleEditMovie(@ModelAttribute Movie movie, @PathVariable int index) {
        movie.setMovie_id(movieList.get(index).getMovie_id());
        movieRepo.editMovie(movie);


        return "redirect:/movies";
    }

    /**
     * maps the delete movie page with the help of the indexs, deletes a movie from the database
     * @param index index of the movie from the movie list
     * @return redirects to the movies page
     */
    @GetMapping(value = "/movies/delete/{index}")
    public String deleteMovie(@PathVariable int index) {
        movieRepo.deleteMovie(movieList.get(index));

        return "redirect:/movies";
    }





}
