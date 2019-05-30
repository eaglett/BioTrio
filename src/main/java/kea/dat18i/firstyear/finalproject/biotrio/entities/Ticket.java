package kea.dat18i.firstyear.finalproject.biotrio.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ticket {

    int ticket_id;
    int showing_id;
    int customer_id;
    int seat_row;
    int seat_nb;
    //attributes bellow this are not saved in the DB in ticket table, they are used just as helpers here
    String phone_nb;
    String first_name;
    String last_name;
    String theatre_name;
    String movie_name;
    int movie_id;
    LocalDate date;
    LocalTime time;

    public Ticket(int ticket_id, int showing_id, int customer_id, int seat_row, int seat_nb) {
        this.ticket_id = ticket_id;
        this.showing_id = showing_id;
        this.customer_id = customer_id;
        this.seat_row = seat_row;
        this.seat_nb = seat_nb;
    }


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

    public String toString(){
        return "row: " + this.seat_row + " seat: " + this.seat_nb + " phoneNb: " + phone_nb + " showing: " + showing_id + "movie : " + movie_id;

    }


}
