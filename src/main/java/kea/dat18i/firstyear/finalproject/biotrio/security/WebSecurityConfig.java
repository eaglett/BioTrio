package kea.dat18i.firstyear.finalproject.biotrio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomAuth customAuth;


    // Custom configuration for our HttpSecurity to check a username and password
    // from a loginForm() as well as restrict/permit access to specific users
    @Override
    protected void configure(final HttpSecurity http) throws Exception {


        // Spring Security Cross-Site Request Forgery disabled.
        // Have to check if it will mess anything up if enabled
        http.csrf().disable();


        // If another URL needs to be accessed by all users just add it as a String URL
        // in the antMatchers(String URL) parameters
        // Grant access for specified URLs to all users
        http.authorizeRequests().antMatchers(
                "/", "/home", "/movies", "/movies/showings/**",
                "/movies/showings/reserve/**", "/create_account", "/login", "/logout")
                .permitAll();

        // Grant CRUD operation access for movies and showings
        // to all employees with BASIC and ADMIN level authority
        http.authorizeRequests().antMatchers(
                "/movies/**", "/movies/**/**", "/movies/showings/edit/**")
                .hasAnyAuthority("ADMIN", "BASIC");


        // Grant CRUD operation access for theatres and employees
        // to all employees with ADMIN level authority
        http.authorizeRequests().antMatchers(
                "/theatres", "/theatres/**", "/theatres/**/**",
                          "/employees", "/employees/**", "/employees/**/**")
                .hasAuthority("ADMIN");


//        // Works; need to create an html file for editing the customer account
//        http.authorizeRequests().antMatchers(
//                "/edit_account")
//                .hasAuthority("CUSTOMER");


        // Login and logout configuration
        // username and password parameters must match the username and password parameter in the
        // login form in our login html file(s).
        // Have to make logout redirect to /login or /home with an and().logout().logoutSuccessUrl().
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/login").loginPage("/login")
                .defaultSuccessUrl("/").failureUrl("/login?error")
                .usernameParameter("username").passwordParameter("password");


        // Doesn't work like this, looking for solution
//        http.authorizeRequests().and().formLogin()
//                .loginProcessingUrl("/employee-login").loginPage("/employee-login")
//                .defaultSuccessUrl("/").failureUrl("/employee-login")
//                .usernameParameter("username").passwordParameter("password");


        // For logging user out of system and deleting and cookies that might
        // keep the user further authenticated in the system
        http.authorizeRequests().and().logout().
                deleteCookies("remove").logoutUrl("/logout").
                logoutSuccessUrl("/?logout");


        //Handling Access Denied Request
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/home?unauthorized");


    }

    // Allows our custom authentication to authenticate users
    // and provide usernames, passwords, and roles to our HttpSecurity
    // in our overridden configure(HttpSecurity http) method
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuth);
    }

}
