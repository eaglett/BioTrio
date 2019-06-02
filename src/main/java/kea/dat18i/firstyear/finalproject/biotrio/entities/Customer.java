package kea.dat18i.firstyear.finalproject.biotrio.entities;

/**
 *  A "Customer" entity that can, either book Bio Trio show tickets by registering
 *  over the Bio Trio website as a User or calling a Bio Trio over the phone
 *  and booking a ticket.
 *
 */

public class Customer extends User {

    private String phoneNumber;
    private String email;
    private String password;

    public Customer() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
