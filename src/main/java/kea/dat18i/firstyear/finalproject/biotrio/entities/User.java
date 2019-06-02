package kea.dat18i.firstyear.finalproject.biotrio.entities;


/**
 *  An entity classified as a "User".
 *  Can be used to avoid code repetition in case new actors emerge and the system
 *  has to treat them us a User of the Bio Trio web application
 */
public abstract class User extends Person {

    protected int id;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
