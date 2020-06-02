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

import com.fis.model.UserProfile;
import com.fis.repository.UserProfileDao;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
// @CrossOrigin
// @Controller
public class UserProfileController {
  @Autowired private UserProfileDao userProfileRespository;

  // @PostMapping("/employees/create")
  @CrossOrigin(origins = "http://localhost:4000")
  @RequestMapping(method = RequestMethod.POST, value = "userprofile/create")
  public UserProfile createUserProfile(@RequestBody UserProfile userProfile) {
    System.out.println("Receiving id" + userProfile.getId());
    System.out.println("Receiving FN" + userProfile.getFirstName());
    System.out.println("Receiving LN" + userProfile.getLastName());
    System.out.println("Receiving EMAIL" + userProfile.getEmailId());
    System.out.println("Receiving DOB" + userProfile.getDateOfBirth());
    System.out.println("Receiving Address" + userProfile.getAddress());
    return userProfileRespository.save(userProfile);
  }

  @PutMapping("/userprofile/{id}")
  public ResponseEntity<UserProfile> updateUserProfile(
      @PathVariable(value = "id") long customerId, @Valid @RequestBody UserProfile profileDetails)
      throws ResourceNotFoundException {

    UserProfile userProfile =
        userProfileRespository
            .findById(customerId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Employee not found for this id :: " + customerId));
    userProfile.setFirstName(profileDetails.getFirstName());
    userProfile.setLastName(profileDetails.getLastName());
    userProfile.setEmailId(profileDetails.getEmailId());
    userProfile.setId(profileDetails.getId());

    final UserProfile updateUserProfile = userProfileRespository.save(userProfile);
    return ResponseEntity.ok(updateUserProfile);
  }

  @DeleteMapping("/userprofile/{id}")
  public Map<String, Boolean> deleteUserProfile(@PathVariable(value = "id") Long customerId)
      throws ResourceNotFoundException {

    UserProfile customer =
        userProfileRespository
            .findById(customerId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Employee not found for this id : " + customerId));
    userProfileRespository.delete(customer);

    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }

  @GetMapping("/userprofile")

  // @RequestMapping(method = RequestMethod.GET, value="employees")
  public List<UserProfile> getAllUserProfile() {

    return userProfileRespository.findAll();
  }

  @GetMapping("/userprofile/{id}")
  public ResponseEntity<UserProfile> getUserProfileById(
      Model model, @PathVariable(value = "id") Long customerId) throws ResourceNotFoundException {

    UserProfile customer =
        userProfileRespository
            .findById(customerId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("No Customer found for this id: " + customerId));
    /*model.addAttribute("employee", employee);
    return "employeeview";*/

    return ResponseEntity.ok(customer);
  }
}
