package kea.dat18i.firstyear.finalproject.biotrio.entities;


// An employee of Bio Trio who has the authority to CRUD shows as well as book tickets
// for customers who wish to book their ticket over the phone.
// The administrator can also be considered as an "employee" of Bio Trio,
// hence this class being used to classify the "Admin" actor
public class Employee extends User {

    private String password;

    public Employee() {

    }

    // Constructor for testing purposes
    public Employee(String firstName, String lastName, String username, String password) {
        super.firstName = firstName;
        super.lastName = lastName;
        super.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
