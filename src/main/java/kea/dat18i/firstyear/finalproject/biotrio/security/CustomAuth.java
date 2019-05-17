package kea.dat18i.firstyear.finalproject.biotrio.security;


import kea.dat18i.firstyear.finalproject.biotrio.entities.Employee;
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

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        // Get username and password from our login form
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        // Get an employee object from our database that matches the current user's username
        Employee employee = employeeRepo.findEmployeeByUsername(username);


        // Have to add user verification for customers as well - will be similar

        // Check if employee/user exists
        if(employee == null) {
            throw new BadCredentialsException("Username not found");
        }

        // Check if password from login form matches password in our database
        if(!password.equals(employee.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }


        // Create new HashSet that holds SimpleGrantedAuthority objects; authoritative roles.
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        // Create new authoritative role based on the currently logging in user's access_level and add to authorities
        authorities.add(new SimpleGrantedAuthority(employee.getAccessLevel().toUpperCase()));

        /* Have to come up with something to add any customer role by name for checking their own account */


        // return the currently logging in user for authentication processing
        return new UsernamePasswordAuthenticationToken(username, password, authorities);


    }


    // Implementing AuthenticationProvider means we have to override this method.
    // Returning true indicates that the AuthenticationProvider will support a closer evaluation of
    // of authenticating the presented instance of the Authentication class.
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }


}
