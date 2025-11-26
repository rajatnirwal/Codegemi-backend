package com.itservices_backend.controller;

import com.itservices_backend.model.User;
import com.itservices_backend.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        // Note: अगर तुम्हें सभी users की list चाहिए तो UserService में method बनानी पड़ेगी
        return ResponseEntity.ok().build(); // अभी dummy है
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        Optional<User> existing = userService.findByUsername(username);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = existing.get();
        user.setEmail(updatedUser.getEmail());
        // password update ke liye alag API better hai (security reasons)
        return ResponseEntity.ok(userService.registerUser(user));
    }
}
