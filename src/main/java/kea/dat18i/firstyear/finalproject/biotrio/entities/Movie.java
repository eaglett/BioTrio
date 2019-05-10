package kea.dat18i.firstyear.finalproject.biotrio.entities;

public class Movie {

    private int movie_id;
    private String movie_name;
    private int year_of_production;
    private int duration; //in minutes


    //when cleaning the code in the end, delete the constructors that are not in use


    public Movie(String movie_name, int year_of_production, int duration) {
        this.movie_name = movie_name;
        this.year_of_production = year_of_production;
        this.duration = duration;
    }

    public Movie(String movie_name) {
        this.movie_name = movie_name;
    }

    public Movie(int movie_id, String movie_name, int year_of_production, int duration) {
        this.movie_id = movie_id;
        this.movie_name = movie_name;
        this.year_of_production = year_of_production;
        this.duration = duration;
    }

    public Movie(int movie_id, String movie_name) {
        this.movie_id = movie_id;
        this.movie_name = movie_name;
    }

    public int getId() {
        return movie_id;
    }

    public String getName() {
        return movie_name;
    }

    public void setName(String movie_name) {
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
