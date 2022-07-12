package com.example.music.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.music.models.User;
import com.example.music.models.UserDetail;
import com.example.music.res.ResObj;
import com.example.music.utils.JwtResponse;
import com.example.music.utils.JwtToken;

@RestController
@RequestMapping("/api")
public class AuthenticationUser {
    // @Autowired
    // AuthenticationManager authenticationManager;
    // @Autowired
    // private JwtToken token;

    // private static final Logger logger =
    // LoggerFactory.getLogger(SongController.class);

    // @PostMapping("/login")
    // public ResponseEntity<ResObj> authenticateUser(@RequestBody User user) {

    // logger.info("Logging in:..." + user.getUsername() + "/" +
    // user.getPassword());

    // try {
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(
    // user.getUsername(),
    // user.getPassword()));

    // logger.info("Correct login information!");
    // SecurityContextHolder.getContext().setAuthentication(authentication);

    // String jwt = token.generateToken((UserDetail) authentication.getPrincipal());

    // logger.info("Generated token: " + jwt);

    // JwtResponse token = new JwtResponse(jwt);

    // return ResponseEntity.status(HttpStatus.OK).body(
    // new ResObj(200, "Login success", token));

    // } catch (AuthenticationException exception) {
    // logger.error("Error: " + exception.getMessage());
    // }
    // return ResponseEntity.status(HttpStatus.OK).body(
    // new ResObj(500, "error", null));
    // }
}
