/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.BookingStatus;
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
public class BookingDTO implements Serializable {
    private String bookingId;
    private String userId;
    private String showId;
    private int numberOfTickets;
    private List<String> seatNumbers;
    private LocalDateTime bookingDate;
    private BigDecimal totalAmount;
    private BookingStatus status;
}
