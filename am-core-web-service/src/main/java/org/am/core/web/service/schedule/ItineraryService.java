package org.am.core.web.service.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.domain.entity.admingeneral.Curriculum;
import org.am.core.web.domain.entity.schedule.Itinerary;
import org.am.core.web.dto.schedule.ItineraryDto;
import org.am.core.web.dto.schedule.ItineraryRequest;
import org.am.core.web.repository.jdbc.schedule.ItineraryJdbcRepository;
import org.am.core.web.repository.jpa.CustomMap;
import org.am.core.web.repository.jpa.admingeneral.CurriculumRepository;
import org.am.core.web.repository.jpa.schedule.ItineraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItineraryService implements CustomMap<ItineraryDto, Itinerary> {
    private final ItineraryRepository itineraryRepository;
    private final ItineraryJdbcRepository itineraryJdbcRepository;
    private final CurriculumRepository curriculumRepository;

    public ItineraryDto save(ItineraryRequest itineraryRequest){
        return toDto(itineraryRepository.save(toEntity(itineraryRequest)));
    }

    public List<ItineraryDto> listItinerariesByAreaId(Integer areaId) {
        return itineraryJdbcRepository.getItinerariesByAreaId(areaId);
    }

    public Optional<ItineraryDto> getItineraryById(Integer id){
        return itineraryRepository.findById(id).map(this::toDto);
    }

    public ItineraryDto edit(ItineraryRequest itineraryRequest, Integer itineraryId){
        Itinerary itineraryFromDB = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));

        itineraryFromDB.setName(itineraryRequest.name());

        Curriculum curriculum=curriculumRepository.findById(itineraryRequest.curriculumId())
                .orElseThrow(()->new IllegalArgumentException("Invalid id"));

        itineraryFromDB.setCurriculum(curriculum);

        return toDto(itineraryRepository.save(itineraryFromDB));
    }

    @Override
    public ItineraryDto toDto(Itinerary itinerary) {

        return new ItineraryDto(
                itinerary.getId(),
                itinerary.getName(),
                itinerary.getCurriculum().getCareer().getId(),
                itinerary.getCurriculum().getCareer().getName(),
                itinerary.getCurriculum().getId(),
                itinerary.getCurriculum().getName()
        );
    }

    @Override
    public Itinerary toEntity(ItineraryDto itineraryDto) {
        return null;
    }

    public Itinerary toEntity(ItineraryRequest itineraryRequest) {
        Curriculum curriculum=curriculumRepository.findById(itineraryRequest.curriculumId())
                .orElseThrow(()->new IllegalArgumentException("Invalid id"));

        Itinerary itinerary = new Itinerary();
        itinerary.setName(itineraryRequest.name());
        itinerary.setActive(Boolean.TRUE);
        itinerary.setCurriculum(curriculum);
        return itinerary;
    }

    public void delete(Integer id){
        itineraryRepository.deleteById(id);
    }
}
