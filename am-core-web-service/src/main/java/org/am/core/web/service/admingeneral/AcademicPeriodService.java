package org.am.core.web.service.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.AcademicPeriod;
import org.am.core.web.domain.entity.admingeneral.Area;
import org.am.core.web.dto.admingeneral.AcademicPeriodDto;
import org.am.core.web.dto.admingeneral.AcademicPeriodRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.AcademicPeriodRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class AcademicPeriodService implements CustomMap<AcademicPeriodDto, AcademicPeriod> {
    private final AcademicPeriodRepository academicPeriodRepository;

    @Transactional(readOnly = true)
    public List<AcademicPeriodDto> getAcademicPeriodsActiveByAreaId(Integer areaID) {

        return academicPeriodRepository.findAllByAreaIdAndActiveOrderById(areaID, Boolean.TRUE).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AcademicPeriodDto save(AcademicPeriodRequest academicPeriodRequest){
        return toDto(academicPeriodRepository.save(this.toEntity(academicPeriodRequest)));
    }

    public AcademicPeriodDto edit(AcademicPeriodDto academicPeriodDto){
        AcademicPeriod academicPeriod = academicPeriodRepository.findById(academicPeriodDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        academicPeriod.setYear(academicPeriodDto.year());
        academicPeriod.setName(academicPeriodDto.name());
        academicPeriod.setStartDate(academicPeriodDto.startDate());
        academicPeriod.setEndDate(academicPeriodDto.endDate());
        academicPeriod.setEnrollmentCost(academicPeriodDto.enrollmentCost());

        return toDto(academicPeriodRepository.save(academicPeriod));

    }
    public void delete(Integer id){
        try {
            academicPeriodRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) { // logical delete
            AcademicPeriod academicPeriod = academicPeriodRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
            academicPeriod.setActive(Boolean.FALSE);
            academicPeriodRepository.save(academicPeriod);
        }

    }

    @Transactional(readOnly = true)
    public Optional<AcademicPeriodDto> getAcademicPeriodById(Integer id) {
        return academicPeriodRepository.findById(id).map(this::toDto);
    }

    @Override
    public AcademicPeriodDto toDto(AcademicPeriod academicPeriod) {
        return new AcademicPeriodDto(
                academicPeriod.getId(),
                academicPeriod.getYear(),
                academicPeriod.getName(),
                academicPeriod.getStartDate(),
                academicPeriod.getEndDate(),
                academicPeriod.getEnrollmentCost()
        );
    }

    @Override
    public AcademicPeriod toEntity(AcademicPeriodDto academicPeriodDto) {
       return null;
    }
    private AcademicPeriod toEntity(AcademicPeriodRequest academicPeriodRequest) {
        AcademicPeriod academicPeriod = new AcademicPeriod();
        academicPeriod.setYear(academicPeriodRequest.year());
        academicPeriod.setName(academicPeriodRequest.name());
        academicPeriod.setStartDate(academicPeriodRequest.startDate());
        academicPeriod.setEndDate(academicPeriodRequest.endDate());
        academicPeriod.setActive(Boolean.TRUE);
        academicPeriod.setEnrollmentCost(academicPeriodRequest.enrollmentCost());

        Area area = new Area();
        area.setId(academicPeriodRequest.areaId());
        academicPeriod.setArea(area);

        return academicPeriod;
    }
}