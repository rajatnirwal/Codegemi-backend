package com.itservices_backend.controller;

import com.itservices_backend.config.JwtService;
import com.itservices_backend.model.User;
import com.itservices_backend.service.UserService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173") // React port
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
    	
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        System.out.println("Welcome to AuthController");
    }

    // ✅ Register new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        System.out.println("I am in register now ...");
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("❌ Email already in use");
        }
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("❌ Username already taken");
        }
        User saved = userService.registerUser(user);
        return ResponseEntity.ok(saved);
    }
    
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ✅ Token generate karo
        String token = jwtService.generateToken(user.getUsername());

        //return ResponseEntity.ok(token);
        return ResponseEntity.ok(Map.of("token", token));
    }


    // ✅ Logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("✅ Logged out successfully!");
    }
}

//
//package com.itservices_backend.controller;
//
//import com.itservices_backend.config.JwtService;
//import com.itservices_backend.model.User;
//import com.itservices_backend.service.UserService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*") // Add CORS if needed
//public class AuthController {
//
//    private final UserService userService;
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//
//    public AuthController(UserService userService,
//                         AuthenticationManager authenticationManager,
//                         JwtService jwtService) {
//        this.userService = userService;
//        this.authenticationManager = authenticationManager;
//        this.jwtService = jwtService;
//    }
//
//    // ✅ Register new user with better error handling
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody User user) {
//        try {
//            // Debug logging
//            System.out.println("Registration attempt for user: " + user);
//            
//            // Validate input
//            if (user == null) {
//                return ResponseEntity.badRequest().body(createErrorResponse("User data is required"));
//            }
//            
//            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
//                return ResponseEntity.badRequest().body(createErrorResponse("Username is required"));
//            }
//            
//            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
//                return ResponseEntity.badRequest().body(createErrorResponse("Email is required"));
//            }
//            
//            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
//                return ResponseEntity.badRequest().body(createErrorResponse("Password is required"));
//            }
//
//            // Check if email already exists
//            if (userService.existsByEmail(user.getEmail())) {
//                return ResponseEntity.badRequest().body(createErrorResponse("Email already in use"));
//            }
//            
//            // Check if username already exists
//            if (userService.existsByUsername(user.getUsername())) {
//                return ResponseEntity.badRequest().body(createErrorResponse("Username already taken"));
//            }
//
//            // Register user
//            User savedUser = userService.registerUser(user);
//            
//            // Create success response
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "User registered successfully");
//            response.put("userId", savedUser.getId());
//            response.put("username", savedUser.getUsername());
//            response.put("email", savedUser.getEmail());
//            
//            return ResponseEntity.ok(response);
//            
//        } catch (Exception e) {
//            e.printStackTrace(); // Print stack trace for debugging
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(createErrorResponse("Registration failed: " + e.getMessage()));
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        try {
//            // Validate input
//            if (user.getUsername() == null || user.getPassword() == null) {
//                return ResponseEntity.badRequest().body(createErrorResponse("Username and password are required"));
//            }
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Generate token
//            String token = jwtService.generateToken(user.getUsername());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("token", token);
//            response.put("message", "Login successful");
//            
//            return ResponseEntity.ok(response);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(createErrorResponse("Login failed: " + e.getMessage()));
//        }
//    }
//
//    // ✅ Logout
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout() {
//        SecurityContextHolder.clearContext();
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Logged out successfully");
//        return ResponseEntity.ok(response);
//    }
//    
//    // Test endpoint to check if controller is working
//    @GetMapping("/test")
//    public ResponseEntity<?> test() {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Auth controller is working");
//        return ResponseEntity.ok(response);
//    }
//    
//    private Map<String, String> createErrorResponse(String message) {
//        Map<String, String> error = new HashMap<>();
//        error.put("error", message);
//        return error;
//    }
//}
