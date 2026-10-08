/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.controller;

import jakarta.validation.Valid;
import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dto.TheatreDTO;
import lk.ijse.cmjd114_115.MovieBooking.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/api/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping
    public ResponseEntity<Void> createTheatre(@Valid @RequestBody TheatreDTO theatre) {
        theatreService.createTheatre(theatre);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{theatreId}")
    public ResponseEntity<TheatreDTO> getSelectedTheatre(@PathVariable String theatreId) {
        return ResponseEntity.ok(theatreService.getSelectedTheatre(theatreId));
    }

    @GetMapping
    public ResponseEntity<List<TheatreDTO>> getAllTheatres() {
        return ResponseEntity.ok(theatreService.getAllTheatres());
    }

    @PutMapping("/{theatreId}")
    public ResponseEntity<Void> updateTheatre(@PathVariable String theatreId,
                                              @Valid @RequestBody TheatreDTO theatre) {
        theatreService.updateTheatre(theatreId, theatre);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{theatreId}")
    public ResponseEntity<Void> deleteTheatre(@PathVariable String theatreId) {
        theatreService.deleteTheatre(theatreId);
        return ResponseEntity.noContent().build();
    }
}