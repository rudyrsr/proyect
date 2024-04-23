package org.am.core.web.rest.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.ItineraryDto;
import org.am.core.web.dto.schedule.ItineraryRequest;
import org.am.core.web.service.schedule.ItineraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/admin/areas/{areaId}/itineraries")
@RequiredArgsConstructor
public class ItineraryController {
    private final ItineraryService itineraryService;

    @PostMapping
    public ResponseEntity<ItineraryDto> create(@RequestBody final ItineraryRequest itineraryRequest) throws URISyntaxException {
        ItineraryDto itineraryDto = itineraryService.save(itineraryRequest);

        return ResponseEntity.created(new URI("/admin/areas/itineraries/" + itineraryDto.id())).body(itineraryDto);
    }

    @GetMapping
    public ResponseEntity<List<ItineraryDto>> listByAreaId(@PathVariable final Integer areaId){
        return ResponseEntity.ok().body(itineraryService.listItinerariesByAreaId(areaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItineraryDto> getItinerayById(@PathVariable final Integer id){
        return ResponseEntity
                .ok()
                .body(itineraryService.getItineraryById(id).orElseThrow(IllegalArgumentException::new));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItineraryDto> edit(@PathVariable final Integer id, @RequestBody ItineraryRequest itineraryRequest){
        return ResponseEntity
                .ok()
                .body(itineraryService.edit(itineraryRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id){

        itineraryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
