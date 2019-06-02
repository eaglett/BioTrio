package kea.dat18i.firstyear.finalproject.biotrio.repositories;



import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class CustomerRepository {

    /**
     * @Autowired to connect our Spring application to our database
     */

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * @param customer(Customer) passed to retrieve information from a Customer object and insert data into our database
     * Inserting the data into the table is done using a preparedStatement
     */
    public Customer insertCustomer(Customer customer) throws NullPointerException{

    /**
     * Lambda expression to create a Connection to our database and then a preparedStatement
     * to set each field's data to the passed Customer object's data
    */
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO customer VALUES (null, ?, ?, ?, ?, ?)", new String[]{"customer_id"});

            // first parameter is the column number. The column numbers are indexed.
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPassword());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPhoneNumber());

            return ps;
        };
        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
            customer.setId(keyholder.getKey().intValue());
        } catch (NullPointerException e) {
        }
        return customer;
    }


    /**
     *Finds a specific customer in customer table from the database by customer.getId()
     *Can also replace customer.getId() by putting an "int id" parameter instead of
     *"Customer customer" in the method parameters
     * @param id must not be null
     * @return the customer entity with the given id from the customer table in the database
     */
    public Customer findCustomer(int id) {
        // Create query for sql and parse an object into class
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM customer WHERE customer_id = " + id);

        Customer customer = new Customer();
        while (rs.next()) {
            customer.setId(rs.getInt("customer_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setPassword(rs.getString("customer_password"));
            customer.setEmail(rs.getString("email"));
            customer.setPhoneNumber(rs.getString("phone_nb"));

        }

        return customer;

    }

    /**
     *For Spring Security authentication validation
     *Finding an customer by their email is needed
     * @param email
     * @return the customer entity with the given email from the customer table
     */
    public Customer findCustomerByEmail(String email) {
            SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM customer WHERE email = '" + email + "';" );

            // Create an employee object of null value which will be
            // returned if no customer with specified email exists
            Customer customer = null;

            // Our CustomAuth (AuthenticationProvider) only needs email and password for authenticating customers
            if(rs.first()) {
                customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setPassword(rs.getString("customer_password"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_nb"));
            }

            return customer;
        }

    /**
     *
     * @param customer(Customer) passed to retrieve information from a Customer object
     *                           and update data into our customer table from the database
     */
    public void updateCustomer(Customer customer) {

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "UPDATE customer SET " +
                            "first_name = ?, last_name = ?, customer_password = ?,  email = ?, phone_nb = ? " +
                            "WHERE customer_id = ?");
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPassword());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPhoneNumber());
            ps.setInt(6, customer.getId());


            return ps;
        };

        jdbc.update(psc);

    }


}
