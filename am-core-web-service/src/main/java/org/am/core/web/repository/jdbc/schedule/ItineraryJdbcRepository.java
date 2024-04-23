package org.am.core.web.repository.jdbc.schedule;

import org.am.core.web.dto.schedule.ItineraryDto;

import java.util.List;

public interface ItineraryJdbcRepository {
    List<ItineraryDto> getItinerariesByAreaId(Integer areaId);
}
