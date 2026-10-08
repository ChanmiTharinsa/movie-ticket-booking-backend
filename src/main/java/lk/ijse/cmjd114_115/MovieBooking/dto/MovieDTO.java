/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.MovieStatus;
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
public class MovieDTO implements Serializable {
    private String movieId;
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @Positive(message = "Duration must be greater than 0")
    private int durationMinutes;
    private String language;
    private String genre;
    private LocalDate releaseDate;
    private MovieStatus status;
}

