package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;
import kea.dat18i.firstyear.finalproject.biotrio.entities.Employee;
import kea.dat18i.firstyear.finalproject.biotrio.repositories.CustomerRepository;
import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// Trying to create a session controller for handling log ins and log outs
@Controller
public class CurrentUserController {

    @Autowired
    CustomerRepository customerRepo;

    private Principal principal = new Principal();


    @GetMapping(value ="/login_check")
    public String loginCheck() {


        return "login-page";
    }

    @GetMapping(value ="/login")
    public String login() {
        System.out.println(principal.toString());

        return "login";
    }

    @GetMapping (value="/customer-login")
    public String customerLogin(Model m){
        m.addAttribute("loginform", new Customer());
        return "customer-login";
    }

    @PostMapping("/loginCustomer")
//    @ResponseBody
    public String loginCustomer(@ModelAttribute Customer customer){

        Customer logingCustomer = customerRepo.findCustomer(customer);
       /* Car carInserted = carRepo.insert(car);*/
//        return "Your data is saved and secured don't worry about GDPR." + customerInserted;
        return "redirect:/";
    }

    @GetMapping (value="employee-login")
    public String employeeLogin(Model m){
        m.addAttribute("loginform", new Employee());
        return "employee-login";
    }

    // Don't need this with our new login - but let's keep it here for now
//    @PostMapping("/loginEmployee")
////    @ResponseBody
//    public String loginEmployee(@ModelAttribute Employee employee){
//        Employee logingEmployee = EmployeeRepository.findEmployee(employee);
//        /* Car carInserted = carRepo.insert(car);*/
////        return "Your data is saved and secured don't worry about GDPR." + employeeInserted;
//        return "redirect:/";
//    }

    @PostMapping(value ="/logout")
    public String logout() {


        return "redirect:/login";
    }



}
