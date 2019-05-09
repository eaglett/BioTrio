package kea.dat18i.firstyear.finalproject.biotrio.controllers;

// import Customer entity
import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;

import kea.dat18i.firstyear.finalproject.biotrio.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerRepository customerRepo;

    private List<Customer> customerList = new ArrayList<>();


    @GetMapping(value = "/customers")
    public String customers(Model model){
        model.addAttribute("customers", customerList);

        return "customers-page";
    }

    @GetMapping(value = "/create_Account")
    public String createAccount(Model model) {
        model.addAttribute("newCustomer", new Customer());

        // test to check in terminal if customer values get passed from HTML to controller console
        for(Customer customer : customerList) {
            System.out.println(customer.getFirstName());
            System.out.println(customer.getLastName());
            System.out.println(customer.getPhoneNumber());
            System.out.println(customer.getEmail());
            System.out.println(customer.getPassword());


            System.out.println();
        }


        return "create-account-page";
    }


    @PostMapping(value = "/create_Account")
    public String handleCreateAccount(@ModelAttribute Customer customer) {
        customerList.add(customer);
        return "redirect:/";
    }





}
