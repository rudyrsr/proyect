package org.am.core.web.service.admingeneral;


import org.am.core.web.domain.entity.admingeneral.Area;
import org.am.core.web.domain.entity.admingeneral.Subject;
import org.am.core.web.dto.admingeneral.SubjectDto;
import org.am.core.web.dto.admingeneral.SubjectRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.SubjectRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class SubjectService implements CustomMap<SubjectDto, Subject> {
    private final SubjectRepository subjectRepository;


    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional(readOnly = true)
    public List<SubjectDto> getActiveSubjectsByAreaId(Integer areaID) {
        return subjectRepository.findAllByAreaIdAndActiveOrderById(areaID, Boolean.TRUE)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<SubjectDto> getSubjectById(Integer id) {
        return subjectRepository.findById(id).map(this::toDto);
    }

    public SubjectDto save(SubjectRequest subjectRequest) {
        return toDto(subjectRepository.save(this.toEntity(subjectRequest)));
    }

    public SubjectDto edit(SubjectDto subjectDto) {
        Subject subjectFromDB = subjectRepository.findById(subjectDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        subjectFromDB.setName(subjectDto.name());
        subjectFromDB.setInitials(subjectDto.initials());

        return toDto(subjectRepository.save(subjectFromDB));
    }

    public void delete(Integer id) {
        try {
            subjectRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // logical delete
            Subject subjectFromDB = subjectRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
            subjectFromDB.setActive(Boolean.FALSE);
            subjectRepository.save(subjectFromDB);
        }

    }

    @Override
    public SubjectDto toDto(Subject subject) {
        return new SubjectDto(
                subject.getId(),
                subject.getName(),
                subject.getInitials()
        );
    }

    @Override
    public Subject toEntity(SubjectDto subjectDto) {
        return null;
    }

    private Subject toEntity(SubjectRequest subjectRequest) {
        Subject subject = new Subject();
        subject.setInitials(subjectRequest.initials());
        subject.setName(subjectRequest.name());
        subject.setActive(Boolean.TRUE);

        Area area= new Area();
        area.setId(subjectRequest.areaID());
        subject.setArea(area);

        return subject;
    }
}
