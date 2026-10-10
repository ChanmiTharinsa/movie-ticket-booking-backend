/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dao.BookingDao;
import lk.ijse.cmjd114_115.MovieBooking.dao.ShowDao;
import lk.ijse.cmjd114_115.MovieBooking.dao.UserDao;
import lk.ijse.cmjd114_115.MovieBooking.dto.BookingDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.BookingRequestDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.BookingStatus;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.ShowStatus;
import lk.ijse.cmjd114_115.MovieBooking.entities.BookingEntity;
import lk.ijse.cmjd114_115.MovieBooking.entities.ShowEntity;
import lk.ijse.cmjd114_115.MovieBooking.entities.UserEntity;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DataNotFoundException;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DuplicateDataException;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.InvalidRequestException;
import lk.ijse.cmjd114_115.MovieBooking.service.BookingService;
import lk.ijse.cmjd114_115.MovieBooking.util.IDGenerate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BookingServiceIMPL implements BookingService {

    private final BookingDao bookingDao;
    private final ShowDao showDao;
    private final UserDao userDao;

    @Override
    public BookingDTO createBooking(BookingRequestDTO request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userDao.findByEmail(email)
            .orElseThrow(() -> new DataNotFoundException("User not found"));

        ShowEntity show = showDao.findById(request.getShowId())
            .orElseThrow(() -> new DataNotFoundException("Show not found"));
        if (show.getStatus() != ShowStatus.SCHEDULED) {
            throw new InvalidRequestException("Cancelled or completed shows cannot be booked");
        }

        if (request.getSeatNumbers().size() != request.getNumberOfTickets()) {
            throw new InvalidRequestException("Number of tickets must match the number of seats");
        }
        if (request.getSeatNumbers().stream().distinct().count() != request.getSeatNumbers().size()) {
            throw new InvalidRequestException("Duplicate seats in the request");
        }

        if (bookingDao.existsSeatConflict(show.getShowId(), BookingStatus.CANCELLED, request.getSeatNumbers())) {
            throw new DuplicateDataException("One or more seats are already booked");
        }

        BigDecimal total = show.getTicketPrice().multiply(BigDecimal.valueOf(request.getNumberOfTickets()));

        BookingEntity booking = new BookingEntity();
        booking.setBookingId(IDGenerate.bookingId());
        booking.setUser(user);
        booking.setShow(show);
        booking.setNumberOfTickets(request.getNumberOfTickets());
        booking.setSeatNumbers(request.getSeatNumbers());
        booking.setBookingDate(LocalDateTime.now());
        booking.setTotalAmount(total);
        booking.setStatus(BookingStatus.PENDING);

        return toDTO(bookingDao.save(booking));
    }
    
    @Override
    public List<BookingDTO> getMyBookings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookingDao.findByUser_Email(email)
            .stream()
            .map(this::toDTO)
            .toList();
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingDao.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }
    
    private void checkOwnerOrAdmin(BookingEntity booking) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && !booking.getUser().getEmail().equals(auth.getName())) {
            throw new AccessDeniedException("You can only access your own bookings");
        }
    }

    @Override
    public BookingDTO getBookingById(String bookingId) {
        BookingEntity booking = bookingDao.findById(bookingId)
            .orElseThrow(() -> new DataNotFoundException("Booking not found"));
        checkOwnerOrAdmin(booking);
        return toDTO(booking);
    }
    

    private BookingDTO toDTO(BookingEntity b) {
        BookingDTO dto = new BookingDTO();
        dto.setBookingId(b.getBookingId());
        dto.setUserId(b.getUser().getUserId());
        dto.setShowId(b.getShow().getShowId());
        dto.setNumberOfTickets(b.getNumberOfTickets());
        dto.setSeatNumbers(b.getSeatNumbers());
        dto.setBookingDate(b.getBookingDate());
        dto.setTotalAmount(b.getTotalAmount());
        dto.setStatus(b.getStatus());
        return dto;
    }
    
    @Override
    public BookingDTO cancelBooking(String bookingId) {
        BookingEntity booking = bookingDao.findById(bookingId)
            .orElseThrow(() -> new DataNotFoundException("Booking not found"));

        checkOwnerOrAdmin(booking);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new InvalidRequestException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return toDTO(bookingDao.save(booking));
    }
}
