package kea.dat18i.firstyear.finalproject.biotrio.controllers;

public class MovieController {

    private int movie_id;
    private String movie_name;
    private int year_of_production;
    private int duration;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public int getYear_of_production() {
        return year_of_production;
    }

    public void setYear_of_production(int year_of_production) {
        this.year_of_production = year_of_production;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
