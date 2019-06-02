package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrentUserController {

    /**
     * @param principal(Principal) helper Principal object, stores currently logged user
     */
    private Principal principal = new Principal();

    /** maps login page to "/login" and sends principal object through model to the page
     * @param model used to pass attributes to the templates
     * @return login html page
     */
    @GetMapping(value ="/login")
    public String login(Model model) {
        model.addAttribute("principal", principal);

        return "login";
    }

}
