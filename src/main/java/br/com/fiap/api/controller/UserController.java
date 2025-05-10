package br.com.fiap.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.api.model.User;
import br.com.fiap.api.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listAllUsers() {
        return userService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> searchUserForId(@PathVariable Long id) {
        return userService.searchForId(id)
        		.map(user -> ResponseEntity.ok(user)) //Outra forma de escrever abreviadamente: '.map(ResponseEntity::ok)'
        		.orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
    	try {
            User savedUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> existingUser = userService.searchForId(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setClientName(updatedUser.getClientName());
            user.setEmail(updatedUser.getEmail());
            user.setBetMaxValue(updatedUser.getBetMaxValue());
            user.setUserPixKey(updatedUser.getUserPixKey());

            User savedUser = userService.save(user);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserField(@PathVariable Long id, @RequestBody User parcialUser) {
        Optional<User> existingUser = userService.searchForId(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if (parcialUser.getClientName() != null) {
                user.setClientName(parcialUser.getClientName());
            }
            if (parcialUser.getEmail() != null) {
                user.setEmail(parcialUser.getEmail());
            }
            if (parcialUser.getBetMaxValue() != null) {
                user.setBetMaxValue(parcialUser.getBetMaxValue());
            }
            if (parcialUser.getUserPixKey() != null) {
            	user.setUserPixKey(parcialUser.getUserPixKey());
            }

            User savedUser = userService.save(user);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
