/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service.impl;

/**
 *
 * @author User
 */
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lk.ijse.cmjd114_115.MovieBooking.dao.UserDao;
import lk.ijse.cmjd114_115.MovieBooking.entities.UserEntity;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DataNotFoundException;
import lk.ijse.cmjd114_115.MovieBooking.dto.UserDTO;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DuplicateDataException;
import lk.ijse.cmjd114_115.MovieBooking.service.UserService;
import lk.ijse.cmjd114_115.MovieBooking.util.Conversion;
import lk.ijse.cmjd114_115.MovieBooking.util.IDGenerate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service 
@RequiredArgsConstructor 
@Transactional 
public class UserServiceIMPL implements UserService{
    private final UserDao userDao;
    private final Conversion conversion;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void createUser(UserDTO user) {
        if (userDao.existsByEmail(user.getEmail())) {
            throw new DuplicateDataException("Email already registered");
        }
        user.setUserId(IDGenerate.userId());
        UserEntity entity = conversion.toUserEntity(user);
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(entity);
}

    @Override
    public UserDTO getSelectedUser(String userId) {
       UserEntity userEntity= userDao.findById(userId)
                               .orElseThrow(()-> new DataNotFoundException("user not found") );
        return conversion.toUserDTO(userEntity);                          
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> allUsers =userDao.findAll();
        return userDao.findAll().stream()
        .map(conversion::toUserDTO)
        .toList();
    }

    @Override
    public void updateUser(String userId, UserDTO user) {
        UserEntity foundUser= userDao.findById(userId)
                               .orElseThrow(()-> new DataNotFoundException("user not found") );
                               foundUser.setFirstName(user.getFirstName());
                               foundUser.setLastName(user.getLastName());
                               foundUser.setRole(user.getRole());
                               if (user.getPassword() != null && !user.getPassword().isBlank()) {
                                   foundUser.setPassword(passwordEncoder.encode(user.getPassword()));
                               }
                               userDao.save(foundUser);
                              
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity foundUser= userDao.findById(userId)
                               .orElseThrow(()-> new DataNotFoundException("user not found") );
                               userDao.delete(foundUser);
       
    }
}
