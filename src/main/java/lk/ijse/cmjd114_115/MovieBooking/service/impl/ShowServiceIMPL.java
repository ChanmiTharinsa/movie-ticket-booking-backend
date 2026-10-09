/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service.impl;

import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dao.MovieDao;
import lk.ijse.cmjd114_115.MovieBooking.dao.ShowDao;
import lk.ijse.cmjd114_115.MovieBooking.dao.TheatreDao;
import lk.ijse.cmjd114_115.MovieBooking.dto.ShowDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.MovieStatus;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.ShowStatus;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.TheatreStatus;
import lk.ijse.cmjd114_115.MovieBooking.entities.MovieEntity;
import lk.ijse.cmjd114_115.MovieBooking.entities.ShowEntity;
import lk.ijse.cmjd114_115.MovieBooking.entities.TheatreEntity;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.BadRequestException;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DataNotFoundException;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DuplicateDataException;
import lk.ijse.cmjd114_115.MovieBooking.service.ShowService;
import lk.ijse.cmjd114_115.MovieBooking.util.IDGenerate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Service
@RequiredArgsConstructor
@Transactional

public class ShowServiceIMPL implements ShowService {

    private final ShowDao showDao;
    private final MovieDao movieDao;
    private final TheatreDao theatreDao;

    @Override
    public void createShow(ShowDTO dto) {
        MovieEntity movie = movieDao.findById(dto.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Movie not found"));
        TheatreEntity theatre = theatreDao.findById(dto.getTheatreId())
                .orElseThrow(() -> new DataNotFoundException("Theatre not found"));

        if (movie.getStatus() == MovieStatus.ENDED) {
            throw new BadRequestException("Cannot create a show for an ended movie");
        }
        if (theatre.getStatus() != TheatreStatus.ACTIVE) {
            throw new BadRequestException("Shows can only be created for active theatres");
        }
        if (showDao.existsByTheatre_TheatreIdAndShowDateAndShowTime(
                dto.getTheatreId(), dto.getShowDate(), dto.getShowTime())) {
            throw new DuplicateDataException("This theatre already has a show at that date and time");
        }

        ShowEntity show = new ShowEntity();
        show.setShowId(IDGenerate.showId());
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowDate(dto.getShowDate());
        show.setShowTime(dto.getShowTime());
        show.setTicketPrice(dto.getTicketPrice());
        show.setStatus(ShowStatus.SCHEDULED);
        showDao.save(show);
    }

    @Override
    public ShowDTO getSelectedShow(String showId) {
        ShowEntity show = showDao.findById(showId)
                .orElseThrow(() -> new DataNotFoundException("Show not found"));
        return toDTO(show);
    }

    @Override
    public List<ShowDTO> getAllShows() {
        return showDao.findAll().stream().map(this::toDTO).toList();
    }

    private ShowDTO toDTO(ShowEntity show) {
        return new ShowDTO(show.getShowId(), show.getMovie().getMovieId(),
                show.getTheatre().getTheatreId(), show.getShowDate(),
                show.getShowTime(), show.getTicketPrice(), show.getStatus());
    }
    
    @Override
    public void deleteShow(String showId) {
        ShowEntity show = showDao.findById(showId)
            .orElseThrow(() -> new DataNotFoundException("Show not found"));
        showDao.delete(show);
    }
    
    @Override
    public void updateShow(String showId, ShowDTO dto) {
        ShowEntity show = showDao.findById(showId)
            .orElseThrow(() -> new DataNotFoundException("Show not found"));
        
        MovieEntity movie = movieDao.findById(dto.getMovieId())
        .orElseThrow(() -> new DataNotFoundException("Movie not found"));
        TheatreEntity theatre = theatreDao.findById(dto.getTheatreId())
        .orElseThrow(() -> new DataNotFoundException("Theatre not found"));

        if (movie.getStatus() == MovieStatus.ENDED) {
            throw new BadRequestException("Cannot create a show for an ended movie");
        }
        if (theatre.getStatus() != TheatreStatus.ACTIVE) {
            throw new BadRequestException("Shows can only be created for active theatres");
        }

        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowDate(dto.getShowDate());
        show.setShowTime(dto.getShowTime());
        show.setTicketPrice(dto.getTicketPrice());
        if (dto.getStatus() != null) {
            show.setStatus(dto.getStatus());
        }
        showDao.save(show);
    }

}
