package kea.dat18i.firstyear.finalproject.biotrio.entities;

public class Movie {

    private int id;
    private String name;
    private int year_of_production;
    private int duration; //in minutes


    //when cleaning the code in the end, delete the construcotrs that are not in use


    public Movie(String name, int year_of_production, int duration) {
        this.name = name;
        this.year_of_production = year_of_production;
        this.duration = duration;
    }

    public Movie(String name) {
        this.name = name;
    }

    public Movie(int id, String name, int year_of_production, int duration) {
        this.id = id;
        this.name = name;
        this.year_of_production = year_of_production;
        this.duration = duration;
    }

    public Movie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                " id: " + this.id +
                " name: " + this.name +
                " year of production: " + this.year_of_production +
                " duration: " + this.duration + "minutes }");
    }
}
