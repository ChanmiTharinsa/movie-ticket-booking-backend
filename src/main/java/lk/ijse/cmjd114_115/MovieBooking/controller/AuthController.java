/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.controller;

import jakarta.validation.Valid;
import lk.ijse.cmjd114_115.MovieBooking.dto.secure.JWTResponseDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.secure.SignInDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.secure.SignUpDTO;
import lk.ijse.cmjd114_115.MovieBooking.service.secure.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
        authService.signUp(signUpDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponseDTO> signIn(@Valid @RequestBody SignInDTO signInDTO) {
        return ResponseEntity.ok(authService.signIn(signInDTO));
    }
}
