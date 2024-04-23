package org.am.core.web.dto.admingeneral;

import java.util.List;

public record LevelDto(Short levelIdentifier,
                       String levelName,
                       List<SubjectCurriculumDetailedDto> subjectCurriculumDetailedDtoList) {
}
