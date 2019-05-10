package kea.dat18i.firstyear.finalproject.biotrio.repositories;



import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // Inserting data into table customer by using a PreparedStatement
    public Customer insertCustomer(Customer customer) throws NullPointerException{
        // trying with lambda expression
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO theater VALUES (null, ?, ?, ?, ?, ?)", new String[]{"customer_id"});
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

    public static Customer findCustomer(Customer customer){
        System.out.println(customer.toString());
        return customer;
    }



}
