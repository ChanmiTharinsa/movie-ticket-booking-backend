/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.util;

import java.util.UUID;

/**
 *
 * @author User
 */
public class IDGenerate {
    public static String movieId(){
        return "MV-"+UUID.randomUUID();
    }
    public static String theatreId(){
        return "TH-"+UUID.randomUUID();
    }
    public static String showId(){
        return "SH-"+UUID.randomUUID();
    }
    public static String userId(){
        return "US-"+UUID.randomUUID();
    }
    public static String bookingId(){
        return "BK-"+UUID.randomUUID();
    }
    public static String paymentId(){
        return "PY-"+UUID.randomUUID();
    }

}
