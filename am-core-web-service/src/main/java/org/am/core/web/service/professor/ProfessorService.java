package org.am.core.web.service.professor;

import org.am.core.web.domain.entity.professor.Professor;
import org.am.core.web.dto.professor.ProfessorDto;
import org.am.core.web.dto.professor.ProfessorRequest;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {
    Optional<ProfessorDto> getById(Integer id);
    List<ProfessorDto> listProfessorByArea(Integer areaId);
    ProfessorDto save(ProfessorRequest professorRequest);
    ProfessorDto edit(ProfessorDto professorDto);
    void delete(Integer areaId, Integer professorId);

    void logicalDelete(Integer areaId, Integer id);

    default ProfessorDto toDto(Professor professor) {
        return new ProfessorDto(
                professor.getId(),
                professor.getName(),
                professor.getLastName(),
                professor.getSecondLastName(),
                professor.getEmail()
        );
    }
}
