/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service.impl;

/**
 *
 * @author User
 */
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lk.ijse.cmjd114_115.MovieBooking.dao.TheatreDao;
import lk.ijse.cmjd114_115.MovieBooking.entities.TheatreEntity;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DataNotFoundException;
import lk.ijse.cmjd114_115.MovieBooking.dto.TheatreDTO;
import lk.ijse.cmjd114_115.MovieBooking.service.TheatreService;
import lk.ijse.cmjd114_115.MovieBooking.util.Conversion;
import lk.ijse.cmjd114_115.MovieBooking.util.IDGenerate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional

public class TheatreServiceIMPL implements TheatreService {
    
    private final TheatreDao theatreDao;
    private final Conversion conversion;

     @Override
    public void createTheatre(TheatreDTO theatre) {
        theatre.setTheatreId(IDGenerate.theatreId());
       theatreDao.save(conversion.toTheatreEntity(theatre));   
    }

     @Override
    public TheatreDTO getSelectedTheatre(String theatreId) {
    TheatreEntity theatreEntity= theatreDao.findById(theatreId)
                               .orElseThrow(()-> new DataNotFoundException("theatre not found") );
        return conversion.toTheatreDTO(theatreEntity);                       
    }

    @Override
    public List<TheatreDTO> getAllTheatres() {
        return theatreDao.findAll().stream()
        .map(conversion::toTheatreDTO)
        .toList();
    }

    @Override
    public void updateTheatre(String theatreId,TheatreDTO theatre) {
       TheatreEntity foundtheatre= theatreDao.findById(theatreId)
                               .orElseThrow(()-> new DataNotFoundException("theatre not found") );
                               foundtheatre.setName(theatre.getName());
                               foundtheatre.setStatus(theatre.getStatus());
                               foundtheatre.setLocation(theatre.getLocation());
                               foundtheatre.setCapacity(theatre.getCapacity());
                               theatreDao.save(foundtheatre);
                            
    }

    @Override
    public void deleteTheatre(String theatreId) {
        TheatreEntity foundTheatre= theatreDao.findById(theatreId)
                               .orElseThrow(()-> new DataNotFoundException("theatre not found") );
                               theatreDao.delete(foundTheatre);
    }
}
