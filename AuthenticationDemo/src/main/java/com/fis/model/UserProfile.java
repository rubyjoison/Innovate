package com.fis.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserProfile {
  @Id private long id;
  private String firstName;
  private String lastName;
  private String emailId;
  private String dateOfBirth;
  private String address;

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public UserProfile() {}

  public UserProfile(
      long id,
      String firstName,
      String lastName,
      String emailId,
      String dateOfBirth,
      String address) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailId = emailId;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
