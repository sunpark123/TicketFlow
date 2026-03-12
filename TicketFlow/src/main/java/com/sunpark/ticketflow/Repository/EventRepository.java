package com.sunpark.ticketflow.Repository;

import com.sunpark.ticketflow.Entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer>{
    @Query("""
        SELECT e FROM EventEntity e
        ORDER BY
            CASE e.status
                WHEN 'OPEN' THEN 1
                WHEN 'ENROLLMENT' THEN 2
                WHEN 'SOLD_OUT'   THEN 3
                WHEN 'CLOSED'     THEN 4
                WHEN 'COMPLETED'  THEN 5
                WHEN 'CANCELLED'  THEN 6
            END ASC
    """)
    List<EventEntity> findAllOrderByStatus();
}

