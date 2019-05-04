package kea.dat18i.firstyear.finalproject.biotrio.entities;


// An entity classified as a "User".
// Can be used to avoid code repetition in case new actors emerge and the system
// has to treat them us a User of the Bio Trio web application
public abstract class User extends Person {

    protected String username;
    protected int id;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
