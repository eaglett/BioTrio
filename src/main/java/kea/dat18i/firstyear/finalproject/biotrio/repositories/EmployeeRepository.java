package kea.dat18i.firstyear.finalproject.biotrio.repositories;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    @Autowired // Handle this field and create the object that needs to be created
    private JdbcTemplate jdbc;


    public static Employee findEmployee(Employee employee){
        System.out.println(employee.toString());
        return employee;
    }

    // Find all the Employees from database
    public List<Employee> findAllEmployees() {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM employee");
        List<Employee> employeeList = new ArrayList<>();
        while (rs.next()) {
            Employee employee = new Employee();
            employee.setId(rs.getInt("employee_id"));
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("employee_password"));
            employee.setAccessLevel(rs.getString("access_level"));
            employeeList.add(employee);
        }
        return employeeList;
    }

    //insert data in the db using the PreparedStatement
    public Employee insertEmployee(Employee employee) throws NullPointerException{

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO employee VALUES (null, ?, ?, ?)", new String[]{"employee_id"});
            ps.setString(1, employee.getUsername());
            ps.setString(2, employee.getPassword());
            ps.setString(3, employee.getAccessLevel());

            System.out.println("ps Inserted Successfully!");
            return ps;
        };

        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
            employee.setId(keyholder.getKey().intValue());
        } catch (NullPointerException e) {
            System.out.println(e + " at INSERT employee in our repository");
        }

        return employee;

    }

    // Delete an employee inside the db
    public void deleteEmployee(Employee employee) {
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "DELETE FROM employee WHERE employee_id = ?");
            ps.setInt(1, employee.getId());

            return ps;
        };
        jdbc.update(psc);
    }

    //edit an employee inside the db
    public void editEmployee(Employee employee, int id) {


        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "UPDATE employee SET username = ?, employee_password = ?, access_level = ? WHERE employee_id = " + id);
            ps.setString(1, employee.getUsername());
            ps.setString(2, employee.getPassword());
            ps.setString(3, employee.getAccessLevel());

            return ps;
        };

        jdbc.update(psc);
    }


    // For Spring Security authentication validation
    // we need to find an employee by their username
    public Employee findEmployeeByUsername(String username) {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM employee WHERE username = '" + username + "';" );

        // Create an employee object of null value which will be
        // returned if no employee with specified username exists
        Employee employee = null;

        // Our CustomAuth (AuthenticationProvider) only needs username, password, and access_level
        // to be able to authenticate a user
        if(rs.first()) {
            employee = new Employee();
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("employee_password"));
            employee.setAccessLevel(rs.getString("access_level"));
        }

        return employee;

    }

}
