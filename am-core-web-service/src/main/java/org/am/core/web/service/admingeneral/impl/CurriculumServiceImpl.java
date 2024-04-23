package org.am.core.web.service.admingeneral.impl;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Career;
import org.am.core.web.domain.entity.admingeneral.Curriculum;
import org.am.core.web.domain.entity.admingeneral.Subject;
import org.am.core.web.domain.entity.schedule.SubjectCurriculum;
import org.am.core.web.domain.entity.schedule.SubjectCurriculumId;
import org.am.core.web.dto.admingeneral.CurriculumDetailedDto;
import org.am.core.web.dto.admingeneral.CurriculumDetailedRequest;
import org.am.core.web.dto.admingeneral.CurriculumDto;
import org.am.core.web.dto.admingeneral.CurriculumRequest;
import org.am.core.web.dto.admingeneral.LevelRequest;
import org.am.core.web.dto.admingeneral.SubjectCurriculumRequest;
import org.am.core.web.repository.jpa.admingeneral.CurriculumRepository;
import org.am.core.web.service.admingeneral.CurriculumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CurriculumServiceImpl implements CurriculumService {
    private final CurriculumRepository curriculumRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CurriculumDto> getCurriculumsByCareerId(Integer careerId) {
        return curriculumRepository.findCurriculumByCareer_IdAndActiveOrderByName(careerId, Boolean.TRUE)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public CurriculumDto save(CurriculumRequest curriculumRequest) {
        Curriculum savedCurriculum = curriculumRepository.save(toEntity(curriculumRequest));
        List<SubjectCurriculum> subjectCurriculumList = createSubjectCurriculums(curriculumRequest, savedCurriculum);
        savedCurriculum.setSubjectCurriculumList(subjectCurriculumList);
        return toDto(curriculumRepository.save(savedCurriculum));
    }


    @Override
    public CurriculumDto edit(CurriculumDetailedRequest dto) {
        Curriculum curriculumFromDb = curriculumRepository.findById(dto.curriculumId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id for curriculumId"));
        curriculumFromDb.setName(dto.name());
        curriculumFromDb.setMinApprovedSubjeccts(dto.minApprovedSubjects());
        curriculumFromDb.setStarDate(dto.startDate());
        curriculumFromDb.setEndDate(dto.endDate());

        List<SubjectCurriculum> newSubjectCurriculumList = new ArrayList<>();
        List<SubjectCurriculum> subjectCurriculumList = curriculumFromDb.getSubjectCurriculumList();
        List<SubjectCurriculum> subjectCurriculumListToDelete = curriculumFromDb.getSubjectCurriculumList();

        for (LevelRequest levelRequest : dto.levelList()) {
            for (SubjectCurriculumRequest subjectCurriculumRequest : levelRequest.subjectCurriculumList()) {

                Optional<SubjectCurriculum> subjectCurriculumOptional = subjectCurriculumList
                        .stream()
                        .filter(obj -> obj.getSubjectCurriculumId().getCurriculumId().equals(dto.curriculumId())
                                && obj.getSubjectCurriculumId().getSubjectId().equals(subjectCurriculumRequest.subjectId()))
                        .findFirst();
                if (subjectCurriculumOptional.isPresent()) {
                    SubjectCurriculum subjectCurriculum = subjectCurriculumOptional.get();
                    subjectCurriculum.setOptional(subjectCurriculumRequest.optional());
                    subjectCurriculum.setPath(subjectCurriculumRequest.path());
                    subjectCurriculum.setWorkload(subjectCurriculumRequest.workload());

                    newSubjectCurriculumList.add(subjectCurriculum);

                    subjectCurriculumListToDelete.remove(subjectCurriculum);
                } else {
                    newSubjectCurriculumList.add(getSubjectCurriculumInstance(subjectCurriculumRequest,
                            levelRequest, dto.curriculumId()));
                }
            }
        }

        curriculumFromDb.setSubjectCurriculumList(newSubjectCurriculumList);
        Curriculum curriculumEdited = curriculumRepository.save(curriculumFromDb);

        // delete subject curriculum that was deleted from UI on edit
        if (!subjectCurriculumListToDelete.isEmpty()) {
            List<Integer> subjectIds = subjectCurriculumListToDelete.stream()
                    .map(obj -> obj.getSubjectCurriculumId().getSubjectId())
                    .toList();
            curriculumRepository.deleteSubjectCurriculumList(dto.curriculumId(), subjectIds);
        }

        return toDto(curriculumEdited);
    }

    @Override
    public void delete(Integer id) {
        curriculumRepository.deleteById(id);
    }

    @Override
    public void logicalDelete(Integer id) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        curriculum.setActive(Boolean.FALSE);

        for (SubjectCurriculum subjectCurriculum : curriculum.getSubjectCurriculumList()) {
            subjectCurriculum.setActive(false);
        }
        curriculumRepository.save(curriculum);
    }

    @Override
    public Optional<CurriculumDto> getById(Integer id) {
        return curriculumRepository.findById(id).map(this::toDto);
    }

    @Override
    public Optional<CurriculumDetailedDto> getDetailedById(Integer id) {
        return curriculumRepository.findById(id).map(this::toCurriculumDetailedDto);
    }

    public Curriculum toEntity(CurriculumRequest curriculumRequest) {
        Curriculum curriculum = new Curriculum();
        curriculum.setName(curriculumRequest.name());
        curriculum.setMinApprovedSubjeccts(curriculumRequest.minApprovedSubjects());
        curriculum.setStarDate(curriculumRequest.startDate());
        curriculum.setEndDate(curriculumRequest.endDate());
        curriculum.setActive(Boolean.TRUE);

        Career career = new Career();
        career.setId(curriculumRequest.careerId());
        curriculum.setCareer(career);

        return curriculum;
    }

    private List<SubjectCurriculum> createSubjectCurriculums(CurriculumRequest curriculumRequest, Curriculum savedCurriculum) {
        List<SubjectCurriculum> subjectCurriculumList = new ArrayList<>();
        for (LevelRequest levelRequest : curriculumRequest.levelList()) {
            for (SubjectCurriculumRequest subjectCurriculumRequest : levelRequest.subjectCurriculumList()) {
                SubjectCurriculum subjectCurriculum = getSubjectCurriculum(levelRequest, subjectCurriculumRequest, savedCurriculum);
                subjectCurriculumList.add(subjectCurriculum);
            }
        }
        return subjectCurriculumList;
    }

    private static SubjectCurriculum getSubjectCurriculum(LevelRequest levelRequest,
                                                          SubjectCurriculumRequest subjectCurriculumRequest,
                                                          Curriculum savedCurriculum) {
        SubjectCurriculum subjectCurriculum = new SubjectCurriculum();

        Subject subject = new Subject();
        subject.setId(subjectCurriculumRequest.subjectId());
        subjectCurriculum.setSubject(subject);

        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId(
                savedCurriculum.getId(),
                subject.getId()

        );
        subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);
        subjectCurriculum.setLevelName(levelRequest.levelName().trim());
        subjectCurriculum.setLevel(levelRequest.levelIdentifier());
        subjectCurriculum.setPath(subjectCurriculumRequest.path());
        subjectCurriculum.setWorkload(subjectCurriculumRequest.workload());
        subjectCurriculum.setOptional(subjectCurriculumRequest.optional());
        subjectCurriculum.setActive(Boolean.TRUE);
        subjectCurriculum.setCurriculum(savedCurriculum);

        return subjectCurriculum;
    }

    private static SubjectCurriculum getSubjectCurriculumInstance(SubjectCurriculumRequest subjectCurriculumRequest,
                                                                  LevelRequest levelRequest, Integer curriculumId) {
        Curriculum curriculum = new Curriculum();
        curriculum.setId(curriculumId);

        Subject subject = new Subject();
        subject.setId(subjectCurriculumRequest.subjectId());

        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId(
                curriculum.getId(),
                subject.getId()
        );
        return new SubjectCurriculum(
                subjectCurriculumId,
                levelRequest.levelIdentifier(),
                subjectCurriculumRequest.optional(),
                subjectCurriculumRequest.path(),
                subjectCurriculumRequest.workload(),
                Boolean.TRUE,
                levelRequest.levelName(),
                curriculum,
                subject
        );
    }
}
