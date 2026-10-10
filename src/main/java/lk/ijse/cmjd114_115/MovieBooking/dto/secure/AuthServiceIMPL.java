/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.dto.secure;

import lk.ijse.cmjd114_115.MovieBooking.dao.UserDao;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.Role;
import lk.ijse.cmjd114_115.MovieBooking.entities.UserEntity;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DataNotFoundException;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DuplicateDataException;
import lk.ijse.cmjd114_115.MovieBooking.util.IDGenerate;
import lk.ijse.cmjd114_115.MovieBooking.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceIMPL implements AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public void signUp(SignUpDTO signUp) {
        if (userDao.existsByEmail(signUp.getEmail())) {
            throw new DuplicateDataException("Email already registered");
        }
        UserEntity user = new UserEntity();
        user.setUserId(IDGenerate.userId());
        user.setFirstName(signUp.getFirstName());
        user.setLastName(signUp.getLastName());
        user.setEmail(signUp.getEmail());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        user.setRole(Role.CUSTOMER);
        userDao.save(user);
    }

    @Override
    public JWTResponseDTO signIn(SignInDTO signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        UserEntity user = userDao.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        String token = jwtUtil.generateToken(user);
        return new JWTResponseDTO(token, user.getUserId(), user.getEmail(), user.getRole());
    }
}
