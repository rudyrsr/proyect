package org.am.core.web.dto.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduleEditRequest(Integer id,
                                  LocalTime startTime,
                                  LocalTime endTime,
                                  DayOfWeek dayOfWeek,
                                  String assistant,
                                  Integer classroomId,
                                  Integer professorId) {
}