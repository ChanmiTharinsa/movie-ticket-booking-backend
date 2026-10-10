/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.dao;

import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.BookingStatus;
import lk.ijse.cmjd114_115.MovieBooking.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public interface BookingDao extends JpaRepository<BookingEntity, String> {

    List<BookingEntity> findByUser_Email(String email);

    @Query("SELECT COUNT(b) > 0 FROM BookingEntity b JOIN b.seatNumbers s " +
           "WHERE b.show.showId = :showId AND b.status <> :cancelled AND s IN :seats")
    boolean existsSeatConflict(@Param("showId") String showId,
                               @Param("cancelled") BookingStatus cancelled,
                               @Param("seats") List<String> seats);
}
