/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.controller;

import jakarta.validation.Valid;
import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dto.MovieDTO;
import lk.ijse.cmjd114_115.MovieBooking.service.MovieService;
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
@RequestMapping("/api/movies")
@RequiredArgsConstructor

public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<Void> saveMovie(@Valid @RequestBody MovieDTO movie) {
        movieService.saveMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDTO> getSelectedMovie(@PathVariable String movieId) {
        return ResponseEntity.ok(movieService.getSelectedMovie(movieId));
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<Void> updateMovie(@PathVariable String movieId,
                                            @Valid @RequestBody MovieDTO movie) {
        movieService.updateMovie(movieId, movie);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build();
    }
}
