/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.cmjd114_115.MovieBooking.util;

/**
 *
 * @author User
 */

import lk.ijse.cmjd114_115.MovieBooking.dto.MovieDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.TheatreDTO;
import lk.ijse.cmjd114_115.MovieBooking.dto.UserDTO;
import lk.ijse.cmjd114_115.MovieBooking.entities.MovieEntity;
import lk.ijse.cmjd114_115.MovieBooking.entities.TheatreEntity;
import lk.ijse.cmjd114_115.MovieBooking.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Conversion {

    private final ModelMapper modelMapper;

    public MovieDTO toMovieDTO(MovieEntity movie) {
        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieEntity toMovieEntity(MovieDTO dto) {
        return modelMapper.map(dto, MovieEntity.class);
    }
    
    public UserDTO toUserDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserEntity toUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }
    
    public TheatreDTO toTheatreDTO(TheatreEntity theatreEntity) {
        return modelMapper.map(theatreEntity, TheatreDTO.class);
    }

    public TheatreEntity toTheatreEntity(TheatreDTO theatreDTO) {
        return modelMapper.map(theatreDTO, TheatreEntity.class);
    }
}
