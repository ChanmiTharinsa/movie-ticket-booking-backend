/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.service.MovieService;
import lk.ijse.cmjd114_115.MovieBooking.dao.MovieDao;
import lk.ijse.cmjd114_115.MovieBooking.dto.MovieDTO;
import lk.ijse.cmjd114_115.MovieBooking.entities.MovieEntity;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DataNotFoundException;
import lk.ijse.cmjd114_115.MovieBooking.util.Conversion;
import lk.ijse.cmjd114_115.MovieBooking.util.IDGenerate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MovieServiceIMPL implements MovieService {

    private final MovieDao movieDao;
    private final Conversion conversion;

    @Override
    public void saveMovie(MovieDTO movieDTO) {
        MovieEntity movie = conversion.toMovieEntity(movieDTO);
        movie.setMovieId(IDGenerate.movieId());
        movieDao.save(movie);
    }

    @Override
    public MovieDTO getSelectedMovie(String movieId) {
        MovieEntity movie = movieDao.findById(movieId)
                .orElseThrow(() -> new DataNotFoundException("Movie not found"));
        return conversion.toMovieDTO(movie);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieDao.findAll().stream()
                .map(conversion::toMovieDTO)
                .toList();
    }

    @Override
    public void updateMovie(String movieId, MovieDTO movieDTO) {
        MovieEntity movie = movieDao.findById(movieId)
                .orElseThrow(() -> new DataNotFoundException("Movie not found"));
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setDurationMinutes(movieDTO.getDurationMinutes());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setStatus(movieDTO.getStatus());
        movieDao.save(movie);
    }

    @Override
    public void deleteMovie(String movieId) {
        if (!movieDao.existsById(movieId)) {
            throw new DataNotFoundException("Movie not found");
        }
        movieDao.deleteById(movieId);
    }
}