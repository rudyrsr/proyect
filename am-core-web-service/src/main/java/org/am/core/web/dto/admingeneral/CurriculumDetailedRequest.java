package org.am.core.web.dto.admingeneral;

import java.time.LocalDate;
import java.util.List;

public record CurriculumDetailedRequest(Integer curriculumId,
                                        String name,
                                        Short minApprovedSubjects,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        List<LevelRequest> levelList) {
}
