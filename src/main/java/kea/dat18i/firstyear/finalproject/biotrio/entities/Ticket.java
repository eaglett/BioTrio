package kea.dat18i.firstyear.finalproject.biotrio.entities;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *  An entity defined as "Ticket" which holds:
 *  the specific showing as well as the specific customer
 *  row and seat number
 *  a boolean attribute which defines whether the ticket is used or not
 */

public class Ticket {

    private int ticket_id;
    private int showing_id;
    private int customer_id;
    private int seat_row;
    private int seat_nb;
    private boolean used;
    // attributes bellow this are not saved in the DB in ticket table, they are used just as helpers here
    // customer
    private String email;
    private String phone_nb;
    private String first_name;
    private String last_name;
    // theatre
    private String theatre_name;
    // movie
    private String movie_name;
    // showing
    private int movie_id;
    private LocalDate date;
    private LocalTime time;

    public Ticket() {
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getShowing_id() {
        return showing_id;
    }

    public void setShowing_id(int showing_id) {
        this.showing_id = showing_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getSeat_row() {
        return seat_row;
    }

    public void setSeat_row(int seat_row) {
        this.seat_row = seat_row;
    }

    public int getSeat_nb() {
        return seat_nb;
    }

    public void setSeat_nb(int seat_nb) {
        this.seat_nb = seat_nb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_nb() {
        return phone_nb;
    }

    public void setPhone_nb(String phone_nb) {
        this.phone_nb = phone_nb;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getTheatre_name() {
        return theatre_name;
    }

    public void setTheatre_name(String theatre_name) {
        this.theatre_name = theatre_name;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String toString(){
        return "row: " + this.seat_row + " seat: " + this.seat_nb + " phoneNb: " + phone_nb + " showing: " + showing_id + "movie : " + movie_id;

    }


}
