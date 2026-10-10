/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    @NotBlank(message = "Show id is required")
    private String showId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<String> seatNumbers;

    @Min(value = 1, message = "At least one ticket is required")
    private int numberOfTickets;
}
