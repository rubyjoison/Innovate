package com.fis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fis.model.Employee;
import com.fis.repository.EmployeeDao;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
// @CrossOrigin
// @Controller
public class EmployeeController {
  @Autowired private EmployeeDao employeeRespository;

  // @PostMapping("/employees/create")
  @CrossOrigin(origins = "http://localhost:4000")
  @RequestMapping(method = RequestMethod.POST, value = "register")
  public Employee createEmployee(@RequestBody Employee employee) {
    System.out.println("Receiving id" + employee.getId());
    System.out.println("Receiving FN" + employee.getFirstName());
    System.out.println("Receiving LN" + employee.getLastName());
    System.out.println("Receiving EMAIL" + employee.getEmailId());
    return employeeRespository.save(employee);
  }

  @PutMapping("/employees/{id}")
  public ResponseEntity<Employee> updateEmployee(
      @PathVariable(value = "id") Long employeeId, @Valid @RequestBody Employee employeeDetails)
      throws ResourceNotFoundException {

    Employee employee =
        employeeRespository
            .findById(employeeId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Employee not found for this id :: " + employeeId));
    employee.setFirstName(employeeDetails.getFirstName());
    employee.setLastName(employeeDetails.getLastName());
    employee.setEmailId(employeeDetails.getEmailId());
    employee.setId(employeeDetails.getId());

    final Employee updateEmployee = employeeRespository.save(employee);
    return ResponseEntity.ok(updateEmployee);
  }

  @DeleteMapping("/employees/{id}")
  public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
      throws ResourceNotFoundException {

    Employee employee =
        employeeRespository
            .findById(employeeId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Employee not found for this id : " + employeeId));
    employeeRespository.delete(employee);

    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }

  @GetMapping("/employees")

  // @RequestMapping(method = RequestMethod.GET, value="employees")
  public List<Employee> getAllEmployees() {

    return employeeRespository.findAll();
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeeById(
      Model model, @PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {

    Employee employee =
        employeeRespository
            .findById(employeeId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("No Employee found for this id: " + employeeId));
    /*model.addAttribute("employee", employee);
    return "employeeview";*/

    return ResponseEntity.ok(employee);
  }
}
