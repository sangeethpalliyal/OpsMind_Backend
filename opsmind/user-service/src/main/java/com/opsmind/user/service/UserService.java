package com.opsmind.user.service;
import com.opsmind.user.model.AppUser;
import com.opsmind.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserService {
 private final UserRepository repo; private final PasswordEncoder encoder;
 public UserService(UserRepository repo,PasswordEncoder encoder){this.repo=repo;this.encoder=encoder;}
 public AppUser register(String username,String password){ if(repo.findByUsername(username).isPresent()) throw new IllegalArgumentException("Username already exists"); AppUser u=new AppUser();u.setUsername(username);u.setPassword(encoder.encode(password));return repo.save(u); }
 public AppUser find(String username){return repo.findByUsername(username).orElseThrow(()->new IllegalArgumentException("User not found"));}
}
