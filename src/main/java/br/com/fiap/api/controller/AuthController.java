package br.com.fiap.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.api.dto.LoginRequest;
import br.com.fiap.api.dto.ResetPasswordRequest;
import br.com.fiap.api.service.UserService;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginUserRequest) {
        boolean autenticated = userService.autenticateUser(
            loginUserRequest.getUsername(), loginUserRequest.getPassword());

        if (autenticated) {
            return ResponseEntity.ok("Login succeeded");
        } else {
            return ResponseEntity.status(401).body("Not authorized. Invalid credentials.");
        }
    }
    
    @PatchMapping("/{username}/password")
    public ResponseEntity<String> resetPassword(
            @PathVariable String username,
            @RequestBody ResetPasswordRequest request) {

        boolean updatedPassword = userService.resetPassword(username, request);

        if (updatedPassword) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(401).body("Not authorized to perform this action.");
        }
    }
}


