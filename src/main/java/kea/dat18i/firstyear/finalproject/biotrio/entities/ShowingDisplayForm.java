package kea.dat18i.firstyear.finalproject.biotrio.entities;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *  An object to use in our form tag when displaying a Showing
 *  It holds the movie and the theatre name
 *  as well as the date, time and location.
 */

public class ShowingDisplayForm {

    private int showing_id;
    private String movie_name;
    private String theatre_name;
    private LocalDate date;
    private LocalTime time;
    private int Duration;

    public ShowingDisplayForm() {
    }

    public int getShowing_id() {
        return showing_id;
    }

    public void setShowing_id(int showing_id) {
        this.showing_id = showing_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getTheatre_name() {
        return theatre_name;
    }

    public void setTheatre_name(String theatre_name) {
        this.theatre_name = theatre_name;
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

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    @Override
    public String toString() {
        return "ShowingDisplayForm{" +
                "showing_id=" + showing_id +
                ", movie_name='" + movie_name + '\'' +
                ", theatre_name='" + theatre_name + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", Duration=" + Duration +
                '}';
    }
}
