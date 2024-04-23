package org.am.core.web.dto.schedule;

import java.time.LocalTime;

public record ScheduleDto(Integer id,
                          String dayOfWeek,
                          LocalTime startTime,
                          LocalTime endTime,
                          String classroomName,
                          String classroomInitials,
                          String professorFullName) {
}
