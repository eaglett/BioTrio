package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping(value = "/")
    public String homePage() {

        return "home";
    }


}
