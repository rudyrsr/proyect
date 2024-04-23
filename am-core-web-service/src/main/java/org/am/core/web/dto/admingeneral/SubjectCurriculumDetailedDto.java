package org.am.core.web.dto.admingeneral;

public record SubjectCurriculumDetailedDto(
        Integer curriculumId,
        Integer subjectId,
        String subjectName,
        String subjectInitials,
        Boolean optional,
        String path,
        Short workload
) {
}
