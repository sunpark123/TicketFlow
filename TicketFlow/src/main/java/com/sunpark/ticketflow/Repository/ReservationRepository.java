package com.sunpark.ticketflow.Repository;

import com.sunpark.ticketflow.Entity.EventEntity;
import com.sunpark.ticketflow.Entity.ReservationEntity;
import com.sunpark.ticketflow.Entity.SeatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer>{
}

