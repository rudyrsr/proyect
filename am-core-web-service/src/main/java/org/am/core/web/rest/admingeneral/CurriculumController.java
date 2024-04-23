package org.am.core.web.rest.admingeneral;

import lombok.RequiredArgsConstructor;
import org.am.core.web.dto.admingeneral.CurriculumDetailedRequest;
import org.am.core.web.dto.admingeneral.CurriculumDto;
import org.am.core.web.dto.admingeneral.CurriculumRequest;
import org.am.core.web.service.admingeneral.CurriculumService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin/areas/{areaId}/careers/{careerId}/curriculums")
@RequiredArgsConstructor
public class CurriculumController {

    private final CurriculumService curriculumService;
    @GetMapping
    public ResponseEntity<List<CurriculumDto>> listByCareerId(@PathVariable final Integer careerId) {
        return ResponseEntity.ok().body(curriculumService.getCurriculumsByCareerId(careerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCurriculumById(@PathVariable final Integer id,
                                                                 @RequestParam(required = false) final Boolean detailed) {
        return ResponseEntity.ok().body(
                detailed ?
                        curriculumService.getDetailedById(id).orElseThrow(() -> new IllegalArgumentException("Curriculum with "+ id+" not exist"))
                        :
                        curriculumService.getById(id).orElseThrow(() -> new IllegalArgumentException("Curriculum with "+ id+" not exist"))
        );
    }

    @PostMapping
    public ResponseEntity<CurriculumDto> create(@RequestBody final CurriculumRequest curriculumRequest,
                                            @PathVariable final Integer areaId) throws URISyntaxException {
        CurriculumDto curriculumDto = curriculumService.save(curriculumRequest);
        return ResponseEntity.created(new URI("/admin/areas/"+areaId+"/careers" + curriculumRequest.careerId() + "/curriculums/" + areaId)).body(curriculumDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurriculumDto> edit(@RequestBody final CurriculumDetailedRequest dto,
                                                      @PathVariable final Integer id) {
        if (dto.curriculumId() == null) {
            throw new IllegalArgumentException("Invalid career ID");
        }

        if (!Objects.equals(id, dto.curriculumId())) {
            throw new IllegalArgumentException("Invalid ID");
        }

        return ResponseEntity
                .ok()
                .body(curriculumService.edit(dto));
    }

    @DeleteMapping("/{curriculumid}")
    public ResponseEntity<Void> delete(@PathVariable final Integer curriculumid) {

        try {
            curriculumService.delete(curriculumid);
        } catch (DataIntegrityViolationException e) {
            curriculumService.logicalDelete(curriculumid);
        }

        return ResponseEntity.noContent().build();
    }
}
