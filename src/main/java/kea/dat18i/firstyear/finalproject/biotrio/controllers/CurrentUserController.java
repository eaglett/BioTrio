package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Employee;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// Trying to create a session controller for handling log ins and log outs
@Controller
public class CurrentUserController {


    @GetMapping(value ="/login")
    public String login() {


        return "login-page";
    }

    @GetMapping (value="/customer-login")
    public String customerLogin(Model m){
        m.addAttribute("loginform", new Customer());
        return "customer-login";
    }

    @PostMapping("/loginCustomer")
//    @ResponseBody
    public String loginCustomer(@ModelAttribute Customer customer){

        Customer logingCustomer = CustomerRepository.findCustomer(customer);
       /* Car carInserted = carRepo.insert(car);*/
//        return "Your data is saved and secured don't worry about GDPR." + carInserted;
        return "redirect:/customer-login";
    }

    @GetMapping (value="employee-login")
    public String employeeLogin(Model m){
        m.addAttribute("loginform", new Employee());
        return "employee-login";
    }

    @PostMapping(value ="/logout")
    public String logout() {



        return "redirect:/login";
    }



}
