package kea.dat18i.firstyear.finalproject.biotrio.repositories;

import kea.dat18i.firstyear.finalproject.biotrio.entities.Employee;

public class EmployeeRepository {

    public static Employee findEmployee(Employee employee){
        System.out.println(employee.toString());
        return employee;
    }
}
