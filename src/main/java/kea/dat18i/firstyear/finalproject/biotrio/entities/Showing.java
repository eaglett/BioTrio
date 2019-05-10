package kea.dat18i.firstyear.finalproject.biotrio.entities;


import java.time.LocalDateTime;

// An entity defined as a "Showing" which has a name that describes which movie is showing,
// a date and time of the showing, and the ticket price
public class Showing {
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
