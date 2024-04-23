package org.am.core.web.dto.admingeneral;

import java.time.LocalTime;

public record ScheduleParametersDto(Integer areaId, String nameArea, String initialsArea,
                                    Boolean mondaySchedule, Boolean tuesdaySchedule,
                                    Boolean wednesdaySchedule, Boolean thursdaySchedule,
                                    Boolean fridaySchedule, Boolean saturdaySchedule,
                                    Boolean sundaySchedule, LocalTime startTimeSchedule,
                                    LocalTime endTimeSchedule, Integer timeIntervalSchedule) {
}
