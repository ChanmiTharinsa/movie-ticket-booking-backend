/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service;

import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dto.UserDTO;

/**
 *
 * @author User
 */
public interface UserService {
    void createUser(UserDTO user);
    UserDTO getSelectedUser(String userId);
    List<UserDTO> getAllUsers();
    void updateUser(String userId, UserDTO user);
    void deleteUser(String userId);
}
