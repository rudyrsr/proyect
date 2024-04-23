package org.am.core.web.service.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.AreaParameters;
import org.am.core.web.dto.admingeneral.ScheduleParametersDto;
import org.am.core.web.dto.admingeneral.ScheduleParametersRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.AreaParametersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleParametersService implements CustomMap<ScheduleParametersDto, AreaParameters> {

    private final AreaParametersRepository areaParametersRepository;

    @Transactional(readOnly = true)
    public Optional<ScheduleParametersDto> getScheduleParametersById(Integer areaId) {
        return areaParametersRepository.findById(areaId).map(this::toDto);
    }

    public ScheduleParametersDto edit(Integer areaId, ScheduleParametersRequest parametersRequest) {
        AreaParameters areaParametersFromDB = areaParametersRepository.findById(areaId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        areaParametersFromDB.setMondaySchedule(parametersRequest.mondaySchedule());
        areaParametersFromDB.setTuesdaySchedule(parametersRequest.tuesdaySchedule());
        areaParametersFromDB.setWednesdaySchedule(parametersRequest.wednesdaySchedule());
        areaParametersFromDB.setThursdaySchedule(parametersRequest.thursdaySchedule());
        areaParametersFromDB.setFridaySchedule(parametersRequest.fridaySchedule());
        areaParametersFromDB.setSaturdaySchedule(parametersRequest.saturdaySchedule());
        areaParametersFromDB.setSundaySchedule(parametersRequest.sundaySchedule());
        areaParametersFromDB.setStartTimeSchedule(parametersRequest.startTimeSchedule());
        areaParametersFromDB.setEndTimeSchedule(parametersRequest.endTimeSchedule());
        areaParametersFromDB.setTimeIntervalSchedule(parametersRequest.timeIntervalSchedule());

        return toDto(areaParametersRepository.save(areaParametersFromDB));
    }

    @Override
    public ScheduleParametersDto toDto(AreaParameters areaParameters) {
        return new ScheduleParametersDto(
                areaParameters.getAreaId(),
                areaParameters.getArea().getName(),
                areaParameters.getArea().getInitials(),
                areaParameters.getMondaySchedule(),
                areaParameters.getTuesdaySchedule(),
                areaParameters.getWednesdaySchedule(),
                areaParameters.getThursdaySchedule(),
                areaParameters.getFridaySchedule(),
                areaParameters.getSaturdaySchedule(),
                areaParameters.getSundaySchedule(),
                areaParameters.getStartTimeSchedule(),
                areaParameters.getEndTimeSchedule(),
                areaParameters.getTimeIntervalSchedule()
        );
    }

    @Override
    public AreaParameters toEntity(ScheduleParametersDto scheduleParametersDto) {
        return null;
    }
}
