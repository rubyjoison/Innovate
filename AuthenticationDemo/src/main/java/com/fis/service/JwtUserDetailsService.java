package com.fis.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fis.model.UserDTO;
import com.fis.model.Users;
import com.fis.repository.UserDao;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired private UserDao userDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Users user = userDao.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), new ArrayList<>());

    /*
     * if ("javainuse".equals(username)) { return new User("javainuse",
     * "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", new
     * ArrayList<>()); } else { throw new
     * UsernameNotFoundException("User not found with username: " + username); }
     */ }

  public Users saveUser(UserDTO user) {

    Users newUser = new Users();

    newUser.setUsername(user.getUsername());
    newUser.setPassword(user.getPassword());
    newUser.setFirstName(user.getFirstName());
    newUser.setLastName(user.getLastName());

    return userDao.save(newUser);
  }
}
