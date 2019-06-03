package kea.dat18i.firstyear.finalproject.biotrio.controllers;

// import Customer entity
import kea.dat18i.firstyear.finalproject.biotrio.entities.Customer;

import kea.dat18i.firstyear.finalproject.biotrio.repositories.CustomerRepository;
import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo = new CustomerRepository();

    private Principal principal = new Principal();

    /**
     * maps Create account page to "create_Account" and sends principal and customer objects through model
     * @param model passes attributes to the template
     * @return create account html page
     */
    @GetMapping(value = "/create_Account")
    public String createAccount(Model model) {
        model.addAttribute("newCustomer", new Customer());
        model.addAttribute("principal", principal);

        return "/customer/create-account-page";
    }

    /**
     * post maps "create_Account", receives customer from the template and inserts customer into database
     * @param customer passed back from a template form
     * @return redirects to the home page
     */
    @PostMapping(value = "/create_Account")
    public String handleCreateAccount(@ModelAttribute Customer customer) {
        customerRepo.insertCustomer(customer);

        return "redirect:/";
    }

    /**
     * maps account overview page to "/customers/account", sends principal object to the template as well as customer after
     * finding the customer with the help of customerRepo and principal id.
     * @param model passes attributes to the template
     * @return if there is an exception it returns the home page and if everything works fine returns the customer account overview page
     */
    @GetMapping(value = "/customers/account")
    public String customerAccount(Model model){
        model.addAttribute("principal", principal);

        // Get account information of currently logged in user
        try {
            Customer customer = customerRepo.findCustomer(principal.getPrincipal_id());
            model.addAttribute("customer", customer);
        } catch (Exception e) {
            return "home-page";
        }

        return "/customer/customer-account";
    }

    /**
     * maps account editing to "/customer/account/edit", sends principal object to the template as well as a customer object after
     * finding the customer with the help of customerRepo and principal id
     * @param model passes attributes to the template
     * @return if there is an exception it returns the home page and if everything works fine returns the customer edit page
     */
    @GetMapping(value = "/customers/account/edit")
    public String editAccount(Model model){
        model.addAttribute("principal", principal);

        // Get account information of currently logged in user
        try {
            Customer customer = customerRepo.findCustomer(principal.getPrincipal_id());
            model.addAttribute("editCustomer", customer);
        } catch (Exception e) {
            return "home-page";
        }
        return "/customer/edit-account";
    }


    /**
     * post maps the edit customer page, updates the customer in the database
     * @param customer receives a customer object from the template with the changed info
     * @return redirects to the customer overview page
     */
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