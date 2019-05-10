package kea.dat18i.firstyear.finalproject.biotrio.controllers;


import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ShowingController {

    private int showing_id;
    private int movie_id;
    private int theatre_id;
    private LocalDateTime start_date_time;

    public int getShowing_id() {
        return showing_id;
    }

    public void setShowing_id(int showing_id) {
        this.showing_id = showing_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getTheatre_id() {
        return theatre_id;
    }

    public void setTheatre_id(int theatre_id) {
        this.theatre_id = theatre_id;
    }

    public LocalDateTime getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(LocalDateTime start_date_time) {
        this.start_date_time = start_date_time;
    }
}
