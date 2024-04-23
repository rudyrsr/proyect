package org.am.core.web.dto.admingeneral;

import java.time.LocalDate;
import java.util.List;

public record CurriculumDetailedDto(Integer curriculumId,
                                    String careerName,
                                    String careerInitials,
                                    String name,
                                    Short minApprovedSubjects,
                                    LocalDate startDate,
                                    LocalDate endDate,
                                    List<LevelDto> levelDtoList) {
}
