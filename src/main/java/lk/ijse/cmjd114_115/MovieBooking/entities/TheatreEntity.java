/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.entities;

/**
 *
 * @author User
 */
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.TheatreStatus;
import lombok.Data;

@Data
@Entity
@Table(name = "theatres")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreEntity {

    @Id
    private String theatreId;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String location;

    @Column(nullable = false)
    private int capacity;

    private String language;
    private String genre;
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TheatreStatus status;
}

