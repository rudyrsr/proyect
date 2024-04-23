package org.am.core.web.dto.admingeneral;

import java.util.List;

public record LevelRequest(Short levelIdentifier,
                           String levelName,
                           List<SubjectCurriculumRequest> subjectCurriculumList) {
}
