package kea.dat18i.firstyear.finalproject.biotrio.controllers;

// import Customer entity
import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;

import kea.dat18i.firstyear.finalproject.biotrio.repositories.CustomerRepository;
import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerRepository customerRepo = new CustomerRepository();

    private List<Customer> customerList = new ArrayList<>();

    private Principal principal = new Principal();


    @GetMapping(value = "/customers")
    public String customers(Model model){
        model.addAttribute("customers", customerList);

        return "customers-page";
    }

    @GetMapping(value = "/create_Account")
    public String createAccount(Model model) {
        model.addAttribute("newCustomer", new Customer());

        return "create-account-page";
    }


    @PostMapping(value = "/create_Account")
    public String handleCreateAccount(@ModelAttribute Customer customer) {
        customerRepo.insertCustomer(customer);
        customerList.add(customer);

        return "redirect:/";
    }

    @GetMapping(value = "/customers/account")
    public String customerAccount(Model model){

        // Get account information of currently logged in user
        try {
            Customer customer = customerRepo.findCustomer(principal.getPrincipal_id());
            model.addAttribute("customer", customer);
        } catch (Exception e) {
            return "home-page";
        }

        return "/customer/customer-account";
    }

    @GetMapping(value = "/customers/account/edit")
    public String editAccount(Model model){

        // Get account information of currently logged in user
        try {
            Customer customer = customerRepo.findCustomer(principal.getPrincipal_id());
            model.addAttribute("editCustomer", customer);
        } catch (Exception e) {
            return "home-page";
        }
        return "/customer/edit-account";
    }


    @PostMapping(value = "/customers/account/edit")
    public String handleEditAccount(@ModelAttribute Customer customer) {

        try {
            customer.setId(principal.getPrincipal_id());
            customerRepo.updateCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/customers/account?error";
        }

        return "redirect:/customers/account";
    }





}
