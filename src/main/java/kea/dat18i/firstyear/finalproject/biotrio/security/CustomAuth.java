package kea.dat18i.firstyear.finalproject.biotrio.security;


import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Employee;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.CustomerRepository;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

// Custom configuration to authenticate a user
@Configuration
public class CustomAuth implements AuthenticationProvider {

    // For getting an employee from our database by their username
    @Autowired
    EmployeeRepository employeeRepo;

    // For getting a customer from our database by their email
    @Autowired
    CustomerRepository customerRepo;

    private Principal principal = new Principal();

    /**
     * @Override authenticate(AuthenticationProvider) from our parent class
     * @param auth(Authentication)
     * @throws AuthenticationException
     * @return new UsernamePasswordAuthenticationToken(username, password, authorities)
     *
     * Custom authentication method which helps us get the currently authenticating user's
     * credentials and return an authenticated user and their credentials
     *
     */
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        // Get username and password from our login form
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        // Get an employee object from our database that matches the current user's username
        Employee employee = employeeRepo.findEmployeeByUsername(username);

        // Get a customer object from our database that matches the current user's username
        Customer customer = customerRepo.findCustomerByEmail(username);

        // Check if user is recognized as either an employee or customer
        // in our database and store information into our Prinpical.
        if(employee != null) {
            principal.setPrincipal_id(employee.getId());
            principal.setUsername(employee.getUsername());
            principal.setPassword(employee.getPassword());
            principal.setPhonenumber(null);
            principal.setAccessLevel(employee.getAccessLevel().toUpperCase());
        } else if (customer != null) {
            principal.setPrincipal_id(customer.getId());
            principal.setUsername(customer.getEmail());
            principal.setPassword(customer.getPassword());
            principal.setPhonenumber(customer.getPhoneNumber());
            principal.setAccessLevel("CUSTOMER");

        } else {
            principal.clearAttributes();
            throw new BadCredentialsException("Invalid username");
        }

        // Check if authenticating user's password matches
        // password of a user in the database
        if(!password.equals(principal.getPassword())) {
            principal.clearAttributes();
            throw new BadCredentialsException("Invalid password");
        }


        // Create new HashSet that holds SimpleGrantedAuthority objects; authoritative roles.
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        // Create new authoritative role based on the currently logging in user's access level and add to authorities
        authorities.add(new SimpleGrantedAuthority(principal.getAccessLevel().toUpperCase()));


        // return the currently logging in user for authentication processing
        return new UsernamePasswordAuthenticationToken(username, password, authorities);

    }

    /**
     * @Override supports(AuthenticationProvider) from our parent class
     * @param arg0(Class<?>)
     * @return true
     *
     * Implementing AuthenticationProvider means we have to override this method.
     * Returning true indicates that the AuthenticationProvider will support a closer evaluation of
     * of authenticating the presented instance of the Authentication class.
     *
     */
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }



}
