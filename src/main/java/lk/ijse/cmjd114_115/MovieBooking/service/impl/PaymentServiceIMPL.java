/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dao.BookingDao;
import lk.ijse.cmjd114_115.MovieBooking.dao.PaymentDao;
import lk.ijse.cmjd114_115.MovieBooking.dto.PaymentDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.PaymentRequestDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.BookingStatus;
import lk.ijse.cmjd114_115.MovieBooking.dto.enums.PaymentStatus;
import lk.ijse.cmjd114_115.MovieBooking.entities.BookingEntity;
import lk.ijse.cmjd114_115.MovieBooking.entities.PaymentEntity;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.DataNotFoundException;
import lk.ijse.cmjd114_115.MovieBooking.exceptions.InvalidRequestException;
import lk.ijse.cmjd114_115.MovieBooking.service.PaymentService;
import lk.ijse.cmjd114_115.MovieBooking.util.IDGenerate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PaymentServiceIMPL implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceIMPL.class);

    private final PaymentDao paymentDao;
    private final BookingDao bookingDao;

    @Override
    public PaymentDTO processPayment(PaymentRequestDTO request) {

        logger.info("Processing payment for booking {}", request.getBookingId());

        BookingEntity booking = bookingDao.findById(request.getBookingId())
                .orElseThrow(() -> new DataNotFoundException("Booking not found"));

        checkOwnerOrAdmin(booking);

        
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            logger.warn("Payment rejected: booking {} is cancelled", booking.getBookingId());
            throw new InvalidRequestException("Cancelled bookings cannot be paid");
        }

        PaymentEntity payment = paymentDao.findByBooking_BookingId(booking.getBookingId()).orElse(null);

        
        if (payment != null && payment.getStatus() == PaymentStatus.COMPLETED) {
            logger.warn("Payment rejected: booking {} is already paid", booking.getBookingId());
            throw new InvalidRequestException("Payment is already completed for this booking");
        }

        
        if (payment == null) {
            payment = new PaymentEntity();
            payment.setPaymentId(IDGenerate.paymentId());
            payment.setBooking(booking);
        }
        payment.setAmount(booking.getTotalAmount());
        payment.setMethod(request.getMethod());
        payment.setPaymentDate(LocalDateTime.now());

        if (request.isSimulateFailure()) {
            payment.setStatus(PaymentStatus.FAILED);          
        } else {
            payment.setStatus(PaymentStatus.COMPLETED);
            booking.setStatus(BookingStatus.CONFIRMED);       
            bookingDao.save(booking);
        }

        PaymentEntity saved = paymentDao.save(payment);
        logger.info("Payment {} for booking {} is {}", saved.getPaymentId(), booking.getBookingId(), saved.getStatus());
        return toDTO(saved);
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentDao.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public PaymentDTO getPaymentById(String paymentId) {
        PaymentEntity payment = paymentDao.findById(paymentId)
                .orElseThrow(() -> new DataNotFoundException("Payment not found"));
        checkOwnerOrAdmin(payment.getBooking());
        return toDTO(payment);
    }

    private void checkOwnerOrAdmin(BookingEntity booking) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && !booking.getUser().getEmail().equals(auth.getName())) {
            logger.warn("Access denied for {} on booking {}", auth.getName(), booking.getBookingId());
            throw new AccessDeniedException("You can only access your own bookings");
        }
    }

    private PaymentDTO toDTO(PaymentEntity p) {
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(p.getPaymentId());
        dto.setBookingId(p.getBooking().getBookingId());
        dto.setPaymentDate(p.getPaymentDate());
        dto.setAmount(p.getAmount());
        dto.setStatus(p.getStatus());
        dto.setMethod(p.getMethod());
        dto.setBookingStatus(p.getBooking().getStatus());
        return dto;
    }
}