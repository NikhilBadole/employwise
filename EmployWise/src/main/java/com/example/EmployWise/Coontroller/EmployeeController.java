package com.example.EmployWise.Coontroller;

import com.example.EmployWise.Model.Employee;
import com.example.EmployWise.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody Employee employee) {
        int id = employeeService.addEmployee(employee);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public Iterable<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @DeleteMapping("/deleteId")
    public void deleteEmployee(@RequestParam int id) {
        employeeService.deleteEmployee(id);
    }

    @PutMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) throws Exception {
        return employeeService.updateEmployee(id, employee);
    }

    @GetMapping("/nthLevelManager")
    public ResponseEntity<Employee> getNthLevelManager(@RequestParam int employeeId, @RequestParam int level){
        try {
            Employee manager = employeeService.getNthLevelManager(employeeId, level);
            return new ResponseEntity<>(manager, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getPagingEmployees")
    public Page<Employee> getAllEmployees(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "employeeName") String sortBy) {
        return employeeService.getAllEmployees(pageNumber, pageSize, sortBy);
    }

}
