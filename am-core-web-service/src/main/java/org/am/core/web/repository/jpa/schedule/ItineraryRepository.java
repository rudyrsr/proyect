package org.am.core.web.repository.jpa.schedule;

import org.am.core.web.domain.entity.schedule.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
    List<Itinerary> findAllByActiveOrderByName(Boolean active);
}
