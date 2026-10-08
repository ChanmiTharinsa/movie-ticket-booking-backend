/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service;

/**
 *
 * @author User
 */

import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dto.TheatreDTO;

public interface TheatreService {
    void createTheatre(TheatreDTO theatre);
    TheatreDTO getSelectedTheatre(String theatreId);
    List<TheatreDTO> getAllTheatres();
    void updateTheatre(String theatreId, TheatreDTO theatre);
    void deleteTheatre(String theatreId);
}
