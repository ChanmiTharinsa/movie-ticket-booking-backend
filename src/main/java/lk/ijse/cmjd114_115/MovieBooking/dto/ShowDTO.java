/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.ShowStatus;
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
public class ShowDTO implements Serializable {
    private String showId;
    @NotBlank(message = "Movie is required")
    private String movieId;
    @NotBlank(message = "Theatre is required")
    private String theatreId;
    @NotNull(message = "Show date is required")
    private LocalDate showDate;
    @NotNull(message = "Show time is required")
    private LocalTime showTime;
    @NotNull(message = "Ticket price is required")
    @Positive(message = "Ticket price must be greater than 0")
    private BigDecimal ticketPrice;
    private ShowStatus status;
}
