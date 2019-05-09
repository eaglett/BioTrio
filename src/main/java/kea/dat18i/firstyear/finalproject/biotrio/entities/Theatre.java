package kea.dat18i.firstyear.finalproject.biotrio.entities;

// An entity defined as "Theatre" which has rows and seat numbers
// as well as has a specific name
public class Theatre {

    private int id;
    private String name;
    private int rows;
    private int seatsPerRow;

    public Theatre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }
}
