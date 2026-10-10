/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service.secure;

import lk.ijse.cmjd114_115.MovieBooking.dto.secure.JWTResponseDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.secure.SignInDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.secure.SignUpDTO;

/**
 *
 * @author User
 */
public interface AuthService {
    JWTResponseDTO signIn(SignInDTO signIn);
    void signUp(SignUpDTO signUp);
}
