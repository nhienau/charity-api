package com.test.charity_api.controller;

import com.test.charity_api.dto.AuthResponseDTO;
import com.test.charity_api.dto.ChangePasswordDTO;
import com.test.charity_api.dto.DonorDTO;
import com.test.charity_api.dto.LoginDTO;
import com.test.charity_api.security.JWTGenerator;
import com.test.charity_api.service.DonorService;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;
    private DonorService donorService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, DonorService donorService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.donorService = donorService;
    }

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

    @GetMapping("/info")
    public ResponseEntity<DonorDTO> getUserInfo(@CookieValue("accessToken") String token) {
        String username = jwtGenerator.getUsernameFromJWT(token);
        DonorDTO donor = donorService.findById(username);

        return new ResponseEntity<>(donor, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@CookieValue("accessToken") String token, @RequestBody ChangePasswordDTO changePasswordDTO) throws Exception {
        // Lấy thông tin người dùng từ jwtGenerator
        String currentUsername = jwtGenerator.getUsernameFromJWT(token);

        // Lấy thông tin người dùng từ database
        DonorDTO donor = donorService.FindUser(currentUsername);

        // Kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), donor.getPassword())) {
            return new ResponseEntity<>("fail", HttpStatus.UNAUTHORIZED);
        }

        // Cập nhật mật khẩu mới
        String encodedNewPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        donorService.updatePassword(currentUsername, encodedNewPassword);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue("accessToken") String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
