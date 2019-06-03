package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Employee;

import kea.dat18i.firstyear.finalproject.biotrio.repositories.EmployeeRepository;
import kea.dat18i.firstyear.finalproject.biotrio.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepo;

    private List<Employee> employeeList = new ArrayList<>();

    private Principal principal = new Principal();


    /**
     * maps "/employees" page that shows all employees from the database, employeeRepo finds all of the employees, employees list and principal
     * containing the current user are passed through the model to the template
     * @param model passes attributes to the template
     * @return returns employees page
     */
    @GetMapping(value = "/employees")
    public String employees(Model model) {
        employeeList = employeeRepo.findAllEmployees();
        model.addAttribute("employees", employeeList);
        model.addAttribute("principal", principal);
        return "/employee/employees-page";
    }

    /**
     * maps add employee page to "/employees/add_employee", sends employee object(used to get info for the new employee) and
     * principal (contains current user) to the template
     * @param model passes attributes to the template
     * @return returns add employee page
     */
    @GetMapping(value = "/employees/add_employee")
    public String addEmployee (Model model) {
        model.addAttribute("newEmployee", new Employee ());
        model.addAttribute("principal", principal);
        return "/employee/add-employee-page";
    }

    /**
     * post maps "add employee" page, receives employee object from the template that contains info of a new employee object
     * and inserts that new employee into the database
     * @param employee new employee that we get from the template form
     * @return redirects to the employees page
     */
    @PostMapping(value = "/employees/add_employee")
    public String handleAddEmployee (@ModelAttribute Employee employee) {
        employee = employeeRepo.insertEmployee(employee);
        employeeList.add(employee);
        return "redirect:/employees";
    }

    /**
     * maps delete employee with the help of index
     * @param index index  of the employee that needs to be deleted in the employeeList
     * @return redirects to the employees page
     */
    @GetMapping(value = "/employees/delete/{index}")
    public String handleDeleteEmployee (@PathVariable int index) {
        employeeRepo.deleteEmployee(employeeList.get(index));
        return "redirect:/employees";
    }

    /**
     * maps edit employee page with the help of index, also it passes the employee object and the principal as well as the index to the
     * template
     * @param index index of an employee in the employee list
     * @param model passes attributes to the template
     * @return returns edit employee page
     */
    @GetMapping("/employees/edit/{index}")
    public String editEmployee(@PathVariable int index, Model model) {
        Employee editEmployee = employeeList.get(index);
        model.addAttribute("index", index);
        model.addAttribute("editEmployee", editEmployee);
        model.addAttribute("principal", principal);
        return "/employee/edit-employee-page";
    }

    /**
     * post maps edit employee page, receives edited employee object form the template and updates that employee in the database
     * @param index index of the employee in the employee list
     * @param employee edited employee object
     * @return redirects to the employees page
     */
    @PostMapping(value = "/employees/edit/{index}")
    public String handleEditEmployee(@PathVariable int index, @ModelAttribute Employee employee) {
        employee.setId(employeeList.get(index).getId());
        employeeRepo.editEmployee(employee);
        return "redirect:/employees";
    }

}
