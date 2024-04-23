package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Subject;
import org.am.core.web.domain.entity.schedule.*;

import org.am.core.web.dto.schedule.GroupAuxDto;
import org.am.core.web.dto.schedule.GroupDetailedAuxDto;
import org.am.core.web.dto.schedule.GroupDetailedDto;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.dto.schedule.GroupRequest;
import org.am.core.web.dto.schedule.ScheduleDetailedDto;
import org.am.core.web.dto.schedule.ScheduleDto;
import org.am.core.web.repository.jdbc.schedule.GroupItineraryJdbcRepository;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.GroupItineraryRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.am.core.web.util.CommonUtils.getFullName;
import static org.am.core.web.util.UtilConstants.NOT_ASSIGNED_YET;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupItineraryService implements CustomMap<GroupDto, GroupItinerary> {

    private final GroupItineraryRepository groupItineraryRepository;
    private final ScheduleItineraryService scheduleItineraryService;
    private final GroupItineraryJdbcRepository groupItineraryJdbcRepository;

    public List<GroupDto> getItineraryGroupsByCareerAndItinerary(Integer careerId, Integer itineraryId) {
        List<GroupDto> groupDtoList = new ArrayList<>();
        GroupAuxDto previousGroupAuxDto = null;
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        for (GroupAuxDto auxDto: groupItineraryJdbcRepository.getGroupItinerariesByCareer(careerId, itineraryId)) {
            if (previousGroupAuxDto != null && previousGroupAuxDto.groupItineraryId().intValue() != auxDto.groupItineraryId().intValue()) {
                groupDtoList.add(new GroupDto(
                        previousGroupAuxDto.groupItineraryId(),
                        previousGroupAuxDto.level(),
                        previousGroupAuxDto.subjectName(),
                        previousGroupAuxDto.subjectInitials(),
                        previousGroupAuxDto.groupIdentifier(),
                        previousGroupAuxDto.groupRemark(),

                        new ArrayList<>(scheduleDtoList)));
                scheduleDtoList.clear();
            }

            String fullName = NOT_ASSIGNED_YET;
            if (auxDto.professorName() != null) {
                fullName = getFullName(auxDto.professorName(), auxDto.professorLastName(), auxDto.professorSecondLastName());
            } else if (auxDto.assistant() != null && !auxDto.assistant().isEmpty()) {
                fullName = auxDto.assistant();
            }

            scheduleDtoList.add(new ScheduleDto(
                    auxDto.scheduleItineraryId(),
                    DayOfWeek.of(auxDto.dayOfWeek()).toString(),
                    auxDto.startTime(),
                    auxDto.endTime(),
                    auxDto.classroomName(),
                    auxDto.classroomInitials(),
                    fullName
            ));
            previousGroupAuxDto = auxDto;
        }

        if (previousGroupAuxDto != null) {
            groupDtoList.add(new GroupDto(
                    previousGroupAuxDto.groupItineraryId(),
                    previousGroupAuxDto.level(),
                    previousGroupAuxDto.subjectName(),
                    previousGroupAuxDto.subjectInitials(),
                    previousGroupAuxDto.groupIdentifier(),
                    previousGroupAuxDto.groupRemark(),
                    new ArrayList<>(scheduleDtoList)));
            scheduleDtoList = null;
        }

        return groupDtoList;
    }

    public List<GroupDetailedDto> getGroupsScheduleByCareerAndItinerary(Integer careerId, Integer itineraryId) {
        List<GroupDetailedDto> groupDtoList = new ArrayList<>();
        GroupDetailedAuxDto previousGroupDetailedAuxDto = null;
        List<ScheduleDetailedDto> scheduleDtoList = new ArrayList<>();
        for (GroupDetailedAuxDto detailedAuxDto: groupItineraryJdbcRepository.getGroupItinerariesDetailedByCareer(careerId, itineraryId)) {
            if (previousGroupDetailedAuxDto != null && previousGroupDetailedAuxDto.groupItineraryId().intValue() != detailedAuxDto.groupItineraryId().intValue()) {
                groupDtoList.add(new GroupDetailedDto(
                        previousGroupDetailedAuxDto.groupItineraryId(),
                        previousGroupDetailedAuxDto.groupIdentifier(),
                        previousGroupDetailedAuxDto.groupRemark(),
                        previousGroupDetailedAuxDto.curriculumId(),
                        previousGroupDetailedAuxDto.subjectId(),
                        itineraryId,
                        previousGroupDetailedAuxDto.professorId(),
                        previousGroupDetailedAuxDto.classroomId(),
                        new ArrayList<>(scheduleDtoList)));
                scheduleDtoList.clear();
            }

            scheduleDtoList.add(new ScheduleDetailedDto(
                    detailedAuxDto.startTime(),
                    detailedAuxDto.endTime(),
                    DayOfWeek.of(detailedAuxDto.dayOfWeek()),
                    detailedAuxDto.assistant(),
                    detailedAuxDto.classroomId(),
                    detailedAuxDto.professorId(),
                    detailedAuxDto.groupItineraryId()
            ));
            previousGroupDetailedAuxDto = detailedAuxDto;
        }

        if (previousGroupDetailedAuxDto != null) {
            groupDtoList.add(new GroupDetailedDto(
                    previousGroupDetailedAuxDto.groupItineraryId(),
                    previousGroupDetailedAuxDto.groupIdentifier(),
                    previousGroupDetailedAuxDto.groupRemark(),
                    previousGroupDetailedAuxDto.curriculumId(),
                    previousGroupDetailedAuxDto.subjectId(),
                    itineraryId,
                    previousGroupDetailedAuxDto.professorId(),
                    previousGroupDetailedAuxDto.classroomId(),
                    new ArrayList<>(scheduleDtoList)));
            scheduleDtoList = null;
        }

        return groupDtoList;
    }

    @Transactional(readOnly = true)
    public String suggestGroupIdentifier(Integer subjectId, Integer curriculumId) {
        return groupItineraryJdbcRepository.suggestGroupItineraryIdentifier(subjectId, curriculumId);
    }

    @Transactional(readOnly = true)
    public Optional<GroupDto> getItineraryById(Integer id){
        return groupItineraryRepository.findById(id).map(this::toDto);
    }

    public GroupDto save(GroupRequest groupRequest) {
        GroupItinerary savedGroup = groupItineraryRepository.save(toEntity(groupRequest));
        scheduleItineraryService.saveAll(groupRequest.listSchedule(), savedGroup);

        return toDto(savedGroup);
    }



    public GroupDto edit(GroupRequest groupDto, Integer groupItineraryId){
        GroupItinerary groupItineraryFromDB = groupItineraryRepository.findById(groupItineraryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        Itinerary itinerary = new Itinerary();
        itinerary.setId(groupDto.itineraryId());
        groupItineraryFromDB.setItinerary(itinerary);


        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId();
        subjectCurriculumId.setCurriculumId(groupDto.curriculumId());
        subjectCurriculumId.setSubjectId(groupDto.subjectId());

        Subject subject = new Subject();
        subject.setId(groupDto.subjectId());

        SubjectCurriculum subjectCurriculum = new SubjectCurriculum();
        subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);
        subjectCurriculum.setSubject(subject);
        groupItineraryFromDB.setSubjectCurriculum(subjectCurriculum);

        groupItineraryFromDB.setIdentifier(groupDto.identifier());
        groupItineraryFromDB.setRemark(groupDto.remark());


        GroupItinerary savedGroup = groupItineraryRepository.save(groupItineraryFromDB);


        List<ScheduleItinerary> schedules = scheduleItineraryService.findSchedleByGroupId(groupItineraryId);
        scheduleItineraryService.deleteAll(schedules);

        scheduleItineraryService.saveAll(groupDto.listSchedule(), savedGroup);


        return toDto(savedGroup);
    }

    public void delete(Integer id) {
            List<ScheduleItinerary> schedules = scheduleItineraryService.findSchedleByGroupId(id);
            scheduleItineraryService.deleteAll(schedules);
            groupItineraryRepository.deleteById(id);
    }

    @Override
    public GroupDto toDto(GroupItinerary groupItinerary) {
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (groupItinerary.getScheduleItineraries() != null) {
            scheduleDtos = groupItinerary.getScheduleItineraries()
                    .stream()
                    .map(scheduleItineraryService::toDto)
                    .collect(Collectors.toList());
        }

        return new GroupDto(
                groupItinerary.getId(),
                groupItinerary.getSubjectCurriculum().getLevel(),
                groupItinerary.getSubjectCurriculum().getSubject().getName(),
                groupItinerary.getSubjectCurriculum().getSubject().getInitials(),
                groupItinerary.getIdentifier(),
                groupItinerary.getRemark(),
                scheduleDtos
        );
    }

    @Override
    public GroupItinerary toEntity(GroupDto groupDto) {
        return null;
    }

    public GroupItinerary toEntity(GroupRequest groupRequest) {
        GroupItinerary groupItinerary = new GroupItinerary();

        Itinerary itinerary = new Itinerary();
        itinerary.setId(groupRequest.itineraryId());
        groupItinerary.setItinerary(itinerary);

        SubjectCurriculumId subjectCurriculumId = new SubjectCurriculumId();
        subjectCurriculumId.setCurriculumId(groupRequest.curriculumId());
        subjectCurriculumId.setSubjectId(groupRequest.subjectId());

        Subject subject = new Subject();
        subject.setId(groupRequest.subjectId());

        SubjectCurriculum subjectCurriculum = new SubjectCurriculum();
        subjectCurriculum.setSubjectCurriculumId(subjectCurriculumId);
        subjectCurriculum.setSubject(subject);

        groupItinerary.setSubjectCurriculum(subjectCurriculum);

        groupItinerary.setIdentifier(groupRequest.identifier());
        groupItinerary.setRemark(groupRequest.remark());

        return groupItinerary;
    }
}
