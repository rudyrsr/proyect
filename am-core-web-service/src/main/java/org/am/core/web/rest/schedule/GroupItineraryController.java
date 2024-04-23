package org.am.core.web.rest.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.dto.schedule.GroupRequest;
import org.am.core.web.service.schedule.GroupItineraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/itineraries/{itineraryId}/itinerary-groups")
@RequiredArgsConstructor
public class GroupItineraryController {

    private final GroupItineraryService groupItineraryService;
    @GetMapping
    public ResponseEntity<List<GroupDto>> listItineraryGroupsByCareerAndItinerary(@PathVariable final Integer careerId,
                                                              @PathVariable final Integer itineraryId){
        return ResponseEntity
                .ok()
                .body(groupItineraryService.getItineraryGroupsByCareerAndItinerary(careerId, itineraryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroupItineraryById(@PathVariable final Integer id){
        return ResponseEntity
                .ok()
                .body(groupItineraryService.getItineraryById(id).orElseThrow(IllegalArgumentException::new));
    }
    @GetMapping("/suggest-group-identifier")
    public ResponseEntity<String> suggestGroupIdentifier(@RequestParam final Integer subjectId,
                                                         @RequestParam final Integer curriculumId) {
        return ResponseEntity.ok().body(groupItineraryService.suggestGroupIdentifier(subjectId, curriculumId));
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@PathVariable final Integer areaId,
                                           @PathVariable final Integer careerId,
                                           @PathVariable final Integer itineraryId,
                                           @RequestBody final GroupRequest groupRequest) throws URISyntaxException {
        GroupDto groupDto = groupItineraryService.save(groupRequest);

        return ResponseEntity.created(
                new URI("/admin/areas/" + areaId + "/careers/" + careerId + "/itineraries/" + itineraryId +
                        "/itinerary-groups/" + groupDto.id())
                )
                .body(groupDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> edit(@PathVariable final Integer id, @RequestBody GroupRequest groupRequest){
        return ResponseEntity
                .ok()
                .body(groupItineraryService.edit(groupRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id){
        groupItineraryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
