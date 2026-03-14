package com.sunpark.ticketflow.DAO;

import com.sunpark.ticketflow.DTO.ReservationDTO;
import com.sunpark.ticketflow.Entity.SeatsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeatsDAO {
    void bulkInsert(List<SeatsEntity> seatsEntityList);
    void seatConfirm(ReservationDTO reservationDTO);
}
