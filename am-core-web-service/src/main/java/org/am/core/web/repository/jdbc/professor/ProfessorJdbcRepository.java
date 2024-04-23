package org.am.core.web.repository.jdbc.professor;

import org.am.core.web.dto.professor.ProfessorDto;

import java.util.List;

public interface ProfessorJdbcRepository {
    List<ProfessorDto> getProfessorsByAreaIdAndActive(Integer areaId, Boolean active);
}
