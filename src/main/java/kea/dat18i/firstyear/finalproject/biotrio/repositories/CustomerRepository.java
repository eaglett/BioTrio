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

    @Autowired
    private JdbcTemplate jdbc;

    // Inserting data into table customer by using a PreparedStatement
    public Customer insertCustomer(Customer customer) throws NullPointerException{
        // Lambda expression to create a Connection to our database and then a preparedStatement
        // to set each field's data to the passed Customer object's data
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO customer VALUES (null, ?, ?, ?, ?, ?)", new String[]{"customer_id"});

            // first parameter is the column number. The column numbers are indexed.
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPassword());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPhoneNumber());

            System.out.println("ps Inserted Successfully!");
            return ps;
        };
        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
            customer.setId(keyholder.getKey().intValue());
        } catch (NullPointerException e) {
            System.out.println(e + " at INSERT customer in our repository");
        }
        return customer;
    }

    // Finds a specific customer in customer table from database biotrio by customer.getId()
    // Can also replace customer.getId() by putting an "int id" parameter instead of
    // "Customer customer" in the method parameters
    public Customer findCustomer(int id) {
        // Create query for sql and parse an object into class
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM customer WHERE customer_id = " + id);

        // Can change this to "customer" if we change the method parameter
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

        // For Spring Security authentication validation
        // we need to find an customer by their email
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
