package kea.dat18i.firstyear.finalproject.biotrio.entities;

/**
 *  An entity classified as a "Person".
 *  Can be used in case the system needs an overhaul and new actors who are going
 *  to use the system, thus avoiding code repetition by being able to extend
 *  the attributes into a new class.
 */

public abstract class Person {

    protected String firstName;
    protected String lastName;

    public Person() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
