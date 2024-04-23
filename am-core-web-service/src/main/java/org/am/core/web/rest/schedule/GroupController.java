package org.am.core.web.rest.schedule;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.schedule.GroupDto;
import org.am.core.web.dto.schedule.GroupRequest;
import org.am.core.web.dto.schedule.GroupEditRequest;
import org.am.core.web.service.schedule.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/academic-periods/{academicPeriodId}/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/bulk-create-groups")
    public ResponseEntity<GroupDto> bulkCreate(@PathVariable final Integer careerId,
                                               @PathVariable final Integer academicPeriodId,
                                               @RequestParam final Integer itineraryId) {
        groupService.generateGroupsFromItinerary(careerId, itineraryId, academicPeriodId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@RequestBody final GroupRequest groupRequest,
                                           @PathVariable final Integer areaId,
                                           @PathVariable final Integer careerId,
                                           @PathVariable final Integer academicPeriodId
                                           ) throws URISyntaxException {
         GroupDto groupFromDB  = groupService.save(groupRequest, academicPeriodId);
         return ResponseEntity.created(new URI("/admin/areas/"
                 + areaId + "/careers/" + careerId + "/academic-periods/"
                 + academicPeriodId + groupFromDB.id())).body(groupFromDB);

    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> listGroupsByCareerAndAcademicPeriod(@PathVariable final Integer careerId,
                                                                                  @PathVariable final Integer academicPeriodId){
        return ResponseEntity
                .ok()
                .body(groupService.listGroupsByCareerAndAcademicPeriod(careerId, academicPeriodId));
    }
    @GetMapping("/suggest-group-identifier")
    public ResponseEntity<String> suggestGroupIdentifier(@RequestParam final Integer subjectId,
                                                         @RequestParam final Integer curriculumId) {
        return ResponseEntity
                .ok()
                .body(groupService.suggestGroupIdentifier(subjectId, curriculumId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> edit(@RequestBody final GroupEditRequest dto,
                                         @PathVariable final Integer academicPeriodId,
                                              @PathVariable final Integer id) {

        return ResponseEntity
                .ok()
                .body(groupService.edit(dto, id, academicPeriodId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Integer id) {
            groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
