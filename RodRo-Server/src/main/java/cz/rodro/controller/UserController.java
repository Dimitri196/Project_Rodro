package cz.rodro.controller;

import cz.rodro.dto.UserDTO;
import cz.rodro.entity.UserEntity;
import cz.rodro.exception.DuplicateEmailException;
import cz.rodro.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ REGISTER NEW USER
    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            UserDTO created = userService.create(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Duplicate email", "message", e.getMessage()));
        }
    }

    // ✅ LOGIN (session-based)
    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials,
                                   HttpServletRequest req) throws ServletException {
        String email = credentials.get("email");
        String password = credentials.get("password");

        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid credentials", "message", "Email and password are required"));
        }

        req.login(email, password);

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO dto = mapToDto(user);
        return ResponseEntity.ok(dto);
    }

    // ✅ LOGOUT
    @DeleteMapping("/auth")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest req) throws ServletException {
        req.logout();
        return ResponseEntity.ok(Map.of("message", "User logged out"));
    }

    // ✅ CURRENT USER INFO
    @GetMapping("/auth")
    public ResponseEntity<?> getCurrentUser() {
        try {
            UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDTO dto = mapToDto(user);
            return ResponseEntity.ok(dto);
        } catch (ClassCastException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized", "message", "No user is currently authenticated"));
        }
    }

    // ✅ Map UserEntity → UserDTO (helper)
    private UserDTO mapToDto(UserEntity user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setAdmin(user.isAdmin());
        return dto;
    }

    // ✅ Handle unauthorized servlet exceptions
    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleServletException(ServletException e) {
        return Map.of("error", "Authentication failed", "message", e.getMessage());
    }
}
