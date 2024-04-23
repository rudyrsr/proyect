package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.domain.entity.professor.Professor;
import org.am.core.web.domain.entity.schedule.*;
import org.am.core.web.dto.schedule.*;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.ScheduleItineraryRepository;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.am.core.web.util.CommonUtils.getFullName;
import static org.am.core.web.util.UtilConstants.NOT_ASSIGNED_YET;


@Service
@RequiredArgsConstructor
public class ScheduleItineraryService implements CustomMap<ScheduleDto, ScheduleItinerary> {

    private final ScheduleItineraryRepository scheduleItineraryRepository;


    public ScheduleDto save(ScheduleRequest scheduleRequest){
        return toDto(scheduleItineraryRepository.save(toEntity(scheduleRequest)));
    }


    public void saveAll(List<ScheduleRequest> scheduleRequestList, GroupItinerary savedGroup) {

        List<ScheduleItinerary> scheduleItineraries = new ArrayList<>();

        for(ScheduleRequest request : scheduleRequestList) {
            ScheduleItinerary schedule = toEntity(request);
            schedule.setGroupItinerary(savedGroup);
            scheduleItineraries.add(schedule);
        }

        scheduleItineraryRepository.saveAll(scheduleItineraries);
    }

    public ScheduleDto edit(ScheduleRequest scheduleDto, Integer scheduleItineraryId){
        ScheduleItinerary scheduleItineraryFromDB = scheduleItineraryRepository.findById(scheduleItineraryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        scheduleItineraryFromDB.setDayOfWeek(scheduleDto.dayOfWeek().getValue());
        scheduleItineraryFromDB.setStartTime(scheduleDto.startTime());
        scheduleItineraryFromDB.setEndTime(scheduleDto.endTime());

        scheduleItineraryFromDB.setProfessor(buildProfessorById(scheduleDto.professorId()));

        scheduleItineraryFromDB.setAssistant(scheduleDto.assistant());

        Classroom classroom = new Classroom();
        classroom.setId(scheduleDto.classroomId());
        scheduleItineraryFromDB.setClassroom(classroom);

        GroupItinerary groupItinerary = new GroupItinerary();
        groupItinerary.setId(scheduleDto.groupItineraryId());
        scheduleItineraryFromDB.setGroupItinerary(groupItinerary);

        return toDto(scheduleItineraryRepository.save(scheduleItineraryFromDB));
    }

    public void delete(Integer id) {
        scheduleItineraryRepository.deleteById(id);
    }
    public void deleteAll( List<ScheduleItinerary> schedules) {
        scheduleItineraryRepository.deleteAll(schedules);
    }
    @Override
    public ScheduleDto toDto(ScheduleItinerary scheduleItinerary) {
        String fullName = NOT_ASSIGNED_YET;
        if (scheduleItinerary.getProfessor() != null) {
            fullName = getFullName(scheduleItinerary.getProfessor().getName(),
                    scheduleItinerary.getProfessor().getLastName(),
                    scheduleItinerary.getProfessor().getSecondLastName());
        } else if (scheduleItinerary.getAssistant() != null && !scheduleItinerary.getAssistant().isEmpty()) {
            fullName = scheduleItinerary.getAssistant();
        }

        return new ScheduleDto(
                scheduleItinerary.getId(),
                DayOfWeek.of(scheduleItinerary.getDayOfWeek()).toString(),
                scheduleItinerary.getStartTime(),
                scheduleItinerary.getEndTime(),
                scheduleItinerary.getClassroom().getName(),
                scheduleItinerary.getClassroom().getInitials(),
                fullName
        );
    }

    @Override
    public ScheduleItinerary toEntity(ScheduleDto scheduleDto) {
        return null;
    }

    public ScheduleItinerary toEntity(ScheduleRequest scheduleRequest) {
        ScheduleItinerary scheduleItinerary = new ScheduleItinerary();

        scheduleItinerary.setDayOfWeek(scheduleRequest.dayOfWeek().getValue());
        scheduleItinerary.setStartTime(scheduleRequest.startTime());
        scheduleItinerary.setEndTime(scheduleRequest.endTime());

        scheduleItinerary.setProfessor(buildProfessorById(scheduleRequest.professorId()));

        scheduleItinerary.setAssistant(scheduleRequest.assistant());

        Classroom classroom = new Classroom();
        classroom.setId(scheduleRequest.classroomId());
        scheduleItinerary.setClassroom(classroom);

        GroupItinerary groupItinerary = new GroupItinerary();
        groupItinerary.setId(scheduleRequest.groupItineraryId());
        scheduleItinerary.setGroupItinerary(groupItinerary);

        return scheduleItinerary;
    }

    private Professor buildProfessorById(Integer professorId) {
        Professor professor = new Professor();
        if (professorId != null) {
            professor.setId(professorId);
        } else {
            professor = null;
        }

        return professor;
    }

    public List<ScheduleItinerary> findSchedleByGroupId(Integer groupId){
        return scheduleItineraryRepository.findByGroupItineraryId(groupId);
    }
}


