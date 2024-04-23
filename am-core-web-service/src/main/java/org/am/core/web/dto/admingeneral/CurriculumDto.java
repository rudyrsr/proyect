package org.am.core.web.dto.admingeneral;

import java.time.LocalDate;

public record CurriculumDto(Integer id,
                            String name,
                            Short minApprovedSubjects,
                            LocalDate startDate,
                            LocalDate endDate) {
}
