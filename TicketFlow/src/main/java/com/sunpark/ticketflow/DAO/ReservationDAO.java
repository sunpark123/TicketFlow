package com.sunpark.ticketflow.DAO;

import com.sunpark.ticketflow.DTO.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationDAO {
    int reservationConfirm(ReservationDTO reservationDTO);
}
