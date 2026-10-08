/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.controller;

/**
 *
 * @author User
 */
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lk.ijse.cmjd114_115.MovieBooking.dto.UserDTO;
import lk.ijse.cmjd114_115.MovieBooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequestMapping("/api/users")
@RestController 
@RequiredArgsConstructor 
public class UserController {

    private final UserService userService;
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
}

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getSelectedUser(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getSelectedUser(userId), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@PathVariable String userId, @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(userId, userDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
