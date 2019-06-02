package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeController {

    private Principal principal = new Principal();

    @GetMapping(value = {"/", "/home"})
    public String homePage(HttpServletRequest servletRequest, Model model) {

        // For tracking if a user is logged in
        model.addAttribute("principal", principal);

        // Clear currently logged user data when logging out
        try {
            System.out.println(principal.getAccessLevel());
            if(servletRequest.getParameter("logout").equals("true")) {
                principal.clearAttributes();
            }
        } catch (Exception e) {
            System.out.println("ERROR AT HOME PAGE CLEARING PRINCIPAL");
        }

        return "home";
    }





}
