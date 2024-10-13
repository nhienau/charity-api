package com.test.charity_api.controller;

import com.test.charity_api.dto.AuthResponseDTO;
import com.test.charity_api.dto.LoginDTO;
import com.test.charity_api.repository.DonorRepository;
import com.test.charity_api.repository.RoleRepository;
import com.test.charity_api.security.JWTGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private DonorRepository donorRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, DonorRepository donorRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.donorRepository = donorRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

//    @GetMapping("/register")
//    public ResponseEntity<String> register() {
//        return new ResponseEntity<>(passwordEncoder.encode("123456"), HttpStatus.OK);
//    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

}
