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

    private final PaymentDao paymentDao;
    private final BookingDao bookingDao;

    @Override
    public PaymentDTO processPayment(PaymentRequestDTO request) {

        BookingEntity booking = bookingDao.findById(request.getBookingId())
                .orElseThrow(() -> new DataNotFoundException("Booking not found"));

        checkOwnerOrAdmin(booking);

       
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new InvalidRequestException("Cancelled bookings cannot be paid");
        }

        PaymentEntity payment = paymentDao.findByBooking_BookingId(booking.getBookingId()).orElse(null);

        
        if (payment != null && payment.getStatus() == PaymentStatus.COMPLETED) {
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

        return toDTO(paymentDao.save(payment));
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
