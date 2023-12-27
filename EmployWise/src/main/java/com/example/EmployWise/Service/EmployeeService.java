package com.example.EmployWise.Service;

import com.example.EmployWise.EmailService;
import com.example.EmployWise.Model.Employee;
import com.example.EmployWise.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmailService emailService;

    public int addEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);

        // Send email to level 1 manager
        sendEmailToLevel1Manager(savedEmployee);

        return savedEmployee.getId();
    }

    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) throws Exception {
        // Check if the employee with the given id exists
        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(id);

        // if employee not found with this Id then throw Exception
        if(!existingEmployeeOptional.isPresent()){
            throw new Exception("Employee not found");
        }
        Employee existingEmployee = existingEmployeeOptional.get();

        // Update fields based on the updatedEmployee
        existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
        existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setReportsTo(updatedEmployee.getReportsTo());
        existingEmployee.setProfileImage(updatedEmployee.getProfileImage());

        // Save the updated employee
        return employeeRepository.save(existingEmployee);
    }

    public Employee getNthLevelManager(int employeeId, int level) throws Exception {
        // Check if the employee with the given id exists
        if (level < 1) {
            throw new Exception("Invalid level");
        }

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        // If employee with this id is not present
        if (!employeeOptional.isPresent()) {
            throw new Exception("Employee does not exist");
        }

        // If employee is present
        Employee employee = employeeOptional.get();

        // Base case: If the level is 1, return the current employee (manager)
        if (level == 1) {
            int managerId = employee.getReportsTo();
            if (managerId != 0 && employeeRepository.findById(managerId).isPresent()) {
                return employeeRepository.findById(managerId).get();
            } else {
                // if no Manager present for employee
                throw new Exception(employee.getEmployeeName() + " is a Top-level manager, so we cannot get Nth level Manager");
            }
        }

        // Recursive case: Find the nth level manager of the current employee's manager
        int managerId = employee.getReportsTo();
        return getNthLevelManager(managerId, level - 1);
    }

    public Page<Employee> getAllEmployees(int pageNumber, int pageSize, String sortBy) {
        // Create a PageRequest with pagination and sorting parameters
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy));

        // Fetch employees based on the PageRequest
        return (Page<Employee>) employeeRepository.findAll(pageRequest);
    }

    private void sendEmailToLevel1Manager(Employee addedEmployee) {
        try {
            int level = 1;
            Employee manager = getNthLevelManager(addedEmployee.getId(), level);

            if (manager != null) {
                String subject = "New Employee Addition";
                String body = String.format(
                        "%s will now work under you. Mobile number is %s and email is %s.",
                        addedEmployee.getEmployeeName(),
                        addedEmployee.getPhoneNumber(),
                        addedEmployee.getEmail()
                );

                emailService.sendEmail(manager.getEmail(), subject, body);
            }
        } catch (Exception e) {
            // Handle exceptions (log or ignore based on your requirements)
            e.printStackTrace();
        }
    }


}
