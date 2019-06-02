package kea.dat18i.firstyear.finalproject.biotrio.entities;


/**
 * An HtmlFormObject used to store reserved tickets into our database.
 * This class is not an actual Entity but more of a "Helper" class to help us
 * properly parse the data from our reserve-ticket template view to our backend
 * and store the tickets into our database.
 */
public class TicketReservationForm {

    private Ticket ticket1 = new Ticket();
    private Ticket ticket2 = new Ticket();
    private Ticket ticket3 = new Ticket();
    private Ticket ticket4 = new Ticket();

    // A customer to be stored into the DB when employee is reserving tickets for a Customer
    private Customer customer = new Customer();

    public Ticket getTicket1() {
        return ticket1;
    }

    public void setTicket1(Ticket ticket1) {
        this.ticket1 = ticket1;
    }

    public Ticket getTicket2() {
        return ticket2;
    }

    public void setTicket2(Ticket ticket2) {
        this.ticket2 = ticket2;
    }

    public Ticket getTicket3() {
        return ticket3;
    }

    public void setTicket3(Ticket ticket3) {
        this.ticket3 = ticket3;
    }

    public Ticket getTicket4() {
        return ticket4;
    }

    public void setTicket4(Ticket ticket4) {
        this.ticket4 = ticket4;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
