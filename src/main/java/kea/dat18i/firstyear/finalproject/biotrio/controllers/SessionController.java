package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.SessionBean;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Employee;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

// Trying to create a session controller for handling log ins and log outs
@Controller
public class SessionController {
/*
    @Autowired
    private SessionBean sessionBean;


    @GetMapping(value ="/login")
    public String login(ServletRequest request, HttpSession session) {


        return "login-page";
    }




    @PostMapping(value ="/logout")
    public String logout() {



        return "redirect:/login";
    }


    // Controls what Spring Security does - not the entire application
    // Spring Security creates a session when it needs one - this is done by the default "if_Required"
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // For allowing concurrent user sessions to be up and running
        httpSecurity.sessionManagement()
                .maximumSessions(3)
                .expiredUrl("/sessionExpired");

        httpSecurity.sessionManagement()
                .invalidSessionUrl("/invalidSession");


    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement()
                .maximumSessions(3)
                .expiredUrl("/sessionExpired");

        httpSecurity.sessionManagement()
                .invalidSessionUrl("/invalidSession");


    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement()
                .sessionFixation().migrateSession();
    }

    // Essential to make sure that the Spring Security session registry is notified
    // when the session is destroyed.
    // Checks if a user is already authenticated
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

*/
}
