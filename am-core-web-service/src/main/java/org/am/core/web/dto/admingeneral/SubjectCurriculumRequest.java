package org.am.core.web.dto.admingeneral;

public record SubjectCurriculumRequest(
        Integer subjectId,
        Boolean optional,
        String path,
        Short workload
) {
}
