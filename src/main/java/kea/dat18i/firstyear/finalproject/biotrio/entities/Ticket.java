package kea.dat18i.firstyear.finalproject.biotrio.entities;

public class Ticket {

    int ticket_id;
    int showing_id;
    int customer_id;
    int seat_row;
    int seat_nb;

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
}
