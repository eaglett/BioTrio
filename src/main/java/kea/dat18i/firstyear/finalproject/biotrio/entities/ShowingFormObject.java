package kea.dat18i.firstyear.finalproject.biotrio.entities;

/**
 *  An object to use in our form tag when adding a Showing in our add-showing template.
 *  This class is not an actual Entity, but used as as "Helper" class to properly parse
 *  data from our add-showing and edit-showing template views and check for errors before
 *  storing data about a Showing into our database.
 */

public class ShowingFormObject {

    private int movie_id;
    private int theatre_id;
    private String start_date;
    private String start_time;

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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    @Override
    public String toString() {
        return "ShowingFormObject{" +
                "movie_id=" + movie_id +
                ", theatre_id=" + theatre_id +
                ", start_date='" + start_date + '\'' +
                ", start_time='" + start_time + '\'' +
                '}';
    }
}
