package org.am.core.web.repository.jpa.schedule;

import org.am.core.web.domain.entity.schedule.GroupItinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupItineraryRepository extends JpaRepository<GroupItinerary, Integer> {

}
