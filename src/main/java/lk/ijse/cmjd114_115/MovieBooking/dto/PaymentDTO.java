/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.BookingStatus;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.PaymentMethod;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.PaymentStatus;
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
public class PaymentDTO implements Serializable {
    private String paymentId;
    private String bookingId;
    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private PaymentStatus status;
    private PaymentMethod method;
    private BookingStatus bookingStatus;
}
