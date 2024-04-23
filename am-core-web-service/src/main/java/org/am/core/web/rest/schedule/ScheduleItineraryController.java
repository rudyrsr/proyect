package org.am.core.web.rest.schedule;


import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.ScheduleDto;
import org.am.core.web.dto.schedule.ScheduleRequest;
import org.am.core.web.service.schedule.ScheduleItineraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/admin/group/{groupId}/scheduleItinerary")
@RequiredArgsConstructor
public class ScheduleItineraryController {

    private final ScheduleItineraryService scheduleItineraryService;



    @PostMapping
    public ResponseEntity<ScheduleDto> create(@RequestBody final ScheduleRequest scheduleRequest, @PathVariable final Integer groupId) throws URISyntaxException {
        ScheduleDto scheduleDto = scheduleItineraryService.save(scheduleRequest);

        return ResponseEntity.created(new URI("/admin/group/" + groupId + "/scheduleItinerary"+ scheduleDto.id())).body(scheduleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> edit(@PathVariable final Integer id, @RequestBody ScheduleRequest scheduleRequest){
        return ResponseEntity
                .ok()
                .body(scheduleItineraryService.edit(scheduleRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id){

        scheduleItineraryService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
