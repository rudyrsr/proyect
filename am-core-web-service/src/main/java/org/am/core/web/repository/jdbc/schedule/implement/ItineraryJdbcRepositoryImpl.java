package org.am.core.web.repository.jdbc.schedule.implement;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.ItineraryDto;
import org.am.core.web.repository.jdbc.schedule.ItineraryJdbcRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItineraryJdbcRepositoryImpl implements ItineraryJdbcRepository {

    private final JdbcClient jdbcClient;
    @Override
    public List<ItineraryDto> getItinerariesByAreaId(Integer areaId) {
        return jdbcClient.sql("""
                select i.id,
                    i.name,
                    c.id as careerId,
                    c.name as careerName,
                    cu.id as curriculumId,
                    cu.name as curriculumName
                from itinerary i
                inner join curriculum cu on i.curriculum_id = cu.id
                inner join career c on cu.career_id = c.id
                where c.area_id= ? and i.active=true
                order by c.name, i.name
                """)
                .param(areaId)
                .query(ItineraryDto.class)
                .list();
    }
}
