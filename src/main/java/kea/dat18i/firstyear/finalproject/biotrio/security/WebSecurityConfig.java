package kea.dat18i.firstyear.finalproject.biotrio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @configuration
 * @WebSecurity
 * Our very own Custom Web Security Configuration which extends Spring Security's own
 * base Web Security Configuration, enabling us to permit certain sections of
 * our web application to specific users with individual "Authorities"
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @param customAuth(CustomAuth) autowire our CustomAuth - Custom Authentication Provider
     */
    @Autowired
    private CustomAuth customAuth;

    /**
     * @param http(HttpSecurity)
     * @Override configure(WebSecurityConfigurerAdapter) from our parent class
     *
     * Custom configuration for our HttpSecurity to check a username and password
     * from a loginForm() as well as restrict/permit access to specific users
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {


        // Grant access for specified URLs to all users
        http.authorizeRequests().antMatchers(
                "/", "/home", "/movies", "/movies/showings/*",
                          "/create_account", "/login", "/logout")
                .permitAll();

        // Grant CRUD operation access for movies and showings
        // to all employees with BASIC and ADMIN level authority
        http.authorizeRequests().antMatchers(
                "/movies/*", "/movies/edit/*", "/movies/delete/*",
                          "/movies/showings/edit/*", "/findTicket", "/select-ticket",
                          "/movies/showings/delete/*")
                .hasAnyAuthority("ADMIN", "BASIC");


        // Grant only logged in users the ticket-reservation privileges
        http.authorizeRequests().antMatchers(
                "/movies/showings/reserve/*")
                .hasAnyAuthority("ADMIN", "BASIC", "CUSTOMER");


        // Grant CRUD operation access for theatres and employees
        // to all employees with ADMIN level authority
        http.authorizeRequests().antMatchers(
                "/theatres", "/theatres/**", "/theatres/**/*",
                          "/employees", "/employees/*", "/employees/edit/*",
                          "/employees/delete/*")
                .hasAuthority("ADMIN");


        // Grant users with CUSTOMER level authority access to
        // view and edit their own account information
        http.authorizeRequests().antMatchers(
                "/customers/account", "/customers/account/edit")
                .hasAuthority("CUSTOMER");


        // Login and logout configuration
        // username and password parameters must match the username and password parameter in the
        // login form in our login html file.
        // If login attempt is unsuccessful the login form
        // will display an error message.
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/login").loginPage("/login")
                .defaultSuccessUrl("/").failureUrl("/login?error=true")
                .usernameParameter("username").passwordParameter("password");



        // For logging user out of system and deleting any cookies that might
        // keep the user further authenticated in the system
        http.authorizeRequests().and().logout().
                deleteCookies("remove").logoutUrl("/logout").
                logoutSuccessUrl("/home?logout=true");


        // Handling Access Denied Request if a user attempts to access
        // a URL which they do not have permission to access
        // and redirects them to the home page as well as displays an error message
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/home?unauthorized");


    }

    /**
     * Allows our custom authentication to authenticate users
     * and provide usernames, passwords, and roles to our HttpSecurity
     * in our overridden configure(HttpSecurity http) method
     *
     * @param auth(AuthenticationManagerBuilder)
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuth);
    }

}
