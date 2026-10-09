/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service;

import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dto.ShowDTO;

/**
 *
 * @author User
 */
public interface ShowService {
    void createShow(ShowDTO show);
    ShowDTO getSelectedShow(String showId);
    List<ShowDTO> getAllShows();
    void updateShow(String showId, ShowDTO show);
    void deleteShow(String showId);
}
