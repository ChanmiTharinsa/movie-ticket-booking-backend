/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.service;

import java.util.List;
import lk.ijse.cmjd114_115.MovieBooking.dto.PaymentDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.PaymentRequestDTO;

/**
 *
 * @author User
 */
public interface PaymentService {
    PaymentDTO processPayment(PaymentRequestDTO request);
    List<PaymentDTO> getAllPayments();
    PaymentDTO getPaymentById(String paymentId);
}
