/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.ShowStatus;
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
@Table(name = "shows")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ShowEntity {

    @Id
    private String showId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theatre_id")
    private TheatreEntity theatre;

    @Column(nullable = false)
    private LocalDate showDate;

    @Column(nullable = false)
    private LocalTime showTime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ticketPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShowStatus status;
}
