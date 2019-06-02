package kea.dat18i.firstyear.finalproject.biotrio.entities;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * An entity defined as a "Showing" which has a name that describes which movie is showing,
 *  a date and time of the showing.
 */

public class Showing {

    private int showing_id;
    private int movie_id;
    private int theatre_id;
    private LocalDate date;
    private LocalTime time;

    public Showing() {
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Showing{" +
                "showing_id=" + showing_id +
                ", movie_id=" + movie_id +
                ", theatre_id=" + theatre_id +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
