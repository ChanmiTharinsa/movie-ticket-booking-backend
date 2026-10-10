/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.dto.secure;

import java.io.Serializable;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */
@Data 
@AllArgsConstructor 
@NoArgsConstructor

public class JWTResponseDTO implements Serializable {
    private String token;
    private String userId;
    private String email;
    private Role role;
}
