/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service;

import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dto.MovieDTO;

/**
 *
 * @author User
 */
public interface MovieService {
    void saveMovie(MovieDTO movie);
    MovieDTO getSelectedMovie(String movieId);
    List<MovieDTO> getAllMovies();
    void updateMovie(String movieId, MovieDTO movie);
    void deleteMovie(String movieId);
}
