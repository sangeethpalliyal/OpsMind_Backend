package com.opsmind.user.controller;
import com.opsmind.user.auth.JwtService;import com.opsmind.user.model.AppUser;import com.opsmind.user.service.UserService;
import jakarta.validation.Valid;import jakarta.validation.constraints.NotBlank;import org.springframework.http.ResponseEntity;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/users")
public class UserController{
 private final UserService users; private final PasswordEncoder encoder; private final JwtService jwt;
 public UserController(UserService users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 record Credentials(@NotBlank String username,@NotBlank String password){}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody Credentials c){AppUser u=users.register(c.username(),c.password());return ResponseEntity.ok(Map.of("id",u.getId(),"username",u.getUsername()));}
 @PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody Credentials c){AppUser u=users.find(c.username());if(!encoder.matches(c.password(),u.getPassword())) return ResponseEntity.status(401).body(Map.of("error","Invalid credentials"));return ResponseEntity.ok(Map.of("token",jwt.generate(u.getUsername(),u.getRole()),"username",u.getUsername()));}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("service","user-service","status","UP");}
}
