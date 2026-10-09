/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.dao;

/**
 *
 * @author User
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.entities.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowDao extends JpaRepository<ShowEntity, String> {
    List<ShowEntity> findByMovie_MovieId(String movieId);
    boolean existsByTheatre_TheatreIdAndShowDateAndShowTime(String theatreId, LocalDate showDate, LocalTime showTime);
}
