package org.am.core.web.dto.schedule;

import java.time.LocalTime;
import java.util.List;

public record GroupDetailedDto(Integer id,
                               String groupIdentifier,
                               String remark,
                               Integer curriculumId,
                               Integer SubjectId,
                               Integer itineraryId,
                               Integer professorId,
                               Integer classroomId,
                               List<ScheduleDetailedDto> listScheduleDto) {
}
