package org.am.core.web.repository.jpa.schedule;

import org.am.core.web.domain.entity.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
