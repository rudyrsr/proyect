package org.am.core.web.service.professor.impl;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Area;
import org.am.core.web.domain.entity.professor.AreaProfessor;
import org.am.core.web.domain.entity.professor.AreaProfessorId;
import org.am.core.web.domain.entity.professor.Professor;
import org.am.core.web.dto.professor.ProfessorDto;
import org.am.core.web.dto.professor.ProfessorRequest;
import org.am.core.web.repository.jdbc.professor.ProfessorJdbcRepository;
import org.am.core.web.repository.jpa.professor.AreaProfessorRepository;
import org.am.core.web.repository.jpa.professor.ProfessorRepository;
import org.am.core.web.service.professor.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorRepository professorRepository;
    private final ProfessorJdbcRepository professorJdbcRepository;
    private AreaProfessorRepository areaProfessorRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<ProfessorDto> getById(Integer id) {
        return professorRepository.findById(id).map(this::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProfessorDto> listProfessorByArea(Integer areaId) {
        return professorJdbcRepository.getProfessorsByAreaIdAndActive(areaId,Boolean.TRUE);
    }

    @Override
    public ProfessorDto save(ProfessorRequest professorRequest) {
        Professor professor = new Professor(
                professorRequest.name(),
                professorRequest.lastName(),
                professorRequest.secondLastName(),
                professorRequest.email()
        );
        professorRepository.save(professor);

        Area area = new Area();
        area.setId(professorRequest.areaId());

        AreaProfessor areaProfessor = new AreaProfessor();
        areaProfessor.setAreaProfessorId(new AreaProfessorId(professor.getId(), area.getId()));
        areaProfessor.setActive(Boolean.TRUE);
        areaProfessor.setCreationDate(LocalDate.now());
        areaProfessor.setArea(area);
        areaProfessor.setProfessor(professor);
        areaProfessorRepository.save(areaProfessor);

        return toDto(professor);
    }

    @Override
    public ProfessorDto edit(ProfessorDto professorDto) {
        Professor professorFromDb = professorRepository.findById(professorDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid professor Id: " + professorDto.id()));
        professorFromDb.setName(professorDto.name());
        professorFromDb.setLastName(professorDto.lastName());
        professorFromDb.setSecondLastName(professorDto.secondLastName());
        professorFromDb.setEmail(professorDto.email());

        return toDto(professorRepository.save(professorFromDb));
    }

    @Override
    public void delete(Integer areaId, Integer professorId) {
        professorRepository.deleteById(professorId);
        areaProfessorRepository.deleteAreaProfessorByArea_IdAndProfessorId(areaId, professorId);
    }

    @Override
    public void logicalDelete(Integer areaId, Integer professorId) {
        areaProfessorRepository.logicalDeleteByAreaIdAndProfessorId(areaId, professorId);
    }

    @Autowired
    public void setAreaProfessorRepository(AreaProfessorRepository areaProfessorRepository) {
        this.areaProfessorRepository = areaProfessorRepository;
    }
}
