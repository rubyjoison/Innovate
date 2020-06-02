package com.fis.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fis.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
@CrossOrigin(origins = "http://localhost:4200")
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired private JwtUserDetailsService jwtUserDetailsService;
  @Autowired private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    Enumeration<String> headerNames = request.getHeaderNames();

    while (headerNames.hasMoreElements()) {

      String headerName = headerNames.nextElement();
      System.out.println("JWT Token headerName ==>" + headerName);

      Enumeration<String> headers = request.getHeaders(headerName);
      while (headers.hasMoreElements()) {
        String headerValue = headers.nextElement();
        System.out.println("JWT Token headerValue ==>" + headerValue);
      }
    }

    String requestTokenHeader = "";
    // requestTokenHeader = request.getHeader("authorization");
    /*    Enumeration headers = request.getHeaders("access-control-request-headers");
     request.geth
     while (headers.hasMoreElements()) {
       String key = (String) headers.nextElement();
       String headerKey[] = key.split(",");
       String value = request.getHeader(key);
       System.out.println(key + " : " + value);
       System.out.println("Value 111 : " + headerKey[0]);
       requestTokenHeader = request.getHeader(headerKey[0]);
       System.out.println("JWT Token header111 ==>" + requestTokenHeader);
     }
    Enumeration headerNames = request.getHeaderNames();
     while (headerNames.hasMoreElements()) {
       String key = (String) headerNames.nextElement();
       String value = request.getHeader(key);
       System.out.println(key + " : " + value);
       System.out.println("Value : " + value);
       final String requestTokenHeader1 = request.getHeader(value);
       System.out.println("JWT Token header ==>" + requestTokenHeader1);
     }*/
    if (requestTokenHeader.equals("")) {
      requestTokenHeader = request.getHeader("Authorization");
      System.out.println("JWT Token ==>" + requestTokenHeader);
    }
    String username = null;
    String jwtToken = null;
    // JWT Token is in the form "Bearer token". Remove Bearer word and get
    // only the Token
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        System.out.println("JWT Token has expired");
      }
    } else {
      logger.warn("JWT Token does not begin with Bearer String");
    }
    // Once we get the token validate it.
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
      // if token is valid configure Spring Security to manually set
      // authentication
      if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    chain.doFilter(request, response);
  }
}
