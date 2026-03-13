package com.sunpark.ticketflow.Mapper;

import com.sunpark.ticketflow.Entity.SeatsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeatsMapper {
    void bulkInsert(List<SeatsEntity> seatsEntityList);
}
