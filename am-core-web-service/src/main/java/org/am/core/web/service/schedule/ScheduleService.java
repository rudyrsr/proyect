package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Classroom;
import org.am.core.web.domain.entity.professor.Professor;
import org.am.core.web.domain.entity.schedule.Group;
import org.am.core.web.domain.entity.schedule.Schedule;
import org.am.core.web.dto.schedule.ScheduleDetailedDto;
import org.am.core.web.dto.schedule.ScheduleDto;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.schedule.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;

import static org.am.core.web.util.CommonUtils.getFullName;
import static org.am.core.web.util.UtilConstants.NOT_ASSIGNED_YET;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService implements CustomMap<ScheduleDto, Schedule> {

    private final ScheduleRepository scheduleRepository;
    public void delete(Integer id) {
        scheduleRepository.deleteById(id);

    }

    public Schedule finById(Integer id){
        return scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id for Schedule"));
    }

    @Override
    public ScheduleDto toDto(Schedule schedule) {
        String fullName = NOT_ASSIGNED_YET;
        if (schedule.getProfessor() != null) {
            fullName = getFullName(schedule.getProfessor().getName(),
                    schedule.getProfessor().getLastName(),
                    schedule.getProfessor().getSecondLastName());
        } else if (schedule.getAssistant() != null && !schedule.getAssistant().isEmpty()) {
            fullName = schedule.getAssistant();
        }

        return new ScheduleDto(
                schedule.getId(),
                DayOfWeek.of(schedule.getWeekday()).toString(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getClassroom().getName(),
                schedule.getClassroom().getInitials(),
                fullName
        );
    }

    @Override
    public Schedule toEntity(ScheduleDto scheduleDto) {
        return null;
    }

    public Schedule toEntity(ScheduleDetailedDto scheduleRequest) {
        Schedule schedule = new Schedule();

        schedule.setWeekday(scheduleRequest.dayOfWeek().getValue());
        schedule.setStartTime(scheduleRequest.startTime());
        schedule.setEndTime(scheduleRequest.endTime());

        schedule.setProfessor(buildProfessorById(scheduleRequest.professorId()));

        schedule.setAssistant(scheduleRequest.assistant());

        Classroom classroom = new Classroom();
        classroom.setId(scheduleRequest.classroomId());
        schedule.setClassroom(classroom);

        Group group = new Group();
        group.setId(scheduleRequest.groupId());
        schedule.setGroup(group);

        return schedule;
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
}
