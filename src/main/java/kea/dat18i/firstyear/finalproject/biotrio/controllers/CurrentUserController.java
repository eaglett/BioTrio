package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// Trying to create a session controller for handling log ins and log outs
@Controller
public class CurrentUserController {


    @GetMapping(value ="/login")
    public String login() {


        return "login-page";
    }




    @PostMapping(value ="/logout")
    public String logout() {



        return "redirect:/login";
    }



}
