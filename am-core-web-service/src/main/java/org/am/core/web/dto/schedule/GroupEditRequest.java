package org.am.core.web.dto.schedule;

import java.util.List;

public record GroupEditRequest(Integer id,
                               String identifier,
                               String remark,
                               Integer curriculumId,
                               Integer subjectId,
                               Integer itineraryId,
                               List<ScheduleEditRequest> listScheduleRequestDto) {
}
