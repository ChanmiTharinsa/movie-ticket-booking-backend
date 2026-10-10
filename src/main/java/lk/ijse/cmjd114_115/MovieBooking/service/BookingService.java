/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service;

import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dto.BookingDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.BookingRequestDTO;

/**
 *
 * @author User
 */

public interface BookingService {

    BookingDTO createBooking(BookingRequestDTO request);

    List<BookingDTO> getMyBookings();

    List<BookingDTO> getAllBookings();

    BookingDTO getBookingById(String bookingId);

    BookingDTO cancelBooking(String bookingId);
}
