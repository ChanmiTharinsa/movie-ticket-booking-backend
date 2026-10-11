/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.config;

/**
 *
 * @author User
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lk.ijse.cmjd114_115.MovieBooking.dao.UserDao;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.Role;
import lk.ijse.cmjd114_115.MovieBooking.entities.UserEntity;
import lk.ijse.cmjd114_115.MovieBooking.util.IDGenerate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userDao.existsByEmail("admin@moviebooking.com")) {
            UserEntity admin = new UserEntity();
            admin.setUserId(IDGenerate.userId());
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setEmail("admin@moviebooking.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(Role.ADMIN);
            userDao.save(admin);
            logger.info("Default admin account created");
        }
    }
}
