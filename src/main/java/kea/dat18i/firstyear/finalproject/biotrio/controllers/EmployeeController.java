package kea.dat18i.firstyear.finalproject.biotrio.controllers;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Employee;

import kea.dat18i.firstyear.finalproject.biotrio.repositories.EmployeeRepository;
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
public class EmployeeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeRepository employeeRepo;

    private List<Employee> employeeList = new ArrayList<>();

    private Principal principal = new Principal();


    @GetMapping(value = "/employees")
    public String employees(Model model) {
        employeeList = employeeRepo.findAllEmployees();
        model.addAttribute("employees", employeeList);
        model.addAttribute("principal", principal);
        return "employees-page";
    }

    @GetMapping(value = "/employees/add_employee")
    public String addEmployee (Model model) {
        model.addAttribute("newEmployee", new Employee ());
        model.addAttribute("principal", principal);
        return "add-employee-page";
    }

    @PostMapping(value = "/employees/add_employee")
    public String handleAddEmployee (@ModelAttribute Employee employee) {
        employee = employeeRepo.insertEmployee(employee);
        employeeList.add(employee);
        return "redirect:/employees";
    }

    @GetMapping(value = "/employees/delete/{index}")
    public String handleDeleteEmployee (@PathVariable int index) {
        employeeRepo.deleteEmployee(employeeList.get(index));
        return "redirect:/employees";
    }

    @GetMapping("/employees/edit/{index}")
    public String editEmployee(@PathVariable int index, Model model) {
        Employee editEmployee = employeeList.get(index);
        model.addAttribute("index", index);
        model.addAttribute("editEmployee", editEmployee);
        model.addAttribute("principal", principal);
        return "edit-employee-page";
    }

    @PostMapping(value = "/employees/edit/{index}")
    public String handleEditEmployee(@PathVariable int index, @ModelAttribute Employee employee) {
        employee.setId(employeeList.get(index).getId());
        employeeRepo.editEmployee(employee);
        return "redirect:/employees";
    }

}
