/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.TheatreStatus;
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
public class TheatreDTO implements Serializable {
    private String theatreId;
    @NotBlank(message = "Name is required")
    private String name;
    private String location;
    @Positive(message = "Capacity must be greater than 0")
    private int capacity;
    private TheatreStatus status;
}