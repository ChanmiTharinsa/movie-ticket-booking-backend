/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.PaymentMethod;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaymentEntity {

    @Id
    private String paymentId;

    @Column(nullable = false)
    private LocalDateTime paymentDate;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "booking_id", unique = true)
    private BookingEntity booking;
    
}


