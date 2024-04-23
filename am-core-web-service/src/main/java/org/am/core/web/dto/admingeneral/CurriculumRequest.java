package org.am.core.web.dto.admingeneral;

import java.time.LocalDate;
import java.util.List;

public record CurriculumRequest(
        String name,
        Short minApprovedSubjects,
        LocalDate startDate,
        LocalDate endDate,
        Integer careerId,
        List<LevelRequest> levelList
) {
}
