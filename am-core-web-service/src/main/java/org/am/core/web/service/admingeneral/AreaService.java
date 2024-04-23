package org.am.core.web.service.admingeneral;

import org.am.core.web.domain.entity.admingeneral.Area;
import org.am.core.web.domain.entity.admingeneral.AreaParameters;
import org.am.core.web.dto.admingeneral.AreaDto;
import org.am.core.web.dto.admingeneral.AreaRequest;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.AreaParametersRepository;
import org.am.core.web.repository.jpa.admingeneral.AreaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.am.core.web.util.UtilConstants.DEFAULT_END_TIME_SCHEDULE;
import static org.am.core.web.util.UtilConstants.DEFAULT_START_TIME_SCHEDULE;
import static org.am.core.web.util.UtilConstants.DEFAULT_TIME_INTERVAL_SCHEDULE;

@Service
@Transactional
public class AreaService implements CustomMap<AreaDto, Area> {
    private final AreaRepository areaRepository;
    private final AreaParametersRepository areaParametersRepository;

    public AreaService(AreaRepository areaRepository, AreaParametersRepository areaParametersRepository) {
        this.areaRepository = areaRepository;
        this.areaParametersRepository = areaParametersRepository;
    }

    @Transactional(readOnly = true)
    public List<AreaDto> findAllActive() {
        return areaRepository.findAllByActiveOrderByName(Boolean.TRUE)
                .stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    public AreaDto save(AreaRequest areaRequest) {
        Area areaPersisted = areaRepository.save(this.toEntity(areaRequest));
        // Init with default values
        AreaParameters areaParameters = new AreaParameters(
                true,
                true,
                true,
                true,
                true,
                true,
                false,
                DEFAULT_START_TIME_SCHEDULE,
                DEFAULT_END_TIME_SCHEDULE,
                DEFAULT_TIME_INTERVAL_SCHEDULE,
                areaPersisted
        );
        areaParametersRepository.save(areaParameters);
        return toDto(areaPersisted);
    }

    public AreaDto edit(AreaDto areaDto) {
        Area areaFromDB = areaRepository.findById(areaDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        areaFromDB.setName(areaDto.name());
        areaFromDB.setInitials(areaDto.initials());
        return toDto(areaRepository.save(areaFromDB));
    }

    @Transactional(readOnly = true)
    public Optional<AreaDto> getAreaById(Integer id) {
        return areaRepository.findById(id).map(this::toDto);
    }

    public void delete(Integer id) {
        try {
            areaParametersRepository.deleteById(id);
            areaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            Area area = areaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));
            area.setActive(false);
            areaRepository.save(area);
        }
    }

    @Override
    public AreaDto toDto(Area area) {
        return new AreaDto(
                area.getId(),
                area.getName(),
                area.getInitials(),
                area.getDescription()
        );
    }

    @Override
    public Area toEntity(AreaDto areaDto) {
        Area area = new Area();
        area.setId(areaDto.id());
        area.setName(areaDto.name());
        area.setInitials(areaDto.initials());
        area.setDescription(areaDto.description());
        return area;
    }

    private Area toEntity(AreaRequest areaRequest) {
        return new Area(
                areaRequest.name(),
                areaRequest.initials(),
                Boolean.TRUE,
                areaRequest.description()
        );
    }
}
