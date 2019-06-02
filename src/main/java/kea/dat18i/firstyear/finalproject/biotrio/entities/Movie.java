package kea.dat18i.firstyear.finalproject.biotrio.entities;

/**
 *  An entity defined as "Movie" which has the movie name, the year of production
 *  and the duration in minutes.
 */

public class Movie {

    private int movie_id;
    private String movie_name;
    private int year_of_production;
    private int duration;


    public Movie() {
    }

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

    @Override
    public String toString(){
        return( "Movie {" +
                " movie_id: " + this.movie_id +
                " movie_name: " + this.movie_name +
                " year of production: " + this.year_of_production +
                " duration: " + this.duration + "minutes }");
    }
}
