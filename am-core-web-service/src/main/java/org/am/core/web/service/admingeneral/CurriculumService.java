package org.am.core.web.service.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Curriculum;
import org.am.core.web.domain.entity.schedule.SubjectCurriculum;
import org.am.core.web.dto.admingeneral.CurriculumDetailedDto;
import org.am.core.web.dto.admingeneral.CurriculumDetailedRequest;
import org.am.core.web.dto.admingeneral.CurriculumDto;
import org.am.core.web.dto.admingeneral.CurriculumRequest;
import org.am.core.web.dto.admingeneral.LevelDto;
import org.am.core.web.dto.admingeneral.SubjectCurriculumDetailedDto;
import org.am.core.web.service.compators.ComparatorLevelCurriculum;

import java.util.*;
import java.util.stream.Collectors;

import static org.am.core.web.util.UtilConstants.UNDERSCORE_CHARACTER;

public interface CurriculumService {
    List<CurriculumDto> getCurriculumsByCareerId(Integer careerId);
    CurriculumDto save(CurriculumRequest curriculumRequest);
    CurriculumDto edit(CurriculumDetailedRequest dto);
    void delete(Integer id);
    void logicalDelete(Integer id);
    Optional<CurriculumDto> getById(Integer id);
    Optional<CurriculumDetailedDto> getDetailedById(Integer id);

    default CurriculumDto toDto(Curriculum curriculum) {
        return new CurriculumDto(
                curriculum.getId(),
                curriculum.getName().trim(),
                curriculum.getMinApprovedSubjeccts(),
                curriculum.getStarDate(),
                curriculum.getEndDate()
        );
    }

    default CurriculumDetailedDto toCurriculumDetailedDto(Curriculum curriculum) {
        List<SubjectCurriculum> subjectCurriculumList = curriculum.getSubjectCurriculumList();

        Map<String, List<SubjectCurriculumDetailedDto>> map = subjectCurriculumList.stream()
                .collect(Collectors.groupingBy(obj -> obj.getLevel() + "_" + obj.getLevelName(),
                        Collectors.mapping(sc -> new SubjectCurriculumDetailedDto(
                                sc.getSubjectCurriculumId().getCurriculumId(),
                                sc.getSubjectCurriculumId().getSubjectId(),
                                sc.getSubject().getName(),
                                sc.getSubject().getInitials(),
                                sc.getOptional(),
                                sc.getPath(),
                                sc.getWorkload()
                        ), Collectors.toList())));

        List<LevelDto> levelDtoList = new ArrayList<>();
        for (Map.Entry<String, List<SubjectCurriculumDetailedDto>> entry : map.entrySet()) {
            int index = entry.getKey().indexOf(UNDERSCORE_CHARACTER);
            LevelDto levelDto = new LevelDto(
                    Short.parseShort(entry.getKey().substring(0, index)),
                    entry.getKey().substring(index + 1),
                    entry.getValue()
            );
            levelDtoList.add(levelDto);
        }

        levelDtoList.sort(new ComparatorLevelCurriculum());

        return new CurriculumDetailedDto(
                curriculum.getId(),
                curriculum.getCareer().getName(),
                curriculum.getCareer().getInitials(),
                curriculum.getName(),
                curriculum.getMinApprovedSubjeccts(),
                curriculum.getStarDate(),
                curriculum.getEndDate(),
                levelDtoList
        );
    }
}