package org.am.core.web.dto.schedule;

import java.util.List;

public record GroupRequest (Integer itineraryId,
                            Integer curriculumId,
                            Integer subjectId,
                            String identifier,
                            String remark,
                            List<ScheduleRequest> listSchedule){
}
