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

import com.fis.model.Customer;
import com.fis.repository.CustomerDao;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
// @CrossOrigin
// @Controller
public class CustomerController {
  @Autowired private CustomerDao customerRespository;

  // @PostMapping("/employees/create")
  @CrossOrigin(origins = "http://localhost:4000")
  @RequestMapping(method = RequestMethod.POST, value = "customers/create")
  public Customer createCustomer(@RequestBody Customer customer) {
    System.out.println("Receiving id" + customer.getId());
    System.out.println("Receiving FN" + customer.getFirstName());
    System.out.println("Receiving LN" + customer.getLastName());
    System.out.println("Receiving EMAIL" + customer.getEmailId());
    return customerRespository.save(customer);
  }

  @PutMapping("/customers/{id}")
  public ResponseEntity<Customer> updateCustomer(
      @PathVariable(value = "id") Long customerId, @Valid @RequestBody Customer customerDetails)
      throws ResourceNotFoundException {

    Customer customer =
        customerRespository
            .findById(customerId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Employee not found for this id :: " + customerId));
    customer.setFirstName(customerDetails.getFirstName());
    customer.setLastName(customerDetails.getLastName());
    customer.setEmailId(customerDetails.getEmailId());
    customer.setId(customerDetails.getId());

    final Customer updateCustomer = customerRespository.save(customer);
    return ResponseEntity.ok(updateCustomer);
  }

  @DeleteMapping("/customers/{id}")
  public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId)
      throws ResourceNotFoundException {

    Customer customer =
        customerRespository
            .findById(customerId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Employee not found for this id : " + customerId));
    customerRespository.delete(customer);

    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }

  @GetMapping("/customers")

  // @RequestMapping(method = RequestMethod.GET, value="employees")
  public List<Customer> getAllCustomers() {

    return customerRespository.findAll();
  }

  @GetMapping("/customers/{id}")
  public ResponseEntity<Customer> getCustomerById(
      Model model, @PathVariable(value = "id") Long customerId) throws ResourceNotFoundException {

    Customer customer =
        customerRespository
            .findById(customerId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("No Customer found for this id: " + customerId));
    /*model.addAttribute("employee", employee);
    return "employeeview";*/

    return ResponseEntity.ok(customer);
  }
}
