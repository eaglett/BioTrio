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

    /**
     * @Autowired to connect our Spring application to our database
     */

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * Finds all the Employees from database
     * @return all the Employee entities
     */
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

    /**
     * Insert data in the database using the PreparedStatement
     * @param employee(Employee) passed to retrieve information from a Employee object
     *                           and insert data into our employee table from the database
     * @return an employee entity
     * @throws NullPointerException
     */
    public Employee insertEmployee(Employee employee) throws NullPointerException{

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "INSERT INTO employee VALUES (null, ?, ?, ?)", new String[]{"employee_id"});
            ps.setString(1, employee.getUsername());
            ps.setString(2, employee.getPassword());
            ps.setString(3, employee.getAccessLevel());

            return ps;
        };

        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbc.update(psc, keyholder);
            employee.setId(keyholder.getKey().intValue());
        } catch (NullPointerException e) {
        }

        return employee;

    }

    /**
     * Deleting an employee inside the database
     * @param employee(Employee)
     */
    public void deleteEmployee(Employee employee) {
        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "DELETE FROM employee WHERE employee_id = ?");
            ps.setInt(1, employee.getId());

            return ps;
        };
        jdbc.update(psc);
    }

    /**
     *
     * @param employee(Employee) passed to retrieve information from an Employee object
     *                           and edit data into our employee table from the database
     */
    public void editEmployee(Employee employee) {

        PreparedStatementCreator psc = Connection -> {
            PreparedStatement ps = Connection.prepareStatement(
                    "UPDATE employee SET username = ?, employee_password = ?, access_level = ? WHERE employee_id = ?");
            ps.setString(1, employee.getUsername());
            ps.setString(2, employee.getPassword());
            ps.setString(3, employee.getAccessLevel());
            ps.setInt(4, employee.getId());

            return ps;
        };

        jdbc.update(psc);
    }

    /**
     *      For Spring Security authentication validation
     *      Finding an employee by their username is needed
     * @param username
     * @return the employee entity with the given username from the employee table
     */
    public Employee findEmployeeByUsername(String username) {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM employee WHERE username = '" + username + "';" );

    /**
     * Create an employee object of null value which will be
     * returned if no employee with specified username exists.
     * The CustomAuth (AuthenticationProvider) only needs username, password, and access_level
     * to be able to authenticate a user
     */
        Employee employee = null;
        if(rs.first()) {
            employee = new Employee();
            employee.setId(rs.getInt("employee_id"));
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("employee_password"));
            employee.setAccessLevel(rs.getString("access_level"));
        }

        return employee;

    }

}
