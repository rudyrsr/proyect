package org.am.core.web.rest.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.SubjectDto;
import org.am.core.web.dto.admingeneral.SubjectRequest;
import org.am.core.web.service.admingeneral.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/areas/{areaID}/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectDto>> listSubjectByArea(@PathVariable final Integer areaID) {
        return ResponseEntity.ok().body(subjectService.getActiveSubjectsByAreaId(areaID));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable final Integer id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject with " + id + "not exist")));
    }

    @PostMapping
    public ResponseEntity<SubjectDto> createSubject(@RequestBody final SubjectRequest subjectRequest) throws URISyntaxException {

        SubjectDto subjectDB = subjectService.save(subjectRequest);

        return ResponseEntity.created(new URI("/admin/areas/{areaID}/subjects/" + subjectDB.id())).body(subjectDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDto> editSubject(@RequestBody final SubjectDto dto, @PathVariable final Integer id) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Invalid subject ID");
        }

        if (!Objects.equals(id, dto.id())) {
            throw new IllegalArgumentException("Invalid ID");
        }
        return ResponseEntity
                .ok()
                .body(subjectService.edit(dto));
    }


    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> delete(@PathVariable final Integer subjectId) {
        subjectService.delete(subjectId);
        return ResponseEntity.noContent().build();
    }
}
