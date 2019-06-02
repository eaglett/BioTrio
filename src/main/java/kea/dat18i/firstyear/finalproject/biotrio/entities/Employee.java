package kea.dat18i.firstyear.finalproject.biotrio.entities;

/**
 *  An employee of Bio Trio who has the authority to CRUD shows as well as book tickets
 *  for customers who wish to book their ticket over the phone.
 */

public class Employee extends User {

    private String username;
    private String password;
    private String accessLevel;

    public Employee() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "username='" + username + '\'' +
                "password='" + password + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}